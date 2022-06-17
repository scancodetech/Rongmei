package com.rongmei.dao.proposal;

import com.rongmei.entity.proposal.ProposalParticipate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProposalParticipateDao extends JpaRepository<ProposalParticipate, Integer> {

  long countAllByRelationIdAndIsAgree(int relationId, boolean isAgree);

  long countAllByRelationIdAndUsername(int relationId, String username);
}
