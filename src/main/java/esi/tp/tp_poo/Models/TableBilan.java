package esi.tp.tp_poo.Models;

public class TableBilan {
    private String Date;
    private String BilanId;

    public TableBilan(String date, String bilanId) {
        this.Date = date;
        this.BilanId = bilanId;
    }

    public String getDate() {
        return Date;
    }

    public String getBilanId() {
        return BilanId;
    }
}
