/*
 * Copyright 2016 Sören Reimler
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sreimler.calculator.calculator;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.sreimler.calculator.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Espresso UI tests for the {@link CalculatorActivity}.
 */
@RunWith(AndroidJUnit4.class)
public class CalculatorActivityTest {

    @Rule
    public ActivityTestRule<CalculatorActivity> mActivityRule =
            new ActivityTestRule<>(CalculatorActivity.class);

    @Test
    public void calculatorButtons_shouldBeVisible() {
        // Numbers
        onView(withId(R.id.btn_0)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_1)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_2)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_3)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_4)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_5)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_6)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_7)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_8)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_9)).check(matches(isDisplayed()));

        // Operators
        onView(withId(R.id.btn_plus)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_minus)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_multiply)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_divide)).check(matches(isDisplayed()));

        // Actions
        onView(withId(R.id.btn_clear)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_equals)).check(matches(isDisplayed()));
    }

    @Test
    public void calculatorDisplays_shouldBeVisible() {
        onView(withId(R.id.txtv_display_operator)).check(matches(isDisplayed()));
        onView(withId(R.id.txtv_display_calculation)).check(matches(isDisplayed()));
    }

    @Test
    public void clicksOnNumberButtons_shouldUpdateTheCalculatorDisplay() {
        String input = "537";

        for (int i = 0; i < input.length(); i++) {
            onView(withText(input.substring(i, i + 1))).perform(click());
        }

        onView(withId(R.id.txtv_display_calculation)).check(matches(withText(input)));
    }

}