package og.administration.permissions.main;

import org.bukkit.plugin.java.JavaPlugin;

public class OstrongGamesPermissionsMain extends JavaPlugin {

    private static OstrongGamesPermissionsMain mainInstance;

    @Override
    public void onEnable(){
        mainInstance = this;
        getServer().getPluginManager().registerEvents(null, this);
    }

    @Override
    public void onDisable(){

    }

    public OstrongGamesPermissionsMain getMainInstance(){
        return mainInstance;
    }
}
