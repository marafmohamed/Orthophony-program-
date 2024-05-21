package esi.tp.tp_poo.Models;

import java.sql.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Patient {
    protected String numDossier;
    protected String nom;
    protected String prenom;
    protected Date dateNaissance;
    protected String lieuNaissance;
    protected String adresse;

    public Patient(String numDossier, String nom, String prenom, Date dateNaissance, String adresse, String lieuNaissance) {
        this.numDossier = numDossier;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.lieuNaissance=lieuNaissance;
        this.adresse = adresse;
    }

    public String obtenirDossier() {
        // Logic to access patient's dossier
        return numDossier;
    }
    public static boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "^(0\\d{9}|\\+213\\d{9})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

}
