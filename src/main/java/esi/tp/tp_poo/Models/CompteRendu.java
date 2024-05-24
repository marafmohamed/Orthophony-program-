package esi.tp.tp_poo.Models;

public abstract class CompteRendu {

    private int IdOrthophoniste;
    protected double TotalScore;
    private Patient patient;

    public CompteRendu(int IdOrthophoniste) {
        this.IdOrthophoniste = IdOrthophoniste;
    }

    public abstract double CalculateTotalScore();
}
