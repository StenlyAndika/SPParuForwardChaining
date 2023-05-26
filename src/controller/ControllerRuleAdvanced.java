package controller;

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
import javafx.scene.Parent;
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
import javafx.scene.layout.Pane;
import model.ModelGejala;
import model.ModelPenyakit;
import model.ModelRule;
import styles.AnimFadeInRightTransition;

/**
 * FXML Controller class
 *
 * @author Stenly Andika
 */
public class ControllerRuleAdvanced implements Initializable {

    @FXML private AnchorPane blur;
    @FXML private Pane paneEditData;
    @FXML private Label lblsebelum;
    @FXML private RadioButton yasebelumnya, tidaksebelumnya;
    @FXML private Label lblkodes, aqgsebelumnya, lblsetelah;
    @FXML private RadioButton afteryes, afterno;
    @FXML private Label lblkodeb;
    @FXML private TextField kodebaru;
    @FXML private Label lbltpb;
    @FXML private ComboBox<String> tipebaru, ke_tabel;
    @FXML private Label aqgbaru, labeltipebaru;
    @FXML private TableView<?> tblGejala;
    @FXML private TableColumn<?, ?> colKode, colGejala;
    @FXML private Label labellistbaru;
    @FXML private TableView<?> tblPenyakit, tblgejalasekarang;
    @FXML private TableColumn<?, ?> colKodep, colPenyakit, colKodes, colKets;
    @FXML private TextField kontenbaru, pertanyaansebelumnya, kodesebelumnya;
    @FXML private Label lblwarning;
    @FXML private JFXButton bataldata, simpandata, bdatabaruafter, bchange;
    @FXML private JFXButton bdatabaru, beditdata, bhapusdata;
    @FXML private TextField koderule;
    @FXML private JFXButton bataltabel, simpantabel, bdiagnosisbaru, bhapusdiagnosis, bok;
    @FXML private ComboBox<String> cPilihRule;

    private ToggleGroup tgb;
    private ToggleGroup tga;
    private String KodeSekarang;
    private String dataOption,addStatus;
    private String nowKode;
    
    ModelGejala modgejala = new ModelGejala();
    ModelPenyakit modpenyakit = new ModelPenyakit();
    ModelRule modrule = new ModelRule();
    BridgeCon Con = new BridgeCon();
    ObservableList data = FXCollections.observableArrayList("Question","Answer","Go To");
    ObservableList dataGejala = FXCollections.observableArrayList();
    ObservableList dataPenyakit = FXCollections.observableArrayList();
    ObservableList dataSelectedRule = FXCollections.observableArrayList();
    ObservableList dataCurrentRule = FXCollections.observableArrayList();
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        defaultStatus(true);
        tipebaru.setItems(data);
        cPilihRule.setItems(modrule.LoadDataRule());
        tgb = new ToggleGroup();
        tga = new ToggleGroup();
        yasebelumnya.setToggleGroup(tgb);tidaksebelumnya.setToggleGroup(tgb);
        afteryes.setToggleGroup(tga);afterno.setToggleGroup(tga);
        
        bchange.setOnAction(e->{
            try {
                Parent database_parent = FXMLLoader.load(getClass().getResource("/view/FormRule.fxml"));
                blur.getChildren().clear();
                blur.setOpacity(0);
                new AnimFadeInRightTransition(blur).play();
                blur.getChildren().setAll(database_parent);
            } catch (IOException ui) {}
        });
        
        bok.setOnAction(e->{
            if(cPilihRule.getSelectionModel().getSelectedItem().equals("[Pilih Diagnosis]") ){
                Con.showAlert(Alert.AlertType.INFORMATION, "Message", null, "Harap Pilih Diagnosis Terlebih Dahulu");
            }else{
                disableButton(true, true);
                RestoreLayoutY(true);
                tblgejalasekarang.setDisable(false);
                bdatabaru.setDisable(false);
                showSelectedRule();
                disableDiagnosis(false);
            }
        });
        
        afteryes.setOnMouseClicked(e->{
            String curKode;
            String[] tmpKode = new String[99];
            int i=0;
            tmpKode[0] = KodeSekarang+"1";
            curKode = getTmpKode(tmpKode[i]);
            for (int j=0;j<=i;j++) {
                if (curKode == null) {
                    kodebaru.setText(tmpKode[i]);
                } else {
                    i++;
                    tmpKode[i] = curKode + "1";
                    curKode = getTmpKode(tmpKode[i]);
                }
            }
        });
        
        afterno.setOnMouseClicked(e->{
            String curKode;
            String[] tmpKode = new String[99];
            int i=0;
            tmpKode[0] = KodeSekarang+"0";
            curKode = getTmpKode(tmpKode[i]);
            for (int j=0;j<=i;j++) {
                if (curKode == null) {
                    kodebaru.setText(tmpKode[i]);
                } else {
                    i++;
                    tmpKode[i] = curKode + "0";
                    curKode = getTmpKode(tmpKode[i]);
                }
            }
        });
        
        bdatabaruafter.setOnAction(e->{
            disableButton(false, true);
            RestoreLayoutY(false);

            addStatus = "DataBaruA";
            dataOption = "addNew";

            yasebelumnya.setSelected(true);
            tidaksebelumnya.setDisable(true);

            String nowTbl = cPilihRule.getSelectionModel().getSelectedItem().toLowerCase();
            
            String getnowIdx = KodeSekarang.substring(0, KodeSekarang.length());
            kodesebelumnya.setText(String.valueOf(getnowIdx));
            String lastIdxNum = getnowIdx.substring(getnowIdx.length()-1,getnowIdx.length());
            
            tipebaru.setValue("");
            ke_tabel.setItems(modrule.LoadDataRule());
            tipebaru.getSelectionModel().select(0);

            pertanyaansebelumnya.setText(Con.getContent(nowTbl, getnowIdx));

            if ("G".equals(pertanyaansebelumnya.getText().substring(0,1))) {
                aqgsebelumnya.setText("Kode Gejala :");
            } else {
                aqgsebelumnya.setText("Kode Penyakit :");
            }
        });
        
        bhapusdata.setOnAction(e->{
            modrule.delete_all_after(cPilihRule.getSelectionModel().getSelectedItem().toLowerCase(), KodeSekarang, "Delete");
            modrule.delete(cPilihRule.getSelectionModel().getSelectedItem().toLowerCase(), KodeSekarang);
            modrule.refresh();
            showSelectedRule();
            disableButton(true, true);
            RestoreLayoutY(true);
        });
        
        bdatabaru.setOnAction(e->{
            addStatus = "DataBaru";
            String nowTbl = cPilihRule.getSelectionModel().getSelectedItem().toLowerCase();
            disableButton(false, true);
            RestoreLayoutY(true);

            String getnowIdx = KodeSekarang.substring(0, KodeSekarang.length());
            kodesebelumnya.setText(String.valueOf(getnowIdx));
            int lastIdxNum = Integer.valueOf(getnowIdx.substring(getnowIdx.length()-1,getnowIdx.length()));
            if (lastIdxNum<1) {
                pertanyaansebelumnya.setText("");
            } else {
                pertanyaansebelumnya.setText(Con.getContent(nowTbl, getnowIdx));
            }
            
            ke_tabel.setItems(modrule.LoadDataRule());
            aqgsebelumnya.setText("Data Sebelumnya :");
        });
        
        simpandata.setOnAction(e->{
            if(dataOption.equals("addNew")) {
                String tblNow = cPilihRule.getSelectionModel().getSelectedItem().toLowerCase();
                modrule.advanceAdd(tblNow, kodebaru.getText(), labeltipebaru.getText(), kontenbaru.getText());
            } else if(dataOption.equals("editData")) {
                modrule.basicEdit(cPilihRule.getSelectionModel().getSelectedItem().toLowerCase(), KodeSekarang, tipebaru.getSelectionModel().getSelectedItem(), labeltipebaru.getText(), kontenbaru.getText());
            }
            ClearTextField();
            showSelectedRule();
            disableButton(true, true);
            RestoreLayoutY(true);
            modrule.refresh();
        });
        
        beditdata.setOnAction(e->{
            addStatus = "DataBaruA";
            disableButton(false, true);
            RestoreLayoutY(false);
        });
        
        bataltabel.setOnAction(e->{
            disableButton(true, true);
            RestoreLayoutY(true);
        });
        
        tblgejalasekarang.setOnMouseClicked(e->{
            disableButton(true, false);
            PilihSelectedRule();
        });
        
        tipebaru.setOnAction(e->{
            if ("DataBaru".equals(addStatus)) {
                SelectNewTypeNew();
            } else {
                SelectNewType();
            }
        });
        
        ke_tabel.setOnAction(e->{
            if(tipebaru.getSelectionModel().getSelectedIndex()==2){
                labeltipebaru.setText("G");
                kontenbaru.setText("G." +modrule.getTable(String.valueOf(ke_tabel.getSelectionModel().getSelectedItem())));
            }
        });
        
        tblGejala.setOnMouseClicked(e->{
            PilihKodeGejala();
            if ("DataBaru".equals(addStatus)) {
                AddNewData("Q");
            }
        });
        
        tblPenyakit.setOnMouseClicked(e->{
            PilihKodePenyakit();
            if ("DataBaru".equals(addStatus)) {
                AddNewData("A");
                tblPenyakit.getItems().clear();
                disableButton(true, true);
                RestoreLayoutY(true);
            }
        });
        
        bhapusdiagnosis.setOnAction(e->{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Messages");
            alert.setHeaderText(null);
            alert.setContentText("Hapus Rule Dengan Kode : " + cPilihRule.getSelectionModel().getSelectedItem() + " ?");
            alert.showAndWait();
            if(alert.getResult()==ButtonType.OK)
            {
                modrule.deleteDetection(cPilihRule.getSelectionModel().getSelectedItem().toLowerCase());
                bdatabaru.setDisable(true);
                beditdata.setDisable(true);
                bhapusdata.setDisable(true);
                bataldata.setDisable(true);
                bhapusdiagnosis.setDisable(true);
                cPilihRule.setItems(modrule.LoadDataRule());
                tblgejalasekarang.getItems().clear();
            }
        });
        
        koderule.setDisable(true);
        bdiagnosisbaru.setOnAction(e->{
            simpantabel.setDisable(false);
            bataltabel.setDisable(false);
            koderule.setDisable(false);
            koderule.requestFocus();
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
        
        beditdata.setOnAction(e->{
            disableButton(false, true);
            RestoreLayoutY(false);
            dataOption = "editData";
            if (KodeSekarang.length()>1) {
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
            kodebaru.setText(KodeSekarang);
            tipebaru.setValue("");
            ke_tabel.setItems(modrule.LoadDataRule());
            tipebaru.setItems(data);

            kodesebelumnya.setText(KodeSekarang.substring(0,KodeSekarang.length()-1));
            Con.Open();
            try {
                Con.PStatem = Con.dbKoneksi.prepareStatement("SELECT * FROM " + cPilihRule.getSelectionModel().getSelectedItem().toLowerCase() + " WHERE Kode = '" + KodeSekarang.substring(0,KodeSekarang.length()-1) + "'");
                Con.Reset = Con.PStatem.executeQuery();
                while (Con.Reset.next()) {
                    pertanyaansebelumnya.setText(Con.Reset.getString("Pertanyaan"));
                }
            } catch (SQLException uy) {}
            Con.Close();
            
            String yesornope = String.valueOf(KodeSekarang.charAt(KodeSekarang.length() - 1));
            if (yesornope.equals("1")) {
                afteryes.setSelected(true);
                afterno.setDisable(true);
            } else {
                afterno.setSelected(true);
                afteryes.setDisable(true);
            }
        });
        
    }    
    
    public ObservableList LoadSelectedRule(String query) {
        ObservableList ListRule = FXCollections.observableArrayList();
        try {
            Con.Open();
            Con.PStatem = Con.dbKoneksi.prepareStatement(query);
            Con.Reset = Con.PStatem.executeQuery();
            while(Con.Reset.next()) {
                String strD = Con.Reset.getString("Pertanyaan");
                String Dsub = strD.substring(0,2);
                String tp,pt="";
                
                if ("G.".equals(Dsub)) {
                    tp = "RuleUtama";
                    pt = "Kode";
                    Con.PStatem2 = Con.dbKoneksi.prepareStatement("SELECT * FROM " + tp + " WHERE Kode = '" + Con.Reset.getString("Pertanyaan").substring(2,4) + "'");
                } else if ("P0".equals(Dsub) | "P1".equals(Dsub) | "P2".equals(Dsub) | "P3".equals(Dsub) | "P4".equals(Dsub) | "P5".equals(Dsub)) {
                    tp = "Penyakit";
                    pt = "Penyakit";
                    Con.PStatem2 = Con.dbKoneksi.prepareStatement("SELECT * FROM " + tp + " WHERE Kode = '" + Con.Reset.getString("Pertanyaan") + "'");
                } else if ("G0".equals(Dsub) | "G1".equals(Dsub) | "G2".equals(Dsub) | "G3".equals(Dsub) | "G4".equals(Dsub) | "G5".equals(Dsub)) {
                    tp = "Gejala";
                    pt = "Gejala";
                    Con.PStatem2 = Con.dbKoneksi.prepareStatement("SELECT * FROM " + tp + " WHERE Kode = '" + Con.Reset.getString("Pertanyaan") + "'");
                }
                
                Con.Reset2 = Con.PStatem2.executeQuery();
                while(Con.Reset2.next()) {
                    ModelRule mrule = new ModelRule(Con.Reset.getString(1),Con.Reset2.getString(pt));
                    ListRule.add(mrule);
                }
            }
            Con.Close();
        } catch (SQLException ex) {
        }
        return ListRule;
    }
    
    private void showSelectedRule() {
        Con.Open();
        try {
            Con.PStatem = Con.dbKoneksi.prepareStatement("SELECT * FROM " + cPilihRule.getSelectionModel().getSelectedItem().toLowerCase() + " ORDER BY Kode ASC");
            Con.Reset = Con.PStatem.executeQuery();
            while (Con.Reset.next()) {
                String lastStat = Con.Reset.getString("Pertanyaan");
                String lastStatKode = lastStat.substring(0, 1);
                if ("P".equals(lastStatKode)) {
                    bdatabaru.setDisable(true);
                } else {
                    bdatabaru.setDisable(false);
                }
            }
        } catch (SQLException ui) {}
        Con.Close();
                
        dataSelectedRule.clear();
        dataSelectedRule = LoadSelectedRule("SELECT Kode, Pertanyaan FROM " + cPilihRule.getSelectionModel().getSelectedItem().toLowerCase() + " ORDER BY Kode ASC");
        tblgejalasekarang.setItems(dataSelectedRule);
        tblgejalasekarang.getSelectionModel().clearSelection();
        colKodes.setCellValueFactory(new PropertyValueFactory("Kode"));
        colKets.setCellValueFactory(new PropertyValueFactory("Pertanyaan"));
    }
        
    private void PilihSelectedRule() {
        try {
            modrule = (ModelRule) tblgejalasekarang.getSelectionModel().getSelectedItems().get(0);
            String RIdx = modrule.getPertanyaan();
            String StarsIdx = RIdx.substring(0,2);
            if ("r1".equals(StarsIdx) | "r2".equals(StarsIdx) | "r3".equals(StarsIdx) | "r4".equals(StarsIdx) | "r5".equals(StarsIdx) | "r6".equals(StarsIdx) | "r7".equals(StarsIdx) | "r8".equals(StarsIdx) | "r9".equals(StarsIdx)) {
                bdatabaruafter.setDisable(true);
            } else {
                bdatabaruafter.setDisable(false);
                bdatabaruafter.setText("Tambah Data Baru Setelah " + modrule.getKode());
                KodeSekarang = modrule.getKode();
            }
        } catch(Exception e) {
            Con.showAlert(Alert.AlertType.WARNING,"Messages",null,"Data Tidak Ada!");
        }
    }
    
    private void SelectNewTypeNew() {
        if (pertanyaansebelumnya.getText() == null | "".equals(pertanyaansebelumnya.getText())) {
            switch (tipebaru.getSelectionModel().getSelectedItem()) {
                case "Question":
                    ke_tabel.setDisable(true);
                    labeltipebaru.setText("Q");
                    aqgbaru.setText("Kode Gejala :");
                    ShowListGejala();
                    tblPenyakit.setVisible(false);
                    tblGejala.setVisible(true);
                    tblGejala.setDisable(false);
                    break;
                case "Answer":
                    ke_tabel.setDisable(true);
                    labeltipebaru.setText("A");
                    aqgbaru.setText("Kode Penyakit :");
                    ShowListPenyakit();
                    tblGejala.setVisible(false);
                    tblPenyakit.setVisible(true);
                    tblPenyakit.setDisable(false);
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
                    ke_tabel.requestFocus();
                    break;
                default:
                    break;
            }
        } else {
            switch (tipebaru.getSelectionModel().getSelectedItem()) {
                case "Question":
                    ke_tabel.setDisable(true);
                    labeltipebaru.setText("Q");
                    aqgbaru.setText("Kode Gejala :");
                    ShowListGejala();
                    tblPenyakit.setVisible(false);
                    tblGejala.setVisible(true);
                    tblGejala.setDisable(false);
                    break;
                case "Answer":
                    ke_tabel.setDisable(true);
                    labeltipebaru.setText("A");
                    aqgbaru.setText("Kode Penyakit :");
                    ShowListPenyakit();
                    tblGejala.setVisible(false);
                    tblPenyakit.setVisible(true);
                    tblPenyakit.setDisable(false);
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
                    ke_tabel.requestFocus();
                    break;
                default:
                    break;
            }
        }
    }
    
    private void SelectNewType() {
        if (pertanyaansebelumnya.getText() == null | "".equals(pertanyaansebelumnya.getText())) {
            switch (tipebaru.getSelectionModel().getSelectedItem()) {
                case "Question":
                    ke_tabel.setDisable(true);
                    labeltipebaru.setText("Q");
                    aqgbaru.setText("Kode Gejala :");
                    ShowListGejala();
                    tblPenyakit.setVisible(false);
                    tblGejala.setVisible(true);
                    tblGejala.setDisable(false);
                    break;
                case "Answer":
                    ke_tabel.setDisable(true);
                    labeltipebaru.setText("A");
                    aqgbaru.setText("Kode Penyakit :");
                    ShowListPenyakit();
                    tblGejala.setVisible(false);
                    tblPenyakit.setVisible(true);
                    tblPenyakit.setDisable(false);
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
                    ke_tabel.requestFocus();
                    break;
                default:
                    break;
            }
        } else {
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
                        break;
                    case "Answer":
                        ke_tabel.setDisable(true);
                        labeltipebaru.setText("A");
                        aqgbaru.setText("Kode Penyakit :");
                        ShowListPenyakit();
                        tblGejala.setVisible(false);
                        tblPenyakit.setVisible(true);
                        tblPenyakit.setDisable(false);
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
                    break;
                default:
                    break;
                }
            }
        }
    }
    
    private void defaultStatus(boolean awal) {
        cPilihRule.getSelectionModel().select("[Pilih Diagnosis]");
        disableButton(true, true);
        RestoreLayoutY(true);
        tblgejalasekarang.setDisable(awal);
        bdatabaru.setDisable(awal);
        disableDiagnosis(awal);
    }
    
    private void disableButton(boolean tambah, boolean edit) {
        bdatabaruafter.setDisable(edit);
        beditdata.setDisable(edit);
        bhapusdata.setDisable(edit);
    
        aqgsebelumnya.setDisable(tambah);
        pertanyaansebelumnya.setDisable(tambah);
        lblsetelah.setDisable(tambah);
        afteryes.setDisable(tambah);
        afterno.setDisable(tambah);
        lblkodeb.setDisable(tambah);
        kodebaru.setDisable(tambah);
        lbltpb.setDisable(tambah);
        tipebaru.setDisable(tambah);
        ke_tabel.setDisable(tambah);
        labellistbaru.setDisable(tambah);
        tblGejala.setDisable(tambah);
        tblPenyakit.setDisable(tambah);
        disableDiagnosis(true);
    }
    
    private void ClearTextField() {
        afteryes.setSelected(false);
        afterno.setSelected(false);
        kodesebelumnya.clear();
        pertanyaansebelumnya.clear();
        kodebaru.clear();
        tipebaru.getSelectionModel().select("");
        ke_tabel.getSelectionModel().select("");
        tblGejala.getItems().clear();
        tblPenyakit.getItems().clear();
        yasebelumnya.setSelected(false);
        tidaksebelumnya.setSelected(false);
        kodesebelumnya.clear();
        kontenbaru.clear();
    }
    
    private void RestoreLayoutY(boolean yes) {
        if (yes == true) {
            //reset layout
            aqgsebelumnya.setLayoutY(6);
            pertanyaansebelumnya.setLayoutY(6);
            lblsetelah.setLayoutY(33);
            afteryes.setLayoutY(37);
            afterno.setLayoutY(37);
            lblkodeb.setLayoutY(60);
            kodebaru.setLayoutY(58);
            lbltpb.setLayoutY(86);
            tipebaru.setLayoutY(85);
            ke_tabel.setLayoutY(85);
            labellistbaru.setLayoutY(112);
            tblGejala.setLayoutY(137);
            tblPenyakit.setLayoutY(137);
            tblGejala.setPrefHeight(358);
            tblPenyakit.setPrefHeight(358);
            //hide
            lblsebelum.setVisible(false);
            yasebelumnya.setVisible(false);
            tidaksebelumnya.setVisible(false);
            lblkodes.setVisible(false);
            kodesebelumnya.setVisible(false);
            aqgbaru.setVisible(false);
            kontenbaru.setVisible(false);
            lblwarning.setVisible(false);
            simpandata.setVisible(false);
            bataldata.setVisible(false);
        } else {
            //restore layout
            aqgsebelumnya.setLayoutY(53);
            pertanyaansebelumnya.setLayoutY(51);
            lblsetelah.setLayoutY(93);
            afteryes.setLayoutY(97);
            afterno.setLayoutY(97);
            lblkodeb.setLayoutY(120);
            kodebaru.setLayoutY(118);
            lbltpb.setLayoutY(146);
            tipebaru.setLayoutY(145);
            ke_tabel.setLayoutY(145);
            labellistbaru.setLayoutY(172);
            tblGejala.setLayoutY(202);
            tblPenyakit.setLayoutY(202);
            tblGejala.setPrefHeight(192);
            tblPenyakit.setPrefHeight(192);
            //show
            lblsebelum.setVisible(true);
            yasebelumnya.setVisible(true);
            tidaksebelumnya.setVisible(true);
            lblkodes.setVisible(true);
            kodesebelumnya.setVisible(true);
            aqgbaru.setVisible(true);
            kontenbaru.setVisible(true);
            lblwarning.setVisible(true);
            simpandata.setVisible(true);
            bataldata.setVisible(true);  
        }
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
    
    private void PilihKodeGejala() {
        try {
            modgejala = (ModelGejala) tblGejala.getSelectionModel().getSelectedItems().get(0);
            kontenbaru.setText(modgejala.getKode());
            nowKode = modgejala.getKode();
        } catch(Exception e) {
            Con.showAlert(Alert.AlertType.WARNING,"Messages",null,"Data Tidak Ada!");
        }
    }
    
    private void PilihKodePenyakit() {
        try {
            modpenyakit = (ModelPenyakit) tblPenyakit.getSelectionModel().getSelectedItems().get(0);
            kontenbaru.setText(modpenyakit.getKode());
            nowKode = modpenyakit.getKode();
        } catch(Exception e) {
            Con.showAlert(Alert.AlertType.WARNING,"Messages",null,"Data Tidak Ada!");
        }
    }
    
    private String getTmpKode(String tmpKode) {
        Con.Open();
        try {
            Con.PStatem = Con.dbKoneksi.prepareStatement("SELECT * FROM " + cPilihRule.getSelectionModel().getSelectedItem().toLowerCase() + " WHERE Kode = '" + tmpKode + "'");
            Con.Reset = Con.PStatem.executeQuery();
            if(Con.Reset.next()) {
                return Con.Reset.getString("Kode");
            }
        } catch (SQLException ox) {}
        Con.Close();
        return null;
    }
    
    private void disableDiagnosis(boolean enabled) {
        koderule.setDisable(enabled);
        koderule.clear();
        simpantabel.setDisable(enabled);
        bataltabel.setDisable(enabled);
        bhapusdiagnosis.setDisable(enabled);
    }
    
    private void AddNewData(String type) {
        Con.Open();
        try {
            Con.PStatem = Con.dbKoneksi.prepareStatement("SELECT * FROM " + cPilihRule.getSelectionModel().getSelectedItem().toLowerCase() + " ORDER BY Kode DESC");
            Con.Reset = Con.PStatem.executeQuery();
            if (Con.Reset.next()) {
                String lastKode = Con.Reset.getString("Kode");
                String newKode = lastKode + "1";
                Con.PStatem2 = Con.dbKoneksi.prepareStatement("INSERT INTO " + cPilihRule.getSelectionModel().getSelectedItem().toLowerCase() + " VALUES (?,?,?)");
                Con.PStatem2.setString(1, newKode);
                Con.PStatem2.setString(2, type);
                Con.PStatem2.setString(3, nowKode);
                Con.PStatem2.execute();
            } else {
                Con.PStatem2 = Con.dbKoneksi.prepareStatement("INSERT INTO " + cPilihRule.getSelectionModel().getSelectedItem().toLowerCase() + " VALUES (?,?,?)");
                Con.PStatem2.setString(1, "1");
                Con.PStatem2.setString(2, type);
                Con.PStatem2.setString(3, nowKode);
                Con.PStatem2.execute();
            }
        } catch (SQLException oi) { }
        Con.Close();
        showSelectedRule();
    }
}
