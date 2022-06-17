package com.rongmei.blservice.finance;

import com.rongmei.entity.finance.Finance;
import com.rongmei.parameters.finance.FinanceParameters4MyAccount;
import com.rongmei.response.Response;
import org.springframework.data.domain.Pageable;

public interface FinanceService {




    Response findBySome(String type, String transactionType, Long startTime, Long endTime, Integer time, Integer page, Integer size);

    Response saveByserciceCash(Double startPrice,String username,Double margin);

    Response saveByserciceCash1(Double startPrice,String username);

    Response save(Finance finance);

    Response findByUserAccount(FinanceParameters4MyAccount financeParameters4MyAccount, Pageable pageable);

    Response findById(long id);
}
