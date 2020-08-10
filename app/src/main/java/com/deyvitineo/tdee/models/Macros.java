package com.deyvitineo.tdee.models;

public class Macros {
    private int protein;
    private int carbohydrates;
    private int fat;

    public Macros() {
    }

    public Macros(int protein, int carbohydrate, int fat) {
        this.protein = protein;
        this.carbohydrates = carbohydrate;
        this.fat = fat;
    }


    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(int carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }
}
