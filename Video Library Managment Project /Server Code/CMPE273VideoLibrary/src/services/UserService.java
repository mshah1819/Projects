package services;

import java.sql.SQLException;

import model.Movie;
import model.MovieCart;
import model.User;
import databaseConnection.DataAccessObject;

public class UserService {

	
	public MovieCart[] addToCart(MovieCart[] movieCart) throws SQLException {
		DataAccessObject dao = new DataAccessObject();
		return dao.addToCart(movieCart);
	}
	
	public MovieCart[] retrieveCart(int userId) throws SQLException {
		DataAccessObject dao = new DataAccessObject();
		return dao.retrieveCart(userId);
	}
		
	public Movie[] retrieveMovieDataForCart(MovieCart[] cartData) throws SQLException{
		DataAccessObject dao = new DataAccessObject();
		return dao.retrieveMovieDataForCart(cartData);
	}
	
	public void deleteFromCart(int userID, int movieID) throws SQLException {
		DataAccessObject dao = new DataAccessObject();
		dao.deleteFromCart(userID, movieID);
	}
			
	public boolean paymentGatewayCheck(User user){
		DataAccessObject dao = new DataAccessObject();
		return dao.paymentGatewayCheck(user);
	}
	
	public void addBalance(int userID, float amountToAdd) throws SQLException{
		DataAccessObject dao = new DataAccessObject();
		dao.addBalance(userID, amountToAdd);
	}
	
	public boolean returnedMovie(Movie[] movie, User user) throws SQLException {
		DataAccessObject dao = new DataAccessObject();
		return dao.returnedMovie(movie, user);
	}
	
	public User getUserObject(int userId) {
		DataAccessObject dao = new DataAccessObject();
		return dao.getUserObject(userId);
	}
	
	public Movie[] getUserMoviesBought(int userId) {
		DataAccessObject dao = new DataAccessObject();
		return dao.getUserMoviesBought(userId);
	}
}
