package com.rongmei.response.tcc;

import com.rongmei.entity.tcc.TCCTuple;
import com.rongmei.response.Response;
import java.util.List;

public class TCCTupleGetResponse extends Response {

  private List<TCCTuple> tccTupleList;

  public TCCTupleGetResponse() {
  }

  public TCCTupleGetResponse(List<TCCTuple> tccTupleList) {
    this.tccTupleList = tccTupleList;
  }

  public List<TCCTuple> getTccTupleList() {
    return tccTupleList;
  }

  public void setTccTupleList(List<TCCTuple> tccTupleList) {
    this.tccTupleList = tccTupleList;
  }
}
