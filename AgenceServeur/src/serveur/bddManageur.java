/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur;

import agencecommun.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Feriel Mokhtari
 */
public class bddManageur {

    static Connection conn = new Connect().connectDB();
    static PreparedStatement pst = null;
    static ResultSet rs = null;
    static PreparedStatement pst1 = null;
    static ResultSet rs1 = null;
    static PreparedStatement pst2 = null;
    static ResultSet rs2 = null;
    static String format = "dd/MM/yy";
    static SimpleDateFormat formater = new SimpleDateFormat(format);
    static Date date = new Date();
    private static ArrayList<Notify> threadList = new ArrayList<Notify>();

    protected static Reservation reservation(int codeReservation) {

        String sql = "select * from reservation where code = '" + codeReservation + "'";

        Reservation rv = null;
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            Vehicule voiture = null;
            while (rs.next()) {
                Vehicule v = new Vehicule(rs.getInt("code"), null, null, null, 0.0, 0.0, true);

                rv = new Reservation(rs.getInt("code"), rs.getString("username"), v,
                        rs.getString("datede"), rs.getString("datea"), rs.getDouble("prix"));

            }
        } catch (SQLException ex) {

        }
        return rv;
    }
//<editor-fold defaultstate="collapsed" desc="Table">

//</editor-fold>
    protected static boolean ajouterHistorique(Reservation rv) throws ParseException {

        String format = "yyyy-MM-dd H:mm:ss";

        java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat(format);
        java.util.Date date = new java.util.Date();

        String sql = "insert into historique (codevehicule ,username, datede , datea , daterendu)"
                + " values ('" + rv.getCode() + "' "
                + ",'" + rv.getClientUsername() + "' "
                + ",'" + rv.getDateDe() + "' "
                + ",'" + rv.getDateA() + "' "
                + ",'" + formater.format(date) + "')";

        try {
            pst = conn.prepareStatement(sql);
            pst.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ServeurIHM.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    protected static boolean verifierDelaiReservation(int code) throws ParseException {
        String sql = "select datea from reservation where code ='" + code + "'";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date datePrevue = /*sdf.parse(*/ rs.getDate("datea");
                Date maintenant = sdf.parse(sdf.format(date));

                if (maintenant.before(datePrevue) || maintenant.equals(datePrevue)) {

                    return true;
                }

            }
        } catch (SQLException ex) {

        }

        return false;
    }

    protected static boolean terminerReservation(int codeReservation) throws ParseException {

        Reservation rv = reservation(codeReservation);
        if (rv == null) {

            return false;
        } else {
            if (verifierDelaiReservation(codeReservation)) {

                if (ajouterHistorique(rv)) {
                    String sql = "delete from reservation where code ='" + codeReservation + "'";

                    try {
                        pst = conn.prepareStatement(sql);
                        pst.execute();
                        return true;
                    } catch (SQLException ex) {
                        // Logger.getLogger(ServeurIHM.class.getName()).log(Level.SEVERE, null, ex);
                        return false;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "historique n'est pas enregistrer !");
                    return false;
                }

            } else {
                try {
                    penalite(rv.getClientUsername(), 1500);
                    String sql = "delete from reservation where code ='" + codeReservation + "'";

                    try {
                        pst = conn.prepareStatement(sql);
                        pst.execute();

                    } catch (SQLException ex) {

                        return false;
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(bddManageur.class.getName()).log(Level.SEVERE, null, ex);
                }

                return false;
            }
        }
    }

    protected static void payer(Reservation rv) throws RemoteException {
        double solde = getClient(rv.getClientUsername()).getSolde();
        String sql = "update client set solde = '" + (solde - rv.getPrix()) + "' where username = '" + rv.getClientUsername() + "'";
        try {
            pst = conn.prepareStatement(sql);
            pst.execute();

        } catch (SQLException ex) {
            Logger.getLogger(bddManageur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected static boolean peutReserver(Notify n, Reservation rv) throws RemoteException, ParseException {
        HashMap<Integer,Reservation> HR = reservationsVoiture(rv.getVoiture());
         
        Set cles = HR.keySet();
        Iterator it = cles.iterator();
        System.out.println("prix : " + rv.getPrix() + "solde avant :" + getClient(rv.getClientUsername()).getSolde());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dD = sdf.parse(rv.getDateDe());
        Date dF = sdf.parse(rv.getDateA());
     
        while (it.hasNext()) {
            //listv.add(v.getCode() + " " + v.getMarque());
            // tu peux typer plus finement ici

            Reservation v = HR.get(it.next()); // tu peux typer plus finement ici
          
            Date dDL = sdf.parse(v.getDateDe());
            Date dFL = sdf.parse(v.getDateA());

            if ((dD.after(dDL) && dD.before(dFL))
                    || (dF.after(dDL) && dF.before(dFL))
                    || (dD.before(dDL) && dF.after(dFL))) {
                return false;
            }
        }

        String sql = "insert into reservation (username,codevehicule , datede , datea,prix)values "
                + "('" + rv.getClientUsername() + "' "
                + ",'" + rv.getVoiture().getCode() + "'"
                + ",'" + rv.getDateDe() + "'"
                + ",'" + rv.getDateA() + "'"
                + ",'" + rv.getPrix() + "')";
        try {
            payer(rv);
            pst = conn.prepareStatement(sql);
            pst.execute();
            n.infoReservation(rv);

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(ServeurIHM.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    protected static HashMap<Integer, Reservation> reservationsClient(String username) throws RemoteException {

        HashMap<Integer, Reservation> HR = new HashMap<>();

        String sql = "select * from reservation where username = '" + username + "'";
        try {
            pst1 = conn.prepareStatement(sql);
            rs1 = pst1.executeQuery();
            while (rs1.next()) {

                Vehicule v = getVehicule(rs1.getInt("codevehicule"));
                Reservation rv = new Reservation(rs1.getInt("code"),
                        username,
                        v,
                        rs1.getString("datede"),
                        rs1.getString("datea"),
                        rs1.getDouble("prix"));
                HR.put(rv.getCode(), rv);

            }
        } catch (SQLException ex) {
            Logger.getLogger(ServeurIHM.class.getName()).log(Level.SEVERE, null, ex);
        }

        return HR;
    }

    protected static HashMap<Integer, Reservation> reservationsVoiture(Vehicule voiture) throws RemoteException {

        HashMap<Integer, Reservation> HR = new HashMap<>();
        String sql = "select * from reservation where codevehicule = '" + voiture.getCode() + "'";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                Reservation rv = new Reservation(rs.getInt("code"), rs.getString("username"), voiture,
                        rs.getString("datede"), rs.getString("datea"), rs.getDouble("prix"));
               HR.put(rv.getCode(), rv);

            }
        } catch (SQLException ex) {
            Logger.getLogger(ServeurIHM.class.getName()).log(Level.SEVERE, null, ex);
        }
        return HR;
    }

    protected static int codeVehicule(String marque) throws RemoteException {

        String sql = "select code from vehicule where marque = '" + marque + "'";

        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                return rs.getInt("code");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServeurIHM.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;

    }

    protected static boolean inscription(Client client) throws RemoteException {

        String sql = "insert into client (username , password , solde) values ('" + client.getUsername() + "'"
                + " ,'" + client.getPassword() + "' , '" + client.getSolde() + "')";
        try {
            pst = conn.prepareStatement(sql);
            pst.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ServeurIHM.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    protected static double connexion(Client client) throws RemoteException {

        String sql = "select * from client where username ='" + client.getUsername() + "' and password ='" + client.getPassword() + "'";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {

                return rs.getDouble("solde");

            }
        } catch (SQLException ex) {
            Logger.getLogger(ServeurIHM.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    protected static void ajouterPromo(Vehicule voiture, int promo, ArrayList<Notify> threadList) throws RemoteException {

        String sql = "update vehicule set promo = '" + promo + "' where code ='" + voiture.getCode() + "'";
        try {
            pst = conn.prepareStatement(sql);
            pst.execute();

        } catch (SQLException ex) {
            Logger.getLogger(ServeurIHM.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        for (int i = 0; i < threadList.size(); i++) {
            threadList.get(i).annoncePromotion(voiture);

        }

    }

    protected static int ajouterVihecule(String marque, String couleur, String carburant, Double kilometrage, Double prix, byte[] photo) throws RemoteException {

        String sql = "INSERT INTO vehicule(marque,couleur,carburant,kilometrage,prix,disponible,photo) VALUES "
                + "('" + marque + "' ,'" + couleur + "' ,'" + carburant + "' ,'" + kilometrage + "' ,'" + prix + "','1' ,?)";
        try {

            pst = conn.prepareStatement(sql);
            if (photo != null) {
                InputStream image = new ByteArrayInputStream(photo);
                pst.setBinaryStream(1, image, photo.length);

            } else {
                pst.setNull(1, Types.BLOB);

            }
            pst.execute();

        } catch (SQLException ex) {
            return -1;
        }
        return codeVehicule(marque);
    }

    protected static void editerVehicule(Vehicule v) throws RemoteException {
        int disponible;
        if (v.isDisponible()) {
            disponible = 1;
        } else {
            disponible = 0;
        }
        String sql = "update vehicule set marque = '" + v.getMarque() + "' ,couleur ='" + v.getCouleur() + "' ,"
                + "carburant ='" + v.getCarburant() + "' , kilometrage ='" + v.getKilometrage() + "' ,"
                + "prix ='" + v.getPrix() + "' , disponible='" + disponible + "' where code='" + v.getCode() + "'";
        try {

            pst = conn.prepareStatement(sql);
            pst.execute();

        } catch (SQLException ex) {
            Logger.getLogger(ServeurIHM.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }
     protected static ArrayList<Vehicule> afficherToutParTri(String triPar, String orderBy) throws RemoteException {
        String sql = "select * from vehicule order by " + triPar + " " + orderBy;
        ArrayList<Vehicule> listv = new ArrayList<>();

        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {

                int code = Integer.parseInt(rs.getString("code"));
                String marque = rs.getString("marque");
                String couleur = rs.getString("couleur");
                String carburant = rs.getString("carburant");
                double kilometrage = Integer.parseInt(rs.getString("kilometrage"));
                double prix = Double.parseDouble(rs.getString("prix"));
                boolean disponible = rs.getBoolean("disponible");

                InputStream photo = rs.getBinaryStream("photo");
                byte[] image = null;
                if (photo != null) {
                    image = IOUtils.toByteArray(photo);
                }
                int promo = rs.getInt("promo");

                Vehicule v = new Vehicule(code, marque, couleur, carburant, kilometrage, prix, disponible, image, promo);
               listv.add(v);
            }
        } catch (IOException | NumberFormatException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return listv;
    }
    //<editor-fold defaultstate="collapsed" desc="AfficherTout">
//</editor-fold>
    protected static HashMap<Integer,Vehicule> afficherToutParTri1(String triPar, String orderBy) throws RemoteException {
        String sql = "select * from vehicule order by " + triPar + " " + orderBy;
        HashMap<Integer,Vehicule>  HV = new HashMap<>();

        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {

                int code = Integer.parseInt(rs.getString("code"));
                String marque = rs.getString("marque");
                String couleur = rs.getString("couleur");
                String carburant = rs.getString("carburant");
                double kilometrage = Integer.parseInt(rs.getString("kilometrage"));
                double prix = Double.parseDouble(rs.getString("prix"));
                boolean disponible = rs.getBoolean("disponible");

                InputStream photo = rs.getBinaryStream("photo");
                byte[] image = null;
                if (photo != null) {
                    image = IOUtils.toByteArray(photo);
                }
                int promo = rs.getInt("promo");

                Vehicule v = new Vehicule(code, marque, couleur, carburant, kilometrage, prix, disponible, image, promo);
                HV.put(code, v);
            }
        } catch (IOException | NumberFormatException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return HV;
    }

    protected static void suppPromo(int code) {
        String sql = "update vehicule set promo = '" + 0 + "' where code ='" + code + "'";
        try {
            pst = conn.prepareStatement(sql);
            pst.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ServeurIHM.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    protected static Vehicule getVehicule(int code) throws RemoteException {
        String sql = "select *  from vehicule where code = '" + code + "'";
        Vehicule v = null;
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                v = new Vehicule(code, rs.getString("marque"), null, null, 0.0, rs.getDouble("prix"), rs.getBoolean("disponible"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(bddManageur.class.getName()).log(Level.SEVERE, null, ex);
        }
        return v;
    }

    protected static Client getClient(String username) throws RemoteException {
        String sql = "select *  from client where username = '" + username + "'";
        Client c = null;
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                c = new Client(rs.getString("username"), rs.getString("password"), rs.getDouble("solde"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(bddManageur.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }

    protected static HashMap<Integer,Reservation> listeReservations() {
        HashMap<Integer,Reservation> HR= new HashMap<>();
        String sql = "select * from reservation";
        Reservation rv = null;
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                Vehicule v = new Vehicule(rs.getInt("code"), null, null, null, 0.0, 0.0, true);
                rv = new Reservation(rs.getInt("code"), rs.getString("username"), v, rs.getString("datede"), rs.getString("datea"), rs.getDouble("prix"));
                HR.put(rv.getCode(), rv);
            }
        } catch (SQLException ex) {
            Logger.getLogger(bddManageur.class.getName()).log(Level.SEVERE, null, ex);
        }
        return HR;
    }

    protected static boolean supprimerVehicule(int code) {

        String sql = "delete from vehicule where code='" + code + "'";

        try {
            pst = conn.prepareStatement(sql);
            pst.execute();
            return true;
        } catch (SQLException ex) {
            return false;
        }

    }

    protected static boolean modifierSoldeClient(String username, double nvSolde) throws RemoteException {
        double dernierSolde = getClient(username).getSolde();
        double nSolde = dernierSolde + nvSolde;
        System.out.println("username : " + nvSolde + " solde " + nSolde);
        String sql = "update client set solde = '" + nSolde + "' where username ='" + username + "'";
        try {
            pst2 = conn.prepareStatement(sql);
            pst2.execute();
            System.out.println(" ok");
            return true;
        } catch (SQLException ex) {
            System.out.println("not ok");
            return false;
        }

    }

    protected static boolean vehiculeEnPane(int code) {
        String sql = "update vehicule set promo='" + 0 + "' and disponible='" + 0 + "' where code='" + code + "'";

        try {
            pst = conn.prepareStatement(sql);
            pst.execute();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    static boolean vehiculeDispo(int code) {
        String sql = "update vehicule set disponible='" + 1 + "' where code='" + code + "'";

        try {
            pst = conn.prepareStatement(sql);
            pst.execute();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    protected static HashMap<Integer,Historique> listeHistorique() {
        HashMap<Integer,Historique> HH = new HashMap<>();
        String sql = "select * from historique";
        Historique hi = null;
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                Vehicule v = new Vehicule(rs.getInt("code"), null, null, null, 0.0, 0.0, true);
                hi = new Historique(rs.getInt("code"), rs.getString("username"), v, rs.getString("datede"), rs.getString("datea"), rs.getString("daterendu"));
                HH.put(hi.getCode(),hi);
            }
        } catch (SQLException ex) {
            Logger.getLogger(bddManageur.class.getName()).log(Level.SEVERE, null, ex);
        }
        return HH;
    }

    private static void penalite(String clientUsername, int n) throws RemoteException {
        double solde = getClient(clientUsername).getSolde();
        String sql = "update client set solde = '" + (solde - n) + "' where username = '" + clientUsername + "'";
        try {
            pst = conn.prepareStatement(sql);
            pst.execute();
        } catch (SQLException ex) {
            Logger.getLogger(bddManageur.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    protected static int annulerReservation(int codeReservation, Notify n) throws RemoteException, ParseException {
        Reservation rv = reservation(codeReservation);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date maintenant = sdf.parse(sdf.format(date));
        Date dD = sdf.parse(rv.getDateDe());
        System.out.println("hello feryem");
        if (maintenant.before(dD)) {
            modifierSoldeClient(rv.getClientUsername(), rv.getPrix());

            penalite(rv.getClientUsername(), 1500);

            String sql = "delete from reservation where code='" + codeReservation + "'";
            try {
                pst1 = conn.prepareStatement(sql);
                pst1.execute();
                n.reservationAnnuler(rv);
                return 1;
            } catch (SQLException ex) {
                return -1;
            }

        } else {

            if (terminerReservation(codeReservation)) {
                n.reservationTerminer(rv);
                return 2;
            } else {
                n.reservationTerminerAvecPenalite(rv);
                return 3;
            }

        }

    }
}
