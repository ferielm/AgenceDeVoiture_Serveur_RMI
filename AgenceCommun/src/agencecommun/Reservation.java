package agencecommun;


import agencecommun.Client;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Feriel Mokhtari
 */
public class Reservation implements Serializable{
    private int code;
    private Vehicule Voiture ;
    private String clientUsername ;
    private String  dateDe;
    private String  dateA;
    private double prix;

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    

    public Reservation(int code, String clientUsername, Vehicule Voiture, String dateDe, String dateA, double prix) {
        this.code = code;
        this.Voiture = Voiture;
        this.clientUsername = clientUsername;
        this.dateDe = dateDe;
        this.dateA = dateA;
        this.prix = prix;
    }

    public Reservation(int code, String client, Vehicule voiture, String dateDe, String dateA) {
        this.code = code;
        this.Voiture = voiture;
        this.clientUsername = client;
        this.dateDe = dateDe;
        this.dateA = dateA;
    }
   

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Reservation{" + "code=" + code + ", codeVoiture=" + Voiture + ", clientUsername=" + clientUsername + ", dateDe=" + dateDe + ", dateA=" + dateA + '}';
    }

    public String getDateDe() {
        return dateDe;
    }

    public void setDateDe(String dateDe) {
        this.dateDe = dateDe;
    }

    public String getDateA() {
        return dateA;
    }

    public void setDateA(String dateA) {
        this.dateA = dateA;
    }

    public Vehicule getVoiture() {
        return Voiture;
    }

    public void setVoiture(Vehicule codeVoiture) {
        this.Voiture = codeVoiture;
    }

    public String getClientUsername() {
        return clientUsername;
    }

    public void setClientUsername(String clientUsername) {
        this.clientUsername = clientUsername;
    }

    
   
    
    
}
