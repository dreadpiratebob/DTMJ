package service.models;

import lombok.Data;

@Data
public class Health
{
  ServiceStatus apiStatus = ServiceStatus.DOWN;
  ServiceStatus databaseStatus = ServiceStatus.DOWN;
}