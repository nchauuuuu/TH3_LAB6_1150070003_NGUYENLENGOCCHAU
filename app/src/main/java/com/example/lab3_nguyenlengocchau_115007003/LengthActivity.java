package com.example.lab3_nguyenlengocchau_115007003;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class LengthActivity extends AppCompatActivity {

    private Spinner spnFromLength, spnToLength;
    private EditText edtLength;
    private Button btnConvertLength, btnBackToMain;   // ⭐ THÊM NÚT BACK
    private TextView txtLengthResult;

    // Danh sách đơn vị
    private String[] lengthUnits = {"mm", "cm", "dm", "m", "dam", "hm", "km"};

    // Ma trận tỉ lệ đổi độ dài
    private double[][] lengthMatrix = {
            // mm     cm      dm      m       dam     hm      km
            {1,      0.1,    0.01,   0.001,  0.0001, 0.00001, 0.000001},   // mm
            {10,     1,      0.1,    0.01,   0.001,  0.0001,  0.00001},    // cm
            {100,    10,     1,      0.1,    0.01,   0.001,   0.0001},     // dm
            {1000,   100,    10,     1,      0.1,    0.01,    0.001},      // m
            {10000,  1000,   100,    10,     1,      0.1,     0.01},       // dam
            {100000, 10000,  1000,   100,    10,     1,       0.1},        // hm
            {1000000,100000, 10000,  1000,   100,    10,      1},          // km
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_length);

        edtLength = findViewById(R.id.edtLength);
        spnFromLength = findViewById(R.id.spnFromLength);
        spnToLength = findViewById(R.id.spnToLength);
        btnConvertLength = findViewById(R.id.btnConvertLength);
        txtLengthResult = findViewById(R.id.txtLengthResult);

        // ⭐ LIÊN KẾT NÚT BACK
        btnBackToMain = findViewById(R.id.btnBackToMain);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lengthUnits);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnFromLength.setAdapter(adapter);
        spnToLength.setAdapter(adapter);

        btnConvertLength.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertLength();
            }
        });

        // ⭐ XỬ LÝ QUAY LẠI MÀN HÌNH CHÍNH
        btnBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();   // Đóng LengthActivity → quay lại MainActivity
            }
        });
    }

    private void convertLength() {
        String sValue = edtLength.getText().toString().trim();
        if (sValue.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập độ dài", Toast.LENGTH_SHORT).show();
            return;
        }

        double value = Double.parseDouble(sValue);
        int from = spnFromLength.getSelectedItemPosition();
        int to = spnToLength.getSelectedItemPosition();

        double result = value * lengthMatrix[from][to];

        txtLengthResult.setText(value + " " + lengthUnits[from] + " = " +
                result + " " + lengthUnits[to]);
    }
}
