package com.rongmei.blservice.notice;

import com.rongmei.parameters.notice.NoticeSendParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.notice.NoticeGetResponse;

public interface NoticeBlService {

  SuccessResponse sendNotice(NoticeSendParameters parameters);

  NoticeGetResponse getMineNotice(int type);

  NoticeGetResponse getMineAllNotice(int page, int limit);
}
