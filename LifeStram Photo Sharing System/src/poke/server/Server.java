/*
 * copyright 2012, gash
 * 
 * Gash licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package poke.server;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.jboss.netty.bootstrap.Bootstrap;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.AdaptiveReceiveBufferSizePredictorFactory;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.ChannelGroupFuture;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import poke.monitor.HeartMonitor;
import poke.monitor.MonitorNeighbor;
import poke.monitor.ReconnectNeighbor;

import poke.server.conf.JsonUtil;
import poke.server.conf.ServerConf;
import poke.server.management.ManagementDecoderPipeline;
import poke.server.management.ManagementQueue;
import poke.server.management.ServerHeartbeat;
import poke.server.resources.ResourceFactory;
import poke.server.routing.ServerDecoderPipeline;

/**
 * Note high surges of messages can close down the channel if the handler cannot
 * process the messages fast enough. This design supports message surges that
 * exceed the processing capacity of the server through a second thread pool
 * (per connection or per server) that performs the work. Netty's boss and
 * worker threads only processes new connections and forwarding requests.
 * <p>
 * Reference Proactor pattern for additional information.
 * 
 * @author gash
 * 
 */
public class Server {
	protected static Logger logger = LoggerFactory.getLogger("server");

	protected static final ChannelGroup allChannels = new DefaultChannelGroup(
			"server");
	protected static HashMap<Integer, Bootstrap> bootstrap = new HashMap<Integer, Bootstrap>();
	protected ChannelFactory cf, mgmtCF;
	protected ServerConf conf;
	protected ServerHeartbeat heartbeat;

	// List of neighbors for the server
	protected ArrayList<ServerConf.NeighbourConf> neighbors;

	// Server's own node id
	public static String ownNodeID;

	// Path for the Hibernate Config file for the Server to connect to its
	// Database
	public static String configPath;

	/**
	 * static because we need to get a handle to the factory from the shutdown
	 * resource
	 */
	public static void shutdown() {
		try {
			ChannelGroupFuture grp = allChannels.close();
			grp.awaitUninterruptibly(5, TimeUnit.SECONDS);
			for (Bootstrap bs : bootstrap.values())
				bs.getFactory().releaseExternalResources();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("Server shutdown");
		System.exit(0);
	}

	/**
	 * initialize the server with a configuration of it's resources
	 * 
	 * @param cfg
	 */
	public Server(File cfg) {
		init(cfg);
	}

	private void init(File cfg) {
		// resource initialization - how message are processed
		BufferedInputStream br = null;
		try {
			byte[] raw = new byte[(int) cfg.length()];
			br = new BufferedInputStream(new FileInputStream(cfg));
			br.read(raw);
			conf = JsonUtil.decode(new String(raw), ServerConf.class);
			logger.info(conf.toString());
			ResourceFactory.initialize(conf);

		} catch (Exception e) {
		}

		// communication - external (TCP) using asynchronous communication
		cf = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool());
		// internal using TCP - a better option
		mgmtCF = new NioServerSocketChannelFactory(
		// Executors.newCachedThreadPool(),
		// Executors.newFixedThreadPool(2)); Commented because the server was
		// not accepting more than two connections then
				Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool());

	}

	public void release() {
		if (heartbeat != null)
			heartbeat.release();
	}

	private void createPublicBoot(int ownPort) {
		// construct boss and worker threads (num threads = number of cores)

		ServerBootstrap bs = new ServerBootstrap(cf);

		// Set up the pipeline factory.
		bs.setPipelineFactory(new ServerDecoderPipeline());

		// tweak for performance
		bs.setOption("child.tcpNoDelay", true);
		bs.setOption("child.keepAlive", true);
		bs.setOption("receiveBufferSizePredictorFactory",
				new AdaptiveReceiveBufferSizePredictorFactory(1024 * 2,
						1024 * 4, 1048576));

		bootstrap.put(ownPort, bs);

		// Bind and start to accept incoming connections.
		Channel ch = bs.bind(new InetSocketAddress(ownPort));
		allChannels.add(ch);
	}

	private void createManagementBoot(int port) {
		// construct boss and worker threads (num threads = number of cores)
		// TCP
		ServerBootstrap bs = new ServerBootstrap(mgmtCF);

		// Set up the pipeline factory.
		bs.setPipelineFactory(new ManagementDecoderPipeline());

		// tweak for performance
		// bs.setOption("tcpNoDelay", true);
		bs.setOption("child.tcpNoDelay", true);
		bs.setOption("child.keepAlive", true);

		bootstrap.put(port, bs);

		// Bind and start to accept incoming connections.
		Channel ch = bs.bind(new InetSocketAddress(port));
		allChannels.add(ch);

	}

	protected void run() {
		logger.info(conf.toString());
		int ownPort = Integer.parseInt(conf.getServer().getProperty("port"));
		int ownMgmtPort = Integer.parseInt(conf.getServer().getProperty(
				"port.mgmt"));

		neighbors = new ArrayList<ServerConf.NeighbourConf>();

		Iterator<ServerConf.NeighbourConf> m = conf.getServerconnect()
				.iterator();

		while (m.hasNext()) {
			ServerConf.NeighbourConf n = (ServerConf.NeighbourConf) m.next();
			neighbors.add(n);
			ManagementQueue.nodeMgmtPortLog.put(n.getProperty("cid"),
					Integer.parseInt(n.getProperty("cportmgmt")));
			ManagementQueue.nodePortLog.put(n.getProperty("cid"),
					Integer.parseInt(n.getProperty("cport")));
		}

		System.out.println(" Neighbour List for Server : " + neighbors);

		// start communication
		createPublicBoot(ownPort);
		createManagementBoot(ownMgmtPort);

		// start management
		ManagementQueue.startup();

		ownNodeID = conf.getServer().getProperty("node.id");
		logger.info("SETTING the own node id::" + ownNodeID);
		configPath = "/home/krunal/Public/" + ownNodeID + "hibernate.cfg.xml";

		// start heartbeat
		heartbeat = ServerHeartbeat.getInstance(ownNodeID);
		heartbeat.setName("heartbeatThread" + ownNodeID.toUpperCase());
		heartbeat.start();
		logger.info("Server ########### " + ownNodeID + " ############# ready");

		for (int i = 0; i < neighbors.size(); i++) {
			ServerConf.NeighbourConf n = neighbors.get(i);
			int cport = Integer.parseInt(n.getProperty("cportmgmt"));
			
			// Creating a new monitor for every neighbor to be monitored
			
			MonitorNeighbor monitorNeighbor = new MonitorNeighbor(cport,
					ownNodeID);
			monitorNeighbor.start();
		}
		
		// Thread to check the neighbors continuously for failure recovery
		ReconnectNeighbor reconnectNeighbor = new ReconnectNeighbor(ownNodeID);
		reconnectNeighbor.start();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		if (args.length != 1) {
			System.err.println("Usage: java "
					+ Server.class.getClass().getName() + " conf-file");
			System.exit(1);
		}

		File cfg = new File(args[0]);
		if (!cfg.exists()) {
			Server.logger.error("configuration file does not exist: " + cfg);
			System.exit(2);
		}

		Server svr = new Server(cfg);
		svr.run();
		/*	
	*/
	}
}
