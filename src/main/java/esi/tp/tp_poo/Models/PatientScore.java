package esi.tp.tp_poo.Models;

public class PatientScore {
    protected int PatientScore_id;
    protected int score; // Score that the patient received for this exercise
    protected int CompteRendu;
    public PatientScore(int score, int CompteRendu) {
        this.score = score;
        this.CompteRendu = CompteRendu;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
