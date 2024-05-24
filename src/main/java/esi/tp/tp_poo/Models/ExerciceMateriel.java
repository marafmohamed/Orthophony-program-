package esi.tp.tp_poo.Models;

public class ExerciceMateriel extends Exercice {

    private String Materiel;

    public ExerciceMateriel(String enonce, String reponse, String Materiel, String consigne) {
        super(enonce, reponse, consigne);
        this.Materiel = Materiel;
    }
}
