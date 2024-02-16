package algonquin.cst2335.findmyrecipe.recyclerviews.homerecycler;

import android.graphics.Bitmap;

/**
 * Row model for the home recycler.
 */
public class HomeRecyclerRowData {

    Bitmap thumbnail;
    String title;

    int apid;
    String image_key;

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getApid() {
        return apid;
    }

    public void setApid(int apid) {
        this.apid = apid;
    }

    public String getImage_key() {
        return image_key;
    }

    public void setImageKey(String image_key) {
        this.image_key = image_key;
    }
}
