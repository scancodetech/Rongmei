package com.rongmei.response.tcc;

import com.rongmei.entity.tcc.TCCTuple;
import com.rongmei.response.Response;

public class TCCTupleDetailResponse extends Response {

  private TCCTuple tccTuple;

  public TCCTupleDetailResponse(TCCTuple tccTuple) {
    this.tccTuple = tccTuple;
  }

  public TCCTupleDetailResponse() {
  }

  public TCCTuple getTccTuple() {
    return tccTuple;
  }

  public void setTccTuple(TCCTuple tccTuple) {
    this.tccTuple = tccTuple;
  }
}
