package com.rongmei.response.boxegg;

import com.rongmei.response.Response;

import java.util.List;

public class AddBoxEggSeriesGetResponse extends Response {


    private List<AddBoxEggItem> addBoxEggItemList;

    public AddBoxEggSeriesGetResponse() {
    }

    public AddBoxEggSeriesGetResponse(List<AddBoxEggItem> addBoxEggItemList) {
        this.addBoxEggItemList = addBoxEggItemList;
    }

    public List<AddBoxEggItem> getAddBoxEggItemList() {
        return addBoxEggItemList;
    }

    public void setAddBoxEggItemList(List<AddBoxEggItem> addBoxEggItemList) {
        this.addBoxEggItemList = addBoxEggItemList;
    }
}
