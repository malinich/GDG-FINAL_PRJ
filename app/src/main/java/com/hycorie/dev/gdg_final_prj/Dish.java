package com.hycorie.dev.gdg_final_prj;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Dish extends Product implements Parcelable{
    static final String ID = "id";
    static final String NAME = "name";
    static final String DESCRIPTION = "description";
    static final String INGREDIENTS = "ingredients";
    static final String IMAGE = "image";
    static final String SCORE = "score";

    private Integer mId;
    private List<String> mIngredients;
    private String mDescription;
    private String mName;
    private Uri mImage;
    private Integer mScore;

    public Dish(JSONObject jo) throws JSONException {
        this.setId(jo.getInt(ID));
        this.setName(jo.getString(NAME));
        this.setDescription(jo.getString(DESCRIPTION));
        this.setIngredients(jo.getString(INGREDIENTS));
        this.setImage(jo.getString(IMAGE));
        this.setScore(jo.getInt(SCORE));
    }

    public Dish() {
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

    public List<String> getIngredients() {
        return mIngredients;
    }

    public void setIngredients(String items) throws JSONException{
        mIngredients = new ArrayList<>();
        JSONArray ja = new JSONArray(items);

        for (int i = 0; i < ja.length(); i++){
            mIngredients.add(String.valueOf(ja.get(i)));
        }
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
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

    public Integer getScore() {
        return mScore;
    }

    public void setScore(Integer score) {
        mScore = score;
    }

    @Override
    public JSONObject convertToJSON() throws JSONException{
        JSONObject jo = new JSONObject();

        jo.put(ID, mId);
        jo.put(NAME, mName);
        jo.put(DESCRIPTION, mDescription);
        jo.put(INGREDIENTS, mIngredients);
        jo.put(IMAGE, mImage);
        jo.put(SCORE, mScore);

        return jo;
    }

    public void setIngredients(List<String> ingredients) {
        mIngredients = ingredients;
    }

    @Override
    public String toString() {
        return getName();
    }

    private int mData;

    // parcel
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(getId());
        out.writeString(getName());
        out.writeString(getDescription());
        out.writeList(getIngredients());
        out.writeString(getImage().toString());
        out.writeInt(getScore());
    }

    public static final Parcelable.Creator<Dish> CREATOR = new Parcelable.Creator<Dish>() {

        public Dish createFromParcel(Parcel in) {
            return new Dish(in);
        }

        public Dish[] newArray(int size) {
            return new Dish[size];
        }
    };

    private Dish(Parcel in) {
        mIngredients = new ArrayList<String>();

        this.setId(in.readInt());
        this.setName(in.readString());
        this.setDescription(in.readString());
        in.readList(mIngredients, null);
        this.setImage(in.readString());
        this.setScore(in.readInt());
    }
}
