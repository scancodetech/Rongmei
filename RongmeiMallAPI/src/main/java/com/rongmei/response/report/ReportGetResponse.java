package com.rongmei.response.report;

import com.rongmei.response.Response;
import java.util.List;

public class ReportGetResponse extends Response {

  private List<ReportItem> reportItemList;
  private long count;

  public ReportGetResponse() {
  }

  public ReportGetResponse(List<ReportItem> reportItemList, long count) {
    this.reportItemList = reportItemList;
    this.count = count;
  }

  public List<ReportItem> getReportItemList() {
    return reportItemList;
  }

  public void setReportItemList(List<ReportItem> reportItemList) {
    this.reportItemList = reportItemList;
  }

  public long getCount() {
    return count;
  }

  public void setCount(long count) {
    this.count = count;
  }
}
