package com.rongmei.bl.distribution;

import com.rongmei.blservice.distribution.DistributionBlService;
import com.rongmei.dao.distribution.DistributionDao;
import com.rongmei.dao.distribution.UserDistributionDao;
import com.rongmei.dao.distribution.UserDistributionScoreDao;
import com.rongmei.entity.distribution.Distribution;
import com.rongmei.entity.distribution.UserDistribution;
import com.rongmei.entity.distribution.UserDistributionScore;
import com.rongmei.exception.UsernameDoesNotFoundException;
import com.rongmei.paramaters.distribution.DistributionParameters;
import com.rongmei.paramaters.distribution.UserDistributionUpdateParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.distribution.UserDistributionDetailResponse;
import com.rongmei.util.PathUtil;
import com.rongmei.util.QrCodeUtil;
import com.rongmei.util.RandomUtil;
import com.rongmei.util.UserInfoUtil;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DistributionBlServiceImpl implements DistributionBlService {

  private final DistributionDao distributionDao;
  private final UserDistributionDao userDistributionDao;
  private final UserDistributionScoreDao userDistributionScoreDao;
  @Value(value = "${qrcode.url}")
  private String SHARE_CODE_PREFIX_URL;
  @Value(value = "${file.url}")
  private String FILE_PREFIX_URL;

  @Autowired
  public DistributionBlServiceImpl(DistributionDao distributionDao,
      UserDistributionDao userDistributionDao,
      UserDistributionScoreDao userDistributionScoreDao) {
    this.distributionDao = distributionDao;
    this.userDistributionDao = userDistributionDao;
    this.userDistributionScoreDao = userDistributionScoreDao;
  }

  @Override
  public synchronized UserDistributionDetailResponse getUserDistribution(String username)
      throws UsernameDoesNotFoundException {
    UserDistribution userDistribution = userDistributionDao.findFirstByUsername(username);
    if (userDistribution == null) {
      Distribution distribution = distributionDao.findFirstByToUsername(username);
      if (distribution == null) {
        throw new UsernameDoesNotFoundException();
      }
      UserDistribution fromUserDistribution = userDistributionDao
          .findFirstByUsername(distribution.getFromUsername());
      updateUserDistribution(
          new UserDistributionUpdateParameters(username, fromUserDistribution.getLevel() + 1));
      userDistribution = userDistributionDao.findFirstByUsername(username);
    }
    UserDistributionScore userDistributionScore = userDistributionScoreDao
        .findFirstByUsername(username);
    if (userDistributionScore == null) {
      userDistributionScore = new UserDistributionScore(0, username, 0);
      userDistributionScoreDao.save(userDistributionScore);
    }
    List<Distribution> distributions = distributionDao.findAllByFromUsername(username);
    List<UserDistributionDetailResponse> children = new ArrayList<>();
//    for (Distribution distribution : distributions) {
//      children.add(getUserDistribution(distribution.getToUsername()));
//    }
    return new UserDistributionDetailResponse(userDistribution.getId(), username,
        userDistribution.getCode(), userDistribution.getQrcodeUrl(), userDistribution.getLevel(),
        userDistribution.getCreateTime(), userDistribution.getUpdateTime(),
        userDistributionScore.getScore(), children);
  }

  @Override
  public SuccessResponse distribution(DistributionParameters parameters)
      throws UsernameDoesNotFoundException {
    String toUsername = parameters.getUsername().length() == 0 ? UserInfoUtil.getUsername()
        : parameters.getUsername();
    UserDistribution userDistribution = userDistributionDao.findFirstByCode(parameters.getCode());
    if (userDistribution == null) {
      throw new UsernameDoesNotFoundException();
    }
    Distribution distribution = new Distribution(0, userDistribution.getUsername(),
        toUsername, System.currentTimeMillis());
    distributionDao.save(distribution);
    updateUserDistribution(
        new UserDistributionUpdateParameters(toUsername, userDistribution.getLevel() + 1));
    return new SuccessResponse("distribute success");
  }

  @Override
  public SuccessResponse updateUserDistribution(UserDistributionUpdateParameters parameters) {
    String username = parameters.getUsername().length() == 0 ? UserInfoUtil.getUsername()
        : parameters.getUsername();
    UserDistribution userDistribution = userDistributionDao
        .findFirstByUsername(username);
    if (userDistribution != null) {
      userDistribution.setLevel(parameters.getLevel());
      userDistribution.setUpdateTime(System.currentTimeMillis());
      userDistributionDao.save(userDistribution);
      return new SuccessResponse("update success");
    } else {
      String shareCode;
      do {
        shareCode = RandomUtil.generateShareCode();
      } while (userDistributionDao.findFirstByCode(shareCode) != null);
      String qrcodeSourceUrl = SHARE_CODE_PREFIX_URL + shareCode;
      QrCodeUtil.createQrCode(qrcodeSourceUrl, PathUtil.getStaticPath(), shareCode);
      String qrcodeUrl = FILE_PREFIX_URL + shareCode;
      userDistribution = new UserDistribution(0, username, shareCode, qrcodeUrl,
          parameters.getLevel(), System.currentTimeMillis(), System.currentTimeMillis());
      userDistributionDao.save(userDistribution);
      return new SuccessResponse("add success");
    }
  }
}
