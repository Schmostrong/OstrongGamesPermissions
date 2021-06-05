package og.administration.permissions.utils;

public class OstrongGamesPermission {
    private String permission;
    private String plugin;

    public OstrongGamesPermission(String permission, String plugin) {
        this.permission = permission;
        this.plugin = plugin;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getPlugin() {
        return plugin;
    }

    public void setPlugin(String plugin) {
        this.plugin = plugin;
    }
}
