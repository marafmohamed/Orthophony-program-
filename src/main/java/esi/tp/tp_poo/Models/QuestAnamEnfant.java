package esi.tp.tp_poo.Models;

import esi.tp.tp_poo.Enums.CatAnamEnfant;

public class QuestAnamEnfant extends QuestAnam{
    private CatAnamEnfant category;

    public QuestAnamEnfant(String questionText, String answerText,CatAnamEnfant category) {
        super(questionText,answerText);
        this.category = category;
    }

    public CatAnamEnfant getCategory() {
        return category;
    }

    public void setCategory(CatAnamEnfant category) {
        this.category = category;
    }

    @Override
    public void askQuestion() {
        System.out.println("Child question [" + category + "]: " + getQuestionText());
    }
}
