package og.administration.permissions.runtime_data;

import og.administration.permissions.utils.OstrongGamesGroup;
import og.administration.permissions.utils.OstrongGamesPermission;
import og.administration.permissions.utils.OstrongGamesUser;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * This class holds all variables that are processed at the runtime
 * The memory administration is configured, that every variable not necessary is moved to the database
 * and removed from the runtime data
 *
 * Every action concerning data is handled by the RuntimeData class
 * This includes saving and loading data from the database
 */
public class RuntimeData {
    private static RuntimeData runtime;
    private Set<OstrongGamesPermission> runtimePermissions;
    private Set<OstrongGamesGroup> runtimeGroups;
    private Set<OstrongGamesUser> runtimeUsers;

    public RuntimeData(){
        runtime = this;
        this.runtimePermissions = new HashSet<>();
        this.runtimeGroups = new HashSet<>();
        this.runtimeUsers = new HashSet<>();
    }

    /**
     * Function is used to return a single instance of the RuntimeData object
     * @return - type: RuntimeData; Returns the initialized RuntimeData object
     */
    public static RuntimeData getRuntime() {
        return runtime;
    }

    /**
     * Function is used to return the OstrongGamesPermissions objects, that are currently loaded
     * @return - type: Set<OstrongGamesPermission>; Returns the currently loaded OstrongGamesPermissions as a Set
     */
    public Set<OstrongGamesPermission> getRuntimePermissions() {
        return runtimePermissions;
    }

    /**
     * Function is used to query a single permission
     * @param permission - type: String; Represents the permission that is searched
     * @return - type: OstrongGamesPermission; Represents the OstrongGamesPermission object that is queried
     */
    public OstrongGamesPermission getRuntimePermission(String permission){
        for(OstrongGamesPermission perm : getRuntimePermissions()){
            if(perm.getPermission().equals(permission))
                return perm;
        }
        return null;
    }

    public OstrongGamesPermission createRuntimePermission(String permission, String plugin){
        OstrongGamesPermission newPerm = new OstrongGamesPermission(permission, plugin);
        runtimePermissions.add(newPerm);
        return newPerm;
    }

    /**
     * Function is used to return the OstrongGamesGroups objects, that are currently loaded
     * @return - type: Set<OstrongGamesGroups>; Returns the currently loaded OstrongGamesGroups as a Set
     */
    public Set<OstrongGamesGroup> getRuntimeGroups() {
        return runtimeGroups;
    }

    /**
     * Function is used to return a specific OstrongGamesGroup based on the name of the group
     * This is valuable when adding a player to a group
     * @param groupName - type: String; Represents the name of the group the user should be added
     * @return - type: OstrongGamesGroup; Represents the OstrongGamesGroup object of the group the user should be added
     */
    public OstrongGamesGroup getRuntimeGroup(String groupName){
        for(OstrongGamesGroup group : getRuntimeGroups()){
            if(group.getGroupName().equals(groupName))
                return group;
        }
        return null;
    }

    /**
     * Function is used to return the OstrongGamesUser objects, that are currently loaded
     * @return - type: Set<OstrongGamesUser>; Returns the currently loaded OstrongGamesUsers as a Set
     */
    public Set<OstrongGamesUser> getRuntimeUsers() {
        return runtimeUsers;
    }

    /**
     * Function is used to return an OstrongGamesUser object based on the uuid of the player
     * @param uuid - type: UUID; Represents the uuid the minecraft server uses to identify a player
     * @return - type: OstrongGamesUser; Represents the queried OstrongGamesUser object
     */
    public OstrongGamesUser getRuntimeUser(UUID uuid){
        for(OstrongGamesUser user : getRuntimeUsers()){
            if(user.getUUID() == uuid){
                return user;
            }
        }
        return null;
    }

    public boolean userHasPermission(OstrongGamesUser user, String permission){
        for(OstrongGamesPermission perm : user.getUserPermissions()){
            if(perm.getPermission().equals(permission)){
                return true;
            }
        }
        return false;
    }

    /**
     * Function is used to add a permission to an existing group
     * @param newPerm - type: OstrongGamesPermission; Represents the permission that should be granted
     * @param group - type: OstrongGamesGroup; Represents the group this permission should be added to
     */
    public void addPermissionToGroup(OstrongGamesPermission newPerm, OstrongGamesGroup group){
        group.addGroupPermission(newPerm);
    }
}
