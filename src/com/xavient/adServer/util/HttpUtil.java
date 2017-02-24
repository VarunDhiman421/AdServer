package com.xavient.adServer.util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSessionContext;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.xavient.adServer.dataObjects.AdCampaign;
import com.xavient.adServer.dataObjects.CommonDO;

public class HttpUtil {
	
	public static CommonDO getAdObject (String jsonStr, CommonDO dataObj){
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			dataObj =mapper.readValue(jsonStr, dataObj.getClass());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return dataObj;

	}
	
	public static String getAdString (AdCampaign adCamp) throws JsonGenerationException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(adCamp);
	}
	
	
	
	/*
	public static void main (String [] a){
		String s ="{ \"partner_id\": \"unique_string_representing_partner\", \"duration\": \"45\", \"ad_content\": \"string_of_content_to_display_as_ad\"}";
		CommonDO dot = getAdObject(s, new AdCampaign());
		AdCampaign ad =(AdCampaign)dot;
		System.out.println(ad.getAd_content());
	}*/

}
