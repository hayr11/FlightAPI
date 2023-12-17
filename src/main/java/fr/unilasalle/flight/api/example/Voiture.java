package fr.unilasalle.flight.api.example;

public class Voiture extends Vehicule{

    private double nombre_places;

    public Voiture(String nom, double poids, double prix, double vitesse, double nombre_places) {
        super(nom, poids, prix, vitesse);

        this.nombre_places = nombre_places;
    }


    public void demarrer(){
        vitesse = 10;
    }

}
