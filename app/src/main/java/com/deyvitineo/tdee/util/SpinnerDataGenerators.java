package com.deyvitineo.tdee.util;

import java.util.ArrayList;

public class SpinnerDataGenerators {

    public static ArrayList<String> getHeights(){
        ArrayList<String> arrayList = new ArrayList<>();
        String feet = "ft";
        String inches = "in";
        for(int i = 4; i <= 7; i++){
            for(int k = 0; k <= 11; k++){
                arrayList.add(i + feet + " " + k + inches);
            }
        }
        return arrayList;
    }

    public static ArrayList<String> getActivityLevels() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Sedentary (OfficeJob)");
        arrayList.add("Light (Exercise 1-2 days/week)");
        arrayList.add("Moderate (Exercise 3-5 days/week)");
        arrayList.add("Heavy (Exercise 6-7 days/week)");
        arrayList.add("Athlete (2x per day!)");
        return arrayList;
    }
}
