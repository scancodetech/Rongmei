package com.rongmei.exception;

import com.rongmei.response.WrongResponse;

public class ThingIdDoesNotExistException extends Exception {
    private WrongResponse response = new WrongResponse(10002, "thing id does not exists.");

    public WrongResponse getResponse() {
        return response;
    }
}
