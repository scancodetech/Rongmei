package com.rongmei.response.boxegg;

import com.rongmei.response.Response;

import java.util.List;

public class RandomBoxEggListResponse extends Response {

    private List<BoxList> boxEggItemList;//箱子列表  多少箱

    public RandomBoxEggListResponse() {
    }

    public RandomBoxEggListResponse(List<BoxList> boxEggItemList) {
        this.boxEggItemList = boxEggItemList;
    }

    public List<BoxList> getBoxEggItemList() {
        return boxEggItemList;
    }

    public void setBoxEggItemList(List<BoxList> boxEggItemList) {
        this.boxEggItemList = boxEggItemList;
    }
}
