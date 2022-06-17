package com.rongmei.blservice.groupshopping;

import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.groupshopping.GroupShoppingUpdateParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.groupshopping.GroupShoppingDetailResponse;
import com.rongmei.response.groupshopping.GroupShoppingGetResponse;

public interface GroupShoppingBlService {

  GroupShoppingGetResponse getGroupShoppings(int page, int limit, int draftStatus);

  GroupShoppingGetResponse getUserGroupShoppings(int page, int limit, String username, int status);

  GroupShoppingDetailResponse getGroupShopping(int id) throws ThingIdDoesNotExistException;

  SuccessResponse deleteGroupShopping(int id) throws ThingIdDoesNotExistException;

  SuccessResponse participateGroupShopping(int id, String username);

  GroupShoppingGetResponse getRecommendGroupShoppings(int page, int limit);

  SuccessResponse updateGroupShopping(GroupShoppingUpdateParameters parameters);

  GroupShoppingGetResponse getAuthorGroupShoppings(String username, int status);

  GroupShoppingGetResponse getUserParticipateGroupShoppings(String username);
}
