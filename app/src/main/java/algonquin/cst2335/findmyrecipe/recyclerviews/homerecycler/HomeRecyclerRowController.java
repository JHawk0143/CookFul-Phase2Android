package algonquin.cst2335.findmyrecipe.recyclerviews.homerecycler;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

/**
 * Functionality for manipulating the recycler
 */
public class HomeRecyclerRowController extends ViewModel  {

    private MutableLiveData<ArrayList<HomeRecyclerRowData>> rowModel;

    //this exists solely to flag changes in the list, since updates to the array list
    //does not update the live data object.
    private MutableLiveData<HomeRecyclerRowData> latestRow;

    private ArrayList<HomeRecyclerRowData> undone;

    /**
     * Default constructor, chains to the main constructor
     */
    public HomeRecyclerRowController()  {
        this( new ArrayList<HomeRecyclerRowData>() );
    }

    /**
     * main constructor, will set the rows if given.
     * @param row_data
     */
    public HomeRecyclerRowController(ArrayList<HomeRecyclerRowData> row_data )  {
        this.rowModel = new MutableLiveData<ArrayList<HomeRecyclerRowData>>();
        this.latestRow = new MutableLiveData<HomeRecyclerRowData>();
        this.undone = new ArrayList<HomeRecyclerRowData>();
        this.rowModel.setValue( row_data );
    }

    /**
     * Row count
     * @return row count
     */
    public int getSize()  {
        return this.rowModel.getValue().size();
    }

    /**
     * Adds a row to the recycler
     * @param row row data to be added
     */
    public void addRow(HomeRecyclerRowData row)  {
        this.rowModel.getValue().add(row);
        this.latestRow.setValue(row);
    }

    /**
     * Add a row to a specific position
     * @param row row data to add
     * @param position position in the recycler to add the row
     */
    public void addRow(HomeRecyclerRowData row, int position)  {
        this.rowModel.getValue().add(position, row);
        this.latestRow.setValue(row);
    }

    /**
     * Get row data
     * @param position position of the row to get
     * @return row data
     */
    public HomeRecyclerRowData getRow(int position)  {
        return this.rowModel.getValue().get(position);
    }

    /**
     * delete a row given it's position
     * @param position row to remove
     */
    public void removeRow(int position)  {
        this.undone.add(0 , this.rowModel.getValue().get(position));
        this.rowModel.getValue().remove(position);
        this.latestRow.setValue(null);
    }

    /**
     * Remove all rows in the recycler.
     */
    public void removeAll()  {
        this.rowModel.getValue().clear();
        //this.latestRow = null;
        this.undone.clear();

    }



}
