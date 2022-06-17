package com.rongmei.exception;

import com.rongmei.response.WrongResponse;

public class ParametersErrorException extends Exception {

  private WrongResponse response = new WrongResponse(10008, "Parameters error.");

  public WrongResponse getResponse() {
    return response;
  }
}
