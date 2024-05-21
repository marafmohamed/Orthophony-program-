package esi.tp.tp_poo.Models;

import java.util.List;

public class FicheSuivi {
    private List<Objectif> objectifs;
    private String observations;
    private Boolean objectiveDone;

    public FicheSuivi(List<Objectif> objectifs, String observations) {
        this.objectifs = objectifs;
        this.observations = observations;
        this.objectiveDone = false;
    }

    public void evaluerProgression(Objectif objectif, int newScore) {
        objectif.evaluer(newScore);
    }

    public double calculerProgression() {
        int totalEvolution = 0;
        int maxEvolution = objectifs.size() * 5; // Each objective can have a maximum evolution score of 5

        for (Objectif objectif : objectifs) {
            totalEvolution += objectif.getEvolution();
        }

        return ((double) totalEvolution / maxEvolution) * 100;
    }

    public void objectivesDone() {
        this.objectiveDone = true;
    }
}
