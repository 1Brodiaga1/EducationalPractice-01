package com.example.sciencelab;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

public class ExperimentDetailActivity extends AppCompatActivity {

    private TextView titleTextView, descriptionTextView, startDateTextView, endDateTextView, statusTextView, researcherTextView;
    private Button buttonBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment_detail);

        titleTextView = findViewById(R.id.titleTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        startDateTextView = findViewById(R.id.startDateTextView);
        endDateTextView = findViewById(R.id.endDateTextView);
        statusTextView = findViewById(R.id.statusTextView);
        researcherTextView = findViewById(R.id.researcherTextView);
        buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(ExperimentDetailActivity.this, MainActivity.class);
                startActivity(back);
            }
        });

        // Извлекаем данные из Intent
        Intent intent = getIntent();
        String title = intent.getStringExtra("TITLE");
        String description = intent.getStringExtra("DESCRIPTION");
        String startDate = intent.getStringExtra("START_DATE");
        String endDate = intent.getStringExtra("END_DATE");
        String status = intent.getStringExtra("STATUS");
        String researcher = intent.getStringExtra("RESEARCHER");

        // Отображаем данные в TextView
        titleTextView.setText(title);
        descriptionTextView.setText(description);
        startDateTextView.setText(startDate);
        endDateTextView.setText(endDate);
        statusTextView.setText(status);
        researcherTextView.setText(researcher);
    }
}

