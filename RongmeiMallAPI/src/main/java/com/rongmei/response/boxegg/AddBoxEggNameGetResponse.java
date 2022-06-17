package com.rongmei.response.boxegg;

import com.rongmei.response.Response;

import java.util.List;

public class AddBoxEggNameGetResponse extends Response {

    private List<String> boxEggName;

    public AddBoxEggNameGetResponse() {
    }

    public AddBoxEggNameGetResponse(List<String> boxEggName) {
        this.boxEggName = boxEggName;
    }

    public List<String> getBoxEggName() {
        return boxEggName;
    }

    public void setBoxEggName(List<String> boxEggName) {
        this.boxEggName = boxEggName;
    }
}
