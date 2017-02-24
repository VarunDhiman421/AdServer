package test;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AdCampTest {
	
	 HttpURLConnection connection = null;
  private static int testCaseCounter =0;
  
  @BeforeClass
  public static void preRequisite(){
	  File db = new File("AdDatabase");
	  if(db.exists())
		if( ! db.delete())
			fail("Setup Failed");
  }
	
	public void setup(String strurl, String method) {
		
		try{
		 URL url = new URL(strurl);
		    connection = (HttpURLConnection) url.openConnection();
		    connection.setRequestMethod(method);
		    connection.setRequestProperty("Content-Type", 
		        "application/x-www-form-urlencoded");
		    connection.setRequestProperty("Content-Language", "en-US");  

		    connection.setUseCaches(false);
		    connection.setDoOutput(true);
		}catch(Exception e){
			fail("Test Setup failed");
		}
		
	}
	@Before
	public void createConnection(){
		testCaseCounter++;
		System.out.println("TEST NUMBER "+testCaseCounter);
		String url = "http://localhost:8080/AdCampaignServlet/";
		String method="POST";
		if(testCaseCounter==4){
			url = "http://localhost:8080/AdCampaignServlet/Synechron";
			method="GET";
		}
		if(testCaseCounter==5){
			url = "http://localhost:8080/AdCampaignServlet/Honeywell";
			method="GET";
		}
		setup(url,method);
	}
	
	
	@Test
	public void validateCreateRequestFormat() {
		String payload = "{\"partner_id\":\"Synechron\",\"duration\":abc,\"ad_content\":\"Yo yo Honey singh\"}";
		try {
			String response = getResponse(payload);
			System.out.println(response.trim());
			if(!"Malformed Data".equals(response.trim()))
				fail("Request format not validated correctly");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Internal error caused the test to fail");
		}
		
	}
	
	@Test
	public void testCreateAd() {
		String payload = "{\"partner_id\":\"Synechron\",\"duration\":0,\"ad_content\":\"Yo yo Honey singh\"}";
		try {
			String response = getResponse(payload);
			System.out.println(response);
			if(!"Ad Campaign Saved Successfully".equals(response.trim()))
				fail("Campaign Create test failed");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Internal error caused the test to fail");
		}
		
	}
	
	@Test
	public void testCreateSecondPartnerAd() {
		String payload = "{\"partner_id\":\"Synechron\",\"duration\":0,\"ad_content\":\"Yo yo Honey singh\"}";
		try {
			String response = getResponse(payload);
			System.out.println(response);
			if(!"Operation not permitted This partner already has a live campaign".equals(response.trim()))
				fail("Campaign Create test failed unique partner check");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Internal error caused the test to fail");
		}
		
	}
	
	@Test
	public void fetchExpiredRecord() {
		String payload = null;
		try {
			String response = getResponse(payload);
			System.out.println(response);
			if(!response.contains("No Active campaign exists for this Partner"))
				fail("Expired record Test failed");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Internal error caused the test to fail");
		}
		
	}
	
	@After
	public void tearDown(){
		connection=null;
	}
	
	
	private String getResponse(String body) throws IOException{
		if(body!=null){
		DataOutputStream wr = new DataOutputStream (
		        connection.getOutputStream());
		    wr.writeBytes(body);
		    wr.close();
		}
		    //Get Response  
		    InputStream is = connection.getInputStream();
		    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		    StringBuilder response = new StringBuilder(); 
		    String line;
		    while ((line = rd.readLine()) != null) {
		      response.append(line);
		      response.append('\r');
		    }
		    rd.close();
		    return response.toString();
	}

}
