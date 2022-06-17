package com.rongmei.dao.select;

import com.rongmei.entity.report.Report;
import com.rongmei.entity.select.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SelectDao extends JpaRepository<Select, Integer> {

        List<Select> findAllByOrderBySortDesc();

}
