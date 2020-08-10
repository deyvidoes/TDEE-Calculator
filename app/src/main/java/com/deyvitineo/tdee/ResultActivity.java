package com.deyvitineo.tdee;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.deyvitineo.tdee.models.Calories;
import com.deyvitineo.tdee.models.Macros;
import com.deyvitineo.tdee.util.Calculators;
import com.deyvitineo.tdee.util.Constants;

public class ResultActivity extends AppCompatActivity {

    private TextView resultsHeaderText;
    private TextView loseMacros;
    private TextView maintainMacros;
    private TextView gainMacros;
    private TextView loseCalories;
    private TextView maintainCalories;
    private TextView gainCalories;
    private Button recalculateButton;

    private int mTdee;
    private int mBmr;

    private Macros mLoseWeightMacros;
    private Macros mMaintainWeightMacros;
    private Macros mGainWeightMacros;

    private Calories mLoseWeightCalories;
    private Calories mMaintainWeightCalories;
    private Calories mGainWeightCalories;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);
        Bundle extras = getIntent().getExtras();
        mTdee = extras.getInt(Constants.TDEE);
        mBmr = extras.getInt(Constants.BMR);

        initWidgets();
        initMacros();
        initCalories();

        final Animation alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);

        displayMacrosText();
        displayCaloriesText();

        recalculateButton.setOnClickListener(v -> {
            v.startAnimation(alphaAnimation);
            Intent i = new Intent(ResultActivity.this, TdeeCalculator.class);
            startActivity(i);
            finish();
        });

    }

    private void initMacros() {
        mLoseWeightMacros = Calculators.getMacros(mTdee - Constants.CALORIC_DEFICIT_SURPLUS);
        mMaintainWeightMacros = Calculators.getMacros(mTdee);
        mGainWeightMacros = Calculators.getMacros(mTdee + Constants.CALORIC_DEFICIT_SURPLUS);
    }

    private void initCalories() {
        mLoseWeightCalories = Calculators.getCalories(mTdee - Constants.CALORIC_DEFICIT_SURPLUS);
        mMaintainWeightCalories = Calculators.getCalories(mTdee);
        mGainWeightCalories = Calculators.getCalories(mTdee + Constants.CALORIC_DEFICIT_SURPLUS);
    }

    public void initWidgets() {
        resultsHeaderText = findViewById(R.id.results_header_text);
        String textToShow = "Based on the information you entered, your TDEE is: " + mTdee + ". Your BMR is: " + mBmr + ".";
        resultsHeaderText.setText(textToShow);

        loseMacros = findViewById(R.id.lose_macros);
        maintainMacros = findViewById(R.id.maintainMacros);
        gainMacros = findViewById(R.id.gainMacros);
        loseCalories = findViewById(R.id.lose_calories);
        maintainCalories = findViewById(R.id.maintain_calories);
        gainCalories = findViewById(R.id.gain_calories);
        recalculateButton = findViewById(R.id.recalculate_button);
    }

    private void displayMacrosText() {
        loseMacros.setText(getMacrosString(mLoseWeightMacros));
        maintainMacros.setText(getMacrosString(mMaintainWeightMacros));
        gainMacros.setText(getMacrosString(mGainWeightMacros));
    }

    private void displayCaloriesText() {
        loseCalories.setText(getCaloriesString(mLoseWeightCalories));
        maintainCalories.setText(getCaloriesString(mMaintainWeightCalories));
        gainCalories.setText(getCaloriesString(mGainWeightCalories));
    }

    private String getMacrosString(Macros macros) {
        int protein = macros.getProtein();
        int carbohydrates = macros.getCarbohydrates();
        int fat = macros.getFat();
        int totals = protein + carbohydrates + fat;
        return protein + "g\n" + carbohydrates + "g\n" + fat + "g\n" + totals + "g";
    }

    private String getCaloriesString(Calories calories) {
        int protein = calories.getProtein();
        int carbohydrates = calories.getCarbohydrates();
        int fat = calories.getFat();
        int totals = protein + carbohydrates + fat;

        return protein + " calories\n" + carbohydrates + " calories\n" + fat + " calories\n" + totals + " calories";
    }
}
