package controller;

import model.ModelPenyakit;
import model.ModelGejala;
import model.ModelRule;
import com.jfoenix.controls.JFXButton;
import connection.BridgeCon;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import styles.AnimFadeInRightTransition;



/**
 * FXML Controller class
 *
 * @author Stenly Andika
 */
public class ControllerRule implements Initializable {

    @FXML private AnchorPane blur;
    @FXML private RadioButton yasebelumnya,tidaksebelumnya,afteryes,afterno;;
    @FXML private ComboBox<String> cPilihRule;
    @FXML private ComboBox<String> tipebaru,ke_tabel;
    @FXML private TextField pertanyaansebelumnya,pertanyaansekarang,vcari;
    @FXML private TextField kodesebelumnya,kodebaru,kodesekarang,tipesekarang,koderule,kodekonten,kontenbaru;
    @FXML private Label pertanyaan,labeltipebaru,aqgbaru,aqgsebelumnya;
    @FXML private JFXButton bya,bkembali,btidak,bok,bchange;
    @FXML private JFXButton bdatabaru,bataldata,beditdata,simpandata,bhapusdata;
    @FXML private JFXButton bataltabel,simpantabel,bdiagnosisbaru,bhapusdiagnosis;
    @FXML private TableView<?> tblGejala,tblPenyakit;
    @FXML private TableColumn<?, ?> colKode,colGejala,colKodep,colPenyakit;
    private ToggleGroup tgb;
    private ToggleGroup tga;
    
    ModelGejala modgejala = new ModelGejala();
    ModelPenyakit modpenyakit = new ModelPenyakit();
    ModelRule modrule = new ModelRule();
    BridgeCon Con = new BridgeCon();
    ObservableList data = FXCollections.observableArrayList("Question","Answer","Go To");
    ObservableList dataGejala = FXCollections.observableArrayList();
    ObservableList dataPenyakit = FXCollections.observableArrayList();
    boolean addNoPrev;
    private String dataOption;
    private String YesOrNope;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        YesOrNope = "[F]";
        bya.setStyle("-fx-background-color : white;"+"-fx-text-fill : #46a445;");
        btidak.setStyle("-fx-background-color : white;"+"-fx-text-fill : #f20606;");
        bkembali.setStyle("-fx-background-color : white;"+"-fx-text-fill : #438ae1;");
        bya.setOnMouseEntered(e->{
            bya.setStyle("-fx-background-color : #00B6FF;"+"-fx-text-fill : white;");
        });
        bya.setOnMouseExited(e->{
            bya.setStyle("-fx-background-color : white;"+"-fx-text-fill : #46a445;");
        });
        bkembali.setOnMouseEntered(e->{
            bkembali.setStyle("-fx-background-color : #00B6FF;"+"-fx-text-fill : white;");
        });
        bkembali.setOnMouseExited(e->{
            bkembali.setStyle("-fx-background-color : white;"+"-fx-text-fill : #438ae1;");
        });
        btidak.setOnMouseEntered(e->{
            btidak.setStyle("-fx-background-color : #00B6FF;"+"-fx-text-fill : white;");
        });
        btidak.setOnMouseExited(e->{
            btidak.setStyle("-fx-background-color : white;"+"-fx-text-fill : #f20606;");
        });
        
        bchange.setOnAction(e->{
            try {
                Parent database_parent = FXMLLoader.load(getClass().getResource("/view/FormRuleAdvanced.fxml"));
                blur.getChildren().clear();
                blur.setOpacity(0);
                new AnimFadeInRightTransition(blur).play();
                blur.getChildren().setAll(database_parent);
            } catch (IOException ui) {}
        });
        
        kodesekarang.setEditable(false);
        kodesebelumnya.setEditable(false);
        pertanyaansebelumnya.setEditable(false);
        kodekonten.setEditable(false);
        tipesekarang.setEditable(false);
        pertanyaansekarang.setEditable(false);
        kontenbaru.setEditable(false);
        kodebaru.setEditable(false);

        disableData(true);
        disableDiagnosis(true);
        disableEditData(true);
        disableButton(true);
        tblPenyakit.setVisible(false);
        cPilihRule.setValue("[Pilih Diagnosis]");
        tipebaru.setItems(data);
        cPilihRule.setDisable(false);
        bok.setDisable(false);
        bdiagnosisbaru.setDisable(false);
        cPilihRule.setItems(modrule.LoadDataRule());
        tgb = new ToggleGroup();
        tga = new ToggleGroup();
        yasebelumnya.setToggleGroup(tgb);tidaksebelumnya.setToggleGroup(tgb);
        afteryes.setToggleGroup(tga);afterno.setToggleGroup(tga);
        
        vcari.setOnKeyReleased(e->{
            dataGejala.clear();
            dataGejala = LoadDBGejala("SELECT DISTINCT * FROM gejala WHERE kode LIKE '%" + vcari.getText().trim() + "%' OR gejala LIKE '%" + vcari.getText().trim() + "%'");
            tblGejala.setItems(dataGejala);
            tblGejala.getSelectionModel().clearSelection();
        });
        
        koderule.setDisable(true);
        bdiagnosisbaru.setOnAction(e->{
            simpantabel.setDisable(false);
            bataltabel.setDisable(false);
            koderule.setDisable(false);
            koderule.requestFocus();
        });
        
        tblGejala.setOnMousePressed(e->{
            PilihKodeGejala();
        });
        
        tblPenyakit.setOnMousePressed(e->{
            PilihKodePenyakit();
        });
        
        simpantabel.setOnAction(e->{
            if (koderule.getText().isEmpty()) {
                Con.showAlert(Alert.AlertType.INFORMATION, "Message", null, "Harap Lengkapi Data Terlebih Dahulu");
            } else {
                modrule.newDetection(koderule.getText().toLowerCase());
                cPilihRule.setItems(modrule.LoadDataRule());
                disableDiagnosis(true);
            }
        });
        
        bok.setOnAction(e->{
            if(cPilihRule.getSelectionModel().getSelectedItem().equals("[Pilih Diagnosis]") ){
                Con.showAlert(Alert.AlertType.INFORMATION, "Message", null, "Harap Pilih Diagnosis Terlebih Dahulu");
            }else{
                modrule.start(String.valueOf(cPilihRule.getSelectionModel().getSelectedItem()));
                setAll();
                bkembali.setDisable(true);
            }
        });
        
        bataldata.setOnAction(e->{
            disableData(true);
        });
        
        bataltabel.setOnAction(e->{
            disableDiagnosis(true);
        });
        
        bya.setOnAction(e->{
            modrule.yes();
            setAll();
            YesOrNope = "[YES]";
        });
        
        btidak.setOnAction(e->{
            modrule.no();
            setAll();
            YesOrNope = "[NO]";
        });
        
        bkembali.setOnAction(e->{
            modrule.back();
            setAll();
            disableData(true);
        });
        
        bhapusdata.setOnAction(e->{
            modrule.delete_all_after(modrule.getNowTable(), modrule.getNowIndex(), "Delete");
            modrule.delete(modrule.getNowTable(), modrule.getNowIndex());
            modrule.refresh();
            setAll();
        });
        
        beditdata.setOnAction(e->{
            disableEditData(false);
            dataOption = "editData";
            if (modrule.getNowIndex().length()>1) {
                yasebelumnya.setSelected(true);
                yasebelumnya.setDisable(false);
                tidaksebelumnya.setSelected(false);
                tidaksebelumnya.setDisable(true);
            } else {
                yasebelumnya.setSelected(false);
                yasebelumnya.setDisable(true);
                tidaksebelumnya.setSelected(true);
                tidaksebelumnya.setDisable(false);
            }
            kodebaru.setText(modrule.getNowIndex());
            tipebaru.setValue("");
            ke_tabel.setItems(modrule.LoadDataRule());
            tipebaru.setItems(data);

            if(modrule.getNowType().equals("Q")){
                tipebaru.getSelectionModel().select(0);
                kodesebelumnya.setText(modrule.getNowType());
                pertanyaansebelumnya.setText(modrule.getNowContent());
            }else if(modrule.getNowType().equals("A")){
                tipebaru.getSelectionModel().select(1);
                kodesebelumnya.setText(modrule.getNowType());
                pertanyaansebelumnya.setText(modrule.getNowContent());
            }else if(modrule.getNowType().substring(1, 1).equals("G")){
                tipebaru.getSelectionModel().select(2);
                kodesebelumnya.setText(modrule.getNowType());
                pertanyaansebelumnya.setText(modrule.getNowContent());
            }
            
            String yesornope = String.valueOf(modrule.getNowIndex().charAt(modrule.getNowIndex().length() - 1));
            if (yesornope.equals("1")) {
                afteryes.setSelected(true);
                afterno.setDisable(true);
            } else {
                afterno.setSelected(true);
                afteryes.setDisable(true);
            }
        });
        
        tipebaru.setOnAction(e->{
            if (dataOption.equals("editData")) {
                SelectNewType();
            } else if (dataOption.equals("addNew")) {
                SelectNewType();
            }
        });
            
        ke_tabel.setOnAction(e->{
            if(tipebaru.getSelectionModel().getSelectedIndex()==2){
                labeltipebaru.setText("G");
                kontenbaru.setText("G." +modrule.getTable(String.valueOf(ke_tabel.getSelectionModel().getSelectedItem())));
            }
        });
                
        bdatabaru.setOnAction(e->{
            disableData(false);
            
            if ("1".equals(modrule.getNowIndex())) {
                kodebaru.setText("1");
            } else {
                kodebaru.setText(modrule.getNowIndex());
            }
            
            dataOption = "addNew";
            Con.Open();
            try {
                Con.PStatem = Con.dbKoneksi.prepareStatement("SELECT * FROM " + modrule.getNowTable() + "");
                Con.Reset = Con.PStatem.executeQuery();
                if(Con.Reset.next()) {
                    yasebelumnya.setSelected(true);
                    tidaksebelumnya.setDisable(true);
                    if (YesOrNope.equals("[YES]")) {
                        afteryes.setSelected(true);
                    } else if (YesOrNope.equals("[NO]")) {
                        afterno.setSelected(true);
                    }
                    tipebaru.setValue("");
                    ke_tabel.setItems(modrule.LoadDataRule());
                    tipebaru.getSelectionModel().select(0);
                    String getnowIdx = modrule.getNowIndex().substring(0, modrule.getNowIndex().length()-1);
                    kodesebelumnya.setText(String.valueOf(getnowIdx));
                    
                    String nowTbl = cPilihRule.getSelectionModel().getSelectedItem().toLowerCase();
                    String lastIdx = modrule.getNowIndex().substring(0, modrule.getNowIndex().length()-1);
                    pertanyaansebelumnya.setText(Con.getContent(nowTbl, lastIdx));
                    if ("G".equals(pertanyaansebelumnya.getText().substring(0,1))) {
                        aqgsebelumnya.setText("Kode Gejala :");
                    } else {
                        aqgsebelumnya.setText("Kode Penyakit :");
                    }
                    if (afteryes.isSelected()){
                        kodebaru.setText(kodesebelumnya.getText() +"1");
                        if (modrule.dataFound(modrule.getNowTable(), kodebaru.getText())){
                            afteryes.setSelected(false);
                            afterno.setSelected(false);
                        }
                    }else if (afterno.isSelected()){
                        kodebaru.setText(kodesebelumnya.getText() +"0");
                        if (modrule.dataFound(modrule.getNowTable(), kodebaru.getText())){
                            afteryes.setSelected(false);
                            afterno.setSelected(false);
                        }
                    }
                } else {
                    tidaksebelumnya.setSelected(true);
                    yasebelumnya.setDisable(true);
                    
                    tipebaru.setValue("");
                    ke_tabel.setItems(modrule.LoadDataRule());
                    tipebaru.getSelectionModel().select(0);
                    tipebaru.setDisable(true);
                    vcari.setDisable(false);
                    
                    afteryes.setSelected(false);
                    afteryes.setDisable(true);
                    afterno.setSelected(false);
                    afterno.setDisable(true);
                }
                Con.Close();
            } catch (SQLException nt) {
                
            }
        });
                
        simpandata.setOnAction(e->{
            if(dataOption.equals("addNew")) {
                modrule.basicAdd(modrule.getNowTable(), kodebaru.getText(), labeltipebaru.getText(), kontenbaru.getText());
            } else if(dataOption.equals("editData")) {
                modrule.basicEdit(modrule.getNowTable(), modrule.getNowIndex(), modrule.getNowType(), labeltipebaru.getText(), kontenbaru.getText());
            }
            modrule.refresh();
            setAll();
            disableData(true);
            dataGejala.clear();
        });
        
        bhapusdiagnosis.setOnAction(e->{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Messages");
            alert.setHeaderText(null);
            alert.setContentText("Hapus Rule Dengan Kode : " + cPilihRule.getSelectionModel().getSelectedItem() + " ?");
            alert.showAndWait();
            if(alert.getResult()==ButtonType.OK)
            {
                modrule.deleteDetection(modrule.getNowTable());
                pertanyaan.setText("");
                kodesekarang.setText("");
                tipesekarang.setText("");
                kodekonten.setText("");
                pertanyaansekarang.setText("");
                bya.setDisable(true);
                bkembali.setDisable(true);
                btidak.setDisable(true);
                bdatabaru.setDisable(true);
                beditdata.setDisable(true);
                bhapusdata.setDisable(true);
                bataldata.setDisable(true);
                bhapusdiagnosis.setDisable(true);
                cPilihRule.setItems(modrule.LoadDataRule());
            }
        });
    }
    
    private void PilihKodeGejala() {
        try {
            modgejala = (ModelGejala) tblGejala.getSelectionModel().getSelectedItems().get(0);
            kontenbaru.setText(modgejala.getKode());
        } catch(Exception e) {
            Con.showAlert(Alert.AlertType.WARNING,"Messages",null,"Data Tidak Ada!");
        }
    }
    
    private void PilihKodePenyakit() {
        try {
            modpenyakit = (ModelPenyakit) tblPenyakit.getSelectionModel().getSelectedItems().get(0);
            kontenbaru.setText(modpenyakit.getKode());
        } catch(Exception e) {
            Con.showAlert(Alert.AlertType.WARNING,"Messages",null,"Data Tidak Ada!");
        }
    }

    public void setAll(){
        disableButton(false);
        
        pertanyaan.setDisable(false);
        pertanyaan.setDisable(false);
        cPilihRule.setDisable(false);
        beditdata.setDisable(false);

        if(modrule.getNowContent()==null){
            bya.setDisable(true);
            btidak.setDisable(true);
            bkembali.setDisable(false);
            beditdata.setDisable(true);
            bdatabaru.setDisable(false);
            bdiagnosisbaru.setDisable(false);
            bhapusdiagnosis.setDisable(false);
            bhapusdata.setDisable(true);
                
            pertanyaan.setText("Data tidak ditemukan. Klik \"Data Baru\" untuk membuatnya !");
            kodesekarang.setText("Data tidak ditemukan. Klik \"Data Baru\" untuk membuatnya !");
            kodekonten.setText("Data tidak ditemukan. Klik \"Data Baru\" untuk membuatnya !");
            tipesekarang.setText("Data tidak ditemukan. Klik \"Data Baru\" untuk membuatnya !");
            pertanyaansekarang.setText("Data tidak ditemukan. Klik \"Data Baru\" untuk membuatnya !");
        }else{
            cPilihRule.setValue(modrule.getNowTable().toUpperCase());
            bdatabaru.setDisable(true);
            kodekonten.setText(modrule.getNowContent());
            kodesekarang.setText(modrule.getNowIndex());
            tipesekarang.setText(modrule.getNowType());
            pertanyaansekarang.setText(modrule.getNowQuestion());
        
            if (modrule.getNowIndex().length()<2) {
                bkembali.setDisable(true);
            } else {
                bkembali.setDisable(false);
            }

            if (modrule.getNowType().equals("A")) {
                bya.setDisable(true); btidak.setDisable(true);
                bya.setDisable(true);
                pertanyaan.setText("Anda Mengidap Penyakit "+modrule.getNowQuestion() + "");
                btidak.setDisable(true);
            }else if(modrule.getNowType().equals("Q")) {
                bya.setDisable(false);
                btidak.setDisable(false);
                pertanyaan.setText("Apakah "+modrule.getNowQuestion() + " ?");
            }else if (modrule.getNowType().startsWith("G")) {
                btidak.setDisable(true);
                bya.setDisable(false);
                pertanyaan.setText("Lanjutkan Ke Rule Berikutnya");
            }
        }
    }
    
    private void SelectNewType() {
        if (YesOrNope.equals("[YES]")) {
            afteryes.setDisable(false);
            afterno.setDisable(true);
        } else if (YesOrNope.equals("[NO]")) {
            afteryes.setDisable(true);
            afterno.setDisable(false);
        } else {
            afteryes.setDisable(true);
            afterno.setDisable(true);
        }
        if(yasebelumnya.isSelected()){
            switch (tipebaru.getSelectionModel().getSelectedItem()) {
                case "Question":
                    ke_tabel.setDisable(true);
                    labeltipebaru.setText("Q");
                    aqgbaru.setText("Kode Gejala :");
                    ShowListGejala();
                    tblPenyakit.setVisible(false);
                    tblGejala.setVisible(true);
                    tblGejala.setDisable(false);
                    vcari.setDisable(false);
                    break;
                case "Answer":
                    ke_tabel.setDisable(true);
                    labeltipebaru.setText("A");
                    aqgbaru.setText("Kode Penyakit :");
                    ShowListPenyakit();
                    tblGejala.setVisible(false);
                    tblPenyakit.setVisible(true);
                    tblPenyakit.setDisable(false);
                    vcari.setDisable(true);
                    break;
                case "Go To":
                    ke_tabel.setDisable(false);
                    labeltipebaru.setText("G");
                    kontenbaru.setText("G.");
                    aqgbaru.setText("Ke Rule :");
                    tblGejala.getItems().clear();
                    tblPenyakit.getItems().clear();
                    tblGejala.setDisable(true);
                    tblPenyakit.setDisable(true);
                    vcari.setDisable(true);
                    ke_tabel.requestFocus();
                    break;
                default:
                    break;
            }
        } else if (tidaksebelumnya.isSelected()) {
            tipebaru.setEditable(false);
            tipebaru.getSelectionModel().select(0);
            switch (tipebaru.getSelectionModel().getSelectedItem()) {
            case "Question":
                ke_tabel.setDisable(true);
                labeltipebaru.setText("Q");
                aqgbaru.setText("Kode Gejala :");
                ShowListGejala();
                tblPenyakit.setVisible(false);
                tblGejala.setVisible(true);
                vcari.setDisable(false);
                break;
            default:
                break;
            }
        }
    }
    
    private void disableData(boolean enabled) {
        aqgsebelumnya.setText("Aturan");
        aqgbaru.setText("Aturan");
        dataGejala.clear();
        tblGejala.setDisable(enabled);
        dataPenyakit.clear();
        tblPenyakit.setDisable(enabled);
        
        kodesebelumnya.setDisable(enabled);
        yasebelumnya.setDisable(enabled);
        afteryes.setDisable(enabled);
        afterno.setDisable(enabled);
        tidaksebelumnya.setDisable(enabled);
        pertanyaansebelumnya.setDisable(enabled);
        kodebaru.setDisable(enabled);
        tipebaru.setDisable(enabled);
        ke_tabel.setDisable(enabled);
        labeltipebaru.setDisable(enabled);
        kontenbaru.setDisable(enabled);
        
        yasebelumnya.setSelected(false);
        tidaksebelumnya.setSelected(false);
        afteryes.setSelected(false);
        afterno.setSelected(false);
        kodesebelumnya.clear();
        pertanyaansebelumnya.clear();
        kodebaru.clear();
        labeltipebaru.setText("");
        kontenbaru.clear();
        vcari.clear();
        vcari.setPromptText("Cari Gejala");
        vcari.setDisable(true);
        
        bataldata.setDisable(enabled);
        simpandata.setDisable(enabled);
    }
    
    private void disableEditData(boolean enabled) {
        tblGejala.setDisable(enabled);
        tblPenyakit.setDisable(enabled);
        kodebaru.setDisable(enabled);
        tipebaru.setDisable(enabled);
        labeltipebaru.setDisable(enabled);
        kontenbaru.setDisable(enabled);
        ke_tabel.setDisable(enabled);
        kodesebelumnya.setDisable(enabled);
        yasebelumnya.setDisable(enabled);
        tidaksebelumnya.setDisable(enabled);
        afteryes.setDisable(enabled);
        afterno.setDisable(enabled);
        
        kodebaru.clear();
        labeltipebaru.setText("");
        kontenbaru.clear();
        
        bataldata.setDisable(enabled);
        simpandata.setDisable(enabled);
    }
    
    private void disableDiagnosis(boolean enabled) {
        koderule.setDisable(enabled);
        koderule.clear();
        simpantabel.setDisable(enabled);
        bataltabel.setDisable(enabled);
    }
    
    private void disableButton(boolean enabled) {
        bok.setDisable(enabled);
        bya.setDisable(enabled);
        bkembali.setDisable(enabled);
        btidak.setDisable(enabled);
        bdatabaru.setDisable(enabled);
        beditdata.setDisable(enabled);
        bhapusdata.setDisable(enabled);
        bdiagnosisbaru.setDisable(enabled);
        bhapusdiagnosis.setDisable(enabled);
    }
    
    public ObservableList LoadDBGejala(String query) {
        ObservableList ListGejala = FXCollections.observableArrayList();
        try {
            Con.Open();
            Con.PStatem = Con.dbKoneksi.prepareStatement(query);
            Con.Reset = Con.PStatem.executeQuery();
            while(Con.Reset.next()) {
                ModelGejala mgejala = new ModelGejala(Con.Reset.getString(1),Con.Reset.getString(2));
                ListGejala.add(mgejala);
            }
            Con.Close();
        } catch (SQLException ex) {
        }
        return ListGejala;
    }
    
    private void ShowListGejala() {
        dataGejala.clear();
        dataGejala = LoadDBGejala("SELECT * FROM gejala ORDER BY Kode");
        tblGejala.setItems(dataGejala);
        tblGejala.getSelectionModel().clearSelection();
        colKode.setCellValueFactory(new PropertyValueFactory("Kode"));
        colGejala.setCellValueFactory(new PropertyValueFactory("Gejala"));
    }
    
    public ObservableList LoadDBPenyakit() {
        ObservableList ListPenyakit = FXCollections.observableArrayList();
        try {
            Con.Open();
            Con.PStatem = Con.dbKoneksi.prepareStatement("SELECT * FROM penyakit ORDER BY Kode");
            Con.Reset = Con.PStatem.executeQuery();
            while(Con.Reset.next()) {
                ModelPenyakit mpenyakit = new ModelPenyakit(Con.Reset.getString(1),Con.Reset.getString(2),Con.Reset.getString(3),Con.Reset.getString(4));
                ListPenyakit.add(mpenyakit);
            }
            Con.Close();
        } catch (SQLException ex) {
        }
        return ListPenyakit;
    }
    
    private void ShowListPenyakit() {
        dataPenyakit.clear();
        dataPenyakit = LoadDBPenyakit();
        tblPenyakit.setItems(dataPenyakit);
        tblPenyakit.getSelectionModel().clearSelection();
        colKodep.setCellValueFactory(new PropertyValueFactory("Kode"));
        colPenyakit.setCellValueFactory(new PropertyValueFactory("Penyakit"));
    }
    
}
