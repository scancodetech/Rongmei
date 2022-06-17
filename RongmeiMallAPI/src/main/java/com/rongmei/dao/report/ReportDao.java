package com.rongmei.dao.report;

import com.rongmei.entity.report.Report;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReportDao extends JpaRepository<Report, Integer> {

  @Query(nativeQuery = true, value = "select r.* from report as r order by r.create_time desc limit ?1 offset ?2")
  List<Report> findAllByLimitAndPage(int limit, int offset);
}
