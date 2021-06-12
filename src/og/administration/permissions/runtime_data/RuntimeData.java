package og.administration.permissions.runtime_data;

import og.administration.permissions.main.OstrongGamesPermissionsMain;
import og.administration.permissions.utils.OstrongGamesGroup;
import og.administration.permissions.utils.OstrongGamesPermission;
import og.administration.permissions.utils.OstrongGamesUser;

import java.sql.SQLException;
import java.util.*;

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

    /**
     * Function is used to create a new permission that is added to the runtime data
     * @param permission - type: String; Actual permission that should be created
     * @param plugin - type: String; Plugin this permission belongs to
     * @return
     */
    public OstrongGamesPermission createRuntimePermission(String permission, String plugin){
        OstrongGamesPermission newPerm = new OstrongGamesPermission(permission, plugin);
        runtimePermissions.add(newPerm);
        return newPerm;
    }

    /**
     * Function is used to create a new user used that is added to the runtime data
     * @param user - type: UUID; Represents the unique identifier of a minecraft player
     * @param groupName - type; String; Represents the name of the group this user belongs to
     * @return
     */
    public OstrongGamesUser createRuntimeUser(UUID user, String groupName){
        OstrongGamesUser runtimeUser = new OstrongGamesUser(user, groupName);
        runtimeUsers.add(runtimeUser);
        return runtimeUser;
    }

    /**
     * Function is used to create a new group that is added to the runtime data
     * @param groupName - type: String; Represents the name of the created group
     * @param groupPrefix - type: String; Represents the prefix of the created group
     * @return
     */
    public OstrongGamesGroup createRuntimeGroup(String groupName, String groupPrefix){
        OstrongGamesGroup newGroup = new OstrongGamesGroup(groupName, groupPrefix);
        runtimeGroups.add(newGroup);
        return newGroup;
    }

    /**
     * Function is used to remove a user from the runtime data
     * @param uuid - type: UUID; Represents the unique identifier of a minecraft player
     */
    public void removeRuntimeUser(UUID uuid){
        OstrongGamesUser user = null;
        for(OstrongGamesUser runtimeUser : runtimeUsers){
            if(runtimeUser.getUUID() == uuid){
                user = runtimeUser;
            }
        }
        runtimeUsers.remove(user);
    }

    /**
     * Function is used to remove a group from the runtime data, when the user leaving is the last member that is currently online
     * @param uuid - type: UUID; Represents the unique identifier for a minecraft player
     * @return - type: OstrongGamesGroup; Return the group that is removed from the runtime data;
     */
    public OstrongGamesGroup removeRuntimeGroupIfLastMember(UUID uuid){
        OstrongGamesGroup group = null;
        for(OstrongGamesUser runtimeUser : runtimeUsers){
            if(runtimeUser.getUUID() == uuid){
                group = runtimeUser.getUserGroup();
            }
        }

        for(OstrongGamesUser user : runtimeUsers){
            if(user.getUserGroup() == group){
                group = null;
            }

            if(group != null){
                runtimeGroups.remove(group);
            }
        }

        return group;
    }

    /**
     * Function is used to remove group on command and move users to the default group
     * @param groupName
     */
    public void removeGroupIfLoadedAndMoveUsers(String groupName){
        OstrongGamesGroup group = null;
        for(OstrongGamesGroup loadedGroup : runtimeGroups){
            if (loadedGroup.getGroupName().equals(groupName)){
                group = loadedGroup;
            }
        }

        if(group != null){
            runtimeGroups.remove(group);

            List<OstrongGamesUser> users = new ArrayList<>();
            for(OstrongGamesUser loadedUser : runtimeUsers){
                if(loadedUser.getUserGroup() == group){
                    users.add(loadedUser);
                }
            }

            if(getRuntimeGroup(OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getDefault_group()) == null){
                try {
                    OstrongGamesGroup loadedGroup = OstrongGamesPermissionsMain.getDAO().loadDefaultGroup(OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getDefault_group());
                    for(OstrongGamesUser user : users){
                        user.setUserGroup(loadedGroup);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    public void setRuntimeUserGroup(UUID user, OstrongGamesGroup group){
        OstrongGamesUser runtimeUser = getRuntimeUser(user);

        if(runtimeUser != null){
            runtimeUser.setUserGroup(group);
        }
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

    /**
     * Function is used to check whether a user is permitted to do a specific action
     * @param user - type: OstrongGamesUser; Represents the user that should be checked
     * @param permission - type: String; Permission that should be checked
     * @return - type: boolean; Indicates, whether the user is permitted
     */
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
