package com.rongmei.bl.notice;

import com.rongmei.blservice.notice.NoticeBlService;
import com.rongmei.dao.notice.NoticeDao;
import com.rongmei.entity.notice.Notice;
import com.rongmei.parameters.notice.NoticeSendParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.notice.NoticeGetResponse;
import com.rongmei.response.notice.NoticeItem;
import com.rongmei.util.UserInfoUtil;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoticeBlServiceImpl implements NoticeBlService {

  private final NoticeDao noticeDao;

  @Autowired
  public NoticeBlServiceImpl(NoticeDao noticeDao) {
    this.noticeDao = noticeDao;
  }

  @Override
  public SuccessResponse sendNotice(NoticeSendParameters parameters) {
    if (parameters.getFromUsername().equals("")) {
      parameters.setFromUsername(UserInfoUtil.getUsername());
    }

    Notice notice = new Notice(parameters.getTitle(), parameters.getContent(),
        parameters.getFromUsername(), parameters.getToUsername(), parameters.getType(),
        System.currentTimeMillis());
    noticeDao.save(notice);
    return new SuccessResponse("send success");
  }

  @Override
  public NoticeGetResponse getMineNotice(int type) {
    List<Notice> notices = noticeDao
        .findAllByToUsernameOrderByCreateTimeDesc(UserInfoUtil.getUsername());
    List<NoticeItem> noticeItems = new ArrayList<>();
    for (Notice notice : notices) {
      noticeItems.add(
          new NoticeItem(notice.getId(), notice.getType(), notice.getTitle(), notice.getContent(),
              notice.getFromUsername(), notice.getCreateTime()));
    }
    return new NoticeGetResponse(noticeItems, noticeItems.size());
  }

  @Override
  public NoticeGetResponse getMineAllNotice(int page, int limit) {
    String username = UserInfoUtil.getUsername();
    List<Notice> notices = noticeDao
        .findAllByToUsernameOrderByCreateTimeDescAndLimitOffset(username, limit,
            (page - 1) * limit);
    long total = noticeDao.countAllByToUsername(username);
    List<NoticeItem> noticeItems = new ArrayList<>();
    for (Notice notice : notices) {
      noticeItems.add(
          new NoticeItem(notice.getId(), notice.getType(), notice.getTitle(), notice.getContent(),
              notice.getFromUsername(), notice.getCreateTime()));
    }
    return new NoticeGetResponse(noticeItems, total);
  }
}
