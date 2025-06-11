package com.deyvitineo.tdee;

import com.deyvitineo.tdee.models.Calories;
import com.deyvitineo.tdee.models.Macros;
import com.deyvitineo.tdee.util.Calculators;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.number.IsCloseTo.closeTo;

public class CalculatorTests {
    private Macros mMacros;
    private Calories mCalories;
    private final int TEST_CALORIES = 2000;

    @Before
    public void setUp() {
        mMacros = Calculators.getMacros(TEST_CALORIES);
        mCalories = Calculators.getCalories(TEST_CALORIES);
    }

    @Test
    public void calculateMultiplier() {
        assertEquals(1.1, Calculators.calculateActivityLevelMultiplier("female", "sedentary"));
        assertEquals(1.275, Calculators.calculateActivityLevelMultiplier("female", "light"));
        assertEquals(1.35, Calculators.calculateActivityLevelMultiplier("female", "moderate"));
        assertEquals(1.525, Calculators.calculateActivityLevelMultiplier("female", "heavy"));
        assertEquals(1.6, Calculators.calculateActivityLevelMultiplier("female", "athlete"));

        assertEquals(1.2, Calculators.calculateActivityLevelMultiplier("male", "sedentary"));
        assertEquals(1.375, Calculators.calculateActivityLevelMultiplier("male", "light"));
        assertEquals(1.55, Calculators.calculateActivityLevelMultiplier("male", "moderate"));
        assertEquals(1.725, Calculators.calculateActivityLevelMultiplier("male", "heavy"));
        assertEquals(1.9, Calculators.calculateActivityLevelMultiplier("male", "athlete"));
    }

    @Test
    public void getMacros() {
        assertEquals(200, mMacros.getProtein());
        assertEquals(200, mMacros.getCarbohydrates());
        assertEquals(44, mMacros.getFat());
    }

    @Test
    public void getCalories(){
        assertEquals(800, mCalories.getProtein());
        assertEquals(800, mCalories.getCarbohydrates());
        assertEquals(400, mCalories.getFat());
    }
}
