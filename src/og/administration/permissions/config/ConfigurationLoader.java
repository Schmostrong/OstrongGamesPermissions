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
    private String new_permission_missing_plugin;
    private String not_permitted;
    private String database_error;
    private String default_group;
    private String default_prefix;
    private String group_user_add;

    public ConfigurationLoader(String dataBaseUrl, int dataBasePort, String dataBaseName, String ingamePrefix, String wrong_parameters_count, String parameter_value_insufficient, String group_add_successful, String group_already_exists, String group_permission_add_successful, String group_already_has_permission, String player_permission_add_successful, String player_already_has_permission, String group_remove_not_existing, String group_remove_existing, String group_permission_add_not_existing, String new_permission_missing_plugin, String not_permitted, String database_error, String default_group, String default_prefix, String group_user_add) {
        this.dataBaseUrl = dataBaseUrl;
        this.dataBasePort = dataBasePort;
        this.dataBaseName = dataBaseName;
        this.ingamePrefix = ingamePrefix;
        this.wrong_parameters_count = wrong_parameters_count;
        this.parameter_value_insufficient = parameter_value_insufficient;
        this.group_add_successful = group_add_successful;
        this.group_already_exists = group_already_exists;
        this.group_permission_add_successful = group_permission_add_successful;
        this.group_already_has_permission = group_already_has_permission;
        this.player_permission_add_successful = player_permission_add_successful;
        this.player_already_has_permission = player_already_has_permission;
        this.group_remove_not_existing = group_remove_not_existing;
        this.group_remove_existing = group_remove_existing;
        this.group_permission_add_not_existing = group_permission_add_not_existing;
        this.new_permission_missing_plugin = new_permission_missing_plugin;
        this.not_permitted = not_permitted;
        this.database_error = database_error;
        this.default_group = default_group;
        this.default_prefix = default_prefix;
        this.group_user_add = group_user_add;
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

    /**
     * Function is used to return a displayed message from the config
     * @return - type: String; Represents a message that is displayed by the plugin
     */
    public String getNew_permission_missing_plugin() {
        return new_permission_missing_plugin;
    }

    /**
     * Function is used to return a displayed message from the config
     * @return - type: String; Represents a message that is displayed by the plugin
     */
    public String getNot_permitted() {
        return not_permitted;
    }

    /**
     * Function is used to return a displayed message from the config
     * @return - type: String; Represents a message that is displayed by the plugin
     */
    public String getDatabase_error() {
        return database_error;
    }

    /**
     * Function is used to return a displayed message from the config
     * @return - type: String; Represents a message that is displayed by the plugin
     */
    public String getDefault_group() {
        return default_group;
    }

    /**
     * Function is used to return a displayed message from the config
     * @return - type: String; Represents a message that is displayed by the plugin
     */
    public String getDefault_prefix() {
        return default_prefix;
    }

    /**
     * Function is used to return a displayed message from the config
     * @return - type: String; Represents a message that is displayed by the plugin
     */
    public String getGroup_user_add() {
        return group_user_add;
    }
}
