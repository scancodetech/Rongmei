package com.rongmei.exception;

import com.rongmei.response.WrongResponse;

public class FileSuffixWrongException extends Exception{

    private WrongResponse response ;

    public FileSuffixWrongException(String msg){

        this.response = new WrongResponse(10003,msg);
    }

    public WrongResponse getResponse() {
        return response;
    }
}
