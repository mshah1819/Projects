package poke.monitor;

import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import poke.server.management.ManagementQueue;

public class ReconnectNeighbor extends Thread {
	protected static Logger logger = LoggerFactory
			.getLogger("reconnectNeighbor");
	HeartMonitor hm;
	String ownNodeId;

	public ReconnectNeighbor(String ownNodeId) {
		this.ownNodeId = ownNodeId;
	}

	@Override
	public void run() {
		while (true) {
			logger.info("Checking the status of neighbors.....");
			Map<String, Boolean> heartbeatLog = ManagementQueue.heartBeatLog;
			Iterator it = heartbeatLog.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pairs = (Map.Entry) it.next();
				// System.out.println(pairs.getKey() + " = " +
				// pairs.getValue());
				if (!(Boolean) pairs.getValue()) {
					int portToConnectTo = ManagementQueue.nodeMgmtPortLog
							.get((String) pairs.getKey());

					MonitorNeighbor monitorNeighbor = new MonitorNeighbor(
							portToConnectTo, ownNodeId);
					monitorNeighbor.start();
				}
				it.remove(); // avoids a ConcurrentModificationException
			}
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
