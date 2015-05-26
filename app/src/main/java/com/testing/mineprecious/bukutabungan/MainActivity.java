package com.testing.mineprecious.bukutabungan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.database.sqlite.*;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //buka database

    }
        //Button btNew = (Button)findViewById(R.id.button);
        //Button btView = (Button)findViewById(R.id.button2);

        //btNew.setOnClickListener(new Button.OnClickListener() {
        //    public void onClick(View v){
        //        //do new view
        //        Intent intent = new Intent(this,Transaksi.class);
        //    }
        //});

        //btView.setOnClickListener(new Button.OnClickListener(){
        //    public void onClick(View v){
        //        //do open view
        //    }
        //});

    public void openNew(View v) {
        //do somethin here
        Intent intent = new Intent(this,Transaksi.class);
        startActivity(intent);


    }

    public void openRekap(View v){
        //do sometin here
        Intent intent = new Intent(this,Rekap.class);
        startActivity(intent);

    }

    public void openSetting(View v){
        Intent intent = new Intent(this,Settings.class);
        startActivity(intent);
    }

    public void deleteRekap(View v){
//        dbHelper db = new dbHelper(this);
//        db.deleteTable();
//        Log.d("Main/deleteRekap","database deleted");
//        Toast.makeText(this, "deleted database", Toast.LENGTH_SHORT).show();

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Toast.makeText(MainActivity.this, "deleted database", Toast.LENGTH_SHORT).show();

                        //Yes button clicked
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        Toast.makeText(MainActivity.this, "nope nope nope nope", Toast.LENGTH_SHORT).show();

                        //No button clicked
                        break;
                }
            }
        };

        Context con = v.getContext();

        AlertDialog.Builder builder = new AlertDialog.Builder(con);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

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
