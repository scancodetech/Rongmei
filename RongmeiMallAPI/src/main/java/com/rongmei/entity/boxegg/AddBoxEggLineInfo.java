package com.rongmei.entity.boxegg;


import javax.persistence.*;

@Entity
@Table(name = "box_egg_line")
public class  AddBoxEggLineInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(name = "add_box_egg_name")
  private String addBoxEggName;//盒蛋系列名称

  @Column(name = "username")
  private String username;//排队的用户名

  @Column(name = "status_number")
  private int statusNumber;


  public AddBoxEggLineInfo() {
  }


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getAddBoxEggName() {
    return addBoxEggName;
  }

  public void setAddBoxEggName(String addBoxEggName) {
    this.addBoxEggName = addBoxEggName;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public int getStatusNumber() {
    return statusNumber;
  }

  public void setStatusNumber(int statusNumber) {
    this.statusNumber = statusNumber;
  }
}
