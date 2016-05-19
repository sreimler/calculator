package com.sreimler.calculator.calculator;

import com.sreimler.calculator.models.Operand;
import com.sreimler.calculator.models.Operator;
import com.sreimler.calculator.utils.Calculator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the implementation of {@link CalculatorPresenter}.
 */
@RunWith(MockitoJUnitRunner.class)
public class CalculatorPresenterTest {

    @Mock
    private Calculator mCalculator;

    @Mock
    private CalculatorContract.View mView;

    @Mock
    private Operand mPreviousOperand;

    @Mock
    private Operand mCurrentOperand;

    @InjectMocks
    private CalculatorPresenter mPresenter;

    private static final String SHORT_INPUT_A = "8";
    private static final String SHORT_INPUT_B = "5";
    private static final String LONG_INPUT = "38493";
    private static final Operator[] OPERATORS = {Operator.PLUS, Operator.MULTIPLY, Operator.DIVIDE, Operator.MINUS};

    @Before
    public void setupCalculatorPresenter() {
        // Inject the Mockito mocks
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void afterInitialization_operatorShouldBeEmpty() {
        assertThat("Operator is empty after initialization",
                mPresenter.getOperator(), is(equalTo(Operator.EMPTY)));
    }

    @Test
    public void userEventDelete_shouldResetCalculator() {
        mPresenter.appendValue(SHORT_INPUT_B);
        mPresenter.appendOperator(Operator.PLUS.toString());
        mPresenter.appendValue(SHORT_INPUT_B);

        // When the delete ("C") button is clicked
        mPresenter.clearCalculation();

        // All operands and operators should be removed from the calculation
        verify(mPreviousOperand).reset();
        verify(mCurrentOperand, times(2)).reset();
        assertThat("Operator was reset", mPresenter.getOperator(), is(equalTo(Operator.EMPTY)));

        // Operand and operator display should be reset
        verify(mView, times(4)).displayOperand(anyString());
        verify(mView, times(4)).displayOperator(anyString());
    }

    @Test
    public void numbersEntered_shouldBeStoredAsOperand() {
        for (int i = 0; i < LONG_INPUT.length(); i++) {
            mPresenter.appendValue(LONG_INPUT.substring(i, i + 1));
        }

        verify(mCurrentOperand, times(LONG_INPUT.length())).appendValue(anyString());
        verify(mView, times(LONG_INPUT.length())).displayOperand(anyString());
    }

    @Test
    public void operatorsEntered_shouldBeStoredAndDisplayed() {
        for (Operator operator : OPERATORS) {
            mPresenter.appendOperator(operator.toString());
        }

        assertThat("Operator has been stored and updated",
                mPresenter.getOperator(), is(equalTo(OPERATORS[OPERATORS.length - 1])));
        verify(mView, times(OPERATORS.length)).displayOperator(anyString());
    }

    @Test
    public void numericalInputAfterOperator_shouldBeStoredAsNewOperand() {
        mPresenter.appendValue(SHORT_INPUT_A);
        when(mCurrentOperand.getValue()).thenReturn(SHORT_INPUT_A);
        mPresenter.appendOperator(Operator.PLUS.toString());
        mPresenter.appendValue(SHORT_INPUT_B);

        verify(mPreviousOperand).setValue(SHORT_INPUT_A);
        verify(mCurrentOperand).appendValue(SHORT_INPUT_B);
    }

    @Test
    public void secondDistinctOperator_shouldExecutePartialCalculation() {
        String result = calculateResult(SHORT_INPUT_A, SHORT_INPUT_B, Operator.PLUS);
        when(mCalculator.add(any(Operand.class), any(Operand.class))).thenReturn(result);

        mPresenter.appendValue(SHORT_INPUT_A);
        mPresenter.appendOperator(Operator.PLUS.toString());
        mPresenter.appendValue(SHORT_INPUT_B);
        when(mCurrentOperand.getValue()).thenReturn(result);
        mPresenter.appendOperator(Operator.DIVIDE.toString());

        verify(mCalculator).add(any(Operand.class), any(Operand.class));
        verify(mView, atLeastOnce()).displayOperand(result);
    }

    @Test
    public void operatorEnteredBeforeFirstOperand_shouldSetFirstOperandToZero() {
        when(mCurrentOperand.getValue()).thenReturn(Operand.EMPTY_VALUE);
        mPresenter.appendOperator(Operator.PLUS.toString());
        mPresenter.appendValue(SHORT_INPUT_A);

        verify(mPreviousOperand).setValue(Operand.EMPTY_VALUE);
    }

    @Test
    public void userEventCalculate_shouldExecuteCalculationAndUpdateDisplay() {
        mPresenter.appendValue(SHORT_INPUT_B);
        mPresenter.appendOperator(Operator.MULTIPLY.toString());
        mPresenter.appendValue(SHORT_INPUT_A);
        mPresenter.performCalculation();

        verify(mCalculator).multiply(Mockito.any(Operand.class), Mockito.any(Operand.class));
        // Views should have been updated 4 times in total
        verify(mView, times(4)).displayOperand(anyString());
        verify(mView, times(4)).displayOperator(anyString());
    }

    @Test
    public void resultCalculation_shouldResetOperator() {
        mPresenter.appendOperator(Operator.MULTIPLY.toString());
        mPresenter.performCalculation();

        assertThat("Operator has been reset",
                mPresenter.getOperator(), is(equalTo(Operator.EMPTY)));
    }

    @Test
    public void numberAfterCalculation_shouldStartNewCalculation() {
        mPresenter.appendValue(SHORT_INPUT_A);
        mPresenter.appendOperator(Operator.PLUS.toString());
        mPresenter.appendValue(SHORT_INPUT_B);
        mPresenter.performCalculation();
        mPresenter.appendValue(SHORT_INPUT_B);

        verify(mCurrentOperand, times(2)).reset();
        verify(mPreviousOperand, times(2)).reset();

        assertThat("Previous operator has been reset",
                mPresenter.getOperator(), is(equalTo(Operator.EMPTY)));
    }

    private String calculateResult(String firstOperand, String secondOperand, Operator operator) {
        switch (operator) {
            case PLUS:
                return Integer.toString(Integer.valueOf(firstOperand)
                        + Integer.valueOf(secondOperand));
            case MINUS:
                return Integer.toString(Integer.valueOf(firstOperand)
                        - Integer.valueOf(secondOperand));
            case MULTIPLY:
                return Integer.toString(Integer.valueOf(firstOperand)
                        * Integer.valueOf(secondOperand));
            case DIVIDE:
                return Integer.toString(Integer.valueOf(firstOperand)
                        / Integer.valueOf(secondOperand));
        }

        return "";
    }
}
