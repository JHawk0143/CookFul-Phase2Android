package algonquin.cst2335.findmyrecipe.util;

import android.util.Log;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.findmyrecipe.database.AppDatabase;
import algonquin.cst2335.findmyrecipe.database.daos.IngredientListDAO;
import algonquin.cst2335.findmyrecipe.database.daos.PantryDAO;
import algonquin.cst2335.findmyrecipe.database.daos.RecipeDAO;
import algonquin.cst2335.findmyrecipe.database.daos.UnitsDAO;
import algonquin.cst2335.findmyrecipe.database.models.IngredientListModel;
import algonquin.cst2335.findmyrecipe.database.models.PantryModel;
import algonquin.cst2335.findmyrecipe.database.models.RecipeModel;
import algonquin.cst2335.findmyrecipe.database.models.UnitsModel;

public class DatabaseTester {

    AppDatabase db;
    PantryDAO pdao;
    RecipeDAO rdao;
    UnitsDAO udao;
    IngredientListDAO ildao;
    public DatabaseTester(AppDatabase db)  {
        this.db = db;
        this.pdao = db.PantryDao();
        this.rdao = db.RecipeDao();
        this.udao = db.UnitsDao();
        this.ildao = db.IngredientsListDao();
    }

    public long addRecipe()  {

        RecipeModel recipe = new RecipeModel();
        recipe.ingredientListId = (int) addIngredientList();
        recipe.name = TestAndDebugFunc.generateSaneString(8);
        recipe.instructions = TestAndDebugFunc.generateSaneString(8);
        recipe.category = TestAndDebugFunc.generateSaneString(8);

        Log.d("db_tester", "adding recipe");

        //Executor thread = Executors.newSingleThreadExecutor();
        //thread.execute(() -> {
            return rdao.insertRecipe(recipe);
        //});

    }

    public long addUnit()  {

        UnitsModel unit = new UnitsModel();
        unit.name = TestAndDebugFunc.generateSaneString(8);


        Log.d("db_tester", "adding unit");

        return udao.insertUnit(unit);

    }

    public long addIngredient()  {

        PantryModel ingredient = new PantryModel();
        ingredient.name = TestAndDebugFunc.generateSaneString(8);
        ingredient.quantity = 5;
        ingredient.unitId = (int) addUnit();

        Log.d("db_tester", "adding ingredient");

        return pdao.insertIngredient(ingredient);

    }

    public long addIngredientList()  {

        IngredientListModel list = new IngredientListModel();

        list.ingredientId = (int) addIngredient();
        list.description = TestAndDebugFunc.generateSaneString(8);
        list.quantity = 4;
        list.position = 1;
        list.unitsId = (int) addUnit();

        Log.d("db_tester", "adding ingredient list");

        return ildao.insertIngredientList(list);

    }


}
