package esi.tp.tp_poo.Models;

import java.sql.Date;

public abstract class Patient {
    protected String numDossier;
    protected String nom;
    protected String prenom;
    protected Date dateNaissance;
    protected String adresse;

    public Patient(String numDossier, String nom, String prenom, Date dateNaissance, String adresse, String informationsContact) {
        this.numDossier = numDossier;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.adresse = adresse;
    }

    public String obtenirDossier() {
        // Logic to access patient's dossier
        return numDossier;
    }

}
