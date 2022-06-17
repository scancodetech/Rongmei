package com.rongmei.blservice.certification;

import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.certification.UserCertificationApproveParameters;
import com.rongmei.parameters.certification.UserCertificationUpdateParameters;
import com.rongmei.parameters.certification.UserMasterpieceApproveParameters;
import com.rongmei.parameters.certification.UserMasterpieceUpdateParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.certification.CertificationResponse;
import com.rongmei.response.certification.UserCertificationGetResponse;
import com.rongmei.response.certification.UserMasterpieceGetResponse;

public interface CertificationBlService {

  SuccessResponse updateUserCertification(UserCertificationUpdateParameters parameters)
      throws ThingIdDoesNotExistException;

  UserCertificationGetResponse getAllUserCertification();

  SuccessResponse approveUserCertification(UserCertificationApproveParameters parameters)
      throws ThingIdDoesNotExistException;

  SuccessResponse updateMasterpieceCertification(UserMasterpieceUpdateParameters parameters)
      throws ThingIdDoesNotExistException;

  UserMasterpieceGetResponse getAllUserMasterpiece();

  SuccessResponse approveMasterpieceCertification(UserMasterpieceApproveParameters parameters)
      throws ThingIdDoesNotExistException;

  CertificationResponse getMineCertification(String certificationType);
}
