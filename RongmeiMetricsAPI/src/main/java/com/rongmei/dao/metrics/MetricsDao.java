package com.rongmei.dao.metrics;

import com.rongmei.entity.metrics.Metrics;
import com.rongmei.response.metrics.MetricsItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MetricsDao extends JpaRepository<Metrics, Long> {

  long countAllByMetricsKey(String key);

  @Query(value = "select new com.rongmei.response.metrics.MetricsItem(m.metricsKey,count(*)) from Metrics m where m.metricsKey in (?1) and m.currTime between ?2 and ?3 group by m.metricsKey")
  List<MetricsItem> countAllByMetricsKeyInAndStartTimeAndEndTimeAndGroupBy(List<String> keys,
      long startTime, long endTime);

  boolean existsMetricsByUsernameAndMetricsKey(String username, String key);
}
