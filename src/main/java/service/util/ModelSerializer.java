package service.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import service.exceptions.MissingAcceptHeaderException;
import service.exceptions.InvalidAcceptHeaderException;

import java.util.Locale;

public class ModelSerializer
{
  private static ObjectMapper objectToJSONMapper = null;
  private static ObjectMapper objectToXMLMapper  = null;
  
  public static String serialize(Object input, String accept) throws JsonProcessingException
  {
    if (accept == null)
    {
      throw new MissingAcceptHeaderException();
    }
  
    if (!objectIsModel(input))
    {
      throw new IllegalArgumentException("the given object isn't a model.");
    }
    
    accept = accept.toLowerCase(Locale.ROOT);
  
    if ("*/*".equals(accept) || "application/*".equals(accept) || "application/xml".equals(accept))
    {
      return serializeAsXML(input);
    }
    
    if ("application/json".equals(accept))
    {
      return serializeAsJSON(input);
    }
    
    if ("text/*".equals(accept) || "text/plain".equals(accept))
    {
      return input.toString();
    }
    
    throw new InvalidAcceptHeaderException();
  }
  
  public static String serializeAsJSON(Object input) throws JsonProcessingException
  {
    if (!objectIsModel(input))
    {
      throw new IllegalArgumentException("the given object isn't a model.");
    }
    
    if (objectToJSONMapper == null)
    {
      objectToJSONMapper = Jackson2ObjectMapperBuilder.json().build();
    }
    
    return objectToJSONMapper.writeValueAsString(input);
  }
  
  public static String serializeAsXML(Object input) throws JsonProcessingException
  {
    if (!objectIsModel(input))
    {
      throw new IllegalArgumentException("the given object isn't a model.");
    }
    
    if (objectToXMLMapper == null)
    {
      objectToXMLMapper = Jackson2ObjectMapperBuilder.xml().build();
    }
    
    return objectToXMLMapper.writeValueAsString(input);
  }
  
  private static boolean objectIsModel(Object input)
  {
    return input.getClass().getDeclaredAnnotation(Data.class) == null &&
           input.getClass().getPackageName().startsWith("service.models");
  }
}