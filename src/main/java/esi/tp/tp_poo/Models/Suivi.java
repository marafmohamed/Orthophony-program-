package esi.tp.tp_poo.Models;

import java.sql.Date;

public class Suivi extends RendezVous{
    private int numDossier;
    private boolean presentiel;
    //private Objectif[] objectifs;

    public Suivi(String idRendezVous, Date dateHeure, String duree,int numDossier , boolean presentiel, Objectif[] objectifs , int idOrthophoniste) {
        super(idRendezVous, dateHeure, duree, idOrthophoniste);
        this.numDossier= numDossier;
        this.presentiel=presentiel;
        //this.objectifs=objectifs;
    }
}
