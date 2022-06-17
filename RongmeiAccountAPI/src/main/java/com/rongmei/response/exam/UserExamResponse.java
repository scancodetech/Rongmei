package com.rongmei.response.exam;

import com.rongmei.publicdatas.exam.UserExerciseItem;
import com.rongmei.response.Response;
import java.util.List;

public class UserExamResponse extends Response {

  private int id;
  private String username;
  private List<UserExerciseItem> userExercises;
  private boolean isPass;
  private long createTime;
  private long updateTime;

  public UserExamResponse() {
  }

  public UserExamResponse(int id, String username,
      List<UserExerciseItem> userExercises, boolean isPass, long createTime, long updateTime) {
    this.id = id;
    this.username = username;
    this.userExercises = userExercises;
    this.isPass = isPass;
    this.createTime = createTime;
    this.updateTime = updateTime;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public List<UserExerciseItem> getUserExercises() {
    return userExercises;
  }

  public void setUserExercises(List<UserExerciseItem> userExercises) {
    this.userExercises = userExercises;
  }

  public boolean isPass() {
    return isPass;
  }

  public void setPass(boolean pass) {
    isPass = pass;
  }

  public long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(long createTime) {
    this.createTime = createTime;
  }

  public long getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(long updateTime) {
    this.updateTime = updateTime;
  }
}
