package com.rongmei.entity.platform;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "platform")
public class Platform {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(name = "name")
  private String name;
  @Column(name = "platform_key")
  private String platformKey;
  @Column(name = "status")
  private int status;
  @Column(name = "access_usernames")
  @ElementCollection(targetClass = String.class)
  private List<String> accessUsernames;

  public Platform() {
  }

  public Platform(String name, String platformKey, int status,
      List<String> accessUsernames) {
    this.name = name;
    this.platformKey = platformKey;
    this.status = status;
    this.accessUsernames = accessUsernames;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPlatformKey() {
    return platformKey;
  }

  public void setPlatformKey(String platformKey) {
    this.platformKey = platformKey;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public List<String> getAccessUsernames() {
    return accessUsernames;
  }

  public void setAccessUsernames(List<String> accessUsernames) {
    this.accessUsernames = accessUsernames;
  }
}
