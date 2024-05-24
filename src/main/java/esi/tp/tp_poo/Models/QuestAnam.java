package esi.tp.tp_poo.Models;

public abstract class QuestAnam {
    protected int Anamnese;
    protected int Question_id;
    protected String questionText;
    protected String answerText;
    public QuestAnam(String questionText, String answerText, int Anamnese) {
        this.questionText = questionText;
        this.answerText = answerText;
        this.Anamnese = Anamnese;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
    public int getAnamneseId(){
        return Anamnese;
    }

}
