package tests.api;

import service.api.HealthController;

import org.junit.Assert;
import org.junit.Test;

public class SampleTest
{
  @Test
  public void test()
  {
    final String message = "a message!";
    
    final String expectedStatus = "{\"apiStatus\":\"up\",\"databaseStatus\":\"down\",\"message\":\"" + message + "\"}";
    final String actualStatus = new HealthController().checkHealth(message);
    
    Assert.assertEquals(expectedStatus, actualStatus);
  }
}
