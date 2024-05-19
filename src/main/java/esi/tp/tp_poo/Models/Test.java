package esi.tp.tp_poo.Models;

import java.util.ArrayList;
import java.util.List;

public class Test {
    private String nomTest;
    private List<Resultat> resultats = new ArrayList<>();

    public Test(String nomTest) {
        this.nomTest = nomTest;
    }

    public int evaluer() {
        return resultats.stream().mapToInt(Resultat::getScore).sum();
    }
}