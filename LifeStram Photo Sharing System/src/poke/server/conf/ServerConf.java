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
package poke.server.conf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Multiset.Entry;

/**
 * Routing information for the server - internal use only
 * 
 * TODO refactor StorageEntry to be neutral for cache, file, and db
 * 
 * @author gash
 * 
 */
@XmlRootElement(name = "conf")
@XmlAccessorType(XmlAccessType.FIELD)
public class ServerConf {
	private GeneralConf server;
	private NeighbourConf connect;
	private List<NeighbourConf> serverconnect;
	private List<ResourceConf> routing;

	private volatile HashMap<Integer, ResourceConf> idToRsc;

	// private List<NeighbourConf> connect;

	private volatile HashMap<Integer, NeighbourConf> idToRsc1;

	private HashMap<Integer, ResourceConf> asMap() {
		// System.out.println("Inside asMap function in ServerConf.java");
		if (idToRsc != null)
			return idToRsc;

		if (idToRsc == null) {
			synchronized (this) {
				if (idToRsc == null) {
					idToRsc = new HashMap<Integer, ResourceConf>();
					if (routing != null) {
						for (ResourceConf entry : routing) {
							idToRsc.put(entry.id, entry);
						}
					}
				}
			}
		}

		return idToRsc;
	}

	private HashMap<Integer, NeighbourConf> asConnectedNode() {
		// System.out.println("Inside asMap1 function in ServerConf.java");
		if (idToRsc1 != null)
			return idToRsc1;

		if (idToRsc1 == null) {
			synchronized (this) {
				if (idToRsc1 == null) {
					idToRsc1 = new HashMap<Integer, NeighbourConf>();
					if (serverconnect != null) {
						for (NeighbourConf con : serverconnect) {
							idToRsc1.put(
									Integer.valueOf(con.getProperty("cid")),
									con);
						}
					}
				}
			}
		}

		return idToRsc1;
	}

	public void addGeneral(String name, String value) {
		if (server == null)
			server = new GeneralConf();

		server.add(name, value);
	}

	public GeneralConf getServer() {
		// System.out.println("Server val = " + server);
		return server;
	}

	public void setServer(GeneralConf server) {
		this.server = server;
	}

	public void addServerconnect(NeighbourConf obj) {
		if (obj == null)
			return;
		else if (serverconnect == null)
			serverconnect = new ArrayList<NeighbourConf>();
		serverconnect.add(obj);
	}

	public int findServerByPort(String port) {
		HashMap<Integer, NeighbourConf> temp = asConnectedNode();
		Iterator i = asConnectedNode().keySet().iterator();
		// System.out.println(" vv : " + temp.keySet());
		while (i.hasNext()) {
			Integer key = (Integer) i.next();
			// System.out.println(" iter i : " + key + " " + port);
			// System.out.println(" vall : " +
			// temp.get(key).getProperty("cportmgmt"));
			if (Integer.parseInt(temp.get(key).getProperty("cportmgmt")) == Integer
					.parseInt(port)) {
				System.out.println("Key:" + key);
				return key;
			}
		}
		return -1;
	}

	public NeighbourConf findById(String id) {
		// System.out.println("   " + asConnectedNode().keySet());
		// System.out.println(" .. " + asConnectedNode().values());
		// System.out.println(" xx " + asConnectedNode().keySet().iterator());
		Iterator i = asConnectedNode().keySet().iterator();
		while (i.hasNext()) {
			Integer key = (Integer) i.next();
			System.out.println("val == " + key);
			// NeighbourConf s = serverconnect.get(key);
			// if(s.getProperty("cport")== id)
			{
				// System.out.println("if loop - " +
				// s.getProperty("cportmgmt"));
			}
		}

		// String xx = s.getProperty(id);

		// System.out.println("Port number is : " + xx);
		return asConnectedNode().get(id);
	}

	public HashMap<Integer, NeighbourConf> retKey() {
		return asConnectedNode();
	}

	public List<NeighbourConf> getServerconnect() {
		/*
		 * Iterator iter = serverconnect.iterator(); while(iter.hasNext()) {
		 * NeighbourConf n = (NeighbourConf)iter.next();
		 * 
		 * System.out.println(" svr ::: " +
		 * Integer.parseInt(n.getProperty("cport"))); }
		 */
		return serverconnect;
	}

	public void setServerconnect(List<NeighbourConf> connect) {
		this.serverconnect = connect;
	}

	public void addResource(ResourceConf entry) {
		if (entry == null)
			return;
		else if (routing == null)
			routing = new ArrayList<ResourceConf>();

		routing.add(entry);
	}

	public ResourceConf findById(int id) {
		return asMap().get(id);
	}

	public List<ResourceConf> getRouting() {
		return routing;
	}

	public void setRouting(List<ResourceConf> conf) {
		this.routing = conf;
	}

	/**
	 * storage setup and configuration
	 * 
	 * @author gash1
	 * 
	 */
	@XmlRootElement(name = "general")
	@XmlAccessorType(XmlAccessType.FIELD)
	public static final class GeneralConf {
		private TreeMap<String, String> general;

		public String getProperty(String name) {
			// System.out.println("String(name) value in getProperty function is : "
			// + name + " & ret val = " + general.get(name)); //key = "port" and
			// value = port number
			return general.get(name);
		}

		public void add(String name, String value) {
			if (name == null)
				return;
			else if (general == null)
				general = new TreeMap<String, String>();

			general.put(name, value);
		}

		public TreeMap<String, String> getGeneral() {
			return general;
		}

		public void setGeneral(TreeMap<String, String> general) {
			this.general = general;
		}
	}

	@XmlRootElement(name = "entry")
	@XmlAccessorType(XmlAccessType.FIELD)
	public static final class ResourceConf {
		// System.out.println("Called ResourceConf class in ServerConf file..");
		private int id;
		private String name;
		private String clazz;
		private boolean enabled;

		public ResourceConf() {
		}

		public ResourceConf(int id, String name, String clazz) {
			// System.out.println("Constructor of ResourceConf .. " + id + " " +
			// name + " " + clazz);
			this.id = id;
			this.name = name;
			this.clazz = clazz;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getClazz() {
			return clazz;
		}

		public void setClazz(String clazz) {
			this.clazz = clazz;
		}

		public boolean isEnabled() {
			return enabled;
		}

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}
	}

	/**
	 * command (request) delegation
	 * 
	 * @author gash1
	 * 
	 */
	@XmlRootElement(name = "serverconnect")
	@XmlAccessorType(XmlAccessType.FIELD)
	public static final class NeighbourConf {
		private TreeMap<String, String> connect;

		public String getProperty(String name) {
			// System.out.println("String(name) value in getProperty function is : "
			// + name + " & ret val = " + connect.get(name)); //key = "port" and
			// value = port number
			return connect.get(name);
		}

		public void add(String name, String value) {
			if (name == null)
				return;
			else if (connect == null)
				connect = new TreeMap<String, String>();

			connect.put(name, value);
		}

		public TreeMap<String, String> getConnect() {
			return connect;
		}

		public void setConnect(TreeMap<String, String> connect) {
			this.connect = connect;
		}
	}

}
