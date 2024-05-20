package esi.tp.tp_poo.Models;

import java.util.ArrayList;
import java.util.List;


public abstract class Question {
    private String enonce;
    private List<String> propositions;
    protected int score;

    public Question(String enonce, List<String> propositions, int score) {
        this.enonce = enonce;
        this.propositions = propositions;
        this.score = 0;
    }

    public List<String> getPropositions() {
        return propositions;
    }

    public String getEnonce() {
        return enonce;
    }

    public void setPropositions(List<String> propositions) {
        this.propositions = propositions;
    }

    public void setEnonce(String enonce) {
        this.enonce = enonce;
    }

    public abstract void afficher();
}