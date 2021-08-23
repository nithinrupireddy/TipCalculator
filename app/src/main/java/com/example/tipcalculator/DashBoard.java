package com.example.tipcalculator;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;


public class DashBoard extends AppCompatActivity {

    //UI Components
    private EditText billTotalEdit, tipAmountEdit, totalWithTipEdit, noOfPeopleEdit,
            totalPerPersonEdit, overageEdit;
    private Button goButton, clearBtn;
    private RadioGroup radioGroup;
    private RadioButton selectedRadioButton;

    //vars
    private double billTotalWithTax, tipAmount, totalWithTip, totalPerSon, overage,tipPercent,temp;
    private int noOfPeople;

    //format to print dollar
    DecimalFormat currencyFormat = new DecimalFormat("$###,###.##");
    //format to roundup decimal
    DecimalFormat roundedDecimal = new DecimalFormat("0.00");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializing_views(); //initializing views
        collectingInputs();  // collecting inputs here
        displayingValues();

        // clearing values
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioGroup.clearCheck();
                billTotalEdit.getText().clear();
                tipAmountEdit.getText().clear();
                totalWithTipEdit.getText().clear();
                noOfPeopleEdit.getText().clear();
                totalPerPersonEdit.getText().clear();
                overageEdit.getText().clear();

            }
        });

    }

    private void initializing_views() {

        billTotalEdit = findViewById(R.id.billTotalEdit);
        tipAmountEdit = findViewById(R.id.tipAmountEdit);
        totalWithTipEdit = findViewById(R.id.totalWithTipEdit);
        noOfPeopleEdit = findViewById(R.id.noOfPeopleEdit);
        goButton = findViewById(R.id.goButton);
        clearBtn = findViewById(R.id.clearBtn);
        radioGroup = findViewById(R.id.radioGroup);
        totalPerPersonEdit = findViewById(R.id.totalPerPersonEdit);
        overageEdit = findViewById(R.id.overageEdit);
    }

    private void collectingInputs() {

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                selectedRadioButton = (RadioButton) findViewById(selectedId);

                //based on radio check option ,displaying total tip
                if (R.id.radioButton1 == checkedId) {
                    if (billTotalEdit.getText().toString().length() == 0) {
                        Toast.makeText(getApplicationContext(), "please enter bill total with tax", Toast.LENGTH_SHORT).show();
                        radioGroup.clearCheck();
                    } else {
                        billTotalWithTax = Double.parseDouble(billTotalEdit.getText().toString());
                        tipPercent =  Double.valueOf(selectedRadioButton.getText().toString().replace("%",""));
                        calculateTotal(billTotalWithTax,tipPercent);
                    }
                } else if (R.id.radioButton2 == checkedId) {

                    if (billTotalEdit.getText().toString().length() == 0) {
                        Toast.makeText(getApplicationContext(), "please enter bill total with tax", Toast.LENGTH_SHORT).show();
                        radioGroup.clearCheck();
                    } else {
                        billTotalWithTax = Double.parseDouble(billTotalEdit.getText().toString());
                        tipPercent =  Double.valueOf(selectedRadioButton.getText().toString().replace("%",""));
                        calculateTotal(billTotalWithTax,tipPercent);
                    }
                } else if (R.id.radioButton3 == checkedId) {

                    if (billTotalEdit.getText().toString().length() == 0) {
                        Toast.makeText(getApplicationContext(), "please enter bill total with tax", Toast.LENGTH_SHORT).show();
                        radioGroup.clearCheck();
                    } else {
                        billTotalWithTax = Double.parseDouble(billTotalEdit.getText().toString());
                        tipPercent =  Double.valueOf(selectedRadioButton.getText().toString().replace("%",""));
                        calculateTotal(billTotalWithTax,tipPercent);
                    }
                } else if (R.id.radioButton4 == checkedId) {

                    if (billTotalEdit.getText().toString().length() == 0) {
                        Toast.makeText(getApplicationContext(), "please enter bill total with tax", Toast.LENGTH_SHORT).show();
                        radioGroup.clearCheck();
                    } else {
                        billTotalWithTax = Double.parseDouble(billTotalEdit.getText().toString());
                        tipPercent = Double.valueOf(selectedRadioButton.getText().toString().replace("%",""));
                        calculateTotal(billTotalWithTax,tipPercent);
                    }
                }
            }
        });

    }

    private void calculateTotal(double billTotalWithTax, double tipPercent) {

        tipAmount = ((tipPercent / 100) * billTotalWithTax);
        tipAmount = Double.parseDouble(roundedDecimal.format(tipAmount));
        totalWithTip = billTotalWithTax + tipAmount;

        tipAmountEdit.setText(currencyFormat.format(tipAmount));
        totalWithTipEdit.setText(currencyFormat.format(totalWithTip));
        overageEdit.getText().clear();
        totalPerPersonEdit.getText().clear();
    }


    private void displayingValues() {

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //handling exception using try/catch
                try {
                    //validating whether all inputs are given are not
                    if (validate()) {

                        //to hide keyboard
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(goButton.getApplicationWindowToken(), 0);
                        noOfPeople = Integer.parseInt(noOfPeopleEdit.getText().toString().trim());

                        totalPerSon = totalWithTip / noOfPeople;
                        totalPerSon = Double.parseDouble(roundedDecimal.format(totalPerSon));
                        totalPerPersonEdit.setText(currencyFormat.format(totalPerSon));
                        temp=Double.parseDouble(roundedDecimal.format((totalPerSon * noOfPeople)));
                        overage =  temp- totalWithTip;
                        String formatedOverage = String.valueOf(overage);
                        formatedOverage=formatedOverage.replace("-","");
                        Double overageEdited = Double.parseDouble(formatedOverage);
                        overageEdit.setText(currencyFormat.format(overageEdited));
                    } else {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
    }

    private boolean validate() {

        //validating all inputs
        if (billTotalEdit.getText().toString().length() == 0) {
            Toast.makeText(this, "please enter bill total with tax", Toast.LENGTH_SHORT).show();
            radioGroup.clearCheck();
            return false;
        } else if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "please check tip percent:", Toast.LENGTH_SHORT).show();
            return false;
        } else if (noOfPeopleEdit.getText().toString().length() == 0 ) {
            Toast.makeText(this, "please enter no of people :", Toast.LENGTH_SHORT).show();
            return false;
        }else if (Integer.parseInt(noOfPeopleEdit.getText().toString())<1) {
            Toast.makeText(this, "No. of peope should be greater than or equal to 1", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


}