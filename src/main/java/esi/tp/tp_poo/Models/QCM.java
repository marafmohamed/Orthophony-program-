package esi.tp.tp_poo.Models;

import java.util.List;

public class QCM extends Question {

    private List<Integer> reponsesCorrectes;

    public QCM(String enonce, List<String> reponsesPossibles, List<Integer> reponsesCorrectes) {
        super(enonce, reponsesPossibles, 0);
        this.reponsesCorrectes = reponsesCorrectes;
    }

    public void afficher() {
        System.out.println("Qcm");
    }
}
