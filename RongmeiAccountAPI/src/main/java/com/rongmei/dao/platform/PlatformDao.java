package com.rongmei.dao.platform;

import com.rongmei.entity.platform.Platform;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatformDao extends JpaRepository<Platform, Integer> {

  Platform findFirstByPlatformKey(String platformKey);
}
