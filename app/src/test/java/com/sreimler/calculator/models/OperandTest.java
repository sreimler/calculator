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

package com.sreimler.calculator.models;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

/**
 * Unit tests for the {@link Operand}.
 */
public class OperandTest {

    private Operand mOperand;

    private static final String VALUE = "537";

    @Before
    public void setUp() throws Exception {
        mOperand = new Operand();
    }

    @Test
    public void initialValue_shouldBeZero() {
        assertThat("Initial value was zero", mOperand.getValue(), is(equalTo(Operand.EMPTY_VALUE)));
    }

    @Test
    public void appendValue_shouldAppendNumbers() {
        appendValues(VALUE);

        assertThat("Values were stored in the operand", mOperand.getValue(), is(equalTo(VALUE)));
    }

    @Test
    public void reset_shouldSetValueToZero() {
        appendValues(VALUE);
        mOperand.reset();

        assertThat("After reset, value was zero", mOperand.getValue(), is(equalTo(Operand.EMPTY_VALUE)));
    }

    private void appendValues(String value) {
        for(int i=0; i<value.length(); i++) {
            mOperand.appendValue(value.substring(i, i+1));
        }
    }
}