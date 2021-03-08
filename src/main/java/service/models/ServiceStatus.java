package service.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ServiceStatus
{
  UP("up"),
  DOWN("down");
  
  private final String label;
  
  private ServiceStatus(String label)
  {
    this.label = label;
  }
  
  @JsonValue
  @Override
  public String toString()
  {
    return label;
  }
}
