package com.rongmei.response.boxegg;

import com.rongmei.response.Response;

import java.util.List;

public class AddBoxEggGetResponse extends Response {

    private BoxEggItem  hideBoxEggItem;
    private List<BoxEggItem> boxEggItemList;

    public AddBoxEggGetResponse() {
    }

    public AddBoxEggGetResponse(BoxEggItem hideBoxEggItem, List<BoxEggItem> boxEggItemList) {
        this.hideBoxEggItem = hideBoxEggItem;
        this.boxEggItemList = boxEggItemList;
    }

    public BoxEggItem getHideBoxEggItem() {
        return hideBoxEggItem;
    }

    public void setHideBoxEggItem(BoxEggItem hideBoxEggItem) {
        this.hideBoxEggItem = hideBoxEggItem;
    }

    public List<BoxEggItem> getBoxEggItemList() {
        return boxEggItemList;
    }

    public void setBoxEggItemList(List<BoxEggItem> boxEggItemList) {
        this.boxEggItemList = boxEggItemList;
    }

}
