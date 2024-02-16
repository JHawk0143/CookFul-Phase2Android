package algonquin.cst2335.findmyrecipe.database.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "units")
public class UnitsModel {


    @PrimaryKey(autoGenerate = true)
    public int unit_id;

    @ColumnInfo( name = "name")
    public String name;
}
