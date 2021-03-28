package tests.JSONEncoding.models;

import lombok.Data;

@Data
public class TestModel
{
  private String name;
  private Integer value;
  private TestSubModel data;
}