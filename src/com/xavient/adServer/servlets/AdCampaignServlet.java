package com.xavient.adServer.servlets;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xavient.adServer.dataObjects.AdCampaign;
import com.xavient.adServer.dataObjects.CommonDO;
import com.xavient.adServer.util.AdException;
import com.xavient.adServer.util.HttpUtil;

/**
 * Servlet implementation class AdCampaignServlet
 */

@WebServlet("/AdCampaignServlet/*")
public class AdCampaignServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	AdCampaignServletHelper adCampHelp;
    /**
     * Default constructor. 
     */
    public AdCampaignServlet() {
        // TODO Auto-generated constructor stub
    	adCampHelp = new AdCampaignServletHelper(); 
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String partnerID=null;
		if(request.getPathInfo()!=null)
			partnerID=request.getPathInfo().substring(1);
		
		String result =  adCampHelp.getCampaignData(partnerID);
		response.getWriter().append(result);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String responseString ="";
		try{
			String reqPayload = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			CommonDO dataObj = HttpUtil.getAdObject(reqPayload, new AdCampaign());
			AdCampaign ad;
			if(dataObj!=null){
				ad =(AdCampaign)dataObj;
				adCampHelp.saveCampaign(ad);
				responseString ="Ad Campaign Saved Successfully";

			}else{
				responseString ="Malformed Data";
			}
		}catch (AdException ae){
			responseString ="Operation not permitted "+ae.getMessage();
		}catch (Exception e){
			e.printStackTrace();
			response.setStatus(500);
			responseString ="Operation could not be completed "+e.getMessage();
		}

		response.getWriter().append(responseString);
	}

}
