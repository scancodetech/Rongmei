package com.rongmei.response.boxegg;

import com.rongmei.response.Response;
import com.rongmei.response.boxorder.BoxOrderItem;

import java.util.List;

public class BoxEggGetResponse extends Response {

    private List<BoxEggItem> boxEggItemList;

    public BoxEggGetResponse() {
    }

    public BoxEggGetResponse(List<BoxEggItem> boxEggItemList) {
        this.boxEggItemList = boxEggItemList;
    }

    public List<BoxEggItem> getBoxEggItemList() {
        return boxEggItemList;
    }

    public void setBoxEggItemList(List<BoxEggItem> boxEggItemList) {
        this.boxEggItemList = boxEggItemList;
    }
}
