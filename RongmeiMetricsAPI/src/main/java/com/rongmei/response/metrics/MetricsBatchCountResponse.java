package com.rongmei.response.metrics;

import com.rongmei.response.Response;
import java.util.List;

public class MetricsBatchCountResponse extends Response {

  private List<MetricsItem> counts;

  public MetricsBatchCountResponse() {
  }

  public MetricsBatchCountResponse(List<MetricsItem> counts) {
    this.counts = counts;
  }

  public List<MetricsItem> getCounts() {
    return counts;
  }

  public void setCounts(List<MetricsItem> counts) {
    this.counts = counts;
  }
}
