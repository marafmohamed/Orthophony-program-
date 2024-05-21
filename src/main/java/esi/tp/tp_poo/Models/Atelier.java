package esi.tp.tp_poo.Models;

import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class Atelier extends RendezVous{
    private String Thematique;
    private List<Patient> Patients;
    private Duration duration=Duration.ofHours(1);
    public Atelier(Date date, Time time, String Thematique, ArrayList<Patient> Patients , int IdOrthophoniste){
        super(date,time,IdOrthophoniste);
        this.Thematique=Thematique;
        this.Patients=Patients;     //will change this line to search for Patients with their NumDossiers
    }
}
