package og.administration.permissions.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a group that insists of a name, a prefix is displayed in front of members in the chat
 * and a Set of permissions this group is holding
 */
public class OstrongGamesGroup {
    private String groupName;
    private String groupPrefix;
    private Set<OstrongGamesPermission> groupPermissions;

    public OstrongGamesGroup(){
        this("Spieler", "§7[§5Spieler§7] >>");
    }

    /**
     * Constructor that is used to initiate a new group based on a name and a group prefix
     * @param groupName - type: String; Represents the name of a group
     * @param groupPrefix - type: String; Represents a prefix that is displayed in chat and on scoreboard in front of group members
     */
    public OstrongGamesGroup(String groupName, String groupPrefix){
        this(groupName, groupPrefix, new HashSet<>());
    }

    /**
     * Constructor that is used to initiate a new group based on a name, a prefix and a existing Set of permissions
     * @param groupName - type: String; Represents the name of a group
     * @param groupPrefix - type: String; Represents the prefix that is displayed in chat and on scoreboard in front of group members
     * @param groupPermissions - type: Set<OstrongGamesPermission>; Represents a Set of permissions that are granted to this group
     */
    public OstrongGamesGroup(String groupName, String groupPrefix, Set<OstrongGamesPermission> groupPermissions){
        this.groupName = groupName;
        this.groupPrefix = groupPrefix;
        this.groupPermissions = groupPermissions;
    }

    /**
     * Function returns the name of a group
     * @return
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     *Function returns the prefix of a group
     * @return
     */
    public String getGroupPrefix() {
        return groupPrefix;
    }

    /**
     * Function return the Set of permissions, that are granted to this group
     * @return
     */
    public Set<OstrongGamesPermission> getGroupPermissions() {
        return groupPermissions;
    }

    /**
     * Grants a group a permission based on the permission and the plugin this permission belongs to
     * @param permission - type: String; Represents the actual permission that is to be granted
     * @param plugin - type: String; Represents the plugin this permission belongs to
     * @return - type: boolean; Represents, wheter the permission was added
     */
    public boolean addGroupPermission(String permission, String plugin){
        OstrongGamesPermission newPermission = new OstrongGamesPermission(permission, plugin);
        return this.groupPermissions.add(newPermission);
    }

    /**
     * Function grants a group a permission based on the OstrongGamesPermission object
     * @param newPermission - type: OstrongGamesPermission; Represents a OstrongGamesPermission object that is to be granted
     * @return - type: boolean; Represents, wheter the permission was added
     */
    public boolean addGroupPermission(OstrongGamesPermission newPermission){
        return this.groupPermissions.add(newPermission);
    }

    /**
     * Function is used to remove a granted permission from this group
     * @param permission - type: String; Represents the permission that should be revoked
     * @return - Returns wheter the permission was removed
     */
    public boolean removeGroupPermission(String permission){
        OstrongGamesPermission permissionObject = null;
        for(OstrongGamesPermission permissionObj : this.groupPermissions){
            if(permissionObj.getPermission().equals(permission))
                permissionObject = permissionObj;

        }
        if(permissionObject != null)
            return this.groupPermissions.remove(permissionObject);
        return false;
    }
}
