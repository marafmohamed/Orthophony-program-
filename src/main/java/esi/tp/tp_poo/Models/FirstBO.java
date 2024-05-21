package esi.tp.tp_poo.Models;

import java.util.ArrayList;

public class FirstBO extends BilanOrthophonique {
    Anamnese anamnese;
    public FirstBO (Patient patient, String dateRealisation , ArrayList<EpreuveClinique> EprvsCliniques , int IdOrthophoniste, ArrayList<Trouble> diagnostic , String ProjetTherap,Anamnese anamnese) {
        super(patient,dateRealisation,EprvsCliniques,IdOrthophoniste,diagnostic,ProjetTherap);
        this.anamnese=anamnese;
    }
}
