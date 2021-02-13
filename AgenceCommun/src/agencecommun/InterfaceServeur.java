package agencecommun;

import java.io.InputStream;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Feriel Mokhtari
 */
public interface InterfaceServeur extends Remote {

    void ajouterVihecule(String marque, String couleur, String carburant, Double kilometrage, Double prix, byte[] photo) throws RemoteException;

    void editerVehicule(Vehicule voiture , int row) throws RemoteException;

   

    HashMap<Integer,Reservation> reservationsVoiture(Vehicule voiture) throws RemoteException;
    HashMap<Integer,Reservation> reservationsClient(String username) throws RemoteException;

    String test() throws RemoteException;

    boolean modifierSoldeClient(String username, double nvSolde) throws RemoteException;

    void ajouterPromo(Vehicule voiture, int prm,int row ) throws RemoteException;

    
    
    ArrayList<Vehicule> afficherToutParTri(String triPar, String orderBy) throws RemoteException;
    HashMap<Integer,Vehicule> afficherToutParTri1(String triPar, String orderBy) throws RemoteException;
    void rejoindre(Notify n) throws RemoteException;

    void quitter(Notify n) throws RemoteException;

    void callMeBack(Notify n) throws RemoteException;

    double connexion(Client client) throws RemoteException;

    boolean inscription(Client client) throws RemoteException;

    public void clientInscrit(Notify info) throws RemoteException;

    public boolean peutReserver(Notify n, Reservation rv) throws RemoteException;
     Client getClient(String username)throws RemoteException;

    public int annulerReservation(int codeReservation,Notify n)throws RemoteException;

  
}
