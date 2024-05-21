package esi.tp.tp_poo.Models;

import esi.tp.tp_poo.Enums.TypeTerm;

public class Objectif {
    private String nom;
    private String description;
    private TypeTerm terme;
    private int evolution;

    public Objectif(String nomObjectif, String description, TypeTerm terme) {
        this.nom = nomObjectif;
        this.description = description;
        this.terme = terme;
        this.evolution = 0;
    }

    public void evaluer(int evolution) throws IllegalArgumentException {
        if (evolution < 1 || evolution > 5) {
            throw new IllegalArgumentException("Evaluation must be between 1 and 5");
        }
        this.evolution = evolution;
    }

    public int getEvolution() {
        return evolution;
    }
}
