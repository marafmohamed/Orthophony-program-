package esi.tp.tp_poo.Models;

public class TestTable {
    private String Nom;
    private String Capacite;

    public TestTable(String date, String bilanId) {
        this.Nom = date;
        this.Capacite = bilanId;
    }

    public String getNom() {
        return Nom;
    }

    public String getCapacite() {
        return Capacite;
    }
}
