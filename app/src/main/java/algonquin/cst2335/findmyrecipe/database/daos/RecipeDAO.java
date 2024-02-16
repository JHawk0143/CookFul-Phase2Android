package algonquin.cst2335.findmyrecipe.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import algonquin.cst2335.findmyrecipe.database.models.RecipeIngredientRelation;
import algonquin.cst2335.findmyrecipe.database.models.RecipeModel;

@Dao
public interface RecipeDAO {

    @Insert
    public long insertRecipe(RecipeModel recipe_model);

    @Update
    public int updateRecipe(RecipeModel recipe_model);

    @Delete
    public void deleteRecipe(RecipeModel recipe_model);

    @Transaction
    @Query("select * from recipes where favorite = 0")
    public List<RecipeIngredientRelation> getViewedRecipes();

    @Transaction
    @Query("select * from recipes")
    public List<RecipeIngredientRelation> getAllRecipe();

    @Transaction
    @Query("select * from recipes where recipe_id = :recipe_id ")
    public RecipeIngredientRelation getRecipeById(int recipe_id);

    @Transaction
    @Query("select * from recipes where apid = :apid ")
    public RecipeModel getRecipeByApid(int apid);

    @Transaction
    @Query("select * from recipes where favorite = 1" )
    public List<RecipeIngredientRelation> getFavorites();
}
