package databaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import model.Address;
import model.BillingHistory;
import model.City;
import model.Country;
import model.Movie;
import model.MovieCart;
import model.MovieCategory;
import model.Preference;
import model.State;
import model.Status;
import model.User;
import services.Validation;

public class DataAccessObject {
	Connection con = null;
	static ResultSet rs;
	PreparedStatement pst = null;
	ConnectionPool pool = null;
	public static int totalNoOfRecords;

	public DataAccessObject() {
		try {
			pool = ConnectionPool.getInstance();
			System.out
					.println("Size of free pool in dao constructor b4 getting a conn::"
							+ ConnectionPool.freecon.size());
			System.out
					.println("Size of busy pool in dao constructor b4 getting a conn::"
							+ ConnectionPool.busycon.size());
			con = pool.getCon();
			System.out.println("Got a con");
			// pst = (PreparedStatement) con.createStatement();
			System.out.println("Checking whether the conn is closed");
			if (!con.isClosed())
				System.out.println("Successfully Connected!!!");
			System.out
					.println("Size of free pool in dao constructor after getting a conn::"
							+ ConnectionPool.freecon.size());
			System.out
					.println("Size of busy pool in dao constructor after getting a conn::"
							+ ConnectionPool.busycon.size());

		} catch (Exception e) {
		}
	}

	public boolean paymentGatewayCheck(User user) {
		boolean isValid = true;
		try {
			pst = con
					.prepareStatement("select vlp_id from "
							+ constants.ConstantData.SCHEMA_NAME
							+ ".videolibpayment "
							+ "where vlp_cardnumberone = ? and vlp_cardnumbertwo = ? and vlp_cardnumberthree = ? "
							+ "and vlp_cardnumberfour = ? and vlp_cardcvv = ? and vlp_cardexpirydate = ? and vlp_nameoncard = ? and vlp_rowstate != -1");
			pst.setString(1, user.getPayment().getCardNumOne());
			pst.setString(2, user.getPayment().getCardNumTwo());
			pst.setString(3, user.getPayment().getCardNumThree());
			pst.setString(4, user.getPayment().getCardNumFour());
			pst.setString(5, user.getPayment().getCardCvv());
			pst.setString(6, user.getPayment().getCardExpDate());
			pst.setString(7, user.getPayment().getNameOnCard());
			ResultSet rs = pst.executeQuery();
			System.out.println(pst);
			while (!rs.next()) {
				isValid = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isValid;
	}

	public User authenticateSignIn(User user) throws SQLException {
		try {
			String query = "select vlusr_id,vlusr_displayname,vlusr_prefernce,"
					+ "vlusr_loginid,vlusr_password,vlusr_invalidlogins,vlusr_statusid from "
					+ constants.ConstantData.SCHEMA_NAME
					+ ".VideoLibUsers where vlusr_loginid = ? and vlusr_rowstate != -1";
			pst = con.prepareStatement(query);
			pst.setString(1, user.getLoginId());
			rs = pst.executeQuery();

			while (rs.next()) {
				user.setId(rs.getInt(1));
				user.setDisplayName(rs.getString(2));
				user.getPreference().setPreferenceId(rs.getInt(3));
				user.setInvalidLogins(rs.getInt(6));
				user.getStatus().setStatusId(rs.getInt(7));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeCon(con);
		}
		return user;
	}

	public void updateInvalidLogin(String loginid) throws SQLException {
		java.util.Date today = new java.util.Date();
		java.sql.Date sqlToday = new java.sql.Date(today.getTime());
		try {
			String query = "update  "
					+ constants.ConstantData.SCHEMA_NAME
					+ ".VideoLibUsers set vlusr_invalidlogins = vlusr_invalidlogins+1 , vlusr_lastdetailsupdate = ? "
					+ "where vlusr_loginid = ? and vlusr_rowstate != -1";
			pst = con.prepareStatement(query);
			pst.setDate(1, sqlToday);
			pst.setString(2, loginid);
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pst.close();
			pool.freeCon(con);
		}
	}

	public String[] listState(String country) throws SQLException {
		ArrayList<String> stateList = new ArrayList<String>();
		// String[] stateList = new String[1000];
		int countryid = 0;
		// int i = 1;

		try {
			String queryStmt = "select vlc_countryid from "
					+ constants.ConstantData.SCHEMA_NAME
					+ ".VideoLibCountryMaster where vlc_rowstate != -1 and vlc_countryname = ?";
			pst = con.prepareStatement(queryStmt);
			pst.setString(1, country);
			rs = pst.executeQuery();
			while (rs.next()) {
				countryid = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			String query = "select vls_statename from "
					+ constants.ConstantData.SCHEMA_NAME
					+ ".VideoLibStateMaster where vls_rowstate != -1 and vls_countryid = ?";
			pst = con.prepareStatement(query);
			pst.setInt(1, 1);
			rs = pst.executeQuery();
			while (rs.next()) {
				stateList.add(rs.getString(1));
				// stateList[i] = rs.getString(1);
				// i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rs.close();
			pst.close();
			pool.freeCon(con);
		}
		String[] stateListArray = new String[stateList.size()];
		return stateList.toArray(stateListArray);
	}

	public String[] listCountry() throws SQLException {
		ArrayList<String> countryList = new ArrayList<String>();
		// int i = 1;
		try {
			pst = con.prepareStatement("select vlc_countryname from "
					+ constants.ConstantData.SCHEMA_NAME
					+ ".VideoLibCountryMaster where vlc_rowstate != -1");
			rs = pst.executeQuery();
			while (rs.next()) {
				countryList.add(rs.getString(1));
				// countryList[i] = rs.getString(1);
				// i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rs.close();
			pst.close();
			pool.freeCon(con);
		}
		System.out.println("List country size from dao::" + countryList.size());
		String[] countryListArray = new String[countryList.size()];
		return countryList.toArray(countryListArray);
	}

	public String[] listCity(String state) throws SQLException {
		ArrayList<String> cityList = new ArrayList<String>();
		int stateid = 0;
		// int i = 1;
		try {
			pst = con
					.prepareStatement("select vls_stateid from "
							+ constants.ConstantData.SCHEMA_NAME
							+ ".VideoLibStateMaster where vls_rowstate != -1 and vls_statename = ?");
			pst.setString(1, state);
			rs = pst.executeQuery();
			while (rs.next()) {
				stateid = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			pst = con
					.prepareStatement("select vlcm_cityname from "
							+ constants.ConstantData.SCHEMA_NAME
							+ ".VideoLibCityMaster where vlcm_rowstate != -1 and vlcm_stateid = ?");
			pst.setInt(1, stateid);
			rs = pst.executeQuery();
			while (rs.next()) {
				cityList.add(rs.getString(1));
				// cityList[i] = rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rs.close();
			pst.close();
			pool.freeCon(con);
		}
		String[] cityListArray = new String[cityList.size()];
		return cityList.toArray(cityListArray);
	}

	public int getpreferenceID(Preference pref) throws SQLException {
		int prefId = 0;
		try {
			pst = con
					.prepareStatement("select vlusrtype_id from "
							+ constants.ConstantData.SCHEMA_NAME
							+ ".videolibusertypemaster where vlusrtype_name = ? and vlusrtype_statusid = 1 and"
							+ " vlusrtype_rowstate != -1");
			pst.setString(1, pref.getPreferenceType());
			rs = pst.executeQuery();
			while (rs.next()) {
				prefId = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pst.close();
			rs.close();
			pool.freeCon(con);
		}
		return prefId;
	}

	public int getCountryID(Country country) throws SQLException {
		int countryId = 0;
		try {
			pst = con
					.prepareStatement("select vlc_countryid from "
							+ constants.ConstantData.SCHEMA_NAME
							+ ".videolibcountrymaster where vlc_countryname = ? and vlc_rowstate != -1");
			pst.setString(1, country.getCountryName());
			rs = pst.executeQuery();
			while (rs.next()) {
				countryId = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pst.close();
			rs.close();
			pool.freeCon(con);
		}
		return countryId;
	}

	public int getStateID(State state, Country country) throws SQLException {
		int stateId = 0;
		try {
			pst = con
					.prepareStatement("select vls_stateid from "
							+ constants.ConstantData.SCHEMA_NAME
							+ ".videolibstatemaster where vls_statename = ? and vls_countryid = ? and vls_rowstate != -1");
			pst.setString(1, state.getStateName());
			pst.setInt(2, getCountryID(country));
			rs = pst.executeQuery();
			while (rs.next()) {
				stateId = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pst.close();
			rs.close();
			pool.freeCon(con);
		}
		return stateId;
	}

	public int getCityID(State state, City city, Country country)
			throws SQLException {
		int cityId = 0;
		try {
			pst = con
					.prepareStatement("select vlcm_cityid from "
							+ constants.ConstantData.SCHEMA_NAME
							+ ".videolibcitymaster where vlcm_cityname = ? and vlcm_stateid = ? and vlcm_rowstate != -1");
			pst.setString(1, city.getCityName());
			pst.setInt(2, getStateID(state, country));
			rs = pst.executeQuery();
			while (rs.next()) {
				cityId = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pst.close();
			rs.close();
			pool.freeCon(con);
		}
		return cityId;
	}

	public void SignUp(User user) throws SQLException {
		java.util.Date today = new java.util.Date();
		java.sql.Date sqlToday = new java.sql.Date(today.getTime());

		try {
			pst = con
					.prepareStatement(constants.ConstantData.INSERT_VIDEOLIBUSERS);
			pst.setString(1, user.getSalutation());
			pst.setString(2, user.getFirstName());
			pst.setString(3, user.getMiddleName());
			pst.setString(4, user.getLastName());
			pst.setString(5, user.getDisplayName());
			pst.setString(6, user.getDob());
			pst.setString(7, user.getSex());
			pst.setInt(8, insertAddress(user.getAddress()));
			pst.setString(9, user.getLoginId());
			pst.setString(10, user.getPassword());
			pst.setDate(11, sqlToday);
			pst.setInt(12, constants.ConstantData.ACTIVE_STATUS_ID);
			pst.setInt(13, user.getPreference().getPreferenceId());
			pst.setString(14, user.getEmailId());
			pst.setInt(15, 0);
			pst.setDate(16, null);
			pst.setString(17, user.getSsnId());
			pst.setFloat(18, user.getSubscriptionFee());
			pst.setDate(19, null);
			pst.setDate(20, sqlToday);
			pst.setDate(21, null);
			pst.setInt(22, 1);
			pst.executeUpdate();
			pst = con.prepareStatement("update "
					+ constants.ConstantData.SCHEMA_NAME
					+ ".videolibusers set vlusr_membershipid = MEM-"
					+ Integer.toString(getMaxUserId())
					+ "-0001 where vlusr_id = ? and vlusr_rowstate != -1");
			pst.setInt(1, getMaxUserId());
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rs.close();
			pst.close();
			pool.freeCon(con);
		}

	}

	public int getMaxUserId() {
		int userMaxId = 1;
		try {
			pst = con.prepareStatement("select MAX(vlusr_id) from "
					+ constants.ConstantData.SCHEMA_NAME + ".videolibusers");
			rs = pst.executeQuery();
			while (rs.next()) {
				if (rs.getInt(1) >= 1) {
					userMaxId = rs.getInt(1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeCon(con);
		}
		return userMaxId;
	}

	/**
	 * Inserts the address of the user and returns the new generated addressID
	 * 
	 * @param address
	 * @return
	 * @throws SQLException
	 */
	public int insertAddress(Address address) throws SQLException {
		ResultSet rs = null;
		PreparedStatement pst = null;
		java.util.Date today = new java.util.Date();
		java.sql.Date sqlToday = new java.sql.Date(today.getTime());
		int cityid = 0, countryid = 0, stateid = 0, maxAddrId = 1;
		try {

			pst = con
					.prepareStatement("select vlcm_cityid from "
							+ constants.ConstantData.SCHEMA_NAME
							+ ".VideoLibCityMaster where vlcm_cityname = ? and vlcm_rowstate != -1");
			pst.setString(1, address.getCity().getCityName());
			rs = pst.executeQuery();
			while (rs.next()) {
				cityid = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			rs = null;
			pst = con
					.prepareStatement("select vls_stateid from "
							+ constants.ConstantData.SCHEMA_NAME
							+ ".VideoLibStateMaster where vls_statename = ? and vls_rowstate != -1");
			pst.setString(1, address.getState().getStateName());
			rs = pst.executeQuery();
			while (rs.next()) {
				stateid = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			rs = null;
			pst = con
					.prepareStatement("select vlc_countryid from "
							+ constants.ConstantData.SCHEMA_NAME
							+ ".VideoLibCountryMaster where vlc_countryname = ? and vlc_rowstate != -1");
			pst.setString(1, address.getCountry().getCountryName());
			rs = pst.executeQuery();
			while (rs.next()) {
				countryid = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			pst = con
					.prepareStatement(constants.ConstantData.INSERT_VIDEOLIBUSERADDRESSMASTER);
			pst.setString(1, address.getAddrLine1());
			pst.setString(2, address.getAddrLine2());
			pst.setString(3, address.getAddrLine3());
			pst.setString(4, address.getTelNo());
			pst.setInt(5, cityid);
			pst.setInt(6, stateid);
			pst.setInt(7, countryid);
			pst.setString(8, address.getZipCode());
			pst.setDate(9, sqlToday);
			pst.setInt(10, 1);
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			rs = null;
			pst = con.prepareStatement("select MAX(vlusraddress_id) from "
					+ constants.ConstantData.SCHEMA_NAME
					+ ".VideoLibUserAddressMaster");
			rs = pst.executeQuery();
			while (rs.next()) {
				maxAddrId = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rs.close();
			pst.close();
		}
		return maxAddrId;
	}

	public User[] displayUsers(int preferenceId, int offset, int noOfRecords)
			throws SQLException {
		ArrayList<User> users = new ArrayList<User>();

		try {
			if (!con.isClosed()) {
				/*
				 * this case is for when user enters nothing and searches for
				 * which we have to display all the users
				 */
				ResultSet rt = null;
				if (preferenceId == 0) {
					System.out.println("Fetching all users");
					pst = con
							.prepareStatement("select SQL_CALC_FOUND_ROWS * from videolibrary.VideoLibUsers where vlusr_rowstate !=-1 and vlusr_statusid = 1");

				} /* this one is for all users */

				else {
					System.out.println("Fetchin all members with pref id::"
							+ preferenceId);
					pst = con
							.prepareStatement("select * from videolibrary.VideoLibUsers where vlusr_rowstate !=-1 and vlusr_statusid = 1 and vlusr_prefernce=?");
					pst.setInt(1, preferenceId);
				}
				rt = pst.executeQuery();

				while (rt.next()) {
					User user = new User();
					user.setId(rt.getInt(1));
					user.setSalutation(rt.getString(2));
					user.setFirstName(rt.getString(3));
					user.setMiddleName(rt.getString(4));
					user.setLastName(rt.getString(5));
					user.setDisplayName(rt.getString(6));
					user.setDob(String.valueOf(rt.getString(7)));
					user.setSex(rt.getString(8));
					user.setLoginId(rt.getString(10));
					user.setLastPwdUpdate(String.valueOf(rt.getDate(12)));
					user.setEmailId(rt.getString(15));
					user.setLastLogin(String.valueOf(rt.getDate(17)));
					user.setSubscriptionRenewal(String.valueOf(rt.getDate(18)));
					users.add(user);

				} /* end of while */
				rt.close();
				rt = pst.executeQuery("SELECT FOUND_ROWS()");
				// if (rs.next())
				// this.setNoOfRecords(rs.getInt(1));

			} /* this one is for end of if */
			/* this one is for if user enters particular userid */

		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			pst.close();
			pool.freeCon(con);
		}
		System.out.println("inside method size=" + users.size());
		User[] userArray = new User[users.size()];
		userArray = (User[]) users.toArray(userArray);
		return userArray;

	} /* end of method for user */

	public ArrayList<Movie> getLatestMovies(ArrayList<Movie> latestMovieDetails)
			throws SQLException {
		try {
			if (!con.isClosed()) {
				pst = con
						.prepareStatement("select vlmov_id , vlmov_name , vlmt_availablecopies from "
								+ constants.ConstantData.SCHEMA_NAME
								+ ".videolibmoviesmaster "
								+ "where vlmov_releasedate between current_date and current_date + interval '1' month");
				rs = pst.executeQuery();
				while (rs.next()) {
					Movie movie = new Movie();
					movie.setMovieId(rs.getInt(1));
					movie.setMovieName(rs.getString(2));
					movie.setAvailableCopies(rs.getInt(3));
					latestMovieDetails.add(movie);
				}
			} /* this one is closing if brace */
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pst.close();
			rs.close();
			pool.freeCon(con);
			System.out.println("Size of free pool::"
					+ ConnectionPool.freecon.size());
			System.out.println("Size of busy pool::"
					+ ConnectionPool.busycon.size());

		}
		return latestMovieDetails;
	}

	public ArrayList<Movie> displayMovies(String filterAlphabet)
			throws SQLException {
		ArrayList<Movie> displayMovieDetails = new ArrayList<Movie>();
		try {
			if (!con.isClosed()) {
				pst = con
						.prepareStatement("select mv.vlmov_id , mv.vlmov_name , mv.vlmov_production , mv.vlmov_releasedate , mct.vlmcat_id , "
								+ "mct.vlmcat_name , mv.vlmt_availablecopies, mv.vlmov_rentamount from "
								+ constants.ConstantData.SCHEMA_NAME
								+ ".videolibmoviesmaster mv left outer join "
								+ constants.ConstantData.SCHEMA_NAME
								+ ".videolibmoviecategory mct on mv.vlmov_categoryid = mct.vlmcat_id "
								+ "where mv.vlmov_name like ? and mv.vlmov_rowstate != -1 and mct.vlmcat_rowstate != -1");
				String filterAlphabet1 = filterAlphabet + "%";
				pst.setString(1, filterAlphabet1);

				System.out.println(pst);
				rs = pst.executeQuery();
				while (rs.next()) {
					Movie movie = new Movie();
					MovieCategory movieCategory = new MovieCategory();
					movie.setMovieId(rs.getInt("mv.vlmov_id"));
					movie.setMovieName(rs.getString("mv.vlmov_name"));
					movie.setProdBanner(rs.getString("mv.vlmov_production"));
					movie.setReleaseDate(rs.getString("mv.vlmov_releasedate"));
					movieCategory
							.setMovieCategoryId(rs.getInt("mct.vlmcat_id"));
					movieCategory.setMovieCategory(rs
							.getString("mct.vlmcat_name"));
					movie.setMovieCategory(movieCategory);
					movie.setAvailableCopies(rs
							.getInt("mv.vlmt_availablecopies"));
					movie.setRentAmount(rs.getFloat("mv.vlmov_rentamount"));
					displayMovieDetails.add(movie);
				}// System.out.println("size in::::::::::::::::::::" +
					// displayMovieDetails.size());
			} /* this one is closing if brace */
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pst.close();
			rs.close();
			pool.freeCon(con);
			System.out.println("Size of free pool::"
					+ ConnectionPool.freecon.size());
			System.out.println("Size of busy pool::"
					+ ConnectionPool.busycon.size());

		}
		return displayMovieDetails;
	}

	public User userExistance(User user) throws SQLException {
		String errorMsg = "Registration issue kindly connect to administrator";
		ResultSet rs = null;
		int userid = 0;
		try {
			pst = con
					.prepareStatement("select vlusr_id from "
							+ constants.ConstantData.SCHEMA_NAME
							+ ".videolibusers where vlusr_id = ? and vlusr_rowstate != -1");
			pst.setInt(1, user.getId());
			rs = pst.executeQuery();
			while (rs.next())
				userid = rs.getInt("vlusr_id");
			if (userid == 0)
				user.setErrorMsg(errorMsg);
			else
				user.setErrorMsg("Success");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pst.close();
			rs.close();
			pool.freeCon(con);
		}
		return user;
	}

	/**
	 * 
	 * @param movieCart
	 * @return
	 * @throws SQLException
	 */
	public MovieCart[] movieExistance(MovieCart[] movieCart)
			throws SQLException {
		String errorMsg = "Invalid : Movie no longer available in the library";
		try {
			for (int i = 0; i < movieCart.length; i++) {
				ResultSet rs = null;
				PreparedStatement pst = con
						.prepareStatement("select vlmov_id , vlmt_availablecopies from "
								+ constants.ConstantData.SCHEMA_NAME
								+ ".videolibmoviesmaster where vlmov_id = ? and vlmov_rowstate != -1");
				pst.setInt(1, movieCart[i].getMovieId());
				rs = pst.executeQuery();
				while (rs.next()) {
					if (rs.getInt(2) <= 0)
						movieCart[i].setErrorMsg(errorMsg);
				}
				pst.close();
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeCon(con);
		}
		return movieCart;
	}

	/**
	 * 
	 * @param movieCart
	 * @return
	 * @throws SQLException
	 */
	public MovieCart[] userMovieMapExistance(MovieCart[] movieCart)
			throws SQLException {
		PreparedStatement[] pst = new PreparedStatement[movieCart.length];
		ResultSet rs[] = new ResultSet[movieCart.length];
		try {
			for (int i = 0; i < movieCart.length; i++) {
				pst[i] = con
						.prepareStatement("select vlmt_cartstatus from "
								+ constants.ConstantData.SCHEMA_NAME
								+ ".VideoLibMovieCart where vlmt_movieid = ? and vlmt_userid = ? and vlmt_rowstate != -1 ");
				pst[i].setInt(1, movieCart[i].getMovieId());
				pst[i].setInt(2, movieCart[i].getUserId());
				rs[i] = pst[i].executeQuery();
				while (rs[i].next()) {
					if (rs[i].getString(1).equals("B")) {
						System.out.println("Movie already bought!");
						movieCart[i]
								.setErrorMsg("Invalid : Movie is already available in your account!");
					}

					else if (rs[i].getString(1).equals("C")) {
						System.out.println("Movie already in cart");
						movieCart[i]
								.setErrorMsg("Invalid : Movie is already added in your cart");
					}

				}
				pst[i].close();
				rs[i].close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeCon(con);
		}

		return movieCart;
	}

	/**
	 * 
	 * @param movieCart
	 * @return
	 * @throws SQLException
	 */
	public MovieCart[] addToCart(MovieCart[] movieCart) throws SQLException {
		System.out.println("Into Add to Cart");
		java.util.Date today = new java.util.Date();
		java.sql.Date sqlToday = new java.sql.Date(today.getTime());
		PreparedStatement pst[] = new PreparedStatement[movieCart.length];
		// int i = movieCart.length;
		try {
			User user = new User();
			user.setId(movieCart[0].getUserId());
			user = userExistance(user);
			if (user.getErrorMsg() != "Success") {
				movieCart[0]
						.setErrorMsg("Invalid : Registration issue kindly connect to administrator");
				return movieCart;
			}
			System.out.println("User authenticated!");
			movieCart = movieExistance(movieCart);
			System.out.println("Movie authenticated!");
			// for(i = 0 ; i <= movieCart.length ; i++){
			// if(movieCart[i].getErrorMsg() != null) return movieCart;
			// }
			movieCart = userMovieMapExistance(movieCart);
			System.out
					.println("Movie not already present! Going to insert in cart!");
			// for(i = 0 ; i <= movieCart.length ; i++){
			// if(movieCart[i].getErrorMsg() != null) return movieCart;
			// }
			for (int i = 0; i < movieCart.length
					&& movieCart[i].getErrorMsg() == null; i++) {
				// i = 0;
				// while ((i != movieCart.length)
				// && (movieCart[i].getErrorMsg() == null)) {
				pst[i] = con
						.prepareStatement(constants.ConstantData.INSERT_VIDEOLIBMOVIECART);
				pst[i].setInt(1, movieCart[i].getMovieId());
				pst[i].setInt(2, movieCart[i].getUserId());
				pst[i].setDate(3, sqlToday);
				pst[i].setString(4, "C");
				// pst[i].setInt(5, 1);
				pst[i].setDate(5, sqlToday);
				pst[i].setInt(6, 1);
				pst[i].setInt(7, 1); // Qty
				pst[i].executeUpdate();
				// i++;
				pst[i].close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		System.out.println("Returning the updated cart array");
		return movieCart;
	}

	/**
	 * 
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public MovieCart[] retrieveCart(int userId) throws SQLException {
		System.out.println("Retrieving the cart for the user");
		ArrayList<MovieCart> movieCartArrayList = new ArrayList<MovieCart>();
		// ArrayList<MovieCart> movieCartArrayList = new ArrayList<MovieCart>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			String query = "select vlmt_movieid, vlmt_qty from "
					+ constants.ConstantData.SCHEMA_NAME
					+ ".VideoLibMovieCart "
					+ "where vlmt_userid = ? and vlmt_rowstate != -1 and vlmt_cartstatus = 'C'";
			pst = con.prepareStatement(query);
			pst.setInt(1, userId);
			rs = pst.executeQuery();
			while (rs.next()) {
				MovieCart movieCart = new MovieCart();
				movieCart.setMovieId(rs.getInt(1));
				movieCart.setQty(rs.getInt(2));
				movieCartArrayList.add(movieCart);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeCon(con);
		}
		MovieCart[] movieCartArray = new MovieCart[movieCartArrayList.size()];

		return movieCartArrayList.toArray(movieCartArray);
	}

	/**
	 * 
	 * @param cartData
	 * @return
	 * @throws SQLException
	 */
	public Movie[] retrieveMovieDataForCart(MovieCart[] cartData)
			throws SQLException {
		Movie[] movieArray = new Movie[cartData.length];
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			for (int i = 0; i < movieArray.length; i++) {
				int movieId = cartData[i].getMovieId();
				String query = "select vlmov_name, vlmov_production, vlmov_rentamount, vlmov_id from "
						+ constants.ConstantData.SCHEMA_NAME
						+ ".videolibmoviesmaster "
						+ "where vlmov_id = ? and vlmov_rowstate != -1";
				pst = con.prepareStatement(query);
				pst.setInt(1, movieId);
				rs = pst.executeQuery();
				while (rs.next()) {
					Movie movie = new Movie();
					movie.setMovieName(rs.getString(1));
					movie.setProdBanner(rs.getString(2));
					movie.setRentAmount(rs.getInt(3));
					movie.setMovieId(rs.getInt(4));
					movieArray[i] = movie;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeCon(con);
		}
		return movieArray;

	}

	public void deleteFromCart(int userID, int movieID) throws SQLException {
		try {
			String query = "update "
					+ constants.ConstantData.SCHEMA_NAME
					+ ".videolibmoviecart set vlmt_rowstate = -1 where vlmt_userid = ? and vlmt_movieid = ?";
			pst = con.prepareStatement(query);
			pst.setInt(1, userID);
			pst.setInt(2, movieID);
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeCon(con);

		}
	}

	public void addBalance(int userID, float amountToAdd) throws SQLException {
		try {
			String query = "update "
					+ constants.ConstantData.SCHEMA_NAME
					+ ".videolibusers set vlusr_subscriptionfee = vlusr_subscriptionfee + ? "
					+ "where vlusr_id = ? and vlusr_rowstate != -1";
			pst = con.prepareStatement(query);
			pst.setInt(2, userID);
			pst.setFloat(1, amountToAdd);
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeCon(con);
		}
	}

	// ***************************************************************************************************
	public Movie[] checkOutMovie(Movie[] movie, User user) throws SQLException {
		java.util.Date today = new java.util.Date();
		java.sql.Date sqlToday = new java.sql.Date(today.getTime());
		PreparedStatement pst = null;
		PreparedStatement[] pst1 = new PreparedStatement[movie.length];
		int i = 0;
		String appendPst = "";
		ResultSet rs1 = null, rs2 = null, rs3 = null, rs4 = null;
		ResultSet[] resultSetArray = new ResultSet[movie.length];
		int userMovieAccountCount = 0;
		try {

			// Checking the preference ID of user
			pst = con
					.prepareStatement("select vlusr_prefernce from "
							+ constants.ConstantData.SCHEMA_NAME
							+ ".videolibusers where vlusr_id = ? and vlusr_rowstate != -1 and vlusr_statusid = 1");
			pst.setInt(1, user.getId());
			rs1 = pst.executeQuery();
			while (rs1.next()) {
				if (rs1.getInt(1) == 1) {
					System.out.println("Simple User");
					if (movie.length <= 2) {
						System.out
								.println(" Fetching the already bought count for user");
						pst = con
								.prepareStatement("select count(vlmt_movieid) from "
										+ constants.ConstantData.SCHEMA_NAME
										+ ".videolibmoviecart where vlmt_userid = ? and vlmt_rowstate != -1 and vlmt_cartstatus = ?");
						pst.setInt(1, user.getId());
						pst.setString(2, "B");
						rs2 = pst.executeQuery();
						while (rs2.next()) {
							userMovieAccountCount = rs2.getInt(1);
						}
						System.out.println("Already bought movies count:"
								+ userMovieAccountCount);
						if (userMovieAccountCount + movie.length <= 2) {
							System.out
									.println("userMovieAccountCount + movie.length <= 2... Hence can proceed");
							System.out.println("Checking the balance");
							pst = con
									.prepareStatement("select vlusr_subscriptionfee from "
											+ constants.ConstantData.SCHEMA_NAME
											+ ".videolibusers where vlusr_id = ? and vlusr_rowstate != -1 and vlusr_statusid = 1");
							pst.setInt(1, user.getId());
							rs3 = pst.executeQuery();
							while (rs3.next()) {
								if (rs3.getFloat(1) > 0) {
									System.out.println("balance "
											+ rs3.getFloat(1)
											+ " greater than 0.. valid");
									System.out
											.println("Fetching the total rent amount of all the movies for user");
									for (i = 0; i < movie.length - 1; i++)
										appendPst = appendPst.concat(",?");
									pst = con
											.prepareStatement("select SUM(vlmov_rentamount) from "
													+ constants.ConstantData.SCHEMA_NAME
													+ ".videolibmoviesmaster where vlmov_id in (?"
													+ appendPst + ")");
									for (i = 0; i < movie.length; i++)
										pst.setInt(i + 1, movie[i].getMovieId());
									rs4 = pst.executeQuery();
									while (rs4.next()) {
										System.out
												.println("total rent amount for the movies to be added: "
														+ rs4.getInt(1));
										if (rs4.getFloat(1) <= rs3.getFloat(1)) {
											System.out
													.println("Total rent amount less than balance.. valid");
											System.out
													.println("Checking the avl copies of all movies");
											for (i = 0; i < movie.length; i++) {
												pst1[i] = con
														.prepareStatement("select vlmt_availablecopies from "
																+ constants.ConstantData.SCHEMA_NAME
																+ ".videolibmoviesmaster where vlmov_id = ? and vlmov_rowstate != -1");
												pst1[i].setInt(1,
														movie[i].getMovieId());
												resultSetArray[i] = pst1[i]
														.executeQuery();
												while (resultSetArray[i].next()) {
													System.out
															.println("Avl copies for "
																	+ movie[i]
																			.getMovieId()
																	+ " are "
																	+ resultSetArray[i]
																			.getInt(1));
													if (resultSetArray[i]
															.getInt(1) > 0) {
														System.out
																.println("Inserting into simple user trxn table");
														pst1[i] = con
																.prepareStatement("insert into "
																		+ constants.ConstantData.SCHEMA_NAME
																		+ ".videolibsimpleusertransaction (vlsut_usrid , vlsut_rentedmovie , vlsut_moviereturned , vlsut_payableamount ," +
																		" vlsut_amountpaid , vlsut_createddate , vlsut_rowstate) values (?,?,?,?,?,?,?)");
														pst1[i].setInt(1,
																user.getId());
														pst1[i].setInt(
																2,
																movie[i].getMovieId());
														pst1[i].setInt(3, 0);
														pst1[i].setInt(4, 0);
														pst1[i].setFloat(
																5,
																getPayableAmount(movie[i]
																		.getMovieId()));
														pst1[i].setDate(6,
																sqlToday);
														pst1[i].setInt(7, 1);
														pst1[i].executeUpdate();
														System.out
																.println("Updating B in corresponding CArt entry");
														pst1[i] = con
																.prepareStatement("update "
																		+ constants.ConstantData.SCHEMA_NAME
																		+ ".videolibmoviecart set vlmt_cartstatus = 'B' where vlmt_userid = ? and vlmt_movieid = ? and vlmt_rowstate != -1");
														pst1[i].setInt(1,
																user.getId());
														pst1[i].setInt(
																2,
																movie[i].getMovieId());
														pst1[i].executeUpdate();
														System.out
																.println("Reducing the avl copy for movie "
																		+ movie[i]
																				.getMovieId());
														pst1[i] = con
																.prepareStatement("update "
																		+ constants.ConstantData.SCHEMA_NAME
																		+ ".videolibmoviesmaster set vlmt_availablecopies = vlmt_availablecopies-1 " +
																		"where vlmov_id = ? and vlmov_rowstate != -1");
														pst1[i].setInt(1, movie[i].getMovieId());
														pst1[i].executeUpdate();
														pst1[i].close();
														
														PreparedStatement pst4 = con.prepareStatement("Update videolibrary.videolibusers set " +
																"vlusr_subscriptionfee = vlusr_subscriptionfee - ? where vlusr_id =? and vlusr_rowstate != -1 ");
														pst4.setFloat(1, rs4.getFloat(1));
														pst4.setInt(2, user.getId());
														pst4.executeUpdate();
														System.out
																.println("Balance reduced in the user account");
													} else {
														System.out
																.println("Invalid - Movie not available in the store");
														movie[i].setErrorMsg("Invalid - Movie not available in the store");
													}

												}
											}
										} else {
											System.out
													.println("Invalid - Insufficient balance kindly recharge your account");
											movie[0].setErrorMsg("Invalid - Insufficient balance kindly recharge your account");
											return movie;
										}
									}
								} else {
									System.out
											.println("Invalid - Insufficient balance kindly recharge your account");
									movie[0].setErrorMsg("Invalid - Insufficient balance kindly recharge your account");
									return movie;
								}
							}
						} else {
							while (i <= movie.length) {
								System.out
										.println("Invalid - Your account exceeded the maximum number of movies.kindly return the previous movie item");
								movie[0].setErrorMsg("Invalid - Your account exceeded the maximum number of movies.kindly return the previous movie item");
								i++;
							}
							return movie;
						}
					} else {
						while (i <= movie.length) {
							System.out
									.println("Invalid - Your account exceeded the maximum number of movies.kindly return the previous movie item");
							movie[0].setErrorMsg("Invalid - Your account exceeded the maximum number of movies.kindly return the previous movie item");
							i++;
						}
						return movie;
					}
				}
				if (rs1.getInt(1) == 2) {
					System.out.println("Premium user");
					if (movie.length <= 10) {
						System.out
								.println("Movie to be added is not greater than 10... valid");
						System.out
								.println("getting all the bought movies count for user");
						pst = con
								.prepareStatement("select count(vlmt_movieid) from "
										+ constants.ConstantData.SCHEMA_NAME
										+ ".videolibmoviecart where vlmt_userid = ? and vlmt_rowstate != -1 and vlmt_cartstatus = ?");
						pst.setInt(1, user.getId());
						pst.setString(2, "B");
						rs2 = pst.executeQuery();
						while (rs2.next()) {
							System.out
									.println("Count of already bought movies:"
											+ rs2.getInt(1));
							userMovieAccountCount = rs2.getInt(1);
						}
						if (userMovieAccountCount + movie.length <= 10) {
							System.out
									.println("sum of already bought n to be added is !> 10.. valid");
							// System.out.println("checking balance");
							// pst = con
							// .prepareStatement("select vlusr_subscriptionfee from "
							// + constants.ConstantData.SCHEMA_NAME
							// +
							// ".videolibusers where vlusr_id = ? and vlusr_rowstate != -1 and vlusr_status = 1");
							// pst.setInt(1, user.getId());
							// rs3 = pst.executeQuery();
							// while (rs3.next()) {
							// if (rs3.getDouble(1) > 0) {s
							// for (i = 0; i < movie.length - 1; i++)
							// appendPst = appendPst.concat(",?");
							// pst = con
							// .prepareStatement("select SUM(vlmov_rentamount) from "
							// + constants.ConstantData.SCHEMA_NAME
							// + ".videolibmoviesmaster where vlmov_id in (?"
							// + appendPst + ")");
							// for (i = 0; i < movie.length; i++)
							// pst.setInt(i + 1, movie[i].getMovieId());
							// rs4 = pst.executeQuery();
							// while (rs4.next()) {
							// if (rs4.getInt(1) <= rs3.getDouble(1)) {
							System.out
									.println("Checking for the avalilable copies for all movies...");
							for (i = 0; i < movie.length; i++) {
								pst1[i] = con
										.prepareStatement("select vlmt_availablecopies from "
												+ constants.ConstantData.SCHEMA_NAME
												+ ".videolibmoviesmaster where vlmov_id = ? and vlmov_rowstate != -1");
								pst1[i].setInt(1, movie[i].getMovieId());
								resultSetArray[i] = pst1[i].executeQuery();
								pst1[i].close();
								while (resultSetArray[i].next()) {
									System.out.println("Avl copies for movies "
											+ movie[i].getMovieId() + " is "
											+ resultSetArray[i].getInt(1));
									if (resultSetArray[i].getInt(1) > 0) {
										System.out
												.println("Inserting into premium transaction table");
										pst1[i] = con
												.prepareStatement("insert into "
														+ constants.ConstantData.SCHEMA_NAME
														+ ".videolibpremiumusertransaction (vlput_usrid , vlput_rentedmovie , vlput_moviereturned , vlput_availablebalance , vlput_totaldeduction , vlput_createddate , vlput_rowstate) values (?,?,?,?,?,?,?)");
										pst1[i].setInt(1, user.getId());
										pst1[i].setInt(2, movie[i].getMovieId());
										pst1[i].setInt(3, 0);
										pst1[i].setInt(4, 0);
										pst1[i].setFloat(5,
												getPayableAmount(movie[i]
														.getMovieId()));
										pst1[i].setDate(6, sqlToday);
										pst1[i].setInt(7, 1);
										pst1[i].executeUpdate();
										System.out
												.println("Updating B in corresponding CArt entry");
										pst1[i] = con
												.prepareStatement("update "
														+ constants.ConstantData.SCHEMA_NAME
														+ ".videolibmoviecart set vlmt_cartstatus = 'B' where vlmt_userid = ? and vlmt_movieid = ? and vlmt_rowstate != -1");
										pst1[i].setInt(1, user.getId());
										pst1[i].setInt(2, movie[i].getMovieId());
										pst1[i].executeUpdate();
										System.out
												.println("Reducing the avl copy for movie "
														+ movie[i].getMovieId());
										pst1[i] = con
												.prepareStatement("update "
														+ constants.ConstantData.SCHEMA_NAME
														+ ".videolibmoviesmaster set vlmt_availablecopies = vlmt_availablecopies-1 where vlmov_id = ? and vlmov_rowstate != -1");
										pst1[i].executeUpdate();
										pst1[i].close();
									} else {
										System.out
												.println("Invalid - Movie not available in the store");
										movie[i].setErrorMsg("Invalid - Movie not available in the store");
									}
								}
							}
							// } else {
							// System.out.println("Invalid - Insufficient balance kindly recharge your account");
							// movie[0].setErrorMsg("Invalid - Insufficient balance kindly recharge your account");
							// return movie;
							// }
							// }
							// } else {
							// System.out.println("Invalid - Insufficient balance kindly recharge your account");
							// movie[0].setErrorMsg("Invalid - Insufficient balance kindly recharge your account");
							// return movie;
							// }

						} else {
							while (i <= movie.length) {
								movie[0].setErrorMsg("Invalid - Your account exceeded the maximum number of movies.kindly return the previous movie item");
								i++;
							}
							System.out
									.println("Invalid - Your account exceeded the maximum number of movies.kindly return the previous movie item");
							return movie;
						}
					} else {
						while (i <= movie.length) {
							movie[0].setErrorMsg("Invalid - Your account exceeded the maximum number of movies.kindly return the previous movie item");
							i++;
						}
						System.out
								.println("Invalid - Your account exceeded the maximum number of movies.kindly return the previous movie item");
						return movie;
					}
				}
				// if()
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pst.close();
			// rs1.close();
			// rs2.close();
			pool.freeCon(con);
		}
		return movie;
	}

	public float getPayableAmount(int movieid) throws SQLException {
		ResultSet rs = null;
		float movieAmount = 0;
		try {
			pst = con
					.prepareStatement("select vlmov_rentamount from "
							+ constants.ConstantData.SCHEMA_NAME
							+ ".videolibmoviesmaster where vlmov_id = ? and vlmov_rowstate != -1");
			pst.setInt(1, movieid);
			rs = pst.executeQuery();
			while (rs.next()) {
				movieAmount = rs.getFloat(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pst.close();
			pool.freeCon(con);
		}
		return movieAmount;
	}

	public Movie[] movieCriteriaSearch(Movie movie) throws SQLException {
		java.sql.Date releasedDate = null;
		// System.out.println("release Date inside method="+movie.getReleaseDate());
		// System.out.println("release date length="+movie.getReleaseDate().length());

		/*
		 * the below if condition checks if user entered data field and if
		 * entered converts it into mysql date
		 */
		if (movie.getReleaseDate().length() != 0) {
			System.out.println("inside if of null date");
			DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
			Date releaseDateUtil = null;
			try {

				releaseDateUtil = formater.parse(movie.getReleaseDate());
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			releasedDate = new java.sql.Date(releaseDateUtil.getTime());
			System.out.println("release date in sql format=" + releasedDate);
			System.out.println("size of released date=");
		} /* method to convert from string to mysql date-end */

		String query = "select * from videolibrary.VideoLibMoviesMaster where vlmov_rowstate != -1 AND", query1 = "";
		if (movie.getMovieName().length() != 0)
			query = query + " vlmov_name LIKE '%" + movie.getMovieName()
					+ "%'  AND";
		if (movie.getProdBanner().length() != 0)
			query = query + " vlmov_production LIKE '%" + movie.getProdBanner()
					+ "%' AND";
		if (movie.getMovieCategory().getMovieCategoryId() != 0)
			query = query + " vlmov_categoryid= "
					+ movie.getMovieCategory().getMovieCategoryId() + " AND";
		if (movie.getReleaseDate().length() != 0)
			query = query + " vlmov_releasedate < '" + releasedDate + "' AND";
		if (movie.getAvailableCopies() != 0)
			query = query + " vlmt_availablecopies> "
					+ movie.getAvailableCopies() + " AND";
		/* start-this if condition is to remove extra and that is entered */
		if (query.endsWith("AND")) {
			System.out
					.println("Query for movie search ending with AND..so removing it!");
			query1 = query.substring(0, query.length() - 3);
			query1 = query1 + ";";
		}/* end-this if condition is to remove extra and that is entered */

		/* start-this if condition is when no criteria is entered */
		if (query.endsWith("where")) {
			System.out.println("insdie where clause for movie search query");
			query1 = query.substring(0, query.length() - 5);
			query1 = query1 + ";";
		}/* end-this if condition is when no criteria is entered */

		System.out.println("query formed for Movie search criteria =" + query1);

		/*
		 * the below if condition is when the search is for movieName based
		 * search
		 */
		ArrayList<Movie> movies = new ArrayList<Movie>();
		ResultSet rt = null;

		try {
			pst = con.prepareStatement(query1);
			rt = pst.executeQuery();
			System.out.println("after execute query");
			while (rt.next()) {
				Movie movie1 = new Movie();
				MovieCategory movieCategory = new MovieCategory();
				movie1.setMovieId(rt.getInt(1));
				movie1.setMovieName(rt.getString(2));
				movie1.setProdBanner(rt.getString(3));
				movieCategory.setMovieCategoryId(rt.getInt(4));
				int categoryId = rt.getInt(4);
				PreparedStatement pstmt2 = con
						.prepareStatement("select vlmcat_name from videolibrary.VideoLibMoviecategory where vlmcat_id=?");
				pstmt2.setInt(1, categoryId);
				ResultSet rt2 = pstmt2.executeQuery();
				String movieCategoryName = null;
				while (rt2.next()) {
					movieCategoryName = rt2.getString(1);
				}
				movieCategory.setMovieCategory(movieCategoryName);
				movie1.setMovieCategory(movieCategory);
				movie1.setReleaseDate(String.valueOf(rt.getDate(5)));
				movie1.setRentAmount(rt.getFloat(6));
				movie1.setTotalCopies(rt.getInt(7));
				movie1.setAvailableCopies(rt.getInt(8));
				movies.add(movie1);

			} /* end of while */

		} /* end of try */
		catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			pst.close();
			pool.freeCon(con);
		}

		/* converting array list to movie array */
		Movie[] movieArray = new Movie[movies.size()];
		movieArray = (Movie[]) movies.toArray(movieArray);
		System.out.println("Returning the searched movie array of length:"
				+ movieArray.length);
		return movieArray;
	} /* end of method for movie search criteria */

	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	public MovieCategory[] fetchMovieCategory() throws SQLException {
		System.out.println("Fetching all the movie categories");
		ArrayList<MovieCategory> movieCatArrList = new ArrayList<MovieCategory>();
		ResultSet rs = null;
		try {
			String query = "select vlmcat_id, vlmcat_name from videolibrary.videolibmoviecategory";
			pst = con.prepareStatement(query);
			rs = pst.executeQuery();
			while (rs.next()) {
				MovieCategory movieCategory = new MovieCategory();
				movieCategory.setMovieCategoryId(rs.getInt(1));
				movieCategory.setMovieCategory(rs.getString(2));
				movieCatArrList.add(movieCategory);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pst.close();
			rs.close();
			pool.freeCon(con);
		}
		System.out.println("Movie Category array list size: "
				+ movieCatArrList.size());
		MovieCategory[] movieCatArr = new MovieCategory[movieCatArrList.size()];
		return movieCatArrList.toArray(movieCatArr);
	}

	public boolean returnedMovie(Movie[] movie, User user) throws SQLException {
		boolean state = false;
		java.util.Date today = new java.util.Date();
		java.sql.Date sqlToday = new java.sql.Date(today.getTime());
		ResultSet rs = null;
		System.out.println("User is returning an array movie");
		try {
			System.out.println("Preference Id check");
			pst = con
					.prepareStatement("select vlusr_prefernce from "
							+ constants.ConstantData.SCHEMA_NAME
							+ ".videolibusers where vlusr_id = ? and vlusr_rowstate != -1");
			pst.setInt(1, user.getId());
			rs = pst.executeQuery();
			while (rs.next()) {
				if (rs.getInt(1) == 1) {
					System.out.println("Preference Id == 1 Simple User");
					try {
						for (int i = 0; i < movie.length; i++) {
							pst = con
									.prepareStatement("insert into "
											+ constants.ConstantData.SCHEMA_NAME
											+ ".videolibsimpleusertransaction "
											+ "(vlsut_usrid,vlsut_rentedmovie , vlsut_moviereturned , vlsut_createddate , vlsut_rowstate)"
											+ " values (?,?,?,?,?)");
							pst.setInt(1, user.getId());
							pst.setInt(2, 0);
							pst.setInt(3, movie[i].getMovieId());
							pst.setDate(4, sqlToday);
							pst.setInt(5, 1);
							pst.executeUpdate();
							System.out
									.println("Cart Status changed from B to R for simple user");
							pst = con
									.prepareStatement("update "
											+ constants.ConstantData.SCHEMA_NAME
											+ ".videolibmoviecart set vlmt_cartstatus = ? , vlmt_lastupdateddate = now() "
											+ "where vlmt_id = ? and vlmt_rowstate != -1");
							pst.setString(1, "R");
							pst.setInt(
									2,
									getTransactionId(user.getId(),
											movie[i].getMovieId()));
							pst.executeUpdate();
							System.out
									.println("Change available copies to available + 1");
							pst = con
									.prepareStatement("update "
											+ constants.ConstantData.SCHEMA_NAME
											+ ".videolibmoviesmaster set "
											+ "vlmt_availablecopies = vlmt_availablecopies + 1 , vlmov_lastupdateddate = now() "
											+ "where vlmov_id = ? and vlmov_rowstate != -1");
							pst.setInt(1, movie[i].getMovieId());
							pst.executeUpdate();
							state = true;
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						pst.close();
						pool.freeCon(con);
					}
				}
				if (rs.getInt(1) == 2) {
					try {
						System.out.println("Preference Id == 2 Premium User");
						for (int i = 0; i < movie.length; i++) {
							pst = con
									.prepareStatement("insert into "
											+ constants.ConstantData.SCHEMA_NAME
											+ ".videolibpremiumusertransaction (vlput_usrid,vlput_rentedmovie , vlput_moviereturned , " +
											"vlput_createddate , vlput_rowstate) values (?,?,?,?,?)");
							pst.setInt(1, user.getId());
							pst.setInt(2, 0);
							pst.setInt(3, movie[i].getMovieId());
							pst.setDate(4, sqlToday);
							pst.setInt(5, 1);
							pst.executeUpdate();
							System.out
									.println("Cart Status changed from B to R for premium user");
							pst = con
									.prepareStatement("update "
											+ constants.ConstantData.SCHEMA_NAME
											+ ".videolibmoviecart set vlmt_cartstatus = ? , vlmt_lastupdateddate = now() where vlmt_id = ? and vlmt_rowstate != -1");
							pst.setString(1, "R");
							pst.setInt(
									2,
									getTransactionId(user.getId(),
											movie[i].getMovieId()));
							pst.executeUpdate();
							System.out
									.println("Change available copies to available + 1");
							pst = con
									.prepareStatement("update "
											+ constants.ConstantData.SCHEMA_NAME
											+ ".videolibmoviesmaster set vlmt_availablecopies = vlmt_availablecopies + 1 , vlmt_lastupdateddate = now()"
											+ " where vlmov_id = ? and vlmov_rowstate != -1");
							pst.setInt(1, movie[i].getMovieId());
							pst.executeUpdate();
							state = true;
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						pst.close();
						pool.freeCon(con);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return state;
	}

	/**
	 * 
	 * @param userid
	 * @param movieid
	 * @return
	 * @throws SQLException
	 */
	public int getTransactionId(int userid, int movieid) throws SQLException {
		System.out.println("Movie id:"+movieid +" and "+"user id:"+userid);
		int transactionId = 0;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = con
					.prepareStatement("select vlmt_id from "
							+ constants.ConstantData.SCHEMA_NAME
							+ ".videolibmoviecart where vlmt_movieid = ? and vlmt_userid = ? and vlmt_rowstate != -1");
			pst.setInt(1, movieid);
			pst.setInt(2, userid);
			rs = pst.executeQuery();
			while (rs.next()) {
				transactionId = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rs.close();
		}
		System.out.println("Trxn id to be marked R:"+transactionId);
		return transactionId;
	}

	/*
	 * Author: Sudheer Kumar Kethireddy This method returns the user object of a
	 * particular user with userId
	 */
	public User getUserObject(int userId) {
		System.out.println("Fetching the user obj for id:" + userId);
		User user = new User();
		Status status = new Status();
		Address address = new Address();
		Preference preference = new Preference();
		City city = new City();
		State state = new State();
		Country country = new Country();

		String query = " select "
				+ " user.vlusr_id, "
				+ " user.vlusr_salutation, "
				+ " user.vlusr_firstname, "
				+ " user.vlusr_middlename, "
				+ " user.vlusr_lastname, "
				+ " user.vlusr_displayname, "
				+ " user.vlusr_loginid, "
				+ " user.vlusr_password, "
				+ " user.vlusr_invalidlogins, "
				+ " user.vlusr_lastlogin, "
				+ " user.vlusr_dob, "
				+ " user.vlusr_sex, "
				+ " user.vlusr_emailid, "
				+ " user.vlusr_ssnid, "
				+ " user.vlusr_subscriptionfee, "
				+ " user.vlusr_subscriptionrenewal, "
				+ " address.vlusraddress_id, "
				+ " address.vlusraddress_addressline1, "
				+ " address.vlusraddress_addressline2, "
				+ " address.vlusraddress_addressline3, "
				+ " address.vlusraddress_telephonenumber, "
				+ " address.vlusraddress_zipcode, "
				+ " pref.vlusrtype_id, "
				+ " pref.vlusrtype_name, "
				+ " city.vlcm_cityid, "
				+ " city.vlcm_cityname, "
				+ " state.vls_stateid, "
				+ " state.vls_statename, "
				+ " country.vlc_countryid, "
				+ " country.vlc_countryname, "
				+ " status.vlstatus_id, "
				+ " status.vlstatus_name "
				+ " from "
				+ " videolibrary.videolibusers user, "
				+ " videolibrary.videolibstatus status, "
				+ " videolibrary.videolibuseraddressmaster address, "
				+ " videolibrary.videolibusertypemaster pref, "
				+ " videolibrary.videolibcitymaster city, "
				+ " videolibrary.videolibcountrymaster country, "
				+ " videolibrary.videolibstatemaster state "
				+ " where "
				+ " user.vlusr_id=? and user.vlusr_rowstate!=-1 "
				+ " and user.vlusr_addressid = address.vlusraddress_id "
				+ " and user.vlusr_prefernce = pref.vlusrtype_id "
				+ " and user.vlusr_statusid = status.vlstatus_id "
				+ " and address.vlusraddress_cityid = city.vlcm_cityid "
				+ " and address.vlusraddress_stateid = state.vls_stateid "
				+ " and address.vlusraddress_countryid = country.vlc_countryid; ";

		// String query =
		// "select * from videolibrary.videolibusers where vlusr_id=? and vlusr_rowstate!=-1";

		try {
			if (!con.isClosed()) {
				PreparedStatement pst = con.prepareStatement(query);
				pst.setInt(1, userId);
				rs = pst.executeQuery();
				while (rs.next()) {
					System.out.println("user returned from query!");
					user.setId(rs.getInt(1));
					user.setSalutation(rs.getString(2));
					user.setFirstName(rs.getString(3));
					user.setMiddleName(rs.getString(4));
					user.setLastName(rs.getString(5));
					user.setDisplayName(rs.getString(6));
					user.setLoginId(rs.getString(7));
					user.setPassword(rs.getString(8));
					user.setInvalidLogins(rs.getInt(9));
					user.setLastLogin(rs.getString(10));
					user.setDob(rs.getString(11));
					user.setSex(rs.getString(12));
					user.setEmailId(rs.getString(13));
					user.setSsnId(rs.getString(14));
					user.setSubscriptionFee(rs.getFloat(15));
					user.setSubscriptionRenewal(rs.getString(16));
					address.setAddressId(rs.getInt(17));
					address.setAddrLine1(rs.getString(18));
					address.setAddrLine2(rs.getString(19));
					address.setAddrLine3(rs.getString(20));
					address.setTelNo(rs.getString(21));
					address.setZipCode(rs.getString(22));
					preference.setPreferenceId(rs.getInt(23));
					preference.setPreferenceType(rs.getString(24));
					city.setCityId(rs.getInt(25));
					city.setCityName(rs.getString(26));
					state.setStateId(rs.getInt(27));
					state.setStateName(rs.getString(28));
					country.setCountryId(rs.getInt(29));
					country.setCountryName(rs.getString(30));
					status.setStatusId(rs.getInt(31));
					status.setStatusName(rs.getString(32));

					// user.setId(rt.getInt(1));
					// user.setSalutation(rt.getString(2));
					// user.setFirstName(rt.getString(3));
					// user.setMiddleName(rt.getString(4));
					// user.setLastName(rt.getString(5));
					// user.setDisplayName(rt.getString(6));
					// user.setDob(rt.getString(7));
					// user.setSex(rt.getString(8));
					// address.setAddressId(rt.getInt(9));
					// PreparedStatement pstmt2 = con
					// .prepareStatement("select * from videolibrary.videolibuseraddressmaster where vlusraddress_id =?");
					// pstmt2.setInt(1, rt.getInt(9));
					// ResultSet rt2 = pstmt2.executeQuery();
					// while (rt2.next()) {
					// address.setAddrLine1(rt2.getString(3));
					// address.setAddrLine2(rt2.getString(4));
					// address.setAddrLine3(rt2.getString(5));
					// address.setTelNo(rt2.getString(6));
					//
					// //
					// address.setZipCode(Integer.parseInt(rt.getString(10)));
					//
					// }
					// user.setAddress(address);
					// user.setLoginId(rt.getString(10));
					// user.setPassword(rt.getString(11));
					// user.setLastPwdUpdate(rt.getString(12));
					//
					// PreparedStatement pstmt3 = con
					// .prepareStatement("select vlstatus_name"
					// +
					// " from videolibrary.videolibstatus where vlstatus_id=?");
					// pstmt3.setInt(1, rt.getInt(13));
					// ResultSet rt3 = pstmt3.executeQuery();
					// while (rt3.next()) {
					// status.setStatusName(rt3.getString(1));
					// }
					// user.setStatus(status);
					// user.setPreference(null);
					// user.setEmailId(rt.getString(15));
					// user.setInvalidLogins(rt.getInt(16));
					// user.setLastLogin(String.valueOf(rt.getDate(17)));
					// user.setSsnId(rt.getString(18));
					//
				} /* this while will populate the user object */
			} /*
			 * end of if condition that checks whether connection is closed or
			 * not
			 */
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pool.freeCon(con);
		}
		address.setCity(city);
		address.setState(state);
		address.setCountry(country);
		user.setAddress(address);
		user.setPreference(preference);
		user.setStatus(status);
		System.out.println("Returnin user obj with id:" + user.getId()
				+ " and preference id : "
				+ user.getPreference().getPreferenceId());
		return user;

	} /* end of method for userGetObject */

	/*
	 * Author: Sudheer Kumar Kethireddy This method returns the list of movies
	 * bought by a user with particular userid
	 */
	public Movie[] getUserMoviesBought(int userId) {
		ArrayList<Movie> movies = new ArrayList<Movie>();
		try {
			if (!con.isClosed()) {
				PreparedStatement pstmt = con
						.prepareStatement("select vlmt_movieid from videolibrary.videolibmoviecart where vlmt_userid=? and vlmt_cartstatus = 'B' ");
				pstmt.setInt(1, userId);
				ResultSet rt = pstmt.executeQuery();
				while (rt.next()) {
					Movie movie = getMovieObject(rt.getInt(1));
					MovieCategory movieCategory = getMovieCategoryObject(movie
							.getMovieCategoryId());
					movie.setMovieCategory(movieCategory);
					movies.add(movie);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pool.freeCon(con);
		}
		/* converting array list to movie array */
		Movie[] movieArray = new Movie[movies.size()];
		movieArray = (Movie[]) movies.toArray(movieArray);
		return movieArray;
	}

	public Movie getMovieObject(int movieId) {
		Movie movie = new Movie();
		try {
			PreparedStatement pstmt2 = con
					.prepareStatement("select * from videolibrary.videolibmoviesmaster where vlmov_id = ? and vlmov_rowstate!=-1");
			pstmt2.setInt(1, movieId);
			ResultSet rt2 = pstmt2.executeQuery();

			while (rt2.next()) {
				movie.setMovieId(rt2.getInt(1));
				movie.setMovieName(rt2.getString(2));
				movie.setProdBanner(rt2.getString(3));
				movie.setMovieCategoryId(rt2.getInt(4));
				movie.setReleaseDate(rt2.getString(5));
				movie.setRentAmount(rt2.getFloat(6));
				movie.setTotalCopies(rt2.getInt(7));
				movie.setAvailableCopies(rt2.getInt(8));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return movie;
	}

	public MovieCategory getMovieCategoryObject(int movieCatId) {
		MovieCategory movieCat = new MovieCategory();
		try {
			PreparedStatement pstmt3 = con
					.prepareStatement("select * from videolibrary.VideoLibMoviecategory where vlmcat_id=?");
			pstmt3.setInt(1, movieCatId);
			ResultSet rt3 = pstmt3.executeQuery();
			while (rt3.next()) {
				movieCat.setMovieCategoryId(rt3.getInt(1));
				movieCat.setMovieCategory(rt3.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return movieCat;
	}

	/*
	 * Author: Sudheer Kumar KethiReddy This method searches for users
	 * satsifying a specific criteria
	 */
	public User[] userSearchCriteria(User user) throws SQLException {
		System.out.println("Creating the query for the User Search");
		String query = "select * from videolibrary.VideoLibUsers u, "
				+ "videolibrary.VideoLibuseraddressmaster a,videolibrary.videolibcitymaster c "
				+ "where u.vlusr_addressid = a.vlusraddress_id AND "
				+ "a.vlusraddress_cityid = c.vlcm_cityid AND u.vlusr_rowstate != -1 "
				+ "AND u.vlusr_prefernce !=3 AND", query1 = "";
		if (user.getAddress().getCity().getCityName() != null)
			query = query + " c.vlcm_cityname LIKE '%"
					+ user.getAddress().getCity().getCityName() + "%'  AND";
		if (user.getAddress().getZipCode().length() != 0)
			query = query + " a.vlusraddress_zipcode LIKE '%"
					+ user.getAddress().getZipCode() + "%'  AND";
		if (user.getDisplayName().length() != 0)
			query = query + " u.vlusr_displayname LIKE '%"
					+ user.getDisplayName() + "%'  AND";
		if (user.getPreferenceId() != 0)
			query = query + " u.vlusr_prefernce = " + user.getPreferenceId()
					+ " AND";
		if (user.getLoginId().length() != 0)
			query = query + " u.vlusr_loginid LIKE '%" + user.getLoginId()
					+ "%' AND";
		if (user.getEmailId().length() != 0)
			query = query + " u.vlusr_emailid LIKE '%" + user.getEmailId()
					+ "%' AND";

		if (query.endsWith("AND")) {
			System.out.println("true");
			query1 = query.substring(0, query.length() - 3);
			query1 = query1 + ";";
		}

		/* start-this if condition is when no criteria is entered */
		if (query.endsWith("where")) {
			query1 = query.substring(0, query.length() - 5);
			query1 = query1 + ";";
		}/* end-this if condition is when no criteria is entered */

		System.out.println("query formed =" + query1);

		ArrayList<User> users = new ArrayList<User>();
		try {
			if (!con.isClosed()) {
				pst = con.prepareStatement(query1);
				rs = pst.executeQuery();

				while (rs.next()) {

					User user1 = new User();
					user1.setId(rs.getInt(1));
					user1.setSalutation(rs.getString(2));
					user1.setFirstName(rs.getString(3));
					user1.setMiddleName(rs.getString(4));
					user1.setLastName(rs.getString(5));
					user1.setDisplayName(rs.getString(6));
					user1.setDob(rs.getString(7));
					user1.setSex(rs.getString(8));
					user1.setMembershipId(rs.getString(25));
					Address address = new Address();
					address.setAddressId(rs.getInt(9));
					PreparedStatement pst = con
							.prepareStatement("select * from videolibrary.videolibuseraddressmaster where vlusraddress_id =?");
					pst.setInt(1, rs.getInt(9));
					ResultSet rt2 = pst.executeQuery();
					while (rt2.next()) {
						address.setAddrLine1(rs.getString(3));
						address.setAddrLine2(rs.getString(4));
						address.setAddrLine3(rs.getString(5));
						address.setTelNo(rs.getString(6));

						// address.setZipCode(Integer.parseInt(rt2.getString(10)));

					}
					user1.setAddress(address);
					user1.setLoginId(rs.getString(10));
					user1.setPassword(rs.getString(11));
					user1.setLastPwdUpdate(rs.getString(12));
					user1.setStatusId(rs.getInt(13));
					user1.setPreferenceId(rs.getInt(14));
					user1.setEmailId(rs.getString(15));
					user1.setInvalidLogins(rs.getInt(16));
					user1.setLastLogin(String.valueOf(rs.getDate(17)));
					user1.setSsnId(rs.getString(18));
					users.add(user1);
				} /* end of while method for resultSet */

			} /* end of if condition to check whether connection is closed or not */
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pst.close();
			pool.freeCon(con);
		}

		/* converting array list to user array */

		User userArray[] = new User[users.size()];
		userArray = (User[]) users.toArray(userArray);
		return userArray;
	} /* end of method for user search criteria */

	public Movie adminInsertMovie(Movie movie) throws SQLException {
		MovieCategory movieCat = new MovieCategory();
		java.util.Date today = new java.util.Date();
		java.sql.Date sqlToday = new java.sql.Date(today.getTime());
		boolean insertFlag = false, error = false;
		String state = "";
		try {
			if (movie.getMovieCategory() != null)
				movie.getMovieCategory().setMovieCategoryId(
						getCategory(movie.getMovieCategory()));
			else {
				state = state.concat(" Invalid Category");
				error = true;
			}

			if (movie.getMovieName() != null) {
				System.out.println(movie.getMovieName());
				if (!Validation.validNameString(movie.getMovieName())) {
					System.out.println("Invalid Movie Name");
					state = state.concat(" Invalid Movie Name");
					error = true;
				}
			} else {
				state = state.concat(" Invalid Movie Name");
				error = true;
			}

			if (movie.getProdBanner() != null) {
				System.out.println(movie.getProdBanner());
				if (!Validation.validNameString(movie.getProdBanner())) {
					System.out.println("Invalid Production Name");
					state = state.concat(" Invalid Production Name");
					error = true;
				}
			} else {
				state = state.concat(" Invalid Production Name");
				error = true;
			}

			if (movie.getReleaseDate() != null) {
				System.out.println(movie.getReleaseDate());
				if (!Validation.validDateFormatString(movie.getReleaseDate())) {
					System.out
							.println("Invalid Release Date(Should be less than today's date)");
					state = state
							.concat(" Invalid Release Date(Should be less than today's date)");
					error = true;
				}
			} else {
				state = state.concat(" Invalid Release Date");
				error = true;
			}
			if (Float.toString(movie.getRentAmount()) != null) {
				System.out.println(movie.getRentAmount());
				if (!Validation.validDecimalFormatString(Float.toString(movie
						.getRentAmount()))) {
					System.out
							.println("Invalid Rent Amount (Two Decimal places)");
					state = state
							.concat(" Invalid Rent Amount (Two Decimal places)");
					error = true;
				}
			} else {
				state = state.concat(" Invalid Rent Amount");
				error = true;
			}
			if (Integer.toString(movie.getTotalCopies()) != null) {
				System.out.println(movie.getTotalCopies());
				if (!Validation.validNumericString(Integer.toString(movie
						.getTotalCopies()))) {
					System.out
							.println("Invalid Total Copies field can only be positive integer count");
					state = state
							.concat(" Invalid Total Copies field can only be positive integer count");
					error = true;
				}
			} else {
				state = state.concat(" Invalid Total Copies");
				error = true;
			}
			if (error) {
				movie.setErrorMsg(state);
			} else {
				if (!adminMovieAvailability(movie)) {
					movieCat.setMovieCategory(movie.getMovieCategory()
							.getMovieCategory());
					SimpleDateFormat formatter = new SimpleDateFormat(
							"dd/MM/yyyy");
					java.util.Date dt = formatter.parse(movie.getReleaseDate());
					java.sql.Date dte = new java.sql.Date(dt.getTime());
					dte = new java.sql.Date(dt.getTime());
					pst = con
							.prepareStatement(constants.ConstantData.INSERT_VIDEOLIBMOVIESMASTER);
					pst.setString(1, movie.getMovieName());
					pst.setString(2, movie.getProdBanner());
					pst.setInt(3, movie.getMovieCategory().getMovieCategoryId());
					pst.setDate(4, dte);
					pst.setFloat(5, movie.getRentAmount());
					pst.setInt(6, movie.getTotalCopies());
					pst.setInt(7, movie.getTotalCopies());
					pst.setDate(8, sqlToday);
					pst.setInt(9, 1);
					pst.executeUpdate();
					movie.setErrorMsg("Success");
				} else
					movie.setErrorMsg("Invalid Error Movie Already Exist");
			}
			System.out.println(insertFlag);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return movie;
	}

	public int getCategory(MovieCategory movieCat) throws SQLException {
		int catFlag = 0;
		PreparedStatement pst = null;
		java.util.Date today = new java.util.Date();
		java.sql.Date sqlToday = new java.sql.Date(today.getTime());
		ResultSet rs = null;
		try {
			pst = con
					.prepareStatement("select vlmcat_id from "
							+ constants.ConstantData.SCHEMA_NAME
							+ ".videolibmoviecategory where vlmcat_name = ? and vlmcat_rowstate != -1");
			pst.setString(1, movieCat.getMovieCategory());
			rs = pst.executeQuery();
			while (rs.next()) {
				movieCat.setMovieCategoryId(rs.getInt(1));
				catFlag = 1;
			}
			while (catFlag != 1) {
				pst = con
						.prepareStatement("insert into "
								+ constants.ConstantData.SCHEMA_NAME
								+ ".videolibmoviecategory (vlmcat_name , vlmcat_createddate , vlmcat_rowstate) values(?,?,?)");
				pst.setString(1, movieCat.getMovieCategory());
				pst.setDate(2, sqlToday);
				pst.setInt(3, 1);
				pst.executeUpdate();
				pst = con.prepareStatement("select MAX(vlmcat_id) from "
						+ constants.ConstantData.SCHEMA_NAME
						+ ".videolibmoviecategory");
				rs = pst.executeQuery();
				catFlag = 1;
				while (rs.next()) {
					movieCat.setMovieCategoryId(rs.getInt(1));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pst.close();
			rs.close();

		}
		return movieCat.getMovieCategoryId();
	}

	public Movie adminUpdateMovie(Movie movie) throws SQLException {
		boolean error = false;
		java.util.Date today = new java.util.Date();
		java.sql.Date sqlToday = new java.sql.Date(today.getTime());
		String state = "";
		// MovieCategory movieCat = new MovieCategory();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {

			if (movie.getMovieCategory().getMovieCategory() != null)
				movie.getMovieCategory().setMovieCategoryId(
						getCategory(movie.getMovieCategory()));
			else {
				state = state.concat(" Invalid Category");
				error = true;
			}

			if (movie.getMovieName() != null || movie.getMovieName() != "") {
				if (!Validation.validNameString(movie.getMovieName())) {
					System.out.println("Invalid Movie Name");
					state = state.concat(" Invalid Movie Name");
					error = true;
				}
			} else {
				state = state.concat(" Invalid Movie Name");
				error = true;
			}

			if (movie.getProdBanner() != null) {
				System.out.println(movie.getProdBanner());
				if (!Validation.validNameString(movie.getProdBanner())) {
					System.out.println("Invalid Production Name");
					state = state.concat(" Invalid Production Name");
					error = true;
				}
			} else {
				state = state.concat(" Invalid Production Name");
				error = true;
			}

			if (movie.getReleaseDate() != null) {
				System.out.println(movie.getReleaseDate());
				if (!Validation.validDateFormatString(movie.getReleaseDate())) {
					System.out
							.println("Invalid Release Date(Should be less than today's date)");
					state = state
							.concat(" Invalid Release Date(Should be less than today's date)");
					error = true;
				}
			} else {
				state = state.concat(" Invalid Release Date");
				error = true;
			}
			if (Float.toString(movie.getRentAmount()) != null) {
				System.out.println(movie.getRentAmount());
				if (!Validation.validDecimalFormatString(Float.toString(movie
						.getRentAmount()))) {
					System.out
							.println("Invalid Rent Amount (Two Decimal places)");
					state = state
							.concat(" Invalid Rent Amount (Two Decimal places)");
					error = true;
				}
			} else {
				state = state.concat(" Invalid Rent Amount");
				error = true;
			}
			if (Integer.toString(movie.getTotalCopies()) != null) {
				System.out.println(movie.getTotalCopies());
				if (!Validation.validNumericString(Integer.toString(movie
						.getTotalCopies()))) {
					System.out
							.println("Invalid Total Copies field can only be positive integer count");
					state = state
							.concat(" Invalid Total Copies field can only be positive integer count");
					error = true;
				}
			} else {
				state = state.concat(" Invalid Total Copies");
				error = true;
			}
			if (error) {
				movie.setErrorMsg(state);
			} else {
				pst = con
						.prepareStatement("select vlmov_totalcopies , vlmt_availablecopies from "
								+ constants.ConstantData.SCHEMA_NAME
								+ ".videolibmoviesmaster where vlmov_id = ? and vlmov_rowstate != -1");
				pst.setInt(1, movie.getMovieId());
				rs = pst.executeQuery();
				while (rs.next()) {
					if (rs.getInt(1) > movie.getTotalCopies()) {
						movie.setAvailableCopies(rs.getInt(2)
								- (rs.getInt(1) - movie.getTotalCopies()));
					} else if (rs.getInt(1) < movie.getTotalCopies()) {
						movie.setAvailableCopies(rs.getInt(2)
								+ (movie.getTotalCopies() - rs.getInt(1)));
					}
				}
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				java.util.Date dt = formatter.parse(movie.getReleaseDate());
				java.sql.Date dte = new java.sql.Date(dt.getTime());
				dte = new java.sql.Date(dt.getTime());
				pst = con
						.prepareStatement("update "
								+ constants.ConstantData.SCHEMA_NAME
								+ ".videolibmoviesmaster set vlmov_name = ? , vlmov_production  = ? , vlmov_categoryid = ? , vlmov_releasedate = ? , vlmov_rentamount = ? , vlmov_totalcopies = ? , vlmt_availablecopies = ? , vlmov_lastupdateddate = ? where vlmov_id = ?");
				pst.setString(1, movie.getMovieName());
				pst.setString(2, movie.getProdBanner());
				pst.setInt(3, movie.getMovieCategory().getMovieCategoryId());
				pst.setDate(4, dte);
				pst.setFloat(5, movie.getRentAmount());
				pst.setInt(6, movie.getTotalCopies());
				pst.setInt(7, movie.getAvailableCopies());
				pst.setDate(8, sqlToday);
				pst.setInt(9, getMovieID(movie));
				pst.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pst.close();
			con.close();
		}
		return movie;
	}

	public int getMovieID(Movie movie) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = con
					.prepareStatement("select vlmov_id from "
							+ constants.ConstantData.SCHEMA_NAME
							+ ".videolibmoviesmaster where vlmov_name = ? and vlmov_rowstate != -1");
			pst.setString(1, movie.getMovieName());
			rs = pst.executeQuery();
			while (rs.next()) {
				movie.setMovieId(rs.getInt(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pst.close();
			rs.close();
		}
		return movie.getMovieId();
	}

	public BillingHistory[] fetchBillingHistory(User user) throws SQLException {
		ArrayList<BillingHistory> displayBillingHistory = new ArrayList<BillingHistory>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = con
					.prepareStatement("select vlusr_prefernce from "
							+ constants.ConstantData.SCHEMA_NAME
							+ ".videolibusers where vlusr_id = ? and vlusr_rowstate != -1");
			pst.setInt(1, user.getId());
			rs = pst.executeQuery();
			while (rs.next()) {
				if (rs.getInt(1) == 1) {
					pst = con
							.prepareStatement("select mv.vlmov_name , smp.vlsut_createddate , smp.vlsut_amountpaid , "
									+ "(case when smp.vlsut_rentedmovie <= 0 THEN ? else ? end) as state from "
									+ constants.ConstantData.SCHEMA_NAME
									+ ".videolibsimpleusertransaction smp "
									+ "left outer join "
									+ constants.ConstantData.SCHEMA_NAME
									+ ".videolibmoviesmaster mv on "
									+ "(mv.vlmov_id = smp.vlsut_rentedmovie or mv.vlmov_id = vlsut_moviereturned) where mv.vlmov_rowstate != -1 and smp.vlsut_rowstate != -1 and smp.vlsut_usrid = ? ");
					pst.setString(1, "Returned");
					pst.setString(2, "Rented");
					pst.setInt(3, user.getId());
					rs = pst.executeQuery();
					while (rs.next()) {
						BillingHistory billHistory = new BillingHistory();
						billHistory.setMovieName(rs.getString(1));
						billHistory.setMovieAddedOn(rs.getString(2));
						billHistory.setMovieAmountPaid(rs.getFloat(3));
						billHistory.setMovieState(rs.getString(4));
						displayBillingHistory.add(billHistory);
					}
				} else if (rs.getInt(1) == 2) {
					pst = con
							.prepareStatement("select vlput_rentedmovie , mv.vlmov_name , vlput_moviereturned , vlput_createddate , vlput_totaldeduction ,"
									+ "(case when vlput_rentedmovie <= 0 THEN ? else ? end) as state "
									+ " from "
									+ constants.ConstantData.SCHEMA_NAME
									+ ".videolibpremiumusertransaction smp "
									+ "left outer join "
									+ constants.ConstantData.SCHEMA_NAME
									+ ".videolibmoviesmaster mv on (mv.vlmov_id = smp.vlput_rentedmovie or mv.vlmov_id = vlput_moviereturned) "
									+ "where mv.vlmov_rowstate != -1 and smp.vlput_rowstate != -1 and smp.vlput_usrid = ?");
					pst.setString(1, "Returned");
					pst.setString(2, "Rented");
					pst.setInt(3, user.getId());
					rs = pst.executeQuery();
					while (rs.next()) {
						BillingHistory billHistory = new BillingHistory();
						billHistory.setMovieName(rs.getString(1));
						billHistory.setMovieAddedOn(rs.getString(2));
						billHistory.setMovieAmountPaid(rs.getFloat(3));
						billHistory.setMovieState(rs.getString(4));
						displayBillingHistory.add(billHistory);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pst.close();
			rs.close();
			pool.freeCon(con);
		}

		BillingHistory historyArray[] = new BillingHistory[displayBillingHistory
				.size()];
		return displayBillingHistory.toArray(historyArray);
	}

	public boolean adminDeleteMovie(Movie movie) throws SQLException {
		boolean state = false;
		try {
			pst = con
					.prepareStatement("update "
							+ constants.ConstantData.SCHEMA_NAME
							+ ".videolibpremiumusertransaction set vlput_rowstate = -1 where (vlput_rentedmovie = ? or vlput_moviereturned = ?) and vlput_rowstate != -1");
			pst.setInt(1, movie.getMovieId());
			pst.setInt(2, movie.getMovieId());
			pst.executeUpdate();
			pst = con
					.prepareStatement("update "
							+ constants.ConstantData.SCHEMA_NAME
							+ ".videolibsimpleusertransaction set vlsut_rowstate = -1 where (vlsut_rentedmovie = ? or vlsut_moviereturned = ?) and vlsut_rowstate != -1");
			pst.setInt(1, movie.getMovieId());
			pst.setInt(2, movie.getMovieId());
			pst.executeUpdate();
			pst = con
					.prepareStatement("update "
							+ constants.ConstantData.SCHEMA_NAME
							+ ".videolibmoviecart set vlmt_rowstate = -1 where vlmt_movieid = ? and vlmt_rowstate != -1");
			pst.setInt(1, movie.getMovieId());
			pst.executeUpdate();
			pst = con
					.prepareStatement("update "
							+ constants.ConstantData.SCHEMA_NAME
							+ ".videolibmoviesmaster set vlmov_rowstate = -1 where vlmov_id = ? and vlmov_rowstate != -1");
			pst.setInt(1, movie.getMovieId());
			pst.executeUpdate();
			state = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pst.close();
			con.close();
		}
		return state;
	}

	/**
	 * 
	 * @param userID
	 * @return
	 * @throws SQLException
	 */
	public boolean deleteUser(int userID) throws SQLException {
		boolean state = false;
		Movie movie = new Movie();
		ArrayList<Movie> movieIdArrList = new ArrayList<Movie>();
		int preferenceid = 0;
		if (userID != 0) {
			try {
				System.out.println("Fetching the pref id for user to delete");
				String query = "select vlusr_prefernce from videolibrary.videolibusers where vlusr_id=? and vlusr_rowstate!=-1";
				pst = con.prepareStatement(query);
				pst.setInt(1, userID);
				rs = pst.executeQuery();
				while (rs.next()) {
					preferenceid = rs.getInt(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (preferenceid == 1) {
				System.out.println("deleting simple user from user trxn");
				try {
					String query = "update videolibrary.videolibsimpleusertransaction"
							+ " SET vlsut_rowstate = -1 where vlsut_usrid=? and vlsut_rowstate!=-1";
					pst = con.prepareStatement(query);
					pst.setInt(1, userID);
					pst.executeUpdate();
					System.out.println("Fetching movie ids for deleted user");

					pst = con
							.prepareStatement("select vlmt_movieid from "
									+ "videolibrary.videolibmoviecart where "
									+ "vlmt_cartstatus= ? and vlmt_userid= ? and vlmt_rowstate != -1");
					pst.setString(1, "B");
					pst.setInt(2, userID);
					System.out.println(pst);
					rs = pst.executeQuery();
					if (!rs.next()) {
						System.out
								.println("User has no bought movie.. deleting from master");
						String query6 = "update videolibrary.videolibusers SET vlusr_rowstate=-1 where vlusr_id=? and vlusr_rowstate!=-1 ";
						pst = con.prepareStatement(query6);
						System.out.println(pst);
						pst.setInt(1, userID);
						pst.executeUpdate();
						state = true;
					} else {
						System.out
								.println("User has bought movies.. Updating the avl copies");
						while (rs.next()) {
							movie.setMovieId(rs.getInt(1));
							movieIdArrList.add(movie);
						}

						Movie[] movieIdArr = new Movie[movieIdArrList.size()];
						movieIdArrList.toArray(movieIdArr);

						System.out.println("Updating cart status to R");

						String query1 = "update videolibrary.videolibmoviecart SET vlmt_cartstatus=? where vlmt_userid =? and vlmt_cartstatus=? and vlmt_rowstate!=-1";
						pst = con.prepareStatement(query1);
						pst.setString(1, "R");
						pst.setInt(2, userID);
						pst.setString(3, "B");
						pst.executeUpdate();

						System.out.println("Adding avl copies");
						for (int i = 0; i < movieIdArr.length; i++) {

							String query3 = "update videolibrary.videolibmoviesmaster vlmt_availablecopies=vlmt_availablecopies + 1 where vlmov_id=? and vlmov_rowstate!=-1";
							pst = con.prepareStatement(query3);
							pst.setInt(1, movieIdArr[i].getMovieId());
							pst.executeUpdate();
						}
						System.out.println("Finally deleting user frm master");
						System.out.println(userID);
						String query5 = "update videolibrary.videolibusers SET vlusr_rowstate=-1 where vlusr_id=? and vlusr_rowstate!=-1 ";
						pst = con.prepareStatement(query5);
						System.out.println(pst);
						pst.setInt(1, userID);
						pst.executeUpdate();
						state = true;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (preferenceid == 2) {
				try {
					String query = "update videolibrary.videolibpremiumusertransaction SET vlput_rowstate = -1 where vlput_usrid=? and vlput_rowstate!=-1";
					pst = con.prepareStatement(query);
					pst.setInt(1, userID);
					pst.executeUpdate();
					pst = con
							.prepareStatement("select vlmt_movieid from "
									+ "videolibrary.videolibmoviecart where "
									+ "vlmt_cartstatus= ? and vlmt_userid= ? and vlmt_rowstate != -1");
					pst.setString(1, "B");
					pst.setInt(2, userID);
					System.out.println(pst);
					rs = pst.executeQuery();
					if (!rs.next()) {
						String query6 = "update videolibrary.videolibusers SET vlusr_rowstate=-1 where vlusr_id=? and vlusr_rowstate!=-1 ";
						pst = con.prepareStatement(query6);
						System.out.println(pst);
						pst.setInt(1, userID);
						pst.executeUpdate();
						state = true;
					} else {
						while (rs.next()) {
							movie.setMovieId(rs.getInt(1));
							movieIdArrList.add(movie);
						}

						Movie[] movieIdArr = new Movie[movieIdArrList.size()];
						movieIdArrList.toArray(movieIdArr);
						String query1 = "update videolibrary.videolibmoviecart SET vlmt_cartstatus=? where vlmt_userid =? and vlmt_cartstatus=? and vlmt_rowstate!=-1";
						pst = con.prepareStatement(query1);
						pst.setString(1, "R");
						pst.setInt(2, userID);
						pst.setString(3, "B");
						pst.executeUpdate();

						for (int i = 0; i < movieIdArr.length; i++) {

							String query3 = "update videolibrary.videolibmoviesmaster vlmt_availablecopies=vlmt_availablecopies + 1 where vlmov_id=? and vlmov_rowstate!=-1";
							pst = con.prepareStatement(query3);
							pst.setInt(1, movieIdArr[i].getMovieId());
							pst.executeUpdate();
						}
						String query5 = "update videolibrary.videolibusers SET vlusr_rowstate=-1 where vlusr_id=? and vlusr_rowstate!=-1 ";
						pst = con.prepareStatement(query5);
						pst.setInt(1, userID);
						pst.executeUpdate();
						state = true;

					}
				} catch (SQLException e) {

					e.printStackTrace();
				} finally {
					pst.close();
					pool.freeCon(con);
				}
			}
		}
		return state;
	}

	public boolean adminMovieAvailability(Movie movie) throws SQLException {
		PreparedStatement pst2 = null;
		ResultSet rs = null;
		boolean avail = false;
		try {
			pst2 = con
					.prepareStatement("select vlmov_id from "
							+ constants.ConstantData.SCHEMA_NAME
							+ ".videolibmoviesmaster where vlmov_name = ? and vlmov_rowstate != -1");
			pst2.setString(1, movie.getMovieName());
			rs = pst2.executeQuery();
			while (rs.next()) {
				movie.setMovieId(rs.getInt(1));
				avail = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pst2.close();
			rs.close();
			// pool.freeCon(con);
		}
		return avail;
	}

	public int[] updateAvailableCopies(MovieCart[] movieCrt)
			throws SQLException {
		PreparedStatement pst[] = new PreparedStatement[movieCrt.length];
		ResultSet[] rs = new ResultSet[movieCrt.length];
		int i = 0;
		ArrayList<Integer> itemNotAvailable = new ArrayList<Integer>();
		int[] itemIdNotAvailable = null;
		try {
			while (i != movieCrt.length) {
				pst[i] = con
						.prepareStatement("select vlmt_availablecopies from "
								+ constants.ConstantData.SCHEMA_NAME
								+ ".videolibmoviesmaster where vlmov_id = ? and vlmov_rowstate != -1");
				pst[i].setInt(1, movieCrt[i].getMovieId());
				rs[i] = pst[i].executeQuery();
				i++;
				pst[i].close();
				System.out.println(rs[i].getInt("vlmt_avaiablecopies"));
				if (rs[i].getInt("vlmt_avaiablecopies") > 0) {
					PreparedStatement pt = con
							.prepareStatement("update "
									+ constants.ConstantData.SCHEMA_NAME
									+ ".videolibmoviesmaster set vlmt_availablecopies = vlmt_availablecopies-1 , " +
									"vlmov_lastupdateddate = now() where vlmov_id = ? and vlmov_rowstate != -1");
					pt.setInt(1, rs[i].getInt("vlmt_avaiablecopies"));
					pt.executeUpdate();
					pt.close();
				} else {
					itemNotAvailable.add(rs[i].getInt("vlmt_avaiablecopies"));
					rs[i].close();
				}
			}
			itemIdNotAvailable = new int[itemNotAvailable.size()];
			Iterator<Integer> iterator = itemNotAvailable.iterator();
			for (i = 0; i < itemIdNotAvailable.length; i++)
				itemIdNotAvailable[i] = iterator.next().intValue();
			itemIdNotAvailable[i] = 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeCon(con);
		}
		return itemIdNotAvailable;
	}

	public Movie userMovieView(Movie movie) throws SQLException {
		MovieCategory movieCat = new MovieCategory();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = con
					.prepareStatement("select mv.vlmov_name , mv.vlmov_production , mv.vlmov_releasedate , mvc.vlmcat_name , mv.vlmov_rentamount , "
							+ "mv.vlmov_totalcopies , mv.vlmt_availablecopies from "
							+ constants.ConstantData.SCHEMA_NAME
							+ ".videolibmoviesmaster mv left outer join "
							+ constants.ConstantData.SCHEMA_NAME
							+ ".videolibmoviecategory mvc on mv.vlmov_categoryid = mvc.vlmcat_id where vlmov_id = ? and vlmov_rowstate != -1 and vlmcat_rowstate != -1");
			pst.setInt(1, movie.getMovieId());
			rs = pst.executeQuery();
			while (rs.next()) {
				movie.setMovieName(rs.getString(1));
				movie.setProdBanner(rs.getString(2));
				movie.setReleaseDate(rs.getString(3));
				movieCat.setMovieCategory(rs.getString(4));
				movie.setMovieCategory(movieCat);
				movie.setRentAmount(rs.getFloat(5));
				movie.setTotalCopies(rs.getInt(6));
				movie.setAvailableCopies(rs.getInt(7));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pst.close();
			rs.close();
			pool.freeCon(con);
		}
		return movie;
	}

	public Movie[] testPagination(int offset, int noOfRecords) {
		ArrayList<Movie> movieArrayList = new ArrayList<Movie>();
		try {
			String query = "select SQL_CALC_FOUND_ROWS * from videolibrary.videolibmoviesmaster limit "
					+ offset + ", " + noOfRecords;
			pst = con.prepareStatement(query);
			ResultSet rs1 = pst.executeQuery();
			while (rs1.next()) {
				Movie movie = new Movie();
				movie.setMovieId(rs1.getInt(1));
				movieArrayList.add(movie);
			}
			rs1.close();
			System.out.println("Creating the second query");
			String query2 = "select count(1) from videolibrary.videolibmoviesmaster";
			System.out.println(query2);
			pst = con.prepareStatement(query2);
			System.out.println(pst);
			ResultSet rs2 = pst.executeQuery();
			System.out.println("zdda:" + rs2);
			if (rs2.next()) {
				System.out.println("Setting the total records as "
						+ rs2.getInt(1));
				this.totalNoOfRecords = rs2.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeCon(con);
		}
		System.out.println("creating movie array");
		Movie[] movieArray = new Movie[movieArrayList.size()];
		return movieArrayList.toArray(movieArray);
	}
}
