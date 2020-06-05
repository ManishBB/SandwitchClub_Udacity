package com.example.sandwitchclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sandwitchclub.model.Sandwich;
import com.example.sandwitchclub.utils.JsonUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private ImageView mFoodImageView;
    private TextView mKnownAsTextView;
    private TextView mIngredientsTextView;
    private TextView mPlaceOfOriginTextView;
    private TextView mDescriptionTextView;
    Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mFoodImageView = findViewById(R.id.image_iv);
        mKnownAsTextView = findViewById(R.id.also_known_tv);
        mIngredientsTextView = findViewById(R.id.ingredients_tv);
        mPlaceOfOriginTextView = findViewById(R.id.origin_tv);
        mDescriptionTextView = findViewById(R.id.description_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }
    private void populateUI() {
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.drawable.holder)
                .error(R.drawable.error)
                .into(mFoodImageView);

        setTitle(sandwich.getMainName());

        List knownAsFoodData = sandwich.getAlsoKnownAs();
        for (int i = 0; i < knownAsFoodData.size(); i++) {
            mKnownAsTextView.append(knownAsFoodData.get(i) + " ");
        }
        List ingredientsFoodData = sandwich.getIngredients();
        for (int i = 0; i < ingredientsFoodData.size(); i++) {
            mIngredientsTextView.append(ingredientsFoodData.get(i) + " ");
        }

        mPlaceOfOriginTextView.setText(sandwich.getPlaceOfOrigin());
        mDescriptionTextView.setText(sandwich.getDescription());
    }
}
