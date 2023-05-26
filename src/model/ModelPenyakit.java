package model;

import java.sql.SQLException;
import connection.BridgeCon;

/**
 *
 * @author Stenly Andika
 */
public class ModelPenyakit {
    
    BridgeCon Con = new BridgeCon();
    
    private String Kode;
    private String Penyakit;
    private String Info;
    private String Solusi;

    public ModelPenyakit(String Kode, String Penyakit, String Info, String Solusi) {
        this.Kode = Kode;
        this.Penyakit = Penyakit;
        this.Info = Info;
        this.Solusi = Solusi;
    }

    public ModelPenyakit() {
    }

    public String getKode() {
        return Kode;
    }

    public String getPenyakit() {
        return Penyakit;
    }

    public String getInfo() {
        return Info;
    }
    public String getSolusi() {
        return Solusi;
    }

    public boolean InsertData(ModelPenyakit M,String status)
    {
        boolean sukses = false;
        try {
            Con.Open();            
            if ("Insert".equals(status)) {
                Con.PStatem  = Con.dbKoneksi.prepareStatement("INSERT INTO penyakit VALUES (?,?,?,?)");
                Con.PStatem.setString(1,M.getKode());
                Con.PStatem.setString(2,M.getPenyakit());
                Con.PStatem.setString(3,M.getInfo());
                Con.PStatem.setString(4,M.getSolusi());
                Con.PStatem.execute();
                Con.Close();
            } else {
                Con.PStatem  = Con.dbKoneksi.prepareStatement("UPDATE penyakit SET penyakit=?, info=?, solusi=? WHERE kode=?");
                Con.PStatem.setString(4,M.getKode());
                Con.PStatem.setString(1,M.getPenyakit());
                Con.PStatem.setString(2,M.getInfo());
                Con.PStatem.setString(3,M.getSolusi());
                Con.PStatem.execute();
                Con.Close();
            }            
            sukses = true;
        } catch (SQLException e) {
            sukses = false;
        }finally {
        }return sukses;
    }
    
    public boolean Delete(String kode) throws SQLException {
        Con.Open();
        Con.PStatem = Con.dbKoneksi.prepareStatement("DELETE FROM penyakit WHERE kode='"+kode+"'");
        Con.PStatem.executeUpdate();
        Con.Close();
        return true;
    }
   
}
