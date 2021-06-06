package og.administration.permissions.main;

import og.administration.permissions.commands.OstrongGamesPermissionsGroupAddCommand;
import og.administration.permissions.commands.OstrongGamesPermissionsGroupAddPermissionCommand;
import og.administration.permissions.commands.OstrongGamesPermissionsGroupRemoveCommand;
import og.administration.permissions.config.ConfigurationLoader;
import og.administration.permissions.listener.OstrongGamesPermissionsListener;
import og.administration.permissions.runtime_data.RuntimeData;
import org.bukkit.plugin.java.JavaPlugin;

public class OstrongGamesPermissionsMain extends JavaPlugin {

    private static OstrongGamesPermissionsMain mainInstance;
    private static ConfigurationLoader configurationLoaderInstance;
    private static RuntimeData runtime;

    @Override
    public void onEnable(){
        mainInstance = this;
        configurationLoaderInstance = new ConfigurationLoader(this.getConfig().getString("database_url"),
                                                                this.getConfig().getInt("database_port"),
                                                                this.getConfig().getString("database_name"),
                                                                this.getConfig().getString("ingame_prefix"),
                                                                this.getConfig().getString("wrong_parameters_count"),
                                                                this.getConfig().getString("parameter_value_insufficient"),
                                                                this.getConfig().getString("group_add_successful"),
                                                                this.getConfig().getString("group_already_exists"),
                                                                this.getConfig().getString("group_permission_add_successful"),
                                                                this.getConfig().getString("group_already_has_permission"),
                                                                this.getConfig().getString("player_permission_add_successful"),
                                                                this.getConfig().getString("player_already_has_permission"),
                                                                this.getConfig().getString("group_remove_not_existing"),
                                                                this.getConfig().getString("group_remove_existing"),
                                                                this.getConfig().getString("group_permission_add_not_existing"),
                                                                this.getConfig().getString("new_permission_missing_plugin"),
                                                                this.getConfig().getString("not_permitted"));
        runtime = new RuntimeData();

        this.getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        getCommand("oggroupadd").setExecutor(new OstrongGamesPermissionsGroupAddCommand());
        getCommand("oggroupremove").setExecutor(new OstrongGamesPermissionsGroupRemoveCommand());
        getCommand("oggroupaddperm").setExecutor(new OstrongGamesPermissionsGroupAddPermissionCommand());
        getServer().getPluginManager().registerEvents(new OstrongGamesPermissionsListener(), this);
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
