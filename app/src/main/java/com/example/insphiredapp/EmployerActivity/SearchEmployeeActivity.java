package com.example.insphiredapp.EmployerActivity;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.insphiredapp.R;

public class SearchEmployeeActivity extends AppCompatActivity {
    ImageView backArrowSearch;
    LinearLayout linearStartDateS,linearEndDateS;
    TextView startDateTxt,endDateTxt;
    private int year,month,day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_employee);

        backArrowSearch = findViewById(R.id.backArrowSearch);
        linearStartDateS = findViewById(R.id.linearStartDateS);
        linearEndDateS = findViewById(R.id.linearEndDateS);
        startDateTxt = findViewById(R.id.startDateTxt);
        endDateTxt = findViewById(R.id.endDateTxt);

        backArrowSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        linearStartDateS.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                final Calendar calendar = Calendar.getInstance();

                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                Log.e("month","kkk.." +month);

                //calendar.add(Calendar.MONTH, 1);

                linearStartDateS.setOnClickListener(view -> {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(SearchEmployeeActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            month = month+ 1;

                            String formattedMonth = String.format("%02d", month);
                            String date = day + "-" + formattedMonth + "-" + year;

                            startDateTxt.setText(date);
                        }
                    }, year, month, day);
                    datePickerDialog.show();
                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                    datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
                    datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
                });

            }
        });

        linearEndDateS.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                final Calendar calendar = Calendar.getInstance();

                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                Log.e("month","kkk.." +month);

                //calendar.add(Calendar.MONTH, 1);

                linearEndDateS.setOnClickListener(view -> {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(SearchEmployeeActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            month = month+ 1;

                            String formattedMonth = String.format("%02d", month);
                            String date = day + "-" + formattedMonth + "-" + year;

                            endDateTxt.setText(date);
                        }
                    }, year, month, day);
                    datePickerDialog.show();
                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                    datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
                    datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
                });

            }
        });


    }
}