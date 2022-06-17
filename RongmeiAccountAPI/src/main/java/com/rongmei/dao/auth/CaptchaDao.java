package com.rongmei.dao.auth;

import com.rongmei.entity.auth.Captcha;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaptchaDao extends JpaRepository<Captcha, Integer> {
    Captcha findFirstByPhoneAndCodeOrderByCreateTimeDesc(String phone, String code);
}
