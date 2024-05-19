package esi.tp.tp_poo.Models;

import java.util.List;

public class FicheSuivi {
    private List<Objectif> objectifs;
    private String observations;

    public FicheSuivi(List<Objectif> objectifs, String observations) {
        this.objectifs = objectifs;
        this.observations = observations;
    }

    public void evaluerProgression(Objectif objectif, int newScore) {
        // Logic for evaluating progression
    }
}
