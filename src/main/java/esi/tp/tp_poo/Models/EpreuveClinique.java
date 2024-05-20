package esi.tp.tp_poo.Models;

import java.util.ArrayList;
import java.util.List;

public class EpreuveClinique {
    private String nom;
    private List<String> observations;
    private List<Test> tests;
    public EpreuveClinique(String nom, ArrayList<String> observations, ArrayList<Test> tests) {
        this.nom = nom;
        this.observations = observations;
        this.tests = tests;
    }
}
