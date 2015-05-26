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
public class dbHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "TransDB";

    public dbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    SQLiteDatabase dummy;


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE transaksi ("
                        +" trans_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        +" trans_tgl DATE,"
                        +" trans_jenis TEXT,"
                        +" trans_nominal TEXT,"
                        +" trans_pesan TEXT"
                        +");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS transaksi");
        // create fresh books table
        this.onCreate(db);
    }

    // tabel transaksi
    private static final String TABLE_NAME = "transaksi";

    // Kolom Transaksi
    private static final String KEY_ID = "trans_id";
    private static final String KEY_TGL = "trans_tgl";
    private static final String KEY_JENIS = "trans_jenis";
    private static final String KEY_NOMINAL = "trans_nominal";
    private static final String KEY_PESAN = "trans_pesan";

    private static final String[] COLUMNS = {KEY_ID,KEY_TGL,KEY_JENIS, KEY_NOMINAL, KEY_PESAN};

    public void addTransaksi (Trans trans){
        Log.d("Memasukkan Transaksi",trans.toString());

        // 1. buat database
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. buat content value
        ContentValues values = new ContentValues();
        values.put(KEY_TGL, trans.getTanggal());
        values.put(KEY_JENIS, trans.getJenis());
        values.put (KEY_NOMINAL, trans.getNominal());
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

    public List<Trans> getTransAll(){
        List<Trans> rekap = new LinkedList<Trans>() ;


        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();


        // 2. build query
        String query =  "SELECT  * FROM " + TABLE_NAME+" ORDER BY trans_tgl ASC";
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

    public void updateTrans(int id, Trans trans){
        //1. buat writeabledatabase
        SQLiteDatabase db = this.getWritableDatabase();

        //2. buat content values utk diupdate
        ContentValues values = new ContentValues();
        values.put(KEY_TGL, trans.getTanggal());
        values.put(KEY_JENIS, trans.getJenis());
        values.put (KEY_NOMINAL, trans.getNominal());
        values.put(KEY_PESAN,trans.getPesan());

        //3. update pada id yg dituju
        db.update(TABLE_NAME,values,KEY_ID+" = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public Trans getTrans(int id){
        Trans trans = new Trans();

        SQLiteDatabase db = this.getReadableDatabase();
        String query =  "SELECT  * FROM " + TABLE_NAME+" WHERE trans_id ="+String.valueOf(id);
        Cursor cursor =
                db.rawQuery(query,null);

        if (cursor != null)
            cursor.moveToFirst();

        // ambil dari cursor dan kumpulkan
        trans.setId(Integer.parseInt(cursor.getString(0)));
        trans.setTanggal(cursor.getString(1));
        trans.setJenis(cursor.getString(2));
        trans.setNominal(cursor.getString(3));
        trans.setPesan(cursor.getString(4));

                // tambah ke rekap
        return trans;
    }

    public void deleteTable(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_NAME,null,null);
        db.close();
    }

}
