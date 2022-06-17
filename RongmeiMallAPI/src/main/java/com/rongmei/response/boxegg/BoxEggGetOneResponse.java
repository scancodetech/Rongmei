package com.rongmei.response.boxegg;

import com.rongmei.response.Response;

import java.util.List;

public class BoxEggGetOneResponse extends Response {

   private BoxEggItem boxEggItem;

    public BoxEggGetOneResponse() {
    }

    public BoxEggGetOneResponse(BoxEggItem boxEggItem) {
        this.boxEggItem = boxEggItem;
    }

    public BoxEggItem getBoxEggItem() {
        return boxEggItem;
    }

    public void setBoxEggItem(BoxEggItem boxEggItem) {
        this.boxEggItem = boxEggItem;
    }
}
