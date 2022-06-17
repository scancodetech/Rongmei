package com.rongmei.parameters.boxegg;

import java.util.List;

public class PublishBoxEggParameters {


  private int boxEggId;//盒蛋id

//  private String  boxEggName;//盒蛋名称

  private List<String> usernameList;//用户名集合


  public PublishBoxEggParameters() {
  }

  public PublishBoxEggParameters(int boxEggId, String boxEggName, List<String> usernameList) {
    this.boxEggId = boxEggId;
//    this.boxEggName = boxEggName;
    this.usernameList = usernameList;
  }

  public int getBoxEggId() {
    return boxEggId;
  }

  public void setBoxEggId(int boxEggId) {
    this.boxEggId = boxEggId;
  }

//  public String getBoxEggName() {
//    return boxEggName;
//  }
//
//  public void setBoxEggName(String boxEggName) {
//    this.boxEggName = boxEggName;
//  }

  public List<String> getUsernameList() {
    return usernameList;
  }

  public void setUsernameList(List<String> usernameList) {
    this.usernameList = usernameList;
  }

}
