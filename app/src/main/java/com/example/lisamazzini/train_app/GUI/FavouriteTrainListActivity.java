package com.example.lisamazzini.train_app.GUI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.lisamazzini.train_app.*;
import com.example.lisamazzini.train_app.Controller.FavouriteTrainController;
import com.example.lisamazzini.train_app.Controller.FavouriteTrainListController;
import com.example.lisamazzini.train_app.Older.NotificationService;
import com.example.lisamazzini.train_app.Older.Train;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

/**
 * Created by lisamazzini on 22/01/15.
 */
public class FavouriteTrainListActivity extends Activity{

    private SpiceManager spiceManager = new SpiceManager(UncachedSpiceService.class);
    private FavouriteTrainController favController = new FavouriteTrainController(FavouriteTrainListActivity.this);
    private FavouriteTrainListController listController = new FavouriteTrainListController(favController.getMap());

    @Override
    protected void onStart() {
        spiceManager.start(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_list);
        // registerForContextMenu(this.list);


        while(listController.hasAnotherFavourite()){
            spiceManager.execute(listController.getRequest(), new TrainRequestListener());
        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_fav_train, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        //Train prova = (Train)list.getItemAtPosition(info.position);


        Intent intent = new Intent(FavouriteTrainListActivity.this, NotificationService.class);

        switch (item.getItemId()) {
            case R.id.delete:
                //NUOVO//

                //favController.removeFavourite(prova.getNumber);

                //VECCHIO//

                //fava.removeFavourite(prova.getNumber());
                Toast.makeText(FavouriteTrainListActivity.this, "Rimosso dai preferiti", Toast.LENGTH_LONG).show();
                return true;
            case R.id.pin:
                //intent.putExtra("number", prova.getNumber());
                startService(intent);
                return true;
            case R.id.unpin:
                stopService(intent);
            default:
                return super.onContextItemSelected(item);
        }
    }

    private class TrainRequestListener implements RequestListener<Train> {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(FavouriteTrainListActivity.this,
                    "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT)
                    .show();
        }

        @Override
        public void onRequestSuccess(Train train) {
           // favTrain.add(train);
           // adapter = new TrainAdapter(FavouriteTrainActivity.this, favTrain);
           // list.setAdapter(adapter);

        }
    }
}