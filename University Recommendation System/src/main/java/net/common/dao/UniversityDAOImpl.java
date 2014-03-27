package net.common.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.common.model.UniversityInfo;
import net.common.model.rowmapper.UniversityInfoRowMapper;
import net.common.dao.UniversityDAOImpl;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class UniversityDAOImpl implements UniversityDAO {

	static Logger log = Logger.getLogger(UniversityDAOImpl.class.getName());
	
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private RestTemplate rTemplate;
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	// Get University Name List //	
	public List<UniversityInfo> getUniversityNameList()
	{
		log.info("Entry");
		String universityInfoSql = ServiceContants.UNIVERSITYTABLE_SELECT_ALL;
		List<UniversityInfo> universityNamelist = (List<UniversityInfo>)jdbcTemplate.query(universityInfoSql, new UniversityInfoRowMapper());
		//JSONDisplay.ConvertJacksonToString(junipercustomerlist);
		log.info("Exit");
		return universityNamelist; 
	}

	// Get all the information of organization and employees //

	// Get the university list for the auto suggestion - In the search input text
	@Override
	public List<UniversityInfo> getAutoSuggestUniversityNameList(String startsWith) throws JsonGenerationException, JsonMappingException, IOException {
		
		log.info("Entry");
		String searchWith = startsWith + "%";
		log.debug("UniversityDAO IMPL input " + searchWith);
		String universityInfoSql = ServiceContants.UNIVERSITYTABLE_SELECT_ON_AUTOSUGGEST;
		List<UniversityInfo> universityNameList = (List<UniversityInfo>)jdbcTemplate.query(universityInfoSql, new Object[] {searchWith}, new UniversityInfoRowMapper());
		//JSONDisplay.ConvertJacksonToString(junipercustomerlist);
		String jsonString = JSONDisplay.ConvertJacksonToString(universityNameList);
		if (jsonString.isEmpty())
		{
			log.debug("Result is empty");
			return null;
		}
		log.info("Exit");
		return universityNameList; 
	}
}