package com.deyvitineo.tdee.util;

public class Calculators {

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
    private static double calculateActivityLevelMultiplier(String gender, String activity) {

        double multiplier;
        if (gender.toLowerCase().equals("female")) {

            if (activity.charAt(0) == 'S') {
                multiplier = 1.1;
            } else if (activity.charAt(0) == 'L') {
                multiplier = 1.275;
            } else if (activity.charAt(0) == 'M') {
                multiplier = 1.35;
            } else if (activity.charAt(0) == 'H') {
                multiplier = 1.525;
            } else {
                multiplier = 1.6;
            }
        } else {
            if (activity.charAt(0) == 'S') {
                multiplier = 1.2;
            } else if (activity.charAt(0) == 'L') {
                multiplier = 1.375;
            } else if (activity.charAt(0) == 'M') {
                multiplier = 1.55;
            } else if (activity.charAt(0) == 'H') {
                multiplier = 1.725;
            } else {
                multiplier = 1.9;
            }
        }
        return multiplier;
    }

    public static int heightInCm(String height){
        String[] heightArr = height.split(" ");
        double feet = Integer.parseInt(heightArr[0].replaceAll("[^0-9]", ""));
        double inches = Integer.parseInt(heightArr[1].replaceAll("[^0-9]", ""));

        return (int) Math.round((feet * Constants.FOOT_TO_CM) + (inches * Constants.INCH_TO_CM));
    }

    public static String calculateMacros(int calories){
        int proteins = (int) Math.round((calories * .40) / 4);
        int carbs = (int) Math.round((calories * .40) / 4);
        int fats = (int) Math.round((calories * .20) / 9);
        int totals = proteins + carbs + fats;

        return proteins + "g\n" + carbs + "g\n" + fats + "g\n" + totals + "g";
    }

    public static String calculateCalories(int calories){
        int proteins = (int) Math.round((calories * .40));
        int carbs = (int) Math.round((calories * .40));
        int fats = (int) Math.round((calories * .20));
        int totals = proteins + carbs + fats;

        return proteins + " calories\n" + carbs + " calories\n" + fats + " calories\n" + totals + " calories";
    }
}
