package com.rongmei.entity.auction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "token")
public class Token {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  @Column(name = "token_id")
  private long tokenId;
  @Column(name = "value")
  private String value;

  public Token() {
  }

  public Token(long tokenId, String value) {
    this.tokenId = tokenId;
    this.value = value;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getTokenId() {
    return tokenId;
  }

  public void setTokenId(long tokenId) {
    this.tokenId = tokenId;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
