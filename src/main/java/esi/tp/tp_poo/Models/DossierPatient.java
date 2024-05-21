package esi.tp.tp_poo.Models;

import java.util.ArrayList;
import java.util.List;

public class DossierPatient {
    private Patient patient;
    private int NumDossier;
    private List<RendezVous> rendezVous = new ArrayList<>();
    private List<BilanOrthophonique> bilans = new ArrayList<>();
    private List<FicheSuivi> ficheSuivi = new ArrayList<>();
    public DossierPatient(Patient patient , int NumDossier) {
        this.patient = patient;
        this.NumDossier = NumDossier;
    }

    public void ajouterRendezVous(RendezVous nouveauRendezVous) {
        rendezVous.add(nouveauRendezVous);
    }

    public void ajouterBilan(BilanOrthophonique nouveauBilan) {
        bilans.add(nouveauBilan);
    }
    public void ajouterFicheSuivi(FicheSuivi ficheSuivi) {
        this.ficheSuivi.add(ficheSuivi);
    }
}
