package net.common.dao;

public interface ServiceContants {
	
	String SELECT_STR = "SELECT ";
	String INSERT_STR = "INSERT INTO ";
	String UPDATE_STR = "UPDATE ";
	String FROM_STR = " FROM ";
	String WHERE_STR = " WHERE ";
	String SET_STR = " SET ";
	String AND_STR = " AND ";
	String EQUAL_STR = " = ";
	String VALUES_STR = ") VALUES (";
	String LEFT_BRACKET_STR = " (";
	String RIGHT_BRACKET_STR = ") ";
	String COMMA_STR = ", ";
	String STAR_STR = "*";
	String DOT_STR = ".";
	String JOIN_STR = "JOIN ";
	String ON_STR = "ON ";
	String IN_STR = "IN ";
	String DISTINCT_STR = "DISTINCT ";
	String LIKE_STR = "LIKE ";
	String GROUPBY_STR = "GROUP BY";
	

	/* Database Schema Name */

	String DATABASE_SCHEMA = "university";

	/* Customer Table */

	String UniversityTable_Table = "universityinfo";

	String UniversityTable_univId = "universityinfo.univId";
	String UniversityTable_universityName = "universityinfo.universityName";
	
	String UNIVERSITYTABLE_SELECT_ALL = "select * from university.universityinfo";
	//  ServiceContants.CustomerTable_customerId + ServiceContants.COMMA_STR + ServiceContants.CustomerTable_customerName + " "
	String UNIVERSITYTABLE_SELECT_ON_AUTOSUGGEST = ServiceContants.SELECT_STR + ServiceContants.STAR_STR + ServiceContants.FROM_STR + ServiceContants.UniversityTable_Table + ServiceContants.WHERE_STR +
														ServiceContants.UniversityTable_universityName + " " + ServiceContants.LIKE_STR + "?" + " " + ServiceContants.GROUPBY_STR + " " + ServiceContants.UniversityTable_universityName; 

//	String CUSTOMERTABLE_SELECT_ON_CUSTOMERID =  ServiceContants.SELECT_STR + ServiceContants.CustomerTable_customerId + ServiceContants.COMMA_STR  + ServiceContants.CustomerTable_customerName + ServiceContants.COMMA_STR + ServiceContants.CustomerTable_siteId + " " +
//			ServiceContants.COMMA_STR + ServiceContants.CustomerTable_shortName +  " " + ServiceContants.FROM_STR + ServiceContants.CustomerTable_Table + " " + ServiceContants.WHERE_STR + ServiceContants.CustomerTable_customerId + " " +
//			ServiceContants.EQUAL_STR + "?";
//
//	String CUSTOMERTABLE_SELECT_SITEID = "Select siteId from customer360.customertable Where customerId = ?";


}								
