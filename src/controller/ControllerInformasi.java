package controller;

import com.jfoenix.controls.JFXButton;
import connection.BridgeCon;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Stenly Andika
 */
public class ControllerInformasi implements Initializable {

    @FXML private AnchorPane blur;
    @FXML private JFXButton tdiagnosa;
    @FXML private JFXButton tadmin;
    @FXML private JFXButton isistem;
    @FXML private JFXButton ipenyakit;
    @FXML private JFXButton brateus;
    @FXML private JFXButton babout;
    @FXML private TextArea hasiloutput;
    
    BridgeCon Con = new BridgeCon();

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tdiagnosa.setOnAction(e->{
            hasiloutput.setText("Tutorial Diagnosa :"
                    + "\n1. Isi data pasien terlebih dahulu"
                    + "\n2. Klik tombol Mulai Diagnosa"
                    + "\n3. Pilih jawaban berdasarkan jawaban dari pasien"
                    + "\n4. Setelah sesi pertanyaan selesai klik tombol Cek Diagnosa"
                    + "\n5. Diagnosa telah berhasil di lakukan");
        });
        
        tadmin.setOnAction(e->{
            hasiloutput.setText("Tutorial Mode Admin :"
                    + "\n1. Login terlebih dahulu"
                    + "\n2. Jika anda user pertama, silakan klik daftar dan isikan data anda"
                    + "\n3. Jika anda bukan user pertama, silakan hubungi user pertama untuk mendaftarkan akun anda"
                    + "\n3. Setelah selesai, Akun anda siap untuk digunakan"
                    + "\n4. Login dengan user yang telah didaftarkan, dan anda akan mendapat akses sebagai admin"
                    + "\n5. Klik User untuk Tambah - Edit - Hapus data user"
                    + "\n6. Klik Gejala untuk Tambah - Edit - Hapus data gejala"
                    + "\n7. Klik Penyakit untuk Tambah - Edit - Hapus data penyakit"
                    + "\n8. Klik Rule untuk Tambah - Edit - Hapus data rule/aturan"
                    + "\nSekian dan Terima Kasih. :)");
        });
        
        isistem.setOnAction(e->{
            hasiloutput.setText("Sistem Pakar Diagnosis Penyakit Paru - Paru"
                    + "\nSistem Pakar ini dibuat bukan untuk menggantikan seorang pakar, ataupun spesialis penyakit Paru - Paru, "
                    + "\nKarna sejatinya sistem pakar ini dibuat tidak lain adalah untuk mempermudah pakar dalam melakukan diagnosa"
                    + "\nTidak hanya seorang pakar, Siapapun boleh menggunakan sistem pakar ini atas seizin pihak yang berkenaan"
                    + "\n"
                    + "\nBuild Release v1.0347"
                    + "\nMinggu, 08 Juli 2018"
                    + "\nProgrammer, Stenly Andika, 15111134"
                    + "\nSistem ini dibuat untuk menyelesaikan mata kuliah Skripsi"
                    + "\ndan sebagai syarat untuk meraih gelar Ahli Madya (A.Md)"
                    + "\nAkademi Manajemen Informatika dan Komputer (AMIK) Depati Parbo Kerinci"
                    + "\nThanks to Tuhan YME, Kedua Orang Tua, Dan Teman Seperjuangan yang telah memberikan dukungan"
                    + "\nuntuk menyelesaikan Tugas Akhir ini"
                    + "\nSekian dan Terima Kasih :)");
        });
        
        babout.setOnAction(e->{
            Con.showAlert(Alert.AlertType.INFORMATION,"About",null,"Expert System Lung Diagnostics"
                    + "\nBuild Release v1.0237"
                    + "\nProgrammer, Stenly Andika"
                    + "\nFB : www.facebook.com/Stenly.Andika24"
                    + "\nG+ : stenly.andika@gmail.com");
        });
    }    
    
}
