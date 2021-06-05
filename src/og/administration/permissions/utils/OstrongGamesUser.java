package og.administration.permissions.utils;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class OstrongGamesUser {
    private UUID uuid;
    private Set<OstrongGamesPermission> userPermissions;

    public OstrongGamesUser(UUID uuid){
        this(uuid, new HashSet<>());
    }

    public OstrongGamesUser(UUID uuid, Set<OstrongGamesPermission> userPermissions) {
        this.uuid = uuid;
        this.userPermissions = new HashSet<>();
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public Set<OstrongGamesPermission> getUserPermissions() {
        return this.userPermissions;
    }

    public boolean addPermission(String permission, String plugin){
        return this.userPermissions.add(new OstrongGamesPermission(permission, plugin));
    }
}
