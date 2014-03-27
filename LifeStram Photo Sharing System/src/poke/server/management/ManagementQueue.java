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
package poke.server.management;

import java.net.SocketAddress;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;

import org.jboss.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import poke.server.Server;

import eye.Comm.Management;

/**
 * The management queue exists as an instance per process (node)
 * 
 * @author gash
 * 
 */
public class ManagementQueue {
	protected static Logger logger = LoggerFactory.getLogger("management");

	protected static LinkedBlockingDeque<ManagementQueueEntry> inbound = new LinkedBlockingDeque<ManagementQueueEntry>();
	protected static LinkedBlockingDeque<ManagementQueueEntry> outbound = new LinkedBlockingDeque<ManagementQueueEntry>();
	
	
	// Map to maintain the heartbeats of Neighboring servers
	public static Map<String, Boolean> heartBeatLog = Collections
			.synchronizedMap(new HashMap<String, Boolean>());
	
	// Map to maintain the node and corresponding ports
	public static HashMap<String, Integer> nodeMgmtPortLog = new HashMap<String, Integer>();
	
	// Map to maintain the node and corresponding management ports
	public static HashMap<String, Integer> nodePortLog = new HashMap<String, Integer>();
	
	// TODO static is problematic
	private static OutboundMgmtWorker oworker;
	private static InboundMgmtWorker iworker;

	// not the best method to ensure uniqueness
	private static ThreadGroup tgroup = new ThreadGroup("ManagementQueue-"
			+ System.nanoTime());

	public static void startup() {
		if (iworker != null)
			return;

		iworker = new InboundMgmtWorker(tgroup, 1);
		iworker.setName("iworkerThread" + iworker.workerId); // Added by Krunal
		iworker.start();
		oworker = new OutboundMgmtWorker(tgroup, 1);
		oworker.setName("oworkerThread" + oworker.workerId); // Added by Krunal
		oworker.start();
	}

	public static void shutdown(boolean hard) {
		// TODO shutdon workers
	}

	public static void enqueueRequest(Management req, Channel ch,
			SocketAddress sa) {
		try {
			ManagementQueueEntry entry = new ManagementQueueEntry(req, ch, sa);
			inbound.put(entry);
			logger.info("##### SIZE OF THE management inbound queue::"
					+ inbound.size());
		} catch (InterruptedException e) {
			logger.error("message not enqueued for processing", e);
		}
	}

	public static void enqueueResponse(Management reply, Channel ch) {
		try {
			ManagementQueueEntry entry = new ManagementQueueEntry(reply, ch,
					null);
			outbound.put(entry);
		} catch (InterruptedException e) {
			logger.error("message not enqueued for reply", e);
		}
	}

	public static class ManagementQueueEntry {
		public ManagementQueueEntry(Management req, Channel ch, SocketAddress sa) {
			this.req = req;
			this.channel = ch;
			this.sa = sa;
		}

		public Management req;
		public Channel channel;
		SocketAddress sa;
	}
}