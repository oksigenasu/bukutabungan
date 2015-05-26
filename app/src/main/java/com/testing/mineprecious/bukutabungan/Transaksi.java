package com.testing.mineprecious.bukutabungan;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;


public class Transaksi extends ActionBarActivity {

    //variable untuk update
    String status = "";
    int value =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();

        setContentView(R.layout.activity_transaksi);

        if (extras != null) {
            value = extras.getInt("trans_id");
            Log.d("Transaksi.java"," extras= "+value);

            dbHelper db = new dbHelper(this);

            Trans data = db.getTrans(value);
            Log.d("Transaksi.java"," database retrieved "+data.getTanggal());
            db.close();

            Update(data);
            Log.d("Transaksi.java"," data updated");

            status = "update";
        }


    }


    public void ClickSave(View v){
        Save();
    }

    //executed when button simpan clicked
    public void Save(){
        DatePicker tanggal = (DatePicker) findViewById(R.id.datePicker);
        EditText nominal = (EditText) findViewById(R.id.tbNominal);
        EditText notes = (EditText) findViewById(R.id.tbNotes);
        RadioGroup transaksi = (RadioGroup) findViewById(R.id.rgTransaksi);



        //ambil tanggal di kalender
        //tambahkan 0 didepan utk tanggal dibawah 10
        int tgl = tanggal.getDayOfMonth();
        String tang ="";
        if (tgl<10){
            tang = "0"+String.valueOf(tgl);
        }else{
            tang = String.valueOf(tgl);
        }


        int bul = tanggal.getMonth();

        //tambahkan 0 didepan utk bulan dibawah 10
        String bula ="";
        if (bul+1<10){
            bula = "0"+String.valueOf(bul+1);
        }else{
            bula = String.valueOf(bul+1);
        }
        int tah = tanggal.getYear();

        CharSequence kalender = new StringBuilder()
                // Month is 0 based, just add 1
                .append(tah).append("-").append(bula).append("-")
                .append(tang);

        CharSequence hariini = new StringBuilder().append(tang).append("-").append(bula).append("-")
                .append(tah);


        //ambil nominal di jumlah uang
        //CharSequence uang =  nominal.getText();
        //cek text null
        int nom = 0;
        if (nominal.getText().toString() != "") {
             nom = Integer.valueOf(nominal.getText().toString());
        }
        //ambil tipe di radiobox
        int selectedId = transaksi.getCheckedRadioButtonId();
        RadioButton tipe = (RadioButton) findViewById(selectedId);
        String jenistrans = tipe.getText().toString();

        //ambil catatan
        String catatan = "-";
        if (notes.getText().toString() != "") {
            catatan = notes.getText().toString();
        }
        //simpan di database
        //last check
        if (nom == 0){
            //tampilkan peringatan
            Toast.makeText(this,"Nominal transaksi kosong!",Toast.LENGTH_SHORT).show();
        }else{
            //simpan di database
            CharSequence kata = new StringBuilder().append("Transaksi ").append(jenistrans)
                    .append(" pada tanggal ").append(hariini)
                    .append(" sebesar Rp. ").append(nom)
                    .append(" untuk keperluan ").append(catatan)
                    ;

            dbHelper db = new dbHelper(this);

            if (status == "update"){
                db.updateTrans(value,new Trans(kalender.toString(),String.valueOf(nom),jenistrans,catatan));
                kata = kata+(" berhasil diupdate!!");
            }else{
                db.addTransaksi(new Trans(kalender.toString(),String.valueOf(nom),jenistrans,catatan));
                kata = kata+(" berhasil disimpan!!");
            }
            db.close();
            Intent returnIntent = new Intent();
            setResult(RESULT_OK,returnIntent);
            Log.d("Transaksi","result ok");
            Toast.makeText(this,kata,Toast.LENGTH_SHORT).show();
            finish();


        }




    }

    public void Update(Trans trans){

        DatePicker tanggal = (DatePicker) findViewById(R.id.datePicker);
        EditText nominal = (EditText) findViewById(R.id.tbNominal);
        EditText notes = (EditText) findViewById(R.id.tbNotes);
        RadioGroup transaksi = (RadioGroup) findViewById(R.id.rgTransaksi);


        //set nominal
        nominal.setText(trans.getNominal());
        Log.d("Transaksi.java"," update= nominal set");

        //set jenis transaksi
        if (trans.getJenis().startsWith("C")){
            transaksi.check(R.id.rbCredit);
            Log.d("Transaksi.java"," update= credit set");
        }else{
            transaksi.check(R.id.rbDebit);
            Log.d("Transaksi.java"," update= debit set");
        }

        //set tanggalan
        String kalender = trans.getTanggal();
        String[] b = kalender.split("-");
        int tahun = Integer.valueOf(b[0]);
        int bulan = Integer.valueOf(b[1])-1;
        int tgal = Integer.valueOf(b[2]);
        tanggal.init(tahun,bulan,tgal,null);
        Log.d("Transaksi.java"," update= tanggal set");

        //set pesan
        notes.setText(trans.getPesan());
        Log.d("Transaksi.java"," update= notes set");
    }

    //executed when button cancel clicked
    public void ClickCancel(View v){
        return;

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
            case R.id.action_save_button:
                Save();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }


    }
}
