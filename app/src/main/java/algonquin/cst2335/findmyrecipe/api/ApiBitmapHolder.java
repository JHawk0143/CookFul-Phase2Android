package algonquin.cst2335.findmyrecipe.api;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;

public class ApiBitmapHolder extends ViewModel {

    private static MutableLiveData<HashMap<String, Bitmap>> images;

    /**
     * Constructor, initializes the listenable and the list inside.
     */
    /*
    public ApiBitmapHolder()  {
        images = new MutableLiveData<HashMap<String, Bitmap>>();
        images.setValue( new HashMap<String, Bitmap>() );
    }

     */

    public static void init(Context context)  {
        if(images != null)  { return; }
        images = new MutableLiveData<HashMap<String, Bitmap>>();
        images.setValue(new HashMap<String, Bitmap>());

        String[] files = context.fileList();
        for(String file : files)  {
            if( !file.contains("img") )  { continue; }

            FileInputStream fileInputStream;
            Bitmap image = null;
            try{
                fileInputStream = context.openFileInput(file);
                image = BitmapFactory.decodeStream(fileInputStream);
                fileInputStream.close();
            } catch(Exception e) {
                e.printStackTrace();
            }

            images.getValue().put(file, image);
        }

    }

    /**
     * Gets the reference for the holder object, NOT the list inside.
     * @return reference  to the holder object
     */
    public static MutableLiveData<HashMap<String, Bitmap>> getImages()  {
        if(images == null)  { return null; }
        return images;
    }

    /**
     * get a single image out of the list given a key. All rows have two keys for the small and large files
     * @param key image key
     * @return the image
     */
    public static Bitmap getImage(String key)  {
        if(images == null)  { return null; }
        return images.getValue().get(key);
    }

    /**
     * Store an image in the list, by creating a new list, adding the image, then copying over the list reference.
     * It's terrible, but the only way to get the listener to actually see an update.
     * @param key key to save the image with
     * @param image image to save
     */
    public static void setImage(Context context, String key, Bitmap image)  {
        if(images == null)  { return; }
        HashMap<String, Bitmap> new_map = images.getValue();
        new_map.put(key, image);
        images.setValue(new_map);
        saveImage(context, image, key);

    }

    public static void saveImage(Context context, Bitmap bitmap, String key){

        String file = key;
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = context.openFileOutput(key, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteImage(Context context, String key)  {
        context.deleteFile(key);
        images.getValue().remove(key);
    }

}
