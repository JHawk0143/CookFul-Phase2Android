package algonquin.cst2335.findmyrecipe.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.findmyrecipe.api.ApiBitmapHolder;
import algonquin.cst2335.findmyrecipe.api.ApiConnection;
import algonquin.cst2335.findmyrecipe.api.ApiJSONArrayHolder;
import algonquin.cst2335.findmyrecipe.api.ApiJSONObjectHolder;
import algonquin.cst2335.findmyrecipe.database.DbHolder;
import algonquin.cst2335.findmyrecipe.database.models.RecipeModel;
import algonquin.cst2335.findmyrecipe.databinding.FragmentSearchDialogueBinding;

public class SearchFragmentDialog extends DialogFragment  {

    private int apid;
    private ApiJSONObjectHolder jsonObj;
    private ApiJSONArrayHolder jsonArray;
    //private ApiBitmapHolder bitmaps;
    private ApiConnection connection;

    private FragmentSearchDialogueBinding binding;
    private String website;

    private RecipeModel recipe;

    private boolean favorited;
    private RecyclerView.Adapter adapter;

    private void responseReceived(JSONObject jsonObject)  {
        //Toast.makeText(getContext(), "Recipe received", Toast.LENGTH_SHORT).show();
        this.recipe = new RecipeModel();

        try {

            this.website = jsonObject.getString("spoonacularSourceUrl");
            this.recipe.website = this.website;
            this.recipe.instructions = jsonObject.getString("instructions");
            this.recipe.name = jsonObject.getString("title");
            this.recipe.favorite = false;
            this.recipe.apid = jsonObject.getInt("id");
            this.recipe.prepTime = jsonObject.getInt("preparationMinutes");
            this.recipe.cookTime = jsonObject.getInt("cookingMinutes");
            this.recipe.readyTime = jsonObject.getInt("readyInMinutes");
            this.recipe.vegan = jsonObject.getBoolean("vegan");
            this.recipe.vegetarian = jsonObject.getBoolean("vegetarian");
            this.recipe.glutenfree = jsonObject.getBoolean("glutenFree");
            this.recipe.dairyfree = jsonObject.getBoolean("dairyFree");
            this.recipe.description = jsonObject.getString("summary");
            this.recipe.imageKey = "img" + String.valueOf(this.recipe.apid);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        Executor save_recipe = Executors.newSingleThreadExecutor();
        save_recipe.execute(() -> {
            DbHolder.getDb().RecipeDao().insertRecipe(this.recipe);
        });

        binding.searchdialogWebsite.setEnabled(true);
        binding.searchdialogFav.setEnabled(true);
        binding.searchdialogRemove.setEnabled(true);


    }

    //private void updateImage

    private void deleteRecipe() {
        Executor delete_recipe = Executors.newSingleThreadExecutor();
        delete_recipe.execute(() -> {
            DbHolder.getDb().RecipeDao().deleteRecipe(this.recipe);
        });
    }

    public void setAdapter(RecyclerView.Adapter adapter)  {
        this.adapter = adapter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        this.binding = FragmentSearchDialogueBinding.inflate(inflater);
        Bundle args = this.getArguments();

        this.jsonArray = new ApiJSONArrayHolder();
        this.jsonObj = new ApiJSONObjectHolder();
        //this.bitmaps = new ApiBitmapHolder();
        this.connection = new ApiConnection(getContext(), jsonArray, jsonObj);

        ApiBitmapHolder.init( getContext() );



        final Observer<JSONObject> jsonObserver = this::responseReceived;
        jsonObj.getJSON().observe(getViewLifecycleOwner(), jsonObserver);

        //final Observer<HashMap<String, Bitmap>> bitmapObserver = stringBitmapHashMap -> updateImages();
        //ApiBitmapHolder.getImages().observe(getViewLifecycleOwner(), bitmapObserver);

        TextView name = this.binding.searchdialogName;
        name.setText( args.getString("name") );

        this.apid = args.getInt("apid");

        ImageView image = binding.searchdialogImage;

        image.setImageBitmap( ApiBitmapHolder.getImage("img" + this.apid) );

        this.binding.searchdialogWebsite.setEnabled(false);
        this.binding.searchdialogFav.setEnabled(false);
        this.binding.searchdialogRemove.setEnabled(false);

        //does this already exist?

        Executor save_recipe = Executors.newSingleThreadExecutor();
        save_recipe.execute(() -> {
            RecipeModel recipe = DbHolder.getDb().RecipeDao().getRecipeByApid(this.apid);
            if(recipe == null)  {
                connection.getRecipeData(apid);
            }
            else  {
                this.recipe = recipe;
                this.website = recipe.website;
                this.favorited = recipe.favorite;

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.searchdialogWebsite.setEnabled(true);
                        binding.searchdialogFav.setEnabled(true);
                        binding.searchdialogRemove.setEnabled(true);

                        if(!favorited)  {
                            binding.searchdialogFav.setText("Favorite");
                        }
                        else  {
                            binding.searchdialogFav.setText("Unfavorite");
                        }

                    }
                });
            }
        });

        //Toast.makeText(getContext(), "Getting Recipe Data...", Toast.LENGTH_LONG).show();

        binding.searchdialogClose.setOnClickListener( v -> {
            dismiss();
        });

        binding.searchdialogWebsite.setOnClickListener( v -> {
            if(website.isEmpty() || website == null)  {
                Toast.makeText(getContext(), "No website", Toast.LENGTH_SHORT).show();
            }

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(website));
            startActivity(browserIntent);

        });

        binding.searchdialogRemove.setOnClickListener( v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Are you sure you want to remove this recipe?")
                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            deleteRecipe();
                            //adapter.notifyDataSetChanged();

                            dismiss();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            builder.create().show();
        });

        binding.searchdialogFav.setOnClickListener( v -> {
            this.recipe.favorite = !this.favorited;

            if(!favorited)  {
                binding.searchdialogFav.setText("Favorite");
            }
            else  {
                binding.searchdialogFav.setText("Unfavorite");
            }

            Executor fav = Executors.newSingleThreadExecutor();
            fav.execute(() -> {
                DbHolder.getDb().RecipeDao().updateRecipe(this.recipe);
            });
        });

        return binding.getRoot();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
    }
}
