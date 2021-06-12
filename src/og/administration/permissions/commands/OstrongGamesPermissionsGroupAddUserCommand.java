package og.administration.permissions.commands;

import og.administration.permissions.main.OstrongGamesPermissionsMain;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class OstrongGamesPermissionsGroupAddUserCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            Player p = (Player) commandSender;

            if(OstrongGamesPermissionsMain.getRuntime().userHasPermission(OstrongGamesPermissionsMain.getRuntime().getRuntimeUser(p.getUniqueId()), "og.permissions.useraddgroup")){
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
        if(command.getName().equals("oggroupadduser") && strings.length == 2){
            try {
                String playerName = strings[0];
                String groupName = strings[1];

                if(OstrongGamesPermissionsMain.getDAO().checkIfGroupExists(groupName)){
                    if(OstrongGamesPermissionsMain.getRuntime().getRuntimeGroup(groupName) != null){
                        for(Player p : Bukkit.getOnlinePlayers()){
                            if(p.getDisplayName().equals(playerName)){
                                OstrongGamesPermissionsMain.getRuntime().setRuntimeUserGroup(p.getUniqueId(), OstrongGamesPermissionsMain.getRuntime().getRuntimeGroup(groupName));
                                if(OstrongGamesPermissionsMain.getDAO().updatePlayersGroup(p.getUniqueId(), groupName)){
                                    commandSender.sendMessage(OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getIngamePrefix()
                                            + OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getGroup_user_add());
                                }
                                return true;
                            }
                        }
                    }else{
                        OstrongGamesPermissionsMain.getDAO().loadGroupFromDatabase(groupName);
                        for(Player p : Bukkit.getOnlinePlayers()){
                            if(p.getDisplayName().equals(playerName)){
                                OstrongGamesPermissionsMain.getRuntime().setRuntimeUserGroup(p.getUniqueId(), OstrongGamesPermissionsMain.getRuntime().getRuntimeGroup(groupName));
                                if(OstrongGamesPermissionsMain.getDAO().updatePlayersGroup(p.getUniqueId(), groupName)){
                                    commandSender.sendMessage(OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getIngamePrefix()
                                            + OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getGroup_user_add());
                                }
                                return true;
                            }
                        }
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
        return false;
    }
}
