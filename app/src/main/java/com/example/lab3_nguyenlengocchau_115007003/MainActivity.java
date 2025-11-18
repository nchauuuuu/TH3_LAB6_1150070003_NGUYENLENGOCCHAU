package com.example.lab3_nguyenlengocchau_115007003;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private String[] currencyUnits;

    private double[][] rateMatrix = new double[10][10];

    private EditText edtAmount;
    private Spinner spnFrom, spnTo;
    private Button btnConvert, btnGoToLength;
    private TextView txtResult, txtAuthor, txtComputer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtAmount = findViewById(R.id.edtAmount);
        spnFrom = findViewById(R.id.spnFrom);
        spnTo = findViewById(R.id.spnTo);
        btnConvert = findViewById(R.id.btnConvert);
        txtResult = findViewById(R.id.txtResult);
        txtAuthor = findViewById(R.id.txtAuthor);
        txtComputer = findViewById(R.id.txtComputer);

        btnGoToLength = findViewById(R.id.btnGoToLength);

        currencyUnits = new String[]{
                getString(R.string.usd_unit), // 0
                getString(R.string.eur_unit), // 1
                getString(R.string.gbp_unit), // 2
                getString(R.string.inr_unit), // 3
                getString(R.string.aud_unit), // 4
                getString(R.string.cad_unit), // 5
                getString(R.string.zar_unit), // 6
                getString(R.string.nzd_unit), // 7
                getString(R.string.jpy_unit), // 8
                getString(R.string.vnd_unit)  // 9
        };

        initRateMatrix();

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currencyUnits);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnFrom.setAdapter(adapter);
        spnTo.setAdapter(adapter);

        spnFrom.setSelection(0); // USD
        spnTo.setSelection(9);   // VND

        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertMoney();
            }
        });

        btnGoToLength.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LengthActivity.class);
                startActivity(intent);
            }
        });
    }
    private void initRateMatrix() {

        rateMatrix = new double[][]{
                // USD,      EUR,      GBP,      INR,      AUD,      CAD,      ZAR,      NZD,      JPY,      VND
                {1,         0.80518,  0.6407,   63.3318,  1.21828,  1.16236,  11.7129,  1.2931,   118.337,  21385.7},   // USD
                {1.24172,   1,        0.79757,  78.6084,  1.51266,  1.44314,  14.5731,  1.60576,  146.927,  25661.8},   // EUR
                {1.56044,   1.25667,  1,        98.7848,  1.90091,  1.81355,  18.2683,  2.01791,  184.638,  33374.9},   // GBP
                {0.0158,    0.01272,  0.01012,  1,        0.01924,  0.01836,  0.18493,  0.020324, 1.8691,   337.811},   // INR
                {0.82114,   0.66119,  0.55262,  52.086,   1,        0.95416,  9.61148,  1.06158,  97.112,   17567.9},   // AUD
                {0.86059,   0.69266,  0.57148,  54.5885,  1.04804,  1,        10.0732,  1.11258,  101.777,  18401.7},   // CAD
                {0.08541,   0.06877,  0.05473,  5.40852,  0.10398,  0.09924,  1,        0.11037,  10.0996,  1825.87},   // ZAR
                {0.77402,   0.62413,  0.49627,  49.0031,  0.94215,  0.89953,  9.06754,  1,        91.5139,  16552.1},   // NZD
                {0.00846,   0.00681,  0.00542,  0.53547,  0.0103,   0.00983,  0.09098,  0.01093,  1,        180.837},   // JPY
                {0.00005,   0.00004,  0.00003,  0.00296,  0.00006,  0.00005,  0.00055,  0.00006,  0.00553,  1}          // VND
        };
    }

    private void convertMoney() {
        String sAmount = edtAmount.getText().toString().trim();
        if (sAmount.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập số tiền", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(sAmount);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Số tiền không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        int fromIndex = spnFrom.getSelectedItemPosition();
        int toIndex = spnTo.getSelectedItemPosition();

        double rate = rateMatrix[fromIndex][toIndex];
        double result = amount * rate;

        String fromUnit = currencyUnits[fromIndex];
        String toUnit = currencyUnits[toIndex];

        txtResult.setText(amount + " " + fromUnit + " = " + result + " " + toUnit);
    }
}
