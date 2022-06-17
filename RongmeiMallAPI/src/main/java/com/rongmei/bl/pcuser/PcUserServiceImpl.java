package com.rongmei.bl.pcuser;

import com.rongmei.dao.order.OrderDao;
import com.rongmei.dao.pcuser.PcUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PcUserServiceImpl {


    private final PcUserDao pcUserDao;

    @Autowired
    public PcUserServiceImpl(PcUserDao pcUserDao) {
        this.pcUserDao = pcUserDao;
    }

}
