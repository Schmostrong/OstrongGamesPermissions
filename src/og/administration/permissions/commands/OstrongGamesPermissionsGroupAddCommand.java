package og.administration.permissions.commands;

import og.administration.permissions.main.OstrongGamesPermissionsMain;
import og.administration.permissions.utils.OstrongGamesGroup;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class OstrongGamesPermissionsGroupAddCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            Player p = (Player) commandSender;

            if(OstrongGamesPermissionsMain.getRuntime().userHasPermission(OstrongGamesPermissionsMain.getRuntime().getRuntimeUser(p.getUniqueId()), "og.permissions.addgroup")){
                if(command.getName().equals("oggroupadd") && strings.length == 2){
                    if(OstrongGamesPermissionsMain.getRuntime().getRuntimeGroup(strings[0]) == null){
                        try {
                            if(OstrongGamesPermissionsMain.getDAO().checkIfGroupExists(strings[0])){
                                commandSender.sendMessage(OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getIngamePrefix()
                                        + OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getGroup_already_exists());
                                return true;
                            }else{
                                OstrongGamesPermissionsMain.getRuntime().createRuntimeGroup(strings[0], strings[1]);
                                commandSender.sendMessage(OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getIngamePrefix()
                                        + OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getGroup_add_successful());
                                OstrongGamesPermissionsMain.getDAO().writeGroupToDatabase(strings[0], strings[1]);
                                return true;
                            }
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                            commandSender.sendMessage(OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getIngamePrefix()
                                    + OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getDatabase_error());
                            return true;
                        }
                    }else{
                        commandSender.sendMessage(OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getIngamePrefix()
                                + OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getGroup_already_exists());
                        return true;
                    }
                }else{
                    commandSender.sendMessage(OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getIngamePrefix()
                            + OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getWrong_parameters_count());
                    return false;
                }
            }else{
                commandSender.sendMessage(OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getIngamePrefix()
                        + OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getNot_permitted());
                return true;
            }
        }else{
            if(command.getName().equals("oggroupadd") && strings.length == 2){
                if(OstrongGamesPermissionsMain.getRuntime().getRuntimeGroup(strings[0]) == null){
                    try {
                        if(OstrongGamesPermissionsMain.getDAO().checkIfGroupExists(strings[0])){
                            commandSender.sendMessage(OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getIngamePrefix()
                                    + OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getGroup_already_exists());
                            return true;
                        }else{
                            OstrongGamesPermissionsMain.getRuntime().createRuntimeGroup(strings[0], strings[1]);
                            commandSender.sendMessage(OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getIngamePrefix()
                                    + OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getGroup_add_successful());
                            OstrongGamesPermissionsMain.getDAO().writeGroupToDatabase(strings[0], strings[1]);
                            return true;
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                        commandSender.sendMessage(OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getIngamePrefix()
                                + OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getDatabase_error());
                        return true;
                    }
                }else{
                    commandSender.sendMessage(OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getIngamePrefix()
                            + OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getGroup_already_exists());
                    return true;
                }
            }else{
                commandSender.sendMessage(OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getIngamePrefix()
                        + OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getWrong_parameters_count());
                return false;
            }
        }
    }
}
