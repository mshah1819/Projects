package model;

public class SimpleUserTransaction {

	private int userId;
	private int rentedMovie;
	private int movieReturned;
	private float payableAmt;
	private float amountPaid;

	
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
	public float getPayableAmt() {
		return payableAmt;
	}
	public void setPayableAmt(float payableAmt) {
		this.payableAmt = payableAmt;
	}
	public float getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(float amountPaid) {
		this.amountPaid = amountPaid;
	}
	
		
		
}
