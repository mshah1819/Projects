package poke.server;

public class Neighbor {
	protected String nodeID;
	protected int port;
	protected int portMgmt;
	public String getNodeID() {
		return nodeID;
	}
	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public int getPortMgmt() {
		return portMgmt;
	}
	public void setPortMgmt(int portMgmt) {
		this.portMgmt = portMgmt;
	}
	
	
}
