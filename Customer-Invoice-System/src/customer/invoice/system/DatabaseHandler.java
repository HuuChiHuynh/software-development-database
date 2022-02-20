package customer.invoice.system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author C00261172
 * @summary Singleton Class for database
 */
public class DatabaseHandler {
    static final String DATABASE_URL = "jdbc:mysql://localhost/Application";
    static final String DATABASE_USER = "root";
    static final String DATABASE_PASSWORD = "password";
    
    private static DatabaseHandler instance = null;
    private Connection connection;
    private PreparedStatement pstat;
    
    public static DatabaseHandler getInstance() {
        if (instance == null) { // Only one DatabaseHandler is created
            try {
                String className = "com.mysql.cj.jdbc.Driver";
                Class.forName(className);
                System.out.println("Load Success");
            } catch (ClassNotFoundException ex) {
                System.out.println("Error in loading jdbc Driver");
                System.out.println(ex.getMessage());
            }
            instance = new DatabaseHandler();
        }
        return instance;
    }
    
    /**
     * @summary Prevent creating databaseHandler outside of class
     */
    private DatabaseHandler() {
        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean insert(String query, Object[] args) {
        int entriesCreated = 0;
        try {
            //sqlTable + " (" + sqlParameters[0] + ") VALUES (" + sqlParameters[1]
            pstat = connection.prepareStatement("INSERT INTO " + query);
            for (int i = 0; i < args.length; i++) {
                pstat.setObject(i + 1, args[i]);
            }
            entriesCreated = pstat.executeUpdate();
            if (entriesCreated > 0) {
                System.out.println(entriesCreated + " entries created.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstat != null) {
                    pstat.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return entriesCreated >= 1;
    }
    
    public List<Object> get(String query) {
        ResultSet resultSet = null;
        List<Object> objects = new ArrayList<Object>();
        try {
            pstat = connection.prepareStatement("SELECT " + query);
            resultSet = pstat.executeQuery();
            
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i <= numberOfColumns; i++) {
                    objects.add(resultSet.getObject(i));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return objects;
    }
    
    protected void finalize() { 
        try {
            if (connection != null) {
                connection.close();
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
}
