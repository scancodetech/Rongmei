package com.rongmei.bl.report;

import com.rongmei.blservice.report.ReportBlService;
import com.rongmei.dao.report.ReportDao;
import com.rongmei.entity.report.Report;
import com.rongmei.parameters.report.ReportSubmitParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.report.ReportGetResponse;
import com.rongmei.response.report.ReportItem;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportBlServiceImpl implements ReportBlService {

  private final ReportDao reportDao;

  @Autowired
  public ReportBlServiceImpl(ReportDao reportDao) {
    this.reportDao = reportDao;
  }

  @Override
  public SuccessResponse submitReport(ReportSubmitParameters parameters, String username) {
    Report report = new Report(parameters.getReason(), parameters.getDescription(),
        parameters.getImageUrls(), username, parameters.getRelationId(), System.currentTimeMillis(),
        System.currentTimeMillis());
    reportDao.save(report);
    return new SuccessResponse("submit success");
  }

  @Override
  public ReportGetResponse getReport(int page, int limit) {
    List<Report> reportList = reportDao.findAllByLimitAndPage(limit, (page - 1) * limit);
    long count = reportDao.count();
    List<ReportItem> reportItemList = new ArrayList<>();
    for (Report report : reportList) {
      reportItemList.add(new ReportItem(report.getId(), report.getReason(), report.getDescription(),
          report.getImageUrls(), report.getUsername(), report.getRelationId(),
          report.getCreateTime(), report.getUpdateTime()));
    }
    return new ReportGetResponse(reportItemList, count);
  }
}
