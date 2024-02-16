package algonquin.cst2335.findmyrecipe.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import algonquin.cst2335.findmyrecipe.database.models.UnitsModel;

@Dao
public interface UnitsDAO {

    @Insert
    public long insertUnit(UnitsModel units_model);

    @Update
    public int updateUnit(UnitsModel units_model);

    @Delete
    public void deleteUnit(UnitsModel units_model);

    @Query("select * from units")
    public List<UnitsModel> getAllUnits();

    @Query("select * from units where unit_id = :unit_id ")
    public UnitsModel getUnitById(int unit_id);

    @Query("select * from units where name = :name ")
    public UnitsModel getUnitByName(String name);

}
