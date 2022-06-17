package com.rongmei.exception;

import com.rongmei.response.WrongResponse;

public class RoleNameDoesNotFoundException extends Exception {
    private WrongResponse response = new WrongResponse(10011, "Cannot found the role name.");

    public WrongResponse getResponse() {
        return response;
    }
}
