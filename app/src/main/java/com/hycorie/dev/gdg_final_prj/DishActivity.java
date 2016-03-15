package com.hycorie.dev.gdg_final_prj;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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

        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mDish.getIngredients());

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
}
