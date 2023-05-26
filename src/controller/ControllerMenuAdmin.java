package controller;

import styles.AnimFadeInRightTransition;
import connection.BridgeCon;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import styles.AnimFadeInLeftTransition;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Stenly Andika
 */
public class ControllerMenuAdmin implements Initializable {
    @FXML public AnchorPane blur,paneView;
    @FXML public Button minimize,close;
    Stage stage;
    Rectangle2D rec2;
    Double w,h;
    @FXML private Pane bhome, binfo,badmin,brule,bpasien,bgejala,bpenyakit,bdiagnosa,brekam,bregister,blogin,blogout,bexit;
    @FXML private Label bdate,btime,bconstat,btitle,bloginstat,bloginuser,blogtitle;
    @FXML private Label lblhome,lblinfo,lbldiagnosa,lbluser,lblgejala,lblpenyakit,lblrule,lblpasien,lblrekam,lblregister,lbllogin,lbllogout,lblexit;
    @FXML private TextField vuser;
    @FXML private PasswordField vpass;
    
    BridgeCon Con = new BridgeCon();
    ControllerAdmin mlog = new ControllerAdmin();
    
    private String Title = "";
    private String uid;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try { 
            Con.Open(); 
            if(Con.dbKoneksi != null) {
                bconstat.setText("Status Koneksi : Sukses!");
            }else{
                bconstat.setText("Status Koneksi : Gagal!");
            }
            Con.Close();
        } catch (Exception e) {
            e.getMessage();
        }
        
        cekStatusUser("Logout");
        
        bhome.setStyle("-fx-background-color : white;"+"-fx-border-color : #438ae1;");
        lblhome.setStyle("-fx-text-fill : #438ae1");
        bhome.setOnMouseEntered(e->{
            bhome.setStyle("-fx-border-color : white;"+"-fx-background-color : #438ae1;");
            lblhome.setStyle("-fx-text-fill : white");
        });
        bhome.setOnMouseExited(e->{
            bhome.setStyle("-fx-border-color : #438ae1;"+"-fx-background-color : white;");
            lblhome.setStyle("-fx-text-fill : #438ae1");
        });
        
        binfo.setStyle("-fx-background-color : white;"+"-fx-border-color : #438ae1;");
        lblinfo.setStyle("-fx-text-fill : #438ae1");
        binfo.setOnMouseEntered(e->{
            binfo.setStyle("-fx-border-color : white;"+"-fx-background-color : #438ae1;");
            lblinfo.setStyle("-fx-text-fill : white");
        });
        binfo.setOnMouseExited(e->{
            binfo.setStyle("-fx-border-color : #438ae1;"+"-fx-background-color : white;");
            lblinfo.setStyle("-fx-text-fill : #438ae1");
        });
        
        bdiagnosa.setStyle("-fx-background-color : white;"+"-fx-border-color : #438ae1;");
        lbldiagnosa.setStyle("-fx-text-fill : #438ae1");
        bdiagnosa.setOnMouseEntered(e->{
            bdiagnosa.setStyle("-fx-border-color : white;"+"-fx-background-color : #438ae1;");
            lbldiagnosa.setStyle("-fx-text-fill : white");
        });
        bdiagnosa.setOnMouseExited(e->{
            bdiagnosa.setStyle("-fx-border-color : #438ae1;"+"-fx-background-color : white;");
            lbldiagnosa.setStyle("-fx-text-fill : #438ae1");
        });
        
        badmin.setStyle("-fx-background-color : white;"+"-fx-border-color : #438ae1;");
        lbluser.setStyle("-fx-text-fill : #438ae1");
        badmin.setOnMouseEntered(e->{
            badmin.setStyle("-fx-border-color : white;"+"-fx-background-color : #438ae1;");
            lbluser.setStyle("-fx-text-fill : white");
        });
        badmin.setOnMouseExited(e->{
            badmin.setStyle("-fx-border-color : #438ae1;"+"-fx-background-color : white;");
            lbluser.setStyle("-fx-text-fill : #438ae1");
        });
        
        bgejala.setStyle("-fx-background-color : white;"+"-fx-border-color : #438ae1;");
        lblgejala.setStyle("-fx-text-fill : #438ae1");
        bgejala.setOnMouseEntered(e->{
            bgejala.setStyle("-fx-border-color : white;"+"-fx-background-color : #438ae1;");
            lblgejala.setStyle("-fx-text-fill : white");
        });
        bgejala.setOnMouseExited(e->{
            bgejala.setStyle("-fx-border-color : #438ae1;"+"-fx-background-color : white;");
            lblgejala.setStyle("-fx-text-fill : #438ae1");
        });
        
        bpenyakit.setStyle("-fx-background-color : white;"+"-fx-border-color : #438ae1;");
        lblpenyakit.setStyle("-fx-text-fill : #438ae1");
        bpenyakit.setOnMouseEntered(e->{
            bpenyakit.setStyle("-fx-border-color : white;"+"-fx-background-color : #438ae1;");
            lblpenyakit.setStyle("-fx-text-fill : white");
        });
        bpenyakit.setOnMouseExited(e->{
            bpenyakit.setStyle("-fx-border-color : #438ae1;"+"-fx-background-color : white;");
            lblpenyakit.setStyle("-fx-text-fill : #438ae1");
        });
        
        brule.setStyle("-fx-background-color : white;"+"-fx-border-color : #438ae1;");
        lblrule.setStyle("-fx-text-fill : #438ae1");
        brule.setOnMouseEntered(e->{
            brule.setStyle("-fx-border-color : white;"+"-fx-background-color : #438ae1;");
            lblrule.setStyle("-fx-text-fill : white");
        });
        brule.setOnMouseExited(e->{
            brule.setStyle("-fx-border-color : #438ae1;"+"-fx-background-color : white;");
            lblrule.setStyle("-fx-text-fill : #438ae1");
        });
        
        bpasien.setStyle("-fx-background-color : white;"+"-fx-border-color : #438ae1;");
        lblpasien.setStyle("-fx-text-fill : #438ae1");
        bpasien.setOnMouseEntered(e->{
            bpasien.setStyle("-fx-border-color : white;"+"-fx-background-color : #438ae1;");
            lblpasien.setStyle("-fx-text-fill : white");
        });
        bpasien.setOnMouseExited(e->{
            bpasien.setStyle("-fx-border-color : #438ae1;"+"-fx-background-color : white;");
            lblpasien.setStyle("-fx-text-fill : #438ae1");
        });
        
        brekam.setStyle("-fx-background-color : white;"+"-fx-border-color : #438ae1;");
        lblrekam.setStyle("-fx-text-fill : #438ae1");
        brekam.setOnMouseEntered(e->{
            brekam.setStyle("-fx-border-color : white;"+"-fx-background-color : #438ae1;");
            lblrekam.setStyle("-fx-text-fill : white");
        });
        brekam.setOnMouseExited(e->{
            brekam.setStyle("-fx-border-color : #438ae1;"+"-fx-background-color : white;");
            lblrekam.setStyle("-fx-text-fill : #438ae1");
        });
        
        bregister.setStyle("-fx-background-color : white;"+"-fx-border-color : #438ae1;");
        lblregister.setStyle("-fx-text-fill : #438ae1");
        bregister.setOnMouseEntered(e->{
            bregister.setStyle("-fx-border-color : white;"+"-fx-background-color : #438ae1;");
            lblregister.setStyle("-fx-text-fill : white");
        });
        bregister.setOnMouseExited(e->{
            bregister.setStyle("-fx-border-color : #438ae1;"+"-fx-background-color : white;");
            lblregister.setStyle("-fx-text-fill : #438ae1");
        });
        
        blogin.setStyle("-fx-background-color : white;"+"-fx-border-color : #438ae1;");
        lbllogin.setStyle("-fx-text-fill : #438ae1");
        blogin.setOnMouseEntered(e->{
            blogin.setStyle("-fx-border-color : white;"+"-fx-background-color : #438ae1;");
            lbllogin.setStyle("-fx-text-fill : white");
        });
        blogin.setOnMouseExited(e->{
            blogin.setStyle("-fx-border-color : #438ae1;"+"-fx-background-color : white;");
            lbllogin.setStyle("-fx-text-fill : #438ae1");
        });
        
        blogout.setStyle("-fx-background-color : white;"+"-fx-border-color : #438ae1;");
        lbllogout.setStyle("-fx-text-fill : #438ae1");
        blogout.setOnMouseEntered(e->{
            blogout.setStyle("-fx-border-color : white;"+"-fx-background-color : #438ae1;");
            lbllogout.setStyle("-fx-text-fill : white");
        });
        blogout.setOnMouseExited(e->{
            blogout.setStyle("-fx-border-color : #438ae1;"+"-fx-background-color : white;");
            lbllogout.setStyle("-fx-text-fill : #438ae1");
        });
        
        bexit.setStyle("-fx-background-color : white;"+"-fx-border-color : #438ae1;");
        lblexit.setStyle("-fx-text-fill : #438ae1");
        bexit.setOnMouseEntered(e->{
            bexit.setStyle("-fx-border-color : white;"+"-fx-background-color : #438ae1;");
            lblexit.setStyle("-fx-text-fill : white");
        });
        bexit.setOnMouseExited(e->{
            bexit.setStyle("-fx-border-color : #438ae1;"+"-fx-background-color : white;");
            lblexit.setStyle("-fx-text-fill : #438ae1");
        });
        
        loginFieldStatus(false,true);
        loginButtonStatus("Logout");
        blogout.setVisible(false);
        Title = "";
        cekStatusUser("Logout");
        TimeConfig();
        blogtitle.setText("Login Here :");
        bdate.setText(SystemDate()); 
        showSubForm("Welcome To System","/view/FormHome.fxml");
        
        bhome.setOnMouseClicked(e->{
            showSubForm("Welcome To System","/view/FormHome.fxml");
        });
        
        bregister.setOnMouseClicked(e->{
            Parent newRoot;
            Stage curStage = (Stage)blur.getScene().getWindow();
            blur.setEffect(new GaussianBlur(10));
            try {
                newRoot = FXMLLoader.load(getClass().getResource("/view/FormDaftar.fxml"));
                Stage newStage = new Stage();
                Con.setDragged(newRoot, newStage);
                newStage.setScene(new Scene(newRoot));
                newStage.initStyle(StageStyle.UNDECORATED);
                newStage.initModality(Modality.APPLICATION_MODAL);
                newStage.initOwner(curStage);
                
                ChangeListener<Number> widthListener = (observable, oldValue, newValue) -> {
                        double stageWidth = newValue.doubleValue();
                        newStage.setX(curStage.getX() + curStage.getWidth() / 2 - stageWidth / 2);
                };
                
                ChangeListener<Number> heightListener = (observable, oldValue, newValue) -> {
                        double stageHeight = newValue.doubleValue();
                        newStage.setY(curStage.getY() + curStage.getHeight() / 2 - stageHeight / 2);   
                };

                newStage.widthProperty().addListener(widthListener);
                newStage.heightProperty().addListener(heightListener);

                newStage.setOnShown(en -> {
                    newStage.widthProperty().removeListener(widthListener);
                    newStage.heightProperty().removeListener(heightListener);
                });

                newStage.showAndWait();
                blur.setEffect(new GaussianBlur(0));
                cekStatusUser("Logout");
                vuser.requestFocus();
            } catch (IOException s) {
        
            }
        });
        
        blogin.setOnMouseClicked(e->{
            prosesLogin(vuser.getText(),vpass.getText());
        });
        
        vuser.setOnAction(e->{
            prosesLogin(vuser.getText(),vpass.getText());
        });
        
        vpass.setOnAction(e->{
            prosesLogin(vuser.getText(),vpass.getText());
        });
        
        blogout.setOnMouseClicked(e->{
            Title = "";
            cekStatusUser("Logout");
            loginButtonStatus("Logout");
            showSubForm("Welcome To System","/view/FormHome.fxml");
            blogtitle.setText("Login Here :");
            loginFieldStatus(false,true);
            binfo.setVisible(true);
            bdiagnosa.setVisible(true);
            vuser.requestFocus();
        });
        
        binfo.setOnMouseClicked(e->{
            showSubForm("Informasi (Informasi Penyakit - Sistem)","/view/FormInformasi.fxml");
        });
        
        badmin.setOnMouseClicked(e->{
            showSubForm("Admin (Tambah - Edit - Hapus Admin)","/view/FormAdmin.fxml");
        });
        
        bgejala.setOnMouseClicked(e->{
            showSubForm("Gejala (Tambah - Edit - Hapus Gejala)","/view/FormGejala.fxml");
        });
        
        bpenyakit.setOnMouseClicked(e->{
            showSubForm("Penyakit (Tambah - Edit - Hapus Penyakit)","/view/FormPenyakit.fxml");
        });
        
        brule.setOnMouseClicked(e->{
            showSubForm("Rule (Tambah - Edit - Hapus Rule)","/view/FormRule.fxml");
        });
        
        bdiagnosa.setOnMouseClicked(e->{
            showSubForm("Diagnosa (Diagnosa Penyakit Paru - Paru)","/view/FormDiagnosa.fxml");
        });
        
        bpasien.setOnMouseClicked(e->{
            showSubForm("Data Pasien (Daftar Pasien Penyakit Paru - Paru)","/view/FormPasien.fxml");
        });
        
        brekam.setOnMouseClicked(e->{
            showSubForm("Rekam Medis (Daftar Rekam Medis Penyakit Paru - Paru)","/view/FormRekam.fxml");
        });
        
        minimize.setOnAction(e->{
            stage = (Stage) minimize.getScene().getWindow();
            if (stage.isMaximized()) {
                w = rec2.getWidth();
                h = rec2.getHeight();
                stage.setMaximized(false);
                stage.setHeight(h);
                stage.setWidth(w);
                stage.centerOnScreen();
                Platform.runLater(() -> {
                    stage.setIconified(true);
                });
            }else{
                stage.setIconified(true);
            }
        });
        
        close.setOnAction(e->{
            System.exit(0);
        });
        
        bexit.setOnMouseClicked(e->{
            Stage curStage = (Stage)blur.getScene().getWindow();
            blur.setEffect(new GaussianBlur(10));
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Messages");
            alert.setHeaderText(null);
            alert.setContentText("Keluar Dari Aplikasi?");
            alert.showAndWait();
            if(alert.getResult()==ButtonType.OK)
            {
                System.exit(0);
            }else{
                blur.setEffect(new GaussianBlur(0));
            }
        });
    }    
    
    private void showSubForm(String formtitle,String formdir){
        try {
            btitle.setText(Title + formtitle);
            paneView.getChildren().clear();
            paneView.setOpacity(0);
            new AnimFadeInRightTransition(paneView).play();
            AnchorPane pane = FXMLLoader.load(getClass().getResource(formdir));
            paneView.getChildren().setAll(pane);
        } catch (IOException b) {
            System.out.println(b);
        }
    }
    
    private void TimeConfig() {
    Timeline timeline = new Timeline(
    new KeyFrame(Duration.seconds(0), (e) -> {
        Calendar time = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        btime.setText(simpleDateFormat.format(time.getTime()));
    }),
    new KeyFrame(Duration.seconds(1))
    );
    timeline.setCycleCount(Animation.INDEFINITE);
    timeline.play();
    }
    
    private String SystemDate(){
        Date tgl = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        return(sdf.format(tgl));       
    }
    
    private void cekStatusUser(String getstatus){
        if(getstatus.equals("Login")) {
            new AnimFadeInLeftTransition(badmin).play();
            badmin.setVisible(true);
            new AnimFadeInRightTransition(bgejala).play();
            bgejala.setVisible(true);
            new AnimFadeInLeftTransition(bpenyakit).play();
            bpenyakit.setVisible(true);
            new AnimFadeInRightTransition(brule).play();
            brule.setVisible(true);
            new AnimFadeInLeftTransition(bpasien).play();
            bpasien.setVisible(true);
            new AnimFadeInRightTransition(brekam).play();
            brekam.setVisible(true);
            bregister.setVisible(false);
        } else {
            badmin.setVisible(false);
            bgejala.setVisible(false);
            bpenyakit.setVisible(false);
            brule.setVisible(false);
            bpasien.setVisible(false);
            brekam.setVisible(false);
            new AnimFadeInRightTransition(bregister).play();
            try {
                Con.Open();
                Con.PStatem = Con.dbKoneksi.prepareStatement("SELECT * FROM admin");
                Con.Reset = Con.PStatem.executeQuery();
                if (Con.Reset.next()) {
                    bregister.setVisible(false); 
                } else {
                    bregister.setVisible(true); 
                }
                Con.Close();
            } catch (SQLException l) {}
            try {
                Con.Open();
                Con.PStatem = Con.dbKoneksi.prepareStatement("Delete From templogin");
                Con.PStatem.execute();
                Con.Close();
            } catch (SQLException x) {}
        }
    }
    
    private void loginButtonStatus(String getstatus){
        if(getstatus.equals("Login")){
            blogin.setVisible(false);
            new AnimFadeInRightTransition(blogout).play();
            blogout.setVisible(true);
        }else{
            new AnimFadeInRightTransition(blogin).play();
            blogin.setVisible(true);
            blogout.setVisible(false);
        }
    }
    
    private void loginFieldStatus(boolean disable, boolean enable){
            vuser.setVisible(enable);
            vpass.setVisible(enable);
            bloginstat.setVisible(disable);
            bloginuser.setVisible(disable);
    }
    
    
    private void prosesLogin(String user, String pass){
        try {
            int row=0;
            Con.Open();
            Con.PStatem = Con.dbKoneksi.prepareStatement("Select * From admin Where username ='"+ user + "'");
            Con.Reset = Con.PStatem.executeQuery();
            if(Con.Reset.next()){
                Con.PStatem = Con.dbKoneksi.prepareStatement("Select * From admin Where username ='"+ user + "' And password ='" + pass + "'");
                Con.Reset2 = Con.PStatem.executeQuery();
                if(Con.Reset2.next()){
                    uid = Con.Reset.getString("id_admin");
                    Con.PStatem3 = Con.dbKoneksi.prepareStatement("Insert Into templogin Values (?)");
                    Con.PStatem3.setString(1, Con.Reset2.getString("id_admin"));
                    Con.PStatem3.execute();
                    cekStatusUser("Login");
                    vuser.clear();
                    vpass.clear();
                    Title = "Mode Admin > ";
                    showSubForm("Welcome To System","/view/FormHome.fxml");
                    loginButtonStatus("Login");
                    loginFieldStatus(true,false);
                    blogtitle.setText("Login Information :");
                    bloginstat.setText("Status\t\t: Logged In!");
                    bloginuser.setText("Username\t: " + Con.Reset.getString("username"));
                    TrayNotification tray = new TrayNotification("Login Sukses","Selamat Datang "+Con.Reset.getString("username"),NotificationType.SUCCESS);
                    tray.setRectangleFill(Paint.valueOf("#438ae1"));
                    Image img = new Image("/img/icons8_Checkmark_104px.png");
                    tray.setImage(img);
                    tray.setAnimationType(AnimationType.POPUP);
                    tray.showAndDismiss(Duration.seconds(2));
                } else {
                    vpass.clear();
                    vpass.requestFocus();
                    Con.showAlert(Alert.AlertType.WARNING,"Messages",null,"Password Salah!");
                }
            } else {
                vuser.clear();
                vuser.requestFocus();
                Con.showAlert(Alert.AlertType.WARNING,"Messages",null,"Username Salah!");
            }
            Con.Close();
        } catch (SQLException a) {
        }
    }
  
}
