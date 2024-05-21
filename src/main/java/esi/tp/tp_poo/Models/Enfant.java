package esi.tp.tp_poo.Models;

import java.sql.Date;

public class Enfant extends Patient {
    private String etude;
    private String NumTelephoneParent;

    public Enfant(String numDossier, String nom, String prenom, Date dateNaissance, String adresse,
            String lieuNaissance, String NumTelephoneParent,String etude) {
        super(numDossier, nom, prenom, dateNaissance, adresse, lieuNaissance);
        if (!Patient.isValidPhoneNumber(NumTelephoneParent)) {
            throw new IllegalArgumentException("Invalid phone number: " + NumTelephoneParent);
        }
        this.NumTelephoneParent = NumTelephoneParent;
        this.etude = etude;
    }
    

}
