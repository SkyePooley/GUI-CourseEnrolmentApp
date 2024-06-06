package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Holds a connection to the embedded EnrolmentDatabase, allows queries and updates to be executed
 * Follows singleton pattern, only allows one instance of itself. All instances returned by getDBManager are the same instance.
 * @author Skye Pooley
 */
public class DBManager {
    private static final String USER_NAME = "spcr";
    private static final String PASSWORD  = "spcr";
    private static final String DB_URL    = "jdbc:derby:EnrolmentDatabase;";
    private static DBManager dbManagerInstance;
    
    private Connection connection;
    
    private DBManager() {
        establishConnection();
    }

    /**
     * Get a reference to the static instance of the database manager
     * A new database connection will be opened if one does not already exist
     * @return reference to DBManager instance.
     */
    public static synchronized DBManager getDBManager() {
        if (dbManagerInstance != null)
            return dbManagerInstance;
        dbManagerInstance = new DBManager();
        return dbManagerInstance;
    }
    
    private final void establishConnection() {
        try {
            this.connection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            System.out.println("Opened Connection:  " + DB_URL);
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Closes the currently held connection to the embedded database.
     * Also nullifies the held instance of DBManager so that when getDBManager is next called-
     * -a new connection is established.
     */
    public void closeConnections() {
        if (connection != null) {
            try {
                connection.close();
                dbManagerInstance = null;
            } catch (SQLException ex) {
                System.out.println("Closing the database connection failed with the following message:");
                System.out.println(ex.getMessage());
            }
        }
    }

    /**
     * Execute the given query on the EnrolmentDatabase
     * SQL errors are caught and printed on the console.
     * @param sql SQL statement following apache derby syntax
     * @return Returns a resultset if the query was valid, null otherwise.
     */
    protected ResultSet query(String sql) throws SQLException{
        Statement statement = this.connection.createStatement();
        return statement.executeQuery(sql);
    }

    /**
     * Execute the given update statement on the EnrolmentDatabase
     * SQL errors are caught and printed on the console.
     * @param sqlStatement SQL statement following apache derby syntax
     */
    protected void update(String sqlStatement) {
        try {
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(sqlStatement);
        }
        catch (SQLException e) {
            System.out.println("Statement failed: " + sqlStatement);
            System.out.println(e.getMessage());
        }
    }

    /**
     * Executes the given statements as a batch.
     * @param statements LinkedList containing derby SQL statements
     */
    protected void updateBatch(LinkedList<String> statements) {
        try {
            Statement statement = connection.createStatement();
            for (String sqlStatement : statements) {
                statement.addBatch(sqlStatement);
            }
            statement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
