package esi.tp.tp_poo.Models;

public abstract class CompteRendu {

    protected int IdOrthophoniste;
    protected double TotalScore;
    protected Patient patient;

    public CompteRendu(int IdOrthophoniste) {
        this.IdOrthophoniste = IdOrthophoniste;
    }

    public abstract double CalculateTotalScore();
}
