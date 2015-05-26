package com.testing.mineprecious.bukutabungan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class Settings extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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


    public void DeleteDB(View v){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Toast.makeText(Settings.this, "deleted database", Toast.LENGTH_SHORT).show();
                        dbHelper db = new dbHelper(Settings.this);
                        db.deleteTable();
                        db.close();
                        //Yes button clicked
                        Toast.makeText(Settings.this, "database sudah dihapus", Toast.LENGTH_SHORT).show();

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //Toast.makeText(Settings.this, "nope nope nope nope", Toast.LENGTH_SHORT).show();

                        //No button clicked
                        break;
                }
            }
        };

        Context con = v.getContext();

        AlertDialog.Builder builder = new AlertDialog.Builder(con);
        builder.setMessage("Yakin anda ingin Menghapus Database?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }


    public void ExportDB(View v){

    }

    public void ImportDB(View v){

    }



}
