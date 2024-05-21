package esi.tp.tp_poo.Models;

import esi.tp.tp_poo.Enums.CatAnamAdulte;

public class QuestAnamAdulte extends QuestAnam {
    private CatAnamAdulte category;

    public QuestAnamAdulte(String questionText,String answerText, CatAnamAdulte category) {
        super(questionText,answerText);
        this.category = category;
    }

    public CatAnamAdulte getCategory() {
        return category;
    }

    public void setCategory(CatAnamAdulte category) {
        this.category = category;
    }

    @Override
    public void askQuestion() {
        System.out.println("Adult question [" + category + "]: " + getQuestionText());
    }
}
