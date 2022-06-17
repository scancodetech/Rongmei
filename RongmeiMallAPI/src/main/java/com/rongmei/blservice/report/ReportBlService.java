package com.rongmei.blservice.report;

import com.rongmei.parameters.report.ReportSubmitParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.report.ReportGetResponse;

public interface ReportBlService {

  SuccessResponse submitReport(ReportSubmitParameters parameters, String username);

  ReportGetResponse getReport(int page, int limit);
}
