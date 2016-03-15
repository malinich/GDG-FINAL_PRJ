package com.hycorie.dev.gdg_final_prj.Util;


import android.net.Uri;

import com.hycorie.dev.gdg_final_prj.Dish;
import com.hycorie.dev.gdg_final_prj.Ingredient;
import com.hycorie.dev.gdg_final_prj.Product;
import com.hycorie.dev.gdg_final_prj.ProductSerializer;
import com.hycorie.dev.gdg_final_prj.R;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JSONStore {
    private ProductSerializer<Product> mSerializer;

    public <T extends ProductSerializer<Product>>void setSerializer(T serializer) {
        mSerializer = serializer;
    }

    public void createIngredients(){
        List<Product> p = new ArrayList<>();
        Ingredient p1 = new Ingredient();
        p1.setId(1);
        p1.setName("банан");
        p1.setImage(Uri.parse("android.resource://com.hycorie.dev.gdg_final_prj/" + R.drawable.banana));
        p.add(p1);

        Ingredient p2 = new Ingredient();
        p2.setId(2);
        p2.setName("маракуйя");
        p2.setImage(Uri.parse("android.resource://com.hycorie.dev.gdg_final_prj/" + R.drawable.passionfruit));
        p.add(p2);

        try {
            mSerializer.save(p);
        }
        catch (IOException |JSONException e){
            throw new RuntimeException(e.toString());
        }
    }

    public void createDishes(){
        List<Product> d = new ArrayList<>();
        Dish d1 = new Dish(){{
            setId(1);
            setName("Банана пюре");
            setDescription("Очень вкусная балалайка");
            setIngredients(new ArrayList<String>() {{
                add("банан");
                add("potato");
                add("fish");
            }});
            setScore(5);
            setImage(Uri.parse("android.resource://com.hycorie.dev.gdg_final_prj/" + R.drawable.banana_puree));
        }};

        Dish d2 = new Dish(){{
            setId(2);
            setName("Маракуйя пюре");
            setDescription("Очень вкусная маракушка");
            setIngredients(new ArrayList<String>() {{
                add("маракуйя");
                add("маракуйя");
                add("маракуйя");
            }});
            setScore(4);
            setImage(Uri.parse("android.resource://com.hycorie.dev.gdg_final_prj/"  + R.drawable.passionfruit_puree));
        }};
        d.add(d1);
        d.add(d2);
        d.add(d2);
        d.add(d2);
        d.add(d2);
        d.add(d2);
        d.add(d2);
        d.add(d2);
        d.add(d2);
        d.add(d2);
        d.add(d2);
        d.add(d2);
        d.add(d2);
        d.add(d2);

        try {
            mSerializer.save(d);
        }
        catch (IOException |JSONException e){
            throw new RuntimeException(e.toString());
        }
    }
}
