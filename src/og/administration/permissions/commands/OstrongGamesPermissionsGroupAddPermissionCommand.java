package og.administration.permissions.commands;

import og.administration.permissions.main.OstrongGamesPermissionsMain;
import og.administration.permissions.utils.OstrongGamesGroup;
import og.administration.permissions.utils.OstrongGamesPermission;
import og.administration.permissions.utils.OstrongGamesUser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OstrongGamesPermissionsGroupAddPermissionCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            Player sender = (Player) commandSender;
            OstrongGamesUser user = OstrongGamesPermissionsMain.getRuntime().getRuntimeUser(sender.getUniqueId());
            if(OstrongGamesPermissionsMain.getRuntime().userHasPermission(user, "og.permissions.addgrouppermission")){
                if(command.equals("oggroupaddperm") && strings.length >= 2){
                    OstrongGamesGroup group = OstrongGamesPermissionsMain.getRuntime().getRuntimeGroup(strings[0]);
                    if(group != null){
                        OstrongGamesPermission perm = OstrongGamesPermissionsMain.getRuntime().getRuntimePermission(strings[1]);
                        if(perm != null){
                            OstrongGamesPermissionsMain.getRuntime().addPermissionToGroup(perm, group);
                            commandSender.sendMessage(OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getIngamePrefix()
                                    + OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getGroup_permission_add_successful());
                            return true;
                        }else{
                            if(strings.length == 3){
                                OstrongGamesPermission newPerm = OstrongGamesPermissionsMain.getRuntime().createRuntimePermission(strings[1], strings[2]);
                                OstrongGamesPermissionsMain.getRuntime().addPermissionToGroup(newPerm, group);
                                commandSender.sendMessage(OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getIngamePrefix()
                                        + OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getGroup_permission_add_successful());
                                return true;
                            }else{
                                commandSender.sendMessage(OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getIngamePrefix()
                                        + OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getNew_permission_missing_plugin());
                                return true;
                            }
                        }
                        //TODO: Remove group in database
                    }else{
                        commandSender.sendMessage(OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getIngamePrefix()
                                + OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getGroup_permission_add_not_existing());
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
            if(command.equals("oggroupaddperm") && strings.length >= 2){
                OstrongGamesGroup group = OstrongGamesPermissionsMain.getRuntime().getRuntimeGroup(strings[0]);
                if(group != null){
                    OstrongGamesPermission perm = OstrongGamesPermissionsMain.getRuntime().getRuntimePermission(strings[1]);
                    if(perm != null){
                        OstrongGamesPermissionsMain.getRuntime().addPermissionToGroup(perm, group);
                        commandSender.sendMessage(OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getIngamePrefix()
                                + OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getGroup_permission_add_successful());
                        return true;
                    }else{
                        if(strings.length == 3){
                            OstrongGamesPermission newPerm = OstrongGamesPermissionsMain.getRuntime().createRuntimePermission(strings[1], strings[2]);
                            OstrongGamesPermissionsMain.getRuntime().addPermissionToGroup(newPerm, group);
                            commandSender.sendMessage(OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getIngamePrefix()
                                    + OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getGroup_permission_add_successful());
                            return true;
                        }else{
                            commandSender.sendMessage(OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getIngamePrefix()
                                    + OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getNew_permission_missing_plugin());
                            return true;
                        }
                    }
                    //TODO: Remove group in database
                }else{
                    commandSender.sendMessage(OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getIngamePrefix()
                            + OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getGroup_permission_add_not_existing());
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
