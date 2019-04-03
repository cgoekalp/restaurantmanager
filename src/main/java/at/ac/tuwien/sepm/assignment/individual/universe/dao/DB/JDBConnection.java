package at.ac.tuwien.sepm.assignment.individual.universe.dao.DB;

import org.h2.tools.RunScript;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBConnection {

    private Connection connection = null;

    // Eine (versteckte) Klassenvariable vom Typ der eigenen Klasse
    private static JDBConnection instance;

    // Verhindere die Erzeugung des Objektes uber andere Methoden
    private JDBConnection () {
        try{
            Class.forName("org.h2.Driver");
            this.connection = DriverManager.getConnection("jdbc:h2:~/test2", "fatmac", "12345");
            RunScript.execute(connection, new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/init.sql"))));
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }

    }

    // Eine Zugriffsmethode auf Klassenebene, welches dir '''einmal''' ein konkretes
    // Objekt erzeugt und dieses zuruckliefert.
    public static JDBConnection getInstance () {
        if (JDBConnection.instance == null) {
            JDBConnection.instance = new JDBConnection();
        }
        return JDBConnection.instance;
    }


    public Connection getConnection(){
        return this.connection;
    }
}
