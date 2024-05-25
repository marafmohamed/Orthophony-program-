package esi.tp.tp_poo.Models;

import java.util.List;
import java.util.ArrayList;

public class QCU extends Question {

    private int reponseCorrecte;

    public QCU(String enonce, ArrayList<String> propositions, int reponse) {
        super(enonce, propositions, 0);
        this.reponseCorrecte = reponse;
    }

    public int getReponseCorrecte() {
        return reponseCorrecte;
    }

    public void setReponseCorrecte(int reponseCorrecte) {
        this.reponseCorrecte = reponseCorrecte;
    }

    public void afficher() {
        System.out.println("Qcu");

    }


}
