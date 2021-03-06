package com.rongmei.exception;

import com.rongmei.response.WrongResponse;

public class UsernameDoesNotFoundException extends Exception {
    private WrongResponse response = new WrongResponse(10010, "Cannot found the username.");

    public WrongResponse getResponse() {
        return response;
    }
}
