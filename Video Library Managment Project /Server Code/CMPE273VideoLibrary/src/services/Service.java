package services;

import java.sql.SQLException;

import model.BillingHistory;
import model.Movie;
import model.MovieCart;
import model.MovieCategory;
import model.User;
import databaseConnection.ConnectionPool;
import databaseConnection.DataAccessObject;

public class Service {
	
	public User signIn(String username, String password) throws SQLException {
		LoginLogoutService loginLogoutService = new LoginLogoutService();
		return loginLogoutService.signIn(username, password);  
	}
	
	
	public String[] listState(String country) throws SQLException {
		LoginLogoutService loginLogoutService = new LoginLogoutService();
		return loginLogoutService.listState(country);
	}
	
	public String[] listCountry() throws SQLException {
		LoginLogoutService loginLogoutService = new LoginLogoutService();
		return loginLogoutService.listCountry();
	}
		
	public String[] listCity(String state) throws SQLException {
		LoginLogoutService loginLogoutService = new LoginLogoutService();
		return loginLogoutService.listCity(state);
	}
	
	public String signUp(User user) throws SQLException {
		LoginLogoutService loginLogoutService = new LoginLogoutService();
		return loginLogoutService.signUp(user);
	}
	
	public User[] displayUsers(int preferenceId, int offset, int noOfRecords) throws SQLException {
		AdminService adminService = new AdminService();
		return adminService.displayUsers(preferenceId,offset,noOfRecords);
	}
	
	public Movie[] getLatestMovies() throws SQLException {
		MovieService movieService = new MovieService();
		return movieService.getLatestMovies();
	}

	public Movie[] displayMovies(String filterAlphabet) throws SQLException {
		MovieService movieService = new MovieService();
		return movieService.displayMovies(filterAlphabet);
	}
	
	public MovieCart[] addToCart(MovieCart[] movieCart) throws SQLException {
		UserService userService = new UserService();
		return userService.addToCart(movieCart);
	}

	public MovieCart[] retrieveCart(int userId) throws SQLException {
		UserService userService = new UserService();
		return userService.retrieveCart(userId);
	}
		
	public Movie[] retrieveMovieDataForCart(MovieCart[] cartData) throws SQLException{
		UserService userService = new UserService();
		return userService.retrieveMovieDataForCart(cartData);
	}
	
	public void deleteFromCart(int userID, int movieID) throws SQLException {
		UserService userService = new UserService();
		userService.deleteFromCart(userID, movieID);
	}
	
	public boolean paymentGatewayCheck(User user){
		UserService userService = new UserService();
		return userService.paymentGatewayCheck(user);
	}
	
	public void addBalance(int userID, float amountToAdd) throws SQLException{
		UserService userService = new UserService();
		userService.addBalance(userID, amountToAdd);
	}
	public void closeAllConn(){
		ConnectionPool.closeAllCon();
	}
	
	public Movie[] checkOutMovie(Movie[] movie, User user) throws SQLException {
		MovieService movieService = new MovieService();
		return movieService.checkOutMovie(movie, user);
	}
	
	public Movie[] movieCriteriaSearch(Movie movie) throws SQLException {
		AdminService adminService = new AdminService();
		return adminService.movieCriteriaSearch(movie);
	}
	
	public MovieCategory[] fetchMovieCategory() throws SQLException {
		MovieService movieService = new MovieService();
		return movieService.fetchMovieCategory();
	}
	
	public boolean returnedMovie(Movie[] movie, User user) throws SQLException {
		UserService userService = new UserService();
		return userService.returnedMovie(movie, user);
	}
	
	public User getUserObject(int userId) {
		UserService userService = new UserService();
		return userService.getUserObject(userId);
	}
	
	public Movie[] getUserMoviesBought(int userId) {
		UserService userService = new UserService();
		return userService.getUserMoviesBought(userId);
	}
	
	public User[] userSearchCriteria(User user) throws SQLException {
		AdminService adminService = new AdminService();
		return adminService.userSearchCriteria(user);
	}
	
	public boolean deleteUser(int userID) throws SQLException {
		AdminService adminService = new AdminService();
		return adminService.deleteUser(userID);
	}
	
	public Movie adminInsertMovie(Movie movie) throws SQLException {
		AdminService adminService = new AdminService();
		return adminService.adminInsertMovie(movie);
	}
	
	public Movie adminUpdateMovie(Movie movie) throws SQLException {
		AdminService adminService = new AdminService();
		return adminService.adminUpdateMovie(movie);
	}
	
	public BillingHistory[] fetchBillingHistory(User user) throws SQLException {
		AdminService adminService = new AdminService();
		return adminService.fetchBillingHistory(user);
	}
	
	public boolean adminDeleteMovie(Movie movie) throws SQLException {
		AdminService adminService = new AdminService();
		return adminService.adminDeleteMovie(movie);
	}
	
	public Movie userMovieView(Movie movie) throws SQLException {
		MovieService movieService = new MovieService();
		return movieService.userMovieView(movie);
	}
	
	public int[] updateAvailableCopies(MovieCart[] movieCrt)
			throws SQLException {
		MovieService movieService = new MovieService();
		return movieService.updateAvailableCopies(movieCrt);
	}
	
	public Movie[] testPagination(int offset, int noOfRecords) {
		DataAccessObject dao = new DataAccessObject();
		return dao.testPagination(offset, noOfRecords);
	}
	
	public int getTotalNoOfRecords() {
		return DataAccessObject.totalNoOfRecords;
	}
}
