package esi.tp.tp_poo.Models;

import java.util.ArrayList;
import java.util.List;

public class DossierPatient {
    private Patient patient;
    private List<RendezVous> rendezVous = new ArrayList<>();
    private List<BilanOrthophonique> bilans = new ArrayList<>();

    public DossierPatient(Patient patient) {
        this.patient = patient;
    }

    public void ajouterRendezVous(RendezVous nouveauRendezVous) {
        rendezVous.add(nouveauRendezVous);
    }

    public void ajouterBilan(BilanOrthophonique nouveauBilan) {
        bilans.add(nouveauBilan);
    }
}
