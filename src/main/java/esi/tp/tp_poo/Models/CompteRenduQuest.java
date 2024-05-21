package esi.tp.tp_poo.Models;

import java.util.ArrayList;
import java.util.List;

public class CompteRenduQuest extends CompteRendu{
    private List<PatientQuestScore> QuestScore = new ArrayList<PatientQuestScore>();
    public CompteRenduQuest(int IdOrthophoniste){
        super(IdOrthophoniste);
    }
    public void AddQuestScore(PatientQuestScore QuestScore){
        this.QuestScore.add(QuestScore);
    }
    @Override
    public double CalculateTotalScore() {
        if(QuestScore.isEmpty()) return this.TotalScore = 0;
        double totalScore = 0;
        for(PatientQuestScore QuestScore : QuestScore) totalScore += QuestScore.getScore();
        return totalScore;
    }
}
