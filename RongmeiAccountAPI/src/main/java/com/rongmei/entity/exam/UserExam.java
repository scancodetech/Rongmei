package com.rongmei.entity.exam;

import com.rongmei.publicdatas.exam.UserExerciseItem;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_exam")
public class UserExam {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(name = "username")
  private String username;
  @Column(name = "user_exercises")
  @ElementCollection(targetClass = UserExerciseItem.class)
  private List<UserExerciseItem> userExercises;
  @Column(name = "is_pass")
  private boolean isPass;
  @Column(name = "create_time")
  private long createTime;
  @Column(name = "update_time")
  private long updateTime;

  public UserExam() {
  }

  public UserExam(String username,
      List<UserExerciseItem> userExercises, boolean isPass, long createTime, long updateTime) {
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
