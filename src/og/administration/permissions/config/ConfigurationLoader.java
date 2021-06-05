package og.administration.permissions.config;

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

    public ConfigurationLoader(){
        this("localhost", 3306, "OstrongGamesPermissions", "§7[§3OGPermissions§7] >>");
    }

    public ConfigurationLoader(String dataBaseUrl, int dataBasePort, String dataBaseName, String ingamePrefix){
        this.dataBaseUrl = dataBaseUrl;
        this.dataBasePort = dataBasePort;
        this.dataBaseName = dataBaseName;
        this.ingamePrefix = ingamePrefix;
    }

    public String getDataBaseUrl() {
        return dataBaseUrl;
    }

    public int getDataBasePort() {
        return dataBasePort;
    }

    public String getDataBaseName() {
        return dataBaseName;
    }

    public String getIngamePrefix() {
        return ingamePrefix;
    }
}
