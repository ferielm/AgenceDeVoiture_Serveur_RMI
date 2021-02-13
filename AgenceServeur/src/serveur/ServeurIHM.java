package serveur;

import agencecommun.Client;
import agencecommun.Historique;
import agencecommun.Vehicule;
import agencecommun.InterfaceServeur;
import agencecommun.Notify;
import agencecommun.Reservation;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ServeurIHM extends javax.swing.JFrame implements InterfaceServeur {

    Connection conn;
    PreparedStatement pst = null;
    ResultSet rs = null;

    HashMap<Integer, Vehicule> HV;
    HashMap<Integer, Reservation> HR1;
    HashMap<Integer, Client> HC;

    static Client client;
    Notify promo = null;
    String format = "dd/MM/yy";
    SimpleDateFormat formater;
    Date date;
    DefaultTableModel tableur;
    DefaultTableModel tableur1;
    DefaultTableModel tableur2;
    Object[] tab;
    Object[] tab1;
    Object[] tab2;

    protected static ArrayList<Notify> threadList = new ArrayList<Notify>();
    protected static ArrayList<Historique> liste2 = new ArrayList<>();
    protected static ArrayList<Vehicule> liste = new ArrayList<>();
    protected static HashMap<Integer, Historique> liste22 = new HashMap<>();

    /**
     * Creates new form Serveur
     */
    public ServeurIHM() {

        conn = new Connect().connectDB();
        initIHM();
    }

    private void initIHM() {
        initComponents();
        tableur = (DefaultTableModel) listev.getModel();
        tab = new Object[8];
        try {
            liste = this.afficherToutParTri("marque", "asc");
           
            formater = new SimpleDateFormat(format);
            date = new Date();

            //System.out.println(formater.format(date));
        } catch (RemoteException ex) {

        }
    }

    void affichageVoiture() throws RemoteException {
        tableur = (DefaultTableModel) listev.getModel();
        Object[] tab = new Object[8];
        viderTable(listev);
        HV = this.afficherToutParTri1("marque", "asc");
        
       
        Set cles = HV.keySet();
        Iterator it = cles.iterator();
        while (it.hasNext()) {
            //listv.add(v.getCode() + " " + v.getMarque());
            // tu peux typer plus finement ici

            Vehicule v = HV.get(it.next()); // tu peux typer plus finement ici
           
            tab[0] = v.getCode();
            tab[1] = v.getMarque();
            tab[2] = v.getCouleur();
            tab[3] = v.getCarburant();
            tab[4] = v.getKilometrage();
            tab[5] = v.getPrix();
            tab[6] = v.isDisponible();
            tab[7] = v.getPromo();
            tableur.addRow(tab);

        }
    }

    void affichageReservation() {
        tableur1 = (DefaultTableModel) listeR.getModel();
        tab1 = new Object[6];
        viderTable(listeR);
        HR1 = affichage();
        Set cles = HR1.keySet();
        Iterator it = cles.iterator();
        while (it.hasNext()) {
            //listv.add(v.getCode() + " " + v.getMarque());
            // tu peux typer plus finement ici

            Reservation rv = HR1.get(it.next());
            tab1[0] = rv.getCode();
            tab1[1] = rv.getClientUsername();
            tab1[2] = rv.getVoiture().getCode();
            tab1[3] = rv.getDateDe();
            tab1[4] = rv.getDateA();
            tab1[5] = rv.getPrix();
            tableur1.addRow(tab1);

        }
    }

    void affichageHistorique() {
        tableur2 = (DefaultTableModel) listeH.getModel();
        tab2 = new Object[6];
        viderTable(listeH);
        liste22 = bddManageur.listeHistorique();
        Set cles = liste22.keySet();
        Iterator it = cles.iterator();
        while (it.hasNext()) {
            //listv.add(v.getCode() + " " + v.getMarque());
            // tu peux typer plus finement ici

            Historique rv = liste22.get(it.next());
            tab2[0] = rv.getCode();
            tab2[1] = rv.getClientUsername();
            tab2[2] = rv.getVoiture().getCode();
            tab2[3] = rv.getDateDe();
            tab2[4] = rv.getDateA();
            tab2[5] = rv.getRenduLe();

            tableur2.addRow(tab2);

        }
    }

    void viderTable(JTable list) {
        int size = list.getModel().getRowCount();
        for (int j = size - 1; j >= 0; j--) {
            ((DefaultTableModel) list.getModel()).removeRow(j);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        jPanel1 = new javax.swing.JPanel();
        msg1 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        listev = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        listeR = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        listeH = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();
        msg2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        ajoutervv = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();

        jMenu1.setText("jMenu1");

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Agence de voiture(serveur)");
        setBackground(new java.awt.Color(204, 204, 255));
        setBounds(new java.awt.Rectangle(250, 140, 0, 0));
        setMinimumSize(new java.awt.Dimension(400, 302));
        setResizable(false);
        setSize(new java.awt.Dimension(800, 500));

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));
        jPanel1.setMinimumSize(new java.awt.Dimension(400, 259));
        jPanel1.setName(""); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        msg1.setForeground(new java.awt.Color(204, 0, 0));
        jPanel1.add(msg1, new org.netbeans.lib.awtextra.AbsoluteConstraints(599, 40, 168, -1));

        jButton3.setBackground(new java.awt.Color(0, 102, 102));
        jButton3.setText("Terminer réservation");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 320, 191, -1));

        jTabbedPane1.setBackground(new java.awt.Color(0, 153, 153));
        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        jScrollPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane1MouseClicked(evt);
            }
        });

        listev.setBackground(new java.awt.Color(204, 204, 204));
        listev.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Code", "Marque", "Couleur", "Carburant", "Kilometrage", "Prix", "Disponible", "Promotion"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Boolean.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        listev.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listevMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(listev);

        jTabbedPane1.addTab("Vehicule", jScrollPane1);

        jScrollPane2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane2MouseClicked(evt);
            }
        });

        listeR.setBackground(new java.awt.Color(204, 204, 204));
        listeR.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "code", "Client", "Code voiture", "Date début", "Date fin", "prix"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(listeR);

        jTabbedPane1.addTab("Réservations", jScrollPane2);

        listeH.setBackground(new java.awt.Color(204, 204, 204));
        listeH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "code", "Client", "Code voiture", "Date début", "Date fin", "Rendu le"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(listeH);

        jTabbedPane1.addTab("Historique", jScrollPane3);

        jPanel1.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 747, 230));

        jButton6.setBackground(new java.awt.Color(0, 102, 102));
        jButton6.setText("Vehicule en pane");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 320, 190, -1));

        msg2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        msg2.setForeground(new java.awt.Color(204, 0, 0));
        msg2.setText("          ");
        jPanel1.add(msg2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 20, 566, -1));

        jButton1.setBackground(new java.awt.Color(0, 102, 102));
        jButton1.setText("Vehicule disponible");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 320, 177, -1));

        ajoutervv.setText("Paramètre");

        jMenu2.setText("Ajouter");

        jMenuItem7.setText("Vehicule");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem7);

        jMenuItem9.setText("Promotion");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem9);

        ajoutervv.add(jMenu2);

        jMenu4.setText("Supprimer");

        jMenuItem8.setText("Vehicule");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem8);

        jMenuItem10.setText("Promotion");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem10);

        ajoutervv.add(jMenu4);

        jMenu5.setText("Modifier");
        jMenu5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu5ActionPerformed(evt);
            }
        });

        jMenuItem2.setText("Vehicule");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem2);

        ajoutervv.add(jMenu5);

        jMenuBar1.add(ajoutervv);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 841, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        msg2.setText("");

        int row = listeR.getSelectedRow();
        //int code = (int) tableur.getValueAt(row, 0);
        // System.out.println(listv.getSelectedItem());

        //   if (listv.getSelectedItem() != null) {
        if (row != -1) {
            int code = (int) tableur1.getValueAt(row, 0);

            try {
                if (!bddManageur.terminerReservation(code)) {
                    JOptionPane.showMessageDialog(null, "Le client a dépassé le délai prévu !pénalité de 1000 Da");

                } else {
                    JOptionPane.showMessageDialog(null, "reservation terminer !");
                }
                tableur1.removeRow(row);
            } catch (ParseException ex) {
                Logger.getLogger(ServeurIHM.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            msg2.setText("Selectionnez une ligne ");
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        msg2.setText("");
        int row = listev.getSelectedRow();
        //int code = (int) tableur.getValueAt(row, 0);
        // System.out.println(listv.getSelectedItem());

        //   if (listv.getSelectedItem() != null) {
        if (row != -1) {
            int code = (int) tableur.getValueAt(row, 0);
            if (!bddManageur.vehiculeEnPane(code)) {
                JOptionPane.showMessageDialog(null, "modifiication échouée");
            } else {
                tableur.setValueAt(false, row, 6);
                tableur.setValueAt(0, row,7);
               
            }

        } else {
            msg2.setText("selectionnez une ligne !");
        }

    }//GEN-LAST:event_jButton6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        new AjouterVehicule(this).setVisible(true);
        //  this.setVisible(false);

    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        msg2.setText("");
        int row = listev.getSelectedRow();
        //int code = (int) tableur.getValueAt(row, 0);
        // System.out.println(listv.getSelectedItem());

        //   if (listv.getSelectedItem() != null) {
        if (row != -1) {
            int code = (int) tableur.getValueAt(row, 0);

            Vehicule v = HV.get(code);
            new AjouterPromo(this, v, row).setVisible(true);

        } else {
            msg2.setText("selectionnez une ligne !");
        }
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        msg2.setText("");
        int row = listev.getSelectedRow();
        //int code = (int) tableur.getValueAt(row, 0);
        // System.out.println(listv.getSelectedItem());

        //   if (listv.getSelectedItem() != null) {
        if (row != -1) {
            int code = (int) tableur.getValueAt(row, 0);

            if (!bddManageur.supprimerVehicule(code)) {
                JOptionPane.showMessageDialog(null, "code de voiture incorrecte !");

            } else {
                JOptionPane.showMessageDialog(null, "Voiture supprimer !");
                tableur.removeRow(row);
                //new ServeurIHM().setVisible(true);
                //this.dispose();

            }
        } else {
            msg2.setText("Selectionnez une ligne ");
        }

    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        msg2.setText("");
        int row = listev.getSelectedRow();
        //int code = (int) tableur.getValueAt(row, 0);
        // System.out.println(listv.getSelectedItem());

        //   if (listv.getSelectedItem() != null) {
        if (row != -1) {
            int code = (int) tableur.getValueAt(row, 0);
            suppPromo(code);
            JOptionPane.showMessageDialog(null, "Promotion est supprimée avec succés ! ");
            tableur.setValueAt(0, row, 7);

        } else {
            msg2.setText("selectionnez une ligne !");
        }
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenu5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu5ActionPerformed

    }//GEN-LAST:event_jMenu5ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        msg2.setText("");
        int row = listev.getSelectedRow();
        //int code = (int) tableur.getValueAt(row, 0);
        // System.out.println(listv.getSelectedItem());

        //   if (listv.getSelectedItem() != null) {
        if (row != -1) {
            int code = (int) tableur.getValueAt(row, 0);
            // String[] code = listv.getSelectedItem().split(" ");
            Vehicule v = HV.get(code);
            new ModifierVehicule(this, v, row).setVisible(true);

        } else {
            msg2.setText("selectionnez une ligne !");
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        msg2.setText("");
        int row = listev.getSelectedRow();
        //int code = (int) tableur.getValueAt(row, 0);
        // System.out.println(listv.getSelectedItem());

        //   if (listv.getSelectedItem() != null) {
        if (row != -1) {
            int code = (int) tableur.getValueAt(row, 0);
            if (!bddManageur.vehiculeDispo(code)) {
                JOptionPane.showMessageDialog(null, "modifiication échouée");
            } else {
                tableur.setValueAt(true, row, 6);
            }

        } else {
            msg2.setText("selectionnez une ligne !");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked

    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void jScrollPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseClicked

    }//GEN-LAST:event_jScrollPane1MouseClicked

    private void jScrollPane2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane2MouseClicked

    }//GEN-LAST:event_jScrollPane2MouseClicked

    private void listevMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listevMouseClicked

    }//GEN-LAST:event_listevMouseClicked

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
        // TODO add your handling code here:
        switch (jTabbedPane1.getSelectedIndex()) {
            case 0: {
                try {
                    affichageVoiture();
                } catch (RemoteException ex) {
                    Logger.getLogger(ServeurIHM.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;
            case 1: {

                affichageReservation();

            }
            break;
            case 2: {

                affichageHistorique();

            }
            break;

        }
    }//GEN-LAST:event_jTabbedPane1StateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ServeurIHM.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ServeurIHM.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ServeurIHM.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ServeurIHM.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ServeurIHM objet = new ServeurIHM();
                InterfaceServeur objetexport;
                try {
                    objetexport = (InterfaceServeur) UnicastRemoteObject.exportObject(objet, 1099);
                    // System.out.println(objetexport);
                    Registry registry = LocateRegistry.createRegistry(1099);
                    //System.out.println(registry);
                    registry.rebind("serveur", objetexport);

                } catch (RemoteException ex) {
                    Logger.getLogger(ServeurIHM.class
                            .getName()).log(Level.SEVERE, null, ex);
                }

                new ServeurIHM().setVisible(true);
            }
        });
    }

    @Override
    public void ajouterVihecule(String marque, String couleur, String carburant, Double kilometrage, Double prix, byte[] photo) throws RemoteException {

        int code = bddManageur.ajouterVihecule(marque, couleur, carburant, kilometrage, prix, photo);
        if (code != -1) {
            tab[0] = code;
            tab[1] = marque;
            tab[2] = couleur;
            tab[3] = carburant;
            tab[4] = kilometrage;
            tab[5] = prix;
            tab[6] = true;
            tab[7] = 0;
            tableur.addRow(tab);
        } else {
            JOptionPane.showMessageDialog(null, "la marque doit être unique !");
        }

    }

    @Override
    public void editerVehicule(Vehicule v, int row) throws RemoteException {
        bddManageur.editerVehicule(v);
        tab[0] = v.getCode();
        tab[1] = v.getMarque();
        tab[2] = v.getCouleur();
        tab[3] = v.getCarburant();
        tab[4] = v.getKilometrage();
        tab[5] = v.getPrix();
        tab[6] = v.isDisponible();
        tab[7] = v.getPromo();

        tableur.removeRow(row);
        tableur.addRow(tab);
        HV.get(v.getCode()).setVehicule(v);

    }

    public void callMeBack(Notify n) {
        promo = n;
        threadList.add(promo);

    }

    void suppPromo(int code) {
        bddManageur.suppPromo(code);

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu ajoutervv;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable listeH;
    private javax.swing.JTable listeR;
    private javax.swing.JTable listev;
    private javax.swing.JLabel msg1;
    private javax.swing.JLabel msg2;
    // End of variables declaration//GEN-END:variables

    @Override
    public void rejoindre(Notify n) throws RemoteException {
        // threadList.add(n);
        n.messageConnexion(n.getClient());
    }

    @Override
    public void ajouterPromo(Vehicule voiture, int promo, int row) throws RemoteException {
        bddManageur.ajouterPromo(voiture, promo, threadList);
        tableur.setValueAt(promo, row, 7);
    }

    @Override
    public double connexion(Client client) throws RemoteException {
        return bddManageur.connexion(client);

    }

    public Client getClient(String username) throws RemoteException {
        return bddManageur.getClient(username);
    }

    @Override
    public void quitter(Notify n) throws RemoteException {
        n.messageDeconnexion(n.getClient());
    }

    @Override
    public String test() throws RemoteException {
        return "hello there";
    }

    @Override
    public boolean inscription(Client client) throws RemoteException {
        return bddManageur.inscription(client);
    }

    public void clientInscrit(Notify n) throws RemoteException {
        n.messageInscription();
    }

    @Override
    public HashMap<Integer, Reservation> reservationsVoiture(Vehicule voiture) throws RemoteException {
        return bddManageur.reservationsVoiture(voiture);
    }

    @Override
    public boolean peutReserver(Notify n, Reservation rv) throws RemoteException {
        try {
            return bddManageur.peutReserver(n, rv);
        } catch (ParseException ex) {
            return false;
        }

    }

    protected HashMap<Integer, Reservation> affichage() {
        return bddManageur.listeReservations();
    }

    @Override
    public boolean modifierSoldeClient(String username, double nvSolde) throws RemoteException {
        return bddManageur.modifierSoldeClient(username, nvSolde);
    }

    @Override
    public ArrayList<Vehicule> afficherToutParTri(String triPar, String orderBy) throws RemoteException {
        return bddManageur.afficherToutParTri(triPar, orderBy);
    }
    @Override
    public HashMap<Integer,Vehicule>  afficherToutParTri1(String triPar, String orderBy) throws RemoteException {
        return bddManageur.afficherToutParTri1(triPar, orderBy);
    }

    @Override
    public HashMap<Integer, Reservation> reservationsClient(String username) throws RemoteException {

        return bddManageur.reservationsClient(username);
    }

    @Override
    public int annulerReservation(int codeReservation, Notify n) {
        try {
            return bddManageur.annulerReservation(codeReservation, n);
        } catch (RemoteException ex) {
            Logger.getLogger(ServeurIHM.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ServeurIHM.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

}
