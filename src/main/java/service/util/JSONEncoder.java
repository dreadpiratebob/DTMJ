package service.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

public class JSONEncoder
{
  private static ObjectMapper objectMapper = null;
  
  public static String encode(Object input) throws JsonProcessingException
  {
    if (objectMapper == null)
    {
      objectMapper = Jackson2ObjectMapperBuilder.json().build();
    }
    
    return objectMapper.writeValueAsString(input);
  }
}