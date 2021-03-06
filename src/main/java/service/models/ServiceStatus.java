package service.models;

public enum ServiceStatus
{
  UP("up"),
  DOWN("down");
  
  public final String label;
  
  private ServiceStatus(String label)
  {
    this.label = label;
  }
  
  @Override
  public String toString()
  {
    return label;
  }
}
