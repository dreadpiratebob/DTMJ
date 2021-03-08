package tests.JSONEncoding;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import service.models.Health;
import service.models.ServiceStatus;

public class EncodeModelTests
{
  @Test
  public void modelGetsEncodedCorrectly() throws JsonProcessingException
  {
    ServiceStatus apiStatus = ServiceStatus.UP;
    ServiceStatus dbStatus = ServiceStatus.DOWN;
    String message = null;
    
    Health health = new Health();
    health.setApiStatus(apiStatus);
    health.setDatabaseStatus(dbStatus);
    health.setMessage(message);
    
    ObjectMapper mapper = Jackson2ObjectMapperBuilder.json().build();
    
    final String expected = "{\"apiStatus\":\"" + apiStatus +
                            "\",\"databaseStatus\":\"" + dbStatus +
                            "\",\"message\":" + (message == null ? "null" : message) + "}";
    final String actual = mapper.writeValueAsString(health);
  
    Assert.assertEquals(expected, actual);
  }
}