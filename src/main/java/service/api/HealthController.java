package service.api;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.bind.annotation.*;
import service.exceptions.JSONGenerationException;
import service.models.Health;
import service.models.ServiceStatus;
import service.util.JSONEncoder;

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
    
    health.setMessage(message);
    
    try
    {
      return JSONEncoder.encode(health);
    }
    catch (JacksonException e)
    {
      throw new JSONGenerationException();
    }
  }
  
  @RequestMapping(value = "/", method = RequestMethod.GET)
  @ResponseBody
  public String checkHealthWithASlash(@RequestParam(name="message", required=false, defaultValue = "") String message)
  {
    return checkHealth(message);
  }
}