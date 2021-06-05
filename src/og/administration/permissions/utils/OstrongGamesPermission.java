package og.administration.permissions.utils;

/**
 * The class OstrongGamesPermission defines a single permission on the minecraft server
 * A permissions consists of a String that holds the actual permission and a second One
 * that defines the plugin, this permission belongs to
 *
 * @author OstrongDev
 * @version 1.0
 * @since 2021-06-05
 */
public class OstrongGamesPermission {
    private String permission;
    private String plugin;

    /**
     * Constructor that is used to create a new OstrongGamesPermission object
     * @param permission - type: String; Holds the actual permission that should be granted
     * @param plugin - type: String; Holds the name of the plugin the granted permission belongs to
     */
    public OstrongGamesPermission(String permission, String plugin) {
        this.permission = permission;
        this.plugin = plugin;
    }

    /**
     * Function returns the actual name of the permission
     * @return - type: String; Actual permission
     */
    public String getPermission() {
        return permission;
    }

    /**
     * Function is used to set the value of the actual permission
     * @param permission - type: String; Holds the value of the permission
     */
    public void setPermission(String permission) {
        this.permission = permission;
    }

    /**
     * Function returns the name of the plugins this permission belongs to
     * @return - type: String; Plugin name this permission belongs to
     */
    public String getPlugin() {
        return plugin;
    }

    /**
     * Function is used to set the name of the plugin this permission belongs to
     * @param plugin - type: String; Name of the plugin this permission belongs to
     */
    public void setPlugin(String plugin) {
        this.plugin = plugin;
    }
}
