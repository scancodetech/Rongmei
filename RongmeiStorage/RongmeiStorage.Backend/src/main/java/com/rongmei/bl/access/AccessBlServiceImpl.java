package com.rongmei.bl.access;

import com.rongmei.blservice.access.AccessBlService;
import com.rongmei.dao.access.AccessDao;
import com.rongmei.entity.access.Access;
import com.rongmei.parameters.access.AccessUpdateParameters;
import com.rongmei.publicdatas.operation.Operation;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.access.AccessGetResponse;
import com.rongmei.response.access.AccessItem;
import com.rongmei.util.RandomUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccessBlServiceImpl implements AccessBlService {

  private final AccessDao accessDao;

  @Autowired
  public AccessBlServiceImpl(AccessDao accessDao) {
    this.accessDao = accessDao;
  }

  @Override
  public SuccessResponse updateAccess(AccessUpdateParameters parameters) {
    if (parameters.getOperation() == Operation.DELETE) {
      accessDao.deleteById(parameters.getId());
      return new SuccessResponse("delete success");
    } else if (parameters.getOperation() == Operation.UPDATE) {
      Optional<Access> optionalAccess = accessDao.findById(parameters.getId());
      if (optionalAccess.isPresent()) {
        Access access = optionalAccess.get();
        access.setName(parameters.getName());
        access.setStatus(parameters.getStatus());
        access.setAccessKey(parameters.getAccessKey());
        access.setAccessSecret(parameters.getAccessSecret());
        accessDao.save(access);
      }
      return new SuccessResponse("update success");
    } else {
      Access access = new Access(parameters.getName(), RandomUtil.generateCode(22),
          RandomUtil.genKey(42), System.currentTimeMillis(), 0);
      accessDao.save(access);
      return new SuccessResponse("add success");
    }
  }

  @Override
  public AccessGetResponse getAccess() {
    List<Access> accesses = accessDao.findAll();
    List<AccessItem> accessItems = new ArrayList<>();
    for (Access access : accesses) {
      accessItems.add(new AccessItem(access.getId(), access.getName(), access.getAccessKey(),
          access.getAccessSecret(), access.getStatus()));
    }
    return new AccessGetResponse(accessItems);
  }
}
