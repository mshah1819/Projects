package database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.jws.WebService;

@WebService
public class createNewDatabase{
	//public void newdb(){
		public static void main (String args[]){
		databaseConnection.Connection cn = new databaseConnection.Connection();
		java.util.Date today = new java.util.Date();
		java.sql.Date sqlToday = new java.sql.Date(today.getTime());
		PreparedStatement ps = null;
		try{
				System.out.println("Connection :: ..........Successful !!");
				String schema = "CREATE DATABASE IF NOT EXISTS ";
				schema = schema.concat(constants.ConstantData.SCHEMA_NAME);
				Connection con = cn.getConnection();
				// Configuration Master Data Table
				con.createStatement().executeUpdate(schema);
				con.createStatement().executeUpdate(constants.ConstantData.TABLE_VIDEOLIBSTATUS);
				ps = con.prepareStatement(constants.ConstantData.INSERT_VIDEOLIBSTATUS);
				ps.setInt(1,1);
				ps.setString(2, constants.ConstantData.USER_ACTIVE);
				ps.setDate(3, sqlToday);
				ps.setInt(4,1);
				ps.setInt(5,2);
				ps.setString(6, constants.ConstantData.USER_INACTIVE);
				ps.setDate(7, sqlToday);
				ps.setInt(8, 1);
				ps.executeUpdate();
				
				con.createStatement().executeUpdate(constants.ConstantData.TABLE_VIDEOLIBMOVIECATEGORY);
				ps = con.prepareStatement(constants.ConstantData.INSERT_VIDEOLIBMOVIECATEGORY);
				ps.setInt(1, 1);
				ps.setString(2, constants.ConstantData.CAT_ONE);
				ps.setDate(3, sqlToday);
				ps.setInt(4, 1);
				ps.setInt(5, 2);
				ps.setString(6, constants.ConstantData.CAT_TWO);
				ps.setDate(7, sqlToday);
				ps.setInt(8, 1);
				ps.setInt(9, 3);
				ps.setString(10, constants.ConstantData.CAT_THREE);
				ps.setDate(11, sqlToday);
				ps.setInt(12, 1);
				ps.setInt(13, 4);
				ps.setString(14, constants.ConstantData.CAT_FOUR);
				ps.setDate(15, sqlToday);
				ps.setInt(16, 1);
				ps.executeUpdate();
				
				con.createStatement().executeUpdate(constants.ConstantData.TABLE_VIDEOLIBUSERTYPEMASTER);
				ps = con.prepareStatement(constants.ConstantData.INSERT_VIDEOLIBUSERTYPEMASTER);
				ps.setInt(1, 1);
				ps.setInt(2, 1);
				ps.setString(3, constants.ConstantData.USERTYPE_ADMIN);
				ps.setDate(4, sqlToday);
				ps.setInt(5, 1);
				ps.setInt(6, 2);
				ps.setInt(7, 1);
				ps.setString(8, constants.ConstantData.USERTYPE_PREMIUM);
				ps.setDate(9, sqlToday);
				ps.setInt(10, 1);
				ps.setInt(11, 3);
				ps.setInt(12, 1);
				ps.setString(13, constants.ConstantData.USERTYPE_SIMPLE);
				ps.setDate(14, sqlToday);
				ps.setInt(15, 1);
				ps.executeUpdate();
				
				con.createStatement().executeUpdate(constants.ConstantData.TABLE_VIDEOLIBCOUNTRYMASTER);
				ps = con.prepareStatement(constants.ConstantData.INSERT_VIDEOLIBCOUNTRYMASTER);
				ps.setInt(1, 1);
				ps.setString(2, constants.ConstantData.COUNTRY_NAME);
				ps.setDate(3, sqlToday);
				ps.setInt(4, 1);
				ps.executeUpdate();
				
				
				con.createStatement().executeUpdate(constants.ConstantData.TABLE_VIDEOLIBSTATEMASTER);
				ps = con.prepareStatement(constants.ConstantData.INSERT_VIDEOLIBSTATEMASTER);
				ps.setInt(1, 1);
				ps.setString(2, constants.ConstantData.STATE_NAME_ONE);
				ps.setInt(3, 1);
				ps.setDate(4, sqlToday);
				ps.setInt(5, 1);
				ps.setInt(6, 2);
				ps.setString(7, constants.ConstantData.STATE_NAME_TWO);
				ps.setInt(8, 1);
				ps.setDate(9, sqlToday);
				ps.setInt(10, 1);
				ps.executeUpdate();
				
				con.createStatement().executeUpdate(constants.ConstantData.TABLE_VIDEOLIBCITYMASTER);
				ps = con.prepareStatement(constants.ConstantData.INSERT_VIDEOLIBCITYMASTER);
				ps.setInt(1, 1);
				ps.setString(2,constants.ConstantData.CITY_NAME_ONE);
				ps.setInt(3, 1);
				ps.setDate(4, sqlToday);
				ps.setInt(5, 1);
				
				ps.setInt(6, 2);
				ps.setString(7,constants.ConstantData.CITY_NAME_TWO);
				ps.setInt(8, 1);
				ps.setDate(9, sqlToday);
				ps.setInt(10, 1);
				
				ps.setInt(11, 3);
				ps.setString(12,constants.ConstantData.CITY_NAME_THREE);
				ps.setInt(13, 2);
				ps.setDate(14, sqlToday);
				ps.setInt(15, 1);
				
				ps.setInt(16, 4);
				ps.setString(17,constants.ConstantData.CITY_NAME_FOUR);
				ps.setInt(18, 2);
				ps.setDate(19, sqlToday);
				ps.setInt(20, 1);
				ps.executeUpdate();
				
				
				
				// Master Data Table (Users)
				con.createStatement().executeUpdate(constants.ConstantData.TABLE_VIDEOLIBUSERADDRESSMASTER);
				
				ps = con.prepareStatement(constants.ConstantData.INSERT_VIDEOLIBUSERADDRESSMASTER);
				ps.setInt(1, 1);
				ps.setString(2, constants.ConstantData.ADDONE);
				ps.setString(3, constants.ConstantData.ADDTWO);
				ps.setInt(4, 1);
				ps.setInt(5, 1);
				ps.setInt(6, 1);
				ps.setString(7, constants.ConstantData.ZIP);
				ps.setDate(8,sqlToday);
				//ps.setInt(9, 1);
				//ps.executeUpdate();
				
				
				con.createStatement().executeUpdate(constants.ConstantData.TABLE_VIDEOLIBUSERS);
				ps = con.prepareStatement(constants.ConstantData.INSERT_VIDEOLIBUSERS);
				ps.setInt(1, 1);
				ps.setString(2,constants.ConstantData.FIRSTNAME);
				ps.setString(3,constants.ConstantData.LASTNAME);
				ps.setString(4,constants.ConstantData.DISPLAY);
				ps.setString(5,constants.ConstantData.DOB);
				ps.setString(6,constants.ConstantData.SEX);
				ps.setInt(7, 1);
				ps.setString(8, constants.ConstantData.LOGINID);
				ps.setString(9, constants.ConstantData.PWD);
				ps.setDate(10,sqlToday);
				ps.setInt(11, 1);
				ps.setInt(12, 1);
				ps.setString(13, constants.ConstantData.EMAIL);
				ps.setInt(14, 0);
				ps.setDate(15,sqlToday);
				ps.setString(16, constants.ConstantData.SSN);
				ps.setFloat(17, constants.ConstantData.SUBSCRIPTION_FEE);
				ps.setDate(18,sqlToday);
				//ps.setDate(19,sqlToday);
				//ps.setInt(20, 1);
				//ps.executeUpdate();
				
				
				// Master Data Table (Movies)
				con.createStatement().executeUpdate(constants.ConstantData.TABLE_VIDEOLIBMOVIESMASTER);
				ps = con.prepareStatement(constants.ConstantData.INSERT_VIDEOLIBMOVIESMASTER);
				ps.setInt(1, 1);
				ps.setString(2, constants.ConstantData.MOVIE_NAME);
				ps.setString(3, constants.ConstantData.MOVIE_PROD);
				ps.setInt(4, 1);
				ps.setDate(5, sqlToday);
				ps.setFloat(6, 0.99f);
				ps.setInt(7, 10);
				ps.setInt(8, 10);
				ps.setDate(9,sqlToday);
				ps.setInt(10, 1);
				ps.executeUpdate();
				
				// Transactional Data Table (Movie-User Mapping)
				con.createStatement().executeUpdate(constants.ConstantData.TABLE_VIDEOLIBMOVIECART);
				con.createStatement().executeUpdate(constants.ConstantData.TABLE_VIDEOLIBPREMIUMUSERTRANSACTION);
				con.createStatement().executeUpdate(constants.ConstantData.TABLE_VIDEOLIBSIMPLEUSERTRANSACTION);
				
				// Payment Gateway
				con.createStatement().executeUpdate(constants.ConstantData.TABLE_VIDEOLIBPAYMENT);
				ps = con.prepareStatement(constants.ConstantData.INSERT_VIDEOLIBPAYMENT);
				ps.setInt(1, 1);
				ps.setString(2,constants.ConstantData.CARD_NAME);
				ps.setString(3, constants.ConstantData.CARD_NUMONE);
				ps.setString(4, constants.ConstantData.CARD_NUMTWO);
				ps.setString(5, constants.ConstantData.CARD_NUMTHREE);
				ps.setString(6, constants.ConstantData.CARD_NUMFOUR);
				ps.setString(7, constants.ConstantData.CARD_TYPE);
				ps.setString(8, constants.ConstantData.CARD_CVV);
				ps.setString(9,constants.ConstantData.CARD_EXPIRY);
				ps.setDate(10, sqlToday);
				ps.setInt(11, 1);
				ps.executeUpdate();
				
			}catch(Exception e){
				e.printStackTrace();
			 }finally{
				boolean state = cn.getDisconnection();
				if(!state){
					System.out.println("Connection close :: ..........Unsuccessful !!");
				}else System.out.println("Connection close :: ..........Successful !!");
			 }
	}
}