package agencecommun;

import java.io.InputStream;
import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Feriel Mokhtari
 */
public class Vehicule implements Serializable {

    /* (la marque, la couleur,
le carburant (diesel ou essence), le kilométrage et le prix de location journalier).*/
    private int code;
    private String marque;
    private String couleur;
    private String carburant;
    private Double kilometrage;
    private Double prix;
    private boolean disponible;
    private byte[] photo;
    private int promo;

    public Vehicule(int code, String marque, String couleur, String carburant, Double kilometrage, Double prix, boolean disponible, byte[] photo, int promo) {
        this.code = code;
        this.marque = marque;
        this.couleur = couleur;
        this.carburant = carburant;
        this.kilometrage = kilometrage;
        this.prix = prix;
        this.disponible = disponible;
        this.photo = photo;
        this.promo = promo;
    }

    public Vehicule(int code, String marque, String couleur, String carburant, Double kilometrage, Double prix, boolean disponible, byte[] photo) {
        this.code = code;
        this.marque = marque;
        this.couleur = couleur;
        this.carburant = carburant;
        this.kilometrage = kilometrage;
        this.prix = prix;
        this.disponible = disponible;
        this.photo = photo;
    }

    public int getPromo() {
        return promo;
    }

    public void setPromo(int promo) {
        this.promo = promo;
    }

    public Vehicule(int code, String marque, String couleur, String carburant, Double kilometrage, Double prix, boolean disponible) {
        this.code = code;
        this.marque = marque;
        this.couleur = couleur;
        this.carburant = carburant;
        this.kilometrage = kilometrage;
        this.prix = prix;
        this.disponible = disponible;
    }

    public void setVehicule(Vehicule v) {
        this.setCarburant(v.getCarburant());
        this.setCouleur(v.getCouleur());
        setDisponible(v.isDisponible());
        setKilometrage(v.getKilometrage());
        setPrix(v.getPrix());
        setMarque(v.getMarque());
        
    }

    public byte[] getPhoto() {
        return photo;
    }

    @Override
    public String toString() {
        return "Vehicule{" + "code=" + code + ", marque=" + marque + ", couleur=" + couleur + ", carburant=" + carburant + ", kilometrage=" + kilometrage + ", prix=" + prix + ", disponible=" + disponible + '}';
    }

    public String getMarque() {
        return marque;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public int getCode() {
        return code;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getCarburant() {
        return carburant;
    }

    public void setCarburant(String carburant) {
        this.carburant = carburant;
    }

    public Double getKilometrage() {
        return kilometrage;
    }

    public void setKilometrage(Double kilometrage) {
        this.kilometrage = kilometrage;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

}
