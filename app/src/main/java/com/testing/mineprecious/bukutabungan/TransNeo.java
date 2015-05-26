package com.testing.mineprecious.bukutabungan;

/**
 * Created by MinePrecious on 19/04/2015.
 * Bean class Trans untuk menyimpan transaksi
 */
public class TransNeo {
    private int id;
    private String tanggal;
    private int credit;
    private int debit;
    private int saldo;
    private int saldo2;
    private String pesan;



    public TransNeo(){}

    public TransNeo(String tanggal, int credit, int debit, int saldo, int saldo2, String pesan ) {
        super();
        this.tanggal = tanggal;
        this.credit = credit;
        this.debit = debit;
        this.saldo = saldo;
        this.saldo2 = saldo2;
        this.pesan = pesan;
    }

    //getters & setters

    public String getTanggal(){
        return tanggal;
    }
    public int getCredit(){
        return credit;
    }
    public int getDebit(){
        return debit;
    }
    public int getSaldo(){
        return saldo;
    }
    public int getSaldo2(){
        return saldo2;
    }
    public String getPesan(){
        return pesan;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public void setDebit(int debit) {
        this.debit = debit;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public void setSaldo2(int saldo2) {
        this.saldo2 = saldo2;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }


    @Override
    public String toString() {
        return "Trans [id=" + id + ", tanggal=" + tanggal + ", credit=" + credit +", debit=" + debit + ", saldo=" + saldo +", saldo2=" + saldo2 +", pesan=" + pesan
                + "]";
    }

}
