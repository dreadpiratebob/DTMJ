package tests.JSONEncoding;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service.exceptions.NotImplementedException;
import service.models.Health;
import service.models.ServiceStatus;
import service.util.ModelSerializer;
import tests.JSONEncoding.models.TestModel;
import tests.JSONEncoding.models.TestSubModel;

public class SerializeModelTests
{
  private static final String timeStr = "2020-02-02T02:02:02.000Z";
  
  private TestModel test;
  private TestSubModel data;
  
  @Before
  public void init()
  {
    String datum = "asdfasdf";
    DateTime timestamp = (timeStr == null ? null : DateTime.parse(timeStr));
    ServiceStatus status = ServiceStatus.UP;
  
    data = new TestSubModel();
    data.setDatum(datum);
    data.setDateTime(timestamp);
    data.setStatus(status);
  
    test = new TestModel();
    test.setName("test name.");
    test.setValue(2);
    test.setData(data);
  }
  
  @Test
  public void modelGetsSerializedAsJSONCorrectly() throws JsonProcessingException
  {
    String dataString = "\"data\":null";
    if (test.getData() != null)
    {
      dataString = "\"data\":{" +
                   "\"datum\":" + getStringFromNullableValue(test.getData().getDatum()) +
                   ",\"dateTime\":" + getStringFromNullableValue(timeStr) +
                   ",\"status\":" + getStringFromNullableValue(test.getData().getStatus()) + "}";
    }
    
    final String expected = "{\"name\":" + getStringFromNullableValue(test.getName()) +
                            ",\"value\":" + test.getValue() +
                            "," + dataString + "}";
    final String actual = ModelSerializer.serialize(test, "application/json");
  
    Assert.assertEquals(expected, actual);
  }
  
  @Test
  public void modelGetsSerializedAsXMLCorrectly() throws JsonProcessingException
  {
    String dataString = "<data/>";
    if (test.getData() != null)
    {
      dataString = "<data>" +
                     getXMLStringFromNullableValue("datum",    test.getData().getDatum()) +
                     getXMLStringFromNullableValue("dateTime", timeStr) +
                     getXMLStringFromNullableValue("status",   test.getData().getStatus()) +
                   "</data>";
    }
  
    final String expected = "<TestModel>" +
                              getXMLStringFromNullableValue("name", test.getName()) +
                              getXMLStringFromNullableValue("value", test.getValue()) +
                              dataString +
                            "</TestModel>";
    final String actual = ModelSerializer.serialize(test, "application/xml");
  
    Assert.assertEquals(expected, actual);
  }
  
  @Test
  public void modelGetsDeserializedFromJSONCorrectly() throws JsonProcessingException
  {
    String name = "a name";
    Integer value = 42;
    
    String datum = "some dataish things";
    DateTime dateTime = DateTime.now();
    ServiceStatus status = ServiceStatus.UP;
    
    TestSubModel data = new TestSubModel();
    data.setDatum(datum);
    data.setDateTime(dateTime);
    data.setStatus(status);
    
    TestModel expected = new TestModel();
    expected.setName(name);
    expected.setValue(value);
    expected.setData(data);
    
    String dateTimeStr = dateTime.toString(ModelSerializer.getDateTimeFormatter());
    String json = "{\"name\":\"" + name + "\",\"value\":\"" + value + "\",\"data\":{\"datum\":\"" + datum +
                  "\",\"dateTime\":\"" + dateTimeStr + "\",\"status\":\"" + status.toString() + "\"}}";
    
    TestModel actual = ModelSerializer.deserialize(json, TestModel.class, "application/json");
    
    Assert.assertEquals(expected, actual);
  }
  
  @Test
  public void modelGetsDeserializedFromXMLCorrectly() throws JsonProcessingException
  {
    String name = "a name";
    Integer value = 42;
  
    String datum = "some dataish things";
    DateTime dateTime = DateTime.now();
    ServiceStatus status = ServiceStatus.UP;
  
    TestSubModel data = new TestSubModel();
    data.setDatum(datum);
    data.setDateTime(dateTime);
    data.setStatus(status);
  
    TestModel expected = new TestModel();
    expected.setName(name);
    expected.setValue(value);
    expected.setData(data);
  
    String dateTimeStr = dateTime.toString(ModelSerializer.getDateTimeFormatter());
    String xml = "<TestModel><name>" + name + "</name><value>" + value + "</value><data><datum>" + datum + "</datum><dateTime>" + dateTimeStr + "</dateTime><status>" + status.toString() + "</status></data></TestModel>";
  
    TestModel actual = ModelSerializer.deserialize(xml, TestModel.class, "application/xml");
  
    Assert.assertEquals(expected, actual);
  }
  
  private String getStringFromNullableValue(Object value)
  {
    if (value == null)
    {
      return "null";
    }
    
    return "\"" + value.toString() + "\"";
  }
  
  private String getXMLStringFromNullableValue(String name, Object value)
  {
    if (value == null)
    {
      return "<" + name + "/>";
    }
    
    if (ModelSerializer.objectIsModel(value) && !value.getClass().isEnum())
    {
      throw new NotImplementedException();
    }
    
    return "<" + name + ">" + value.toString() + "</" + name + ">";
  }
}