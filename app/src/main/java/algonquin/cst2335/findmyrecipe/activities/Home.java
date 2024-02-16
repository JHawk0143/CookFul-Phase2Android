package algonquin.cst2335.findmyrecipe.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.findmyrecipe.R;

import algonquin.cst2335.findmyrecipe.database.DbHolder;
import algonquin.cst2335.findmyrecipe.databinding.ActivityHomeBinding;
import algonquin.cst2335.findmyrecipe.fragments.FavoriteFragment;
import algonquin.cst2335.findmyrecipe.fragments.IngredientFragment;
import algonquin.cst2335.findmyrecipe.fragments.SearchFragment;

import algonquin.cst2335.findmyrecipe.fragments.HomeFragment;
import algonquin.cst2335.findmyrecipe.util.TestAndDebugFunc;

public class Home extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityHomeBinding binding;

    /**
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setup the view binding and toolbar
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        Context context = this; //because.

        /*******************************************************************************************
            IMPORTANT:
                When the database structure is updated in any way, all data within the database
                will be deleted. This is fine for development, but must be changed prior to release.
         ******************************************************************************************/
        //the one holy reference to the database, just needs a context.
        DbHolder.initDb( getApplicationContext() );



        /**
         * HOME BUTTON LISTENER
         */
        binding.homeButton.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {

                FragmentTransaction frag = getSupportFragmentManager().beginTransaction();
                frag.replace(R.id.homeFragment, new HomeFragment());
                frag.commit();

                //Toast.makeText(context, "Home Page", Toast.LENGTH_SHORT).show();
            }
        } );

        /**
         * SEARCH BUTTON LISTENER
         */
        binding.searchButton.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {

                FragmentTransaction frag = getSupportFragmentManager().beginTransaction();
                frag.replace(R.id.homeFragment, new SearchFragment());
                frag.commit();

                //Toast.makeText(context, "Search Page", Toast.LENGTH_SHORT).show();
            }

        } );

        /**
         * FAVORITE BUTTON LISTENER
         */
        binding.starButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction frag = getSupportFragmentManager().beginTransaction();
                frag.replace(R.id.homeFragment, new FavoriteFragment());
                frag.commit();

                //Toast.makeText(context, "Favorites Page", Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * INGREDIENTS BUTTON LISTENER
         */
        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction frag = getSupportFragmentManager().beginTransaction();

                frag.replace(R.id.homeFragment, new IngredientFragment());
                frag.commit();

                //Toast.makeText(context, "Ingredients Page", Toast.LENGTH_SHORT).show();

            }
        });

        //debuggin json stuff, can be deleted or switched.
        JSONArray jsonArray = TestAndDebugFunc.jsonTestArray(getApplicationContext());
        Log.d("json", "got length: " + jsonArray.length());
        for(int i = 0; i < jsonArray.length(); i++)  {
            try  {
                JSONObject json = jsonArray.getJSONObject(i);
                Log.d("json", json.toString());

            }
            catch (JSONException e)  {
                throw new RuntimeException(e);
            }
        }

        //show the home fragment
        FragmentTransaction frag = getSupportFragmentManager().beginTransaction();
        frag.replace(R.id.homeFragment, new HomeFragment());
        frag.commit();

    }

    /**
     *
     * @param menu The options menu in which you place your items.
     *
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     *
     * @param item The menu item that was selected.
     *
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }

    /**
     *
     * @return
     */
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

}