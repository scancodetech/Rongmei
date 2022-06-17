package com.rongmei.response.item;

import com.rongmei.entity.account.Items;
import com.rongmei.response.Response;

import java.util.List;

public class ItemListResponse extends Response {
    private List<Items> itemsList;
    public ItemListResponse() {
    }

    public ItemListResponse(List<Items> itemsList) {
        this.itemsList = itemsList;
    }

    public List<Items> getItemsList() {
        return itemsList;
    }

    public void setItemsList(List<Items> itemsList) {
        this.itemsList = itemsList;
    }
}
