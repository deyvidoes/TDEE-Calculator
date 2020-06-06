package com.deyvitineo.tdee;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.deyvitineo.tdee.dialogs.ShowUpdateMessageDialog;
import com.deyvitineo.tdee.util.Calculators;
import com.deyvitineo.tdee.util.Constants;
import com.deyvitineo.tdee.util.SpinnerDataGenerators;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.ArrayList;

public class TdeeCalculator extends AppCompatActivity implements ShowUpdateMessageDialog.MessageShownListener {

    private static final String TAG = "TdeeCalculator";
    private RadioGroup mGenderGroup;
    private RadioButton mGenderButton;
    private EditText mAgeInput;
    private EditText mHeightInput;
    private Spinner mHeightSpinner;
    private EditText mWeightInput;
    private Spinner mActivityLevelSpinner;
    private Button mCalculate;
    private MaterialButtonToggleGroup mToggleButton;

    private boolean mIsImperial = true;

    private SharedPreferences sp;
    private SharedPreferences.Editor mSharedEditor;
    private boolean mIsShown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_tdee);

        sp = getSharedPreferences("doNotShow", MODE_PRIVATE);
        mIsShown = sp.getBoolean("updateMessageShown", false);
        mSharedEditor = sp.edit();

        if(mIsShown == false){
            showUpdateMessage();
        }

        initWidgets();
        setupListeners();
        handleFocusChange();
    }

    public void initWidgets() {
        mGenderGroup = findViewById(R.id.gender_radio_group);
        mAgeInput = findViewById(R.id.et_age);
        mHeightSpinner = findViewById(R.id.spinner_height);
        mHeightInput = findViewById(R.id.et_height);
        mWeightInput = findViewById(R.id.et_weight);
        mActivityLevelSpinner = findViewById(R.id.spinner_activity);
        mCalculate = findViewById(R.id.bt_calculate);
        mToggleButton = findViewById(R.id.toggleButton);


        addItemsToSpinner(SpinnerDataGenerators.getHeights(), mHeightSpinner);
        addItemsToSpinner(SpinnerDataGenerators.getActivityLevels(), mActivityLevelSpinner);
    }

    public void handleFocusChange(){
        mWeightInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });
        mAgeInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });
        mHeightInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });
    }

    public void setupListeners() {
        mToggleButton.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (checkedId == findViewById(R.id.btn_imperial).getId() && isChecked) {
                Log.d(TAG, "setupListeners: IMPERIAL:");
                mHeightSpinner.setVisibility(View.VISIBLE);
                mHeightInput.setVisibility(View.GONE);
                mWeightInput.setHint(getResources().getString(R.string.in_pounds));
                mWeightInput.setText("");
                mIsImperial = true;

            } else if(checkedId == findViewById(R.id.btn_metric).getId() && isChecked){
                Log.d(TAG, "setupListeners: METRIC");
                mHeightSpinner.setVisibility(View.GONE);
                mHeightInput.setVisibility(View.VISIBLE);
                mWeightInput.setHint(getResources().getString(R.string.in_kilos));
                mWeightInput.setText("");
                mIsImperial = false;

            }
        });


        mCalculate.setOnClickListener(v -> {
            int bmr;
            int tdee;
            String height;

            int radioSelected = mGenderGroup.getCheckedRadioButtonId();
            mGenderButton = findViewById(radioSelected);
            String gender = mGenderButton.getText().toString();
            String activity = mActivityLevelSpinner.getSelectedItem().toString();
            String weightString = mWeightInput.getText().toString();
            String ageString = mAgeInput.getText().toString();

            if(mIsImperial){
                    height = mHeightSpinner.getSelectedItem().toString();
            } else{
                if(!mHeightInput.getText().toString().isEmpty()){
                    height = mHeightInput.getText().toString();
                } else{
                    Toast.makeText(this, "Please fill out all the information", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            if (!weightString.isEmpty() && weightString != null && !ageString.isEmpty() && ageString != null) {
                int weight = Integer.parseInt(weightString);
                int age = Integer.parseInt(ageString);
                bmr = Calculators.calculateBMR(height, weight, age, gender, mIsImperial);
                tdee = Calculators.calculateTDEE(bmr, activity, gender);

                Intent i = new Intent(this, ResultActivity.class);
                i.putExtra(Constants.TDEE, tdee);
                i.putExtra(Constants.GENDER, gender);
                i.putExtra(Constants.AGE, age);
                i.putExtra(Constants.BMR, bmr);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(this, "Make sure the information is filled out correctly", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //adds items to a spinner
    public void addItemsToSpinner(ArrayList<String> array, Spinner spinner) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    //hides keyboard as soon as the focus changes
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void showUpdateMessage() {
        ShowUpdateMessageDialog showUpdateMessageDialog = new ShowUpdateMessageDialog();
        showUpdateMessageDialog.show(getSupportFragmentManager(), "Update Dialog");
    }

    @Override
    public void messageShown(boolean isShown) {
        if(isShown){
            mSharedEditor.putBoolean("updateMessageShown", true);
            mSharedEditor.apply();
        }
    }
}