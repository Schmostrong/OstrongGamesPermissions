package og.administration.permissions.commands;

import og.administration.permissions.main.OstrongGamesPermissionsMain;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class OstrongGamesPermissionsGroupAddPermissionCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(command.equals("oggroupaddperm") && strings.length == 2){
            if(OstrongGamesPermissionsMain.getRuntime().getRuntimeGroup(strings[0]) != null){
                //TODO: Remove group in database
            }else{
                commandSender.sendMessage(OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getIngamePrefix()
                                        + OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getGroup_permission_add_not_existing());
            }
        }else{
            commandSender.sendMessage(OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getIngamePrefix()
                                        + OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getWrong_parameters_count());
            return false;
        }
        return false;
    }
}
