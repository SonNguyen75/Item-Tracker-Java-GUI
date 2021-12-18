package ca.cmpt213.a4.client.model;

/**
 * Helper class of ItemID, store only an ItemID of type long. Used to convert ID to JSON format
 */
public class ItemID {
    long itemID;

    public ItemID() {
    }

    public ItemID(long itemID) {
        this.itemID = itemID;
    }

    public long getItemID() {
        return itemID;
    }

    public void setItemID(long itemID) {
        this.itemID = itemID;
    }
}
