package com.hycorie.dev.gdg_final_prj;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.hycorie.dev.gdg_final_prj.Util.JSONStore;

import java.util.ArrayList;
import java.util.Collection;
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
    private ProductSerializer<Product> mIngredientSerializer;
    private ProductSerializer<Product> mDishSerializer;
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

        JSONStore jsonStore = new JSONStore();
        jsonStore.setSerializer(mIngredientSerializer);
        jsonStore.createIngredients();

        jsonStore.setSerializer(mDishSerializer);
        jsonStore.createDishes();

    }

    private void addAdapter() {
        try {
            mIngredientList = mIngredientSerializer.load(Ingredient.class);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
        mGoodsAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, mIngredientList);
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

                DishArrayAdapter adapter = new DishArrayAdapter(MainActivity.this, R.layout.dish_item, filteredDishes);
                sv.setAdapter(adapter);

                int orientation = getResources().getConfiguration().orientation;

                if (orientation == Configuration.ORIENTATION_LANDSCAPE){
                    setListViewHeightBasedOnChildren(sv);
                }
                sv.setOnItemClickListener(new DishClickListener());
            }

            private List<Dish> getDishesDataByIngredients(Collection<String> items, List<Dish> dishes) {
                List<Dish> result = new ArrayList<>();

                for (Dish dish: dishes){
                    Collection ingredientsName = dish.getIngredientsName();

                    if (!items.retainAll(ingredientsName)){
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
                    Log.d("error serializer", String.valueOf(e));
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

    /**** Method for Setting the Height of the ListView dynamically.
     **** Hack to fix the issue of not showing all the items of the ListView
     **** when placed inside a ScrollView  ****/
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

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
            i.putExtra("DISH", (Parcelable)filteredDishes.get(position));
            startActivity(i);
        }
    }

    private class DishArrayAdapter extends ArrayAdapter<Dish>{
        private Context mContext;

        public DishArrayAdapter(Context context, int resource, List<Dish> objects) {
            super(context, resource, objects);
            mContext = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.dish_item, parent,false);

            }
            TextView textView = (TextView)convertView.findViewById(R.id.dish_name);
            TextView score = (TextView)convertView.findViewById(R.id.dish_score);

            Dish dish = getItem(position);
            textView.setText(dish.getName());
            score.setText(dish.getScore().toString());

            return convertView;
        }
    }
}
