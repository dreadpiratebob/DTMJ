package tests.JSONEncoding.models;

import lombok.Data;
import org.joda.time.DateTime;
import service.models.ServiceStatus;

@Data
public class TestSubModel
{
  private String datum;
  private DateTime dateTime;
  private ServiceStatus status;
}