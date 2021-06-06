package og.administration.permissions.main;

import og.administration.permissions.config.ConfigurationLoader;
import og.administration.permissions.runtime_data.RuntimeData;
import org.bukkit.plugin.java.JavaPlugin;

public class OstrongGamesPermissionsMain extends JavaPlugin {

    private static OstrongGamesPermissionsMain mainInstance;
    private static ConfigurationLoader configurationLoaderInstance;
    private static RuntimeData runtime;

    @Override
    public void onEnable(){
        mainInstance = this;
        configurationLoaderInstance = new ConfigurationLoader(this.getConfig().getString("database_url"), this.getConfig().getInt("database_port"), this.getConfig().getString("database_name"), this.getConfig().getString("ingame_prefix"));
        runtime = new RuntimeData();

        this.getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(null, this);
    }

    @Override
    public void onDisable(){

    }

    public static OstrongGamesPermissionsMain getMainInstance(){
        return mainInstance;
    }

    public static ConfigurationLoader getConfigurationLoaderInstance() {
        return configurationLoaderInstance;
    }

    public static RuntimeData getRuntime(){
        return runtime;
    }
}
