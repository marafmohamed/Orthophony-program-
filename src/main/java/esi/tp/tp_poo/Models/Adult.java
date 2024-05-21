package esi.tp.tp_poo.Models;

import java.sql.Date;


public class Adult extends Patient {
    private String diplome;
    private String proffession;
    private String NumTelephone;

    public Adult(String numDossier, String nom, String prenom, Date dateNaissance, String adresse,
            String informationsContact, String diplome, String proffession, String NumTelephone) {
        super(numDossier, nom, prenom, dateNaissance, adresse, informationsContact);
        if (!Patient.isValidPhoneNumber(NumTelephone)) {
            throw new IllegalArgumentException("Invalid phone number: " + NumTelephone);
        }
        this.NumTelephone = NumTelephone;
        this.diplome = diplome;
        this.proffession = proffession;
    }

}
