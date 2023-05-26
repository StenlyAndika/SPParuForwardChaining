package model;

import java.sql.SQLException;
import connection.BridgeCon;

/**
 *
 * @author Stenly Andika
 */
public class ModelGejala {
    
    BridgeCon Con = new BridgeCon();
    
    private String Kode;
    private String Gejala;

    public ModelGejala(String Kode, String Gejala) {
        this.Kode = Kode;
        this.Gejala = Gejala;
    }

    public ModelGejala() {
    }

    public String getKode() {
        return Kode;
    }

    public String getGejala() {
        return Gejala;
    }
    
    public boolean InsertData(ModelGejala M,String status)
    {
        boolean sukses = false;
        try {
            Con.Open();            
            if ("Insert".equals(status)) {
                Con.PStatem  = Con.dbKoneksi.prepareStatement("INSERT INTO gejala VALUES (?,?)");
                Con.PStatem.setString(1,M.getKode());
                Con.PStatem.setString(2,M.getGejala());
                Con.PStatem.execute();
                Con.Close();
            } else {
                Con.PStatem  = Con.dbKoneksi.prepareStatement("UPDATE Gejala SET gejala=? WHERE kode=?");
                Con.PStatem.setString(2,M.getKode());
                Con.PStatem.setString(1,M.getGejala());
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
        Con.PStatem = Con.dbKoneksi.prepareStatement("DELETE FROM gejala WHERE kode='"+kode+"'");
        Con.PStatem.execute();
        Con.Close();
        return true;
    }
    
}
