package esi.tp.tp_poo.Models;

import java.sql.Date;

public class Enfant extends Patient {
    private String etude;
    private String NumTelephoneParent;

    public Enfant(String numDossier, String nom, String prenom, Date dateNaissance, String adresse,
            String informationsContact, String NumTelephoneParent,String etude) {
        super(numDossier, nom, prenom, dateNaissance, adresse, informationsContact);
        this.NumTelephoneParent = NumTelephoneParent;
        this.etude = etude;
    }
    

}
