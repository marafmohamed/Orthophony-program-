package esi.tp.tp_poo.Models;

public class Exercice {
    private int Exo_id;
    private String enonce;
    private String reponse;
    private String consigne;
    protected int score;

    public Exercice(String enonce, String reponse, String consigne) {
        this.enonce = enonce;
        this.reponse = reponse;
        this.score = 10;
        this.consigne = consigne;
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

    public String getConsigne() {
        return this.consigne;
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
