package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import connection.BridgeCon;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Stenly Andika
 */
public class ControllerDaftar implements Initializable {

    @FXML private JFXTextField vusername;
    @FXML private JFXPasswordField vpassword;
    @FXML private JFXPasswordField vkonfirmasi;
    @FXML private ImageView imgverif,imgverifok,imgverifno;
    @FXML private JFXButton daftar,batal,keluar;
    
    String id,pass;
    boolean sukses;
    
    BridgeCon Con = new BridgeCon();
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imgverifok.setVisible(false);
        imgverifno.setVisible(false);
        id = "A001";
        
        vkonfirmasi.setOnKeyReleased(e->{
            if (vpassword.getText().length()<5 | vkonfirmasi.getText().length()<5 | vkonfirmasi.getText().length()>16 | vkonfirmasi.getText().length()>16) {
                imgverif.setVisible(true);
                imgverifno.setVisible(false);
                imgverifok.setVisible(false);
            } else {
                if (vkonfirmasi.getText().length()==vpassword.getText().length()) {
                    if (vkonfirmasi.getText().equals(vpassword.getText())) {
                        imgverif.setVisible(false);
                        imgverifno.setVisible(false);
                        imgverifok.setVisible(true);
                    } else {
                        imgverif.setVisible(false);
                        imgverifok.setVisible(false);
                        imgverifno.setVisible(true);
                    }
                } else {
                    imgverif.setVisible(true);
                    imgverifno.setVisible(false);
                    imgverifok.setVisible(false);
                }
            }
        });
        
        batal.setOnAction(e->{
            Refresh();
        });
        
        keluar.setOnAction(e->{
            Stage currentstage = (Stage)keluar.getScene().getWindow();
            currentstage.close();
        });
        
        daftar.setOnAction(e->{
            if (vusername.getText().isEmpty()) {
                Con.showAlert(Alert.AlertType.INFORMATION, "Informasi", null, "Username Tidak Boleh Kosong!");
                vusername.requestFocus();
            } else if (vpassword.getText().isEmpty()) {
                Con.showAlert(Alert.AlertType.INFORMATION, "Informasi", null, "Password Tidak Boleh Kosong!");
                vpassword.requestFocus();
            } else if (vkonfirmasi.getText().isEmpty()) {
                Con.showAlert(Alert.AlertType.INFORMATION, "Informasi", null, "Harap Konfirmasi Password Terlebih Dahulu!");
                vkonfirmasi.requestFocus();
            } else {
                if (vpassword.getText().length()<5 | vpassword.getText().length()>16) {
                    Con.showAlert(Alert.AlertType.INFORMATION, "Informasi", null, "Password Harus Berisikan 5-16 Karakter");
                    vpassword.requestFocus();
                } else if (vkonfirmasi.getText().length()<5 | vkonfirmasi.getText().length()>16) {
                    Con.showAlert(Alert.AlertType.INFORMATION, "Informasi", null, "Password Harus Berisikan 5-16 Karakter");
                    vkonfirmasi.requestFocus();
                } else {
                    try {
                        Con.Open();
                        Con.PStatem = Con.dbKoneksi.prepareStatement("SELECT username FROM admin WHERE username ='" + vusername.getText() + "'");
                        Con.Reset = Con.PStatem.executeQuery();
                        if (Con.Reset.next()) {
                            Con.showAlert(Alert.AlertType.INFORMATION, "Informasi", null, "Username Sudah Terdaftar!");
                            vusername.clear();
                            vusername.requestFocus();
                        } else {
                            if (vkonfirmasi.getText().equals(vpassword.getText())) {
                                try {
                                    System.out.println("id :"+id);
                                    Con.PStatem = Con.dbKoneksi.prepareStatement("INSERT INTO admin VALUES (?,?,?)");
                                    Con.PStatem.setString(1,id);
                                    Con.PStatem.setString(2,vusername.getText());
                                    Con.PStatem.setString(3,vpassword.getText());
                                    Con.PStatem.execute();
                                    sukses = true;
                                    Con.showAlert(Alert.AlertType.INFORMATION, "Sukses", null, "Pendaftaran Berhasil.. :) \nAkun Anda Siap Digunakan.");
                                } catch (SQLException i) {
                                    sukses = false;
                                }
                                if (sukses == true) {
                                    Stage currentstage = (Stage)keluar.getScene().getWindow();
                                    currentstage.close();
                                }
                            } else {
                                Con.showAlert(Alert.AlertType.INFORMATION, "Informasi", null, "Password Konfirmasi Tidak Sama");
                                vkonfirmasi.clear();
                                vkonfirmasi.requestFocus();
                            }
                        }
                        Con.Close();
                    } catch (SQLException ex) {

                    }
                }
            }
        });
    }
    
    private void Refresh() {
        vusername.clear();
        vpassword.clear();
        vkonfirmasi.clear();
        vusername.requestFocus();
    }

}
