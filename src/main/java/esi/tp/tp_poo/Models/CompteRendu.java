package esi.tp.tp_poo.Models;

public abstract class CompteRendu {
    protected int CompteRendu_id;
    protected int IdOrthophoniste;
    protected int Test_id;
    protected double TotalScore;
    protected int patient;

    public CompteRendu(int IdOrthophoniste, int Test_id, double TotalScore, int patient) {
        this.IdOrthophoniste = IdOrthophoniste;
        this.Test_id = Test_id;
        this.TotalScore = TotalScore;
        this.patient = patient;
    }

    public abstract double CalculateTotalScore();
}
