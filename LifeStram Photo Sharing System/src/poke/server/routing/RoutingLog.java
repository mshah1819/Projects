package poke.server.routing;

import java.util.HashMap;

import poke.server.queue.PerChannelQueue;

public class RoutingLog {
	
	private static RoutingLog routingLogInstance;
	private static Integer correlationId = 0;
	public static HashMap<Integer, PerChannelQueue> routingLog = new HashMap<Integer, PerChannelQueue>();	
	private RoutingLog(){
		
	}
	
	public static synchronized RoutingLog getInstance(){
		//System.out.println("Valus of instance::"+heartBeatLogInstance);
		if(null == routingLogInstance){
			//System.out.println("Creating a new instance of the HeartBeatLog");
			routingLogInstance = new RoutingLog();
		}
		//System.out.println("Returning instance with hashcode::"+heartBeatLogInstance.hashCode());
		return routingLogInstance;
	}
	
	public void addRoutingLog(Integer correlationId, PerChannelQueue sq) {
		routingLog.put(correlationId, sq);
	}
	
	public static Integer getCorrelationId() {
		correlationId++;
		return correlationId;
	}

}
