package algonquin.cst2335.findmyrecipe.database.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ingredient_list")
public class IngredientListModel {

    @PrimaryKey(autoGenerate = true)
    public int ingredient_list_id;

    @ColumnInfo(name = "ingredient_id")
    public int ingredientId;

    @ColumnInfo(name = "units_id")
    public int unitsId;

    @ColumnInfo(name = "position")
    public int position;

    @ColumnInfo(name = "quantity")
    public int quantity;

    @ColumnInfo(name = "description")
    public String description;


}
