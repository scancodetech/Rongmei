package com.rongmei.bl.platform;

import com.rongmei.blservice.platform.PlatformBlService;
import com.rongmei.dao.platform.PlatformDao;
import com.rongmei.entity.platform.Platform;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.platform.PlatformUpdateParameters;
import com.rongmei.response.platform.PlatformDetailResponse;
import com.rongmei.response.platform.PlatformItem;
import com.rongmei.response.platform.PlatformListResponse;
import com.rongmei.util.RandomUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlatformBlServiceImpl implements PlatformBlService {

  private final PlatformDao platformDao;

  @Autowired
  public PlatformBlServiceImpl(PlatformDao platformDao) {
    this.platformDao = platformDao;
  }

  @Override
  public PlatformListResponse getPlatforms() {
    List<Platform> platformList = platformDao.findAll();
    List<PlatformItem> platformItems = new ArrayList<>();
    for (Platform platform : platformList) {
      platformItems.add(
          new PlatformItem(platform.getId(), platform.getName(), platform.getPlatformKey(),
              platform.getStatus()));
    }
    return new PlatformListResponse(platformItems);
  }

  @Override
  public PlatformDetailResponse getPlatformDetail(int platformId)
      throws ThingIdDoesNotExistException {
    Optional<Platform> optionalPlatform = platformDao.findById(platformId);
    if (optionalPlatform.isPresent()) {
      Platform platform = optionalPlatform.get();
      return new PlatformDetailResponse(platform.getId(), platform.getName(),
          platform.getPlatformKey(), platform.getStatus(), platform.getAccessUsernames());
    }
    throw new ThingIdDoesNotExistException();
  }

  @Override
  public PlatformDetailResponse updatePlatform(PlatformUpdateParameters parameters) {
    Platform platform;
    if (parameters.getId() == 0) {
      platform = new Platform(parameters.getName(), RandomUtil.generateCode(6),
          parameters.getStatus(), parameters.getAccessUsernames());
    } else {
      Optional<Platform> optionalPlatform = platformDao.findById(parameters.getId());
      platform = optionalPlatform.orElseGet(Platform::new);
      if (parameters.getName() != null && parameters.getName().length() > 0) {
        platform.setName(parameters.getName());
      }
      if (parameters.getAccessUsernames() != null && parameters.getAccessUsernames().size() > 0) {
        platform.setAccessUsernames(parameters.getAccessUsernames());
      }
      platform.setStatus(parameters.getStatus());
    }
    platformDao.save(platform);
    return new PlatformDetailResponse(platform.getId(), platform.getName(),
        platform.getPlatformKey(), platform.getStatus(), platform.getAccessUsernames());
  }
}
