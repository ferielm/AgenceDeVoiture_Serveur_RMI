package serveur;

import java.sql.*;
import javax.swing.*;

public class Connect {
    public Connection connectDB()
    {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/agencedevoiture","root","");
            return connection;
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
    
}
