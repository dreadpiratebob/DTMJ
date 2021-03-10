package service.api;

import com.fasterxml.jackson.core.JacksonException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import service.exceptions.JSONGenerationException;
import service.models.Health;
import service.models.ServiceStatus;
import service.util.ModelSerializer;

@RestController
@RequestMapping("/health")
public class HealthController
{
  @RequestMapping
  (
    value    = "",
    method   = RequestMethod.GET,
    produces =
    {
      MediaType.APPLICATION_JSON_VALUE,
      MediaType.APPLICATION_XML_VALUE,
      MediaType.TEXT_PLAIN_VALUE
    }
  )
  @ResponseBody
  public String checkHealth
    (
      @RequestHeader(name="accept") String accept,
      @RequestParam(name="message", required=false) String message
    )
  {
    Health health = new Health();
    health.setApiStatus(ServiceStatus.UP);
    
    health.setMessage(message);
    
    try
    {
      return ModelSerializer.serialize(health, accept);
    }
    catch (JacksonException e)
    {
      throw new JSONGenerationException();
    }
  }
  
  @RequestMapping(value = "/", method = RequestMethod.GET)
  @ResponseBody
  public String checkHealthWithASlash
    (
      @RequestHeader(name="accept") String accept,
      @RequestParam(name="message", required=false) String message
    )
  {
    return checkHealth(accept, message);
  }
}