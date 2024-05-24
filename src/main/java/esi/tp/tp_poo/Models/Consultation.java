package esi.tp.tp_poo.Models;

import esi.tp.tp_poo.Enums.TypePatient;
import org.jetbrains.annotations.NotNull;

import java.sql.Date;
import java.sql.Time;
import java.time.Duration;

public class Consultation extends RendezVous {
    private String nom;
    private String prenom;
    private int age;
    private int NumDossier;
    private TypePatient typePatient;
    private Duration duration;
    // num de dossier
    public Consultation(int age, String nom, String prenom , Date date, Time hour, int IdOrthophoniste, @NotNull TypePatient typePatient) {
        super( date,  hour, IdOrthophoniste);
        this.age = age;
        this.nom = nom;
        this.prenom = prenom;
        switch (typePatient){
            case ADULTE ->  {this.duration=Duration.ofMinutes(90); break;}
            case ENFANT -> {this.duration=Duration.ofMinutes(150); break;}
        }
        this.typePatient=typePatient;
    }
    public void setNumDossier(int NumDossier) {
        this.NumDossier = NumDossier;
    }

}
