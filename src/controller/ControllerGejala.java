package controller;

import model.ModelGejala;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import connection.BridgeCon;

/**
 * FXML Controller class
 *
 * @author Stenly Andika
 */
public class ControllerGejala implements Initializable {

    @FXML private AnchorPane blur;
    @FXML private TextField vkode;
    @FXML private TextArea vgejala;
    @FXML private JFXButton bsimpan,bedit,bhapus,bbatal;
    @FXML private TableView<?> tblgejala;
    @FXML private TableColumn<?, ?> colkode, colgejala;
    ObservableList data = FXCollections.observableArrayList();
    
    ModelGejala mgejala = new ModelGejala();
    BridgeCon Con = new BridgeCon();
    private boolean databaru = false;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        databaru = true;
        Kosong();
        ShowListGejala();
        
        tblgejala.setOnMousePressed(e->{
            PilihData();
        });
        
        bbatal.setOnAction(e->{
            Kosong();
        });
        
        bedit.setOnAction(e->{
            setSaveEditDelete(false, true, true);
            setField(true,false);
        });
        
        bhapus.setOnAction(e->{
            if(vkode.getText().isEmpty())
            {
                Con.showAlert(Alert.AlertType.WARNING,"Messages",null,"Tidak Ada Data!");
            }else{
                blur.setEffect(new GaussianBlur(10));
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Messages");
                alert.setHeaderText(null);
                alert.setContentText("Hapus Gejala Dengan Kode : "+vkode.getText()+" ?");
                alert.showAndWait();
                if(alert.getResult()==ButtonType.OK)
                {
                    boolean sukses;
                    try {
                        sukses = mgejala.Delete(vkode.getText());
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
                    ShowListGejala();
                    Kosong();
                }
            }blur.setEffect(new GaussianBlur(0));
        });
        
        bsimpan.setOnAction(e->{
            boolean sukses;
            ModelGejala DataGejala = new ModelGejala(vkode.getText(),vgejala.getText());
            if(vkode.getText().isEmpty()|vgejala.getText().isEmpty()) {
                Con.showAlert(Alert.AlertType.WARNING,"Messages",null,"Field Tidak Boleh Kosong!");
            }else{
                if(databaru==true) {
                    sukses = mgejala.InsertData(DataGejala,"Insert");
                    if(sukses == true) {
                        Con.showAlert(Alert.AlertType.INFORMATION,"Messages",null,"Data Berhasil Di Simpan!");
                        Kosong();
                    } else {
                        Con.showAlert(Alert.AlertType.WARNING,"Messages",null,"Data Gagal Di Simpan!");
                        Kosong();
                    }
                } else {
                    sukses = mgejala.InsertData(DataGejala,"Update");
                    if(sukses == true) {
                        Con.showAlert(Alert.AlertType.INFORMATION,"Messages",null,"Data Berhasil Di Update!");
                        Kosong();
                    } else {
                        Con.showAlert(Alert.AlertType.WARNING,"Messages",null,"Data Gagal Di Update!");
                        Kosong();
                    }
                }
            }
            ShowListGejala();
        });
    }
    
    public ObservableList LoadDBGejala() {
        ObservableList ListGejala = FXCollections.observableArrayList();
        try {
            Con.Open();
            Con.PStatem = Con.dbKoneksi.prepareStatement("SELECT * FROM gejala ORDER BY kode ASC");
            Con.Reset = Con.PStatem.executeQuery();
            while(Con.Reset.next()) {
                mgejala = new ModelGejala(Con.Reset.getString(1),Con.Reset.getString(2));
                ListGejala.add(mgejala);
            }
            Con.Close();
        } catch (SQLException ex) {
        }
        return ListGejala;
    }
    
    private void ShowListGejala() {
        data.clear();
        data = LoadDBGejala();
        tblgejala.setItems(data);
        tblgejala.getSelectionModel().clearSelection();
        colkode.setCellValueFactory(new PropertyValueFactory("Kode"));
        colgejala.setCellValueFactory(new PropertyValueFactory("Gejala"));
    }
    
    private void PilihData() {
        databaru = false;
        try {
            mgejala = (ModelGejala) tblgejala.getSelectionModel().getSelectedItems().get(0);
            vkode.setText(mgejala.getKode());
            vgejala.setText(mgejala.getGejala());
            setSaveEditDelete(true,false,false);
            setField(false,true);
        } catch(Exception e) {
            Con.showAlert(Alert.AlertType.WARNING,"Messages",null,"Data Tidak Ada!");
        }
    }
    
    private void setField(boolean disable,boolean enable) {
        vkode.setDisable(disable);
        vgejala.setDisable(enable);
    }
    
    private void setSaveEditDelete(boolean enable,boolean disable,boolean delete) {
        bsimpan.setDisable(enable);
        bedit.setDisable(disable);
        bhapus.setDisable(delete);
    }
    
    private void Kosong() {
        vkode.clear();
        vgejala.clear();
        vgejala.requestFocus();
        databaru = true;
        setSaveEditDelete(false, true, true);
        setField(true,false);
        tambahid();
    }
    
    private void tambahid() {
        try {
            Con.Open();
            Con.PStatem = Con.dbKoneksi.prepareStatement("SELECT * FROM gejala ORDER BY kode DESC");
            Con.Reset = Con.PStatem.executeQuery();
            if (Con.Reset.next()) {
                String ai = Con.Reset.getString("Kode").substring(1);
                String an = "" + (Integer.parseInt(ai)+1);
                String nol = "";
                if (an.length()==1) {
                	nol = "0";
                } else if (an.length()==2) {
                    nol = "";
                }
                vkode.setText("G" + nol + an);
            } else {
                vkode.setText("G01");
            }
            Con.Close();
	} catch (SQLException e) {}
    }

}
