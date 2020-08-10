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
        assertThat((double) mMacros.getProtein(), is(closeTo((TEST_CALORIES * .40) / 4, 50)));
        assertThat((double) mMacros.getCarbohydrates(), is(closeTo((TEST_CALORIES * .40) / 4, 50)));
        assertThat((double) mMacros.getFat(), is(closeTo((TEST_CALORIES * .20) / 9, 50)));
    }

    @Test
    public void getCalories(){
        assertThat((double) mCalories.getProtein(), is(closeTo(TEST_CALORIES * .40, 50)));
        assertThat((double) mCalories.getCarbohydrates(), is(closeTo(TEST_CALORIES * .40, 50)));
        assertThat((double) mCalories.getFat(), is(closeTo(TEST_CALORIES * .20, 50)));
    }
}
