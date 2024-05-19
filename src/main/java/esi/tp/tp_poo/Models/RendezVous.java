package esi.tp.tp_poo.Models;

import  java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public abstract class RendezVous {
    protected String idRendezVous;
    protected int idOrthophoniste;
    protected Date date;
    protected String duree;
    public RendezVous(String idRendezVous, Date dateHeure , String duree, int idOrthophoniste) {
        this.idRendezVous = idRendezVous;
        this.date = dateHeure;
        this.duree= duree;
        this.idOrthophoniste = idOrthophoniste;
    }

    // public void ajouterParticipant(Patient patient) {
    //     listeParticipants.add(patient);
    // }

    // public void supprimerParticipant(Patient patient) {
    //     listeParticipants.remove(patient);
    // }
}
