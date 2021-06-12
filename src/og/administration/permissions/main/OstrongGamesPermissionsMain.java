package og.administration.permissions.main;

import og.administration.permissions.commands.OstrongGamesPermissionsGroupAddCommand;
import og.administration.permissions.commands.OstrongGamesPermissionsGroupAddPermissionCommand;
import og.administration.permissions.commands.OstrongGamesPermissionsGroupAddUserCommand;
import og.administration.permissions.commands.OstrongGamesPermissionsGroupRemoveCommand;
import og.administration.permissions.config.ConfigurationLoader;
import og.administration.permissions.database_data.DAO;
import og.administration.permissions.listener.OstrongGamesPermissionsListener;
import og.administration.permissions.runtime_data.RuntimeData;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public class OstrongGamesPermissionsMain extends JavaPlugin {

    private static OstrongGamesPermissionsMain mainInstance;
    private static ConfigurationLoader configurationLoaderInstance;
    private static RuntimeData runtime;
    private static DAO dao;

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
                                                                this.getConfig().getString("not_permitted"),
                                                                this.getConfig().getString("database_error"),
                                                                this.getConfig().getString("default_group"),
                                                                this.getConfig().getString("default_prefix"),
                                                                this.getConfig().getString("group_user_add"));
        runtime = new RuntimeData();
        dao = new DAO(this.getConfig().getString("database_url"),
                this.getConfig().getInt("database_port"),
                this.getConfig().getString("database_name"),
                this.getConfig().getString("database_user"),
                this.getConfig().getString("database_password"));

        this.getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        try {
            dao.createDefaultGroupIfNotExisting(getConfigurationLoaderInstance().getDefault_group(), getConfigurationLoaderInstance().getDefault_prefix());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        getCommand("oggroupadd").setExecutor(new OstrongGamesPermissionsGroupAddCommand());
        getCommand("oggroupremove").setExecutor(new OstrongGamesPermissionsGroupRemoveCommand());
        getCommand("oggroupaddperm").setExecutor(new OstrongGamesPermissionsGroupAddPermissionCommand());
        getCommand("oggroupadduser").setExecutor(new OstrongGamesPermissionsGroupAddUserCommand());
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

    public static DAO getDAO(){return dao;}
}
