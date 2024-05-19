package esi.tp.tp_poo.Models;

public class Exercice {
    private String enonce;
    private String reponse;
    protected int score;

    public Exercice(String enonce, String reponse, int score) {
        this.enonce = enonce;
        this.reponse = reponse;
        this.score = 0;
    }

    public String getEnonce() {
        return enonce;
    }

    public String getReponse() {
        return reponse;
    }

    public int getScore() {
        return score;
    }

    public void setEnonce(String enonce) {
        this.enonce = enonce;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void afficher() {
        System.out.println(enonce);
    }
}
