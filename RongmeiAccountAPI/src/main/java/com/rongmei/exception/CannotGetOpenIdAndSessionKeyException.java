package com.rongmei.exception;

import com.rongmei.response.WrongResponse;

public class CannotGetOpenIdAndSessionKeyException extends Exception {
    private WrongResponse response = new WrongResponse(10007, "Cannot register with username's length = 0.");

    public WrongResponse getResponse() {
        return response;
    }
}
