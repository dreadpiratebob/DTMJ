package service.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import service.models.Health;
import service.models.ServiceStatus;

@RestController
@RequestMapping("/health")
public class HealthController
{
  @RequestMapping
  (
    value    = "",
    method   = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  @ResponseBody
  public String checkHealth(@RequestParam(name="message", required=false) String message)
  {
    Health health = new Health();
    health.setApiStatus(ServiceStatus.UP);
    
    StringBuilder result = new StringBuilder("{\n")
        .append("  \"api status\": \"").append(health.getApiStatus()).append("\"\n")
        .append("  \"database status\": \"").append(health.getDatabaseStatus()).append("\"\n");
    
    if (message != null)
    {
      // I should probly get a JSON library to do this for me, but this should be good enough for sample code.
      String jsonMessage = message
          .replace("\"", "\\\"")
          .replace("\r", "\\r")
          .replace("\n", "\\n");
      result.append("  \"message\": \"").append(jsonMessage).append("\"\n");
    }
    
    result.append("}");
    return result.toString();
  }
  
  @RequestMapping(value = "/", method = RequestMethod.GET)
  @ResponseBody
  public String checkHealthWithASlash(@RequestParam(name="message", required=false, defaultValue = "") String message)
  {
    return checkHealth(message);
  }
}