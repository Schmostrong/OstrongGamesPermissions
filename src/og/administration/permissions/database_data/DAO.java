package og.administration.permissions.database_data;

import og.administration.permissions.main.OstrongGamesPermissionsMain;
import og.administration.permissions.utils.OstrongGamesGroup;
import og.administration.permissions.utils.OstrongGamesPermission;
import og.administration.permissions.utils.OstrongGamesUser;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public void retrievePlayerData(UUID playerUUID) throws SQLException {
        this.databaseConnection.openConnection();
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
                if(OstrongGamesPermissionsMain.getRuntime().getRuntimeGroup(OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getDefault_group()) == null){
                    ps = this.databaseConnection.getDatabase_connection().prepareStatement("SELECT groupId, groupName, groupPrefix FROM OstronGamesGroup WHERE groupName = ?");
                    ps.setString(1, OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getDefault_group());
                    ResultSet rs5 = ps.executeQuery();

                    while (rs5.next()){
                        int groupId = rs5.getInt(1);
                        String groupName = rs5.getString(2);
                        String groupPrefix = rs5.getString(3);

                        OstrongGamesGroup runtimeGroup = OstrongGamesPermissionsMain.getRuntime().createRuntimeGroup(groupName, groupPrefix);
                        ps = this.databaseConnection.getDatabase_connection().prepareStatement("SELECT permissionId FROM OstrongGamesGroupPermissions WHERE groupId = ?");
                        ps.setInt(1, groupId);
                        ResultSet rs3 = ps.executeQuery();

                        Set<Integer> permIds = new HashSet<>();
                        while(rs3.next()){
                            permIds.add(rs3.getInt(1));
                        }

                        for(Integer permId : permIds){
                            ps = this.databaseConnection.getDatabase_connection().prepareStatement("SELECT permissionName, permissonPlugin FROM OstrongGamesPermission WHERE permissionId = ?");
                            ps.setInt(1, permId);
                            ResultSet rs4 = ps.executeQuery();

                            while(rs4.next()){
                                OstrongGamesPermissionsMain.getRuntime().addPermissionToGroup(OstrongGamesPermissionsMain.getRuntime().createRuntimePermission(rs4.getString(1), rs4.getString(2)), runtimeGroup);
                            }
                        }
                    }
                }else{
                    OstrongGamesPermissionsMain.getRuntime().createRuntimeUser(playerUUID, OstrongGamesPermissionsMain.getConfigurationLoaderInstance().getDefault_group());
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            this.databaseConnection.closeConnection();
        }
    }

    public void writePlayerData(UUID playerUUID) throws SQLException {
        this.databaseConnection.openConnection();
        PreparedStatement ps = null;
        try{
            ps = this.databaseConnection.getDatabase_connection().prepareStatement("SELECT groupName FROM OstrongGamesGroup g INNER JOIN OstrongGamesUser u ON u.groupId = g.groupId WHERE u.UUID = ?");
            ps.setString(1, "" + playerUUID);

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String groupName = rs.getString(1);

                for(OstrongGamesUser user : OstrongGamesPermissionsMain.getRuntime().getRuntimeUsers()){
                    if(user.getUUID() == playerUUID){
                        if(user.getUserGroup().getGroupName().equals(groupName)){
                            return;
                        }else{
                            ps = this.databaseConnection.getDatabase_connection().prepareStatement("SELECT groupId FROM OstrongGamesGroup WHERE groupName = ?");
                            ps.setString(1, user.getUserGroup().getGroupName());

                            ResultSet rs2 = ps.executeQuery();
                            while(rs2.next()){
                                int groupId = rs2.getInt(1);

                                ps = this.databaseConnection.getDatabase_connection().prepareStatement("UPDATE OstrongGamesUser SET groupId = ? WHERE UUID = ? ");
                                ps.setInt(1, groupId);
                                ps.setString(2, "" + playerUUID);
                                ps.execute();
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
                        ps.execute();

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
                                ps.execute();
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
                            ps.execute();
                        }
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            this.databaseConnection.closeConnection();
        }
    }

    public boolean checkIfGroupExists(String groupName) throws SQLException {
        this.databaseConnection.openConnection();
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
        }finally {
            this.databaseConnection.closeConnection();
        }
    }

    public void writeGroupToDatabase(String groupName, String groupPrefix) throws SQLException {
        this.databaseConnection.openConnection();
        PreparedStatement ps = null;
        try{
            ps = this.databaseConnection.getDatabase_connection().prepareStatement("INSERT INTO OstrongGamesGroup (groupName, groupPrefix, insertDate) VALUES(?, ?, ?)");
            ps.setString(1, groupName);
            ps.setString(2, groupPrefix);
            ps.setDate(3, Date.valueOf(LocalDate.now()));
            ps.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally{
            this.databaseConnection.closeConnection();
        }
    }

    public boolean removeGroupFromDatabase(String groupName) throws SQLException {
        this.databaseConnection.openConnection();
        PreparedStatement ps = null;

        try{
            ps = this.databaseConnection.getDatabase_connection().prepareStatement("SELECT groupId FROM OstrongGamesGroup WHERE groupName = ?");
            ps.setString(1, groupName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int groupId = rs.getInt(1);

                ps = this.databaseConnection.getDatabase_connection().prepareStatement("DELETE FROM OstrongGamesGroup WHERE groupName = ?");
                ps.setString(1, groupName);
                ps.execute();

                ps = this.databaseConnection.getDatabase_connection().prepareStatement("DELETE FROM OstrongGamesGroupsPermissions WHERE groupId = ?");
                ps.setInt(1, groupId);
                ps.execute();
                return true;
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }finally{
            this.databaseConnection.closeConnection();
        }
        return false;
    }

    public void createDefaultGroupIfNotExisting(String groupName, String groupPrefix) throws SQLException {
        this.databaseConnection.openConnection();
        PreparedStatement ps = null;
        ps = this.databaseConnection.getDatabase_connection().prepareStatement("SELECT groupId FROM OstrongGamesGroup WHERE groupName = ?");
        ps.setString(1, groupName);

        ResultSet rs = ps.executeQuery();
        int rsLength = 0;
        while (rs.next()){
            rsLength++;
        }

        if(rsLength == 0){
            writeGroupToDatabase(groupName, groupPrefix);
        }else{
            return;
        }
        this.databaseConnection.closeConnection();
    }

    public OstrongGamesGroup loadDefaultGroup(String defaultGroupName) throws SQLException {
        this.databaseConnection.openConnection();
        PreparedStatement ps = null;
        ps = this.databaseConnection.getDatabase_connection().prepareStatement("SELECT groupName, groupPrefix FROM OstrongGamesGroup WHERE groupName = ?");
        ps.setString(1, defaultGroupName);

        ResultSet rs  = ps.executeQuery();
        while (rs.next()){
            return OstrongGamesPermissionsMain.getRuntime().createRuntimeGroup(rs.getString(1), rs.getString(2));
        }
        this.databaseConnection.closeConnection();
        return null;
    }

    public void loadGroupFromDatabase(String groupName) throws SQLException {
        this.databaseConnection.openConnection();
        PreparedStatement ps = null;
        ps = this.databaseConnection.getDatabase_connection().prepareStatement("SELECT groupName, groupPrefix FROM OstrongGamesGroup WHERE groupName = ?");
        ps.setString(1, groupName);

        ResultSet rs  = ps.executeQuery();
        while (rs.next()){
            OstrongGamesPermissionsMain.getRuntime().createRuntimeGroup(rs.getString(1), rs.getString(2));
        }
        this.databaseConnection.closeConnection();
    }

    public boolean updatePlayersGroup(UUID player, String groupName) throws SQLException {
        this.databaseConnection.openConnection();
        PreparedStatement ps = null;
        ps = this.databaseConnection.getDatabase_connection().prepareStatement("SELECT groupId FROM OstrongGamesGroup WHERE groupName = ?");
        ps.setString(1, groupName);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            int groupId = rs.getInt(1);

            ps = this.databaseConnection.getDatabase_connection().prepareStatement("UPDATE OstrongGamesGroup SET groupId = ? WHERE uuid = ?");
            ps.setInt(1, groupId);
            ps.setString(2, "" + player);
            ps.execute();
            return true;
        }
        return false;
    }
}
