package algonquin.cst2335.findmyrecipe.recyclerviews.searchrecycler;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import algonquin.cst2335.findmyrecipe.util.TestAndDebugFunc;


public class SearchRecyclerRowModel extends ViewModel  {

    private MutableLiveData<ArrayList<SearchRecyclerRowData>> rowModel;

    //this exists solely to flag changes in the list, since updates to the array list
    //does not update the live data object.
    private MutableLiveData<SearchRecyclerRowData> latestRow;

    private ArrayList<SearchRecyclerRowData> undone;

    //default constructor, just leads to the proper constructor
    public SearchRecyclerRowModel()  {
        this( new ArrayList<SearchRecyclerRowData>() );
    }

    //constructor
    public SearchRecyclerRowModel( ArrayList<SearchRecyclerRowData> row_data )  {
        this.rowModel = new MutableLiveData<ArrayList<SearchRecyclerRowData>>();
        this.latestRow = new MutableLiveData<SearchRecyclerRowData>();
        this.undone = new ArrayList<SearchRecyclerRowData>();
        this.rowModel.setValue( row_data );
    }

    //debug and test function
    public void createTestRow()  {
        SearchRecyclerRowData row = new SearchRecyclerRowData();

        //row.setThumbnail(BitmapFactory.decodeResource( Resources.getSystem(), R.drawable.test_image ));
        row.setName("Recipe: " + TestAndDebugFunc.generateSaneString(12));


        this.addRow(row);
    }

    public int getSize()  {
        return this.rowModel.getValue().size();
    }

    public void addRow(SearchRecyclerRowData row)  {
        this.rowModel.getValue().add(row);
        //this.latestRow.setValue(row);
    }
    public void addRow(SearchRecyclerRowData row, int position)  {
        this.rowModel.getValue().add(position, row);
        //this.latestRow.setValue(row);
    }

    public SearchRecyclerRowData getRow(int position)  {
        return this.rowModel.getValue().get(position);
    }

    public ArrayList<SearchRecyclerRowData> getAllRows()  {
        return this.rowModel.getValue();
    }

    public void removeRow(int position)  {
        this.undone.add(0 , this.rowModel.getValue().get(position));
        this.rowModel.getValue().remove(position);
        this.latestRow.setValue(null);
    }

    public void removeAll()  {
        this.rowModel.getValue().clear();
        this.latestRow = null;
        this.undone.clear();

    }
    public void updateRow(SearchRecyclerRowData row, int position)  {
        this.rowModel.getValue().remove(position);
        this.rowModel.getValue().add(position, row);
    }

}
