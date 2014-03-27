package model;

public class PremiumUserTransaction {

	private int userId;
	private int rentedMovie;
	private int movieReturned;
	private float avlBalance;
	private float totalDeduction;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getRentedMovie() {
		return rentedMovie;
	}
	public void setRentedMovie(int rentedMovie) {
		this.rentedMovie = rentedMovie;
	}
	public int getMovieReturned() {
		return movieReturned;
	}
	public void setMovieReturned(int movieReturned) {
		this.movieReturned = movieReturned;
	}
	public float getAvlBalance() {
		return avlBalance;
	}
	public void setAvlBalance(float avlBalance) {
		this.avlBalance = avlBalance;
	}
	public float getTotalDeduction() {
		return totalDeduction;
	}
	public void setTotalDeduction(float totalDeduction) {
		this.totalDeduction = totalDeduction;
	}
	
		
		
}
