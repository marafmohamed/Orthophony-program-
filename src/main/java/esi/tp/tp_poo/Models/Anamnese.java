package esi.tp.tp_poo.Models;

import java.util.ArrayList;
import java.util.List;

public class Anamnese {
    private List<QuestAnam> questions;

    public Anamnese() {
        this.questions = new ArrayList<>();
    }

    public void addQuestion(QuestAnam question) {
        questions.add(question);
    }

    public List<QuestAnam> getQuestions() {
        return questions;
    }

    public void askAllQuestions() {
        for (QuestAnam question : questions) {
            question.askQuestion();
        }
    }
}
