package esi.tp.tp_poo.Models;

public class LineRendezVous {
    public  String Date;
    public String Heure;
    public String typeRendezVous;
    public LineRendezVous( String Date, String Heure, String typeRendezVous){
        this.Date = Date;
        this.Heure = Heure;
        this.typeRendezVous = typeRendezVous;

    }
    // Getters
    public String getDate() {
        return Date;
    }
    public String getHeure() {
        return Heure;
    }
    public String getTypeRendezVous() {
        return typeRendezVous;
    }
    //setters

    public void setDate(String Date) {
        this.Date = Date;
    }
    public void setHeure(String Heure) {
        this.Heure = Heure;
    }
    public void setTypeRendezVous(String typeRendezVous) {
        this.typeRendezVous = typeRendezVous;
    }

}
