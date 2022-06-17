package com.rongmei.blservice.metrics;

import com.rongmei.parameters.metrics.MetricsAddParameters;
import com.rongmei.parameters.metrics.MetricsCountParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.metrics.MetricsBatchCountResponse;
import com.rongmei.response.metrics.MetricsCountResponse;

public interface MetricsBlService {

  SuccessResponse addMetrics(MetricsAddParameters parameters);

  MetricsCountResponse getCountMetrics(String key);

  MetricsBatchCountResponse getBatchCountMetrics(MetricsCountParameters parameters);

  SuccessResponse addSingleMetrics(MetricsAddParameters parameters);
}
