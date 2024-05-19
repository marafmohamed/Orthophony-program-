package esi.tp.tp_poo.Models;

import java.util.ArrayList;

public class TestQuestions extends  Test {

    private ArrayList<Question> Questionnaire;
    private int scoreTotal;

    public TestQuestions(String nomTest, String capacité, Patient patient, CompteRendu compteRendu, Question[] Questionnaire, int score) {
        super(nomTest, capacité, patient, compteRendu);
        this.Questionnaire = new ArrayList<Question>();
    }

    public int CalculerScore(){
        for (Question question : Questionnaire) {
            scoreTotal += question.score;
        }
        return scoreTotal;
    }
}
