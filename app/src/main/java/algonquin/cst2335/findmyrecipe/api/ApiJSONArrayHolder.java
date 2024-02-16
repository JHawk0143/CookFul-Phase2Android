package algonquin.cst2335.findmyrecipe.api;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONArray;

public class ApiJSONArrayHolder extends ViewModel {

    private MutableLiveData<JSONArray> jsonObject;

    /**
     * Gets the reference for the holder, NOT the JSON object
     * @return holder reference
     */
    public MutableLiveData<JSONArray> getJSON()  {
        if(jsonObject == null)  { this.jsonObject = new MutableLiveData<JSONArray>(); }
        return this.jsonObject;
    }

    /**
     * Set the JSON object
     * @param json JSON to save
     */
    public void setJSON( JSONArray json )  {
        this.jsonObject.setValue(json);
    }


}
