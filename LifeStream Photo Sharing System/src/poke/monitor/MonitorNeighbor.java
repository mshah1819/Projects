package poke.monitor;

public class MonitorNeighbor extends Thread{
	HeartMonitor hm;
	public MonitorNeighbor(int cport, String ownNodeID){
		System.out.println("Creating a heart monitor for port ::"+cport);
		
		hm = new HeartMonitor("localhost",cport, ownNodeID);
		
		System.out.println("Calling waitforever for port::"+cport);
	}
	@Override
	public void run() {
		hm.waitForever();
	}
	
}
