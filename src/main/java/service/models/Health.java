package service.models;

import lombok.Data;

@Data
public class Health
{
  private ServiceStatus apiStatus = ServiceStatus.DOWN;
  private ServiceStatus databaseStatus = ServiceStatus.DOWN;
  private String message = null;
}