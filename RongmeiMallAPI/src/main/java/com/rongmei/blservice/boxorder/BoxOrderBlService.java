package com.rongmei.blservice.boxorder;

import com.rongmei.exception.ParametersErrorException;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.boxorder.BoxOrderCommentParameters;
import com.rongmei.parameters.boxorder.BoxOrderRequestSubmitParameters;
import com.rongmei.parameters.boxorder.BoxOrderResultCoverUrlSubmitParameters;
import com.rongmei.parameters.boxorder.BoxOrderResultUrlSubmitParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.boxorder.BoxOrderDetailResponse;
import com.rongmei.response.boxorder.BoxOrderGetResponse;
import com.rongmei.response.boxorder.BoxOrderShareGetResponse;

public interface BoxOrderBlService {

  /**
   * @param status 0:抢单 1:制作中 2:反馈 3:已完成
   * @param page
   * @param limit
   * @return
   */
  BoxOrderGetResponse queryBoxOrder(int status, int page, int limit);

  SuccessResponse submitBoxOrderRequest(BoxOrderRequestSubmitParameters parameters);

  SuccessResponse grabBoxOrder(long orderId, String username) throws ThingIdDoesNotExistException;

  SuccessResponse submitBoxOrderResultCover(BoxOrderResultCoverUrlSubmitParameters parameters)
      throws ThingIdDoesNotExistException;

  SuccessResponse submitBoxOrderResult(BoxOrderResultUrlSubmitParameters parameters)
      throws ThingIdDoesNotExistException;

  SuccessResponse commentBoxOrder(BoxOrderCommentParameters parameters)
      throws ThingIdDoesNotExistException;

  /**
   * @param status 0:待付款 1:待分享 2:待接单 3:待反馈 4:已完成
   * @param page
   * @param limit
   * @return
   */
  BoxOrderGetResponse queryUserBoxOrderRequest(int status, String username, int page, int limit);

  SuccessResponse shareBoxOrderWithUser(int orderId, String username)
      throws ThingIdDoesNotExistException, ParametersErrorException;

  BoxOrderShareGetResponse getSharingBoxOrderWithoutUser(String username);

  BoxOrderDetailResponse getBoxOrder(int orderId) throws ThingIdDoesNotExistException;
}
