package com.rongmei.threads;

import com.rongmei.dao.proposal.ProposalDao;
import com.rongmei.dao.proposal.ProposalParticipateDao;
import com.rongmei.entity.proposal.Proposal;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ProposalThread implements Runnable {

  private ProposalDao proposalDao;
  private ProposalParticipateDao proposalParticipateDao;

  public ProposalThread(ProposalDao proposalDao,
      ProposalParticipateDao proposalParticipateDao) {
    this.proposalDao = proposalDao;
    this.proposalParticipateDao = proposalParticipateDao;
  }

  @Override
  public void run() {
    List<Proposal> proposalList = this.proposalDao
        .findAllByEndTimeBeforeAndStatus(System.currentTimeMillis(), "投票中");
    for (Proposal proposal : proposalList) {
      String status = "拒绝";
      long agreeNum = proposalParticipateDao.countAllByRelationIdAndIsAgree(proposal.getId(), true);
      long disagreeNum = proposalParticipateDao
          .countAllByRelationIdAndIsAgree(proposal.getId(), false);
      if (agreeNum + disagreeNum == 0) {
        status = "拒绝";
      } else {
        if (agreeNum < disagreeNum) {
          status = "拒绝";
        }
        if (agreeNum * 100.0 / (agreeNum + disagreeNum) > proposal.getPassStandard()) {
          status = "通过";
        }
        if (agreeNum * 100.0 / (agreeNum + disagreeNum) > proposal.getPromulgateStandard()) {
          status = "已颁布";
        }
      }
      proposal.setStatus(status);
      proposalDao.save(proposal);
    }
  }
}
