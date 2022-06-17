package com.rongmei.blservice.select;

import com.rongmei.entity.select.Select;
import com.rongmei.parameters.report.ReportSubmitParameters;
import com.rongmei.response.Response;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.report.ReportGetResponse;

public interface SelectBlService {


  Response getSelect();

  Response submitSelect(Select select);

  Response putSelect(Select select);

  Response deleteSelect(Integer id);

  Response getById(Integer id);
}
