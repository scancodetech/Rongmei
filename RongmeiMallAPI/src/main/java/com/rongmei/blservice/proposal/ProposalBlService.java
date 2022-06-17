package com.rongmei.blservice.proposal;

import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.proposal.ProposalUpdateParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.proposal.ProposalDetailResponse;
import com.rongmei.response.proposal.ProposalGetResponse;

public interface ProposalBlService {

  /**
   * @param status 0:进行中 1:过往
   */
  ProposalGetResponse getProposals(int page, int limit, int status);

  SuccessResponse updateProposals(ProposalUpdateParameters parameters);

  ProposalDetailResponse getProposal(int id) throws ThingIdDoesNotExistException;

  ProposalGetResponse getUserProposals(int page, int limit, String username);

  SuccessResponse deleteProposal(int id) throws ThingIdDoesNotExistException;

  SuccessResponse voteProposal(int id, boolean isAgree);
}
