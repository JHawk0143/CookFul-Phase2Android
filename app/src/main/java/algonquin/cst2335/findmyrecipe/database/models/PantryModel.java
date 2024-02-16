package algonquin.cst2335.findmyrecipe.database.models;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pantry")
public class PantryModel {

    @PrimaryKey(autoGenerate = true)
    public int ingredient_id;

    @ColumnInfo(name = "unit_id")
    public int unitId;

    @ColumnInfo(name = "quantity")
    public int quantity;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "selected")
    public boolean selected;

}
