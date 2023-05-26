package model;

import java.sql.SQLException;
import connection.BridgeCon;

/**
 *
 * @author Stenly Andika
 */
public class ModelAdmin {
    
    BridgeCon Con = new BridgeCon();
    
    public String id;
    private String user;
    private String password;
    
    public ModelAdmin() {
    }

    public ModelAdmin(String id, String user, String password) {
        this.id = id;
        this.user = user;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
    
    public boolean InsertData(ModelAdmin M,String status)
    {
        boolean sukses = false;
        try {
            Con.Open();
            if ("Insert".equals(status)) {
                Con.PStatem  = Con.dbKoneksi.prepareStatement("INSERT INTO admin VALUES (?,?,?)");
                Con.PStatem.setString(1,M.getId());
                Con.PStatem.setString(2,M.getUser());
                Con.PStatem.setString(3,M.getPassword());
                Con.PStatem.execute();
                Con.Close();
            } else {
                Con.PStatem2  = Con.dbKoneksi.prepareStatement("UPDATE admin SET username=?, password=? WHERE id_admin=?");
                Con.PStatem2.setString(3,M.getId());
                Con.PStatem2.setString(1,M.getUser());
                Con.PStatem2.setString(2,M.getPassword());
                Con.PStatem2.execute();
                Con.Close();
            }            
            sukses = true;
        } catch (SQLException e) {
            sukses = false;
            e.printStackTrace();
        }finally {
        }return sukses;
    }
        
    public boolean Delete(String id) throws SQLException {
        Con.Open();
        Con.PStatem = Con.dbKoneksi.prepareStatement("DELETE FROM admin WHERE id_admin='"+id+"'");
        Con.PStatem.execute();
        Con.Close();
        return true;
    }
       
}
