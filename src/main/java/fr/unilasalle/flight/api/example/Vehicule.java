package fr.unilasalle.flight.api.example;

public class Vehicule {
    protected String nom;
    protected double poids;
    protected double prix;
    protected double vitesse;


    public Vehicule(String nom, double poids, double prix, double vitesse) {
        this.nom = nom;
        this.poids = poids;
        this.prix = prix;
        this.vitesse = vitesse;
    }


    public void accelerer(){
        System.out.println("J'accelere..");
        vitesse = vitesse + 1;
    }
}
