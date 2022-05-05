package w1d3.Javalin;

public class GroceryItem {
    /*
    private is 1 of 4 access modifiers
     */
    private String itemName;

    public GroceryItem() {

    }

    public GroceryItem(String itemName){
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    /*shortcut to generation menu - alt+insert*/
}
