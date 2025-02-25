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

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.sreimler.calculator.databinding.ActivityMainBinding;
import com.sreimler.calculator.models.Operand;
import com.sreimler.calculator.utils.Calculator;

public class CalculatorActivity extends AppCompatActivity implements CalculatorContract.View {

    private CalculatorContract.Presenter mPresenter;
    // Use the binding generated from activity_main.xml
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout using View Binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize the presenter
        mPresenter = new CalculatorPresenter(new Calculator(), this, new Operand(), new Operand());

        // Attach click listeners to number buttons
        View.OnClickListener numberClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button button = (Button) view;
                mPresenter.appendValue(button.getText().toString());
            }
        };
        binding.btn0.setOnClickListener(numberClickListener);
        binding.btn1.setOnClickListener(numberClickListener);
        binding.btn2.setOnClickListener(numberClickListener);
        binding.btn3.setOnClickListener(numberClickListener);
        binding.btn4.setOnClickListener(numberClickListener);
        binding.btn5.setOnClickListener(numberClickListener);
        binding.btn6.setOnClickListener(numberClickListener);
        binding.btn7.setOnClickListener(numberClickListener);
        binding.btn8.setOnClickListener(numberClickListener);
        binding.btn9.setOnClickListener(numberClickListener);

        // Attach click listeners to operator buttons
        View.OnClickListener operatorClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button button = (Button) view;
                mPresenter.appendOperator(button.getText().toString());
            }
        };
        binding.btnPlus.setOnClickListener(operatorClickListener);
        binding.btnMinus.setOnClickListener(operatorClickListener);
        binding.btnMultiply.setOnClickListener(operatorClickListener);
        binding.btnDivide.setOnClickListener(operatorClickListener);

        // Attach click listeners for clear and equals buttons
        binding.btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.clearCalculation();
            }
        });
        binding.btnEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.performCalculation();
            }
        });
    }

    @Override
    public void displayOperand(String calculation) {
        // Update the calculation display using the binding
        binding.txtvDisplayCalculation.setText(calculation);
    }

    @Override
    public void displayOperator(String operator) {
        // Update the operator display using the binding
        binding.txtvDisplayOperator.setText(operator);
    }
}
