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

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.AppCompatButton;

import com.sreimler.calculator.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Espresso UI tests for the {@link CalculatorActivity}.
 */
@RunWith(AndroidJUnit4.class)
public class CalculatorActivityTest {

    private static char[] OPERATORS = {'+', '-', '*', '/'};
    private static String RANDOM_INPUT = "533";

    @Rule
    public ActivityTestRule<CalculatorActivity> mActivityRule =
            new ActivityTestRule<>(CalculatorActivity.class);

    @Test
    public void calculatorButtons_shouldBeVisible() {
        // Numbers
        for (int i = 0; i <= 9; i++) {
            checkButtonWithText(Integer.toString(i));
        }

        // Operators
        for (char OPERATOR : OPERATORS) {
            checkButtonWithText(String.valueOf(OPERATOR));
        }

        // Actions
        checkButtonWithText("AC");
        checkButtonWithText("=");
    }

    @Test
    public void calculatorDisplays_shouldBeVisible() {
        onView(withId(R.id.txtv_display_operator)).check(matches(isDisplayed()));
        onView(withId(R.id.txtv_display_calculation)).check(matches(isDisplayed()));
    }

    @Test
    public void clicksOnNumberButtons_shouldUpdateCalculatorDisplay() {
        clickSomeNumbers();

        onView(withId(R.id.txtv_display_calculation)).check(matches(withText(RANDOM_INPUT)));
    }

    @Test
    public void clicksOnOperatorButtons_shouldUpdateOperatorDisplay() {
        for (char OPERATOR : OPERATORS) {
            String operator = String.valueOf(OPERATOR);
            clickButtonWithText(operator);

            onView(withId(R.id.txtv_display_operator)).check(matches(withText(operator)));
        }
    }

    @Test
    public void clickOnClearButton_shouldClearDisplays() {
        clickSomeNumbers();
        clickButtonWithText("+");

        // Clear input
        clickButtonWithText("AC");

        // Operator display should be empty, calculator display should be zero
        onView(withId(R.id.txtv_display_operator)).check(matches(withText("")));
        onView(withId(R.id.txtv_display_calculation)).check(matches(withText("0")));
    }

    @Test
    public void clickOnEqualsButton_shouldPerformCalculation() {
        clickSomeNumbers();
        clickButtonWithText("+");
        clickSomeNumbers();
        clickButtonWithText("=");
        String result = String.valueOf(Integer.valueOf(RANDOM_INPUT) + Integer.valueOf(RANDOM_INPUT));

        onView(withId(R.id.txtv_display_calculation)).check(matches(withText(result)));
    }

    private void clickSomeNumbers() {
        for (int i = 0; i < RANDOM_INPUT.length(); i++) {
            clickButtonWithText(RANDOM_INPUT.substring(i, i + 1));
        }
    }

    private void checkButtonWithText(String text) {
        getViewWithText(text).check(matches(isDisplayed()));
    }

    private void clickButtonWithText(String text) {
        getViewWithText(text).perform(click());
    }

    private ViewInteraction getViewWithText(String text) {
        return onView(allOf(withText(text), withClassName(equalTo(AppCompatButton.class.getName()))));
    }
}