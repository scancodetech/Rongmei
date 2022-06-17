package com.rongmei.blservice.event;

import com.rongmei.entity.eventorder.EventOrder;
import com.rongmei.exception.SystemException;
import com.rongmei.response.Response;
import com.rongmei.response.SuccessResponse;

public interface EventOrderService {
    Response save(EventOrder eventOrder) throws SystemException;

}
