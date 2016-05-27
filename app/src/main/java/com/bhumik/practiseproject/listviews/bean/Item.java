package com.bhumik.practiseproject.listviews.bean;

/**
 * Created by bhumik on 17/5/16.
 */
public class Item {
    private int itemId;
    private String itemDetails;

    public Item(int itemId, String itemDetails) {
        this.itemId = itemId;
        this.itemDetails = itemDetails;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(String itemDetails) {
        this.itemDetails = itemDetails;
    }
}
