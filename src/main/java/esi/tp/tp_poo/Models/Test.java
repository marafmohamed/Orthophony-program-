package esi.tp.tp_poo.Models;

import java.util.ArrayList;
import java.util.List;

public abstract class Test {
    protected int Test_id;
    protected String nomTest;
    protected String Capacité;
    protected int patient;
    protected  int compteRendu;
    protected int Bilan;

    public Test(String nomTest, String capacité, int patient, int compteRendu, int Bilan) {
        this.nomTest = nomTest;
        this.Capacité = capacité;
        this.patient = patient;
        this.compteRendu = compteRendu;
        this.Bilan = Bilan;
    }
    public Patient getPatient(){
        return Patient.getPatientById(patient);
    }
    public abstract void insertTest();
}