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
        p1.setImage(Uri.parse("android.resource://com.hycorie.dev.gdg_final_prj/" + R.drawable.ingredient_banana));
        p.add(p1);

        Ingredient p2 = new Ingredient();
        p2.setId(2);
        p2.setName("маракуйя");
        p2.setImage(Uri.parse("android.resource://com.hycorie.dev.gdg_final_prj/" + R.drawable.ingredient_passionfruit));
        p.add(p2);

        Ingredient p3 = new Ingredient();
        p3.setId(3);
        p3.setName("рыба");
        p3.setImage(Uri.parse("android.resource://com.hycorie.dev.gdg_final_prj/" + R.drawable.ingredient_fish));
        p.add(p3);

        Ingredient p4 = new Ingredient();
        p4.setId(4);
        p4.setName("морковь");
        p4.setImage(Uri.parse("android.resource://com.hycorie.dev.gdg_final_prj/" + R.drawable.ingedient_carrot));
        p.add(p4);

        Ingredient p5 = new Ingredient();
        p5.setId(5);
        p5.setName("белочка");
        p5.setImage(Uri.parse("android.resource://com.hycorie.dev.gdg_final_prj/" + R.drawable.ingredient_squirrel));
        p.add(p5);

        Ingredient p6 = new Ingredient();
        p6.setId(6);
        p6.setName("яйца");
        p6.setImage(Uri.parse("android.resource://com.hycorie.dev.gdg_final_prj/" + R.drawable.ingredient_egg));
        p.add(p6);

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
            setName("Пюре из бананов");
            setDescription("Пюре из бананов это не просто 2 кг бананов но и очень ценная кожура" +
                    " дает незаменимый вкус и помогает при переходе через атлантику в брод");
            setIngredients(new ArrayList<String>() {{
                add("банан");
                add("банан");
                add("банан");
            }});
            setScore(5);
            setImage(Uri.parse("android.resource://com.hycorie.dev.gdg_final_prj/" + R.drawable.dish_banana_puree));
        }};
        d.add(d1);

        Dish d2 = new Dish(){{
            setId(2);
            setName("Пюре из маракуи");
            setDescription("Маракуя сама по себе не очень вкусная, но когда ее много она просто " +
                    "незабываемо помогает при хождении по потолкам");
            setIngredients(new ArrayList<String>() {{
                add("маракуйя");
                add("маракуйя");
                add("маракуйя");
            }});
            setScore(4);
            setImage(Uri.parse("android.resource://com.hycorie.dev.gdg_final_prj/"  + R.drawable.dish_passionfruit_puree));
        }};
        d.add(d2);

        Dish d3 = new Dish(){{
            setId(3);
            setName("Венегрет");
            setDescription("Какой же венегрет без селедки и веселья");
            setIngredients(new ArrayList<String>() {{
                add("морковь");
                add("рыба");
                add("белочка");
            }});
            setScore(5);
            setImage(Uri.parse("android.resource://com.hycorie.dev.gdg_final_prj/"  + R.drawable.dish_venegret));
        }};
        d.add(d3);

        Dish d4 = new Dish(){{
            setId(4);
            setName("Мел");
            setDescription("Мел едят не просто люди, а веселые люди");
            setIngredients(new ArrayList<String>() {{
                add("мел");
                add("мел");
                add("белочка");
            }});
            setScore(2);
            setImage(Uri.parse("android.resource://com.hycorie.dev.gdg_final_prj/"  + R.drawable.dish_chalk));
        }};
        d.add(d4);

        Dish d5 = new Dish(){{
            setId(5);
            setName("Маракуя по тибетски");
            setDescription("Маракую редко встретить в Тебете, но если вы ее нашли вам дорого в нирвану");
            setIngredients(new ArrayList<String>() {{
                add("маракуя");
                add("маракуя");
                add("белочка");
            }});
            setScore(4);
            setImage(Uri.parse("android.resource://com.hycorie.dev.gdg_final_prj/"  + R.drawable.dish_passion_fruit_tibet));
        }};
        d.add(d5);

        Dish d6 = new Dish(){{
            setId(6);
            setName("Бананы по-мужски");
            setDescription("Вы думаете легко приготовить бананы, а вы их пробовали сьесть живьем");
            setIngredients(new ArrayList<String>() {{
                add("банан");
                add("банан");
                add("белочка");
            }});
            setScore(3);
            setImage(Uri.parse("android.resource://com.hycorie.dev.gdg_final_prj/"  + R.drawable.dish_banan_men));
        }};
        d.add(d6);

        Dish d7 = new Dish(){{
            setId(7);
            setName("Банан-омлет");
            setDescription("Банан с омлетом просто красивый");
            setIngredients(new ArrayList<String>() {{
                add("банан");
                add("яйца");
                add("белочка");
            }});
            setScore(4);
            setImage(Uri.parse("android.resource://com.hycorie.dev.gdg_final_prj/"  + R.drawable.dish_banan_omelet));
        }};
        d.add(d7);

        Dish d8 = new Dish(){{
            setId(8);
            setName("Малина");
            setDescription("Это же малина, как ее можно не любить она всем ягода ягода");
            setIngredients(new ArrayList<String>() {{
                add("малина");
                add("яйца");
                add("белочка");
            }});
            setScore(6);
            setImage(Uri.parse("android.resource://com.hycorie.dev.gdg_final_prj/"  + R.drawable.dish_raspberry));
        }};
        d.add(d8);

        try {
            mSerializer.save(d);
        }
        catch (IOException |JSONException e){
            throw new RuntimeException(e.toString());
        }
    }
}
