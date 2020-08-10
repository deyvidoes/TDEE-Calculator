package com.deyvitineo.tdee.util;

import com.deyvitineo.tdee.models.Calories;
import com.deyvitineo.tdee.models.Macros;

public class Calculators {

    private static final double PROTEIN_PERCENTAGE = .40;
    private static final double CARBOHYDRATES_PERCENTAGE = .40;
    private static final double FAT_PERCENTAGE = .20;
    private static final int PROTEIN_CALORIES_PER_GRAM = 4;
    private static final int CARBOHYDRATES_CALORIES_PER_GRAM = 4;
    private static final int FAT_CALORIES_PER_GRAM = 9;

    public static int calculateTDEE(double bmr, String activity, String gender) {
        double multiplier = calculateActivityLevelMultiplier(gender, activity);
        return (int) Math.round(bmr * multiplier);
    }

    public static int calculateBMR(String height, int weight, int age, String gender, boolean isImperial) {
        double bmr;
        int heightInCM;
        double weightInKg;

        //Process the incoming data based on whether we are using imperial or metric
        if (isImperial) {
            //getting the height in centimeters
            heightInCM = Calculators.heightInCm(height);
            weightInKg = (weight * Constants.POUNDS_TO_KGS);
        } else {
            heightInCM = Integer.parseInt(height);
            weightInKg = weight;
        }

        if (gender.toLowerCase().equals("female")) {
            bmr = (heightInCM * 6.25) + (weightInKg * 9.99) - (age * 4.92) - 161;
        } else {
            bmr = (heightInCM * 6.25) + (weightInKg * 9.99) - (age * 4.92) + 5;
        }
        return (int) Math.round(bmr);
    }

    //calculates multiplier for the activity selected
    public static double calculateActivityLevelMultiplier(String gender, String activity) {

        double multiplier;
        if (gender.toLowerCase().equals("female")) {

            if (activity.toUpperCase().charAt(0) == 'S') {
                multiplier = Constants.FEMALE_SEDENTARY_MULTIPLIER;
            } else if (activity.toUpperCase().charAt(0) == 'L') {
                multiplier = Constants.FEMALE_LIGHT_MULTIPLIER;
            } else if (activity.toUpperCase().charAt(0) == 'M') {
                multiplier = Constants.FEMALE_MODERATE_MULTIPLIER;
            } else if (activity.toUpperCase().charAt(0) == 'H') {
                multiplier = Constants.FEMALE_HEAVY_MULTIPLIER;
            } else {
                multiplier = Constants.FEMALE_ATHLETE_MULTIPLIER;
            }
        } else {
            if (activity.toUpperCase().charAt(0) == 'S') {
                multiplier = Constants.MALE_SEDENTARY_MULTIPLIER;
            } else if (activity.toUpperCase().charAt(0) == 'L') {
                multiplier = Constants.MALE_LIGHT_MULTIPLIER;
            } else if (activity.toUpperCase().charAt(0) == 'M') {
                multiplier = Constants.MALE_MODERATE_MULTIPLIER;
            } else if (activity.toUpperCase().charAt(0) == 'H') {
                multiplier = Constants.MALE_HEAVY_MULTIPLIER;
            } else {
                multiplier = Constants.MALE_ATHLETE_MULTIPLIER;
            }
        }
        return multiplier;
    }

    public static int heightInCm(String height) {
        String[] heightArr = height.split(" ");
        double feet = Integer.parseInt(heightArr[0].replaceAll("[^0-9]", ""));
        double inches = Integer.parseInt(heightArr[1].replaceAll("[^0-9]", ""));

        return (int) Math.round((feet * Constants.FOOT_TO_CM) + (inches * Constants.INCH_TO_CM));
    }

    public static Macros getMacros(int calories) {
        Macros macros = new Macros();
        macros.setProtein(getProteinMacros(calories));
        macros.setCarbohydrates(getCarbohydratesMacros(calories));
        macros.setFat(getFatMacros(calories));

        return macros;
    }

    public static Calories getCalories(int calories) {
        Calories caloriesModel = new Calories();
        caloriesModel.setProtein((int) getProteinCalories(calories));
        caloriesModel.setCarbohydrates((int) getCarbohydratesCalories(calories));
        caloriesModel.setFat((int) getFatCalories(calories));

        return caloriesModel;
        //return protein + " calories\n" + carbohydrate + " calories\n" + fat + " calories\n" + totals + " calories";
    }

    private static int getProteinMacros(int calories) {
        return (int) Math.round(getProteinCalories(calories) / PROTEIN_CALORIES_PER_GRAM);
    }

    private static int getCarbohydratesMacros(int calories) {
        return (int) Math.round(getCarbohydratesCalories(calories) / CARBOHYDRATES_CALORIES_PER_GRAM);
    }

    private static int getFatMacros(int calories) {
        return (int) Math.round(getFatCalories(calories) / FAT_CALORIES_PER_GRAM);
    }

    private static double getProteinCalories(int calories) {
        return Math.round((calories * PROTEIN_PERCENTAGE));
    }

    private static double getCarbohydratesCalories(int calories) {
        return Math.round((calories * CARBOHYDRATES_PERCENTAGE));
    }

    private static double getFatCalories(int calories) {
        return Math.round((calories * FAT_PERCENTAGE));
    }
}
