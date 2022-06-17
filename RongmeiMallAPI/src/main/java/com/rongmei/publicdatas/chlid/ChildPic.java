package com.rongmei.publicdatas.chlid;

import javax.persistence.Embeddable;

@Embeddable
public class ChildPic {

  private String fileUrl;
  private String fileName;
  private String childUrl;

  public ChildPic() {
  }

  public ChildPic(String fileUrl, String fileName, String childUrl) {
    this.fileUrl = fileUrl;
    this.fileName = fileName;
    this.childUrl = childUrl;
  }

  public String getFileUrl() {
    return fileUrl;
  }

  public void setFileUrl(String fileUrl) {
    this.fileUrl = fileUrl;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getChildUrl() {
    return childUrl;
  }

  public void setChildUrl(String childUrl) {
    this.childUrl = childUrl;
  }
}
