package com.rongmei.response.stats;

public class Node {

  private String id;
  private NodeData data;
  private String shape;

  public Node() {
  }

  public Node(String id, NodeData data, String shape) {
    this.id = id;
    this.data = data;
    this.shape = shape;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public NodeData getData() {
    return data;
  }

  public void setData(NodeData data) {
    this.data = data;
  }

  public String getShape() {
    return shape;
  }

  public void setShape(String shape) {
    this.shape = shape;
  }
}
