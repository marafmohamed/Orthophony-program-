package esi.tp.tp_poo.Models;
public class TableDossier{
    public  String Nom;
    public String Prenom;
    public String Age;
    public  String numDossier;
    public TableDossier( String Nom, String Prenom, String Age, String Numbre){
        this.Nom = Nom;
        this.Prenom = Prenom;
        this.Age = Age;
        this.numDossier = Numbre;
    }
    // Getters
    public String getNom() {
        return Nom;
    }
    public String getPrenom() {
        return Prenom;
    }
    public String getAge() {
        return Age;
    }
    public String getNumDossier() {
        return numDossier;
    }
    // Setters
    public void setNom(String Nom) {
        this.Nom = Nom;
    }
    public void setPrenom(String Prenom) {
        this.Prenom = Prenom;
    }
    public void setAge(String Age) {
        this.Age = Age;
    }
    public void setNumDossier(String Number) {
        this.numDossier = Number;
    }

}
