package og.administration.permissions.commands;

import javafx.print.PageLayout;
import og.administration.permissions.main.OstrongGamesPermissionsMain;
import og.administration.permissions.utils.OstrongGamesGroup;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class OstrongGamesPermissionsGroupRemoveCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            Player p = (Player) commandSender;

            if(OstrongGamesPermissionsMain.getRuntime().userHasPermission(OstrongGamesPermissionsMain.getRuntime().getRuntimeUser(p.getUniqueId()), "og.permissions.groupremove")){
                return proceedCommand(commandSender, command, strings);
            }else{
                commandSender.sendMessage(OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getIngamePrefix()
                        + OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getNot_permitted());
                return true;
            }
        }else{
            return proceedCommand(commandSender, command, strings);
        }
    }

    public boolean proceedCommand(CommandSender commandSender, Command command, String[] strings){
        if(command.getName().equals("oggroupremove") && strings.length == 1){
            try {
                if(OstrongGamesPermissionsMain.getRuntime().getRuntimeGroup(strings[0]) != null || OstrongGamesPermissionsMain.getDAO().checkIfGroupExists(strings[0])){
                    OstrongGamesPermissionsMain.getRuntime().removeGroupIfLoadedAndMoveUsers(strings[0]);
                    if(OstrongGamesPermissionsMain.getDAO().removeGroupFromDatabase(strings[0])){
                        commandSender.sendMessage(OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getIngamePrefix()
                                + OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getGroup_remove_existing());
                        return true;
                    }else{
                        commandSender.sendMessage(OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getIngamePrefix()
                                + OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getDatabase_error());
                        return true;
                    }
                }else{
                    commandSender.sendMessage(OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getIngamePrefix()
                            + OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getGroup_remove_not_existing());
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
                    + OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getWrong_parameters_count());
            return false;
        }
    }
}
