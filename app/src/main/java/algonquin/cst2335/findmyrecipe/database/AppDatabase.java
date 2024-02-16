package algonquin.cst2335.findmyrecipe.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import algonquin.cst2335.findmyrecipe.database.daos.IngredientListDAO;
import algonquin.cst2335.findmyrecipe.database.daos.PantryDAO;
import algonquin.cst2335.findmyrecipe.database.daos.RecipeDAO;
import algonquin.cst2335.findmyrecipe.database.daos.UnitsDAO;
import algonquin.cst2335.findmyrecipe.database.models.IngredientListModel;
import algonquin.cst2335.findmyrecipe.database.models.PantryModel;
import algonquin.cst2335.findmyrecipe.database.models.RecipeIngredientRelation;
import algonquin.cst2335.findmyrecipe.database.models.RecipeModel;
import algonquin.cst2335.findmyrecipe.database.models.UnitsModel;

@Database(entities = {PantryModel.class, RecipeModel.class, UnitsModel.class, IngredientListModel.class}, version = 6)
public abstract class AppDatabase extends RoomDatabase {

    public abstract PantryDAO PantryDao();

    public abstract RecipeDAO RecipeDao();

    public abstract UnitsDAO UnitsDao();

    public abstract IngredientListDAO IngredientsListDao();

}
