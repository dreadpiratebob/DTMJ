package tests.JSONEncoding;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Assert;
import org.junit.Test;
import service.models.Health;
import service.models.ServiceStatus;
import service.util.ModelSerializer;

public class EncodeModelTests
{
  @Test
  public void modelGetsEncodedAsJSONCorrectly() throws JsonProcessingException
  {
    ServiceStatus apiStatus = ServiceStatus.UP;
    ServiceStatus dbStatus = ServiceStatus.DOWN;
    String message = null;
    
    Health health = new Health();
    health.setApiStatus(apiStatus);
    health.setDatabaseStatus(dbStatus);
    health.setMessage(message);
    
    final String expected = "{\"apiStatus\":\"" + apiStatus +
                            "\",\"databaseStatus\":\"" + dbStatus +
                            "\",\"message\":" + (message == null ? "null" : message) + "}";
    final String actual = ModelSerializer.serialize(health, "application/json");
  
    Assert.assertEquals(expected, actual);
  }
  
  @Test
  public void modelGetsEncodedAsXMLCorrectly() throws JsonProcessingException
  {
    ServiceStatus apiStatus = ServiceStatus.UP;
    ServiceStatus dbStatus = ServiceStatus.DOWN;
    String message = "! !";
    
    Health health = new Health();
    health.setApiStatus(apiStatus);
    health.setDatabaseStatus(dbStatus);
    health.setMessage(message);
    
    String expectedAPIStatus = "<apiStatus>" + health.getApiStatus() + "</apiStatus>";
    String expectedDatabaseStatus = "<databaseStatus>" + health.getDatabaseStatus() + "</databaseStatus>";
    String expectedMessage = "<message/>";
    if (message != null)
    {
      expectedMessage = "<message>" + message + "</message>";
    }
    
    final String expected = "<Health>" + expectedAPIStatus + expectedDatabaseStatus + expectedMessage + "</Health>";
    final String actual = ModelSerializer.serialize(health, "application/xml");
    
    Assert.assertEquals(expected, actual);
  }
}