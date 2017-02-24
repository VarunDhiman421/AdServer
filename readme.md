
### How to run?
Download the dist folder on a windows machine and double click on run.bat. This will deploy the application on a jetty server and
start it. Once done type [http://localhost:8080/](http://localhost:8080/) in your browser. If you see a page saying WELCOME TO AD SERVER, it confirms that the application is up

---

### How to create AdCampaigns?
The easiest way to send POST requests would be to use the chrome extension POSTMAN. Once installed select the method as POST and 
enter the following url

[http://localhost:8080/AdCampaignServlet](http://localhost:8080/AdCampaignServlet)

and the following json in data 
```javascript
{
 "partner_id": "CocaCola",
 "duration": "10",
 "ad_content": "Taste the feeling"
}
```
The duration is in days

Try the following test cases:
 - Try to insert 2 records with same partner ID and see the error message
 - Try to put a string in duration and see the error message
 - Create a record with duration 0 (will be used in fetch)

---

### How to fetch AdCampaigns?
Just type the following url in your browser and you are good to go.
[http://localhost:8080/AdCampaignServlet/CocaCola](http://localhost:8080/AdCampaignServlet/CocaCola)

Try the following test cases
 - Try and fetch the record with duration 0 and see the error message
 - Try and fetch a valid record

##### The application also contains junit test cases that can be run from eclipse
