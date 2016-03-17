package com.hycorie.dev.gdg_final_prj;


import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public abstract class Product implements Serializable, Parcelable{

    public abstract String getName();

    JSONObject convertToJSON() throws JSONException{
        return null;
    };
}
