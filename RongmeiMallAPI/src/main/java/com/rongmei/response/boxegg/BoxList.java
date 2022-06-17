package com.rongmei.response.boxegg;

import com.rongmei.entity.blindboxnft.BlindBoxNFT;
import com.rongmei.response.Response;

import java.util.List;

public class BoxList extends Response {

    private List<BlindBoxNFT> boxEggItemList;//箱子列表  多少箱

    public BoxList() {
    }

    public BoxList(List<BlindBoxNFT> boxEggItemList) {
        this.boxEggItemList = boxEggItemList;
    }

    public List<BlindBoxNFT> getBoxEggItemList() {
        return boxEggItemList;
    }

    public void setBoxEggItemList(List<BlindBoxNFT> boxEggItemList) {
        this.boxEggItemList = boxEggItemList;
    }
}
