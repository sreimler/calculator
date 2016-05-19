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

package com.sreimler.calculator.utils;

import com.sreimler.calculator.models.Operand;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the {@link Calculator}.
 */
@RunWith(MockitoJUnitRunner.class)
public class CalculatorTest {

    private Calculator mCalculator;

    @Mock
    private Operand mFirstOperand;

    @Mock
    private Operand mSecondOperand;

    private static final int VALUE_A = 53;
    private static final int VALUE_B = 4;

    @Before
    public void setUp() {
        // Inject the Mockito mocks
        MockitoAnnotations.initMocks(this);

        when(mFirstOperand.getValue()).thenReturn(Integer.toString(VALUE_A));
        when(mSecondOperand.getValue()).thenReturn(Integer.toString(VALUE_B));

        mCalculator = new Calculator();
    }

    @Test
    public void testAddition() {
        String expectedResult = Integer.toString(VALUE_A + VALUE_B);

        assertThat("Addition was executed correctly",
                mCalculator.add(mFirstOperand, mSecondOperand), is(equalTo(expectedResult)));
    }

    @Test
    public void testSubtraction() {
        String expectedResult = Integer.toString(VALUE_A - VALUE_B);

        assertThat("Addition was executed correctly",
                mCalculator.subtract(mFirstOperand, mSecondOperand), is(equalTo(expectedResult)));
    }

    @Test
    public void testMultiplication() {
        String expectedResult = Integer.toString(VALUE_A * VALUE_B);

        assertThat("Addition was executed correctly",
                mCalculator.multiply(mFirstOperand, mSecondOperand), is(equalTo(expectedResult)));
    }

    @Test
    public void testDivision() {
        String expectedResult = Integer.toString(VALUE_A / VALUE_B);

        assertThat("Addition was executed correctly",
                mCalculator.divide(mFirstOperand, mSecondOperand), is(equalTo(expectedResult)));
    }
}