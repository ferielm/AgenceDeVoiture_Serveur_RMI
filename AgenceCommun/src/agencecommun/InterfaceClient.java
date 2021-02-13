package agencecommun;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Feriel Mokhtari
 */
public interface InterfaceClient extends Remote {

    double seConnecter(Client client) throws RemoteException;

  

    boolean peutReserver(Reservation rv) throws RemoteException;

    void visualiser(Vehicule voiture) throws RemoteException;

   
   
    
    void deconnexion() throws RemoteException;

    boolean sinscrire(Client client) throws RemoteException;

    void inscriptionReussite(Client client) throws RemoteException;
}
