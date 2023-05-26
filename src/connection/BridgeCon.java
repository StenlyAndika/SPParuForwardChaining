package connection;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author Stenly Andika
 */
public class BridgeCon {
    private double x;
    private double y;
       
    public Connection dbKoneksi;
    public ResultSet Reset;
    public ResultSet Reset2;
    public ResultSet Reset3;
    public PreparedStatement PStatem;
    public PreparedStatement PStatem2;
    public PreparedStatement PStatem3;

    public void Open() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            dbKoneksi = DriverManager.getConnection("jdbc:mysql://localhost/spparu", "root", "");
        } catch (SQLException e) {
            e.getMessage();
            System.out.println("WARNING : Connection Error!");
        }
    }
 
    public void Close() {
        try {
            if (Reset != null) {
                Reset.close();
            }
            if (PStatem != null) {
                PStatem.close();
            }
            if (dbKoneksi != null) {
                dbKoneksi.close();
            }
        } catch (SQLException D) {
            throw new RuntimeException(D.getMessage());
        }
    }
    
    public String md5(String s) throws NoSuchAlgorithmException{
        String md5;
        MessageDigest m=MessageDigest.getInstance("MD5");
        m.update(s.getBytes(),0,s.length());
        md5=new BigInteger(1,m.digest()).toString(16);
        return md5;
    }
    
    public void setDragged(Parent database_parent, Stage app_stage){
        database_parent.setOnMousePressed((MouseEvent event) -> {
            x=event.getSceneX();
            y=event.getSceneY();
        });

        database_parent.setOnMouseDragged((MouseEvent event) -> {
                app_stage.setX(event.getScreenX()-x);
                app_stage.setY(event.getScreenY()-y);
        });
    }
    
    public void showAlert(Alert.AlertType type, String title, String header, String text){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();
    }
    
    //All Getter
    public String getType(String table_name, String code){
        Open();
        try {
            PStatem = dbKoneksi.prepareStatement("SELECT * FROM " +table_name+ " WHERE kode = '" + code + "'");
            Reset = PStatem.executeQuery();
            if (Reset.next()) {
                return Reset.getString("tipe");
            }
        } catch (SQLException ex) { }
        Close();
        return null;
    }
    
    public String getContent(String table_name, String code){
        Open();
        try {
            PStatem = dbKoneksi.prepareStatement("SELECT * FROM " +table_name+ " WHERE kode = '" + code + "'");
            Reset = PStatem.executeQuery();
            if (Reset.next()) {
                return Reset.getString("pertanyaan");
            }
        } catch (SQLException ex) { }
        Close();
        return null;
    }
    
    public String getQuestion(String tbl, String code){
        Open();
        try {
            PStatem = dbKoneksi.prepareStatement("SELECT * FROM " + tbl + " WHERE kode = '" + code + "'");
            Reset = PStatem.executeQuery();
            if (Reset.next()) {
                return Reset.getString(tbl);
            }
        } catch (SQLException ex) { }
        Close();
        return null;
    }
    
    public String getFirstTable(){
        Open();
        try {    
            PStatem = dbKoneksi.prepareStatement("SELECT * FROM ruleutama ORDER BY kode ASC");
            Reset = PStatem.executeQuery();
            if (Reset.next()) {
                return Reset.getString("kode");
            }
        } catch (SQLException ex) {}
        Close();
        return null;
    }
    
    public String getTable(String name) {
        Open();
        try {
            PStatem = dbKoneksi.prepareStatement("SELECT * FROM ruleutama WHERE kode ='" +name.toLowerCase()+"'");
            Reset = PStatem.executeQuery();
            if (Reset.next()) {
                return Reset.getString("kode");
            }
        } catch (SQLException ux) {}
        Close();
        return null;
    }
        
    public boolean dataFound(String table_name, String code){
        try {
            Open();
            PStatem = dbKoneksi.prepareStatement("SELECT * FROM " + table_name + " WHERE kode = " + code + "");
            Reset = PStatem.executeQuery();
            return Reset.next();
        } catch (SQLException ex) {
            return false;
        }finally{
            Close();
        }
    }
    
    //All Loader
    public ObservableList LoadDataRule(){
        ObservableList data = FXCollections.observableArrayList();
        Open();
        try {
            PStatem = dbKoneksi.prepareStatement("SELECT * FROM ruleutama");
            Reset = PStatem.executeQuery();
            while (Reset.next()) {
                data.add(Reset.getString("kode").toUpperCase());
            }
        } catch (SQLException ex) {}
        Close();
        return data;
    }
    
    public void edit_content(String table_name, String code, String new_type, String new_content){
        Open();
        try {
            PStatem = dbKoneksi.prepareStatement("UPDATE " + table_name + " SET tipe=?, pertanyaan=? WHERE kode=?");
            PStatem.setString(1, new_type);
            PStatem.setString(2, new_content);
            PStatem.setString(3, code);
            PStatem.execute();
            showAlert(Alert.AlertType.INFORMATION, "Message", null, "Data Berhasil Di Update!");
        } catch (SQLException ex) {}
        Close();
    }
    
    public void delete(String table_name, String code){
        Open();
        try {
            PStatem = dbKoneksi.prepareStatement("DELETE FROM " +table_name +" WHERE kode='" +code +"'");
            PStatem.execute();
            showAlert(Alert.AlertType.INFORMATION, "Message", null, "Data Berhasil Di Hapus!");
        } catch (SQLException ex) {}
        Close();
    }
    
    public void delete_all_after(String table_name, String code, String status){
        Open();
        try{
            int jmlBaris = 0;
            PStatem = dbKoneksi.prepareStatement("SELECT COUNT(*) as jmlRow FROM " + table_name + " WHERE kode like '%" + code +"%' ORDER BY kode");
            Reset = PStatem.executeQuery();
            while (Reset.next()) {
                jmlBaris = Reset.getInt("jmlRow");
            }
            
            PStatem2 = dbKoneksi.prepareStatement("SELECT * FROM " +table_name+ " WHERE kode like '%" +code +"%' ORDER BY kode");
            Reset2 = PStatem2.executeQuery();
            String codes[] = new String[jmlBaris];

            int p;
            p=0;
            while(Reset2.next()){
                if (p<codes.length) {
                    codes[p] = Reset2.getString("kode");
                    System.out.println("Data Ketemu : "+codes[p]);
                    p++;
                }
            }
            
            if ("Save".equals(status)) {
                for (int l=1;l<codes.length;l++) {
                    PStatem3 = dbKoneksi.prepareStatement("DELETE FROM " +table_name + " WHERE kode='" + codes[l] + "'");
                    PStatem3.execute();
                    System.out.println("Data Hapus (Save) : "+codes[l]);
                }
            } else {
                for (String code1 : codes) {
                    PStatem3 = dbKoneksi.prepareStatement("DELETE FROM " +table_name + " WHERE kode='" + code1 + "'");
                    PStatem3.execute();
                    System.out.println("Data Hapus (Delete) : "+code1);
                }
            }
        }catch(SQLException ex){}
        Close();
    }
    
    
    //Add Data
    public void addNewContent(String table_name, String code, String type, String pertanyaan){
        Open();
        try {
            PStatem = dbKoneksi.prepareStatement("INSERT INTO "+table_name+" values (?,?,?)");
            PStatem.setString(1, code);
            PStatem.setString(2, type);
            PStatem.setString(3, pertanyaan);
            PStatem.execute();
        } catch (SQLException ex) {
            Logger.getLogger(BridgeCon.class.getName()).log(Level.SEVERE, null, ex);
        }
        Close();
    }
    
    //New Diagnotics
    public void addMainRule(String table_name){
        Open();
        try {
            PStatem = dbKoneksi.prepareStatement("INSERT INTO ruleutama VALUES (?)");
            PStatem.setString(1, table_name);
            PStatem.execute();
        } catch (SQLException ex) {
            Logger.getLogger(BridgeCon.class.getName()).log(Level.SEVERE, null, ex);
        }
        Close();
    }
    
    public void newTable(String table_name){
        Open();
        try {
            PStatem = dbKoneksi.prepareStatement("CREATE TABLE spparu." + table_name + " ( kode VARCHAR(15) NOT NULL , tipe VARCHAR(2) NOT NULL , pertanyaan VARCHAR(5) NOT NULL, PRIMARY KEY (kode)) ENGINE=MYISAM");
            PStatem.execute();
        } catch (SQLException ex) {
            Logger.getLogger(BridgeCon.class.getName()).log(Level.SEVERE, null, ex);
        }
        Close();
    }
    
    public void deleteFromMainRule(String table_name){
        Open();
        try {
            PStatem = dbKoneksi.prepareStatement("DELETE FROM ruleutama WHERE kode='" + table_name + "'");
            PStatem.execute();
        } catch (SQLException ex) {
            Logger.getLogger(BridgeCon.class.getName()).log(Level.SEVERE, null, ex);
        }
        Close();
    }
    
    public void deleteTable(String table_name){
        Open();
        try {
            PStatem = dbKoneksi.prepareStatement("DROP TABLE " + table_name);
            PStatem.execute();
        } catch (SQLException ex) {
            Logger.getLogger(BridgeCon.class.getName()).log(Level.SEVERE, null, ex);
        }
        Close();
    }
}
