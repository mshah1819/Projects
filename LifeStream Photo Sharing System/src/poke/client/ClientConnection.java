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
package poke.client;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.ByteString;
import com.google.protobuf.GeneratedMessage;
import eye.Comm.AddUser;
import eye.Comm.AddImage;
import eye.Comm.Header;
import eye.Comm.Operation;
import eye.Comm.Payload;
import eye.Comm.Request;
import eye.Comm.RetrieveImage;

/**
 * provides an abstraction of the communication to the remote server.
 * 
 * @author gash
 * 
 */
public class ClientConnection {
	protected static Logger logger = LoggerFactory.getLogger("client");

	private String host;
	private int port;
	private ChannelFuture channelFuture; // do not use directly call connect()!
	private ClientBootstrap bootstrap;
	private LinkedBlockingDeque<com.google.protobuf.GeneratedMessage> outbound;
	private OutboundWorker worker;

	protected ClientConnection(String host, int port) {
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

	public static ClientConnection initConnection(String host, int port) {

		ClientConnection rtn = new ClientConnection(host, port);
		return rtn;
	}

	public void poke(String tag, int num) {

		try {
			// enqueue message
			Request req = createAddUserReq("useful");
			outbound.put(req);
		} catch (InterruptedException e) {
			logger.warn("Unable to deliver message, queuing");
		}
	}

	public void addUser(String userName) {

		try {
			// enqueue message
			Request req = createAddUserReq(userName);
			outbound.put(req);
		} catch (InterruptedException e) {
			logger.warn("Unable to deliver message, queuing");
		}
	}

	public void addImage(int userId, byte[] byteImage) {

		try {
			eye.Comm.User user = getUserProtoObject(userId);
			Request req = createAddImageReq(user,
					ByteString.copyFrom(byteImage));
			outbound.put(req);
		} catch (InterruptedException e) {
			logger.warn("Unable to deliver message, queuing");
		}
	}
	
	
	public void retrieveImage(int userId) {

		try {
			eye.Comm.User user = getUserProtoObject(userId);
			Request req = createRetrieveImageReq(user);
			outbound.put(req);
		} catch (InterruptedException e) {
			logger.warn("Unable to deliver message, queuing");
		}
	}

	private void init() {
		// the queue to support client-side surging
		System.out.println("Creating a queue to send message from client...");
		outbound = new LinkedBlockingDeque<com.google.protobuf.GeneratedMessage>();

		// Configure the client.
		bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(
				Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool()));

		bootstrap.setOption("connectTimeoutMillis", 10000);
		bootstrap.setOption("tcpNoDelay", true);
		bootstrap.setOption("keepAlive", true);

		// Set up the pipeline factory.
		bootstrap.setPipelineFactory(new ClientDecoderPipeline());

		// start outbound message processor
		worker = new OutboundWorker(this);
		worker.setName("CCOtbndWrkrThrd2CnnctTo" + this.port);
		logger.info("Starting thread:" + worker);
		worker.start();
	}

	public Request createAddUserReq(String userName) {
		// data to send
		AddUser.Builder au = eye.Comm.AddUser.newBuilder();
		au.setUserName(userName);

		Operation.Builder opn = eye.Comm.Operation.newBuilder();
		opn.setAddUser(au);

		// payload containing data
		Request.Builder r = Request.newBuilder();
		eye.Comm.Payload.Builder p = Payload.newBuilder();
		p.setOperation(opn);

		r.setBody(p.build());

		// header with routing info
		eye.Comm.Header.Builder h = Header.newBuilder();
		h.setOriginator("client");
		h.setTag("test finger");
		h.setTime(System.currentTimeMillis());
		h.setRoutingId(eye.Comm.Header.Routing.FINGER);
		r.setHeader(h.build());
		r.setCorrelationId(1);
		eye.Comm.Request req = r.build();
		return req;
	}

	public eye.Comm.User getUserProtoObject(int userId) {
		eye.Comm.User.Builder user = eye.Comm.User.newBuilder();
		user.setUserId(userId);
		eye.Comm.User returnUser = user.build();
		return returnUser;
	}

	public Request createAddImageReq(eye.Comm.User user, ByteString bytes) {
		System.out.println("bytes:"+bytes);
		// data to send
		AddImage.Builder ai = eye.Comm.AddImage.newBuilder();
		ai.setUser(user);
		ai.setImage(bytes);

		Operation.Builder opn = eye.Comm.Operation.newBuilder();
		opn.setAddImage(ai);

		// payload containing data
		Request.Builder r = Request.newBuilder();
		eye.Comm.Payload.Builder p = Payload.newBuilder();
		p.setOperation(opn);

		r.setBody(p.build());

		// header with routing info
		eye.Comm.Header.Builder h = Header.newBuilder();
		h.setOriginator("client");
		h.setTag("test finger");
		h.setTime(System.currentTimeMillis());
		h.setRoutingId(eye.Comm.Header.Routing.FINGER);
		r.setHeader(h.build());
		r.setCorrelationId(1);
		eye.Comm.Request req = r.build();
		return req;
	}
	
	/**
	 * Creating an image retrieval request
	 * @param user
	 * @return
	 */
	public Request createRetrieveImageReq(eye.Comm.User user) {
		
		// data to send
		RetrieveImage.Builder ri = eye.Comm.RetrieveImage.newBuilder();
		ri.setUser(user);
		Operation.Builder opn = eye.Comm.Operation.newBuilder();
		opn.setRetrieveImage(ri);
		// payload containing data
		Request.Builder r = Request.newBuilder();
		eye.Comm.Payload.Builder p = Payload.newBuilder();
		p.setOperation(opn);
		r.setBody(p.build());
		// header with routing info
		eye.Comm.Header.Builder h = Header.newBuilder();
		h.setOriginator("client");
		h.setTag("test finger");
		h.setTime(System.currentTimeMillis());
		h.setRoutingId(eye.Comm.Header.Routing.FINGER);
		r.setHeader(h.build());
		r.setCorrelationId(1);
		eye.Comm.Request req = r.build();
		return req;
	}

	/**
	 * create connection to remote server
	 * 
	 * @return
	 */
	protected Channel connect() {
		// Start the connection attempt.
		if (channelFuture == null) {
			channelFuture = bootstrap
					.connect(new InetSocketAddress(host, port));
			}

		// wait for the connection to establish
		channelFuture.awaitUninterruptibly();

		if (channelFuture.isDone() && channelFuture.isSuccess()) {
			System.out.println("Channel Future received from port :" + port
					+ " to the client");
			return channelFuture.getChannel();
		} else
			throw new RuntimeException(
					"Not able to establish connection to server");
	}

	/**
	 * queues outgoing messages - this provides surge protection if the client
	 * creates large numbers of messages.
	 * 
	 * @author gash
	 * 
	 */
	protected class OutboundWorker extends Thread {
		ClientConnection clientConnection;
		boolean forever = true;

		public OutboundWorker(ClientConnection conn) {
			this.clientConnection = conn;

			if (conn.outbound == null)
				throw new RuntimeException(
						"connection worker detected null queue");
		}

		@Override
		public void run() {
			System.out
					.println("into the run method of client outoundWorker Thread execution");
			Channel ch = clientConnection.connect();
			if (ch == null || !ch.isOpen()) {
				ClientConnection.logger
						.error("connection missing, no outbound communication");
				return;
			}

			while (true) {
				if (!forever && clientConnection.outbound.size() == 0)
					break;

				try {
					// block until a message is enqueued
					GeneratedMessage msg = clientConnection.outbound.take();
					if (ch.isWritable()) {
						ClientHandler handler = clientConnection.connect()
								.getPipeline().get(ClientHandler.class);

						if (!handler.send(msg))
							clientConnection.outbound.putFirst(msg);

					} else
						clientConnection.outbound.putFirst(msg);
				} catch (InterruptedException ie) {
					break;
				} catch (Exception e) {
					ClientConnection.logger.error(
							"Unexpected communcation failure", e);
					break;
				}
			}

			if (!forever) {
				ClientConnection.logger.info("connection queue closing");
			}
		}
	}
}
