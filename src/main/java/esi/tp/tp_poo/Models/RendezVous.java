package esi.tp.tp_poo.Models;

import  java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


public abstract class RendezVous {
    protected int IdOrthophoniste;
    protected Date date;
    protected Time hour;
    public RendezVous(Date date, Time hour, int IdOrthophoniste) {
        this.IdOrthophoniste = IdOrthophoniste;
        this.date = date;
        this.hour = hour;
    }

}
