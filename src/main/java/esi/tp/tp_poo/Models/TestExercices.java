package esi.tp.tp_poo.Models;

import java.util.ArrayList;

public class TestExercices extends Test {

    private ArrayList<Exercice> SerieExercices;
    double scoreTotal;


    public TestExercices(String nomTest, String capacité, Patient patient, CompteRendu compteRendu, Exercice[] SerieExercices) {
        super(nomTest, capacité, patient, compteRendu);
        this.SerieExercices = new ArrayList<Exercice>();
    }

    public double CalculerScore(){
        for (Exercice exercice : SerieExercices) {
            scoreTotal += exercice.score;
        }
        return scoreTotal;
    }
}
