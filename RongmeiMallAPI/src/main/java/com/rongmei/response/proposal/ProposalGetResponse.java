package com.rongmei.response.proposal;

import com.rongmei.response.Response;
import java.util.List;

public class ProposalGetResponse extends Response {

  private List<ProposalItem> proposalItemList;

  public ProposalGetResponse() {
  }

  public ProposalGetResponse(
      List<ProposalItem> proposalItemList) {
    this.proposalItemList = proposalItemList;
  }

  public List<ProposalItem> getProposalItemList() {
    return proposalItemList;
  }

  public void setProposalItemList(
      List<ProposalItem> proposalItemList) {
    this.proposalItemList = proposalItemList;
  }
}
