package model;

public class BillingHistory {
	private String movieName;
	private String movieState;
	private String movieAddedOn;	
	private float movieAmountPaid;
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public String getMovieState() {
		return movieState;
	}
	public void setMovieState(String movieState) {
		this.movieState = movieState;
	}
	public String getMovieAddedOn() {
		return movieAddedOn;
	}
	public void setMovieAddedOn(String movieAddedOn) {
		this.movieAddedOn = movieAddedOn;
	}
	public float getMovieAmountPaid() {
		return movieAmountPaid;
	}
	public void setMovieAmountPaid(float movieAmountPaid) {
		this.movieAmountPaid = movieAmountPaid;
	}
	}
