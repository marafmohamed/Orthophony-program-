package esi.tp.tp_poo.Models;

public class ExerciceMateriel extends Exercice{

    private  String Materiel;

     public ExerciceMateriel(String enonce, String reponse, int score, String Materiel) {
        super(enonce, reponse, 0);
        this.Materiel = Materiel;
    }
}
