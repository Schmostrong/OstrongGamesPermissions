package og.administration.permissions.runtime_data;

import og.administration.permissions.utils.OstrongGamesGroup;
import og.administration.permissions.utils.OstrongGamesPermission;
import og.administration.permissions.utils.OstrongGamesUser;

import java.util.HashSet;
import java.util.Set;

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
     * Function is used to return the OstrongGamesGroups objects, that are currently loaded
     * @return - type: Set<OstrongGamesGroups>; Returns the currently loaded OstrongGamesGroups as a Set
     */
    public Set<OstrongGamesGroup> getRuntimeGroups() {
        return runtimeGroups;
    }

    /**
     * Function is used to return the OstrongGamesUser objects, that are currently loaded
     * @return - type: Set<OstrongGamesUser>; Returns the currently loaded OstrongGamesUsers as a Set
     */
    public Set<OstrongGamesUser> getRuntimeUsers() {
        return runtimeUsers;
    }
}
