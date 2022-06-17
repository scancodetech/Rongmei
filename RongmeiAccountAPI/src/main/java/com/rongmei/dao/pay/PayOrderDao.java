package com.rongmei.dao.pay;

import com.rongmei.entity.pay.PayOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayOrderDao extends JpaRepository<PayOrder, String> {

}
