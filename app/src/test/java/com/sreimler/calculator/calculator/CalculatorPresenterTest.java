package com.sreimler.calculator.calculator;

import com.sreimler.calculator.models.Operand;
import com.sreimler.calculator.models.Operator;
import com.sreimler.calculator.utils.Calculator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import static org.mockito.Mockito.never;
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

    private CalculatorPresenter mPresenter;

    private static final String SHORT_INPUT_A = "8";
    private static final String SHORT_INPUT_B = "5";
    private static final String LONG_INPUT = "38493";
    private static final Operator[] OPERATORS = {Operator.PLUS, Operator.MULTIPLY, Operator.DIVIDE, Operator.MINUS};

    @Before
    public void setupCalculatorPresenter() {
        // Inject the Mockito mocks
        MockitoAnnotations.initMocks(this);

        when(mCurrentOperand.getValue()).thenReturn(Operand.EMPTY_VALUE);
        when(mPreviousOperand.getValue()).thenReturn(Operand.EMPTY_VALUE);

        when(mCalculator.add(any(Operand.class), any(Operand.class))).thenReturn("0");
        when(mCalculator.subtract(any(Operand.class), any(Operand.class))).thenReturn("0");
        when(mCalculator.multiply(any(Operand.class), any(Operand.class))).thenReturn("0");
        when(mCalculator.divide(any(Operand.class), any(Operand.class))).thenReturn("0");

        mPresenter = new CalculatorPresenter(mCalculator, mView, mCurrentOperand, mPreviousOperand);
    }

    @Test
    public void afterInitialization_operatorShouldBeEmpty() {
        assertThat("Operator is empty after initialization",
                mPresenter.getOperator(), is(equalTo(Operator.EMPTY)));
    }

    @Test
    public void afterInitialization_presenterShouldSetDisplayToZero() {
        verify(mView).displayOperand(Operand.EMPTY_VALUE);
    }

    @Test
    public void userEventDelete_shouldResetCalculator() {
        mPresenter.appendValue(SHORT_INPUT_B);
        mPresenter.appendOperator(Operator.PLUS.toString());
        mPresenter.appendValue(SHORT_INPUT_B);

        // When the delete ("C") button is clicked
        mPresenter.clearCalculation();

        // All operands and operators should be removed from the calculation
        verify(mPreviousOperand, times(2)).reset();
        verify(mCurrentOperand, times(3)).reset();
        assertThat("Operator was reset", mPresenter.getOperator(), is(equalTo(Operator.EMPTY)));

        // Operand and operator display should be reset
        verify(mView, times(5)).displayOperand(anyString());
        verify(mView, times(5)).displayOperator(anyString());
    }

    @Test
    public void numbersEntered_shouldBeStoredAsOperand() {
        for (int i = 0; i < LONG_INPUT.length(); i++) {
            mPresenter.appendValue(LONG_INPUT.substring(i, i + 1));
        }

        verify(mCurrentOperand, times(LONG_INPUT.length())).appendValue(anyString());
        verify(mView, times(LONG_INPUT.length() + 1)).displayOperand(anyString());
    }

    @Test
    public void operatorsEntered_shouldBeStoredAndDisplayed() {
        for (Operator operator : OPERATORS) {
            mPresenter.appendOperator(operator.toString());
        }

        assertThat("Operator has been stored and updated",
                mPresenter.getOperator(), is(equalTo(OPERATORS[OPERATORS.length - 1])));
        verify(mView, times(OPERATORS.length + 1)).displayOperator(anyString());
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
        // Views should have been updated 5 times in total (1 initialization, 4 operations)
        verify(mView, times(5)).displayOperand(anyString());
        verify(mView, times(5)).displayOperator(anyString());
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

        verify(mCurrentOperand, times(3)).reset();
        verify(mPreviousOperand, times(3)).reset();

        assertThat("Previous operator has been reset",
                mPresenter.getOperator(), is(equalTo(Operator.EMPTY)));
    }

    @Test
    public void divisionByZero_shouldActivateErrorState() {
        performZeroDivision();

        verify(mCurrentOperand, times(1)).setValue(anyString());
        verify(mCurrentOperand, atLeastOnce()).setValue(Operand.ERROR_VALUE);
    }

    @Test
    public void inErrorState_newOperatorsShouldBeForbidden() {
        performZeroDivision();

        mPresenter.appendOperator(Operator.PLUS.toString());
        assertThat("New operator was not permitted",
                mPresenter.getOperator(), is(equalTo(Operator.EMPTY)));
    }

    @Test
    public void inErrorState_clearShouldStartNewCalculation() {
        performZeroDivision();

        mPresenter.clearCalculation();
        mPresenter.appendValue(SHORT_INPUT_B);
        mPresenter.appendOperator(Operator.PLUS.toString());

        assertThat("Clearing in error state started new calculation",
                mPresenter.getOperator(), is(equalTo(Operator.PLUS)));
    }

    @Test
    public void inErrorState_numbersShouldStartNewCalculation() {
        performZeroDivision();

        mPresenter.appendValue(SHORT_INPUT_B);
        mPresenter.appendOperator(Operator.PLUS.toString());

        assertThat("Number entered in error state started new calculation",
                mPresenter.getOperator(), is(equalTo(Operator.PLUS)));
    }

    @Test
    public void calculationErrorsByOperatorEntered_shouldNotDisplayOperator() {
        prepareZeroDivision();
        mPresenter.appendOperator(Operator.PLUS.toString());

        verify(mCurrentOperand, atLeastOnce()).setValue(Operand.ERROR_VALUE);
        assertThat("Operator has been reset",
                mPresenter.getOperator(), is(equalTo(Operator.EMPTY)));
    }

    @Test
    public void maxLengthOfInputs_shouldBeLimited() {
        String value = "";
        for (int i = 0; i < Operand.MAX_LENGTH + 1; i++) {
            mPresenter.appendValue(SHORT_INPUT_B);
            value += SHORT_INPUT_B;
            when(mCurrentOperand.getValue()).thenReturn(value);
        }

        verify(mCurrentOperand, times(Operand.MAX_LENGTH)).appendValue(SHORT_INPUT_B);
    }

    @Test
    public void shouldBeAbleToPerformAddition() {
        mPresenter.appendValue(SHORT_INPUT_A);
        mPresenter.appendOperator(Operator.PLUS.toString());
        mPresenter.appendValue(SHORT_INPUT_B);
        mPresenter.performCalculation();

        verify(mCalculator).add(any(Operand.class), any(Operand.class));
    }

    @Test
    public void shouldBeAbleToPerformSubtraction() {
        mPresenter.appendValue(SHORT_INPUT_A);
        mPresenter.appendOperator(Operator.MINUS.toString());
        mPresenter.appendValue(SHORT_INPUT_B);
        mPresenter.performCalculation();

        verify(mCalculator).subtract(any(Operand.class), any(Operand.class));
    }

    @Test
    public void shouldBeAbleToPerformMultiplication() {
        mPresenter.appendValue(SHORT_INPUT_A);
        mPresenter.appendOperator(Operator.MULTIPLY.toString());
        mPresenter.appendValue(SHORT_INPUT_B);
        mPresenter.performCalculation();

        verify(mCalculator).multiply(any(Operand.class), any(Operand.class));
    }

    @Test
    public void shouldBeAbleToPerformDivision() {
        mPresenter.appendValue(SHORT_INPUT_A);
        mPresenter.appendOperator(Operator.DIVIDE.toString());
        mPresenter.appendValue(SHORT_INPUT_B);
        // Mock of current operand must not return 0 to perform a division
        when(mCurrentOperand.getValue()).thenReturn(SHORT_INPUT_B);
        mPresenter.performCalculation();

        verify(mCalculator).divide(any(Operand.class), any(Operand.class));
    }

    @Test
    public void oversizeResult_shouldSwitchToErrorState() {
        // Create oversize result string
        String result = "";
        for (int i = 0; i <= Operand.MAX_LENGTH; i++) {
            result += SHORT_INPUT_A;
        }

        // Make the calculator return the desired result
        when(mCalculator.add(any(Operand.class), any(Operand.class))).thenReturn(result);

        // Perform calculation to trigger mCalculator.add()
        mPresenter.appendValue(SHORT_INPUT_B);
        mPresenter.appendOperator(Operator.PLUS.toString());
        mPresenter.appendValue(SHORT_INPUT_A);
        mPresenter.performCalculation();

        verify(mCurrentOperand, atLeastOnce()).setValue(Operand.ERROR_VALUE);
    }

    @Test
    public void calculationWithoutOperator_shouldReturnOperand() {
        mPresenter.appendValue(SHORT_INPUT_A);
        when(mCurrentOperand.getValue()).thenReturn(SHORT_INPUT_A);
        mPresenter.performCalculation();

        verify(mCurrentOperand).setValue(SHORT_INPUT_A);
    }

    private void prepareZeroDivision() {
        mPresenter.appendValue(SHORT_INPUT_B);
        mPresenter.appendOperator(Operator.DIVIDE.toString());
        mPresenter.appendValue(Operand.EMPTY_VALUE);
        when(mCurrentOperand.getValue()).thenReturn(Operand.EMPTY_VALUE);
        verify(mCurrentOperand, never()).setValue(anyString());
    }

    private void performZeroDivision() {
        prepareZeroDivision();
        mPresenter.performCalculation();
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
