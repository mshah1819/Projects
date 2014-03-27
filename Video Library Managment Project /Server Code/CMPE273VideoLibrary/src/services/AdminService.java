package services;

import java.sql.SQLException;

import model.BillingHistory;
import model.Movie;
import model.User;
import databaseConnection.DataAccessObject;

public class AdminService {

	public User[] displayUsers(int preferenceId, int offset, int noOfRecords)
			throws SQLException {
		DataAccessObject dao = new DataAccessObject();
		return dao.displayUsers(preferenceId, offset, noOfRecords);
	}

	public Movie[] movieCriteriaSearch(Movie movie) throws SQLException {
		DataAccessObject dao = new DataAccessObject();
		return dao.movieCriteriaSearch(movie);
	}

	public User[] userSearchCriteria(User user) throws SQLException {
		DataAccessObject dao = new DataAccessObject();
		return dao.userSearchCriteria(user);
	}

	public boolean deleteUser(int userID) throws SQLException {
		DataAccessObject dao = new DataAccessObject();
		return dao.deleteUser(userID);
	}

	public Movie adminInsertMovie(Movie movie) throws SQLException {
		DataAccessObject dao = new DataAccessObject();
		return dao.adminInsertMovie(movie);
	}
	
	public Movie adminUpdateMovie(Movie movie) throws SQLException {
		DataAccessObject dao = new DataAccessObject();
		return dao.adminUpdateMovie(movie);
	}
	
	public boolean adminDeleteMovie(Movie movie) throws SQLException {
		DataAccessObject dao = new DataAccessObject();
		return dao.adminDeleteMovie(movie);
	}
	
	public BillingHistory[] fetchBillingHistory(User user) throws SQLException {
		DataAccessObject dao = new DataAccessObject();
		return dao.fetchBillingHistory(user);
	}
	
		

}
