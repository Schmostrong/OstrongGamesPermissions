package og.administration.permissions.commands;

import og.administration.permissions.main.OstrongGamesPermissionsMain;
import og.administration.permissions.utils.OstrongGamesGroup;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class OstrongGamesPermissionsGroupAddCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(command.equals("oggroupadd") && strings.length == 2){
            if(OstrongGamesPermissionsMain.getRuntime().getRuntimeGroup(strings[0]) == null){
                OstrongGamesGroup newGroup = new OstrongGamesGroup(strings[0], strings[1]);
                //TODO: Create group in database
            }else{
                commandSender.sendMessage(OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getIngamePrefix()
                                        + OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getGroup_already_exists());
            }
        }else{
            commandSender.sendMessage(OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getIngamePrefix()
                                        + OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getWrong_parameters_count());
            return false;
        }
        return false;
    }
}
