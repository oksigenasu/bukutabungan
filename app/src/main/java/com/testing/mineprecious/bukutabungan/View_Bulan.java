package com.testing.mineprecious.bukutabungan;

/**
 * Created by MinePrecious on 16/05/2015.
 */

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class View_Bulan extends ActionBarActivity{

    public List<Trans> rekapTransaksi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulan);
        Ambiltabel();
    }

    public List<Trans> Ambiltabel(){
        Trans transaksi = new Trans();
        dbHelper db = new dbHelper(this);
        rekapTransaksi = db.getTransAll();
        List<Trans> transaksiBulanan =  db.getTransBulanan(5);
        //transaksi = transaksiBulanan.get(0);
        //setText(transaksi);
        return transaksiBulanan;
    }

    public void setText(Trans transaksi){
        CharSequence kata = new StringBuilder().append("Transaksi ").append(transaksi.getJenis())
                .append(" pada tanggal ").append(transaksi.getTanggal())
                .append(" sebesar Rp. ").append(transaksi.getNominal())
                .append(" untuk keperluan ").append(transaksi.getPesan());

        TextView baru = (TextView) findViewById(R.id.tvNewtext);
        baru.setText(kata);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_transaksi, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_search:
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }


    }



}
