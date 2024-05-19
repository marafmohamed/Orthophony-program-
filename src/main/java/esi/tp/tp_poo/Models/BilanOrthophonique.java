package esi.tp.tp_poo.Models;

import java.util.ArrayList;
import java.util.List;

public class BilanOrthophonique {
    private static int numeroBilan=0;
    private Patient patient;
    private String dateRealisation;
    private List<Test> resultatTests = new ArrayList<>();

    public BilanOrthophonique(Patient patient, String dateRealisation) {
        this.patient = patient;
        this.dateRealisation = dateRealisation;
        numeroBilan+=1;
    }

    public void ajouterTest(Test test) {
        resultatTests.add(test);
    }
}
