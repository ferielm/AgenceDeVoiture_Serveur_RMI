/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agencecommun;

import java.io.Serializable;

/**
 *
 * @author Feriel Mokhtari
 */
public class Historique extends Reservation implements Serializable {

    private String renduLe;

    public Historique(int code, String client, Vehicule voiture, String dateDe, String dateA, String renduLe) {
        super(code, client, voiture, dateDe, dateA);
        this.renduLe = renduLe;
    }

    public String getRenduLe() {
        return renduLe;
    }

    public void setRenduLe(String renduLe) {
        this.renduLe = renduLe;
    }

}
