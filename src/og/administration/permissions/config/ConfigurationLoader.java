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
    private String wrong_parameters_count;
    private String parameter_value_insufficient;
    private String group_add_successful;
    private String group_already_exists;
    private String group_permission_add_successful;
    private String group_already_has_permission;
    private String player_permission_add_successful;
    private String player_already_has_permission;
    private String group_remove_not_existing;
    private String group_remove_existing;
    private String group_permission_add_not_existing;

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

    /**
     * Function is used to return a displayed message from the config
     * @return - type: String; Represents a message that is displayed by the plugin
     */
    public String getWrong_parameters_count() {
        return wrong_parameters_count;
    }

    /**
     * Function is used to return a displayed message from the config
     * @return - type: String; Represents a message that is displayed by the plugin
     */
    public String getParameter_value_insufficient() {
        return parameter_value_insufficient;
    }

    /**
     * Function is used to return a displayed message from the config
     * @return - type: String; Represents a message that is displayed by the plugin
     */
    public String getGroup_add_successful() {
        return group_add_successful;
    }

    /**
     * Function is used to return a displayed message from the config
     * @return - type: String; Represents a message that is displayed by the plugin
     */
    public String getGroup_already_exists() {
        return group_already_exists;
    }

    /**
     * Function is used to return a displayed message from the config
     * @return - type: String; Represents a message that is displayed by the plugin
     */
    public String getGroup_permission_add_successful() {
        return group_permission_add_successful;
    }

    /**
     * Function is used to return a displayed message from the config
     * @return - type: String; Represents a message that is displayed by the plugin
     */
    public String getGroup_already_has_permission() {
        return group_already_has_permission;
    }

    /**
     * Function is used to return a displayed message from the config
     * @return - type: String; Represents a message that is displayed by the plugin
     */
    public String getPlayer_permission_add_successful() {
        return player_permission_add_successful;
    }

    /**
     * Function is used to return a displayed message from the config
     * @return - type: String; Represents a message that is displayed by the plugin
     */
    public String getPlayer_already_has_permission() {
        return player_already_has_permission;
    }

    /**
     * Function is used to return a displayed message from the config
     * @return - type: String; Represents a message that is displayed by the plugin
     */
    public String getGroup_remove_not_existing() {
        return group_remove_not_existing;
    }

    /**
     * Function is used to return a displayed message from the config
     * @return - type: String; Represents a message that is displayed by the plugin
     */
    public String getGroup_remove_existing() {
        return group_remove_existing;
    }

    /**
     * Function is used to return a displayed message from the config
     * @return - type: String; Represents a message that is displayed by the plugin
     */
    public String getGroup_permission_add_not_existing() {
        return group_permission_add_not_existing;
    }
}
