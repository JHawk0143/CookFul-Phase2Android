package algonquin.cst2335.findmyrecipe.api;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONArray;
import org.json.JSONObject;

public class ApiJSONObjectHolder extends ViewModel {

    private MutableLiveData<JSONObject> jsonObject;

    /**
     * Gets the reference for the holder, NOT the JSON object
     * @return holder reference
     */
    public MutableLiveData<JSONObject> getJSON()  {
        if(jsonObject == null)  { this.jsonObject = new MutableLiveData<JSONObject>(); }
        return this.jsonObject;
    }

    /**
     * Set the JSON object
     * @param json JSON to save
     */
    public void setJSON( JSONObject json )  {
        this.jsonObject.setValue(json);
    }


}
