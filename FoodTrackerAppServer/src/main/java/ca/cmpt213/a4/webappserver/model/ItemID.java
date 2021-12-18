package ca.cmpt213.a4.webappserver.model;

public class ItemID {
    long itemID;

    public ItemID() {
    }

    public ItemID(int itemID) {
        this.itemID = itemID;
    }

    public long getItemID() {
        return itemID;
    }

    public void setItemID(long itemID) {
        this.itemID = itemID;
    }
}
