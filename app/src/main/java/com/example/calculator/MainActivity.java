package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Constants
    private static final String AC = "AC";
    private static final String EQUAL = "=";
    private static final String C = "C";
    private static final String ERR = "Err";

    private static final String LOG_TAG = "Calculator";

    // UI components
    TextView resultTv, solutionTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        resultTv = findViewById(R.id.result_tv);
        solutionTv = findViewById(R.id.solution_tv);

        // Buttons
        assignIdToButtonAndListen(R.id.button_c);
        assignIdToButtonAndListen(R.id.button_open_bracket);
        assignIdToButtonAndListen(R.id.button_close_bracket);
        assignIdToButtonAndListen(R.id.button_divide);
        assignIdToButtonAndListen(R.id.button_multiply);
        assignIdToButtonAndListen(R.id.button_plus);
        assignIdToButtonAndListen(R.id.button_minus);
        assignIdToButtonAndListen(R.id.button_equals);
        assignIdToButtonAndListen(R.id.button_0);
        assignIdToButtonAndListen(R.id.button_1);
        assignIdToButtonAndListen(R.id.button_2);
        assignIdToButtonAndListen(R.id.button_3);
        assignIdToButtonAndListen(R.id.button_4);
        assignIdToButtonAndListen(R.id.button_5);
        assignIdToButtonAndListen(R.id.button_6);
        assignIdToButtonAndListen(R.id.button_7);
        assignIdToButtonAndListen(R.id.button_8);
        assignIdToButtonAndListen(R.id.button_9);
        assignIdToButtonAndListen(R.id.button_ac);
        assignIdToButtonAndListen(R.id.button_dot);
    }

    private void assignIdToButtonAndListen(int buttonId) {
        MaterialButton btn = findViewById(buttonId);
        btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solutionTv.getText().toString();

        if (buttonText.equals(AC)) {
            solutionTv.setText("");
            resultTv.setText("0");
            return;
        } else if (buttonText.equals(EQUAL)) {
            solutionTv.setText(resultTv.getText());
            return;
        } else if (buttonText.equals(C)) {
            if (!dataToCalculate.isEmpty()) {
                dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
            }
        } else {
            dataToCalculate = dataToCalculate + buttonText;
        }

        solutionTv.setText(dataToCalculate);

        String finalResult = getResult(dataToCalculate);

        if (!ERR.equals(finalResult)) {
            resultTv.setText(finalResult);
        }

    }

    String getResult(String data) {
        if (data.isEmpty()) {
            return "0";
        }
        Context context = Context.enter();
        try {
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();
            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");
            }
            return finalResult;
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error in calculation", e);
            return ERR;
        } finally {
            Context.exit();
        }
    }

}