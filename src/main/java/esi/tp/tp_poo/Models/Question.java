package esi.tp.tp_poo.Models;

import java.util.List;

public class Question {
    private String enonce;
    private List<String> reponsesPossibles;
    private String reponseCorrecte;

    public Question(String enonce, List<String> reponsesPossibles, String reponseCorrecte) {
        this.enonce = enonce;
        this.reponsesPossibles = reponsesPossibles;
        this.reponseCorrecte = reponseCorrecte;
    }
}