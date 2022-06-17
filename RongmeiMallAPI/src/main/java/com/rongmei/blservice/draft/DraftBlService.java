package com.rongmei.blservice.draft;

import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.draft.DraftReviewParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.child.SimpleChildGetResponse;
import com.rongmei.response.draft.DraftBlindBoxSaleDetailResponse;
import com.rongmei.response.draft.DraftBlindBoxSaleGetResponse;
import com.rongmei.response.draft.DraftChildDetailResponse;
import com.rongmei.response.draft.DraftCommodityDetailResponse;
import com.rongmei.response.draft.DraftCommodityGetResponse;
import com.rongmei.response.draft.DraftGroupShoppingDetailResponse;
import com.rongmei.response.draft.DraftProposalDetailResponse;
import com.rongmei.response.draft.DraftResponse;
import com.rongmei.response.draft.DraftSaleDetailResponse;
import com.rongmei.response.draft.DraftSaleGetResponse;
import com.rongmei.response.groupshopping.GroupShoppingGetResponse;
import com.rongmei.response.proposal.ProposalGetResponse;

public interface DraftBlService {

  SuccessResponse reviewDraft(DraftReviewParameters parameter, String username)
      throws ThingIdDoesNotExistException;

  DraftCommodityGetResponse getDraftCommodity();

  DraftSaleGetResponse getDraftSale();

  DraftBlindBoxSaleGetResponse getDraftBlindBoxSale();

  DraftCommodityDetailResponse getDraftCommodityDetail(int relationId)
      throws ThingIdDoesNotExistException;

  DraftSaleDetailResponse getDraftSaleDetail(int relationId) throws ThingIdDoesNotExistException;

  DraftBlindBoxSaleDetailResponse getDraftBlindBoxSaleDetail(int relationId)
      throws ThingIdDoesNotExistException;

  DraftResponse getDraftReason(int relationId, int draftType) throws ThingIdDoesNotExistException;

  GroupShoppingGetResponse getDraftGroupShopping();

  DraftGroupShoppingDetailResponse getDraftGroupShoppingDetail(int relationId)
      throws ThingIdDoesNotExistException;

  ProposalGetResponse getDraftProposal();

  DraftProposalDetailResponse getDraftProposalDetail(int relationId)
      throws ThingIdDoesNotExistException;

  SimpleChildGetResponse getDraftChild();

  DraftChildDetailResponse getDraftChildDetail(int relationId) throws ThingIdDoesNotExistException;
}
