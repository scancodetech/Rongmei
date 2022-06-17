package com.rongmei.response.stats;

public class Edge {

  private String source;
  private String target;
  private EdgeData data;

  public Edge() {
  }

  public Edge(String source, String target, EdgeData data) {
    this.source = source;
    this.target = target;
    this.data = data;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public String getTarget() {
    return target;
  }

  public void setTarget(String target) {
    this.target = target;
  }

  public EdgeData getData() {
    return data;
  }

  public void setData(EdgeData data) {
    this.data = data;
  }
}
