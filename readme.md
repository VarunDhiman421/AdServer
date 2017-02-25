
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

### Scale Up
The servlet 3 architecture used in the application is somewhat hard to maintain and scale. Addition of a new action (service) would be much easier if done via RESTful style using jersey implementation.
<p>For persistence I had 3 choices</p>
 - Relational database like sqllite or mysql *More reliable but longer setup time and harder to bundle*
 - Store an object inside the server context *Easiest to implement but data is lost on server shutdown*
 - Persist json to file system *Data persists after jvm exit but harder data manipulation*
 
 I decided to stick with 3 because it seemed it met both the other approaches midway. As the application becomes bigger, a DB with hibernate layer would be serve the purpose better. 
 <p>For multi-campaign support for partner, we need to have to separate tables partner and campaign where partnerID(number) can be used as
 a foreign key campaign table</p>
 
