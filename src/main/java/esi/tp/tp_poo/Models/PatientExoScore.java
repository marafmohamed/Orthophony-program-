package esi.tp.tp_poo.Models;

public class PatientExoScore extends PatientScore{
    private Exercice exercice;
    public PatientExoScore(int score,Exercice exercice) {
        super(score);
        this.exercice = exercice;
    }
    public Exercice getExercice() {
        return this.exercice;
    }
}
