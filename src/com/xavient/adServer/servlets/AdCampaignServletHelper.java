package com.xavient.adServer.servlets;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;

import com.xavient.adServer.dataAccess.AdCampaignDAO;
import com.xavient.adServer.dataObjects.AdCampaign;
import com.xavient.adServer.util.AdException;
import com.xavient.adServer.util.HttpUtil;

public class AdCampaignServletHelper {
	
	public void saveCampaign (AdCampaign adCamp) throws IOException{
		
		AdCampaignDAO adDao = new AdCampaignDAO();
		
		if(adDao.checkIfExists(adCamp.getPartner_id()))
			throw new AdException("This partner already has a live campaign");
		
		adDao.addCampaignData(adCamp);
		
	}
	
	public String getCampaignData (String partnerID) throws IOException{
		
		AdCampaignDAO adDao = new AdCampaignDAO();
		AdCampaign adCamp = adDao.getAdCampaign(partnerID);
		String errorMsg= "";
		if(adCamp==null)
			errorMsg="No campaign exists for this Partner";
		else if(adCamp.getTimeStamp()+TimeUnit.DAYS.toMillis(adCamp.getDuration()) < System.currentTimeMillis()){
			errorMsg="No Active campaign exists for this Partner";
			adCamp=null;
		}
		
		JSONObject jo = new JSONObject();
		jo.put("result", errorMsg);
		jo.put("campaign", HttpUtil.getAdString(adCamp));
		return jo.toJSONString();
	}
		
}
