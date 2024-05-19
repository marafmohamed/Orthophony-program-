package esi.tp.tp_poo.Models;

public class Objectif {
    private String nomObjectif;
    private String description;
    private int niveauPriorite;
    private Termes terme;

    public Objectif(String nomObjectif, String description, int niveauPriorite, Termes terme) {
        this.nomObjectif = nomObjectif;
        this.description = description;
        this.niveauPriorite = niveauPriorite;
        this.terme = terme;
    }

    public enum Termes {
        COURT_TERME, MOYEN_TERME, LONG_TERME
    }
}
