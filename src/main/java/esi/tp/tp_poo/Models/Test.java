package esi.tp.tp_poo.Models;

import java.util.ArrayList;
import java.util.List;

public abstract class Test {
    protected int Test_id;
    protected String nomTest;
    protected String Capacité;
    protected Patient patient;
    protected  CompteRendu compteRendu;

    public Test(String nomTest, String capacité, Patient patient, CompteRendu compteRendu) {
        this.nomTest = nomTest;
        this.Capacité = capacité;
        this.patient = patient;
        this.compteRendu = compteRendu;
    }

}