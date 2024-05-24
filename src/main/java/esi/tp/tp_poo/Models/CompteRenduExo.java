package esi.tp.tp_poo.Models;

import java.util.ArrayList;
import java.util.List;

public class CompteRenduExo extends CompteRendu {
    private List<PatientExoScore> ExoScores = new ArrayList<PatientExoScore>();

    public CompteRenduExo(int IdOrthophoniste) {
        super(IdOrthophoniste);
    }

    public void AddExoScore(PatientExoScore ExoScore) {
        this.ExoScores.add(ExoScore);
    }

    @Override
    public double CalculateTotalScore() {
        if (ExoScores.isEmpty()) return this.TotalScore = 0;
        double totalScore = 0;
        for (PatientExoScore PScore : ExoScores)
            totalScore += (double) PScore.getScore() / PScore.getExercice().getScore();
        return this.TotalScore = totalScore;
    }
}
