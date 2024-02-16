package algonquin.cst2335.findmyrecipe.database.models;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class PantryUnitsRelation {

    @Embedded
    public PantryModel pantryModel;
    @Relation(
            parentColumn = "ingredient_id",
            entityColumn = "unit_id"
    )
    public UnitsModel unitsModel;

}
