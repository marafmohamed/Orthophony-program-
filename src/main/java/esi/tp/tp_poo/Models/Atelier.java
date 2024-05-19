package esi.tp.tp_poo.Models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Atelier extends RendezVous{
    private String theme;
    protected List<Patient> listeParticipants = new ArrayList<>();

    public Atelier(String idRendezVous, Date dateHeure, String duree, String theme , int idOrthophoniste) {
        super(idRendezVous, dateHeure, duree, idOrthophoniste);
        this.theme=theme;

        }
}
