package og.administration.permissions.database_data;

/**
 * This class is used to proceed all transactions made with the database
 * This includes the reading and writing processes on UserJoin and UserLeave
 */
public class DAO {
    private DatabaseConnection databaseConnection;

    /**
     * Constructor, that initializes the DatabaseConnection object
     * @param database_url - type: String; Represents the URL of the database to connect to
     * @param database_port - type: int; Represents the port of the database to connect to
     * @param database_name - type: String; Represents the name of the database to connect to
     * @param database_user - type: String; Represents the username of the database to connect to
     * @param database_password - type: String; Represents the password that should be used for authentication
     */
    public DAO(String database_url, int database_port, String database_name, String database_user, String database_password){
        this.databaseConnection = new DatabaseConnection(database_url, database_port, database_name, database_user, database_password);
    }


}
