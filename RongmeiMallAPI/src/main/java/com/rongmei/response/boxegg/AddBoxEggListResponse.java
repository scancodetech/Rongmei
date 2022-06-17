package com.rongmei.response.boxegg;

import com.rongmei.response.Response;

import java.util.List;

public class AddBoxEggListResponse extends Response {

    private List<BoxEggItem> boxEggItemList;

    public AddBoxEggListResponse() {
    }

    public AddBoxEggListResponse(List<BoxEggItem> boxEggItemList) {
        this.boxEggItemList = boxEggItemList;
    }

    public List<BoxEggItem> getBoxEggItemList() {
        return boxEggItemList;
    }

    public void setBoxEggItemList(List<BoxEggItem> boxEggItemList) {
        this.boxEggItemList = boxEggItemList;
    }

}
