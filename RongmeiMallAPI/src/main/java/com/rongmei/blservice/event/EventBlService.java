package com.rongmei.blservice.event;

import com.rongmei.parameters.event.EventApplyParameters;
import com.rongmei.parameters.event.EventApplyUploadParameters;
import com.rongmei.parameters.event.EventUploadParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.event.EventGetResponse;

public interface EventBlService {

  SuccessResponse uploadEvent(EventUploadParameters parameter, String username);

  SuccessResponse applyEvent(EventApplyParameters parameter, String username);

  SuccessResponse uploadApplyEvent(EventApplyUploadParameters parameter, String username);

  SuccessResponse deleteEvent(int eventId);

  EventGetResponse getEvents(int status, String username);
}
