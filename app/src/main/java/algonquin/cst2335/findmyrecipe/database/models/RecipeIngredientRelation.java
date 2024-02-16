package algonquin.cst2335.findmyrecipe.database.models;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class RecipeIngredientRelation {
    @Embedded public RecipeModel recipeModel;
    @Relation(
        parentColumn = "recipe_id",
        entityColumn = "ingredient_list_id"
    )
    public List<IngredientListModel> ingredientListModel;
}
