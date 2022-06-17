package com.rongmei.bl.metrics;

import com.rongmei.blservice.metrics.MetricsBlService;
import com.rongmei.dao.metrics.MetricsDao;
import com.rongmei.entity.metrics.Metrics;
import com.rongmei.parameters.metrics.MetricsAddParameters;
import com.rongmei.parameters.metrics.MetricsCountParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.metrics.MetricsBatchCountResponse;
import com.rongmei.response.metrics.MetricsCountResponse;
import com.rongmei.response.metrics.MetricsItem;
import com.rongmei.util.UserInfoUtil;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetricsBlServiceImpl implements MetricsBlService {

  private final MetricsDao metricsDao;

  @Autowired
  public MetricsBlServiceImpl(MetricsDao metricsDao) {
    this.metricsDao = metricsDao;
  }

  @Override
  public SuccessResponse addMetrics(MetricsAddParameters parameters) {
    Metrics metrics = new Metrics(UserInfoUtil.getUsername(), parameters.getKey(),
        parameters.getMsg(), System.currentTimeMillis());
    metricsDao.save(metrics);
    return new SuccessResponse("add success");
  }

  @Override
  public MetricsCountResponse getCountMetrics(String key) {
    long count = metricsDao.countAllByMetricsKey(key);
    return new MetricsCountResponse(count);
  }

  @Override
  public MetricsBatchCountResponse getBatchCountMetrics(MetricsCountParameters parameters) {
    if (parameters.getKeys().isEmpty()) {
      return new MetricsBatchCountResponse(new ArrayList<>());
    }
    List<MetricsItem> metricsItemList = metricsDao
        .countAllByMetricsKeyInAndStartTimeAndEndTimeAndGroupBy(parameters.getKeys(),
            parameters.getStartTime(), parameters.getEndTime());
    return new MetricsBatchCountResponse(metricsItemList);
  }

  @Override
  public SuccessResponse addSingleMetrics(MetricsAddParameters parameters) {
    String username = UserInfoUtil.getUsername();
    String key = parameters.getKey();
    boolean isMetricsExist = metricsDao.existsMetricsByUsernameAndMetricsKey(username, key);
    if (!isMetricsExist) {
      Metrics metrics = new Metrics(UserInfoUtil.getUsername(), parameters.getKey(),
          parameters.getMsg(), System.currentTimeMillis());
      metricsDao.save(metrics);
      return new SuccessResponse("add success");
    }
    return new SuccessResponse("not single metrics");
  }
}
