package og.administration.permissions.database_data;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class is used to establish a connection to the database this plugin is meant to use
 */
public class DatabaseConnection {
    private String database_url;
    private int database_port;
    private String database_name;
    private String database_user;
    private String database_password;
    private Connection database_connection;

    /**
     * Constructor to initialize an instance of the DatabaseConnection class
     * @param database_url - type: String; Represents the URL of the database to connect to
     * @param database_port - type: int; Represents the port of the database to connect to
     * @param database_name - type: String; Name of the database to connect to
     * @param database_user - type: String; Username that should be used for authentication
     * @param database_password - type: String; Password that should be used for authentication
     */
    public DatabaseConnection(String database_url, int database_port, String database_name, String database_user, String database_password) {
        this.database_url = database_url;
        this.database_port = database_port;
        this.database_name = database_name;
        this.database_user = database_user;
        this.database_password = database_password;
    }

    /**
     * Function returns the port of the database to connect to
     * @return - Returns the port of the database to connect to
     */
    public int getDatabase_port() {
        return database_port;
    }

    /**
     * Function returns the name of the database to connect to
     * @return - Returns the name of the database to connect to
     */
    public String getDatabase_name() {
        return database_name;
    }

    /**
     * Function returns the user the database should used for authentication
     * @return - Returns the user the database should used for authentication
     */
    public String getDatabase_user() {
        return database_user;
    }

    /**
     * Function return the password that should be used for authentication
     * @return - Returns password that should be used for authentication
     */
    public String getDatabase_password() {
        return database_password;
    }

    /**
     * Function returns the URL of the database to connect to
     * @return - Returns the URL of the database to connect to
     */
    public String getDatabase_url() {
        return database_url;
    }

    /**
     * Function is used to establish a connection to the database
     * @throws SQLException
     */
    public void openConnection() throws SQLException {
        if(this.database_connection != null || !this.database_connection.isClosed())
            return;

        this.database_connection = DriverManager.getConnection("jdbc:mysql://" +
                                                                    this.database_url + ":" +
                                                                    this.database_port + "/" +
                                                                    this.database_name, this.database_user, this.database_password);
    }

    /**
     * Function returns the connection object
     * @return - Returns the connection object
     */
    public Connection getDatabase_connection() {
        return database_connection;
    }
}
