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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
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

        when(mPreviousOperand.getValue()).thenReturn(Operand.EMPTY_VALUE);
        when(mCurrentOperand.getValue()).thenReturn(Operand.EMPTY_VALUE);
    }

    @Test
    public void afterInitialization_operatorShouldBeEmpty() {
        assertThat("Operator is empty after initialization",
                mPresenter.getOperator(), is(equalTo(Operator.EMPTY)));
    }

    @Test
    public void userEventDelete_shouldResetCalculator() {
        mPresenter.appendValue(SHORT_INPUT_B);
        mPresenter.setOperator(Operator.PLUS);
        mPresenter.appendValue(SHORT_INPUT_B);

        // When the delete ("C") button is clicked
        mPresenter.deleteCalculation();

        // Operand and operator display should be reset
        verify(mView, atLeastOnce()).displayOperand(Operand.EMPTY_VALUE);
        verify(mView, atLeastOnce()).displayOperator(Operator.EMPTY.toString());

        // All operands and operators should be removed from the calculation
        verify(mPreviousOperand).reset();
        verify(mCurrentOperand, times(2)).reset();
        assertThat("Operator was reset", mPresenter.getOperator(), is(equalTo(Operator.EMPTY)));
    }

    @Test
    public void numbersEntered_shouldBeStoredAsOperand() {
        for (int i = 0; i < LONG_INPUT.length(); i++) {
            mPresenter.appendValue(LONG_INPUT.substring(i, i + 1));
        }

        verify(mCurrentOperand, times(LONG_INPUT.length())).appendValue(anyString());
    }

    @Test
    public void operatorsEntered_shouldBeStoredAndDisplayed() {
        for (Operator operator : OPERATORS) {
            mPresenter.setOperator(operator);
        }

        assertThat("Operator has been stored and updated",
                mPresenter.getOperator(), is(equalTo(OPERATORS[OPERATORS.length - 1])));
        verify(mView, times(OPERATORS.length)).displayOperator(anyString());
    }

    @Test
    public void numericalInputAfterOperator_shouldBeStoredAsNewOperand() {
        mPresenter.appendValue(SHORT_INPUT_A);
        when(mCurrentOperand.getValue()).thenReturn(SHORT_INPUT_A);
        mPresenter.setOperator(Operator.PLUS);
        mPresenter.appendValue(SHORT_INPUT_B);

        verify(mPreviousOperand).setValue(SHORT_INPUT_A);
        verify(mCurrentOperand).appendValue(SHORT_INPUT_B);
    }

    @Test
    public void secondDistinctOperator_shouldExecutePartialCalculation() {
        String result = calculateResult(SHORT_INPUT_A, SHORT_INPUT_B, Operator.PLUS);
        when(mCalculator.add(any(Operand.class), any(Operand.class))).thenReturn(result);

        mPresenter.appendValue(SHORT_INPUT_A);
        mPresenter.setOperator(Operator.PLUS);
        mPresenter.appendValue(SHORT_INPUT_B);
        when(mCurrentOperand.getValue()).thenReturn(result);
        mPresenter.setOperator(Operator.DIVIDE);

        verify(mCalculator).add(any(Operand.class), any(Operand.class));
        verify(mView, atLeastOnce()).displayOperand(result);
    }

    private String calculateResult(String firstOperand, String secondOperand, Operator operator) {
        switch (operator) {
            case PLUS:
                return Integer.toString(Integer.valueOf(SHORT_INPUT_A)
                        + Integer.valueOf(SHORT_INPUT_B));
            case MINUS:
                return Integer.toString(Integer.valueOf(SHORT_INPUT_A)
                        - Integer.valueOf(SHORT_INPUT_B));
            case MULTIPLY:
                return Integer.toString(Integer.valueOf(SHORT_INPUT_A)
                        * Integer.valueOf(SHORT_INPUT_B));
            case DIVIDE:
                return Integer.toString(Integer.valueOf(SHORT_INPUT_A)
                        / Integer.valueOf(SHORT_INPUT_B));
        }

        return "";
    }


    @Test
    public void operatorEnteredBeforeFirstOperand_shouldSetFirstOperandToZero() {
        mPresenter.setOperator(Operator.PLUS);
        mPresenter.appendValue(SHORT_INPUT_A);

        assertThat("Previous operand is zero",
                mPresenter.getPreviousOperand(), is(equalTo(Operand.EMPTY_VALUE)));
    }

    @Test
    public void userEventCalculate_shouldExecuteCalculation() {
        mPresenter.appendValue(SHORT_INPUT_B);
        mPresenter.setOperator(Operator.MULTIPLY);
        mPresenter.appendValue(SHORT_INPUT_A);
        mPresenter.performCalculation();

        verify(mCalculator).multiply(Mockito.any(Operand.class), Mockito.any(Operand.class));
    }
}
