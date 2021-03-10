package service.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import service.exceptions.MissingAcceptHeaderException;
import service.exceptions.InvalidAcceptHeaderException;

import java.util.Locale;

public class ModelSerializer
{
  public static String serialize(Object input, String contentType) throws JsonProcessingException
  {
    if (contentType == null)
    {
      throw new MissingAcceptHeaderException();
    }
  
    if (!objectIsModel(input))
    {
      throw new IllegalArgumentException("the given object isn't a model.");
    }
    
    contentType = contentType.toLowerCase(Locale.ROOT);
  
    if ("*/*".equals(contentType) || "application/*".equals(contentType) || "application/xml".equals(contentType))
    {
      return serializeAsXML(input);
    }
    
    if ("application/json".equals(contentType))
    {
      return serializeAsJSON(input);
    }
    
    if ("text/*".equals(contentType) || "text/plain".equals(contentType))
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
    
    return Jackson2ObjectMapperBuilder.json().build().writeValueAsString(input);
  }
  
  public static String serializeAsXML(Object input) throws JsonProcessingException
  {
    if (!objectIsModel(input))
    {
      throw new IllegalArgumentException("the given object isn't a model.");
    }
    
    return Jackson2ObjectMapperBuilder.xml().build().writeValueAsString(input);
  }
  
  public static <T> T deserialize(String input, Class<T> modelClass, String contentType) throws JsonProcessingException
  {
    if (contentType == null)
    {
      throw new MissingAcceptHeaderException();
    }
    
    contentType = contentType.toLowerCase(Locale.ROOT);
    
    if ("*/*".equals(contentType) || "application/*".equals(contentType) || "application/xml".equals(contentType))
    {
      return deserializeFromXML(input, modelClass);
    }
    
    if ("application/json".equals(contentType))
    {
      return deserializeFromJSON(input, modelClass);
    }
    
    throw new InvalidAcceptHeaderException();
  }
  
  public static <T> T deserializeFromJSON(String json, Class<T> modelClass) throws JsonProcessingException
  {
    if (!classIsModelType(modelClass))
    {
      throw new IllegalArgumentException("the given class isn't a model.");
    }
    
    return Jackson2ObjectMapperBuilder.json().build().readValue(json, modelClass);
  }
  
  public static <T> T deserializeFromXML(String xml, Class<T> modelClass) throws JsonProcessingException
  {
    if (!classIsModelType(modelClass))
    {
      throw new IllegalArgumentException("the given class isn't a model.");
    }
    
    return Jackson2ObjectMapperBuilder.xml().build().readValue(xml, modelClass);
  }
  
  private static boolean objectIsModel(Object input)
  {
    return classIsModelType(input.getClass());
  }
  
  private static <T> boolean classIsModelType(Class<T> modelClass)
  {
    return modelClass.getAnnotations().length == 0 && // lombok.Data isn't retained for reflective access; otherwise i'd make sure modelClass was annotated with it.
           modelClass.getPackageName().startsWith("service.models");
  }
}