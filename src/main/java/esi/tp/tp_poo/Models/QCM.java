package esi.tp.tp_poo.Models;

import java.util.List;

public class QCM extends Question{
    public QCM(String enonce, List<String> reponsesPossibles, String reponseCorrecte) {
        super(enonce, reponsesPossibles, reponseCorrecte);
    }
}
