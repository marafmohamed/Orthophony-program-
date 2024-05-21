package esi.tp.tp_poo.Models;

import java.util.ArrayList;
import java.util.List;

public class BilanOrthophonique {
    private Patient patient;
    private int IdOrthophoniste;
    private List<EpreuveClinique> EprvsCliniques;
    private List<Trouble> diagnostic;
    private String ProjetTherap;
    private String dateRealisation;
    public BilanOrthophonique(Patient patient, String dateRealisation ,ArrayList<EpreuveClinique> EprvsCliniques , int IdOrthophoniste, ArrayList<Trouble> diagnostic , String ProjetTherap) {
        this.patient = patient;
        this.dateRealisation = dateRealisation;
        this.EprvsCliniques=EprvsCliniques;
        this.IdOrthophoniste=IdOrthophoniste;
        this.diagnostic=diagnostic;
        this.ProjetTherap=ProjetTherap;
    }
}
