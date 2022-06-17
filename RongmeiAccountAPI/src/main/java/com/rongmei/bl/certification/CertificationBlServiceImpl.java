package com.rongmei.bl.certification;

import com.rongmei.blservice.certification.CertificationBlService;
import com.rongmei.dao.certification.UserCertificationDao;
import com.rongmei.dao.certification.UserMasterpieceDao;
import com.rongmei.entity.certification.UserCertification;
import com.rongmei.entity.certification.UserMasterpiece;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.certification.UserCertificationApproveParameters;
import com.rongmei.parameters.certification.UserCertificationUpdateParameters;
import com.rongmei.parameters.certification.UserMasterpieceApproveParameters;
import com.rongmei.parameters.certification.UserMasterpieceUpdateParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.certification.CertificationResponse;
import com.rongmei.response.certification.UserCertificationGetResponse;
import com.rongmei.response.certification.UserMasterpieceGetResponse;
import com.rongmei.util.UserInfoUtil;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CertificationBlServiceImpl implements CertificationBlService {

  private final UserCertificationDao userCertificationDao;
  private final UserMasterpieceDao userMasterpieceDao;

  @Autowired
  public CertificationBlServiceImpl(
      UserCertificationDao userCertificationDao,
      UserMasterpieceDao userMasterpieceDao) {
    this.userCertificationDao = userCertificationDao;
    this.userMasterpieceDao = userMasterpieceDao;
  }

  @Override
  public SuccessResponse updateUserCertification(UserCertificationUpdateParameters parameters)
      throws ThingIdDoesNotExistException {
    if (parameters.getId() == 0) {
      UserCertification userCertification = new UserCertification(parameters.getCertificationType(),
          UserInfoUtil.getUsername(), parameters.getName(), parameters.getUserTypes(),
          parameters.getAvatarUrl(), parameters.getPosition(), parameters.getEmail(),
          parameters.getOuterPlatforms(), parameters.getHowToUse(), parameters.getHowToKnow(),
          parameters.getGender(), parameters.getBirthday(), parameters.getAddress(),
          parameters.getWechat(), parameters.getQq(), parameters.getDescription(),
          System.currentTimeMillis(), System.currentTimeMillis(), 0, "");
      userCertificationDao.save(userCertification);
      return new SuccessResponse("add success");
    } else {
      Optional<UserCertification> optionalUserCertification = userCertificationDao
          .findById(parameters.getId());
      if (optionalUserCertification.isPresent()) {
        UserCertification userCertification = optionalUserCertification.get();
        userCertification.setName(parameters.getName());
        userCertification.setUserTypes(parameters.getUserTypes());
        userCertification.setAvatarUrl(parameters.getAvatarUrl());
        userCertification.setPosition(parameters.getPosition());
        userCertification.setEmail(parameters.getEmail());
        userCertification.setOuterPlatforms(parameters.getOuterPlatforms());
        userCertification.setHowToUse(parameters.getHowToUse());
        userCertification.setHowToKnow(parameters.getHowToKnow());
        userCertification.setGender(parameters.getGender());
        userCertification.setBirthday(parameters.getBirthday());
        userCertification.setAddress(parameters.getAddress());
        userCertification.setWechat(parameters.getWechat());
        userCertification.setQq(parameters.getQq());
        userCertification.setDescription(parameters.getDescription());
        userCertification.setUpdateTime(System.currentTimeMillis());
        userCertificationDao.save(userCertification);
      } else {
        throw new ThingIdDoesNotExistException();
      }
      return new SuccessResponse("update success");
    }
  }

  @Override
  public UserCertificationGetResponse getAllUserCertification() {
    List<UserCertification> userCertificationList = userCertificationDao.findAll();
    return new UserCertificationGetResponse(userCertificationList);
  }

  @Override
  public SuccessResponse approveUserCertification(UserCertificationApproveParameters parameters)
      throws ThingIdDoesNotExistException {
    Optional<UserCertification> optionalUserCertification = userCertificationDao
        .findById(parameters.getId());
    if (optionalUserCertification.isPresent()) {
      UserCertification userCertification = optionalUserCertification.get();
      userCertification.setStatus(parameters.isApprove() ? 1 : 2);
      userCertification.setApproveMsg(parameters.getApproveMsg());
      userCertification.setUpdateTime(System.currentTimeMillis());
      userCertificationDao.save(userCertification);
      return new SuccessResponse("approve success");
    } else {
      throw new ThingIdDoesNotExistException();
    }
  }

  @Override
  public SuccessResponse updateMasterpieceCertification(
      UserMasterpieceUpdateParameters parameters) throws ThingIdDoesNotExistException {
    if (parameters.getId() == 0) {
      UserMasterpiece userMasterpiece = new UserMasterpiece(parameters.getCertificationType(),
          UserInfoUtil.getUsername(),
          parameters.getImageZipUrl(), parameters.getCoverUrl(), parameters.getTitle(),
          parameters.getEnTitle(),
          parameters.getPieceType(), parameters.getPieceStyle(), parameters.getPieceDate(),
          parameters.getDescription(), parameters.getOuterPlatforms(),
          parameters.getAuthorizationUrl(), System.currentTimeMillis(), System.currentTimeMillis(),
          0, "");
      userMasterpieceDao.save(userMasterpiece);
      return new SuccessResponse("add success");
    } else {
      Optional<UserMasterpiece> optionalUserMasterpiece = userMasterpieceDao
          .findById(parameters.getId());
      if (optionalUserMasterpiece.isPresent()) {
        UserMasterpiece userMasterpiece = optionalUserMasterpiece.get();
        userMasterpiece.setImageZipUrl(userMasterpiece.getImageZipUrl());
        userMasterpiece.setCoverUrl(userMasterpiece.getCoverUrl());
        userMasterpiece.setTitle(parameters.getTitle());
        userMasterpiece.setEnTitle(parameters.getEnTitle());
        userMasterpiece.setPieceDate(parameters.getPieceType());
        userMasterpiece.setPieceStyle(parameters.getPieceStyle());
        userMasterpiece.setPieceDate(parameters.getPieceDate());
        userMasterpiece.setDescription(parameters.getDescription());
        userMasterpiece.setOuterPlatforms(parameters.getOuterPlatforms());
        userMasterpiece.setAuthorizationUrl(parameters.getAuthorizationUrl());
        userMasterpiece.setUpdateTime(System.currentTimeMillis());
        userMasterpieceDao.save(userMasterpiece);
      } else {
        throw new ThingIdDoesNotExistException();
      }
      return new SuccessResponse("update success");
    }
  }

  @Override
  public UserMasterpieceGetResponse getAllUserMasterpiece() {
    List<UserMasterpiece> userMasterpieceList = userMasterpieceDao.findAll();
    return new UserMasterpieceGetResponse(userMasterpieceList);
  }

  @Override
  public SuccessResponse approveMasterpieceCertification(
      UserMasterpieceApproveParameters parameters) throws ThingIdDoesNotExistException {
    Optional<UserMasterpiece> optionalUserMasterpiece = userMasterpieceDao
        .findById(parameters.getId());
    if (optionalUserMasterpiece.isPresent()) {
      UserMasterpiece userMasterpiece = optionalUserMasterpiece.get();
      userMasterpiece.setStatus(parameters.isApprove() ? 1 : 2);
      userMasterpiece.setApproveMsg(parameters.getApproveMsg());
      userMasterpiece.setUpdateTime(System.currentTimeMillis());
      userMasterpieceDao.save(userMasterpiece);
      return new SuccessResponse("approve success");
    } else {
      throw new ThingIdDoesNotExistException();
    }
  }

  @Override
  public CertificationResponse getMineCertification(String certificationType) {
    String username = UserInfoUtil.getUsername();
    CertificationResponse certificationResponse = new CertificationResponse();
    UserCertification userCertification = userCertificationDao
        .findFirstByUsernameAndCertificationType(username, certificationType);
    UserMasterpiece userMasterpiece = userMasterpieceDao
        .findFirstByUsernameAndCertificationType(username, certificationType);
    if (userCertification == null) {
      certificationResponse.setUserCertificationChecked(false);
    } else {
      certificationResponse.setUserCertification(userCertification);
      certificationResponse.setUserCertificationChecked(userCertification.getStatus() == 1);
      certificationResponse.setUserTypes(userCertification.getUserTypes());
    }
    if (userMasterpiece == null) {
      certificationResponse.setUserMasterpieceChecked(false);
    } else {
      certificationResponse.setUserMasterpiece(userMasterpiece);
      certificationResponse.setUserMasterpieceChecked(userMasterpiece.getStatus() == 1);
    }
    return certificationResponse;
  }
}
