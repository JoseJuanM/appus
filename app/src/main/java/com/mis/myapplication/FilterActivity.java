package com.mis.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FilterActivity extends AppCompatActivity {

    EditText minimumPrice;
    TextView  startDate,endDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        startDate = findViewById(R.id.editTextDate);
        endDate = findViewById(R.id.editTextEndDate);
        minimumPrice = findViewById(R.id.editTextNumberDecimal);

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCalendar(view);
            }
        });
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showCalendar(view);
            }
        });
    }

    private void showCalendar(View view) {

        TextView textView = (TextView) view;
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayofmonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayofmonth);
                textView.setText(new SimpleDateFormat("d MMM yyyy", Locale.ENGLISH).format(calendar.getTime()));
            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void aplicar(View view) {
        Intent intent = new Intent();
        intent.putExtra("filter_priceMin", minimumPrice.getText().toString());

        intent.putExtra("filter_startdate", startDate.getText().toString());
        intent.putExtra("filter_enddate", endDate.getText().toString());

        setResult(RESULT_OK, intent);

        finish();
    }
}