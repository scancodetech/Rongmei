package com.rongmei.blservice;

import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.tcc.TCCTupleUpdateParameters;
import com.rongmei.response.tcc.TCCTupleDetailResponse;
import com.rongmei.response.tcc.TCCTupleGetResponse;

public interface TCCBlService {

  TCCTupleGetResponse getTCCs();

  TCCTupleDetailResponse getTCC(String key) throws ThingIdDoesNotExistException;

  TCCTupleDetailResponse updateTCC(TCCTupleUpdateParameters parameters)
      throws ThingIdDoesNotExistException;
}
