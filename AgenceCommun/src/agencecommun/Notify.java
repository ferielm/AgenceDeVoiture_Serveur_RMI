package agencecommun;

import agencecommun.Client;
import java.rmi.Remote;
import java.rmi.RemoteException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Feriel Mokhtari
 */
public interface Notify extends Remote {

    Client getClient() throws RemoteException;

    void setClient(Client client) throws RemoteException;

    void infoReservation(Reservation rsv) throws RemoteException;

    void annoncePromotion(Vehicule voiture) throws RemoteException;
    void reservationAnnuler(Reservation rv) throws RemoteException;
     void reservationTerminerAvecPenalite(Reservation rv) throws RemoteException;
     void reservationTerminer(Reservation rv) throws RemoteException;
    void messageConnexion(Client client) throws RemoteException;
    void messageDeconnexion(Client client) throws RemoteException;
    void messageInscription( )throws RemoteException;
}
