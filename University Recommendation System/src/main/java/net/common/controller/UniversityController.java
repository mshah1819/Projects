package net.common.controller;

import java.util.ArrayList;
import java.util.List;

import net.common.model.UniversityInfo;
import net.common.controller.UniversityController;
import net.common.dao.JSONDisplay;
import net.common.dao.UniversityDAO;

import org.apache.log4j.Logger;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
//@RequestMapping("/university")
public class UniversityController {

	static Logger log = Logger.getLogger(UniversityController.class.getName());
	//private Class<Wrapper> Wrapper;
	private UniversityDAO universityDAO;
	@Autowired
	private RestTemplate rTemplate;
	/**
	 * @set the customerDAO
	 */
	@Autowired

	public UniversityController(UniversityDAO universityDAO) {
		this.universityDAO = universityDAO;
	}

	// Specific search of customer - Providing auto suggestion option	
	@RequestMapping(value = "/search.htm", method = RequestMethod.GET)  
	public  @ResponseBody List<UniversityInfo> searchUniversity(@RequestParam(value = "startsWith", defaultValue="", required=true) String startsWith)  {
		List<UniversityInfo> universityNameList = new ArrayList<UniversityInfo>(); 
		try 
		{
			if (startsWith == "")
			{
				System.out.println("no input from user");
				return null;
			}
			else
			{
				System.out.println("Keyboard input by user is = " + startsWith);
				universityNameList = universityDAO.getAutoSuggestUniversityNameList(startsWith);
				JSONDisplay.ConvertJacksonToString(universityNameList);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return universityNameList;
	}  	
}
//@Autowired private UniversityDAO univDAO;
//    @Autowired private UniversityDAO univDAO;      
//    @RequestMapping(value = "/")
//    public String home() {
// 	   return "home";
//    }
// 	
    
//    @RequestMapping(value = "/getUniversity", method = RequestMethod.GET, produces = "application/json")
//    public @ResponseBody List<UniversityInfo>  getUniv(Model model) 
//    {
//    	List<UniversityInfo> univList  = univDAO.findAllUniversity();
//    	model.addAttribute( univList);
//		return univList;
//    }
//    
//    
//
//     
//    @RequestMapping(value = "/getUniversityById", method = RequestMethod.GET, produces = "application/json")
//    public @ResponseBody List<UniversityInfo>  getUniversityById(@RequestParam("univ_id") int univ_id, Model model) 
//    {
//    	List<UniversityInfo> univList  = univDAO.findUniversityById(univ_id);
//    	model.addAttribute( univList);
//		return univList;
//    }
//	
//    
//    @RequestMapping(value = "/getUniversityByInstyName", method = RequestMethod.GET, produces = "application/json")
//    public @ResponseBody List<UniversityInfo>  getUniversityByInstyName(@RequestParam("inst_name") String inst_name, Model model) 
//    {
//    	List<UniversityInfo> univList  = univDAO.findUniversityByName(inst_name);
//    	model.addAttribute( univList);
//		return univList;
//    }
//    
//    
//    
//    @RequestMapping(value = "/getUniversityByCity", method = RequestMethod.GET, produces = "application/json")
//    public @ResponseBody List<UniversityInfo>  getUniversityByCity(@RequestParam("city") String city,Model model) 
//    {
//    	List<UniversityInfo> univList  = univDAO.findUniversityByCity(city);
//    	model.addAttribute( univList);
//		return univList;
//    }
//    
//    
//    
//    
//    @RequestMapping(value = "/getUniversityByState", method = RequestMethod.GET, produces = "application/json")
//    public @ResponseBody List<UniversityInfo>  getUniversityByState(@RequestParam("state") String state,Model model) 
//    {
//    	List<UniversityInfo> univList  = univDAO.findUniversityByState(state);
//    	model.addAttribute( univList);
//		return univList;
//    }
//    
//    
//    
//    @RequestMapping(value = "/getUniversityByZip", method = RequestMethod.GET, produces = "application/json")
//    public @ResponseBody List<UniversityInfo>  getUniversityByZip(@RequestParam("zip") int zip,Model model) 
//    {
//    	List<UniversityInfo> univList  = univDAO.findUniversityByZip(zip);
//    	model.addAttribute( univList);
//		return univList;
//    }
//    
//    
//    
//    
//    @RequestMapping(value = "/getUniversityByCountyName", method = RequestMethod.GET, produces = "application/json")
//    public @ResponseBody List<UniversityInfo>  getUniversityByCountyName(@RequestParam("county_name") String county_name,Model model) 
//    {
//    	List<UniversityInfo> univList  = univDAO.findUniversityByCountyName(county_name);
//    	model.addAttribute( univList);
//		return univList;
//    }
//    
//	// Specific search of customer - Providing auto suggestion option
//
//	@RequestMapping(value = "/search.htm", method = RequestMethod.GET)  
//	public  @ResponseBody List<UniversityInfo> getUniversityByInstyName(@RequestParam(value = "startsWith", defaultValue="", required=true) String startsWith)  {
//		List<UniversityInfo> universityNameList = new ArrayList<UniversityInfo>(); 
//		try 
//		{
//			if (startsWith == "")
//			{
//				System.out.println("no input from user");
//				return null;
//			}
//			else
//			{
//				System.out.println("Keyboard input by user is = " + startsWith);
//				universityNameList = UniversityDAO.getAutoSuggestUniversityList(startsWith);
//				JSONDisplay.ConvertJacksonToString(junipercustomerlist);
//			}
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//
//		return junipercustomerlist;
//	}     
//
//	
//    
////    
////    @RequestMapping(value = "/getUniv", method = RequestMethod.GET, produces = "application/json")
////    public String univ(Model model) 
////    {
////    	List<UniversityInfo> univInfo = univDAO.findAllUniversity();
//////		model.addAttribute(univInfo);
////    	System.out.println("University info "+univInfo.size());
////		model.addAttribute("univ", univInfo);
////		return "univ";
////    }
////    
////	
//}
