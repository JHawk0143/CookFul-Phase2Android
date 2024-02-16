package algonquin.cst2335.findmyrecipe.recyclerviews.ingredientmgr;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import algonquin.cst2335.findmyrecipe.util.TestAndDebugFunc;

public class IngredientRecyclerRowModel extends ViewModel {

    private MutableLiveData<ArrayList<IngredientRecyclerRowData>> rowModel;

    private MutableLiveData<IngredientRecyclerRowData> latestRow;

    private ArrayList<IngredientRecyclerRowData> undone;

    int pkhelper = 3256;

    public IngredientRecyclerRowModel()  { this( new ArrayList<IngredientRecyclerRowData>() ); }

    public IngredientRecyclerRowModel( ArrayList<IngredientRecyclerRowData> row_data )  {
        this.rowModel = new MutableLiveData<ArrayList<IngredientRecyclerRowData>>();
        this.latestRow = new MutableLiveData<IngredientRecyclerRowData>();
        this.undone = new ArrayList<IngredientRecyclerRowData>();
        this.rowModel.setValue( row_data );
    }

    public void createTestRow()  {
        IngredientRecyclerRowData row = new IngredientRecyclerRowData();

        row.setName( TestAndDebugFunc.generateSaneString(8) );
        row.setQuantity(5);
        row.setUom( TestAndDebugFunc.generateSaneString(8) );
        row.setIngredientId( ++pkhelper );

        this.addRow(row);
    }

    public int getSize()  {
        return this.rowModel.getValue().size();
    }

    public void addRow(IngredientRecyclerRowData row)  {
        this.rowModel.getValue().add(row);
        //this.latestRow.setValue(row);
        this.latestRow.postValue(row);
    }

    public void addRow(IngredientRecyclerRowData row, int position)  {
        this.rowModel.getValue().add(position, row);
        //this.latestRow.setValue(row);
        this.latestRow.postValue(row);
    }

    public IngredientRecyclerRowData getRow(int position)  {
        return this.rowModel.getValue().get(position);
    }

    public void removeRow(int position)  {
        this.undone.add(0 , this.rowModel.getValue().get(position));
        this.rowModel.getValue().remove(position);
        //this.latestRow.setValue(null);
        this.latestRow.postValue(null);
    }

    public void removeAll()  {
        this.rowModel.getValue().clear();
        this.latestRow = null;
        this.undone.clear();

    }

}
