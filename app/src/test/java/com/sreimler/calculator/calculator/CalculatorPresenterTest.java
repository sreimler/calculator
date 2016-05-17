package com.sreimler.calculator.calculator;

import com.sreimler.calculator.data.Calculator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit tests for the implementation of {@link CalculatorPresenter}.
 */
@RunWith(MockitoJUnitRunner.class)
public class CalculatorPresenterTest {

    @Mock
    private Calculator mCalculator;

    @Mock
    CalculatorContract.View mCalculatorView;

    private CalculatorContract.Presenter mCalculatorPresenter;

    @Before
    public void setupCalculatorPresenter() {
        // Inject the Mockito mocks
        MockitoAnnotations.initMocks(this);

        // Create a CalculatorPresenter object
        mCalculatorPresenter = new CalculatorPresenter(mCalculator, mCalculatorView);
    }

    @Test
    public void clickOnDelete_resetsCalculator() {
        // When the delete ("C") button is clicked
        mCalculatorPresenter.deleteCalculation();

        // The output text should be reset
        Mockito.verify(mCalculatorView).setCalculationText("");

        // TODO: the calculation data model should be reset
    }
}
