package og.administration.permissions.config;

/**
 * This class is used to load the configuration from the config.yml file
 */
public class ConfigurationLoader {

    /**
     * Database settings
     */
    private String dataBaseUrl;
    private int dataBasePort;
    private String dataBaseName;

    /**
     * Ingame settings
     */
    private String ingamePrefix;

    /**
     * Default constructor that fills the available fields with default values
     */
    public ConfigurationLoader(){
        this("localhost", 3306, "OstrongGamesPermissions", "§7[§3OGPermissions§7] >>");
    }

    /**
     * Constructor, that is used to initialize a ConfigurationLoader instance
     * @param dataBaseUrl - Represents the URL of the database to connect to
     * @param dataBasePort - Represents the port of the database to connect to
     * @param dataBaseName - Represents the name of the database that is to be used
     * @param ingamePrefix - Represents the prefix that is handled when the plugin delivers output
     */
    public ConfigurationLoader(String dataBaseUrl, int dataBasePort, String dataBaseName, String ingamePrefix){
        this.dataBaseUrl = dataBaseUrl;
        this.dataBasePort = dataBasePort;
        this.dataBaseName = dataBaseName;
        this.ingamePrefix = ingamePrefix;
    }

    /**
     * Function is used to return the URL of the database that the plugin connects to
     * @return
     */
    public String getDataBaseUrl() {
        return dataBaseUrl;
    }

    /**
     * Function is used to return the port of the database that the plugin connects to
     * @return
     */
    public int getDataBasePort() {
        return dataBasePort;
    }

    /**
     * Function is used to return the name of the database the plugin connects to
     * @return
     */
    public String getDataBaseName() {
        return dataBaseName;
    }

    /**
     * Function is used to return the value of the prefix that is handled when the plugin delivers output
     * @return
     */
    public String getIngamePrefix() {
        return ingamePrefix;
    }
}
