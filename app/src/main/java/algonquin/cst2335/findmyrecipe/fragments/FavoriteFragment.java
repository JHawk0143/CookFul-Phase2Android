package algonquin.cst2335.findmyrecipe.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.findmyrecipe.R;
import algonquin.cst2335.findmyrecipe.api.ApiBitmapHolder;
import algonquin.cst2335.findmyrecipe.database.DbHolder;
import algonquin.cst2335.findmyrecipe.database.models.RecipeIngredientRelation;
import algonquin.cst2335.findmyrecipe.recyclerviews.homerecycler.HomeRecycler;
import algonquin.cst2335.findmyrecipe.recyclerviews.homerecycler.HomeRecyclerRowData;
import algonquin.cst2335.findmyrecipe.recyclerviews.homerecycler.HomeRecyclerRowController;

/**
 * Favorite fragment, third that appears in the app and shows recently Saved recipes.
 */
public class FavoriteFragment extends Fragment implements HomeRecycler.OnItemClickListener {

    private List<RecipeIngredientRelation> recipeList;

    private HomeRecyclerRowController model;
    private HomeRecycler adapter;
    private RecyclerView view;


    /**
     * Observer response function, reloads the recycler view.
     */
    private void updateView()  {

        this.recipeList = DbHolder.getDb().RecipeDao().getFavorites();
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                //Toast.makeText(getActivity(), "update", Toast.LENGTH_LONG).show();

                model.removeAll();
                for(RecipeIngredientRelation recipe : recipeList)  {
                    HomeRecyclerRowData row = new HomeRecyclerRowData();
                    row.setTitle( recipe.recipeModel.name );
                    row.setApid( recipe.recipeModel.apid );
                    row.setImageKey( recipe.recipeModel.imageKey );
                    row.setThumbnail( ApiBitmapHolder.getImage( recipe.recipeModel.imageKey ) );
                    model.addRow(row);
                }
                adapter.notifyDataSetChanged();

            }
        });
    }

    /**
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return recycler view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_third, container, false);
        Context context = container.getContext();

        //Recycler view init

        //model first
        this.model = new HomeRecyclerRowController();

        ApiBitmapHolder.init(context);

        //grab all viewed recipes from the database
        Executor get_recent = Executors.newSingleThreadExecutor();
        get_recent.execute(() -> {

            this.updateView();
        });

        //set the view to the layout RV
        this.view = view.findViewById(R.id.favRecyclerView);

        //setup the adapter
        this.adapter = new HomeRecycler(context, this.model, this); // Pass this as the OnItemClickListener

        //configure view
        this.view.setAdapter(this.adapter);
        this.view.setLayoutManager(new LinearLayoutManager(context));

        // Inflate the layout for this fragment
        return view;
    }

    /**
     * Runs whenever the fragment comes back into view
     */
    @Override
    public void onResume() {
        super.onResume();
        //update the recycler view
        Executor get_recent = Executors.newSingleThreadExecutor();
        get_recent.execute(() -> {
            this.updateView();
        });
    }



    /**
     * apparently this needs to be here, but it's not used.
     * @param position
     */
    @Override
    public void onItemClick(int position) {

    }
}
