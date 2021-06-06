package og.administration.permissions.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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
        //TODO: Load players data from database
    }

    /**
     * Function is used to remove the users permissions from the runtime data and write them to the database
     * @param e - type: PlayerQuitEvent; Represents the PlayerQuitEvent triggered by Spigot
     */
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e){
        Player p = e.getPlayer();
        //TODO: Remove players data from runtime data and write to database
    }
}
