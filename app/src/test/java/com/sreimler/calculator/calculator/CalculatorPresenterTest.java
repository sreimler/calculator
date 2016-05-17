package com.sreimler.calculator.calculator;

import com.sreimler.calculator.utils.Calculator;
import com.sreimler.calculator.models.Operator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.calls;
import static org.mockito.Mockito.inOrder;
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
    CalculatorContract.View mView;

    private CalculatorContract.Presenter mPresenter;

    private static final String SHORT_INPUT_A = "8";
    private static final String SHORT_INPUT_B = "5";
    private static final String ZERO_INPUT = "0";
    private static final String LONG_INPUT = "38493";
    private static final Operator[] OPERATORS = {Operator.PLUS, Operator.MULTIPLY, Operator.DIVIDE, Operator.MINUS};

    @Before
    public void setupCalculatorPresenter() {
        // Inject the Mockito mocks
        MockitoAnnotations.initMocks(this);

        // Create a CalculatorPresenter object
        mPresenter = new CalculatorPresenter(mCalculator, mView);
    }

    @Test
    public void afterInitialization_calculatorValuesShouldBeEmpty() {
        assertThat("First operand is empty after initialization",
                mPresenter.getPreviousOperand(), is(equalTo(ZERO_INPUT)));
        assertThat("Second operand is empty after initialization",
                mPresenter.getCurrentOperand(), is(equalTo(ZERO_INPUT)));
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
        verify(mView, times(1)).displayOperand(ZERO_INPUT);
        verify(mView, times(1)).displayOperator(Operator.EMPTY.toString());

        // All operands and operators should be removed from the calculation
        assertThat("First operand was reset", mPresenter.getPreviousOperand(), is(equalTo(ZERO_INPUT)));
        assertThat("Second operand was reset", mPresenter.getCurrentOperand(), is(equalTo(ZERO_INPUT)));
        assertThat("Operator was reset", mPresenter.getOperator(), is(equalTo(Operator.EMPTY)));
    }

    @Test
    public void numbersEntered_shouldBeStoredAsOperand() {
        for (int i = 0; i < LONG_INPUT.length(); i++) {
            mPresenter.appendValue(LONG_INPUT.substring(i, i + 1));
        }

        assertThat("Entered value is stored as operand",
                mPresenter.getCurrentOperand(), is(equalTo(LONG_INPUT)));
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
        mPresenter.setOperator(Operator.PLUS);
        mPresenter.appendValue(SHORT_INPUT_B);

        assertThat("First number has been stored as first operand",
                mPresenter.getPreviousOperand(), is(equalTo(SHORT_INPUT_A)));
        assertThat("Second number has been stored as second operand",
                mPresenter.getCurrentOperand(), is(equalTo(SHORT_INPUT_B)));
    }

    @Test
    public void secondDistinctOperator_shouldExecutePartialCalculation() {
        String result = calculateResult(SHORT_INPUT_A, SHORT_INPUT_B, Operator.PLUS);
        when(mCalculator.performCalculation(SHORT_INPUT_A, SHORT_INPUT_B, Operator.PLUS))
                .thenReturn(result);

        mPresenter.appendValue(SHORT_INPUT_A);
        mPresenter.setOperator(Operator.PLUS);
        mPresenter.appendValue(SHORT_INPUT_B);
        mPresenter.setOperator(Operator.DIVIDE);

        assertThat("Partial calculation has been executed",
                mPresenter.getCurrentOperand(), is(equalTo(result)));
        verify(mView).displayOperand(result);
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
                mPresenter.getPreviousOperand(), is(equalTo(Integer.toString(0))));
    }

    @Test
    public void userEventCalculate_shouldExecuteCalculation() {
        mPresenter.appendValue(SHORT_INPUT_B);
        mPresenter.setOperator(Operator.MULTIPLY);
        mPresenter.appendValue(SHORT_INPUT_A);
        mPresenter.performCalculation();

        verify(mCalculator).performCalculation(SHORT_INPUT_B, SHORT_INPUT_A, Operator.MULTIPLY);
    }
}
