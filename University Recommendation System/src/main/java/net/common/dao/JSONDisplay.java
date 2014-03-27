package net.common.dao;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
public class JSONDisplay {
	/**
	 * @param jacksonObject
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	@SuppressWarnings("deprecation")
	public static String ConvertJacksonToString(@SuppressWarnings("rawtypes") List jacksonObject) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		try {

			// convert user object to json string, and save to a file
			mapper.defaultPrettyPrintingWriter().writeValue(new File("/Users/mananshah/jsonlogfiles/jsonlog.json"), jacksonObject);

			// display to console
			System.out.println(mapper.defaultPrettyPrintingWriter().writeValueAsString(jacksonObject));
			
		} catch (JsonGenerationException e) {
			System.out.println("JsonGenerationException");
			e.printStackTrace();

		} catch (JsonMappingException e) {
			System.out.println("JsonMappingException");
			e.printStackTrace();

		} catch (IOException e) {
			System.out.println("IOException");
			e.printStackTrace();
		}
		return mapper.defaultPrettyPrintingWriter().writeValueAsString(jacksonObject);
	}
}
