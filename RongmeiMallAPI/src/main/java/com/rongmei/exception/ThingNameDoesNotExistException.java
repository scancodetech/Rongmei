package com.rongmei.exception;

import com.rongmei.response.WrongResponse;

public class ThingNameDoesNotExistException extends Exception {
    private WrongResponse response = new WrongResponse(10003, "thing name does not exists.");

    public WrongResponse getResponse() {
        return response;
    }
}
