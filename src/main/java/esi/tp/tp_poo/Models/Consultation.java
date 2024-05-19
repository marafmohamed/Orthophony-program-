package esi.tp.tp_poo.Models;

import java.sql.Date;
public class Consultation extends RendezVous {
    private String nom;
    private String prenom;
    private int age;

    // num de dossier
    public Consultation(String idRendezVous, Date dateHeure, String duree, int age, String nom, String prenom ,int idOrthophoniste) {
        super(idRendezVous, dateHeure, duree, idOrthophoniste);
        this.age = age;
        this.nom = nom;
        this.prenom = prenom;
    }

    

}
