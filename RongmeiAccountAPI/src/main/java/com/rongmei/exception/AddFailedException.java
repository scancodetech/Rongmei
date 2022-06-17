package com.rongmei.exception;

import com.rongmei.response.WrongResponse;

public class AddFailedException extends Exception {
    private WrongResponse response = new WrongResponse(10008, "add failed");

    public WrongResponse getResponse() {
        return response;
    }

    public AddFailedException() {
    }

    public AddFailedException(String message, WrongResponse response) {
        super(message);
        this.response = response;
    }

}
