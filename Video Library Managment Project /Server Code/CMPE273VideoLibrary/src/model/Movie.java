package model;

public class Movie {
	private int movieId;
	private String movieName;
	private String prodBanner;
	private int movieCategoryId;
	private MovieCategory movieCategory;
	private String releaseDate;
	private float rentAmount;
	private int totalCopies;
	private int availableCopies;
	private String errorMsg;
	
	public int getMovieId() {
		return movieId;
	}
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public String getProdBanner() {
		return prodBanner;
	}
	public void setProdBanner(String prodBanner) {
		this.prodBanner = prodBanner;
	}
	public MovieCategory getMovieCategory() {
		return movieCategory;
	}
	public void setMovieCategory(MovieCategory movieCategory) {
		this.movieCategory = movieCategory;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	public float getRentAmount() {
		return rentAmount;
	}
	public void setRentAmount(float rentAmount) {
		this.rentAmount = rentAmount;
	}
	public int getTotalCopies() {
		return totalCopies;
	}
	public void setTotalCopies(int totalCopies) {
		this.totalCopies = totalCopies;
	}
	public int getAvailableCopies() {
		return availableCopies;
	}
	public void setAvailableCopies(int availableCopies) {
		this.availableCopies = availableCopies;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public int getMovieCategoryId() {
		return movieCategoryId;
	}
	public void setMovieCategoryId(int movieCategoryId) {
		this.movieCategoryId = movieCategoryId;
	}
	
}
