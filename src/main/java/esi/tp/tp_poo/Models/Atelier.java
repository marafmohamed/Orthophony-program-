package esi.tp.tp_poo.Models;

import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class Atelier extends RendezVous{
    private String Thematique;
    private List<DossierPatient> DossierPatient;
    private Duration duration=Duration.ofHours(1);
    public Atelier(Date date, Time time, String Thematique, ArrayList<DossierPatient> DossierPatient , int IdOrthophoniste){
        super(date,time,IdOrthophoniste);
        this.Thematique=Thematique;
        this.DossierPatient=DossierPatient;     //will change this line to search for Patients with their NumDossiers
    }
}
