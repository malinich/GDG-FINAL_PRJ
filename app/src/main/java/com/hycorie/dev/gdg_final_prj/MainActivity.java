package com.hycorie.dev.gdg_final_prj;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity{
    private ArrayAdapter mGoodsAdapter;
    private List<Ingredient> mIngredientList = new ArrayList<>();
    private List<Dish> filteredDishes = new ArrayList<>();
    private Spinner spinner_1;
    private Spinner spinner_2;
    private Spinner spinner_3;

    public static final String JSON_FILE_INGREDIENTS = "ingredients.json";
    public static final String JSON_FILE_DISHES = "dishes.json";
    private ProductSerializer<Ingredient> mIngredientSerializer;
    private ProductSerializer<Dish> mDishSerializer;
    private SpinStorage mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStorage = SpinStorage.getInstance();
        createJSONDate();
        addAdapter();
        assignUIElements();
        assignClickHandler();
        loadSpinnerData();
    }

    private void createJSONDate() {
        mIngredientSerializer = new ProductSerializer<>(JSON_FILE_INGREDIENTS, this);
        mDishSerializer = new ProductSerializer<>(JSON_FILE_DISHES, this);

        List<Ingredient> p = new ArrayList<>();
        Ingredient p1 = new Ingredient();
        p1.setId(1);
        p1.setName("банан");
        p1.setImage(Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.banana));
        p.add(p1);

        Ingredient p2 = new Ingredient();
        p2.setId(2);
        p2.setName("маракуйя");
        p2.setImage(Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.passionfruit));
        p.add(p2);

        List<Dish> d = new ArrayList<>();
        Dish d1 = new Dish(){{
            setId(1);
            setName("Банана пюре");
            setDescription("Очень вкусная балалайка");
            setIngredients(new ArrayList<String>() {{
                add("банан");
                add("potato");
                add("fish");
            }});
            setImage(Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.banana_puree));
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
            setImage(Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.passionfruit_puree));
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
            mIngredientSerializer.save(p);
            mDishSerializer.save(d);
        }
        catch (IOException |JSONException e){
            throw new RuntimeException(e.toString());
        }
    }

    private void addAdapter() {
        try {
            mIngredientList = mIngredientSerializer.load(Ingredient.class);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
        mGoodsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mIngredientList);
        mGoodsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    private void assignUIElements() {
        spinner_1 = (Spinner)findViewById(R.id.spinner_1);
        spinner_2 = (Spinner)findViewById(R.id.spinner_2);
        spinner_3 = (Spinner)findViewById(R.id.spinner_3);

    }

    private void assignClickHandler(){
        mStorage.setSpinHandler(new SpinHandler() {

            @Override
            public void spinFilled(List<SpinController> spinControllers) {
                ListView sv = (ListView) findViewById(R.id.dish_items);

                List<String> items = getSpinerItems();
                filteredDishes = getDishesDataByIngredients(items, getDishesData());

                DishArrayAdapter adapter = new DishArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1, filteredDishes);

                sv.setAdapter(adapter);
                sv.setOnItemClickListener(new DishClickListener());
            }

            private List<Dish> getDishesDataByIngredients(List<String> items, List<Dish> dishes) {
                List<Dish> result = new ArrayList<>();
                for (Dish dish: dishes){
                    boolean b = Collections.disjoint(items, dish.getIngredients());

                    if (!b){
                        result.add(dish);
                    }
                }
                return result;
            }

            private List<Dish> getDishesData() {
                ProductSerializer<Dish> serializer = new ProductSerializer<>(JSON_FILE_DISHES, MainActivity.this);
                List<Dish> dishes = new ArrayList<>();
                try {
                    dishes = serializer.load(Dish.class);
                }
                catch (Exception e){
                    Log.d("error serializer", e.getMessage());
                }
                return dishes;
            }

            private List<String> getSpinerItems() {
                List<String> ingredients = new ArrayList<>();

                for(SpinController s: mStorage.getStorage()){
                    ingredients.add(s.getIngredient().getName());
                }
                return ingredients;

            }
        });
        spinner_1.setOnItemSelectedListener(new SpinClick(0));
        spinner_2.setOnItemSelectedListener(new SpinClick(1));
        spinner_3.setOnItemSelectedListener(new SpinClick(2));
    };

    private void loadSpinnerData() {
        spinner_1.setAdapter(mGoodsAdapter);
        spinner_2.setAdapter(mGoodsAdapter);
        spinner_3.setAdapter(mGoodsAdapter);

    }

    private class SpinClick implements AdapterView.OnItemSelectedListener {
        private int INDEX;

        public SpinClick(int INDEX) {
            this.INDEX = INDEX;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Ingredient tempIngredient = (Ingredient)mGoodsAdapter.getItem(position);

            ImageView img = getImageView(parent);

            SpinController sp = new SpinController(tempIngredient);
            sp.update(img);

            mStorage.getStorage().remove(INDEX);
            mStorage.add(INDEX, sp);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

        private ImageView getImageView(View v) {
            String tag = (String) v.getTag();
            int resId = getResources().getIdentifier(tag, "id", MainActivity.this.getPackageName());
            return (ImageView)findViewById(resId);
        }
    }

    private class DishClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent i = new Intent(MainActivity.this, DishActivity.class);
            i.putExtra("DISH", filteredDishes.get(position));
            startActivity(i);
        }
    }

    private class DishArrayAdapter extends ArrayAdapter<Dish>{
        private Context mContext;

        public DishArrayAdapter(Context context, int resource, List<Dish> objects) {
            super(context, resource, objects);
            mContext = context;
        }

        // TODO: not work
        public View getView_(int position, View convertView, ViewGroup parent) {
            if(convertView == null){

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.dish_item, parent,false);

            }
            TextView textView = (TextView)convertView.findViewById(R.id.dish_id);
            Dish dish = getItem(position);
            textView.setText(dish.getId()+"" +dish.getName());

            return convertView;
        }
    }
}
