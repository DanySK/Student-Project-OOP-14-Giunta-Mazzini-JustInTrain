package com.example.lisamazzini.train_app;

import android.content.Intent;
import android.support.v7.app.*;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;



public class MiaMain extends ActionBarActivity {

    private Button button;
    private EditText insertNumber;
    private Toast error;
    private Button preferiti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.insertNumber = (EditText)findViewById(R.id.insertDeparture);
       // this.button = (Button)findViewById(R.id.button);
       // this.preferiti = (Button)findViewById(R.id.butPref);
        // this.error = Toast.makeText(MainActivity.this, "Inserire un numero", Toast.LENGTH_SHORT);
        this.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!insertNumber.getText().toString().equals("")) {
                  //  Intent intent = new Intent(MainActivity.this, StationListActivity.class);
                   // intent.putExtra("trainNumber", insertNumber.getText().toString());
                   // startActivity(intent);
                } else {
                    error.show();
                }
            }
        });

        this.preferiti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MainActivity.this, TrainFavouriteListActivity.class);

                //startActivity(intent);
            }
        });




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
