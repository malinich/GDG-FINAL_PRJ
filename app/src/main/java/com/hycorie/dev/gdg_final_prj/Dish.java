package com.hycorie.dev.gdg_final_prj;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Dish extends Product{
    static final String ID = "id";
    static final String NAME = "name";
    static final String DESCRIPTION = "description";
    static final String INGREDIENTS = "ingredients";
    static final String IMAGE = "image";
    static final String SCORE = "score";

    private Integer mId;
    private List<Product> mIngredients;
    private String mDescription;
    private String mName;
    private Uri mImage;
    private Integer mScore;

    public Dish(JSONObject jo) throws JSONException {
        this.setId(jo.getInt(ID));
        this.setName(jo.getString(NAME));
        this.setDescription(jo.getString(DESCRIPTION));
        this.setIngredients(jo.getJSONArray(INGREDIENTS));
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

    public List<Product> getIngredients() {
        return mIngredients;
    }

    public void setIngredients(JSONArray items) throws JSONException{
        mIngredients = new ArrayList<>();

        for (int i = 0; i < items.length(); i++){
            JSONObject jo = items.getJSONObject(i);
            Ingredient in = new Ingredient(jo);
            mIngredients.add(in);
        }
    }
    public void setIngredients(List<Product> ingredients) {
        mIngredients = ingredients;
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

    public List<String> getIngredientsName(){
        List<String> ingredients = new ArrayList<>();
        for (int i=0; i < this.getIngredients().size(); i++){
            ingredients.add(this.getIngredients().get(i).getName());
        }
        return ingredients;
    }

    @Override
    public JSONObject convertToJSON() throws JSONException{
        JSONObject jo = new JSONObject();
        JSONArray jaIngredients = new JSONArray();
        for (int i=0; i < getIngredients().size(); i++){
            Product ingredient = getIngredients().get(i);
            jaIngredients.put(ingredient.convertToJSON());
        }

        jo.put(ID, mId);
        jo.put(NAME, mName);
        jo.put(DESCRIPTION, mDescription);
        jo.put(INGREDIENTS, jaIngredients);
        jo.put(IMAGE, mImage);
        jo.put(SCORE, mScore);

        return jo;
    }

    @Override
    public String toString() {
        return getName();
    }

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
        mIngredients = new ArrayList<>();

        this.setId(in.readInt());
        this.setName(in.readString());
        this.setDescription(in.readString());
        in.readList(mIngredients, Ingredient.class.getClassLoader());
        this.setImage(in.readString());
        this.setScore(in.readInt());
    }
}
