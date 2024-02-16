package algonquin.cst2335.findmyrecipe.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.findmyrecipe.database.AppDatabase;
import algonquin.cst2335.findmyrecipe.database.DbHolder;
import algonquin.cst2335.findmyrecipe.database.models.PantryModel;
import algonquin.cst2335.findmyrecipe.R;
import algonquin.cst2335.findmyrecipe.databinding.FragmentIngredientBinding;
import algonquin.cst2335.findmyrecipe.recyclerviews.ingredientmgr.IngredientRecycler;
import algonquin.cst2335.findmyrecipe.recyclerviews.ingredientmgr.IngredientRecyclerRowData;
import algonquin.cst2335.findmyrecipe.recyclerviews.ingredientmgr.IngredientRecyclerRowModel;

public class IngredientFragment extends Fragment {
    EditText ingredient_edit;
    EditText quantity_edit;
    Spinner UOM_edit;
    private AppDatabase db;

    private FragmentIngredientBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        IngredientRecyclerRowModel recycModel;
        RecyclerView recycView;
        IngredientRecycler recycAdapter;

        View rootView = inflater.inflate(R.layout.fragment_ingredient, container, false);
        Context context = getContext();

        db = DbHolder.getDb();
        ingredient_edit = rootView.findViewById(R.id.pantryEnterName);

        recycModel = new IngredientRecyclerRowModel();
        recycView = rootView.findViewById( R.id.pantryRecycView );
        recycAdapter = new IngredientRecycler(context, recycModel, db);

        recycView.setAdapter( recycAdapter );
        recycView.setLayoutManager( new LinearLayoutManager(context) );


        //setup the saved ingredients
        Executor get_ingredients = Executors.newSingleThreadExecutor();
        get_ingredients.execute(() -> {
            List<PantryModel> rows = db.PantryDao().getAllIngredients();

            for(PantryModel pantry_row : rows)  {

                IngredientRecyclerRowData row = new IngredientRecyclerRowData();

                row.setIngredientId( pantry_row.ingredient_id );
                row.setName( pantry_row.name );
                row.setChecked( pantry_row.selected );
                //row.setUom(pantry_row.unitsModel.name);

                recycModel.addRow( row );
            }

        });

        /**
         * CLICK LISTENERS
         */

        Button submitButton = rootView.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(clk -> {

            Log.d("ingredient fragment", ingredient_edit.getText().toString());

            Executor add_ingredient = Executors.newSingleThreadExecutor();
            add_ingredient.execute(() -> {

                PantryModel ingredient = new PantryModel();
                IngredientRecyclerRowData row = new IngredientRecyclerRowData();

                ingredient.name = ingredient_edit.getText().toString();

                row.setIngredientId( (int) db.PantryDao().insertIngredient(ingredient) );
                row.setName( ingredient_edit.getText().toString() );
                row.setChecked(false);

                recycModel.addRow(row);

                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        ingredient_edit.setText("");
                        recycAdapter.notifyDataSetChanged();

                    }
                });

            });

        });

        return rootView;


    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
