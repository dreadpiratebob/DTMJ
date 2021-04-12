package service.api;

import com.fasterxml.jackson.core.JacksonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import service.exceptions.JSONGenerationException;
import service.models.Health;
import service.models.HealthMessageData;
import service.models.HealthMessageResponse;
import service.models.ServiceStatus;
import service.util.ModelSerializer;

@RestController
@RequestMapping("/health")
@PropertySource("classpath:config/application.properties")
public class HealthController
{
  @Autowired
  private Environment env;
  
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
    String demoProperty = env == null ? null : env.getProperty("demo.property");
    
    Health health = new Health();
    health.setApiStatus(ServiceStatus.UP);
    
    health.setPropertiesAreAccessible("demo.value".equals(demoProperty));
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
  
  @RequestMapping
    (
      value    = "/",
      method   = RequestMethod.GET,
      produces =
        {
          MediaType.APPLICATION_JSON_VALUE,
          MediaType.APPLICATION_XML_VALUE,
          MediaType.TEXT_PLAIN_VALUE
        }
    )
  @ResponseBody
  public String checkHealthWithASlash
    (
      @RequestHeader(name="accept") String accept,
      @RequestParam(name="message", required=false) String message
    )
  {
    return checkHealth(accept, message);
  }
  
  // this is really just here to make sure model deserialization works.
  @RequestMapping
    (
      value = "",
      method = RequestMethod.POST,
      consumes =
        {
          MediaType.APPLICATION_JSON_VALUE,
          MediaType.APPLICATION_XML_VALUE
        }
    )
  @ResponseBody
  public String demoSerialization
    (
      @RequestHeader(name = "Content-Type") String contentType,
      @RequestHeader(name = "Accept") String accept,
      @RequestBody String rawData
    )
  {
    HealthMessageData data = null;
    try
    {
      data = ModelSerializer.deserialize(rawData, HealthMessageData.class, contentType);
    }
    catch (JacksonException e)
    {
      throw new JSONGenerationException();
    }
    
    HealthMessageResponse response = new HealthMessageResponse();
    response.setStatus("your message wasn't delivered from " + data.getFrom() + " to " + data.getTo() + ".");
  
    try
    {
      return ModelSerializer.serialize(response, accept);
    }
    catch (JacksonException e)
    {
      throw new JSONGenerationException();
    }
  }
  
  @RequestMapping
    (
      value = "/",
      method = RequestMethod.POST,
      consumes =
        {
          MediaType.APPLICATION_JSON_VALUE,
          MediaType.APPLICATION_XML_VALUE
        }
    )
  @ResponseBody
  public String demoSerializationWithASlash
    (
      @RequestHeader(name = "Content-Type") String contentType,
      @RequestHeader(name = "Accept") String accept,
      @RequestBody String data
    )
  {
    return demoSerialization(contentType, accept, data);
  }
}