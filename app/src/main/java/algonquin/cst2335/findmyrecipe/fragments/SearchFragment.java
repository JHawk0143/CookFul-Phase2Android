package algonquin.cst2335.findmyrecipe.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.findmyrecipe.R;
import algonquin.cst2335.findmyrecipe.api.ApiBitmapHolder;
import algonquin.cst2335.findmyrecipe.api.ApiConnection;
import algonquin.cst2335.findmyrecipe.api.ApiJSONArrayHolder;
import algonquin.cst2335.findmyrecipe.api.ApiJSONObjectHolder;
import algonquin.cst2335.findmyrecipe.database.DbHolder;
import algonquin.cst2335.findmyrecipe.database.models.PantryModel;
import algonquin.cst2335.findmyrecipe.databinding.FragmentSearchBinding;
import algonquin.cst2335.findmyrecipe.recyclerviews.ingredientmgr.IngredientRecyclerRowData;
import algonquin.cst2335.findmyrecipe.recyclerviews.searchrecycler.SearchRecycler;
import algonquin.cst2335.findmyrecipe.recyclerviews.searchrecycler.SearchRecyclerRowData;
import algonquin.cst2335.findmyrecipe.recyclerviews.searchrecycler.SearchRecyclerRowModel;

/**
 * Recipe search fragment
 */
public class SearchFragment extends Fragment {

    Context context;
    View view;
    private SearchRecyclerRowModel model;
    private RecyclerView recycler;
    private SearchRecycler recycAdapter;
    private FragmentSearchBinding binding;
    private ApiConnection connection;
    private ApiJSONArrayHolder jsonArray;
    private ApiJSONObjectHolder jsonObj;
    //private ApiBitmapHolder bitmaps;
    private SharedPreferences searchConfig;

    private int ingredientCount;
    private String currentQuery;
    private boolean complex;
    private String selected;

    /**
     * Observer response function, takes the response json and parses it into rows
     * @param json_array API response
     */
    private void responseReceived(JSONArray json_array)  {
        //Toast.makeText(getContext(), "response", Toast.LENGTH_SHORT).show();

        Log.w("ingredient fragment", json_array.toString());

        this.model.removeAll();
        this.recycAdapter.notifyDataSetChanged();

        //Iterate through json array, pulling out the objects
        for(int i = 0; i < json_array.length(); i++)  {
            JSONObject obj;
            SearchRecyclerRowData row = new SearchRecyclerRowData();

            //setup the row data
            try  {


                obj = json_array.getJSONObject(i);

                if(this.searchConfig.getBoolean("strict", false) == true)  {
                    if(obj.getInt("missedIngredientCount") > 0)  {
                        continue;
                    }
                }

                Log.w("ingredient fragment", obj.toString());

                row.setName( obj.getString("title") );
                row.setUsed( obj.getInt("usedIngredientCount") );
                row.setMissing( obj.getInt("missedIngredientCount") );
                row.setApid( obj.getInt("id") );



                String image_key = "img" + String.valueOf( obj.getInt("id") );
                row.setImageKey(image_key);

                if(ApiBitmapHolder.getImages().getValue().containsKey(image_key))  {
                    row.setImage( ApiBitmapHolder.getImage(image_key) );
                }
                else  {
                    connection.getBitmap(obj.getString("image"), image_key);
                }

                //because the same ingredient can be used in different ways in a recipe, the
                //count of ingredient args and returned used ingredients may differ.
                if(obj.getInt("usedIngredientCount") > ingredientCount)  {
                    row.setUnused(0);
                }
                else {
                    row.setUnused(ingredientCount - obj.getInt("usedIngredientCount"));
                }

                Log.w("ingredient fragment", "adding " + row.getName());
                model.addRow(row);

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }

        recycAdapter.notifyDataSetChanged();

    }

    private void objectReceived(JSONObject json_object)  {
        //Toast.makeText(getContext(), "response", Toast.LENGTH_SHORT).show();

        Log.w("ingredient fragment", json_object.toString());

        this.model.removeAll();
        this.recycAdapter.notifyDataSetChanged();

        try {
            JSONArray results = json_object.getJSONArray("results");
            for(int i = 0; i < results.length(); i++)  {
                JSONObject result = results.getJSONObject(i);
                SearchRecyclerRowData row = new SearchRecyclerRowData();

                row.setApid( result.getInt("id") );
                row.setName( result.getString("title") );
                row.setUnused(0);
                row.setUsed(0);
                row.setMissing(0);

                String image_key = "img" + String.valueOf( result.getInt("id") );
                row.setImageKey(image_key);

                if(ApiBitmapHolder.getImages().getValue().containsKey(image_key))  {
                    row.setImage( ApiBitmapHolder.getImage(image_key) );
                }
                else  {
                    connection.getBitmap(result.getString("image"), image_key);
                }

                model.addRow(row);

            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        recycAdapter.notifyDataSetChanged();

    }

    /**
     * Creates the URL arguments from the SharedPreferences.
     * @return string of URL arguments
     */
    private String generateIngredientArguments()  {

        StringBuilder arg_builder = new StringBuilder();

        arg_builder.append("&");
        arg_builder.append("ingredients=");
        arg_builder.append( this.currentQuery );
        arg_builder.append("&");
        arg_builder.append("ranking=");
        arg_builder.append(this.searchConfig.getInt("ranking", 1));
        arg_builder.append("&");
        arg_builder.append("number=");
        arg_builder.append(this.searchConfig.getInt("number", 10));
        arg_builder.append("&");
        arg_builder.append("offset=");
        arg_builder.append(this.searchConfig.getInt("offset", 0));
        arg_builder.append("&");
        arg_builder.append("ignorePantry=");
        arg_builder.append(this.searchConfig.getBoolean("ignorePantry", true));

        return arg_builder.toString();
    }
    private String generateRecipeArguments()  {

        StringBuilder arg_builder = new StringBuilder();

        arg_builder.append("&");
        arg_builder.append("ranking=");
        arg_builder.append(this.searchConfig.getInt("ranking", 1));
        arg_builder.append("&");
        arg_builder.append("number=");
        arg_builder.append(this.searchConfig.getInt("number", 10));
        arg_builder.append("&");
        arg_builder.append("offset=");
        arg_builder.append(this.searchConfig.getInt("offset", 0));
        arg_builder.append("&");
        arg_builder.append("includeIngredients=");
        arg_builder.append(this.currentQuery);

        arg_builder.append("&");
        arg_builder.append("intolerances=");

        Map<String, ? extends Boolean> config_list = (Map<String, ? extends Boolean>) this.searchConfig.getAll();
        for(Map.Entry<String, ? extends Boolean> setting : config_list.entrySet())  {
            if(setting.getValue().booleanValue())  {
                arg_builder.append(setting.getKey() + ",");
            }
        }

        return arg_builder.toString();
    }

    private void setSelected()  {
        Executor get_selected = Executors.newSingleThreadExecutor();
        get_selected.execute(() -> {
            List<PantryModel> rows = DbHolder.getDb().PantryDao().getSelectedIngredients();

            if(rows.isEmpty() || rows == null)  {
                selected = "";
                return;
            }

            StringBuilder builder = new StringBuilder();
            for(PantryModel pantry_row : rows)  {
                builder.append(pantry_row.name);
                builder.append(" ");
            }
            selected = builder.toString();
        });
    }

    private void searchByIngredients()  {
        EditText txt = view.findViewById(R.id.searchFragSearchBar);
        this.currentQuery = this.selected + txt.getText().toString();
        txt.setText("");

        if(this.currentQuery.isEmpty())  {
            Toast.makeText(getContext(), "Must enter at least one ingredient", Toast.LENGTH_SHORT).show();
            return;
        }

        //Toast.makeText(getContext(), "Searching for recipes", Toast.LENGTH_SHORT).show();

        ingredientCount = 1;
        char space = ' ';
        for(int i = 0; i < this.currentQuery.length(); i++)  {
            if(this.currentQuery.charAt(i) == space)  {
                ingredientCount++;
            }
        }
        Log.d("cnt", String.valueOf(ingredientCount).toString());

        this.currentQuery = this.currentQuery.replace(" ", ",");

        String query = generateIngredientArguments();

        this.connection.searchByIngredients(query);
        Log.d("Search", query);
    }

    private void advancedSearch()  {
        EditText txt = view.findViewById(R.id.searchFragSearchBar);
        this.currentQuery = this.selected + txt.getText().toString();
        txt.setText("");

        if(this.currentQuery.isEmpty())  {
            Toast.makeText(getContext(), "Must enter at least one ingredient", Toast.LENGTH_SHORT).show();
            return;
        }

        //Toast.makeText(getContext(), "Searching for recipes", Toast.LENGTH_SHORT).show();

        this.currentQuery = this.currentQuery.replace(" ", ",");

        String query = generateRecipeArguments();

        this.connection.searchForRecipes(query);
        Log.d("Search", query);

    }

    private void updateImages()  {
        HashMap<String, Bitmap> image_list = ApiBitmapHolder.getImages().getValue();

        //Iterate through the set of images in the list
        for( Map.Entry<String, Bitmap> item : image_list.entrySet() )  {

            String image_key = item.getKey();
            Bitmap image = item.getValue();
            ArrayList<Integer> positions = new ArrayList<Integer>(); //the list rows to be updated

            //Check every row and see if it needs the image updated.
            for( SearchRecyclerRowData row : this.model.getAllRows() )  {
                if( row.getImageKey().equals(image_key) )  {
                    if(row.getImage() == null)  {
                        row.setImage(image);
                        if( !positions.contains(row.getPosition()) )  { positions.add( row.getPosition() ); }
                    }
                }
            }

            //update the rows needing images by recreation. This lets the recycler view
            //re-bind everything so it updates.
            for(Integer position : positions)  {
                SearchRecyclerRowData old_row = this.model.getRow(position);
                SearchRecyclerRowData new_row = new SearchRecyclerRowData();

                new_row.setPosition(position);
                new_row.setImageKey( old_row.getImageKey() );
                new_row.setUnused( old_row.getUnused() );
                new_row.setUsed( old_row.getUsed() );
                new_row.setMissing( old_row.getMissing() );
                new_row.setName( old_row.getName() );
                new_row.setApid( old_row.getApid() );
                new_row.setImage( old_row.getImage() );

                this.model.updateRow(new_row, position);
                recycAdapter.notifyItemChanged(position);
            }
        }

    }

    /**
     * fragment "constructor"
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return
     */
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        setSelected();
        //setup class variables.
        this.view = inflater.inflate(R.layout.fragment_search, container, false);
        this.context = container.getContext();
        this.binding = FragmentSearchBinding.inflate(inflater, container, false);
        this.jsonArray = new ApiJSONArrayHolder();
        this.jsonObj = new ApiJSONObjectHolder();
        //this.bitmaps = new ApiBitmapHolder();

        ApiBitmapHolder.init( this.context );


        this.connection = new ApiConnection(context, jsonArray, jsonObj);
        this.searchConfig = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);

        this.complex = false;
        Map<String, ? extends Boolean> config_list = (Map<String, ? extends Boolean>) this.searchConfig.getAll();
        for(Map.Entry<String, ? extends Boolean> setting : config_list.entrySet())  {
            if(setting.getKey().equals("strict"))  {
                continue;
            }
            this.complex = this.complex || setting.getValue().booleanValue();
        }

        //Response observer
        final Observer<JSONArray> jsonObserver = this::responseReceived;
        jsonArray.getJSON().observe(getViewLifecycleOwner(), jsonObserver);

        final Observer<JSONObject> jsonObjectObserver = this::objectReceived;
        jsonObj.getJSON().observe(getViewLifecycleOwner(), jsonObjectObserver);

        final Observer<HashMap<String, Bitmap>> bitmapObserver = stringBitmapHashMap -> updateImages();
        ApiBitmapHolder.getImages().observe(getViewLifecycleOwner(), bitmapObserver);

        /**
         * click listeners
         */

        //Search button
        Button search_button = view.findViewById(R.id.searchFragSearchButton);
        search_button.setOnClickListener( v -> {

            this.model.removeAll();
            this.recycAdapter.notifyDataSetChanged();
            SearchRecyclerRowData row = new SearchRecyclerRowData();
            row.setName("Searching...");
            this.model.addRow(row);

            if( this.complex )  { this.advancedSearch(); }
            else  { this.searchByIngredients(); }
        });


        //additional options button
        Button options = view.findViewById(R.id.searchFragAdvancedButton);
        options.setOnClickListener( v -> {

            FragmentTransaction frag = getParentFragmentManager().beginTransaction();
            frag.replace(R.id.homeFragment, new SearchOptionsFragment());
            frag.commit();
        });

        /**
         * ****
         */

        //setup the recycler
        this.model = new SearchRecyclerRowModel();
        this.recycler = view.findViewById(R.id.rvResults);
        this.recycAdapter = new SearchRecycler(context, model);
        this.recycler.setAdapter( recycAdapter );
        this.recycler.setLayoutManager( new LinearLayoutManager(context) );


        return view;

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