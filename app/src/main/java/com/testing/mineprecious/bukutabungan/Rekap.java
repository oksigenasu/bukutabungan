package com.testing.mineprecious.bukutabungan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.text.format.DateFormat;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import android.util.Log;
import android.widget.Toast;


public class Rekap extends ActionBarActivity {

    private List<Trans> rekapBulan;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekap);
        startUp();


    }


    public void startUp(){
        Ambiltabel();
        listView = (ListView) findViewById(R.id.lvRekap2);
        listView.setAdapter(new EfficientAdapterRe(this,rekapBulan));
        registerForContextMenu(listView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rekap, menu);
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.lvRekap2) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_list, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case R.id.add:
                Toast.makeText(this, "add", Toast.LENGTH_SHORT).show();

                // add stuff here
                return true;
            case R.id.edit:
                updItem(info.position);// edit stuff here
                //Toast.makeText(this, "edited position:"+String.valueOf(info.position), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.delete:
                delItem(info.position);
                //Toast.makeText(this, "deleted position: "+String.valueOf(info.position), Toast.LENGTH_SHORT).show();
                //startUp();
                // remove stuff here
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void delItem(int id){

        final Integer trans_id = rekapBulan.get(id).getId();
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                       Toast.makeText(Rekap.this, "deleted Transaksi", Toast.LENGTH_SHORT).show();
                        dbHelper db = new dbHelper(Rekap.this);
                        db.deleteTrans(trans_id);
                        db.close();
                        //reFresh();
                        //Yes button clicked
                        finish();
                        reFresh();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        Toast.makeText(Rekap.this, "nope nope nope nope", Toast.LENGTH_SHORT).show();

                        //No button clicked
                        break;
                }
            }

            public void reFresh(){
                Intent intent = new Intent(Rekap.this,Rekap.class);
                startActivity(intent);

            }

        };

        Context con = this;

        AlertDialog.Builder builder = new AlertDialog.Builder(con);
        builder.setMessage("Apakah anda yakin untuk Menghapus?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }

    public void updItem(int id){
        Trans updater = rekapBulan.get(id);
        Log.d("Rekap.java","id="+updater.getId());
        Intent intent = new Intent(this,Transaksi.class);
        intent.putExtra("trans_id",updater.getId());
        startActivityForResult(intent,1);
        //startUp();


    }

    protected void onActivityResult (int requestCode, int resultCode, Intent intent){
        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                //Toast.makeText(this, "activity yes", Toast.LENGTH_SHORT).show();
                //do somethin if ok
                startUp();
            }
            if (resultCode == RESULT_CANCELED){
                //do something if no result
                //Toast.makeText(this, "activity no!", Toast.LENGTH_SHORT).show();

            }
        }
    }

    public Trans Ambiltabel(){
        Trans transaksi = new Trans();
        dbHelper db = new dbHelper(this);
        rekapBulan = db.getTransAll();
        db.close();

        return transaksi;
    }



    private static class EfficientAdapterRe extends BaseAdapter {
        private LayoutInflater mInflater;
        private List<Trans> mTabel;
        //private List<String> mSaldo;

        public EfficientAdapterRe(Context context, List<Trans> tabel) {
            mInflater = LayoutInflater.from(context);
            mTabel = tabel;
            //mSaldo = new LinkedList<>();
        }

        public int getCount() {
            return mTabel.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.rekap_col_layout, null);
                holder = new ViewHolder();
                holder.text1 = (TextView) convertView
                        .findViewById(R.id.TextView01);
                holder.text2 = (TextView) convertView
                        .findViewById(R.id.TextView02);
                holder.text3 = (TextView) convertView
                        .findViewById(R.id.TextView03);
                holder.text4 = (TextView) convertView
                        .findViewById(R.id.TextView04);
                holder.text5 = (TextView) convertView
                        .findViewById(R.id.TextView05);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            //set date
            String a = mTabel.get(position).getTanggal();
            String[] b = a.split("-");
            holder.text1.setText(b[2]+"/"+b[1]);//+" "+b[0]);
            Log.d("Rekap/getView","date set");


            // parse credit and debit and saldo
            if (mTabel.get(position).getJenis().startsWith("C")){
                holder.text2.setText(mTabel.get(position).getNominal());
                //String Sal = CalculateSaldo(String.valueOf(saldo),TransList.nominal[position],"0");
                String Sal="";
                if(position <=0){
                    Sal= "-"+mTabel.get(position).getNominal();
                    mTabel.get(position).setSaldo(Sal);
                    //mSaldo.add(Sal);
                }else{
                    Sal = CalculateSaldo(mTabel.get(position-1).getSaldo(),mTabel.get(position).getNominal(),"0");
                    mTabel.get(position).setSaldo(Sal);

                    //Sal = CalculateSaldo(mSaldo.get(position-1),mTabel.get(position).getNominal(),"0");
                    //mSaldo.add(Sal);

                }
                holder.text3.setText("0");
                holder.text4.setText(Sal);

            }else{

                holder.text2.setText("0");
                holder.text3.setText(mTabel.get(position).getNominal());
                String Sal="";
                if(position <=0){
                    Sal= mTabel.get(position).getNominal();
                    mTabel.get(position).setSaldo(Sal);
                    //mSaldo.add(Sal);
                }else{
                    Sal = CalculateSaldo(mTabel.get(position-1).getSaldo(),"0",mTabel.get(position).getNominal());
                    mTabel.get(position).setSaldo(Sal);
                    //Sal = CalculateSaldo(mSaldo.get(position-1),"0",mTabel.get(position).getNominal());
                    //mSaldo.add(Sal);
                }
                //String Sal = CalculateSaldo(String.valueOf(saldo),"0",TransList.nominal[position]);
                holder.text4.setText(Sal);
            }
            Log.d("Rekap/getView","Jenis Trans & nominal set");

            //holder.text3.setText(TransList.nominal[position]);
            holder.text5.setText(mTabel.get(position).getPesan());
            Log.d("Rekap/getView","Pesan set");

            return convertView;
        }

        public String CalculateSaldo(String Saldo, String Credit,String Debit){

            Integer A = Integer.valueOf(Saldo);
            Integer B = Integer.valueOf(Credit);
            Integer C = Integer.valueOf(Debit);
            Integer D = A-B+C;
            String newSaldo = String.valueOf(D);

            return newSaldo;

        }

        static class ViewHolder {
            TextView text1;
            TextView text2;
            TextView text3;
            TextView text4;
            TextView text5;
        }

    }




}
