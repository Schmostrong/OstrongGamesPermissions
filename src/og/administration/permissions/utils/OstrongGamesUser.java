package og.administration.permissions.utils;

import og.administration.permissions.main.OstrongGamesPermissionsMain;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * The class OstrongGamesUser defines a single minecraft player that has a UUID and permissions
 *
 * @author  OstrongDev
 * @version 1.0
 * @since   2021-06-05
 */
public class OstrongGamesUser {
    private UUID uuid;
    private OstrongGamesGroup userGroup;
    private Set<OstrongGamesPermission> userPermissions;


    /**
     * Constructor used to initiate a user that has never joined the server before
     * @param uuid - Describes the unique identifier user by the minecraft server to identify the player
     */
    public OstrongGamesUser(UUID uuid){
        this(uuid, "Spieler");
    }

    /**
     * Constructor is used to initiate a user that has never joined the server before
     * @param uuid - Describes the unique identifier used by the minecraft server to identify the player
     * @param groupName - Represents the name of the group the player should be added to
     */
    public OstrongGamesUser(UUID uuid, String groupName){
        this(uuid, groupName, new HashSet<>());
    }

    /**
     * Constructor is used to initiate a user that already has joined the server before
     * Therefore, he already has existing permissions that are loaded from the database to the runtime data
     * The runtime data holds all OstrongGamesUser objects that currently are on the minecraft server
     * @param uuid - Describes the unique identifier user by the minecraft server to identify the player
     * @param userPermissions - Describes a set that holds all permissions that are granted to this user
     */
    public OstrongGamesUser(UUID uuid, String groupName, Set<OstrongGamesPermission> userPermissions) {
        this.uuid = uuid;
        this.userGroup = OstrongGamesPermissionsMain.getRuntime().getRuntimeGroup(groupName);
        this.userPermissions = new HashSet<>();
    }

    /**
     * Returns the unique identifier of this user
     * @return
     */
    public UUID getUUID() {
        return this.uuid;
    }

    /**
     * Returns a set of all permissions that are granted to this user
     * @return
     */
    public Set<OstrongGamesPermission> getUserPermissions() {
        return this.userPermissions;
    }

    /**
     * Returns the unique identifier of this user
     * @return
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Returns the group of this user
     * @return
     */
    public OstrongGamesGroup getUserGroup() {
        return userGroup;
    }

    /**
     * Function is used to grant a permission to the user
     * @param permission - type: String; holds the permission that should be added
     * @param plugin - type: String; defines the name of the plugin, the permission belongs to
     * @return - Defines wheter the permission was added
     */
    public boolean addPermission(String permission, String plugin){
        return this.userPermissions.add(new OstrongGamesPermission(permission, plugin));
    }

    /**
     * Function is used to withdraw a specific permission that was granted to the user
     * @param permission - type: String; Defines the permission that should be revoked
     * @return - Defines, wheter the permission was removed
     */
    public boolean removePermission(String permission){
        for(OstrongGamesPermission permissionObj : userPermissions){
            if(permissionObj.getPermission().equals(permission)){
                return userPermissions.remove(permissionObj);
            }
        }
        return false;
    }
}
