package com.hycorie.dev.gdg_final_prj;

import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;

public class Ingredient extends Product{
    static final String ID = "id";
    static final String NAME = "name";
    static final String IMAGE = "image";


    private Integer mId;
    private String mName;
    private Uri mImage;

    public Ingredient(JSONObject jo) throws JSONException {
        this.setId(jo.getInt(ID));
        this.setName(jo.getString(NAME));
        this.setImage(jo.getString(IMAGE));
    }

    public Ingredient() {
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Uri getImage() {
        return mImage;
    }

    public void setImage(Uri image) {
        mImage = image;
    }

    public void setImage(String image) {
        mImage = Uri.parse(image);
    }

    @Override
    public JSONObject convertToJSON() throws JSONException{
        JSONObject jo = new JSONObject();

        jo.put(ID, mId);
        jo.put(NAME, mName);
        jo.put(IMAGE, mImage);

        return jo;
    }

    @Override
    public String toString() {
        return getName();
    }
}
