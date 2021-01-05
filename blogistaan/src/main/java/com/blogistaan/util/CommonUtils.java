package com.blogistaan.util;

import java.io.File;
import java.io.IOException;

//import org.codehaus.jackson.JsonGenerationException;
//import org.codehaus.jackson.map.JsonMappingException;
//import org.codehaus.jackson.map.ObjectMapper;
//
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import com.blogistaan.config.BlogConfig;
import com.blogistaan.config.WebConfig;
import com.blogistaan.entity.User;
import com.blogistaan.service.HomeService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.Year;
import java.time.YearMonth;

import lombok.ToString;

@ToString
public class CommonUtils {
	
	

//	public static <T> JSONObject ConvertObjectToJSON(T obj) {
//		ObjectMapper mapper = new ObjectMapper();
//		String jsonInString = null;
//		try {
//			jsonInString = mapper.writeValueAsString(obj);
//		} catch (JsonGenerationException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (JsonMappingException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		JSONParser parser = new JSONParser();
//		JSONObject json = null;
//		try {
//			json = (JSONObject) parser.parse(jsonInString);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return json;
//
//	}
	
	public static void createDirectoryIfItDoesntExist(String dir) {
		final Path path = Paths.get(dir);
		
		if (Files.notExists(path)) {
			try {
				Files.createDirectories(path);
			} catch (IOException ie) {
			    ie.printStackTrace();
			}
		} 
	}
	
	
	public static Path getImagePath(String fileName) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append(WebConfig.IMAGE_FILE_BASE);
		sb.append(Year.now().getValue());
		
		createDirectoryIfItDoesntExist(sb.toString());
		
		sb.append("/");
		sb.append(YearMonth.now().getMonthValue());
		
		createDirectoryIfItDoesntExist(sb.toString());
		
//		File saveFile = new ClassPathResource(sb.toString()).getFile();
//		System.out.println("File Absolute Path: " + saveFile.getAbsolutePath());
//		Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+fileName);
		
//		return path;
		sb.append("/");
		sb.append(fileName);
		
		return Paths.get(sb.toString());
	}
	

	public static String getImageUrl(String imageFileName) {
	    String baseUrl = WebConfig.BASE_URL;
        StringBuilder sb = new StringBuilder();
        
        System.out.println(sb.append(baseUrl));
        
        if (baseUrl.endsWith("/")) {
            baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
            System.out.println("base Url" + baseUrl);
        }
        
        sb.append(WebConfig.IMAGE_RESOURCE_BASE);
        sb.append(getYearAndMonthUrlFragment());
        sb.append(imageFileName);
        
        return sb.toString();
    }

	
	private static String getYearAndMonthUrlFragment() {
        StringBuilder sb = new StringBuilder();
        sb.append(Year.now().getValue());
        sb.append("/");
        sb.append(YearMonth.now().getMonthValue());
        sb.append("/");
    
        return sb.toString();
	}

	public static Path getUserProfileImagePath(String fileName, User user) {
		StringBuilder sb = new StringBuilder();
		sb.append(WebConfig.IMAGE_FILE_BASE);
		sb.append(user.getId());
		
		createDirectoryIfItDoesntExist(sb.toString());
		
		sb.append("/");
//		sb.append(YearMonth.now().getMonthValue());
		
//		createDirectoryIfItDoesntExist(sb.toString());
		
//		File saveFile = new ClassPathResource(sb.toString()).getFile();
//		System.out.println("File Absolute Path: " + saveFile.getAbsolutePath());
//		Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+fileName);
		
//		return path;
//		sb.append("/");
		sb.append(fileName);
		
		return Paths.get(sb.toString());
	}
	
}
