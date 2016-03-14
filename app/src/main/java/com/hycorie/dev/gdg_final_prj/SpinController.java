package com.hycorie.dev.gdg_final_prj;


import android.widget.ImageView;

public class SpinController {
    Ingredient mIngredient;

    public SpinController(Ingredient ingredient) {

        this.mIngredient = ingredient;
    }

    public void update(ImageView img) {
        img.setImageURI(mIngredient.getImage());
    }

    public Ingredient getIngredient() {
        return mIngredient;
    }
}
