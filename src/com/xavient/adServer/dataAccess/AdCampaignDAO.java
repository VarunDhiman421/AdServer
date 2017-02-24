package com.xavient.adServer.dataAccess;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.xavient.adServer.dataObjects.AdCampaign;

public class AdCampaignDAO extends CommonDAO {

	public AdCampaignDAO() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean checkIfExists (String partnerID) throws IOException{
		File db = getDB();
		System.out.println(db.getAbsolutePath());
		if(db.length()==0)return false;
		ObjectMapper mapper = new ObjectMapper();
		Map<String, AdCampaign> allData = mapper.readValue(db,new TypeReference<Map<String, AdCampaign>>(){});
		if(allData.get(partnerID)!=null)
			return true;
		else
			return false;
	}
	
	public void addCampaignData (AdCampaign adCamp) throws JsonParseException, JsonMappingException, IOException{
		File db = getDB();
		Map<String, AdCampaign> allData = new HashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		if(db.length()!=0){
			allData = mapper.readValue(db,new TypeReference<Map<String, AdCampaign>>(){});
		}
		adCamp.setTimeStamp(System.currentTimeMillis());
		allData.put(adCamp.getPartner_id(), adCamp);
		mapper.writeValue(db, allData);
	}
	
	
	public AdCampaign getAdCampaign(String partnerID) throws IOException{
		File db = getDB();
		Map<String, AdCampaign> allData = new HashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		if(db.length()!=0){
			allData = mapper.readValue(db,new TypeReference<Map<String, AdCampaign>>(){});
		}
		
		return allData.get(partnerID);
		
	}
	
	

}
