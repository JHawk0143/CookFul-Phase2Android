package algonquin.cst2335.findmyrecipe.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import algonquin.cst2335.findmyrecipe.database.models.PantryModel;
import algonquin.cst2335.findmyrecipe.database.models.PantryUnitsRelation;

@Dao
public interface PantryDAO {

    @Insert
    public long insertIngredient(PantryModel pantryModel);

    @Update
    public int updateIngredient(PantryModel pantryModel);

    @Delete
    public int deleteIngredient(PantryModel pantryModel);

    @Transaction
    @Query("select * from pantry where selected = 1")
    public List<PantryModel> getSelectedIngredients();

    @Transaction
    @Query("select * from pantry")
    public List<PantryModel> getAllIngredients();

    @Transaction
    @Query("select * from pantry where ingredient_id = :ingredient_id ")
    public PantryModel getIngredientById(int ingredient_id);

}
