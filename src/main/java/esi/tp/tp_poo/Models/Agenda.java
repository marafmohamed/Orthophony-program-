package esi.tp.tp_poo.Models;

import java.util.ArrayList;
import java.util.List;

public class Agenda {
    private List<RendezVous> listaRendezVous;

    public Agenda() {
        this.listaRendezVous = new ArrayList<>();
    }

    public void AjouterRendezVous(RendezVous rendez) {
        boolean add = listaRendezVous.add(rendez);
    }

    public void AfficherAgenda(Agenda agenda) {
    }
}
