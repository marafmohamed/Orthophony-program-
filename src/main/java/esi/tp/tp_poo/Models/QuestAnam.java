package esi.tp.tp_poo.Models;

public abstract class QuestAnam {
    private String questionText;

    public QuestAnam(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public abstract void askQuestion();
}
