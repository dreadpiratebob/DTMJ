package tests.api;

import org.junit.Ignore;
import service.api.HealthController;

import org.junit.Assert;
import org.junit.Test;

public class SampleTest
{
  @Test
  public void testJSON()
  {
    final String message = "a message!";
    
    final String expectedStatus = "{\"apiStatus\":\"up\",\"databaseStatus\":\"down\",\"propertiesAreAccessible\":false,\"message\":\"" + message + "\"}";
    final String actualStatus = new HealthController().checkHealth("application/json", message);
    
    Assert.assertEquals(expectedStatus, actualStatus);
  }
  
  // XML and plain text tests are excluded here under the assumption that they'll be covered in the EncodeModelTests.
}
