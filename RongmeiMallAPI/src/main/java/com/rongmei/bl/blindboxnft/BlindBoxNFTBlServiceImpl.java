package com.rongmei.bl.blindboxnft;

import com.rongmei.blservice.blindboxnft.BlindBoxNFTBlService;
import com.rongmei.dao.blindboxnft.BlindBoxNFTDao;
import com.rongmei.entity.blindboxnft.BlindBoxNFT;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.blindboxnft.BlindBoxNftTransferParameters;
import com.rongmei.parameters.blindboxnft.BlindBoxNftUpdateParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.blindboxnft.BlindBoxNFTDetailResponse;
import com.rongmei.response.blindboxnft.BlindBoxNFTGetResponse;
import com.rongmei.response.blindboxnft.BlindBoxNFTItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlindBoxNFTBlServiceImpl implements BlindBoxNFTBlService {

  private final BlindBoxNFTDao blindBoxNFTDao;

  @Autowired
  public BlindBoxNFTBlServiceImpl(BlindBoxNFTDao blindBoxNFTDao) {
    this.blindBoxNFTDao = blindBoxNFTDao;
  }

  @Override
  public SuccessResponse updateNFT(BlindBoxNftUpdateParameters parameters, String username)
      throws ThingIdDoesNotExistException {
    if (parameters.getId() == 0) {
      BlindBoxNFT blindBoxNFT = new BlindBoxNFT(parameters.getName(), parameters.getUrl(),
          parameters.getPrice(), parameters.getDescription(),
          parameters.getAuthor(), parameters.getOwner(), parameters.getTokenId(),
          parameters.getTheme(), parameters.getClassification(), parameters.getChainTxId(),
          parameters.getConfirmationLetterUrl(), System.currentTimeMillis(),
          System.currentTimeMillis(), false);
      blindBoxNFTDao.save(blindBoxNFT);
      return new SuccessResponse("add success");
    } else {
      Optional<BlindBoxNFT> optionalBlindBoxNFT = blindBoxNFTDao.findById(parameters.getId());
      if (optionalBlindBoxNFT.isPresent()) {
        BlindBoxNFT blindBoxNFT = optionalBlindBoxNFT.get();
        blindBoxNFT.setName(parameters.getName());
        blindBoxNFT.setUrl(parameters.getUrl());
        blindBoxNFT.setPrice(parameters.getPrice());
        blindBoxNFT.setDescription(parameters.getDescription());
        blindBoxNFT.setAuthor(parameters.getAuthor());
        blindBoxNFT.setOwner(parameters.getOwner());
        blindBoxNFT.setTokenId(parameters.getTokenId());
        blindBoxNFT.setTheme(blindBoxNFT.getTheme());
        blindBoxNFT.setClassification(blindBoxNFT.getClassification());
        blindBoxNFT.setChainTxId(blindBoxNFT.getChainTxId());
        blindBoxNFT.setConfirmationLetterUrl(blindBoxNFT.getConfirmationLetterUrl());
        blindBoxNFT.setUpdateTime(System.currentTimeMillis());
        blindBoxNFTDao.save(blindBoxNFT);
      } else {
        throw new ThingIdDoesNotExistException();
      }
      return new SuccessResponse("update success");
    }
  }

  @Override
  public SuccessResponse transferNFT(BlindBoxNftTransferParameters parameters)
      throws ThingIdDoesNotExistException {
    BlindBoxNFT blindBoxNFT = blindBoxNFTDao.findFirstByTokenId(parameters.getTokenId());
    if (blindBoxNFT == null) {
      throw new ThingIdDoesNotExistException();
    }
    blindBoxNFT.setOwner(parameters.getToUsername());
    blindBoxNFTDao.save(blindBoxNFT);
    return new SuccessResponse("transfer success");
  }

  @Override
  public SuccessResponse deleteNFT(int nftId) throws ThingIdDoesNotExistException {
    Optional<BlindBoxNFT> optionalBlindBoxNFT = blindBoxNFTDao.findById(nftId);
    if (optionalBlindBoxNFT.isPresent()) {
      BlindBoxNFT blindBoxNFT = optionalBlindBoxNFT.get();
      blindBoxNFT.setUpdateTime(System.currentTimeMillis());
      blindBoxNFT.setIsActive(false);
      blindBoxNFTDao.save(blindBoxNFT);
    } else {
      throw new ThingIdDoesNotExistException();
    }
    return new SuccessResponse("delete success");
  }

  @Override
  public BlindBoxNFTGetResponse getBlindBoxList(String theme, String classification) {
    List<BlindBoxNFT> blindBoxNFTList = blindBoxNFTDao
        .findAllByThemeAndClassification(theme, classification);
    return new BlindBoxNFTGetResponse(packBlindBoxNFTItems(blindBoxNFTList));
  }

  @Override
  public BlindBoxNFTGetResponse getMineBlindBoxNFTList(String username, int page, int limit) {
    List<BlindBoxNFT> blindBoxNFTList = blindBoxNFTDao
        .findAllByOwnerLimitOffset(username, limit, limit * page);
    return new BlindBoxNFTGetResponse(packBlindBoxNFTItems(blindBoxNFTList));
  }

  @Override
  public BlindBoxNFTDetailResponse getBlindBoxNFT(int nftId) throws ThingIdDoesNotExistException {
    Optional<BlindBoxNFT> optionalBlindBoxNFT = blindBoxNFTDao.findById(nftId);
    if (optionalBlindBoxNFT.isPresent()) {
      BlindBoxNFT blindBoxNFT = optionalBlindBoxNFT.get();
      return new BlindBoxNFTDetailResponse(blindBoxNFT.getId(), blindBoxNFT.getName(),
          blindBoxNFT.getUrl(), blindBoxNFT.getPrice(), blindBoxNFT.getDescription(),
          blindBoxNFT.getAuthor(), blindBoxNFT.getOwner(), blindBoxNFT.getTokenId(),
          blindBoxNFT.getTheme(), blindBoxNFT.getClassification(), blindBoxNFT.getChainTxId(),
          blindBoxNFT.getConfirmationLetterUrl(), blindBoxNFT.getCreateTime(),
          blindBoxNFT.getUpdateTime(), blindBoxNFT.getIsActive());
    } else {
      throw new ThingIdDoesNotExistException();
    }
  }

  private List<BlindBoxNFTItem> packBlindBoxNFTItems(List<BlindBoxNFT> blindBoxNFTList) {
    List<BlindBoxNFTItem> blindBoxNFTItems = new ArrayList<>();
    for (BlindBoxNFT blindBoxNFT : blindBoxNFTList) {
      blindBoxNFTItems.add(
          new BlindBoxNFTItem(blindBoxNFT.getId(), blindBoxNFT.getName(), blindBoxNFT.getUrl(),
              blindBoxNFT.getPrice(), blindBoxNFT.getDescription(), blindBoxNFT.getTheme(),
              blindBoxNFT.getClassification(), blindBoxNFT.getCreateTime(),
              blindBoxNFT.getUpdateTime(), blindBoxNFT.getIsActive()));
    }
    return blindBoxNFTItems;
  }
}
