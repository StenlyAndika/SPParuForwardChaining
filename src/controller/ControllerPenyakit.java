package controller;

import model.ModelPenyakit;
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
public class ControllerPenyakit implements Initializable {

    @FXML private AnchorPane blur;
    @FXML private TextField vkode,vpenyakit;
    @FXML private TextArea vsolusi, vinfo;
    @FXML private JFXButton bsimpan,bedit,bhapus,bbatal;
    @FXML private TableView<?> tblpenyakit;
    @FXML private TableColumn<?, ?> colkode,colpenyakit,colsolusi;
    BridgeCon Con = new BridgeCon();
    ModelPenyakit mpenyakit = new ModelPenyakit();
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
        Kosong();
        ShowListPenyakit();
        
        tblpenyakit.setOnMousePressed(e->{
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
                alert.setContentText("Hapus Penyakit Dengan Kode : "+vkode.getText()+" ?");
                alert.showAndWait();
                if(alert.getResult()==ButtonType.OK)
                {
                    boolean sukses;
                    try {
                        sukses = mpenyakit.Delete(vkode.getText());
                        if (sukses == true) {
                            Con.showAlert(Alert.AlertType.INFORMATION,"Messages",null,"Data Berhasil Di Hapus!");
                            Kosong();
                        } else {
                            Con.showAlert(Alert.AlertType.WARNING,"Messages",null,"Data Gagal Di Hapus!");
                            Kosong();
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(ControllerGejala.class.getName()).log(Level.SEVERE, null, ex);
                    }
                ShowListPenyakit();
                Kosong();
                }
            }blur.setEffect(new GaussianBlur(0));
        });
                
        bsimpan.setOnAction(e->{
            boolean sukses;
            ModelPenyakit DataPenyakit= new ModelPenyakit(vkode.getText(),vpenyakit.getText(),vinfo.getText(),vsolusi.getText());
            if(vkode.getText().isEmpty()|vpenyakit.getText().isEmpty()|vinfo.getText().isEmpty()|vsolusi.getText().isEmpty()) {
                Con.showAlert(Alert.AlertType.WARNING,"Messages",null,"Field Tidak Boleh Kosong!");
            }else{
                if(databaru==true) {
                    sukses = mpenyakit.InsertData(DataPenyakit,"Insert");
                    if(sukses == true) {
                        Con.showAlert(Alert.AlertType.INFORMATION,"Messages",null,"Data Berhasil Di Simpan!");
                        Kosong();
                    } else {
                        Con.showAlert(Alert.AlertType.WARNING,"Messages",null,"Data Gagal Di Simpan!");
                        Kosong();
                    }
                } else {
                    sukses = mpenyakit.InsertData(DataPenyakit,"Update");
                    if(sukses == true) {
                        Con.showAlert(Alert.AlertType.INFORMATION,"Messages",null,"Data Berhasil Di Update!");
                        Kosong();
                    } else {
                        Con.showAlert(Alert.AlertType.WARNING,"Messages",null,"Data Gagal Di Update!");
                        Kosong();
                    }
                }
            }
            ShowListPenyakit();
        });
    }    
    
    public ObservableList LoadDBPenyakit() {
        ObservableList ListPenyakit = FXCollections.observableArrayList();
        try {
            Con.Open();
            Con.PStatem = Con.dbKoneksi.prepareStatement("SELECT * FROM penyakit ORDER BY kode ASC");
            Con.Reset = Con.PStatem.executeQuery();
            while(Con.Reset.next()) {
                mpenyakit = new ModelPenyakit(Con.Reset.getString(1),Con.Reset.getString(2),Con.Reset.getString(3),Con.Reset.getString(4));
                ListPenyakit.add(mpenyakit);
            }
            Con.Close();
        } catch (SQLException ex) {
        }
        return ListPenyakit;
    }
    
    private void ShowListPenyakit() {
        data.clear();
        data = LoadDBPenyakit();
        tblpenyakit.setItems(data);
        tblpenyakit.getSelectionModel().clearSelection();
        colkode.setCellValueFactory(new PropertyValueFactory("Kode"));
        colpenyakit.setCellValueFactory(new PropertyValueFactory("Penyakit"));
        colsolusi.setCellValueFactory(new PropertyValueFactory("Solusi"));
    }
    
    private void PilihData() {
        databaru = false;
        try {
            mpenyakit = (ModelPenyakit) tblpenyakit.getSelectionModel().getSelectedItems().get(0);
            vkode.setText(mpenyakit.getKode());
            vpenyakit.setText(mpenyakit.getPenyakit());
            vinfo.setText(mpenyakit.getInfo());
            vsolusi.setText(mpenyakit.getSolusi());
            setSaveEditDelete(true,false,false);
            setField(false,true);
        } catch(Exception e) {
            Con.showAlert(Alert.AlertType.WARNING,"Messages",null,"Data Tidak Ada!");
        }
    }
    
    private void setField(boolean disable,boolean enable) {
        vkode.setDisable(disable);
        vpenyakit.setDisable(enable);
        vinfo.setDisable(enable);
        vsolusi.setDisable(enable);
    }
    
    private void setSaveEditDelete(boolean enable,boolean disable,boolean delete) {
        bsimpan.setDisable(enable);
        bedit.setDisable(disable);
        bhapus.setDisable(delete);
    }
    
    private void Kosong() {
        vkode.clear();
        vpenyakit.clear();
        vpenyakit.requestFocus();
        vinfo.clear();
        vsolusi.clear();
        databaru = true;
        setSaveEditDelete(false, true, true);
        setField(true,false);
        tambahid();
    }
    
    private void tambahid() {
        try {
            Con.Open();
            Con.PStatem = Con.dbKoneksi.prepareStatement("SELECT * FROM penyakit ORDER BY kode DESC");
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
                vkode.setText("P" + nol + an);
            } else {
                vkode.setText("P01");
            }
            Con.Close();
	} catch (SQLException e) {}
    }
    
}
