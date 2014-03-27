package databaseConnection;

import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionPool implements Runnable {
	static private ConnectionPool poolinstance = null;
	int mincon = 10;
	int maxcon = 30;
	Connection newcon = null;
	boolean creatingnewcon = false;
	static List<Connection> freecon = new ArrayList<Connection>();
	static List<Connection> busycon = new ArrayList<Connection>();

	private ConnectionPool() {
		System.out.println("creating new pool......");
		while (freecon.size() < mincon) {
			if ((newcon = createNewCon()) != null) {
				try {
					freecon.add(newcon);
				} catch (Exception e) {
					System.out.println("Error in creating new connection....");
				}
			}
		}
	}

	static public ConnectionPool getInstance() {
		if (poolinstance == null) {
			poolinstance = new ConnectionPool();
		}
		return poolinstance;
	}

	public synchronized Connection getCon() {
		if (!freecon.isEmpty()) {
			Connection con = freecon.remove(0);
			busycon.add(con);
			return con;
		} else {
			if ((freecon.size() + busycon.size()) < maxcon && !creatingnewcon) {
				try {
					createAdditionalCon();
				} catch (Exception e) {
					System.out
							.println("Error in creating additional connection....");
				}
			} else {
				try {
					throw new Exception();
				} catch (Exception e) {
					System.out.println("Max. connection limit reached....");
				}
			}
			try {
				wait();
			} catch (Exception e) {
			}

			return getCon();
		}
	}

	private Connection createNewCon() {
		try {
			Class.forName(constants.ConstantData.CONNECTION_DRIVER);
			return (Connection) DriverManager.getConnection(
					constants.ConstantData.CONNECTION_URL
							+ constants.ConstantData.SCHEMA_NAME,
					constants.ConstantData.HOST_NAME,
					constants.ConstantData.USER_PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private void createAdditionalCon() {
		creatingnewcon = true;
		new Thread(this).start();
	}

	public synchronized void freeCon(Connection con) {
		if (!busycon.isEmpty()) {
			busycon.remove(con);
			freecon.add(con);
		}
		System.out.println("Size of free pool after freecon called::"+ConnectionPool.freecon.size());
		System.out.println("Size of busy pool after freecon called::"+ConnectionPool.busycon.size());
		
		notifyAll();
	}

	public void run() {
		synchronized (this) {
			Connection con = createNewCon();
			if (con != null) {
				freecon.add(con);
				creatingnewcon = false;
				notifyAll();
			}
		}
	}

	public static synchronized void closeAllCon() {
		try {
			for (Connection conn : freecon)
				if (!conn.isClosed())
					conn.close();
			for (Connection conn : busycon)
				if (!conn.isClosed())
					conn.close();
			freecon.clear();
			busycon.clear();
			poolinstance = null;
			System.out.println("All connections closed...");
		} catch (Exception e) {
			System.out.println("Error closing connections..." + e);
		}
	}

}
