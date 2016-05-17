package com.sreimler.calculator.calculator;

import com.sreimler.calculator.data.Calculator;
import com.sreimler.calculator.data.Operator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

    @Before
    public void setupCalculatorPresenter() {
        // Inject the Mockito mocks
        MockitoAnnotations.initMocks(this);

        // Create a CalculatorPresenter object
        mPresenter = new CalculatorPresenter(mCalculator, mView);
    }

    @Test
    public void clickOnDelete_resetsCalculator() {
        // When the delete ("C") button is clicked
        mPresenter.deleteCalculation();

        // Operand and operator display should be reset
        verify(mView).displayOperand("");
        verify(mView).displayOperator("");

        // All operands and operators should be removed from the calculation
        assertThat("First operand was reset", mPresenter.getFirstOperand(), is(equalTo("")));
        assertThat("Second operand was reset", mPresenter.getSecondOperand(), is(equalTo("")));
        assertThat("Operator was reset", mPresenter.getOperator(), is(equalTo(Operator.EMPTY)));
    }

    @Test
    public void numbersEntered_shouldBeStoredAsOperand() {
        String value = "7834";

        for (int i = 0; i < value.length(); i++) {
            mPresenter.appendValue(value.substring(i, i + 1));
        }

        assertThat("Entered value is stored as operand",
                mPresenter.getFirstOperand(), is(equalTo(value)));
    }

    @Test
    public void operatorsEntered_shouldBeStoredAndDisplayed() {
        Operator[] operators = {Operator.PLUS, Operator.MULTIPLY, Operator.DIVIDE, Operator.MINUS};

        for (Operator operator : operators) {
            mPresenter.setOperator(operator);
        }

        assertThat("Operator has been stored and updated",
                mPresenter.getOperator(), is(equalTo(operators[operators.length - 1])));
        verify(mView, times(operators.length)).displayOperator(anyString());
    }
}
