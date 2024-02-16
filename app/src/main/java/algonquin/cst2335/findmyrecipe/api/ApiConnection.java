package algonquin.cst2335.findmyrecipe.api;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class ApiConnection {

    /**
     * Not ideal, but good enough for prototyping.
     * Better idea to hide the key on a server and have the app call for it (but the key will still be readable in memory)
     * the hardcoded api links isn't great either, but again it's good enough for now.
     */
    private String key = "bbfe758ff961457c811925364256db2b";
    private String ingredientSearchEndpoint = "https://api.spoonacular.com/recipes/findByIngredients?apiKey=bbfe758ff961457c811925364256db2b";
    private String recipeSearch = "https://api.spoonacular.com/recipes/complexSearch?apiKey=bbfe758ff961457c811925364256db2b";
    private String recipeDataEndpoint = "https://api.spoonacular.com/recipes/*/information?apiKey=bbfe758ff961457c811925364256db2b&includeNutrition=false";

    private RequestQueue requestQueue;
    private JsonArrayRequest jsonArrayRequester;
    private JsonObjectRequest jsonObjectRequester;
    private ImageRequest bitmapRequest;

    private ApiJSONArrayHolder jsonArrayHolder;
    private ApiJSONObjectHolder jsonObjectHolder;
    private Context context;
    //private ApiBitmapHolder bitmapHolder;

    /**
     * Constructor
     * @param context Application contexst
     * @param json_holder reference to the object that will hold json arrays retrieved from the API
     * @param json_obj reference to the object that will hold json objects "                       "
     */
    public ApiConnection(Context context, ApiJSONArrayHolder json_holder, ApiJSONObjectHolder json_obj)  {
        this.requestQueue = Volley.newRequestQueue(context);
        this.context = context;
        this.jsonArrayHolder = json_holder;
        this.jsonObjectHolder = json_obj;
        //this.bitmapHolder = bitmap_holder;
    }

    /**
     * Get all the data about a recipe
     * @param apid key to the recipe in the api
     */
    public void getRecipeData(int apid)  {
        //set the key in the link
        String query = recipeDataEndpoint.replace( "*", String.valueOf(apid) );
        Log.d("recipedata", query);

        //make the request and add it to the queue
        this.jsonObjectRequester = new JsonObjectRequest(Request.Method.GET, query, null, new Response.Listener<JSONObject>()  {
            @Override
            public void onResponse(JSONObject response)  {
                Log.w("VOLLEY_CONNECTION", response.toString());
                jsonObjectHolder.setJSON(response);
            }
        }, new Response.ErrorListener()  {
            @Override
            public void onErrorResponse(VolleyError error)  {
                Log.w("VOLLEY_CONNECTION", error.toString());
            }
        });
        this.requestQueue.add(this.jsonObjectRequester);

    }

    /**
     * Search the api given a list of ingredients.
     * @param query whitespace separated list of ingredients.
     */
    public void searchByIngredients(String query)  {
        String endpoint = ingredientSearchEndpoint + query;
        Log.d("api", endpoint);
        this.jsonArrayRequester = new JsonArrayRequest(Request.Method.GET, endpoint, null, new Response.Listener<JSONArray>()  {

            @Override
            public void onResponse(JSONArray response)  {
                Log.w("VOLLEY_CONNECTION", response.toString());
                jsonArrayHolder.setJSON(response);
            }
        }, new Response.ErrorListener()  {
            @Override
            public void onErrorResponse(VolleyError error)  {
                Log.w("VOLLEY_CONNECTION", error.toString());
            }
        });
        this.requestQueue.add( this.jsonArrayRequester);

    }

    public void searchForRecipes(String query)  {
        String endpoint = recipeSearch + query;
        Log.d("api", endpoint);
        this.jsonObjectRequester = new JsonObjectRequest(Request.Method.GET, endpoint, null, new Response.Listener<JSONObject>()  {

            @Override
            public void onResponse(JSONObject response)  {
                Log.w("VOLLEY_CONNECTION", response.toString());
                jsonObjectHolder.setJSON(response);
            }
        }, new Response.ErrorListener()  {
            @Override
            public void onErrorResponse(VolleyError error)  {
                Log.w("VOLLEY_CONNECTION", error.toString());
            }
        });
        this.requestQueue.add( this.jsonObjectRequester);

    }

    /**
     * Grab an image and save it
     * @param url link to the image
     * @param image_key the key that will reference the image
     */
    public void getBitmap(String url, String image_key)  {
        this.bitmapRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                ApiBitmapHolder.setImage(context, image_key, response);
            }
        }, 2048, 2048, ImageView.ScaleType.CENTER, null, (error ) -> {
        });
        this.requestQueue.add( this.bitmapRequest );
    }

}
