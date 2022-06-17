package com.rongmei.bl.event;

import com.rongmei.blservice.event.EventBlService;
import com.rongmei.dao.event.EventDao;
import com.rongmei.parameters.event.EventApplyParameters;
import com.rongmei.parameters.event.EventApplyUploadParameters;
import com.rongmei.parameters.event.EventUploadParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.event.EventGetResponse;
import org.springframework.stereotype.Service;

@Service
public class EventBlServiceImpl implements EventBlService {
  private final EventDao eventDao;

  public EventBlServiceImpl(EventDao eventDao) {
    this.eventDao = eventDao;
  }

  @Override
  public SuccessResponse uploadEvent(EventUploadParameters parameter, String username) {
    return null;
  }

  @Override
  public SuccessResponse applyEvent(EventApplyParameters parameter, String username) {
    return null;
  }

  @Override
  public SuccessResponse uploadApplyEvent(EventApplyUploadParameters parameter, String username) {
    return null;
  }

  @Override
  public SuccessResponse deleteEvent(int eventId) {
    return null;
  }

  @Override
  public EventGetResponse getEvents(int status, String username) {
    return null;
  }
}
