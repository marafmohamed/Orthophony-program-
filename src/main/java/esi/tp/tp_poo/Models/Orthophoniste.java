package esi.tp.tp_poo.Models;

import java.util.ArrayList;
import java.util.List;

public class Orthophoniste  {
    private String identifiant;
    private String nom;
    private String prenom;
    private String adresse;
    private String telephone;
    private String email;
    private String motDePasse;
    private List<RendezVous> agenda = new ArrayList<>();

    public Orthophoniste(String identifiant, String nom, String prenom, String adresse, String telephone, String email, String motDePasse) {
        this.identifiant = identifiant;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.email = email;
        this.motDePasse = motDePasse;
    }


    public void planifierRendezVous(RendezVous rendezVous) {
        agenda.add(rendezVous);
    }
    public void modifierInformationPersonnelle(String information) {
        // Implementation needed - modify fields based on the information argument
    }
}