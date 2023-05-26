package controller;

import model.ModelRekam;
import com.jfoenix.controls.JFXButton;
import connection.BridgeCon;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author Stenly Andika
 */
public class ControllerRekam implements Initializable {

    @FXML private AnchorPane blur;
    @FXML private TextField vid, vnama, vcari;
    @FXML private TextArea vgejala;
    @FXML private TextArea vpenyakit;
    @FXML private TextField vtgl, btahun;
    @FXML private JFXButton bhapus;
    @FXML private JFXButton bbatal,bcetak;
    @FXML private TableView<?> tblrekam;
    @FXML private TableColumn<?, ?> colid, colnama, coltgl, colpenyakit;
    @FXML private ComboBox<String> bbulan;

    BridgeCon Con = new BridgeCon();
    ObservableList data = FXCollections.observableArrayList();
    ObservableList dataBulan = FXCollections.observableArrayList("Januari","Februari","Maret","April","Mei","Juni","Juli","Agustus","September","Oktober","November","Desember");
    
    String IdxBulan;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showListPasien();
        bbulan.setItems(dataBulan);
        
        bbulan.setOnAction(e->{
            switch (bbulan.getSelectionModel().getSelectedIndex()) {
                case 0:
                    IdxBulan = "1";
                    break;
                case 1:
                    IdxBulan = "2";
                    break;
                case 2:
                    IdxBulan = "3";
                    break;
                case 3:
                    IdxBulan = "4";
                    break;
                case 4:
                    IdxBulan = "5";
                    break;
                case 5:
                    IdxBulan = "6";
                    break;
                case 6:
                    IdxBulan = "7";
                    break;
                case 7:
                    IdxBulan = "8";
                    break;
                case 8:
                    IdxBulan = "9";
                    break;
                case 9:
                    IdxBulan = "10";
                    break;
                case 10:
                    IdxBulan = "11";
                    break;
                case 11:
                    IdxBulan = "12";
                    break;
                default:
                    break;
            }
        });
        
        tblrekam.setOnMouseClicked(e->{
            PilihData();
        });
        
        bbatal.setOnAction(e->{
            Kosong();
        });
        
        vcari.setOnKeyReleased(e->{
            data.clear();
            data = CariPasien(vcari.getText().trim());
            tblrekam.setItems(data);
            tblrekam.getSelectionModel().clearSelection();
        });
        
        bcetak.setOnAction(e->{
            Con.Open();
            try{
                InputStream urlreport = getClass().getResourceAsStream("/report/LaporanBulanan.jasper");
                HashMap Param = new HashMap();
                Param.put("mnt", IdxBulan);
                Param.put("yrs", btahun.getText());
                JasperReport jreprt=(JasperReport)JRLoader.loadObject(urlreport);
                JasperPrint jprintt=JasperFillManager.fillReport(jreprt,Param,Con.dbKoneksi);
                JasperViewer.viewReport(jprintt,false);
            }catch (JRException n){
                Con.showAlert(Alert.AlertType.INFORMATION,"Messages",null,"Gagal Mencetak Laporan!");
            }
            Con.Close();
        });
        
        bhapus.setOnAction(e->{
            blur.setEffect(new GaussianBlur(10));
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Messages");
            alert.setHeaderText(null);
            alert.setContentText("Hapus Data "
                    + "\nID Rekam Medis\t\t: " + vid.getText() + ""
                    + "\nNama Pasien\t\t\t: " + vnama.getText() + ""
                    + "\nTanggal Diagnosa\t\t: " + vtgl.getText() + ""
                    + "\nPenyakit yang Diderita\t: " + vpenyakit.getText() + "");
            alert.showAndWait();
            if(alert.getResult()==ButtonType.OK)
            {
                boolean sukses;
                try {
                    Con.Open();
                    Con.PStatem = Con.dbKoneksi.prepareStatement("DELETE FROM diagnosa WHERE idrekam='" + vid.getText() + "'");
                    Con.PStatem.execute();
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
    
    private ObservableList CariPasien(String keyword){
        String nama = null, idpas = null;
        ObservableList listData = FXCollections.observableArrayList();
        try {
            Con.Open();
            Con.PStatem = Con.dbKoneksi.prepareStatement("SELECT DISTINCT * FROM pasien WHERE nama LIKE '%" + keyword + "%'");
            Con.Reset = Con.PStatem.executeQuery();
            while(Con.Reset.next()) {
                idpas = Con.Reset.getString("idpas");
                nama = Con.Reset.getString("nama");
                try {
                    Con.PStatem2 = Con.dbKoneksi.prepareStatement("SELECT DISTINCT * FROM diagnosa WHERE idpas LIKE '%"+idpas+"%'");
                    Con.Reset2 = Con.PStatem2.executeQuery();
                    while(Con.Reset2.next()){
                        ModelRekam mr = new ModelRekam(Con.Reset2.getString("idrekam"),nama,Con.Reset2.getString("tgl"),Con.Reset2.getString("penyakit"));
                        listData.add(mr);
                    }
                } catch (SQLException ex) {
                }
            }
            Con.Close();
        } catch (SQLException ex) {}
        return listData;
    }
    
    private ObservableList LoadDBPasien() {
        ObservableList ListPasien = FXCollections.observableArrayList();
        String nama = null;
        try {
            Con.Open();
            Con.PStatem = Con.dbKoneksi.prepareStatement("SELECT * FROM diagnosa ORDER BY idrekam ASC");
            Con.Reset = Con.PStatem.executeQuery();
            while(Con.Reset.next()) {
                try {
                    Con.PStatem2 = Con.dbKoneksi.prepareStatement("SELECT * FROM pasien WHERE idpas = '" + Con.Reset.getString("idpas") + "'");
                    Con.Reset2 = Con.PStatem2.executeQuery();
                    while(Con.Reset2.next()) {
                        nama = Con.Reset2.getString("nama");
                    }
                } catch (SQLException ex) {}
                ModelRekam mr = new ModelRekam(Con.Reset.getString("idrekam"),nama,Con.Reset.getString("tgl"),Con.Reset.getString("penyakit"));
                ListPasien.add(mr);
            }
            Con.Close();
        } catch (SQLException ex) {}
        return ListPasien;
    }
    
    private void showListPasien() {
        data.clear();
        data = LoadDBPasien();
        tblrekam.setItems(data);
        tblrekam.getSelectionModel().clearSelection();
        colid.setCellValueFactory(new PropertyValueFactory("Idrekam"));
        colnama.setCellValueFactory(new PropertyValueFactory("Nama"));
        coltgl.setCellValueFactory(new PropertyValueFactory("Tgl"));
        colpenyakit.setCellValueFactory(new PropertyValueFactory("Penyakit"));
    }
    
    private void PilihData() {
        try {
            ModelRekam mr = (ModelRekam) tblrekam.getSelectionModel().getSelectedItems().get(0);
            vid.setText(mr.getIdrekam());
            vtgl.setText(mr.getTgl());
            vnama.setText(mr.getNama());
            vpenyakit.setText(mr.getPenyakit());
            try {
                Con.Open();
                Con.PStatem = Con.dbKoneksi.prepareStatement("SELECT * FROM diagnosa WHERE idrekam = '" + vid.getText() + "'");
                Con.Reset = Con.PStatem.executeQuery();
                while (Con.Reset.next()) {
                    vgejala.setText(Con.Reset.getString("gejala"));
                }
                Con.Close();
            } catch (SQLException v) {}
            vid.setEditable(false);
            vtgl.setEditable(false);
            vnama.setEditable(false);
            vgejala.setEditable(false);
            vpenyakit.setEditable(false);
        } catch(Exception e) {
            Con.showAlert(Alert.AlertType.WARNING,"Messages",null,"Data Tidak Ada!");
        }
    }
    
    private void Kosong() {
        vid.clear();
        vtgl.clear();
        vnama.clear();
        vgejala.clear();
        vpenyakit.clear();
        vcari.clear();
    }
    
}
