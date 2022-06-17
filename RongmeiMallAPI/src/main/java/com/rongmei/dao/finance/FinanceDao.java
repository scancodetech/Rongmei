package com.rongmei.dao.finance;

import com.rongmei.entity.auction.Sale;
import com.rongmei.entity.finance.Finance;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface FinanceDao  extends JpaRepository<Finance, Long>, JpaSpecificationExecutor<Finance> {


    List<Finance> findAllByTransactionTypeAndIsMargin(String transactionType,Integer isMargin);

    List<Finance> findALLByCreateTimeBeforeAndTransactionTypeAndTypeAndIsMargin(
            Long createTime,
            String transactionType,
            String type,
            Integer isMargin
            );
    Finance findFirstByType(String type);


    Finance findByIdAndUsername(long id,String username);
}
