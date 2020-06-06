package com.deyvitineo.tdee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);
        Bundle extras = getIntent().getExtras();
        mTdee = extras.getInt(Constants.TDEE);
        mBmr = extras.getInt(Constants.BMR);

        initWidgets();

        final Animation alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);

        loseMacros.setText(Calculators.calculateMacros(mTdee - Constants.CALORIC_DEFICIT_SURPLUS));
        maintainMacros.setText(Calculators.calculateMacros(mTdee));
        gainMacros.setText(Calculators.calculateMacros(mTdee + Constants.CALORIC_DEFICIT_SURPLUS));
        loseCalories.setText(Calculators.calculateCalories(mTdee - Constants.CALORIC_DEFICIT_SURPLUS));
        maintainCalories.setText(Calculators.calculateCalories(mTdee));
        gainCalories.setText(Calculators.calculateCalories(mTdee + Constants.CALORIC_DEFICIT_SURPLUS));

        recalculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(alphaAnimation);
                Intent i = new Intent(ResultActivity.this, TdeeCalculator.class);
                startActivity(i);
                finish();
            }
        });

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


}
