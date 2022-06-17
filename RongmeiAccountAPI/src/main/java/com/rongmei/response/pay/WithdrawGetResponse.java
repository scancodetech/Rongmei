package com.rongmei.response.pay;

import com.rongmei.response.Response;
import java.util.List;

public class WithdrawGetResponse extends Response {

  private List<WithdrawItem> withdrawItems;

  public WithdrawGetResponse() {
  }

  public WithdrawGetResponse(List<WithdrawItem> withdrawItems) {
    this.withdrawItems = withdrawItems;
  }

  public List<WithdrawItem> getWithdrawItems() {
    return withdrawItems;
  }

  public void setWithdrawItems(List<WithdrawItem> withdrawItems) {
    this.withdrawItems = withdrawItems;
  }
}
