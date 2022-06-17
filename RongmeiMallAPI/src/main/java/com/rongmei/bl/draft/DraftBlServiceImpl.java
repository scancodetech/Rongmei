package com.rongmei.bl.draft;

import static com.rongmei.bl.child.SimpleChildBlServiceImpl.packSimpleChildItems;
import static com.rongmei.bl.groupshopping.GroupShoppingBlServiceImpl.packGroupShoppingItems;
import static com.rongmei.bl.proposal.ProposalBlServiceImpl.packProposalItemsWithoutResult;

import com.rongmei.blservice.draft.DraftBlService;
import com.rongmei.dao.auction.SaleDao;
import com.rongmei.dao.auction.ThingDao;
import com.rongmei.dao.blindboxnft.BlindBoxNFTDao;
import com.rongmei.dao.blindboxnft.BlindBoxSaleDao;
import com.rongmei.dao.child.SimpleChildDao;
import com.rongmei.dao.commodity.CommodityDao;
import com.rongmei.dao.draft.DraftDao;
import com.rongmei.dao.groupshopping.GroupShoppingDao;
import com.rongmei.dao.proposal.ProposalDao;
import com.rongmei.entity.auction.Sale;
import com.rongmei.entity.auction.Thing;
import com.rongmei.entity.blindboxnft.BlindBoxNFT;
import com.rongmei.entity.blindboxnft.BlindBoxSale;
import com.rongmei.entity.child.SimpleChild;
import com.rongmei.entity.commodity.Commodity;
import com.rongmei.entity.draft.Draft;
import com.rongmei.entity.groupshopping.GroupShopping;
import com.rongmei.entity.proposal.Proposal;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.draft.DraftReviewParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.auction.SaleItem;
import com.rongmei.response.blindboxnft.BlindBoxSaleItem;
import com.rongmei.response.child.SimpleChildGetResponse;
import com.rongmei.response.commodity.CommodityItem;
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
import com.rongmei.util.UserInfoUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class DraftBlServiceImpl implements DraftBlService {

  private final DraftDao draftDao;
  private final SaleDao saleDao;
  private final ThingDao thingDao;
  private final CommodityDao commodityDao;
  private final BlindBoxSaleDao blindBoxSaleDao;
  private final BlindBoxNFTDao blindBoxNFTDao;
  private final ProposalDao proposalDao;
  private final GroupShoppingDao groupShoppingDao;
  private final SimpleChildDao simpleChildDao;

  public DraftBlServiceImpl(DraftDao draftDao, SaleDao saleDao,
      ThingDao thingDao, CommodityDao commodityDao,
      BlindBoxSaleDao blindBoxSaleDao,
      BlindBoxNFTDao blindBoxNFTDao, ProposalDao proposalDao,
      GroupShoppingDao groupShoppingDao, SimpleChildDao simpleChildDao) {
    this.draftDao = draftDao;
    this.saleDao = saleDao;
    this.thingDao = thingDao;
    this.commodityDao = commodityDao;
    this.blindBoxSaleDao = blindBoxSaleDao;
    this.blindBoxNFTDao = blindBoxNFTDao;
    this.proposalDao = proposalDao;
    this.groupShoppingDao = groupShoppingDao;
    this.simpleChildDao = simpleChildDao;
  }

  @Override
  public SuccessResponse reviewDraft(DraftReviewParameters parameter, String username)
      throws ThingIdDoesNotExistException {
    switch (parameter.getDraftType()) {
      case 0:
        Optional<Commodity> optionalCommodity = commodityDao
            .findById((int) parameter.getRelationId());
        if (optionalCommodity.isPresent()) {
          Commodity commodity = optionalCommodity.get();
          commodity.setDraftStatus(parameter.isApprove() ? 2 : 1);
          commodityDao.save(commodity);
        } else {
          throw new ThingIdDoesNotExistException();
        }
        break;
      case 1:
        Optional<Sale> optionalSale = saleDao.findById((int) parameter.getRelationId());
        if (optionalSale.isPresent()) {
          Sale sale = optionalSale.get();
          sale.setDraftStatus(parameter.isApprove() ? 2 : 1);
          saleDao.save(sale);
        } else {
          throw new ThingIdDoesNotExistException();
        }
        break;
      case 2:
        Optional<BlindBoxSale> optionalBlindBoxSale = blindBoxSaleDao
            .findById((int) parameter.getRelationId());
        if (optionalBlindBoxSale.isPresent()) {
          BlindBoxSale blindBoxSale = optionalBlindBoxSale.get();
          blindBoxSale.setDraftStatus(parameter.isApprove() ? 2 : 1);
          blindBoxSaleDao.save(blindBoxSale);
        } else {
          throw new ThingIdDoesNotExistException();
        }
        break;
      case 3:
        Optional<Proposal> optionalProposal = proposalDao
            .findById((int) parameter.getRelationId());
        if (optionalProposal.isPresent()) {
          Proposal proposal = optionalProposal.get();
          proposal.setDraftStatus(parameter.isApprove() ? 2 : 1);
          proposalDao.save(proposal);
        } else {
          throw new ThingIdDoesNotExistException();
        }
        break;
      case 4:
        Optional<GroupShopping> optionalGroupShopping = groupShoppingDao
            .findById((int) parameter.getRelationId());
        if (optionalGroupShopping.isPresent()) {
          GroupShopping groupShopping = optionalGroupShopping.get();
          groupShopping.setDraftStatus(parameter.isApprove() ? 2 : 1);
          groupShoppingDao.save(groupShopping);
        } else {
          throw new ThingIdDoesNotExistException();
        }
        break;
      case 5:
        Optional<SimpleChild> optionalSimpleChild = simpleChildDao
            .findById((int) parameter.getRelationId());
        if (optionalSimpleChild.isPresent()) {
          SimpleChild simpleChild = optionalSimpleChild.get();
          simpleChild.setDraftStatus(parameter.isApprove() ? 2 : 1);
          simpleChildDao.save(simpleChild);
        } else {
          throw new ThingIdDoesNotExistException();
        }
        break;
    }
    Draft draft = draftDao.findFirstByRelationIdAndDraftType(parameter.getRelationId(),
        parameter.getDraftType());
    if (draft == null) {
      draft = new Draft();
      draft.setRelationId(parameter.getRelationId());
      draft.setCreateTime(System.currentTimeMillis());
    }
    draft.setDraftType(parameter.getDraftType());
    draft.setMsg(parameter.getMsg());
    draftDao.save(draft);
    return new SuccessResponse("review success");
  }

  @Override
  public DraftCommodityGetResponse getDraftCommodity() {
    List<Commodity> commodityList = commodityDao.findAllByDraftStatusOrderByUpdateTimeDesc(0);
    return new DraftCommodityGetResponse(packCommodities(commodityList));
  }

  @Override
  public DraftSaleGetResponse getDraftSale() {
    List<Sale> saleList = saleDao.findAllByDraftStatusAndIsActiveOrderByCreateTimeDesc(0, true);
    return new DraftSaleGetResponse(packSaleItemList(saleList));
  }

  @Override
  public DraftBlindBoxSaleGetResponse getDraftBlindBoxSale() {
    List<BlindBoxSale> blindBoxSaleList = blindBoxSaleDao
        .findAllByDraftStatusAndIsActiveOrderByUpdateTimeDesc(0, true);
    return new DraftBlindBoxSaleGetResponse(packBlindBoxSaleItems(blindBoxSaleList));
  }

  @Override
  public DraftCommodityDetailResponse getDraftCommodityDetail(int relationId)
      throws ThingIdDoesNotExistException {
    Optional<Commodity> optionalCommodity = commodityDao.findById(relationId);
    if (optionalCommodity.isPresent()) {
      Commodity commodity = optionalCommodity.get();
      DraftCommodityDetailResponse response = new DraftCommodityDetailResponse(commodity.getId(),
          commodity.getTitle(), commodity.getLargePrice(), commodity.getCoverUrl(),
          commodity.getTags(), commodity.getContentUrl(), commodity.getDescription(),
          commodity.getSigningInfo(), commodity.getExtra(), commodity.getCreatorUserGroupId(),
          commodity.getDownloadUrl(), commodity.isExclusive(), commodity.getAuthor(),
          commodity.getDraftStatus(), "");
      Draft draft = draftDao.findFirstByRelationIdAndDraftType(relationId, 0);
      if (draft != null) {
        response.setMsg(draft.getMsg());
      }
      return response;
    } else {
      throw new ThingIdDoesNotExistException();
    }
  }

  @Override
  public DraftSaleDetailResponse getDraftSaleDetail(int relationId)
      throws ThingIdDoesNotExistException {
    Optional<Sale> optionalSale = saleDao.findById(relationId);
    if (optionalSale.isPresent()) {
      Sale sale = optionalSale.get();
      Thing thing = new Thing();
      Optional<Thing> optionalThing = thingDao.findById(sale.getThingId());
      if (optionalThing.isPresent()) {
        thing = optionalThing.get();
      }
      DraftSaleDetailResponse response = new DraftSaleDetailResponse(sale.getId(),
          sale.getStartPrice(), sale.getStatus(),
          sale.getIntervalPrice(), thing, sale.getCreateTime(), sale.getStartTime(),
          sale.getEndTime(), 0, 0, UserInfoUtil.getUserInfo(thing.getAuthor()),
          UserInfoUtil.getUserInfo(thing.getOwner()), sale.getTags(), sale.isNeedEarnestMoney(),
          sale.isEnableIntercept(), sale.isNeedCopyrightTax(), sale.getInterceptPrice(),
          sale.getRights(), sale.getDraftStatus(), "");
      Draft draft = draftDao.findFirstByRelationIdAndDraftType(relationId, 0);
      if (draft != null) {
        response.setMsg(draft.getMsg());
      }
      return response;
    } else {
      throw new ThingIdDoesNotExistException();
    }
  }

  @Override
  public DraftBlindBoxSaleDetailResponse getDraftBlindBoxSaleDetail(int relationId)
      throws ThingIdDoesNotExistException {
    Optional<BlindBoxSale> optionalBlindBoxSale = blindBoxSaleDao.findById(relationId);
    if (optionalBlindBoxSale.isPresent()) {
      BlindBoxSale blindBoxSale = optionalBlindBoxSale.get();
      Optional<BlindBoxNFT> optionalBlindBoxNFT = blindBoxNFTDao
          .findById(blindBoxSale.getBlindBoxNftId());
      if (!optionalBlindBoxNFT.isPresent()) {
        throw new ThingIdDoesNotExistException();
      }
      BlindBoxNFT blindBoxNFT = optionalBlindBoxNFT.get();
      DraftBlindBoxSaleDetailResponse response = new DraftBlindBoxSaleDetailResponse(
          blindBoxSale.getId(), blindBoxSale.getBlindBoxNftId(),
          blindBoxNFT.getName(),
          blindBoxNFT.getUrl(), blindBoxNFT.getPrice(), blindBoxNFT.getDescription(),
          blindBoxNFT.getAuthor(), blindBoxNFT.getOwner(), blindBoxNFT.getTokenId(),
          blindBoxNFT.getTheme(), blindBoxNFT.getClassification(), blindBoxNFT.getChainTxId(),
          blindBoxNFT.getConfirmationLetterUrl(), blindBoxNFT.getCreateTime(),
          blindBoxNFT.getUpdateTime(), blindBoxNFT.getIsActive(), blindBoxSale.getDraftStatus(),
          "");
      Draft draft = draftDao.findFirstByRelationIdAndDraftType(relationId, 0);
      if (draft != null) {
        response.setMsg(draft.getMsg());
      }
      return response;
    } else {
      throw new ThingIdDoesNotExistException();
    }
  }

  @Override
  public DraftResponse getDraftReason(int relationId, int draftType)
      throws ThingIdDoesNotExistException {
    Draft draft = draftDao.findFirstByRelationIdAndDraftType(relationId, draftType);
    if (draft == null) {
      throw new ThingIdDoesNotExistException();
    }
    return new DraftResponse(draft.getMsg());
  }

  @Override
  public GroupShoppingGetResponse getDraftGroupShopping() {
    List<Integer> draftStatus = new ArrayList<>();
    draftStatus.add(0);
    draftStatus.add(1);
    List<GroupShopping> groupShoppings = groupShoppingDao
        .findAllByDraftStatusInAndIsActive(draftStatus, true);
    long count = groupShoppingDao
        .countAllByDraftStatusInAndIsActive(draftStatus, true);
    return new GroupShoppingGetResponse(packGroupShoppingItems(groupShoppings), count);
  }

  @Override
  public DraftGroupShoppingDetailResponse getDraftGroupShoppingDetail(int relationId)
      throws ThingIdDoesNotExistException {
    Optional<GroupShopping> optionalGroupShopping = groupShoppingDao.findById(relationId);
    if (optionalGroupShopping.isPresent()) {
      GroupShopping groupShopping = optionalGroupShopping.get();
      DraftGroupShoppingDetailResponse response = new DraftGroupShoppingDetailResponse(
          groupShopping.getId(), groupShopping.getTitle(),
          groupShopping.getPrice(), groupShopping.getCoverUrl(), groupShopping.getTags(),
          groupShopping.getInformation(), groupShopping.getDescription(),
          groupShopping.getComment(), groupShopping.getStartTime(), groupShopping.getEndTime(),
          groupShopping.getCreateTime(), groupShopping.getUpdateTime(),
          groupShopping.getDraftStatus(), "");
      Draft draft = draftDao.findFirstByRelationIdAndDraftType(relationId, 4);
      if (draft != null) {
        response.setMsg(draft.getMsg());
      }
      return response;
    }
    throw new ThingIdDoesNotExistException();
  }

  @Override
  public ProposalGetResponse getDraftProposal() {
    List<Integer> draftStatus = new ArrayList<>();
    draftStatus.add(0);
    draftStatus.add(1);
    List<Proposal> proposals = proposalDao
        .findAllByDraftStatusInAndIsActive(draftStatus, true);
    return new ProposalGetResponse(packProposalItemsWithoutResult(proposals));
  }

  @Override
  public DraftProposalDetailResponse getDraftProposalDetail(int relationId)
      throws ThingIdDoesNotExistException {
    Optional<Proposal> optionalProposal = proposalDao.findById(relationId);
    if (optionalProposal.isPresent()) {
      Proposal proposal = optionalProposal.get();
      DraftProposalDetailResponse response = new DraftProposalDetailResponse(
          proposal.getId(), proposal.getContent(),
          proposal.getRelationId(),
          proposal.getProposalType(), proposal.getPassStandard(), proposal.getPromulgateStandard(),
          proposal.getStatus(), proposal.getStartTime(), proposal.getEndTime(),
          proposal.getCreateTime(), proposal.getUpdateTime(), proposal.getVisibility(),
          proposal.getPainterName(), proposal.getPainterSampleUrl(), proposal.getWorkPeriod(),
          proposal.getCompletionDegree(), proposal.getVoteEndTime(), proposal.getPrice(),
          0, 0, proposal.getDraftStatus(), "");
      Draft draft = draftDao.findFirstByRelationIdAndDraftType(relationId, 4);
      if (draft != null) {
        response.setMsg(draft.getMsg());
      }
      return response;
    }
    throw new ThingIdDoesNotExistException();
  }

  @Override
  public SimpleChildGetResponse getDraftChild() {
    List<Integer> draftStatus = new ArrayList<>();
    draftStatus.add(0);
    draftStatus.add(1);
    List<SimpleChild> simpleChildren = simpleChildDao
        .findAllByDraftStatusInAndIsActive(draftStatus, true);
    return new SimpleChildGetResponse(packSimpleChildItems(simpleChildren), simpleChildren.size());
  }

  @Override
  public DraftChildDetailResponse getDraftChildDetail(int relationId)
      throws ThingIdDoesNotExistException {
    Optional<SimpleChild> optionalSimpleChild = simpleChildDao.findById(relationId);
    if (optionalSimpleChild.isPresent()) {
      SimpleChild simpleChild = optionalSimpleChild.get();
      DraftChildDetailResponse response = new DraftChildDetailResponse(simpleChild.getId(),
          simpleChild.getCoverUrl(), simpleChild.getContent(), simpleChild.getTopics(),
          simpleChild.getCreateTime(), simpleChild.getUpdateTime(), simpleChild.getAuthor(),
          simpleChild.isActive(), simpleChild.getDraftStatus(), "");
      Draft draft = draftDao.findFirstByRelationIdAndDraftType(relationId, 5);
      if (draft != null) {
        response.setMsg(draft.getMsg());
      }
      return response;
    }
    throw new ThingIdDoesNotExistException();
  }

  private List<CommodityItem> packCommodities(List<Commodity> commodities) {
    List<CommodityItem> commodityItems = new ArrayList<>();
    for (Commodity commodity : commodities) {
      commodityItems.add(
          new CommodityItem(commodity.getId(), commodity.getTitle(), commodity.getCoverUrl(),
              commodity.getLargePrice(),
              commodity.getTags(), commodity.getCreateTime(), commodity.getUpdateTime(),
              commodity.isExclusive(), commodity.getAuthor(), commodity.getDescription()));
    }
    return commodityItems;
  }

  private List<SaleItem> packSaleItemList(List<Sale> sales) {
    List<SaleItem> saleItems = new ArrayList<>();
    for (Sale sale : sales) {
      Thing thing;
      Optional<Thing> optionalThing = thingDao.findById(sale.getThingId());
      if (optionalThing.isPresent()) {
        thing = optionalThing.get();
      } else {
        continue;
      }
      saleItems.add(new SaleItem(sale.getId(), sale.getStartPrice(), sale.getStatus(),
          sale.getIntervalPrice(), sale.getStartPrice(), thing,
          sale.getCreateTime(), sale.getStartTime(),
          sale.getEndTime(), sale.getTags(), sale.isNeedEarnestMoney()));
    }
    return saleItems;
  }

  private List<BlindBoxSaleItem> packBlindBoxSaleItems(List<BlindBoxSale> blindBoxSales) {
    List<BlindBoxSaleItem> blindBoxSaleItemList = new ArrayList<>();
    for (BlindBoxSale blindBoxSale : blindBoxSales) {
      Optional<BlindBoxNFT> optionalBlindBoxNFT = blindBoxNFTDao
          .findById(blindBoxSale.getBlindBoxNftId());
      if (optionalBlindBoxNFT.isPresent()) {
        BlindBoxNFT blindBoxNFT = optionalBlindBoxNFT.get();
        blindBoxSaleItemList.add(
            new BlindBoxSaleItem(blindBoxSale.getId(), blindBoxSale.getBlindBoxNftId(),
                blindBoxNFT.getName(), blindBoxNFT.getUrl(), blindBoxNFT.getPrice(),
                blindBoxNFT.getDescription(), blindBoxNFT.getTheme(),
                blindBoxNFT.getClassification(), blindBoxNFT.getCreateTime(),
                blindBoxNFT.getUpdateTime(), blindBoxNFT.getIsActive()));
      }
    }
    return blindBoxSaleItemList;
  }
}
