package esi.tp.tp_poo.Models;

public class CurrentPatient {

    private static CurrentPatient instance;
    private Patient currentPatient;

    // Private constructor to prevent instantiation
    private CurrentPatient() {
    }

    // Public method to provide access to the single instance
    public static CurrentPatient getInstance() {
        if (instance == null) {
            instance = new CurrentPatient();
        }
        return instance;
    }

    // Method to set the current patient
    public void setCurrentPatient(Patient patient) {
        this.currentPatient = patient;
    }

    // Method to get the current patient
    public Patient getCurrentPatient() {
        return currentPatient;
    }

    // Method to remove the current patient
    public void removeCurrentPatient() {
        this.currentPatient = null;
    }
}
