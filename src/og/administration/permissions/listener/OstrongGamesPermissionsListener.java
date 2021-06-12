package og.administration.permissions.listener;

import og.administration.permissions.main.OstrongGamesPermissionsMain;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.SQLException;

public class OstrongGamesPermissionsListener implements Listener {
    private static OstrongGamesPermissionsListener runtimeListener;

    public OstrongGamesPermissionsListener(){
        runtimeListener = this;
    }

    /**
     * Funtion is used to load the users permission from the database to the runtime data
     * @param e - type: PlayerJoinEvent; Represents the PlayerJoinEvent triggered by Spigot
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        try {
            OstrongGamesPermissionsMain.getDAO().retrievePlayerData(p.getUniqueId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            p.sendMessage(OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getIngamePrefix()
                    + OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getDatabase_error());
        }
    }

    /**
     * Function is used to remove the users permissions from the runtime data and write them to the database
     * @param e - type: PlayerQuitEvent; Represents the PlayerQuitEvent triggered by Spigot
     */
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e){
        Player p = e.getPlayer();
        try {
            OstrongGamesPermissionsMain.getDAO().writePlayerData(p.getUniqueId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            p.sendMessage(OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getIngamePrefix()
                    + OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getDatabase_error());
        }
    }

    /**
     * Function is used to display the groups prefix in the chat
     * @param playerChatEvent
     */
    @EventHandler
    public void onPlayerChat(PlayerChatEvent playerChatEvent){
        Player player = playerChatEvent.getPlayer();
        player.setDisplayName(OstrongGamesPermissionsMain.getRuntime().getRuntimeUser(player.getUniqueId()).getUserGroup().getGroupPrefix() + player.getDisplayName());
    }

}
