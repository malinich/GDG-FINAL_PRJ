package com.hycorie.dev.gdg_final_prj;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class DishActivity extends AppCompatActivity {
    private Dish mDish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish);

        Intent i = getIntent();
        mDish = i.getExtras().getParcelable("DISH");
        Log.d("DISH", mDish.getName());

        assignUiElements();

    }

    private void assignUiElements() {
        TextView dishName = (TextView)findViewById(R.id.dish_name);
        dishName.setText(mDish.getName());

        ImageView imageView = (ImageView)findViewById(R.id.dish_image);
        imageView.setImageURI(mDish.getImage());

        ArrayAdapter adapter = new IngredientArrayAdapter(this, R.layout.ingredient_item, mDish.getIngredients());

        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE){
            LinearLayout layout = (LinearLayout) findViewById(R.id.dish_ingredients_land);

            final int adapterCount = adapter.getCount();
            for (int i = 0; i < adapterCount; i++) {
                View item = adapter.getView(i, null, null);
                layout.addView(item);
            }
        }
        else {
            ListView sv = (ListView) findViewById(R.id.dish_ingredients_vert);
            sv.setAdapter(adapter);
        }

        TextView description = (TextView)findViewById(R.id.dish_description);
        description.setText(mDish.getDescription());

        TextView score = (TextView)findViewById(R.id.dish_score);
        score.setText(mDish.getScore().toString());

    }

    private class IngredientArrayAdapter extends ArrayAdapter {
        Context mContext;

        public IngredientArrayAdapter(Context context, int resource, List<Product> objects) {
            super(context, resource, objects);
            mContext = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.ingredient_item, parent, false);
            }
            Ingredient item = (Ingredient)getItem(position);

            TextView tv = (TextView)convertView.findViewById(R.id.ingredient_item_name);
            tv.setText(item.getName());

            ImageView iv = (ImageView)convertView.findViewById(R.id.ingredient_item_image);
            iv.setImageURI(item.getImage());

            return convertView;
        }
    }
}
