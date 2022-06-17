package com.rongmei.bl;

import com.rongmei.blservice.TCCBlService;
import com.rongmei.dao.tcc.TCCTupleDao;
import com.rongmei.entity.tcc.TCCTuple;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.tcc.TCCTupleUpdateParameters;
import com.rongmei.response.tcc.TCCTupleDetailResponse;
import com.rongmei.response.tcc.TCCTupleGetResponse;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TCCBlServiceImpl implements TCCBlService {

  private final TCCTupleDao tccTupleDao;

  @Autowired
  public TCCBlServiceImpl(TCCTupleDao tccTupleDao) {
    this.tccTupleDao = tccTupleDao;
  }

  @Override
  public TCCTupleGetResponse getTCCs() {
    List<TCCTuple> tccTuples = tccTupleDao.findAll();
    return new TCCTupleGetResponse(tccTuples);
  }

  @Override
  public TCCTupleDetailResponse getTCC(String key) throws ThingIdDoesNotExistException {
    TCCTuple tccTuple = tccTupleDao.findFirstByKey(key);
    if (tccTuple == null) {
      throw new ThingIdDoesNotExistException();
    }
    return new TCCTupleDetailResponse(tccTuple);
  }

  @Override
  public TCCTupleDetailResponse updateTCC(TCCTupleUpdateParameters parameters)
      throws ThingIdDoesNotExistException {
    TCCTuple tccTuple;
    if (parameters.getId() == 0) {
      tccTuple = new TCCTuple(0, parameters.getKey(), parameters.getValue(), parameters.getTitle(),
          parameters.getDescription());
    } else {
      Optional<TCCTuple> optionalTCCTuple = tccTupleDao.findById(parameters.getId());
      if (optionalTCCTuple.isPresent()) {
        tccTuple = optionalTCCTuple.get();
        tccTuple.setKey(parameters.getKey());
        tccTuple.setValue(parameters.getValue());
        tccTuple.setTitle(parameters.getTitle());
        parameters.setDescription(parameters.getDescription());
      } else {
        throw new ThingIdDoesNotExistException();
      }
    }
    tccTupleDao.save(tccTuple);
    return new TCCTupleDetailResponse(tccTuple);
  }
}
