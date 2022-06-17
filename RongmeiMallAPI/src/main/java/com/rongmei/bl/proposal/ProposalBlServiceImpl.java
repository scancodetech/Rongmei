package com.rongmei.bl.proposal;

import com.rongmei.blservice.proposal.ProposalBlService;
import com.rongmei.dao.proposal.ProposalDao;
import com.rongmei.dao.proposal.ProposalParticipateDao;
import com.rongmei.entity.proposal.Proposal;
import com.rongmei.entity.proposal.ProposalParticipate;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.proposal.ProposalUpdateParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.proposal.ProposalDetailResponse;
import com.rongmei.response.proposal.ProposalGetResponse;
import com.rongmei.response.proposal.ProposalItem;
import com.rongmei.threads.AuctionThread;
import com.rongmei.threads.ProposalThread;
import com.rongmei.util.UserInfoUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProposalBlServiceImpl implements ProposalBlService {

  private final ProposalDao proposalDao;
  private final ProposalParticipateDao proposalParticipateDao;

  @Autowired
  public ProposalBlServiceImpl(ProposalDao proposalDao,
      ProposalParticipateDao proposalParticipateDao) {
    this.proposalDao = proposalDao;
    this.proposalParticipateDao = proposalParticipateDao;
    ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();
    ProposalThread proposalThread = new ProposalThread(proposalDao, proposalParticipateDao);
    timer.scheduleAtFixedRate(proposalThread, 1000, 1000 * 60 * 5, TimeUnit.MILLISECONDS);
  }

  @Override
  public ProposalGetResponse getProposals(int page, int limit, int status) {
    List<Proposal> proposalList;
    if (status == 0) {
      proposalList = proposalDao
          .findAllByLimitAndOffsetAndEndTimeAfterAndStatusNotOrderByEndTimeDesc(
              System.currentTimeMillis(), "审核中", limit, (page - 1) * limit);
    } else {
      proposalList = proposalDao
          .findAllByLimitAndOffsetAndEndTimeBeforeAndStatusNotOrderByEndTimeDesc(
              System.currentTimeMillis(), "审核中", limit, (page - 1) * limit);
    }
    return new ProposalGetResponse(packProposalItems(proposalList));
  }

  private List<ProposalItem> packProposalItems(List<Proposal> proposalList) {
    List<ProposalItem> proposalItems = new ArrayList<>();
    for (Proposal proposal : proposalList) {
      long agreeNum = proposalParticipateDao.countAllByRelationIdAndIsAgree(proposal.getId(), true);
      long disagreeNum = proposalParticipateDao
          .countAllByRelationIdAndIsAgree(proposal.getId(), false);
      long mineParticipateCount = proposalParticipateDao
          .countAllByRelationIdAndUsername(proposal.getId(), UserInfoUtil.getUsername());
      ProposalItem proposalItem = new ProposalItem(proposal.getId(), proposal.getContent(),
          proposal.getRelationId(),
          proposal.getProposalType(), proposal.getPassStandard(), proposal.getPromulgateStandard(),
          proposal.getStatus(), proposal.getStartTime(), proposal.getEndTime(),
          proposal.getCreateTime(), proposal.getUpdateTime(), proposal.getVisibility(),
          proposal.getPainterName(), proposal.getPainterSampleUrl(), proposal.getWorkPeriod(),
          proposal.getCompletionDegree(), proposal.getVoteEndTime(), proposal.getPrice(),
          (int) agreeNum,
          (int) disagreeNum, mineParticipateCount > 0);
      proposalItems.add(proposalItem);
    }
    return proposalItems;
  }

  public static List<ProposalItem> packProposalItemsWithoutResult(List<Proposal> proposalList) {
    List<ProposalItem> proposalItems = new ArrayList<>();
    for (Proposal proposal : proposalList) {
      ProposalItem proposalItem = new ProposalItem(proposal.getId(), proposal.getContent(),
          proposal.getRelationId(),
          proposal.getProposalType(), proposal.getPassStandard(), proposal.getPromulgateStandard(),
          proposal.getStatus(), proposal.getStartTime(), proposal.getEndTime(),
          proposal.getCreateTime(), proposal.getUpdateTime(), proposal.getVisibility(),
          proposal.getPainterName(), proposal.getPainterSampleUrl(), proposal.getWorkPeriod(),
          proposal.getCompletionDegree(), proposal.getVoteEndTime(), proposal.getPrice(),
          0, 0, false);
      proposalItems.add(proposalItem);
    }
    return proposalItems;
  }

  @Override
  public SuccessResponse updateProposals(ProposalUpdateParameters parameters) {
    if (parameters.getId() == 0) {
      Proposal proposal = new Proposal(UserInfoUtil.getUsername(), parameters.getContent(),
          parameters.getRelationId(),
          parameters.getProposalType(),
          parameters.getPassStandard(), parameters.getPromulgateStandard(), "审核中",
          parameters.getStartTime(), parameters.getEndTime(), System.currentTimeMillis(),
          System.currentTimeMillis(), true, parameters.getVisibility(), parameters.getPainterName(),
          parameters.getPainterSampleUrl(), parameters.getWorkPeriod(),
          parameters.getCompletionDegree(), parameters.getPrice(), parameters.getVoteEndTime(), 0);
      proposalDao.save(proposal);
      return new SuccessResponse("add success");
    } else {
      Optional<Proposal> optionalProposal = proposalDao.findById(parameters.getId());
      if (optionalProposal.isPresent()) {
        Proposal proposal = optionalProposal.get();
        proposal.setRelationId(proposal.getRelationId());
        proposal.setContent(proposal.getContent());
        proposal.setProposalType(proposal.getProposalType());
        proposal.setPassStandard(proposal.getPassStandard());
        proposal.setPromulgateStandard(proposal.getPromulgateStandard());
        proposal.setStartTime(proposal.getStartTime());
        proposal.setEndTime(proposal.getEndTime());
        proposal.setVisibility(proposal.getVisibility());
        proposal.setPainterName(proposal.getPainterName());
        proposal.setPainterSampleUrl(proposal.getPainterSampleUrl());
        proposal.setWorkPeriod(proposal.getWorkPeriod());
        proposal.setCompletionDegree(proposal.getCompletionDegree());
        proposal.setVoteEndTime(proposal.getVoteEndTime());
        proposal.setUpdateTime(System.currentTimeMillis());
        proposalDao.save(proposal);
      }
      return new SuccessResponse("update success");
    }
  }

  @Override
  public ProposalDetailResponse getProposal(int id) throws ThingIdDoesNotExistException {
    Optional<Proposal> optionalProposal = proposalDao.findById(id);
    if (optionalProposal.isPresent()) {
      Proposal proposal = optionalProposal.get();
      long agreeNum = proposalParticipateDao.countAllByRelationIdAndIsAgree(id, true);
      long disagreeNum = proposalParticipateDao.countAllByRelationIdAndIsAgree(id, false);
      return new ProposalDetailResponse(proposal.getId(), proposal.getContent(),
          proposal.getRelationId(),
          proposal.getProposalType(), proposal.getPassStandard(), proposal.getPromulgateStandard(),
          proposal.getStatus(), proposal.getStartTime(), proposal.getEndTime(),
          proposal.getCreateTime(), proposal.getUpdateTime(), proposal.getVisibility(),
          proposal.getPainterName(), proposal.getPainterSampleUrl(), proposal.getWorkPeriod(),
          proposal.getCompletionDegree(), proposal.getVoteEndTime(), proposal.getPrice(),
          (int) agreeNum,
          (int) disagreeNum);
    }
    throw new ThingIdDoesNotExistException();
  }

  @Override
  public ProposalGetResponse getUserProposals(int page, int limit, String username) {
    List<Proposal> proposalList = proposalDao
        .findAllByLimitAndOffsetAndUsernameOrderByEndTimeDesc(username, limit, (page - 1) * limit);
    return new ProposalGetResponse(packProposalItems(proposalList));
  }

  @Override
  public SuccessResponse deleteProposal(int id) throws ThingIdDoesNotExistException {
    Optional<Proposal> optionalProposal = proposalDao.findById(id);
    if (optionalProposal.isPresent()) {
      Proposal proposal = optionalProposal.get();
      proposal.setActive(false);
      proposalDao.save(proposal);
      return new SuccessResponse("delete success");
    }
    throw new ThingIdDoesNotExistException();
  }

  @Override
  public SuccessResponse voteProposal(int id, boolean isAgree) {
    String username = UserInfoUtil.getUsername();
    ProposalParticipate proposalParticipate = new ProposalParticipate(id, username, isAgree,
        System.currentTimeMillis(), System.currentTimeMillis());
    proposalParticipateDao.save(proposalParticipate);
    return new SuccessResponse("vote success");
  }
}
