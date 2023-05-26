package controller;

import model.ModelDiagnosa;
import com.jfoenix.controls.JFXAutoCompletePopup;
import com.jfoenix.controls.JFXButton;
import connection.BridgeCon;
import java.io.InputStream;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
public class ControllerDiagnosa implements Initializable {

    BridgeCon Con = new BridgeCon();
    ModelDiagnosa mdiagno = new ModelDiagnosa();
    String YesOrNope;
    @FXML private AnchorPane blur;
    @FXML private TextField vnama, valamat, vumur, vidpas, vjml;
    @FXML private RadioButton rlaki, rperem, rbaru, rlama;
    @FXML private DatePicker vtgl;
    @FXML private JFXButton vmulai, vedit, vdiagnobaru, vya, vkembali, vtidak, vcetak, vcek;
    @FXML private TextArea vsaran;
    @FXML private Label vquestion, vouttgl, voutnama, voutumur, voutjekel, voutalamat, voutid, voutjml;
    
    SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
    String idrekam, gender="";
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        JFXAutoCompletePopup<String> autoCompletePopup = new JFXAutoCompletePopup<>();
        autoCompletePopup.getSuggestions().addAll(LoadDBPasien());
    
        autoCompletePopup.setSelectionHandler(event -> {
            vnama.setText(event.getObject());
        });

        vnama.textProperty().addListener(o -> {
            autoCompletePopup.filter(string -> string.toLowerCase().contains(vnama.getText().toLowerCase()));
            if (autoCompletePopup.getFilteredSuggestions().isEmpty() || vnama.getText().isEmpty()) {
                autoCompletePopup.hide();
            } else {
                autoCompletePopup.show(vnama);
            }
        });
    
        ToggleGroup jekel = new ToggleGroup();
        rlaki.setToggleGroup(jekel);
        rperem.setToggleGroup(jekel);
        ToggleGroup jenis = new ToggleGroup();
        rlama.setToggleGroup(jenis);
        rbaru.setToggleGroup(jenis);
        vsaran.setEditable(false);
        vjml.setEditable(false);
        Kosong();
        Refresh();
        
        vya.setStyle("-fx-background-color : white;"+"-fx-text-fill : #46a445;");
        vtidak.setStyle("-fx-background-color : white;"+"-fx-text-fill : #f20606;");
        vkembali.setStyle("-fx-background-color : white;"+"-fx-text-fill : #438ae1;");
        vcetak.setStyle("-fx-background-color : white;"+"-fx-text-fill : #f20606;");
        vcek.setStyle("-fx-background-color : white;"+"-fx-text-fill : #438ae1;");
        vya.setOnMouseEntered(e->{
            vya.setStyle("-fx-background-color : #00B6FF;"+"-fx-text-fill : white;");
        });
        vya.setOnMouseExited(e->{
            vya.setStyle("-fx-background-color : white;"+"-fx-text-fill : #46a445;");
        });
        vkembali.setOnMouseEntered(e->{
            vkembali.setStyle("-fx-background-color : #00B6FF;"+"-fx-text-fill : white;");
        });
        vkembali.setOnMouseExited(e->{
            vkembali.setStyle("-fx-background-color : white;"+"-fx-text-fill : #438ae1;");
        });
        vtidak.setOnMouseEntered(e->{
            vtidak.setStyle("-fx-background-color : #00B6FF;"+"-fx-text-fill : white;");
        });
        vtidak.setOnMouseExited(e->{
            vtidak.setStyle("-fx-background-color : white;"+"-fx-text-fill : #f20606;");
        });
        vcetak.setOnMouseEntered(e->{
            vcetak.setStyle("-fx-background-color : #00B6FF;"+"-fx-text-fill : white;");
        });
        vcetak.setOnMouseExited(e->{
            vcetak.setStyle("-fx-background-color : white;"+"-fx-text-fill : #f20606;");
        });
        vcek.setOnMouseEntered(e->{
            vcek.setStyle("-fx-background-color : #00B6FF;"+"-fx-text-fill : white;");
        });
        vcek.setOnMouseExited(e->{
            vcek.setStyle("-fx-background-color : white;"+"-fx-text-fill : #438ae1;");
        });
        
        rlaki.setOnAction(e->{
            gender = "L";
        });
        
        rperem.setOnAction(e->{
            gender = "P";
        });
        
        vumur.setOnKeyReleased(e->{
            if (!vumur.getText().matches("[0-9]*")){
                Con.showAlert(Alert.AlertType.WARNING, "Messages", null, "Harap Masukkan Hanya Angka!");
                vumur.setText("");
                vumur.requestFocus();
            }
        });
        
        vdiagnobaru.setOnAction(e->{
            Kosong();
            Refresh();
            mdiagno.tempGejala.clear();
            FieldStatus(false);
            vnama.requestFocus();
        });
        
        vcetak.setOnAction(e->{
            Con.Open();
            try{
                InputStream urlreport = getClass().getResourceAsStream("/report/LaporanDiagnosa.jasper");
                HashMap Param = new HashMap();
                Param.put("idrec", idrekam);
                JasperReport jreprt=(JasperReport)JRLoader.loadObject(urlreport);
                JasperPrint jprintt=JasperFillManager.fillReport(jreprt,Param,Con.dbKoneksi);
                JasperViewer.viewReport(jprintt,false);
            }catch (JRException n){
                Con.showAlert(Alert.AlertType.INFORMATION,"Messages",null,"Gagal Membuka Laporan!");
            }
            Con.Close();
        });
        
        vedit.setOnAction(e->{
            FieldStatus(false);
        });
        
        vnama.setOnAction(e->{
            try {
                Con.Open();
                Con.PStatem = Con.dbKoneksi.prepareStatement("SELECT * FROM pasien WHERE nama = '" + vnama.getText() + "'");
                Con.Reset = Con.PStatem.executeQuery();
                if(Con.Reset.next()) {
                    vidpas.setText(Con.Reset.getString("idpas"));
                    valamat.setText(Con.Reset.getString("alamat"));
                    if (Con.Reset.getString("jekel").equals("L")) {
                        rlaki.setSelected(true);
                    } else {
                        rperem.setSelected(true);
                    }
                    vumur.setText(Con.Reset.getString("umur"));
                    vedit.setDisable(false);
                    FieldStatus(true);
                    rlama.setSelected(true);
                    gender = "L";
                } else {
                    rbaru.setSelected(true);
                    gender = "P";
                    valamat.requestFocus();
                }
                Con.Close();
            } catch (SQLException ex) {
            }
        });
        
        vmulai.setOnAction(e->{
            try {
                Con.Open();
                Con.PStatem = Con.dbKoneksi.prepareStatement("SELECT COUNT(idpas) AS LazyBoy FROM diagnosa WHERE idpas = '" + vidpas.getText() + "'");
                Con.Reset = Con.PStatem.executeQuery();
                if (Con.Reset.next()) {
                    vjml.setText(String.valueOf(Con.Reset.getInt("LazyBoy")+1));
                } else {
                    vjml.setText("1");
                }
                Con.Close();
            } catch (SQLException end) {}
            if (vnama.getText().isEmpty()||valamat.getText().isEmpty()||vumur.getText().isEmpty()||gender.isEmpty()) {
                Con.showAlert(Alert.AlertType.INFORMATION,"Messages",null,"Silakan Lengkapi Data Pasien Terlebih Dahulu!");
            }else {
                mdiagno.start();
                setAll();
                vdiagnobaru.setText("Batal Diagnosis");
            }
        });
        
        vya.setOnAction(e->{
            mdiagno.yes();
            setAll();
            YesOrNope = "[YES]";
        });
        
        vtidak.setOnAction(e->{
            mdiagno.no();
            setAll();
            YesOrNope = "[NO]";
        });
        
        vkembali.setOnAction(e->{
            mdiagno.back();
            setAll();
        });
        
        vcek.setOnAction(e->{
            String hasil,name = null, info = null, saran = null;
            String recordGejala = "",recordReport = "";
            for (int i=0;i<mdiagno.tempGejala.size();i++){
                hasil = String.valueOf(mdiagno.tempGejala.get(i));
                try {
                    Con.Open();
                    Con.PStatem = Con.dbKoneksi.prepareStatement("SELECT * FROM gejala WHERE kode ='" + hasil + "'");
                    Con.Reset = Con.PStatem.executeQuery();
                    if (Con.Reset.next()) {
                        name = Con.Reset.getString("gejala");
                    }
                    Con.Close();
                } catch (SQLException ux) {}
                if ("".equals(recordGejala)) {
                    recordGejala = hasil + "|" + name + "\n";
                } else {
                    recordGejala = recordGejala + hasil + "|" + name + "\n";
                }
                if ("".equals(recordReport)) {
                    recordReport = "- " + name + "\n";
                } else {
                    recordReport = recordReport + "- " + name + "\n";
                }
            }
            try {
                Con.Open();
                Con.PStatem = Con.dbKoneksi.prepareStatement("SELECT * FROM penyakit WHERE kode ='" + mdiagno.getNowContent() + "'");
                Con.Reset = Con.PStatem.executeQuery();
                if (Con.Reset.next()) {
                    info = Con.Reset.getString("info");
                    saran = Con.Reset.getString("solusi");
                }
                Con.Close();
            } catch (SQLException ux) {}
            voutid.setText("ID : "+vidpas.getText());
            voutnama.setText(vnama.getText());
            vouttgl.setText(vtgl.getValue().toString());
            voutumur.setText(vumur.getText());
            if (rlaki.isSelected()) {
                voutjekel.setText("Laki-Laki");
            } else {
                voutjekel.setText("Perempuan");
            }
            voutalamat.setText(valamat.getText());
            voutjml.setText(vjml.getText());
            vsaran.setText("Pasien Mengidap : "+mdiagno.getFinResult()+" \n"
                    + "\n"
                    + "Tentang Penyakit : \n"
                    + info
                    + "\n"
                    + "\nGejala Yang Dialami Pasien : "
                    + "\n"+recordReport
                    + "\nSaran Pengobatan :\n"
                    + saran);
            
            try {
                Con.Open();
                Con.PStatem = Con.dbKoneksi.prepareStatement("SELECT * FROM pasien WHERE idpas ='" + vidpas.getText() +"'");
                Con.Reset = Con.PStatem.executeQuery();
                if (Con.Reset.next()) {
                    try {
                        Con.PStatem = Con.dbKoneksi.prepareStatement("UPDATE pasien SET nama=?, alamat=?, jekel=?, umur=? WHERE idpas=?");
                        Con.PStatem.setString(5, vidpas.getText());
                        Con.PStatem.setString(1, vnama.getText());
                        Con.PStatem.setString(2, valamat.getText());
                        Con.PStatem.setString(3, gender);
                        Con.PStatem.setString(4, vumur.getText());
                        Con.PStatem.execute();
                    } catch (SQLException s) {}
                } else {
                    try {
                        Con.PStatem = Con.dbKoneksi.prepareStatement("INSERT INTO pasien VALUES (?,?,?,?,?)");
                        Con.PStatem.setString(1, vidpas.getText());
                        Con.PStatem.setString(2, vnama.getText());
                        Con.PStatem.setString(3, valamat.getText());
                        Con.PStatem.setString(4, gender);
                        Con.PStatem.setString(5, vumur.getText());
                        Con.PStatem.execute();
                    } catch (SQLException u) {}
                }
                Con.Close();
            } catch (SQLException io) {}
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMMM yyyy");
            try {
                Con.Open();
                Con.PStatem = Con.dbKoneksi.prepareStatement("INSERT INTO diagnosa VALUES (?,?,?,?,?,?)");
                Con.PStatem.setString(1, idrekam);
                Con.PStatem.setString(2, vidpas.getText());
                Con.PStatem.setDate(3, Date.valueOf(vtgl.getValue()));
                Con.PStatem.setString(4, String.valueOf(recordReport));
                Con.PStatem.setString(5, mdiagno.getFinResult());
                Con.PStatem.setString(6, vjml.getText());
                Con.PStatem.execute();
                Con.Close();
            } catch (SQLException r) {}
            Kosong();
            FieldStatus(true);
            disableButton(true);
            vcetak.setDisable(false);
            vcek.setDisable(true);
        });
        
    }   
    
    private ObservableList LoadDBPasien() {
        ObservableList ListPasien = FXCollections.observableArrayList();
        try {
            Con.Open();
            Con.PStatem = Con.dbKoneksi.prepareStatement("SELECT * FROM pasien");
            Con.Reset = Con.PStatem.executeQuery();
            while(Con.Reset.next()) {
                ListPasien.add(Con.Reset.getString("nama"));
            }
            Con.Close();
        } catch (SQLException ex) {
        }
        return ListPasien;
    }
    
    private void setAll(){
        disableButton(false);
        vquestion.setDisable(false);
        if(mdiagno.getNowContent()==null){
            disableButton(true);
            vquestion.setText("Pasien Tidak Terdeteksi Penyakit Paru-Paru");
            vkembali.setDisable(false);
        }else{
            if (mdiagno.getNowIndex().length()<2) {
                vkembali.setDisable(true);
            } else {
                vkembali.setDisable(false);
            }
            if (mdiagno.getNowType().startsWith("A")) {
                AutoNumberingIDRekap();
                disableButton(true);
                vquestion.setText(mdiagno.getNowQuestion());
                Con.showAlert(Alert.AlertType.INFORMATION,"Messages",null,"Diagnosa Selesai"
                        + "\nSilakan Klik Cek Hasil Diagnosa");
                vcek.setDisable(false);
            }else if(mdiagno.getNowType().startsWith("Q")) {
                vya.setDisable(false);
                vtidak.setDisable(false);
                vquestion.setText("Apakah "+mdiagno.getNowQuestion() + " ?");
                vcek.setDisable(true);
            }else if (mdiagno.getNowType().startsWith("G")) {
                vtidak.setDisable(true);
                vya.setDisable(false);
                vquestion.setText("Lanjutkan Ke Rule Berikutnya");
                vcek.setDisable(true);
            }
            
        }
    }
    
    private void disableButton(boolean enabled) {
        vya.setDisable(enabled);
        vtidak.setDisable(enabled);
        vkembali.setDisable(enabled);
        vmulai.setDisable(enabled);
    }
    
    private void setAwal(){
        vya.setDisable(true);
        vtidak.setDisable(true);
        vkembali.setDisable(true);
        vcek.setDisable(true);
        vcetak.setDisable(true);
        vdiagnobaru.setText("Diagnosis Baru");
    }
        
    private void AutoNumberingID() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HHmmss");
        DateTimeFormatter ddf = DateTimeFormatter.ofPattern("ddMMyy");
        LocalDateTime waktu = LocalDateTime.now();
        vidpas.setText(waktu.format(ddf)+waktu.format(dtf));
    }
    
    private void AutoNumberingIDRekap() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HHmmss");
        DateTimeFormatter ddf = DateTimeFormatter.ofPattern("ddMMyy");
        LocalDateTime waktu = LocalDateTime.now();
        idrekam = waktu.format(ddf)+waktu.format(dtf);
    }
    
    private void FieldStatus(boolean enable) {
        vtgl.setDisable(enable);
        vnama.setDisable(enable);
        valamat.setDisable(enable);
        vumur.setDisable(enable);
        rlaki.setDisable(enable);
        rperem.setDisable(enable);
        vjml.setDisable(enable);
    }
    
    private void Kosong() {
        vedit.setDisable(true);
        vidpas.setEditable(false);
        vnama.clear();
        vtgl.setValue(LocalDate.now());
        valamat.clear();
        vjml.clear();
        rlaki.setSelected(false);
        rperem.setSelected(false);
        rbaru.setSelected(true);
        vmulai.setDisable(false);
        vumur.clear();
        gender = "";
        setAwal();
        AutoNumberingID();
    }
    
    private void Refresh() {
        voutid.setText("");
        voutnama.setText("");
        vouttgl.setText("");
        voutalamat.setText("");
        voutjekel.setText("");
        voutumur.setText("");
        vsaran.clear();
        vquestion.setText("");
        voutjml.setText("");
        AutoNumberingID();
    }
    
}
