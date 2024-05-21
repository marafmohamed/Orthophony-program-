package esi.tp.tp_poo.Models;

import java.sql.Date;
import java.sql.Time;
import java.time.Duration;

public class Suivi extends RendezVous{
    private int numDossier;
    private boolean presentiel;
    private Duration duration=Duration.ofHours(1);
    public Suivi(Date date, Time hour, int numDossier , boolean presentiel, int IdOrthophoniste) {
        super(date,hour,IdOrthophoniste);
        this.numDossier= numDossier;
        this.presentiel=presentiel;
    }
}
