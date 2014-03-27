package databaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

public class ConnectionPoolManager_TEST implements Runnable{
	
	public static String dataBaseUrl = constants.ConstantData.CONNECTION_URL+"/"+constants.ConstantData.SCHEMA_NAME;
	public static String userName = constants.ConstantData.HOST_NAME;
	public static String password = constants.ConstantData.USER_PASSWORD;
	
	public static String getDataBaseUrl() {
		return dataBaseUrl;
	}

	public static void setDataBaseUrl(String dataBaseUrl) {
		ConnectionPoolManager_TEST.dataBaseUrl = dataBaseUrl;
	}

	public static String getUserName() {
		return userName;
	}

	public static void setUserName(String userName) {
		ConnectionPoolManager_TEST.userName = userName;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		ConnectionPoolManager_TEST.password = password;
	}

	public Vector<Connection> getConnectionPool() {
		return connectionPool;
	}

	public void setConnectionPool(Vector<Connection> connectionPool) {
		this.connectionPool = connectionPool;
	}


	static int MAX_NUMBER_CONNECTIONS = 10;
	
	Vector<Connection> connectionPool  = new Vector<Connection>();
	
	
	
	
	
	
	/*this one is the constructor for initilaizing the variables*/
	public ConnectionPoolManager_TEST(String url, String userName, String passwd)
	{
		ConnectionPoolManager_TEST.dataBaseUrl = url;
		ConnectionPoolManager_TEST.userName = userName;
		ConnectionPoolManager_TEST.password = passwd;
		initializeConnectionPool();
	}
	
	public ConnectionPoolManager_TEST()
	{
		ConnectionPoolManager_TEST.setDataBaseUrl(dataBaseUrl);
		ConnectionPoolManager_TEST.setPassword(password);
		ConnectionPoolManager_TEST.setUserName(userName);
		initializeConnectionPool();
		
	}
	
	public synchronized boolean checkForpoolIsFull()
	{
		if(connectionPool.size()<10)
			return false;
		else
			return true;
	}
	
	public synchronized void returnConnectionToPool(Connection connection)
	{
		if(!checkForpoolIsFull())
		{				
			connectionPool.addElement(connection);
			System.out.println("connection pool size  = "+connectionPool.size());
		}
		else
		{
			try{
				connection.close();
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
	} /*end of method for return connection pool*/
	
	
	public synchronized Connection createNewConnectionToPool() 
	{
		Connection connection = null;
		
		/*we are checking if there are active connections in the vector
		 * if so we are removing that and adding */
		if(!checkForpoolIsFull())
		{	
			try 
			{
				Class.forName(constants.ConstantData.CONNECTION_DRIVER);
				connection = DriverManager.getConnection(getDataBaseUrl(), getUserName(), getPassword());
				
			} 
			catch (ClassNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		} /*end of if condition to check whether connection is full*/
		return connection;
	} /*end of method for create new connection to pool*/
	
	
	
	
	
	
	public void initializeConnectionPool()
	{
		
		while(!checkForpoolIsFull())
		{
			
			connectionPool.addElement(createNewConnectionToPool());
			System.out.println("adding connections now pool size="+connectionPool.size());
		}
		System.out.println("adding is done and connection pool is full!!!!");
	} /*end of method for initializeConnectionPool*/
	
	public synchronized Connection getConnectionFromPool()
	{
		Connection connection = null;
		
		/*the below condition implies that there are connections available in the pool are full and we need to remove one connection*/
		if(connectionPool.size() > 0 )//&& connectionPool.size() == MAX_NUMBER_CONNECTIONS)
		{
			System.out.println("insdie if connections are there so size="+connectionPool.size());
			connection = connectionPool.firstElement();
			connectionPool.remove(0);
			System.out.println("insdie if connections are equal to 10 so size after adding="+connectionPool.size());
		}
		
		/*the below condition implies that there are connections but n0t full*/
		/*else if (connectionPool.size() > 0 && connectionPool.size() < MAX_NUMBER_CONNECTIONS)
		{
			System.out.println("insdie else if connections are less than 10 so adding="+connectionPool.size());
			connectionPool.addElement(createNewConnectionToPool());
			connection = connectionPool.lastElement();
			System.out.println("insdie else if connections are less than 10 so after adding="+connectionPool.size());
		}*/
		
		/* the below condition implies that the connections are empty in the vector*/
		else
		{
			try 
			{
				System.out.println("insdie else connections in pool are  over so adding new connection ="+connectionPool.size());
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection(getDataBaseUrl(), getUserName(), getPassword());
				//connectionPool.addElement(createNewConnectionToPool());
				System.out.println("insdie else after adding connection size="+connectionPool.size());
			} 
			catch (ClassNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} /*end of else*/
		
		return connection;
	}  /*end of method for getConnectionFromPool*/
	
	public void run()
	{
			ConnectionPoolManager_TEST conPoolmanager = new ConnectionPoolManager_TEST();
			Connection[] connArray = new Connection[7];
			for(int i = 0 ; i <5; i++)
			{
				 connArray[i] = conPoolmanager.getConnectionFromPool();
			}
			for(int i = 0 ; i<4; i++)
			{
				conPoolmanager.returnConnectionToPool(connArray[i]);
			}
		
	}
}
