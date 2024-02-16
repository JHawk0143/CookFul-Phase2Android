package algonquin.cst2335.findmyrecipe.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import algonquin.cst2335.findmyrecipe.database.models.IngredientListModel;


@Dao
public interface IngredientListDAO {

    @Insert
    public long insertIngredientList(IngredientListModel ingredient_list);

    @Update
    public int updateUnit(IngredientListModel ingredient_list);

    @Delete
    public void deleteUnit(IngredientListModel ingredient_list);

    @Query("select * from ingredient_list")
    public List<IngredientListModel> getAllIngredientLists();

    @Query("select * from ingredient_list where ingredient_list_id = :ingredient_list_id ")
    public List<IngredientListModel> getIngredientListById(int ingredient_list_id);

}
