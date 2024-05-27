package esi.tp.tp_poo.Models;

public class CurrentBilan {
    private static CurrentBilan instance;
    private BilanOrthophonique currentBilan;

    // Private constructor to prevent instantiation
    private CurrentBilan() {
    }

    // Public method to provide access to the single instance
    public static CurrentBilan getInstance() {
        if (instance == null) {
            instance = new CurrentBilan();
        }
        return instance;
    }

    // Method to set the current patient
    public void setCurrentBilan(BilanOrthophonique BilanOrthophonique) {
        this.currentBilan = BilanOrthophonique;
    }

    // Method to get the current patient
    public BilanOrthophonique getCurrentPatient() {
        return currentBilan;
    }

    // Method to remove the current patient
    public void removeCurrentBilan() {
        this.currentBilan = null;
    }
}

