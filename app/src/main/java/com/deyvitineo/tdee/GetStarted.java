package com.deyvitineo.tdee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

public class GetStarted extends AppCompatActivity {
    private Button start;
    private CheckBox checkBox;

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sp = getSharedPreferences("doNotShow", 0);
        editor = sp.edit();

        final Animation alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);
        start = findViewById(R.id.get_started_button);
        checkBox = findViewById(R.id.do_not_show_again);


        start.setOnClickListener(view -> {
            view.startAnimation(alphaAnimation);
            if (checkBox.isChecked()) {
                editor.putBoolean("doNotShowAgain", true);
                editor.apply();
            }
            Intent intent = new Intent(GetStarted.this, TdeeCalculator.class);
            startActivity(intent);
            finish();
        });
    }
}
