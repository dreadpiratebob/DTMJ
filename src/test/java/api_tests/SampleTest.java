package api_tests;

import service.api.HealthController;

import org.junit.Assert;
import org.junit.Test;
import service.models.ServiceStatus;

public class SampleTest
{
  @Test
  public void test()
  {
    final String message = "a message!";
    
    final String expectedStatus = "{\n  \"api status\": \"up\"\n  \"database status\": \"down\"\n  \"message\": a message!\"\n}";
    final String actualStatus = new HealthController().checkHealth(message);
    
    Assert.assertEquals(expectedStatus, actualStatus);
  }
}
