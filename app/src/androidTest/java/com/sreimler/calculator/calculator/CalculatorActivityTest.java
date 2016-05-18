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
        onView(withText("0")).check(matches(isDisplayed()));
        onView(withText("1")).check(matches(isDisplayed()));
        onView(withText("2")).check(matches(isDisplayed()));
        onView(withText("3")).check(matches(isDisplayed()));
        onView(withText("4")).check(matches(isDisplayed()));
        onView(withText("5")).check(matches(isDisplayed()));
        onView(withText("6")).check(matches(isDisplayed()));
        onView(withText("7")).check(matches(isDisplayed()));
        onView(withText("8")).check(matches(isDisplayed()));
        onView(withText("9")).check(matches(isDisplayed()));

        // Operators
        onView(withText("+")).check(matches(isDisplayed()));
        onView(withText("-")).check(matches(isDisplayed()));
        onView(withText("*")).check(matches(isDisplayed()));
        onView(withText("/")).check(matches(isDisplayed()));

        // Actions
        onView(withText("=")).check(matches(isDisplayed()));
        onView(withText("C")).check(matches(isDisplayed()));
    }

    @Test
    public void calculatorDisplays_shouldBeVisible() {
        onView(withId(R.id.txtv_display_operator)).check(matches(isDisplayed()));
        onView(withId(R.id.txtv_display_calculation)).check(matches(isDisplayed()));
    }
}