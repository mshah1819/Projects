package databaseConnection;

import java.sql.*;

import javax.jws.WebService;
@WebService
public class Connection {
	private java.sql.Connection conn = null;
	public java.sql.Connection getConnection(){
		try 
		{			
			Class.forName(constants.ConstantData.CONNECTION_DRIVER).newInstance();
			conn = DriverManager.getConnection(constants.ConstantData.CONNECTION_URL,constants.ConstantData.HOST_NAME,constants.ConstantData.USER_PASSWORD);
		}
		catch (SQLException e){
			e.printStackTrace();
		} 
		catch (InstantiationException e){
			e.printStackTrace();
		} 
		catch (IllegalAccessException e){
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e){
			e.printStackTrace();
		}
		 return conn;
	}
	
	public boolean getDisconnection(){
		boolean state = false;
		try {			
			conn.close();
			state = true;
		}
		catch (SQLException e){
			e.printStackTrace();
		}  return state;
	}
}