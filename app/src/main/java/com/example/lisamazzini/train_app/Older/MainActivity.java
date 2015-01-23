package com.example.lisamazzini.train_app.Older;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lisamazzini.train_app.R;


public class MainActivity extends ActionBarActivity {


    private Button btnSearchTrain;
    private Button btnSearchJourney;
    private Button btnTrainFavs;
    private Button btnJourneyFavs;

    private EditText insertNumber;
    private EditText insertDeparture;
    private EditText insertArrival;

    private Toast error;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.btnSearchTrain = (Button)findViewById(R.id.btnSearchTrain);
        this.btnSearchJourney = (Button)findViewById(R.id.btnSearchJourney);
        this.btnTrainFavs = (Button)findViewById(R.id.btnTrainFavs);
        this.btnJourneyFavs = (Button)findViewById(R.id.btnJourneyFavs);

        this.insertNumber = (EditText)findViewById(R.id.insertNumber);
        this.insertDeparture = (EditText)findViewById(R.id.insertDeparture);
        this.insertArrival = (EditText)findViewById(R.id.insertArrival);

        this.error = Toast.makeText(MainActivity.this, "Inserire campi", Toast.LENGTH_SHORT);

        btnSearchJourney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!insertDeparture.getText().toString().equals("") || !insertArrival.getText().toString().equals("")) {
//                    Intent intent = new Intent(MainActivity.this, JourneyListActivity.class);
                    Intent intent = new Intent(MainActivity.this, JourneyListwRobospiceActivity.class);
                    intent.putExtra("journeyDeparture", insertDeparture.getText().toString());
                    intent.putExtra("journeyArrival", insertArrival.getText().toString());
                    startActivity(intent);
                }else{
                    error.show();
                }

            }
        });

        btnJourneyFavs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, JourneyFavouriteListActivity.class);
                startActivity(i);
            }
        });

        this.btnSearchTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!insertNumber.getText().toString().equals("")) {
                    Intent intent = new Intent(MainActivity.this, StationListActivity.class);
                    intent.putExtra("trainNumber", insertNumber.getText().toString());
                    startActivity(intent);
                } else {
                    error.show();
                }
            }
        });

        this.btnTrainFavs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TrainFavouriteListActivity.class);
                startActivity(intent);
            }
        });


    }


}
