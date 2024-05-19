package esi.tp.tp_poo.Models;

public class Resultat {
    private Question question;
    private int score;

    public Resultat(Question question, int score) {
        this.question = question;
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
