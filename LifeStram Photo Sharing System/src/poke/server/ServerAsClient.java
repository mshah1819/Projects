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

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import poke.server.routing.ServerAsClientDecoderPipeline;
import poke.server.routing.ServerDecoderPipeline;

import com.google.protobuf.GeneratedMessage;

import eye.Comm.Finger;
import eye.Comm.Header;
import eye.Comm.Payload;
import eye.Comm.Request;

/**
 * provides an abstraction of the communication to the remote server.
 * 
 * @author gash
 * 
 */
public class ServerAsClient {
	public static Logger logger = LoggerFactory.getLogger("serverAsClient");

	private String host;
	private int port;
	private ChannelFuture channelFuture; // do not use directly call connect()!
	private ClientBootstrap bootstrap;
	private LinkedBlockingDeque<com.google.protobuf.GeneratedMessage> outbound;

	protected ServerAsClient(String host, int port) {
		this.host = host;
		this.port = port;

		init();
	}

	/**
	 * release all resources
	 */
	public void release() {
		bootstrap.releaseExternalResources();
	}

	public static ServerAsClient initConnection(String host, int port) {

		ServerAsClient rtn = new ServerAsClient(host, port);
		return rtn;
	}

	private void init() {
		// Configure the client.
		bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(
				Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool()));

		bootstrap.setOption("connectTimeoutMillis", 10000);
		bootstrap.setOption("tcpNoDelay", true);
		bootstrap.setOption("keepAlive", true);

		// Set up the pipeline factory.
		bootstrap.setPipelineFactory(new ServerAsClientDecoderPipeline());

	}

	/**
	 * create connection to remote server
	 * 
	 * @return
	 */
	public Channel connect() {
		// Start the connection attempt.
		if (channelFuture == null) {
			logger.info("---> connecting to:::" + port + " from ServerAsClient");
			channelFuture = bootstrap
					.connect(new InetSocketAddress(host, port));
			// cleanup on lost connection
		}

		// wait for the connection to establish
		channelFuture.awaitUninterruptibly();
		if (channelFuture.isDone() && channelFuture.isSuccess()) {

			return channelFuture.getChannel();
		} else
			throw new RuntimeException(
					"Not able to establish connection to server");
	}
}
