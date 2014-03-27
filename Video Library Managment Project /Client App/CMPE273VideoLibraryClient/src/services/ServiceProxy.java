package services;

public class ServiceProxy implements services.Service {
  private String _endpoint = null;
  private services.Service service = null;
  
  public ServiceProxy() {
    _initServiceProxy();
  }
  
  public ServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initServiceProxy();
  }
  
  private void _initServiceProxy() {
    try {
      service = (new services.ServiceServiceLocator()).getService();
      if (service != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)service)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)service)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (service != null)
      ((javax.xml.rpc.Stub)service)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public services.Service getService() {
    if (service == null)
      _initServiceProxy();
    return service;
  }
  
  public model.User getUserObject(int userId) throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    return service.getUserObject(userId);
  }
  
  public model.User signIn(java.lang.String username, java.lang.String password) throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    return service.signIn(username, password);
  }
  
  public java.lang.String[] listState(java.lang.String country) throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    return service.listState(country);
  }
  
  public java.lang.String[] listCountry() throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    return service.listCountry();
  }
  
  public java.lang.String[] listCity(java.lang.String state) throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    return service.listCity(state);
  }
  
  public java.lang.String signUp(model.User user) throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    return service.signUp(user);
  }
  
  public model.User[] displayUsers(int preferenceId, int offset, int noOfRecords) throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    return service.displayUsers(preferenceId, offset, noOfRecords);
  }
  
  public model.Movie[] getLatestMovies() throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    return service.getLatestMovies();
  }
  
  public model.Movie[] displayMovies(java.lang.String filterAlphabet) throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    return service.displayMovies(filterAlphabet);
  }
  
  public model.MovieCart[] addToCart(model.MovieCart[] movieCart) throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    return service.addToCart(movieCart);
  }
  
  public model.MovieCart[] retrieveCart(int userId) throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    return service.retrieveCart(userId);
  }
  
  public model.Movie[] retrieveMovieDataForCart(model.MovieCart[] cartData) throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    return service.retrieveMovieDataForCart(cartData);
  }
  
  public void deleteFromCart(int userID, int movieID) throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    service.deleteFromCart(userID, movieID);
  }
  
  public boolean paymentGatewayCheck(model.User user) throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    return service.paymentGatewayCheck(user);
  }
  
  public void addBalance(int userID, float amountToAdd) throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    service.addBalance(userID, amountToAdd);
  }
  
  public void closeAllConn() throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    service.closeAllConn();
  }
  
  public model.Movie[] checkOutMovie(model.Movie[] movie, model.User user) throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    return service.checkOutMovie(movie, user);
  }
  
  public model.Movie[] movieCriteriaSearch(model.Movie movie) throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    return service.movieCriteriaSearch(movie);
  }
  
  public model.MovieCategory[] fetchMovieCategory() throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    return service.fetchMovieCategory();
  }
  
  public boolean returnedMovie(model.Movie[] movie, model.User user) throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    return service.returnedMovie(movie, user);
  }
  
  public model.Movie[] getUserMoviesBought(int userId) throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    return service.getUserMoviesBought(userId);
  }
  
  public model.User[] userSearchCriteria(model.User user) throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    return service.userSearchCriteria(user);
  }
  
  public boolean deleteUser(int userID) throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    return service.deleteUser(userID);
  }
  
  public model.Movie adminInsertMovie(model.Movie movie) throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    return service.adminInsertMovie(movie);
  }
  
  public model.Movie adminUpdateMovie(model.Movie movie) throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    return service.adminUpdateMovie(movie);
  }
  
  public model.BillingHistory[] fetchBillingHistory(model.User user) throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    return service.fetchBillingHistory(user);
  }
  
  public boolean adminDeleteMovie(model.Movie movie) throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    return service.adminDeleteMovie(movie);
  }
  
  public model.Movie userMovieView(model.Movie movie) throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    return service.userMovieView(movie);
  }
  
  public int[] updateAvailableCopies(model.MovieCart[] movieCrt) throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    return service.updateAvailableCopies(movieCrt);
  }
  
  public model.Movie[] testPagination(int offset, int noOfRecords) throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    return service.testPagination(offset, noOfRecords);
  }
  
  public int getTotalNoOfRecords() throws java.rmi.RemoteException{
    if (service == null)
      _initServiceProxy();
    return service.getTotalNoOfRecords();
  }
  
  
}