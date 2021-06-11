package og.administration.permissions.database_data;

import og.administration.permissions.main.OstrongGamesPermissionsMain;
import og.administration.permissions.utils.OstrongGamesGroup;
import og.administration.permissions.utils.OstrongGamesPermission;
import og.administration.permissions.utils.OstrongGamesUser;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * This class is used to proceed all transactions made with the database
 * This includes the reading and writing processes on UserJoin and UserLeave
 */
public class DAO {
    private DatabaseConnection databaseConnection;

    /**
     * Constructor, that initializes the DatabaseConnection object
     * @param database_url - type: String; Represents the URL of the database to connect to
     * @param database_port - type: int; Represents the port of the database to connect to
     * @param database_name - type: String; Represents the name of the database to connect to
     * @param database_user - type: String; Represents the username of the database to connect to
     * @param database_password - type: String; Represents the password that should be used for authentication
     */
    public DAO(String database_url, int database_port, String database_name, String database_user, String database_password){
        this.databaseConnection = new DatabaseConnection(database_url, database_port, database_name, database_user, database_password);
    }

    public void retrievePlayerData(UUID playerUUID){
        PreparedStatement ps = null;
        try{
            ps = this.databaseConnection.getDatabase_connection().prepareStatement("SELECT groupId FROM OstrongGamesUser WHERE UUID = ?");
            ps.setString(1, "" + playerUUID);
            ResultSet rs = ps.executeQuery();

            int rsLength = 0;
            while(rs.next()){
                int groupId = rs.getInt(1);
                ps = this.databaseConnection.getDatabase_connection().prepareStatement("SELECT groupName, groupPrefix, groupId FROM OstrongGamesGroup WHERE groupId = ?");
                ps.setInt(1, groupId);
                ResultSet rs2 = ps.executeQuery();

                while (rs2.next()){
                    String groupName = rs2.getString(1);
                    String groupPrefix = rs2.getString(2);

                    OstrongGamesGroup runtimeGroup = OstrongGamesPermissionsMain.getRuntime().getRuntimeGroup(groupName);
                    if(runtimeGroup != null){
                        OstrongGamesPermissionsMain.getRuntime().createRuntimeUser(playerUUID, groupName);
                    }else{
                        runtimeGroup = OstrongGamesPermissionsMain.getRuntime().createRuntimeGroup(groupName, groupPrefix);
                        ps = this.databaseConnection.getDatabase_connection().prepareStatement("SELECT permissionId FROM OstrongGamesGroupPermissions WHERE groupId = ?");
                        ps.setInt(1, groupId);
                        ResultSet rs3 = ps.executeQuery();

                        Set<Integer> permIds = new HashSet<>();
                        while(rs3.next()){
                            permIds.add(rs3.getInt(1));
                        }

                        for(Integer permId : permIds){
                            ps = this.databaseConnection.getDatabase_connection().prepareStatement("SELECT permissionName, permissionPlugin FROM OstrongGamesPermission WHERE permissionId = ?");
                            ps.setInt(1, permId);
                            ResultSet rs4 = ps.executeQuery();

                            while(rs4.next()){
                                OstrongGamesPermissionsMain.getRuntime().addPermissionToGroup(OstrongGamesPermissionsMain.getRuntime().createRuntimePermission(rs4.getString(1), rs4.getString(2)), runtimeGroup);
                            }
                        }
                    }
                }
                rsLength++;
            }

            if(rsLength == 0){
                if(OstrongGamesPermissionsMain.getRuntime().getRuntimeGroup("Spieler") == null){
                    ps = this.databaseConnection.getDatabase_connection().prepareStatement("SELECT groupId FROM OstronGamesGroup WHERE groupName = ?1");
                    ps.setString(1, "Spieler");
                    ResultSet rs5 = ps.executeQuery();

                    while (rs5.next()){
                        int groupId = rs5.getInt(1);

                        ps = this.databaseConnection.getDatabase_connection().prepareStatement("SELECT groupName, groupPrefix, groupId FROM OstrongGamesGroup WHERE groupId = ?");
                        ps.setInt(1, groupId);
                        ResultSet rs2 = ps.executeQuery();

                        while (rs2.next()){
                            String groupName = rs2.getString(1);
                            String groupPrefix = rs2.getString(2);

                            OstrongGamesGroup runtimeGroup = OstrongGamesPermissionsMain.getRuntime().getRuntimeGroup(groupName);
                            if(runtimeGroup != null){
                                OstrongGamesPermissionsMain.getRuntime().createRuntimeUser(playerUUID, groupName);
                            }else{
                                runtimeGroup = OstrongGamesPermissionsMain.getRuntime().createRuntimeGroup(groupName, groupPrefix);
                                ps = this.databaseConnection.getDatabase_connection().prepareStatement("SELECT permissionId FROM OstrongGamesGroupPermissions WHERE groupId = ?");
                                ps.setInt(1, groupId);
                                ResultSet rs3 = ps.executeQuery();

                                Set<Integer> permIds = new HashSet<>();
                                while(rs3.next()){
                                    permIds.add(rs3.getInt(1));
                                }

                                for(Integer permId : permIds){
                                    ps = this.databaseConnection.getDatabase_connection().prepareStatement("SELECT permissionName, plugin FROM OstrongGamesPermission WHERE permissionId = ?");
                                    ps.setInt(1, permId);
                                    ResultSet rs4 = ps.executeQuery();

                                    while(rs4.next()){
                                        OstrongGamesPermissionsMain.getRuntime().addPermissionToGroup(OstrongGamesPermissionsMain.getRuntime().createRuntimePermission(rs4.getString(1), rs4.getString(2)), runtimeGroup);
                                    }
                                }
                            }
                        }
                    }
                }else{
                    OstrongGamesPermissionsMain.getRuntime().createRuntimeUser(playerUUID, "Spieler");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void writePlayerData(UUID playerUUID){
        PreparedStatement ps = null;
        try{
            ps = this.databaseConnection.getDatabase_connection().prepareStatement("SELECT groupName FROM OstrongGamesGroup g INNER JOIN OstrongGamesGroup u ON u.groupId = g.groupId WHERE u.UUID = ?");
            ps.setString(1, "" + playerUUID);

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String groupName = rs.getString(1);

                for(OstrongGamesUser user : OstrongGamesPermissionsMain.getRuntime().getRuntimeUsers()){
                    if(user.getUUID() == playerUUID){
                        if(user.getUserGroup().getGroupName().equals(groupName)){
                            return;
                        }else{
                            ps = this.databaseConnection.getDatabase_connection().prepareStatement("SELECT g.groupId FROM OstrongGamesGroup g INNER JOIN OstrongGamesGroup u ON u.groupId = g.groupId WHERE u.UUID = ?");
                            ps.setString(1, "" + playerUUID);

                            ResultSet rs2 = ps.executeQuery();
                            while(rs2.next()){
                                int groupId = rs2.getInt(1);

                                ps = this.databaseConnection.getDatabase_connection().prepareStatement("UPDATE OstrongGamesUser SET groupId = ? WHERE UUID = ? ");
                                ps.setInt(1, groupId);
                                ps.setString(2, "" + playerUUID);
                                ps.executeQuery();
                            }
                        }
                    }
                }
            }
            OstrongGamesPermissionsMain.getRuntime().removeRuntimeUser(playerUUID);
            OstrongGamesGroup group = OstrongGamesPermissionsMain.getRuntime().removeRuntimeGroupIfLastMember(playerUUID);

            if(group != null){
                Set<Integer> permIds = new HashSet<>();
                for(OstrongGamesPermission perm : group.getGroupPermissions()){
                    ps = this.databaseConnection.getDatabase_connection().prepareStatement("SELECT permissionId FROM OstrongGamesPermission WHERE permission = ?");
                    ps.setString(1, perm.getPermission());

                    ResultSet rs6 = ps.executeQuery();
                    int rsLength = 0;
                    while(rs6.next()){
                        permIds.add(rs6.getInt(1));
                        rsLength++;
                    }
                    if(rsLength == 0){
                        ps = this.databaseConnection.getDatabase_connection().prepareStatement("INSERT INTO OstrongGamesPermission VALUES(?, ?, ?)");
                        ps.setString(1, perm.getPermission());
                        ps.setString(2, perm.getPlugin());
                        ps.setTime(3, Time.valueOf(LocalTime.now()));
                        ps.executeQuery();

                        ps = this.databaseConnection.getDatabase_connection().prepareStatement("SELECT permissionId FROM OstrongGamesPermission WHERE permission = ?");
                        ps.setString(1, perm.getPermission());
                        ResultSet rs7 = ps.executeQuery();

                        while(rs7.next()){
                            int permId = rs7.getInt(1);

                            ps = this.databaseConnection.getDatabase_connection().prepareStatement("SELECT groupId FROM OstrongGamesGroup WHERE groupName = ?");
                            ps.setString(1, group.getGroupName());
                            ResultSet rs8 = ps.executeQuery();

                            while(rs8.next()){
                                int groupId = rs8.getInt(1);

                                ps = this.databaseConnection.getDatabase_connection().prepareStatement("INSERT INTO OstrongGamesGroupsPermissions VALUES(?, ?)");
                                ps.setInt(1, groupId);
                                ps.setInt(2, permId);
                                ps.executeQuery();
                            }
                        }
                    }
                }

                for(Integer permId : permIds){
                    ps = this.databaseConnection.getDatabase_connection().prepareStatement("SELECT permId FROM OstrongGroupsPermissions WHERE permId = ?");
                    ps.setInt(1, permId);

                    ResultSet rs9 = ps.executeQuery();
                    int rsLength = 0;

                    while(rs9.next()){
                        rsLength++;
                    }

                    if (rsLength == 0){
                        ps = this.databaseConnection.getDatabase_connection().prepareStatement("SELECT groupId FROM OstrongGamesGroup WHERE groupName = ?");
                        ps.setString(1, group.getGroupName());
                        ResultSet rs8 = ps.executeQuery();

                        while(rs8.next()){
                            int groupId = rs8.getInt(1);

                            ps = this.databaseConnection.getDatabase_connection().prepareStatement("INSERT INTO OstrongGamesGroupsPermissions VALUES(?, ?)");
                            ps.setInt(1, groupId);
                            ps.setInt(2, permId);
                            ps.executeQuery();
                        }
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean checkIfGroupExists(String groupName){
        PreparedStatement ps = null;
        try{
            ps = this.databaseConnection.getDatabase_connection().prepareStatement("SELECT groupId FROM OstrongGamesGroup WHERE groupName = ?");
            ps.setString(1, groupName);

            ResultSet rs = ps.executeQuery();
            int rsLength = 0;
            while (rs.next()){
                rsLength++;
            }

            if(rsLength == 0){
                return false;
            }
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public void writeGroupToDatabase(String groupName, String groupPrefix){
        PreparedStatement ps = null;
        try{
            ps = this.databaseConnection.getDatabase_connection().prepareStatement("INSERT INTO OstrongGamesGroup VALUES(?, ?, ?)");
            ps.setString(1, groupName);
            ps.setString(2, groupPrefix);
            ps.setTime(3, Time.valueOf(LocalTime.now()));
            ps.executeQuery();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
