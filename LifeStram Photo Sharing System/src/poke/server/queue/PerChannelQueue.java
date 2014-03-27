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
package poke.server.queue;

import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingDeque;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import poke.client.ClientConnection;
import poke.client.ClientHandler;
import poke.server.Server;
//import poke.server.HeartBeatLog;
import poke.server.ServerAsClient;
import poke.server.management.ManagementQueue;
import poke.server.resources.Resource;
import poke.server.resources.ResourceFactory;
import poke.server.resources.ResourceUtil;
import poke.server.routing.RoutingLog;
import poke.server.routing.ServerAsClientHandler;

import com.google.protobuf.GeneratedMessage;

import eye.Comm.Header.ReplyStatus;
import eye.Comm.Finger;
import eye.Comm.Header;
import eye.Comm.Operation;
import eye.Comm.Payload;
import eye.Comm.Request;
import eye.Comm.Response;

/**
 * A server queue exists for each connection (channel).
 * 
 * @author gash
 * 
 */
public class PerChannelQueue implements ChannelQueue {
	protected static Logger logger = LoggerFactory.getLogger("server");

	private Channel channel;
	private LinkedBlockingDeque<com.google.protobuf.GeneratedMessage> inbound;
	private LinkedBlockingDeque<com.google.protobuf.GeneratedMessage> outbound;
	private OutboundWorker oworker;
	private InboundWorker iworker;

	// not the best method to ensure uniqueness
	private ThreadGroup tgroup = new ThreadGroup("ServerQueue-"
			+ System.nanoTime());

	protected PerChannelQueue(Channel channel) {
		this.channel = channel;
		init();
	}

	protected void init() {
		inbound = new LinkedBlockingDeque<com.google.protobuf.GeneratedMessage>();
		outbound = new LinkedBlockingDeque<com.google.protobuf.GeneratedMessage>();

		iworker = new InboundWorker(tgroup, 1, this);
		iworker.setName("inboundWorkerThread::" + this.channel.getId());
		logger.info("Starting:" + iworker);
		iworker.start();

		oworker = new OutboundWorker(tgroup, 1, this);
		oworker.setName("outboundWorkerThread::" + this.channel.getId());
		logger.info("Starting:" + oworker);
		oworker.start();

		// let the handler manage the queue's shutdown
		// register listener to receive closing of channel
		// channel.getCloseFuture().addListener(new CloseListener(this));
	}

	protected Channel getChannel() {
		return channel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see poke.server.ChannelQueue#shutdown(boolean)
	 */
	@Override
	public void shutdown(boolean hard) {
		logger.info("server is shutting down");

		channel = null;

		if (hard) {
			// drain queues, don't allow graceful completion
			inbound.clear();
			outbound.clear();
		}

		if (iworker != null) {
			iworker.forever = false;
			if (iworker.getState() == State.BLOCKED
					|| iworker.getState() == State.WAITING)
				iworker.interrupt();
			iworker = null;
		}

		if (oworker != null) {
			oworker.forever = false;
			if (oworker.getState() == State.BLOCKED
					|| oworker.getState() == State.WAITING)
				oworker.interrupt();
			oworker = null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see poke.server.ChannelQueue#enqueueRequest(eye.Comm.Finger)
	 */
	@Override
	public void enqueueRequest(Request req) {
		try {
			inbound.put(req);
		} catch (InterruptedException e) {
			logger.error("message not enqueued for processing", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see poke.server.ChannelQueue#enqueueResponse(eye.Comm.Response)
	 */
	@Override
	public void enqueueReply(GeneratedMessage gm) {
		try {
			if (gm instanceof Request) {
				Request req = (Request) gm;
				logger.info("Enqueuing the msg of type REQUEST::"
						+ req.getCorrelationId()
						+ " in outbound perchannel queue");
			} else if (gm instanceof Response) {
				Response resp = (Response) gm;
				logger.info("Enqueuing the msg of type RESPONSE::"
						+ resp.getCorrelationId()
						+ " in outbound perchannel queue");
			}
			outbound.put(gm);
		} catch (InterruptedException e) {
			logger.error("message not enqueued for reply", e);
		}
	}

	@Override
	public void enqueueResponse(Response response) {
		logger.error("message response enqueued");
	}

	protected class OutboundWorker extends Thread {
		int workerId;
		PerChannelQueue sq;
		boolean forever = true;

		public OutboundWorker(ThreadGroup tgrp, int workerId, PerChannelQueue sq) {
			super(tgrp, "outbound-" + workerId);
			this.workerId = workerId;
			this.sq = sq;

			if (outbound == null)
				throw new RuntimeException(
						"connection worker detected null queue");
		}

		@Override
		public void run() {
			logger.info("outbound perchannel queue::" + sq.outbound.size());
			Channel sqChannel = sq.channel;
			if (sqChannel == null || !sqChannel.isOpen()) {
				PerChannelQueue.logger
						.error("connection missing, no outbound communication");
				return;
			}

			while (true) {
				if (!forever && sq.outbound.size() == 0)
					break;

				try {
					// block until a message is enqueued
					GeneratedMessage msg = sq.outbound.take();

					if (msg instanceof Request) {
						// Integer correlationId =
						// RoutingLog.getInstance().getCorrelationId();
						ArrayList<String> restrictedNodeIds = new ArrayList<String>();
						Request incomingReq = (Request) msg;
						logger.info("BEFORE :: RESTRICTED node IDs::"
								+ restrictedNodeIds);
						restrictedNodeIds.add(incomingReq.getHeader()
								.getOriginator());
						// if (incomingReq.getHeader().getOriginator()
						// .equalsIgnoreCase("client")) {
						for (int i = 0; i < incomingReq.getNeighborNodesCount(); i++) {
							restrictedNodeIds.add(incomingReq
									.getNeighborNodes(i));
						}
						// }
						logger.info("AFTER :: RESTRICTED node IDs::"
								+ restrictedNodeIds);
						Request reqToForward = generateReqWithNeighborNodes(incomingReq);

						logger.info("Have to forward this request!!! Lets save it in routingLog"
								+ RoutingLog.getInstance().routingLog);
						// req = (Request) msg;
						RoutingLog.getInstance().addRoutingLog(
								reqToForward.getCorrelationId(), sq);
						logger.info("routing Log::"
								+ RoutingLog.getInstance().routingLog);
						//String senderNodeId = req.getHeader().getOriginator();
						// restrictedNodeIds.add(senderNodeId);
						logger.info("The neighbors for this node:::"
								+ ManagementQueue.nodePortLog.keySet());
						for (String nodeId : ManagementQueue.nodePortLog
								.keySet()) {
							// Forward the request only if its allowed on a
							// particular neighbor
							if (!restrictedNodeIds.contains(nodeId)) {
								logger.info("node::"
										+ nodeId
										+ " is NOT restricted!!.... Checking if its alive");
								// Check the neighbor status;
								logger.info("Current heartbeatLog:::"
										+ ManagementQueue.heartBeatLog);

								if (ManagementQueue.heartBeatLog
								 .get(nodeId)) {
								logger.info("node :::" + nodeId
										+ " is alive!!!!");
								ServerAsClient sc = ServerAsClient
										.initConnection("localhost",
												ManagementQueue.nodePortLog
														.get(nodeId));
								Channel ch = sc.connect();

								if (ch.isWritable()) {
									boolean rtn = false;
									if (ch != null && ch.isOpen()
											&& ch.isWritable()) {
										logger.info("FORWARDING request to server::"
												+ nodeId);
										ChannelFuture cf = ch.write(reqToForward);
										cf.awaitUninterruptibly();
										rtn = cf.isSuccess();
										if (!rtn)
											sq.outbound.putFirst(reqToForward);
									}

								} else
									sq.outbound.putFirst(reqToForward);
								 } else {
								 logger.info("Attempted to forward the request to server::"
								 + nodeId
								 + "But it seems that the SERVER is down!");
								 }
							} else {
								logger.info("SORRY !!! No option to FORWARD the request!");
							}
						}
					} else if (msg instanceof Response) {
						if (sqChannel.isWritable()) {
							boolean rtn = false;
							if (channel != null && channel.isOpen()
									&& channel.isWritable()) {
								ChannelFuture cf = channel.write(msg);
								cf.awaitUninterruptibly();
								rtn = cf.isSuccess();
								if (!rtn)
									sq.outbound.putFirst(msg);
							}

						} else
							sq.outbound.putFirst(msg);
					}
				} catch (InterruptedException ie) {
					break;
				} catch (Exception e) {
					PerChannelQueue.logger.error(
							"Unexpected communcation failure", e);
					e.printStackTrace();
					break;
				}
			}

			if (!forever) {
				PerChannelQueue.logger.info("connection queue closing");
			}
		}
	}

	protected class InboundWorker extends Thread {
		int workerId;
		PerChannelQueue sq;
		boolean forever = true;

		public InboundWorker(ThreadGroup tgrp, int workerId, PerChannelQueue sq) {
			super(tgrp, "inbound-" + workerId);
			this.workerId = workerId;
			this.sq = sq;

			if (outbound == null)
				throw new RuntimeException(
						"connection worker detected null queue");
		}

		@Override
		public void run() {
			logger.info("inbound perchannel Queue:" + sq.inbound.size());
			Channel sqChannel = sq.channel;
			if (sqChannel == null || !sqChannel.isOpen()) {
				PerChannelQueue.logger
						.error("connection missing, no inbound communication");
				return;
			}

			while (true) {
				if (!forever && sq.inbound.size() == 0)
					break;

				try {
					// block until a message is enqueued
					GeneratedMessage msg = sq.inbound.take();

					// process request and enqueue response

					// Not required as it is always going to be a request in the
					// inbound queue
					// if (msg instanceof Request) {
					Request req = ((Request) msg);
					Resource rsc = ResourceFactory.getInstance()
							.resourceInstance(req.getHeader().getRoutingId());
					// Response reply = null;
					GeneratedMessage gm = null;
					if (rsc == null) {
						logger.error("failed to obtain resource for " + req);
						gm = ResourceUtil.buildError(req.getHeader(),
								ReplyStatus.FAILURE, "Request not processed");
					} else {
						gm = rsc.process(req);
					}
					sq.enqueueReply(gm);
					
					// }

				} catch (InterruptedException ie) {
					break;
				} catch (Exception e) {
					PerChannelQueue.logger.error(
							"Unexpected processing failure", e);
					e.printStackTrace();
					break;
				}
			}

			if (!forever) {
				PerChannelQueue.logger.info("connection queue closing");
			}
		}
	}

	public class CloseListener implements ChannelFutureListener {
		private ChannelQueue sq;

		public CloseListener(ChannelQueue sq) {
			this.sq = sq;
		}

		@Override
		public void operationComplete(ChannelFuture future) throws Exception {
			sq.shutdown(true);
		}
	}

	private Request generateReqWithNeighborNodes(Request req) {
		logger.info("Creating the new request to forward::");
		Finger.Builder f = eye.Comm.Finger.newBuilder();
		f.setTag(req.getBody().getFinger().getTag());
		f.setNumber(req.getBody().getFinger().getNumber());

		// payload containing data
		Request.Builder r = Request.newBuilder();
		eye.Comm.Payload.Builder p = Payload.newBuilder();
		Operation.Builder opn = eye.Comm.Operation.newBuilder();
		p.setOperation(opn);
		p.setFinger(f.build());
		r.setBody(p.build());

		// header with routing info
		eye.Comm.Header.Builder h = Header.newBuilder();
		h.setOriginator(Server.ownNodeID);
		h.setTag(req.getHeader().getTag());
		h.setTime(req.getHeader().getTime());
		h.setRoutingId(req.getHeader().getRoutingId());
		r.setHeader(h.build());
		// r.setCorrelationId(corrId);
		r.setCorrelationId(req.getCorrelationId());

		ArrayList<String> neighborNodes = new ArrayList<String>();
		for (String nodeId : ManagementQueue.nodeMgmtPortLog.keySet()) {
			neighborNodes.add(nodeId);
		}
		logger.info("Generating the neighbor nodes for the request!!!");
		for (int i = 0; i < neighborNodes.size(); i++) {
			logger.info("adding node::" + neighborNodes.get(i));
			r.addNeighborNodes(neighborNodes.get(i));
		}
		eye.Comm.Request reqWithNeighborNodes = r.build();
		logger.info("Originator ID in the new request::"
				+ reqWithNeighborNodes.getHeader().getOriginator());
		return reqWithNeighborNodes;
	}
}
