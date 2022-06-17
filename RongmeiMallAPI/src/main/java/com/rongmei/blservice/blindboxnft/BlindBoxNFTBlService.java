package com.rongmei.blservice.blindboxnft;

import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.blindboxnft.BlindBoxNftTransferParameters;
import com.rongmei.parameters.blindboxnft.BlindBoxNftUpdateParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.blindboxnft.BlindBoxNFTDetailResponse;
import com.rongmei.response.blindboxnft.BlindBoxNFTGetResponse;

public interface BlindBoxNFTBlService {

  SuccessResponse updateNFT(BlindBoxNftUpdateParameters parameters, String username)
      throws ThingIdDoesNotExistException;

  SuccessResponse transferNFT(BlindBoxNftTransferParameters parameters)
      throws ThingIdDoesNotExistException;

  SuccessResponse deleteNFT(int nftId) throws ThingIdDoesNotExistException;

  BlindBoxNFTGetResponse getBlindBoxList(String theme, String classification);

  BlindBoxNFTGetResponse getMineBlindBoxNFTList(String username, int page, int limit);

  BlindBoxNFTDetailResponse getBlindBoxNFT(int nftId) throws ThingIdDoesNotExistException;
}
