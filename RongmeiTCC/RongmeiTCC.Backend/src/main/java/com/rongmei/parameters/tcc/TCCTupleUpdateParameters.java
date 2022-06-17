package com.rongmei.parameters.tcc;

public class TCCTupleUpdateParameters {

  private int id;
  private String key;
  private String value;
  private String title;
  private String description;

  public TCCTupleUpdateParameters() {
  }

  public TCCTupleUpdateParameters(int id, String key, String value, String title,
      String description) {
    this.id = id;
    this.key = key;
    this.value = value;
    this.title = title;
    this.description = description;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
