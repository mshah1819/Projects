package constants;

import javax.jws.WebService;

@WebService
public class ConstantData {
	public static String SCHEMA_NAME = "videolibrary";
	public static String USER_ACTIVE = "Active";
	public static int ACTIVE_STATUS_ID = 1;
	public static int INACTIVE_STATUS_ID = 0;
	public static String USER_INACTIVE = "Inactive";
	public static String USERTYPE_ADMIN = "Administrator";
	public static String USERTYPE_PREMIUM = "Premium Member";
	public static String USERTYPE_SIMPLE = "Simple User";
	public static String HOST_NAME = "root";
	public static String USER_PASSWORD = "mysql";
	public static String CONNECTION_DRIVER = "com.mysql.jdbc.Driver";
	public static String CONNECTION_URL = "jdbc:mysql://localhost:3306/";
	public static String COUNTRY_NAME = "United States";
	public static String STATE_NAME_ONE = "California";
	public static String STATE_NAME_TWO = "New York";
	public static String CITY_NAME_ONE = "San Jose";
	public static String CITY_NAME_TWO = "San Francisco";
	public static String CITY_NAME_THREE = "Buffalo";
	public static String CITY_NAME_FOUR = "Kingston";
	public static String SSN = "123-456-7890";
	public static String ADDONE = "439 South 4th Street";
	public static String ADDTWO = "Apt. # 28, Metro Station Apartment";
	public static String ZIP = "95112";
	public static String FIRSTNAME = "Prakash";
	public static String LASTNAME = "Manwani";
	public static String DISPLAY = "Prakash Manwani";
	public static String DOB = "03/10/1987";
	public static String SEX = "Male";
	public static String LOGINID = "prakashmanwani";
	public static String PWD = "1qaz@WSX";
	public static String EMAIL = "manwaniprakash@yahoo.co.in";
	public static int SUBSCRIPTION_FEE = 50;
	public static String MOVIE_NAME = "SKYFALL";
	public static String MOVIE_PROD = "MGM";
	public static String CAT_ONE = "Thriller";
	public static String CAT_TWO = "Action";
	public static String CAT_THREE = "Romantic";
	public static String CAT_FOUR = "Horror";
	public static String CARD_NAME = "Prakash Manwani";
	public static String CARD_NUMONE = "1111";
	public static String CARD_NUMTWO = "2222";
	public static String CARD_NUMTHREE = "3333";
	public static String CARD_NUMFOUR = "4444";
	public static String CARD_CVV = "555";
	public static String CARD_EXPIRY = "13/15";
	public static String CARD_TYPE = "VISA";
	public static String SUCCESS = "SUCCESS";
	public static String TABLE_MOVIE = "videolibmoviesmaster";
	public static String TABLE_MOVIECART = "VideoLibMovieCart";
	
	
	/**
	 * Status
	 */
	public static String TABLE_VIDEOLIBSTATUS = "create table if not exists "
			+ SCHEMA_NAME + ".VideoLibStatus("
			+ "vlstatus_id int not null auto_increment,"
			+ "vlstatus_name varchar(20) not null,"
			+ "vlstatus_createddate datetime not null,"
			+ "vlstatus_lastupdateddate datetime,"
			+ "vlstatus_rowstate int not null," + "primary key(vlstatus_id)"
			+ ")";
	
	public static String INSERT_VIDEOLIBSTATUS = "insert into " + SCHEMA_NAME
			+ ".VideoLibStatus values(?,?,?,null,?),(?,?,?,null,?)";
	
	/**
	 * UserType
	 */
	public static String TABLE_VIDEOLIBUSERTYPEMASTER = "create table if not exists "
			+ SCHEMA_NAME
			+ ".VideoLibUserTypeMaster("
			+ "vlusrtype_id int not null auto_increment,"
			+ "vlusrtype_statusid int not null,"
			+ "vlusrtype_name varchar(20) not null,"
			+ "vlusrtype_createddate datetime not null,"
			+ "vlusrtype_lastupdateddate datetime,"
			+ "vlusrtype_rowstate int not null,"
			+ "foreign key(vlusrtype_statusid) references VideoLibStatus(vlstatus_id),"
			+ "primary key(vlusrtype_id)" + ")";
	
	public static String INSERT_VIDEOLIBUSERTYPEMASTER = "insert into "
			+ SCHEMA_NAME
			+ ".VideoLibUserTypeMaster values(?,?,?,?,null,?),(?,?,?,?,null,?),(?,?,?,?,null,?)";
	
	/**
	 * Country
	 */
	public static String TABLE_VIDEOLIBCOUNTRYMASTER = "create table if not exists "
			+ SCHEMA_NAME
			+ ".VideoLibCountryMaster("
			+ "vlc_countryid int not null auto_increment,"
			+ "vlc_countryname varchar(126) not null,"
			+ "vlc_createddate datetime not null,"
			+ "vlc_lastupdateddate datetime,"
			+ "vlc_rowstate int not null,"
			+ "primary key(vlc_countryid)" + ")";
	
	public static String INSERT_VIDEOLIBCOUNTRYMASTER = "insert into "
			+ SCHEMA_NAME + ".VideoLibCountryMaster (vlc_countryname , vlc_createddate , vlc_rowstate) values(?,?,?)";
	
	/**
	 * State
	 */
	public static String TABLE_VIDEOLIBSTATEMASTER = "create table if not exists "
			+ SCHEMA_NAME
			+ ".VideoLibStateMaster("
			+ "vls_stateid int not null auto_increment,"
			+ "vls_statename varchar(126) not null,"
			+ "vls_countryid int not null,"
			+ "vls_createddate datetime not null,"
			+ "vls_lastupdateddate datetime,"
			+ "vls_rowstate int not null,"
			+ "index(vls_countryid),"
			+ "foreign key(vls_countryid) references VideoLibCountryMaster(vlc_countryid),"
			+ "primary key(vls_stateid)" + ")";
	public static String INSERT_VIDEOLIBSTATEMASTER = "insert into "
			+ SCHEMA_NAME
			+ ".VideoLibStateMaster (vls_statename , vls_countryid , vls_createddate , vls_rowstate) values(?,?,?,?)";
	
	/**
	 * City
	 */
	public static String TABLE_VIDEOLIBCITYMASTER = "create table if not exists "
			+ SCHEMA_NAME
			+ ".VideoLibCityMaster("
			+ "vlcm_cityid int not null auto_increment,"
			+ "vlcm_cityname varchar(126) not null,"
			+ "vlcm_stateid int not null,"
			+ "vlcm_createddate datetime not null,"
			+ "vlcm_lastupdateddate datetime,"
			+ "vlcm_rowstate int not null,"
			+ "index(vlcm_stateid),"
			+ "foreign key(vlcm_stateid) references VideoLibStateMaster(vls_stateid),"
			+ "primary key(vlcm_cityid)" + ")";
	
	public static String INSERT_VIDEOLIBCITYMASTER = "insert into "
			+ SCHEMA_NAME
			+ ".VideoLibCityMaster (vlcm_cityname , vlcm_stateid , vlcm_createddate , vlcm_rowstate) values(?,?,?,?)";
	
	/**
	 * Address
	 */
	public static String TABLE_VIDEOLIBUSERADDRESSMASTER = "create table if not exists "
			+ SCHEMA_NAME
			+ ".VideoLibUserAddressMaster("
			+ "vlusraddress_id int not null auto_increment,"
			+ "vlusraddress_addressline1 varchar(126) not null,"
			+ "vlusraddress_addressline2 varchar(126),"
			+ "vlusraddress_addressline3 varchar(126),"
			+ "vlusraddress_telephonenumber varchar(20),"
			+ "vlusraddress_cityid int not null,"
			+ "vlusraddress_stateid int not null,"
			+ "vlusraddress_countryid int not null,"
			+ "vlusraddress_zipcode varchar(5) not null,"
			+ "vlusraddress_createddate datetime not null,"
			+ "vlusraddress_lastupdateddate datetime,"
			+ "vlusraddress_rowsate int not null,"
			+ "index(vlusraddress_countryid),"
			+ "index(vlusraddress_stateid),"
			+ "index(vlusraddress_cityid),"
			+ "index(vlusraddress_zipcode),"
			+ "foreign key(vlusraddress_cityid) references VideoLibCityMaster(vlcm_cityid),"
			+ "foreign key(vlusraddress_stateid) references VideoLibStateMaster(vls_stateid),"
			+ "foreign key(vlusraddress_countryid) references VideoLibCountryMaster(vlc_countryid),"
			+ "primary key(vlusraddress_id)" + ")";
	
	public static String INSERT_VIDEOLIBUSERADDRESSMASTER = "insert into "
			+ SCHEMA_NAME +
			".VideoLibUserAddressMaster (vlusraddress_addressline1 , vlusraddress_addressline2 ," +
			"vlusraddress_addressline3 , vlusraddress_telephonenumber ," +
			"vlusraddress_cityid , vlusraddress_stateid , vlusraddress_countryid ," +
			"vlusraddress_zipcode , vlusraddress_createddate , vlusraddress_rowsate) " +
			"values(?,?,?,?,?,?,?,?,?,?)";
	
	/**
	 * User
	 */
	public static String TABLE_VIDEOLIBUSERS = "create table if not exists "
			+ SCHEMA_NAME
			+ ".VideoLibUsers("
			+ "vlusr_id int not null auto_increment,"
			+ "vlusr_salutation varchar(10),"
			+ "vlusr_firstname varchar(126) not null,"
			+ "vlusr_middlename varchar(126),"
			+ "vlusr_lastname varchar(126) not null,"
			+ "vlusr_displayname varchar(126) not null,"
			+ "vlusr_dob varchar(10) not null,"
			+ "vlusr_sex varchar(5) not null,"
			+ "vlusr_addressid int not null,"
			+ "vlusr_loginid varchar(254) not null,"
			+ "vlusr_password varchar(254) not null,"
			+ "vlusr_lastpwdupdate datetime not null,"
			+ "vlusr_statusid int not null,"
			+ "vlusr_prefernce int not null,"
			+ "vlusr_emailid varchar(254) not null,"
			+ "vlusr_invalidlogins int not null default 0,"
			+ "vlusr_lastlogin datetime,"
			+ "vlusr_ssnid varchar(12),"
			+ "vlusr_subscriptionfee decimal(10,2) default 0.00,"
			+ "vlusr_subscriptionrenewal datetime,"
			+ "vlusr_createddate datetime not null,"
			+ "vlusr_lastdetailsupdate datetime,"
			+ "vlusr_rowstate int not null,"
			+ "index (vlusr_prefernce),"
			+ "foreign key (vlusr_prefernce) references VideoLibUserTypeMaster(vlusrtype_id),"
			+ "foreign key (vlusr_addressid) references VideoLibUserAddressMaster(vlusraddress_id),"
			+ "foreign key(vlusr_statusid) references VideoLibStatus(vlstatus_id),"
			+ "primary key(vlusr_id)" + ")";
	
	public static String INSERT_VIDEOLIBUSERS = "insert into "
			+ SCHEMA_NAME
			+ ".VideoLibUsers (vlusr_salutation , vlusr_firstname , vlusr_middlename , vlusr_lastname , vlusr_displayname , vlusr_dob , vlusr_sex ,"
			+"vlusr_addressid , vlusr_loginid , vlusr_password , vlusr_lastpwdupdate , vlusr_statusid ," 
			+"vlusr_prefernce , vlusr_emailid ,vlusr_invalidlogins,vlusr_lastlogin,vlusr_ssnid,vlusr_subscriptionfee,vlusr_subscriptionrenewal," +
			"vlusr_createddate ,vlusr_lastdetailsupdate, vlusr_rowstate) " +
			"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";/**
	 * Movie
	 */
	public static String TABLE_VIDEOLIBMOVIESMASTER = "create table if not exists "
			+ SCHEMA_NAME
			+ ".VideoLibMoviesMaster("
			+ "vlmov_id int not null auto_increment,"
			+ "vlmov_name varchar(126) not null,"
			+ "vlmov_production varchar(126) not null,"
			+ "vlmov_categoryid int not null,"
			+ "vlmov_releasedate datetime not null,"
			+ "vlmov_rentamount decimal(10,2) not null,"
			+ "vlmov_totalcopies int not null,"
			+ "vlmt_availablecopies int not null,"
			+ "vlmov_createddate datetime not null,"
			+ "vlmov_lastupdateddate datetime,"
			+ "vlmov_rowstate int not null,"
			+ "index(vlmov_categoryid),"
			+ "foreign key (vlmov_categoryid) references VideoLibMovieCategory (vlmcat_id),"
			+ "primary key (vlmov_id)" + ")";
	
	public static String INSERT_VIDEOLIBMOVIESMASTER = "insert into "
			+ SCHEMA_NAME
			+ ".VideoLibMoviesMaster (vlmov_name , vlmov_production , vlmov_categoryid , "
			+ "vlmov_releasedate , vlmov_rentamount , vlmov_totalcopies , vlmt_availablecopies ,"
			+ "vlmov_createddate , vlmov_rowstate) values(?,?,?,?,?,?,?,?,?)";
	
	/**
	 * Movie Cart
	 */
	public static String TABLE_VIDEOLIBMOVIECART = "create table if not exists "
			+ SCHEMA_NAME
			+ ".VideoLibMovieCart("
			+ "vlmt_id int not null auto_increment,"
			+ "vlmt_movieid int not null,"
			+ "vlmt_userid int not null,"
			+ "vlmt_addedon datetime not null,"
			+ "vlmt_itemstatus varchar(1),"
			+ "vlmt_cartstatus varchar(1),"
			+ "vlmt_createddate datetime not null,"
			+ "vlmt_lastupdateddate datetime,"
			+ "vlmt_rowstate int not null,"
			+ "vlmt_qty int,"
			+ "index(vlmt_movieid),"
			+ "index(vlmt_userid),"
			+ "foreign key (vlmt_userid) references VideoLibUsers(vlusr_id),"
			+ "foreign key (vlmt_movieid) references VideoLibMoviesMaster(vlmov_id),"
			+ "primary key(vlmt_id)" + ")";
	
	public static String INSERT_VIDEOLIBMOVIECART = "insert into "
			+ SCHEMA_NAME
			+ ".VideoLibMovieCart (vlmt_movieid , vlmt_userid , vlmt_addedon , vlmt_cartstatus ,"
			+ "vlmt_createddate , vlmt_rowstate, vlmt_qty) values(?,?,?,?,?,?,?)";

	/**
	 * Premium user transaction
	 */
	public static String TABLE_VIDEOLIBPREMIUMUSERTRANSACTION = "create table if not exists "
			+ SCHEMA_NAME
			+ ".VideoLibPremiumUserTransaction("
			+ "vlput_id int not null auto_increment,"
			+ "vlput_usrid int not null,"
			+ "vlput_rentedmovie int not null default 0,"
			+ "vlput_moviereturned int not null default 0,"
			+ "vlput_availablebalance decimal(10,2) not null default 0.00,"
			+ "vlput_totaldeduction decimal(10,2) not null default 0.00,"
			+ "vlput_createddate datetime not null,"
			+ "vlput_lastupdateddate datetime,"
			+ "vlput_rowstate int not null,"
			+ "index(vlput_rentedmovie),"
			+ "index(vlput_moviereturned),"
			+ "index(vlput_availablebalance),"
			+ "foreign key(vlput_usrid) references VideoLibUsers(vlusr_id),"
			+ "foreign key(vlput_rentedmovie) references VideoLibMoviesMaster(vlmov_id),"
			+ "foreign key(vlput_moviereturned) references VideoLibMoviesMaster(vlmov_id),"
			+ "primary key(vlput_id)" + ")";
	
	public static String INSERT_VIDEOLIBPREMIUMUSERTRANSACTION = "insert into "
			+ SCHEMA_NAME
			+ ".VideoLibPremiumUserTransaction (vlput_usrid , vlput_rentedmovie , vlput_moviereturned ,"
			+ " vlput_availablebalance , vlput_totaldeduction , vlput_createddate , vlput_rowstate) "
			+ " values(?,?,?,?,?,?,?)"; 
	
	
	/**
	 * Simple User Transaction
	 */
	public static String TABLE_VIDEOLIBSIMPLEUSERTRANSACTION = "create table if not exists "
			+ SCHEMA_NAME
			+ ".VideoLibSimpleUserTransaction("
			+ "vlsut_id int not null auto_increment,"
			+ "vlsut_usrid int not null,"
			+ "vlsut_rentedmovie int not null default 0,"
			+ "vlsut_moviereturned int not null default 0,"
			+ "vlsut_payableamount decimal(10,2) not null default 0.00,"
			+ "vlsut_amountpaid decimal(10,2) not null default 0.00,"
			+ "vlsut_createddate datetime not null,"
			+ "vlsut_lastupdateddate datetime,"
			+ "vlsut_rowsate int not null,"
			+ "index(vlsut_rentedmovie),"
			+ "index(vlsut_moviereturned),"
			+ "index(vlsut_payableamount),"
			+ "foreign key(vlsut_usrid) references VideoLibUsers(vlusr_id),"
			+ "foreign key(vlsut_rentedmovie) references VideoLibMoviesMaster(vlmov_id),"
			+ "foreign key(vlsut_moviereturned) references VideoLibMoviesMaster(vlmov_id),"
			+ "primary key(vlsut_id)" + ")";
	public static String INSERT_VIDEOLIBSIMPLEUSERTRANSACTION = "insert into "
			+ SCHEMA_NAME
			+ ".VideoLibSimpleUserTransaction (vlsut_usrid , vlsut_rentedmovie , vlsut_moviereturned ,"
			+ " vlsut_payableamount , vlsut_amountpaid , vlsut_createddate , vlsut_rowstate) "
			+ " values(?,?,?,?,?,?,?)"; 

	/**
	 * Movie Category
	 */
	public static String TABLE_VIDEOLIBMOVIECATEGORY = "create table if not exists "
			+ SCHEMA_NAME
			+ ".VideoLibMovieCategory("
			+ "vlmcat_id int not null auto_increment,"
			+ "vlmcat_name varchar(20) not null,"
			+ "vlmcat_createddate datetime not null,"
			+ "vlmcat_lastupdateddate datetime,"
			+ "vlmcat_rowstate int not null," + "primary key(vlmcat_id)" + ")";
	
	public static String INSERT_VIDEOLIBMOVIECATEGORY = "insert into "
			+ SCHEMA_NAME
			+ ".VideoLibMovieCategory values(?,?,?,null,?),(?,?,?,null,?),(?,?,?,null,?),(?,?,?,null,?)";

	/**
	 * Payment
	 */
	public static String TABLE_VIDEOLIBPAYMENT = "create table if not exists "
			+ SCHEMA_NAME + ".VideoLibPayment("
			+ "vlp_id int not null auto_increment,"
			+ "vlp_nameoncard varchar(20) not null,"
			+ "vlp_cardnumberone varchar(4) not null,"
			+ "vlp_cardnumbertwo varchar(4) not null,"
			+ "vlp_cardnumberthree varchar(4) not null,"
			+ "vlp_cardnumberfour varchar(4) not null,"
			+ "vlp_cardtype varchar(20) not null,"
			+ "vlp_cardcvv varchar(3) not null,"
			+ "vlp_cardexpirydate varchar(5) not null,"
			+ "vlp_createddate datetime not null,"
			+ "vlp_lastupdateddate datetime," + "vlp_rowstate int not null,"
			+ "primary key(vlp_id)" + ")";
	
	public static String INSERT_VIDEOLIBPAYMENT = "insert into " + SCHEMA_NAME
			+ ".VideoLibPayment values(?,?,?,?,?,?,?,?,?,?,null,?)";
	
}