package com.hycorie.dev.gdg_final_prj;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;


public class ProductSerializer<T extends Product> {

    private String mFilename;
    private Context mContext;


    public ProductSerializer(String fn, Context context){
        mFilename = fn;
        mContext = context;
    }


    public void save(List<T> products) throws IOException, JSONException{

        JSONArray jArray = new JSONArray();

        for (T p : products)
            jArray.put(p.convertToJSON());

        Writer writer = null;
        try {
            OutputStream out = mContext.openFileOutput(mFilename, mContext.MODE_PRIVATE);

            writer = new OutputStreamWriter(out);
            writer.write(jArray.toString());
        } finally {
            if (writer != null) {

                writer.close();
            }
        }
    }


    public <E> ArrayList<E> load(Class<E> cls) throws Exception{
        ArrayList<E> productList = new ArrayList<>();
        BufferedReader reader = null;

        try {
            InputStream in = mContext.openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null) {

                jsonString.append(line);
            }

            JSONArray jArray = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            for (int i = 0; i < jArray.length(); i++) {

                productList.add(cls.getDeclaredConstructor(JSONObject.class).newInstance(jArray.getJSONObject(i)));
            }
        } catch (FileNotFoundException e) {
            //
        } finally {
            if (reader != null)
                reader.close();
        }

        return productList;
    }
}