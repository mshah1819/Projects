package services;

import static org.junit.Assert.*;

import java.sql.SQLException;

import model.BillingHistory;
import model.Movie;
import model.MovieCart;
import model.MovieCategory;
import model.Preference;
import model.User;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ServiceTest {

	private Service service;

	@Before
	public void setUp() throws Exception {
		service = new Service();
	}

	@Test
	public void testSignIn() throws SQLException {
		try{
		String username="prakashmanwani";
		String password="1qaz@WSX";
		User user = service.signIn(username, password);
		assertNotNull(user);
		assertTrue(user instanceof User);
		}catch(Exception e){
			System.out.println("Want a User object");
		}

	}
	
	@Test
	public void testListState() throws SQLException {
		String country="United States";
		String[] stateList=service.listState(country);
		assertEquals("California",stateList[1]);
	}

	@Test
	public void testListCountry() throws SQLException {
		String[] result = service.listCountry();
		assertEquals("United States",result[1]);
		
	}

	@Test
	public void testListCity() throws SQLException {
		String state="California";
		String[] result=service.listCity(state);
		assertEquals("San Francisco",result[1]);
	}

	@Test
	public void testSignUp() throws SQLException {
		User user = new User();
		user.setSalutation("Mr");
		user.setFirstName("Raj");
		user.setLastName("Patel");
		user.setDisplayName("Raj Patel");
		user.setDob("02/04/1990");
		user.setEmailId("rajpatel@yahoo.com");
		user.setPassword("P@sswo1");
		user.setSex("Male");
		user.getPreference().setPreferenceId(2);
		user.getAddress().setAddrLine1("201 S");
		user.getAddress().setAddrLine2("4th Street");
		user.getAddress().setAddrLine3("Apt# 203");
		user.getAddress().getCity().setCityName("San Jose");
		user.getAddress().getState().setStateName("California");
		user.getAddress().getCountry().setCountryName("United States");
		user.getAddress().setTelNo("4087965231");
		user.getAddress().setZipCode("95112");
		user.setSsnId("123-45-6789");
		user.setSubscriptionFee(10);
		String result=service.signUp(user);
		assertEquals("true",result);	
	}

	@Test
	public void testDisplayUsers() throws SQLException {
		
		User[] user =service.displayUsers(1, 1, 1);
		assertNotNull(user);
		assertTrue(user instanceof User[]);
	}

	@Test
	public void testGetLatestMovies() throws SQLException {
		Movie[] movie;
		
		movie = service.getLatestMovies();
		assertEquals("DieHard 4.0",movie[0].getMovieName());
	}

	@Test
	public void testDisplayMovies() throws SQLException {
		
		String filterAlphabet = "S";
		Movie[] movie;
		movie=service.displayMovies(filterAlphabet);
		assertEquals("SKYFALL",movie[0].getMovieName());
	}

	@Test
	public void testRetrieveCart() throws SQLException {
		int userId = 1;
		MovieCart[] movieCart=service.retrieveCart(userId);
		assertNotNull(movieCart);
		assertTrue(movieCart instanceof MovieCart[]);
	}

	@Test
	public void testRetrieveMovieDataForCart() throws SQLException {
		
		MovieCart[] movieCart = null;
		movieCart[2].setMovieId(453);
		Movie[] movie=service.retrieveMovieDataForCart(movieCart);
		assertNotNull(movie);
		assertTrue(movie instanceof Movie[]);
	}

	@Test
	public void testDeleteFromCart() throws SQLException {
		int userID=1;
		int movieID=1;
		service.deleteFromCart(userID, movieID);
	}
		

	@Test
	public void testPaymentGatewayCheck() {
		User user = new User();
		boolean result=service.paymentGatewayCheck(user);
		assertEquals(true,result);
	}

	@Test
	public void testAddBalance() throws SQLException {
		int userID = 2;
		float amountToAdd = (float) 10.50;
		service.addBalance(userID, amountToAdd);
		
	}

	@Test
	public void testCloseAllConn() {
		service.closeAllConn();
	}

	@Test
	public void testCheckOutMovie() throws SQLException {
		Movie[] movie = null;
		User user = null;
		movie[1].setMovieId(2);
		user.setId(1);
		Movie[] movie1=service.checkOutMovie(movie, user);
		assertNotNull(movie1);
		assertTrue(movie1 instanceof Movie[]);
	}

	@Test
	public void testMovieCriteriaSearch() throws SQLException {
		Movie movie = null;
		movie.setMovieName("SKYFALL");
		movie.getMovieCategory().setMovieCategoryId(1);
		Movie[] movie1=service.movieCriteriaSearch(movie);
		assertNotNull(movie1);
		assertTrue(movie1 instanceof Movie[]);
	}

	@Test
	public void testFetchMovieCategory() throws SQLException {
		MovieCategory[] mc=service.fetchMovieCategory();
		assertNotNull(mc);
		assertTrue(mc instanceof MovieCategory[]);
	}

	@Test
	public void testReturnedMovie() throws SQLException {
		Movie[] movie = null;
		User user = null;
		movie[1].setMovieId(1);
		user.setId(1);
		boolean result=service.returnedMovie(movie, user);
		assertEquals(true,result);
	}

	@Test
	public void testGetUserObject() {
		int userId=1;
		User user=service.getUserObject(userId);
		assertNotNull(user);
		assertTrue(user instanceof User);
		}

	@Test
	public void testGetUserMoviesBought() {
		int userId=1;
		Movie[] movie=service.getUserMoviesBought(userId);
		assertEquals(1,movie[0].getMovieId());
	}

	@Test
	public void testUserSearchCriteria() throws SQLException {
		User user=null;
		user.setLoginId("prakashmanwani");
		user.setDisplayName("Prakash Manwani");
		user.setEmailId("manwaniprakash@yahoo.co.in");
		User[] user1 = service.userSearchCriteria(user);
		assertEquals("Prakash",user1[1].getFirstName());
	}

	@Test
	public void testDeleteUser() throws SQLException {
		int userId =2;
		boolean result=service.deleteUser(userId);
		assertEquals(false,result);
	}


	@Test
	public void testAdminUpdateMovie() throws SQLException {
		int movieId=7;
		Movie movie = null;
		movie.setMovieId(movieId);
		Movie movie1=service.adminUpdateMovie(movie);
		assertEquals("Jab Tak Hai Jaan",movie1.getMovieName());
		
	}

	@Test
	public void testFetchBillingHistory() throws SQLException {
		User user = null;
		user.setId(1);
		BillingHistory[] bs = service.fetchBillingHistory(user);
		assertNotNull(bs);
		assertTrue(bs instanceof BillingHistory[]);
	}

	@Test
	public void testAdminDeleteMovie() throws SQLException {
		Movie movie = null;
		movie.setMovieId(10);
		boolean result=service.adminDeleteMovie(movie);
		assertEquals(true,result);
	}

	@Test
	public void testUserMovieView() throws SQLException {
		Movie movie = null;
		movie.setMovieId(1);
		Movie movie1=service.userMovieView(movie);
		assertNotNull(movie1);
		assertTrue(movie1 instanceof Movie);
	}

}