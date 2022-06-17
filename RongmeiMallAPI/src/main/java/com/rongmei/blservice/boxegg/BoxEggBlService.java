package com.rongmei.blservice.boxegg;

import com.rongmei.exception.AddFailedException;
import com.rongmei.exception.ParametersErrorException;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.exception.ThingNameDoesNotExistException;
import com.rongmei.parameters.boxegg.AddBoxEggParameters;
import com.rongmei.parameters.boxegg.CastBoxEggParameters;
import com.rongmei.parameters.boxegg.PublishBoxEggParameters;
import com.rongmei.parameters.boxegg.UpdateCastBoxEggParameters;
import com.rongmei.parameters.boxorder.BoxOrderCommentParameters;
import com.rongmei.parameters.boxorder.BoxOrderRequestSubmitParameters;
import com.rongmei.parameters.boxorder.BoxOrderResultCoverUrlSubmitParameters;
import com.rongmei.parameters.boxorder.BoxOrderResultUrlSubmitParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.boxegg.*;
import com.rongmei.response.boxorder.BoxOrderDetailResponse;
import com.rongmei.response.boxorder.BoxOrderGetResponse;
import com.rongmei.response.boxorder.BoxOrderShareGetResponse;

public interface BoxEggBlService {

 SuccessResponse castBoxEgg(CastBoxEggParameters parameters) throws AddFailedException;

 BoxEggGetResponse findCastBoxEggByStatus(int status) throws ThingIdDoesNotExistException;
//
// SuccessResponse delCastBoxEggById(int boxEggId) throws ThingIdDoesNotExistException;
//
// SuccessResponse updateCastBoxEgg(UpdateCastBoxEggParameters parameters) throws ThingIdDoesNotExistException;
//
// SuccessResponse addBoxEgg(CastBoxEggParameters parameters);
//
// BoxEggGetOneResponse findCastBoxEggById(int boxEggId) throws ThingIdDoesNotExistException;
//
// SuccessResponse publishCastBoxEgg(PublishBoxEggParameters parameters) throws AddFailedException;

 AddBoxEggListResponse findAddBoxEggByStatus(int status) throws ThingIdDoesNotExistException;

 AddBoxEggNameGetResponse findAllAddBoxEggName();

 AddBoxEggGetResponse findAddBoxEggByName(String seriesName) throws ThingIdDoesNotExistException, ThingNameDoesNotExistException;

 SuccessResponse randomPackageAddBoxEgg(AddBoxEggParameters parameters) throws AddFailedException, ThingIdDoesNotExistException;

 SuccessResponse publishBoxEgg(PublishBoxEggParameters parameters) throws ThingIdDoesNotExistException, AddFailedException;

 RandomBoxEggListResponse randomPackageAddBoxEgg2(int addBoxEggId) throws ThingIdDoesNotExistException;

 SuccessResponse recordLineInformation(int addBoxEggId);

 Object getAllBoxSeriesList();
}
