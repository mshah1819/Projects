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
package poke.monitor;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.Bootstrap;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.bootstrap.ConnectionlessBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioDatagramChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import poke.server.Neighbor;
import poke.server.management.ServerHeartbeat.CloseHeartListener;
import poke.server.management.ServerHeartbeat.HeartData;

import eye.Comm.Management;
import eye.Comm.Network;
import eye.Comm.Network.Action;

public class HeartMonitor {
	protected static Logger logger = LoggerFactory.getLogger("monitor");

	private String host;
	private int port;
	private String ownMonitorId;
	protected ChannelFuture channelFuture; // do not use directly call
											// connect()!
	protected ClientBootstrap bootstrap;
	protected static HashMap<String, Boolean> neighborHBData = new HashMap<String, Boolean>();

	public HeartMonitor(String host, int port, String ownMonitorId) {
		this.host = host;
		this.port = port;
		this.ownMonitorId = ownMonitorId;

		initTCP();
	}

	protected void release() {
		// if (cf != null)
		// cf.releaseExternalResources();
	}

	protected void initUDP() {
		NioDatagramChannelFactory cf = new NioDatagramChannelFactory(
				Executors.newCachedThreadPool());
		ConnectionlessBootstrap bootstrap = new ConnectionlessBootstrap(cf);

		bootstrap.setOption("connectTimeoutMillis", 10000);
		bootstrap.setOption("keepAlive", true);

		// Set up the pipeline factory.
		bootstrap.setPipelineFactory(new MonitorPipeline());
	}

	protected void initTCP() {
		bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(
		// Executors.newCachedThreadPool(),
		// Executors.newFixedThreadPool(2)));
				Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool()));

		bootstrap.setOption("connectTimeoutMillis", 10000);
		bootstrap.setOption("tcpNoDelay", true);
		bootstrap.setOption("keepAlive", true);

		bootstrap.setPipelineFactory(new MonitorPipeline());

	}

	/**
	 * create connection to remote server
	 * 
	 * @return
	 */
	protected Channel connect() {
		// Start the connection attempt.
		channelFuture = bootstrap.connect(new InetSocketAddress(host, port));
		channelFuture.awaitUninterruptibly();
		return channelFuture.getChannel();
	}

	public boolean waitForever() {
		boolean connected = false;
		try {
			Channel ch = connect();

			while (true) {
				if (ch.isConnected()) {
					break;
				} else {
					Thread.sleep(5000);
					ch = connect();
				}
			}
			Network.Builder n = Network.newBuilder();
			n.setNodeId("monitor " + ownMonitorId);
			n.setAction(Action.NODEJOIN);
			Management.Builder m = Management.newBuilder();
			m.setGraph(n.build());
			logger.info("WRITING via channel to the port ::" + port);
			ch.write(m.build());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connected;
	}


}
