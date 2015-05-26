package com.testing.mineprecious.bukutabungan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by MinePrecious on 19/04/2015.
 */
public class dbHelperNeo extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "TransDB";

    public dbHelperNeo(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE transaksiNeo ("
                        +" trans_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        +" trans_tgl TEXT,"
                        +" trans_credit INTEGER,"
                        +" trans_debit INTEGER,"
                        +" trans_Saldo INTEGER,"
                        +" trans_Saldo2 INTEGER,"
                        +" trans_pesan TEXT"
                        +");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS books");
        // create fresh books table
        this.onCreate(db);
    }


    // tabel transaksi
    private static final String TABLE_NAME = "transaksi";

    // Kolom Transaksi
    private static final String KEY_ID = "trans_id";
    private static final String KEY_TGL = "trans_tgl";
    private static final String KEY_CREDIT = "trans_credit";
    private static final String KEY_DEBIT = "trans_debit";
    private static final String KEY_SALDO = "trans_saldo";
    private static final String KEY_SALDO2 = "trans_saldo2";
    private static final String KEY_PESAN = "trans_pesan";

    private static final String[] COLUMNS = {KEY_ID,KEY_TGL,KEY_CREDIT, KEY_DEBIT,KEY_SALDO, KEY_SALDO2, KEY_PESAN};

    public void addTransaksi (TransNeo trans){
        Log.d("Memasukkan Transaksi",trans.toString());

        // 1. buat database
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. buat content value
        ContentValues values = new ContentValues();
        values.put(KEY_TGL, trans.getTanggal());
        values.put(KEY_CREDIT, trans.getCredit());
        values.put (KEY_DEBIT, trans.getDebit());
        values.put(KEY_SALDO, trans.getSaldo());
        values.put (KEY_SALDO2, trans.getSaldo2());
        values.put(KEY_PESAN,trans.getPesan());

        //3. masukkan ke database
        db.insert(TABLE_NAME,null,values);

        db.close();

    }

    public List<Trans> getTransBulanan(int bulan){

        List<Trans> rekap = new LinkedList<Trans>() ;


        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();


        // 2. build query
        String query =  "SELECT  * FROM " + TABLE_NAME + " WHERE trans_tgl LIKE %-" +String.valueOf(bulan)+"-%";
        Cursor cursor =
                db.rawQuery(query,null);

        // 3. if we got results get the first one
        //if (cursor != null)
        //    cursor.moveToFirst();

        // ambil dari cursor dan kumpulkan
        if (cursor.moveToFirst()) {
            // 4. build book object
            do {
                Trans transaksi = new Trans();
                transaksi.setId(Integer.parseInt(cursor.getString(0)));
                transaksi.setTanggal(cursor.getString(1));
                transaksi.setJenis(cursor.getString(2));
                transaksi.setNominal(cursor.getString(3));
                transaksi.setPesan(cursor.getString(4));

                // tambah ke rekap
                rekap.add(transaksi);
            } while (cursor.moveToNext());

        }
        //Book book = new Book();
        //book.setId(Integer.parseInt(cursor.getString(0)));
        //book.setTitle(cursor.getString(1));
        //book.setAuthor(cursor.getString(2));

        //log
        Log.d("getTransaksi("+bulan+")",String.valueOf(bulan));

        // 5. return book
        return rekap;
    }

    public List<TransNeo> getTransAll(){
        List<TransNeo> rekap = new LinkedList<>() ;


        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();


        // 2. build query
        String query =  "SELECT  * FROM " + TABLE_NAME;
        Cursor cursor =
                db.rawQuery(query,null);

        // 3. if we got results get the first one
        //if (cursor != null)
        //    cursor.moveToFirst();

        // ambil dari cursor dan kumpulkan
        if (cursor.moveToFirst()) {
            // 4. build book object
            do {
                TransNeo transaksi = new TransNeo();
                transaksi.setId(Integer.parseInt(cursor.getString(0)));
                transaksi.setTanggal(cursor.getString(1));
                transaksi.setCredit(cursor.getInt(2));
                transaksi.setDebit(cursor.getInt(3));
                transaksi.setSaldo(cursor.getInt(4));
                transaksi.setSaldo2(cursor.getInt(5));
                transaksi.setPesan(cursor.getString(6));

                // tambah ke rekap
                rekap.add(transaksi);
            } while (cursor.moveToNext());

        }
        //Book book = new Book();
        //book.setId(Integer.parseInt(cursor.getString(0)));
        //book.setTitle(cursor.getString(1));
        //book.setAuthor(cursor.getString(2));



        // 5. return book
        return rekap;

    }

    public void deleteTrans(int id){
        // 1. ambil writable database
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME,KEY_ID+" = ?", new String[]{String.valueOf(id)} );
        db.close();

        Log.d("deleteTrans("+id+")",String.valueOf(id));

    }

}
