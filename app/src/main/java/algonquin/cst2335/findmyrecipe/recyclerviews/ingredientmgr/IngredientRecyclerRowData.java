package algonquin.cst2335.findmyrecipe.recyclerviews.ingredientmgr;

public class IngredientRecyclerRowData {

    int ingredient_id;
    String name;

    int quantity;

    String uom;

    boolean checked;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public int getIngredientId() {
        return ingredient_id;
    }

    public void setIngredientId(int ingredient_id) {
        this.ingredient_id = ingredient_id;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
