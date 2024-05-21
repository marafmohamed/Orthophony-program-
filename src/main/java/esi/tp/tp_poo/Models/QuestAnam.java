package esi.tp.tp_poo.Models;

public abstract class QuestAnam {
    private String questionText;
    private String answerText;
    public QuestAnam(String questionText, String answerText) {
        this.questionText = questionText;
        this.answerText = answerText;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public abstract void askQuestion();
}
