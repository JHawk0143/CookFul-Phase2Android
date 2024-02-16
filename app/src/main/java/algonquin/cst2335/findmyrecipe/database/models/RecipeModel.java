package algonquin.cst2335.findmyrecipe.database.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipes")
public class RecipeModel {
    /*
    recipe id
    ing_list_id
    instruct
    name
    instructions
    category

     */

    @PrimaryKey(autoGenerate = true)
    public int recipe_id;

    @ColumnInfo(name = "ingredient_list_id")
    public int ingredientListId;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "instructions")
    public String instructions;

    @ColumnInfo(name = "prep_time")
    public int prepTime;

    @ColumnInfo(name = "cook_time")
    public int cookTime;

    @ColumnInfo(name = "ready_time")
    public int readyTime;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "category")
    public String category;

    @ColumnInfo(name = "favorite")
    public boolean favorite;

    @ColumnInfo(name = "apid")
    public int apid;

    @ColumnInfo(name = "vegan")
    public boolean vegan;

    @ColumnInfo(name = "vegetatian")
    public boolean vegetarian;

    @ColumnInfo(name = "glutenfree")
    public boolean glutenfree;
    @ColumnInfo(name = "dairyfree")
    public boolean dairyfree;

    @ColumnInfo(name = "image_key")
    public String imageKey;

    @ColumnInfo(name = "website")
    public String website;

}
