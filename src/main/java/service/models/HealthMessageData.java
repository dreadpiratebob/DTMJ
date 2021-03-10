package service.models;

import lombok.Data;

@Data
public class HealthMessageData
{
  private String from;
  private String to;
  private String message;
}
