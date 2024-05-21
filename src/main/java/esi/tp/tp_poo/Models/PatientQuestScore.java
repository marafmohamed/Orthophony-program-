package esi.tp.tp_poo.Models;

public class PatientQuestScore extends PatientScore {
    private Question question;
    public PatientQuestScore(int score,Question question) {
        super(score);
        this.question = question;
    }
    public Question getQuestion() {
        return this.question;
    }
}
