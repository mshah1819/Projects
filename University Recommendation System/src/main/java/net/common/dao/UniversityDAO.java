package net.common.dao;

import java.io.IOException;
import java.util.List;
import net.common.model.UniversityInfo;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

public interface UniversityDAO {

	static Logger log = Logger.getLogger(UniversityDAO.class.getName());	
	public List<UniversityInfo> getUniversityNameList();
	public List<UniversityInfo> getAutoSuggestUniversityNameList(String startsWith) throws JsonGenerationException, JsonMappingException, IOException;
}


//	@Autowired private SessionFactory sessionFactory;
//
//	@Transactional
//	public List<UniversityInfo> findAllUniversity(){
//		
//		Session session = sessionFactory.getCurrentSession();
//		List univ = session.createQuery(" from UniversityInfo").list();
//		return univ;
//	}
//	
//	@Transactional
//	public List<UniversityInfo> findUniversityById(int univId){
//		
//		Session session = sessionFactory.getCurrentSession();
//		System.out.println("University ID is "+univId);
//		List univ = session.createQuery(" from UniversityInfo where UnitId ='"+univId+"'").list();
//		return univ;
//	}
//	
//	@Transactional
//	public List<UniversityInfo> findUniversityByName(String instName){
//		
//		Session session = sessionFactory.getCurrentSession();
//		List univ = session.createQuery(" from UniversityInfo where Instnm ='"+instName+"'").list();
//		return univ;
//	}
//	
//	@Transactional
//	public List<UniversityInfo> findUniversityByCity(String city){
//		
//		Session session = sessionFactory.getCurrentSession();
//		List univ = session.createQuery(" from UniversityInfo where City  ='"+city+"'").list();
//		return univ;
//	}
//	
//	@Transactional
//	public List<UniversityInfo> findUniversityByState(String state){
//		
//		Session session = sessionFactory.getCurrentSession();
//		List univ = session.createQuery(" from UniversityInfo where Stabbr ='"+state+"'").list();
//		return univ;
//	}
//	
//	@Transactional
//	public List<UniversityInfo> findUniversityByZip(int zip){
//		
//		Session session = sessionFactory.getCurrentSession();
//		List univ = session.createQuery(" from UniversityInfo where Zip ='"+zip+"'").list();
//		return univ;
//	}
//	
//	@Transactional
//	public List<UniversityInfo> findUniversityByCountyName(String cntName){
//		
//		Session session = sessionFactory.getCurrentSession();
//		List univ = session.createQuery(" from UniversityInfo where Countynm ='"+cntName+"'").list();
//		return univ;
//	}
//	
//	// Get the customer list for the auto suggestion - In the search input text
//
//	@Override
//	public List<UniversityInfo> getAutoSuggestUniversityList(String startsWith) throws JsonGenerationException, JsonMappingException, IOException {
//		
//		log.info("Entry");
//		String searchWith = startsWith + "%";
//		log.debug("Customer360DAO IMPL input " + searchWith);
//		String customerTableSql = ServiceContants.CUSTOMERTABLE_SELECT_ON_AUTOSUGGEST;
//		List<CustomerTable> junipercustomerlist = (List<CustomerTable>)jdbcTemplate.query(customerTableSql, new Object[] {searchWith}, new CustomerTableRowMapper());
//		//JSONDisplay.ConvertJacksonToString(junipercustomerlist);
//		String jsonString = JSONDisplay.ConvertJacksonToString(junipercustomerlist);
//		if (jsonString.isEmpty())
//		{
//			log.debug("Result is empty");
//			return null;
//		}
//		log.info("Exit");
//		return junipercustomerlist; 
//	}	
//	
//	@Transactional
//	public List<UniversityInfo> NikeTshirtMen(){
//		
//		Session session = sessionFactory.getCurrentSession();
//		List univ = session.createQuery(" from UniversityInfo where City = 'nike' and category = 'men' ").list();
//		return univ;
//		
//	}
//	
//	@Transactional
//	public List<Tszhirt> findPumaTshirt(){
//		
//		Session session = sessionFactory.getCurrentSession();
//		List tshirts = session.createQuery(" from Tshirt where tenent_id = 'puma'").list();
//		return tshirts;
//		
//	}
//	
//	@Transactional
//	public List<Tshirt> findPumaTshirtImage(String imgPath){
//		
//		System.out.println("Inside DAO ImagePath is "+imgPath);
//		Session session = sessionFactory.getCurrentSession();
//		
//		
//		Query query = session.createQuery("from Tshirt where image=:imgPath");
//		query.setParameter("imgPath", imgPath);
//		List tshirts  = query.list();
////		List tshirts = session.createQuery(" from Tshirt where image=:imgPath").list();
//        System.out.println(" Inside list "+tshirts);
//		return tshirts;
//		
//	}
//		
//	@Transactional
//	public List<Tshirt> PumaTshirtMen(){
//		Session session = sessionFactory.getCurrentSession();
//		List tshirts = session.createQuery(" from Tshirt where tenent_id = 'puma' and category = 'men' ").list();
//		return tshirts;
//		
//	}	
//	
//	@Transactional
//	public List<Tshirt> PumaTshirtWomen(){
//		
//		Session session = sessionFactory.getCurrentSession();
//		List tshirts = session.createQuery(" from Tshirt where tenent_id = 'puma' and category = 'women' ").list();
//		return tshirts;
//		
//	}
//	
//	@Transactional
//	public List<Tshirt> findPumaWatch(){
//		
//		Session session = sessionFactory.getCurrentSession();
//		List watch = session.createQuery(" from PumaWatch ").list();
//		return watch;
//		
//	}
//	
//	@Transactional
//	public List<Tshirt> findPumaWatchImage(String imgPath){
//		
//		System.out.println("Inside DAO ImagePath is "+imgPath);
//		Session session = sessionFactory.getCurrentSession();
//		
//		
//		Query query = session.createQuery("from PumaWatch where image=:imgPath");
//		query.setParameter("imgPath", imgPath);
//		List watch  = query.list();
////		List tshirts = session.createQuery(" from Tshirt where image=:imgPath").list();
//        System.out.println(" Inside list "+watch);
//		return watch;
//		
//	}
//	
//	@Transactional
//	public int updatePumaTshirt(int qty,String pId){
//		Session session = sessionFactory.getCurrentSession();
//		Query query=session.createQuery("update Tshirt set quantity=quantity-"+qty+"where product_id='"+pId+"'");
//		int status = query.executeUpdate();
//		System.out.println("Status is "+status);
//		return status;
//		
//	}
//	
//	@Transactional
//	public int updatePumaWatch(int qty,String pId){
//		Session session = sessionFactory.getCurrentSession();
//		Query query=session.createQuery("update PumaWatch set quantity=quantity-"+qty+"where product_id='"+pId+"'");
//		int status = query.executeUpdate();
//		System.out.println("Status is "+status);
//		return status;
//		
//	}
//}
