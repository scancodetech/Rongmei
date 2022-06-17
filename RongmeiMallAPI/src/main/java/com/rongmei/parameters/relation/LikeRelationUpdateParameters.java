package com.rongmei.parameters.relation;

public class LikeRelationUpdateParameters {

  private int relationId;

  private int type;//0:拍品,1:素材,2:盒蛋,3:提案,4:巨人车


  public LikeRelationUpdateParameters() {
  }

  public LikeRelationUpdateParameters(int relationId, int type) {
    this.relationId = relationId;
    this.type = type;
  }

  public int getRelationId() {
    return relationId;
  }

  public void setRelationId(int relationId) {
    this.relationId = relationId;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }
}
