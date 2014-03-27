package services;

import java.sql.SQLException;
import java.util.ArrayList;

import model.Movie;
import model.MovieCart;
import model.MovieCategory;
import model.User;
import databaseConnection.DataAccessObject;

public class MovieService {
	
	public Movie[] getLatestMovies() throws SQLException {
		DataAccessObject dao = new DataAccessObject();
		ArrayList<Movie> latestMovieDetails = new ArrayList<Movie>();
		latestMovieDetails = dao.getLatestMovies(latestMovieDetails);
		Movie moviesArray[] = new Movie[latestMovieDetails.size()];
		System.out.println("Latest movies array size=" + latestMovieDetails.size());
		return latestMovieDetails.toArray(moviesArray);
	}

	public Movie[] displayMovies(String filterAlphabet) throws SQLException {
		DataAccessObject dao = new DataAccessObject();
		ArrayList<Movie> displayMovieDetails = dao
				.displayMovies(filterAlphabet);
		System.out.println("Size of the Movie array by filter alphabet::"
				+ displayMovieDetails.size());
		Movie moviesArray[] = new Movie[displayMovieDetails.size()];
		return displayMovieDetails.toArray(moviesArray);
	}

	public Movie[] checkOutMovie(Movie[] movie, User user) throws SQLException {
		DataAccessObject dao = new DataAccessObject();
		return dao.checkOutMovie(movie, user);
	}
	
	public MovieCategory[] fetchMovieCategory() throws SQLException {
		DataAccessObject dao = new DataAccessObject();
		return dao.fetchMovieCategory();
	}
	
	public Movie userMovieView(Movie movie) throws SQLException {
		DataAccessObject dao = new DataAccessObject();
		return dao.userMovieView(movie);
	}
	
	public int[] updateAvailableCopies(MovieCart[] movieCrt)
			throws SQLException {
		DataAccessObject dao = new DataAccessObject();
		return dao.updateAvailableCopies(movieCrt);
	}
	
		
}
