package com.testing.mineprecious.bukutabungan;

/**
 * Created by MinePrecious on 19/04/2015.
 * Bean class Trans untuk menyimpan transaksi
 */
public class Trans {
    private int id;
    private String tanggal;
    private String nominal;
    private String jenis;
    private String pesan;
    private String saldo;



    public Trans(){}

    public Trans(String tanggal, String nominal, String jenis, String pesan ) {
        super();
        this.tanggal = tanggal;
        this.nominal = nominal;
        this.jenis = jenis;
        this.pesan = pesan;
    }

    //getters & setters

    public String getTanggal(){
        return tanggal;
    }
    public String getNominal(){
        return nominal;
    }
    public String getJenis(){
        return jenis;
    }
    public String getPesan(){
        return pesan;
    }
    public String getSaldo() {return saldo;}
    public int getId() {return id;}

    public void setId(int id) {
        this.id = id;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public void setSaldo(String saldo) {this.saldo = saldo; }



    @Override
    public String toString() {
        return "Trans [id=" + id + ", tanggal=" + tanggal + ", nominal=" + nominal +", jenis=" + jenis +", pesan=" + pesan
                + "]";
    }

    public String toStringSimpan(){
        return "Transaksi "+jenis+" pada tanggal "+tanggal+" sebesar: Rp."+nominal+" untuk "+pesan+" berhasil disimpan!";
    }

}
