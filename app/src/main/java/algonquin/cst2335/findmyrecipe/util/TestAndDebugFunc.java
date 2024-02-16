package algonquin.cst2335.findmyrecipe.util;


import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class TestAndDebugFunc  {

    /**
     * Just a way to generate random strings for testing. This can be removed if required, but I like
     * having instrumentation and testing stuff hanging around
     * @param size size of the string expected
     * @return random string
     */
    public static String generateString(int size)  {
        StringBuilder str = new StringBuilder();
        Random rand = new Random();
        char[] characters = "ABCDEFGHIJKLMNOPQRSTUVQXYZabcdefghijklmnopqrstuvwxyz123456789 !@#$%^&*()_+{}[]-=;':,.<>/?".toCharArray();
        for(int i = 0; i < size; i++)  {
            str.append( characters[ rand.nextInt( characters.length -1 ) ] );
        }
        return str.toString();
    }

    public static String generateSaneString(int size)  {
        StringBuilder str = new StringBuilder();
        Random rand = new Random();
        char[] characters = "ABCDEFGHIJKLMNOPQRSTUVQXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
        for(int i = 0; i < size; i++)  {
            str.append( characters[ rand.nextInt( characters.length -1 ) ] );
        }
        return str.toString();
    }

    public static JSONArray jsonTestArray(Context context)  {
        JSONArray json;
        try  {
            json = new JSONArray( jsonTestData(context) );
        }
        catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return json;

    }

    private static String jsonTestData(Context context)  {
        String json = null;
        try {
            InputStream is = context.getAssets().open("json_data");

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
