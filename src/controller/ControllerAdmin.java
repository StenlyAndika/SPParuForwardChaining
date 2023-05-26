package controller;

import model.ModelAdmin;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import connection.BridgeCon;
import java.security.NoSuchAlgorithmException;

/**
 * FXML Controller class
 *
 * @author Stenly Andika
 */
public class ControllerAdmin implements Initializable {

    @FXML private AnchorPane blur;
    @FXML private TextField vuser,vnama;
    @FXML private PasswordField vpassword,vpassb,vpassbk;
    @FXML private JFXButton bsimpan,bedit,bhapus,bbatal;
    @FXML private TableView<?> tbluser;
    @FXML private TableColumn<?, ?> colid,coluser,colpassword;
    BridgeCon Con = new BridgeCon();
    ModelAdmin madmin = new ModelAdmin();
    ObservableList data = FXCollections.observableArrayList();
    private boolean databaru = false;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        databaru = true;
        vuser.setDisable(true);
        ShowListAdmin();
        Kosong();
        
        tbluser.setOnMousePressed(e->{
            PilihData();
        });
        
        bbatal.setOnAction(e->{
            Kosong();
        });
        
        bedit.setOnAction(e->{
            setSaveEditDelete(false, true, true);
            setField(true,false);
            vpassword.clear();
            vpassword.setPromptText("Password Lama");
            vpassb.setVisible(true);
            vpassbk.setVisible(true);
            vuser.requestFocus();
        });
        
        bhapus.setOnAction(e->{
            if(vuser.getText().isEmpty())
            {
                Con.showAlert(Alert.AlertType.WARNING,"Messages",null,"Tidak Ada Data!");
            }else{
                try {
                    Con.Open();
                    Con.PStatem = Con.dbKoneksi.prepareStatement("Select * From templogin where id = '" + vuser.getText() + "'");
                    Con.Reset = Con.PStatem.executeQuery();
                    if (Con.Reset.next()) {
                        Con.showAlert(Alert.AlertType.INFORMATION,"Messages",null,"Tidak Bisa Menghapus Admin Yang Sedang Login!");
                    } else {
                        DeleteAdmin("Informasi"
                                + "\nID Admin\t\t: "+vuser.getText()+""
                                + "\nUsername\t: "+vnama.getText()+""
                                + "\nStatus Admin   : Logout"
                                + "\nHapus Data Admin?");
                    }
                    Con.Close();
                } catch (SQLException ix) {}
            }
        });
        
        bsimpan.setOnAction(e->{
            boolean sukses;
            ModelAdmin DataSave = new ModelAdmin(vuser.getText(),vnama.getText(),vpassword.getText());
            ModelAdmin DataUpdate = new ModelAdmin(vuser.getText(),vnama.getText(),vpassb.getText());
            if(vuser.getText().isEmpty()|vnama.getText().isEmpty()|vpassword.getText().isEmpty()) {
                Con.showAlert(Alert.AlertType.WARNING,"Messages",null,"Field Tidak Boleh Kosong!");
            }else{
                if(databaru==true) {
                    sukses = madmin.InsertData(DataSave,"Insert");
                    if(sukses == true) {
                        Con.showAlert(Alert.AlertType.INFORMATION,"Messages",null,"Data Berhasil Di Simpan!");
                        Kosong();
                    } else {
                        Con.showAlert(Alert.AlertType.WARNING,"Messages",null,"Data Gagal Di Simpan!");
                        Kosong();
                    }
                } else {
                    try {
                        Con.Open();
                        Con.PStatem = Con.dbKoneksi.prepareStatement("SELECT * FROM admin WHERE id_admin = '" + vuser.getText() + "'");
                        Con.Reset = Con.PStatem.executeQuery();
                        if (Con.Reset.next()) {
                            if (!vpassword.getText().equals(Con.Reset.getString("password"))) {
                                Con.showAlert(Alert.AlertType.WARNING,"Messages",null,"Password Sebelumnya Salah!");
                                vpassword.requestFocus();
                                Con.Close();
                            } else {
                                Con.Close();
                                if (vpassb.getText().isEmpty()) {
                                    Con.showAlert(Alert.AlertType.WARNING,"Messages",null,"Isikan Password Baru Terlebih Dahulu");
                                    vpassb.requestFocus();
                                } else if (vpassbk.getText().isEmpty()) {
                                    Con.showAlert(Alert.AlertType.WARNING,"Messages",null,"Konfirmasi Password Baru Terlebih Dahulu");
                                    vpassbk.requestFocus();
                                } else {
                                    if (vpassword.getText().length()<5 | vpassword.getText().length()>16) {
                                        Con.showAlert(Alert.AlertType.WARNING,"Messages",null,"Password Harus Berisikan 5-16 Karakter");
                                        vpassword.requestFocus();
                                    } else if (vpassb.getText().length()<5 | vpassb.getText().length()>16) {
                                        Con.showAlert(Alert.AlertType.WARNING,"Messages",null,"Password Harus Berisikan 5-16 Karakter");
                                        vpassb.requestFocus();
                                    } else if (vpassbk.getText().length()<5 | vpassbk.getText().length()>16) {
                                        Con.showAlert(Alert.AlertType.WARNING,"Messages",null,"Password Harus Berisikan 5-16 Karakter");
                                        vpassbk.requestFocus();
                                    } else {
                                        if (vpassbk.getText().length()!=vpassb.getText().length()) {
                                            Con.showAlert(Alert.AlertType.WARNING,"Messages",null,"Password Konfirmasi Harus Sama Dengan Password Baru!");
                                            vpassbk.requestFocus();
                                        } else {
                                            if (!vpassbk.getText().equals(vpassb.getText())) {
                                                Con.showAlert(Alert.AlertType.WARNING,"Messages",null,"Password Konfirmasi Harus Sama Dengan Password Baru!");
                                                vpassbk.requestFocus();
                                            } else {
                                                sukses = madmin.InsertData(DataUpdate,"Update");
                                                if(sukses == true) {
                                                    Con.showAlert(Alert.AlertType.INFORMATION,"Messages",null,"Data Berhasil Di Update!");
                                                    Kosong();
                                                } else {
                                                    Con.showAlert(Alert.AlertType.WARNING,"Messages",null,"Data Gagal Di Update!");
                                                    Kosong();
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (SQLException ux) {}
                }
            }
            ShowListAdmin();
        });
        
    }    
    
    private void DeleteAdmin(String pesan) {
        blur.setEffect(new GaussianBlur(10));
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Messages");
        alert.setHeaderText(null);
        alert.setContentText(pesan);
        alert.showAndWait();
        if(alert.getResult()==ButtonType.OK)
        {
            boolean sukses;
            try {
                sukses = madmin.Delete(vuser.getText());
                if(sukses == true) {
                    Con.showAlert(Alert.AlertType.INFORMATION,"Messages",null,"Data Berhasil Di Hapus!");
                    Kosong();
                } else {
                    Con.showAlert(Alert.AlertType.WARNING,"Messages",null,"Data Gagal Di Hapus!");
                    Kosong();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ControllerGejala.class.getName()).log(Level.SEVERE, null, ex);
            }
            Kosong();
            ShowListAdmin();
        }blur.setEffect(new GaussianBlur(0));
    }

    private ObservableList LoadDBAdmin() {
        ObservableList ListAdmin = FXCollections.observableArrayList();
        try {
            Con.Open();
            Con.PStatem = Con.dbKoneksi.prepareStatement("SELECT * FROM admin ORDER BY id_admin ASC");
            Con.Reset = Con.PStatem.executeQuery();
            while(Con.Reset.next()) {
                String crypt = null;
                try {
                    crypt = Con.md5(Con.Reset.getString(3));
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(ControllerAdmin.class.getName()).log(Level.SEVERE, null, ex);
                }
                madmin = new ModelAdmin(Con.Reset.getString(1),Con.Reset.getString(2),crypt);
                ListAdmin.add(madmin);
            }
            Con.Close();
        } catch (SQLException ex) {
        }
        return ListAdmin;
    }
    
    private void ShowListAdmin() {
        data.clear();
        data = LoadDBAdmin();
        tbluser.setItems(data);
        tbluser.getSelectionModel().clearSelection();
        colid.setCellValueFactory(new PropertyValueFactory("Id"));
        coluser.setCellValueFactory(new PropertyValueFactory("User"));
        colpassword.setCellValueFactory(new PropertyValueFactory("Password"));
    }
    
    private void PilihData() {
        databaru = false;
        try {
            madmin = (ModelAdmin) tbluser.getSelectionModel().getSelectedItems().get(0);
            vuser.setText(madmin.getId());
            vnama.setText(madmin.getUser());
            vpassword.setText(madmin.getPassword());
            setSaveEditDelete(true,false,false);
            setField(false,true);
        } catch(Exception e) {
            Con.showAlert(Alert.AlertType.WARNING,"Messages",null,"Data Tidak Ada!");
        }
    }
    
    private void setField(boolean disable,boolean enable) {
        vuser.setDisable(disable);
        vnama.setDisable(enable);
        vpassword.setDisable(enable);
    }
    
    private void setSaveEditDelete(boolean enable,boolean disable,boolean delete) {
        bsimpan.setDisable(enable);
        bedit.setDisable(disable);
        bhapus.setDisable(delete);
    }
    
    private void Kosong() {
        vnama.setText("");
        vpassword.setText("");
        vnama.requestFocus();
        vpassb.setVisible(false);
        vpassbk.setVisible(false);
        databaru = true;
        setSaveEditDelete(false, true, true);
        setField(true,false);
        tambahid();
    }
    
    private void tambahid() {
        try {
            Con.Open();
            Con.PStatem = Con.dbKoneksi.prepareStatement("SELECT * FROM admin ORDER BY id_admin DESC");
            Con.Reset = Con.PStatem.executeQuery();
            if (Con.Reset.next()) {
                String ai = Con.Reset.getString("id_admin").substring(1);
                String an = "" + (Integer.parseInt(ai)+1);
                String nol = "";
                switch (an.length()) {
                    case 1:
                        nol = "00";
                        break;
                    case 2:
                        nol = "0";
                        break;
                    case 3:
                        nol = "";
                        break;
                    default:
                        break;
                }
                vuser.setText("A" + nol + an);
            } else {
                vuser.setText("A001");
            }
            Con.Close();
	} catch (SQLException e) {}
    }

}
