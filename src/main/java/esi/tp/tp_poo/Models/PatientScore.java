package esi.tp.tp_poo.Models;

public class PatientScore {
    private int score; // Score that the patient received for this exercise

    public PatientScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
