package controller;

import com.jfoenix.controls.JFXButton;
import connection.BridgeCon;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import model.ModelPasien;

/**
 * FXML Controller class
 *
 * @author Stenly Andika
 */
public class ControllerPasien implements Initializable {

    @FXML private AnchorPane blur;
    @FXML private TextField vid, vnama, vumur, valamat, vcari;
    @FXML private TableView<?> tblpasien;
    @FXML private TableColumn<?, ?> colid, colnama, colalamat, columur, coljekel, coloption;
    @FXML private RadioButton rlaki, rperem;
    @FXML private JFXButton bsimpan, bedit, bhapus, bbatal;

    BridgeCon Con = new BridgeCon();
    ObservableList data = FXCollections.observableArrayList();
    String jekel;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showListPasien();
        Kosong();
        FieldStatus(true, false);
        vid.setEditable(false);
        ToggleGroup gender = new ToggleGroup();
        rlaki.setToggleGroup(gender);
        rperem.setToggleGroup(gender);
        
        rlaki.setOnAction(e->{
            jekel = "L";
        });
        
        rperem.setOnAction(e->{
            jekel = "P";
        });
        
        tblpasien.setOnMouseClicked(e->{
            PilihData();
        });
        
        vcari.setOnKeyReleased(e->{
            data.clear();
            data = LoadDBPasien("SELECT DISTINCT * FROM pasien WHERE nama LIKE '%" + vcari.getText().trim() + "%' OR alamat LIKE '%" + vcari.getText().trim() + "%'");
            tblpasien.setItems(data);
            tblpasien.getSelectionModel().clearSelection();
        });
        
        bbatal.setOnAction(e->{
            Kosong();
            FieldStatus(true, false);
        });
        
        bedit.setOnAction(e->{
            FieldStatus(false, true);
            bsimpan.setVisible(true);
            bedit.setVisible(false);
        });
        
        bsimpan.setOnAction(e->{
            try {
                Con.Open();
                Con.PStatem = Con.dbKoneksi.prepareStatement("UPDATE pasien SET nama =?, alamat=?, jekel=?, umur=? WHERE idpas=?");
                Con.PStatem.setString(1, vnama.getText());
                Con.PStatem.setString(2, valamat.getText());
                Con.PStatem.setString(3, jekel);
                Con.PStatem.setString(4, vumur.getText());
                Con.PStatem.setString(5, vid.getText());
                Con.PStatem.execute();
                Con.Close();
                Con.showAlert(Alert.AlertType.INFORMATION, "Message", null, "Data Berhasil Diupdate!");
                showListPasien();
                FieldStatus(true, false);
            } catch (SQLException x) {
                Con.showAlert(Alert.AlertType.INFORMATION, "Message", null, "Data Gagal Diupdate!");
            }
        });
        
        bhapus.setOnAction(e->{
            blur.setEffect(new GaussianBlur(10));
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Messages");
            alert.setHeaderText(null);
            alert.setContentText("Hapus Data "
                    + "\nID Pasien\t\t: " + vid.getText() + ""
                    + "\nNama Pasien\t: " + vnama.getText() + ""
                    + "\nAlamat Pasien\t: " + valamat.getText() + "");
            alert.showAndWait();
            if(alert.getResult()==ButtonType.OK)
            {
                boolean sukses;
                try {
                    Con.Open();
                    Con.PStatem = Con.dbKoneksi.prepareStatement("DELETE FROM pasien WHERE idpas='" + vid.getText() + "'");
                    Con.PStatem.execute();
                    Con.PStatem2 = Con.dbKoneksi.prepareStatement("DELETE FROM diagnosa WHERE idpas LIKE '%" + vid.getText() + "%'");
                    Con.PStatem2.execute();
                    Con.Close();
                    sukses = true;
                    if(sukses == true) {
                        Con.showAlert(Alert.AlertType.INFORMATION,"Messages",null,"Data Berhasil Di Hapus!");
                    } else {
                        Con.showAlert(Alert.AlertType.WARNING,"Messages",null,"Data Gagal Di Hapus!");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ControllerGejala.class.getName()).log(Level.SEVERE, null, ex);
                }
            showListPasien();
            Kosong();
            }
            blur.setEffect(new GaussianBlur(0));
        });
    }    
    
    private ObservableList LoadDBPasien(String query) {
        ObservableList ListPasien = FXCollections.observableArrayList();
        String nama = null;
        try {
            Con.Open();
            Con.PStatem = Con.dbKoneksi.prepareStatement(query);
            Con.Reset = Con.PStatem.executeQuery();
            while(Con.Reset.next()) {
                ModelPasien mp = new ModelPasien(Con.Reset.getString("idpas"),Con.Reset.getString("nama"),Con.Reset.getString("alamat"),Con.Reset.getString("jekel"),Con.Reset.getInt("umur"));
                ListPasien.add(mp);
            }
            Con.Close();
        } catch (SQLException ex) {}
        return ListPasien;
    }
    
    private void showListPasien() {
        data.clear();
        data = LoadDBPasien("SELECT * FROM pasien ORDER BY idpas ASC");
        tblpasien.setItems(data);
        tblpasien.getSelectionModel().clearSelection();
        colid.setCellValueFactory(new PropertyValueFactory("Idpas"));
        colnama.setCellValueFactory(new PropertyValueFactory("Nama"));
        colalamat.setCellValueFactory(new PropertyValueFactory("Alamat"));
        coljekel.setCellValueFactory(new PropertyValueFactory("Jekel"));
        columur.setCellValueFactory(new PropertyValueFactory("Umur"));
    }
    
    private void PilihData() {
        try {
            ModelPasien mp = (ModelPasien) tblpasien.getSelectionModel().getSelectedItems().get(0);
            vid.setText(mp.getIdpas());
            vnama.setText(mp.getNama());
            valamat.setText(mp.getAlamat());
            if (mp.getJekel().equals("L")) {
                rlaki.setSelected(true);
            } else {
                rperem.setSelected(true);
            }
            vumur.setText(String.valueOf(mp.getUmur()));
        } catch(Exception e) {
            Con.showAlert(Alert.AlertType.WARNING,"Messages",null,"Data Tidak Ada!");
        }
    }
    
    private void Kosong() {
        vid.clear();
        vnama.clear();
        valamat.clear();
        rlaki.setSelected(false);
        rperem.setSelected(false);
        vumur.clear();
        vcari.clear();
        bsimpan.setVisible(false);
        bedit.setVisible(true);
    }
    
    private void FieldStatus(boolean enable, boolean disable) {
        vnama.setDisable(enable);
        valamat.setDisable(enable);
        vumur.setDisable(enable);
        rlaki.setDisable(enable);
        rperem.setDisable(enable);
        rlaki.setDisable(enable);
        rperem.setDisable(enable);
        
        vnama.setEditable(disable);
        valamat.setEditable(disable);
        vumur.setEditable(disable);
    }
    
}
