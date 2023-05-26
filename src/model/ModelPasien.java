package model;

/**
 *
 * @author Stenly Andika
 */
public class ModelPasien {
    String idpas;
    String nama;
    String alamat;
    String jekel;
    int Umur;

    public ModelPasien(String idpas, String nama, String alamat, String jekel, int Umur) {
        this.idpas = idpas;
        this.nama = nama;
        this.alamat = alamat;
        this.jekel = jekel;
        this.Umur = Umur;
    }

    public String getIdpas() {
        return idpas;
    }

    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getJekel() {
        return jekel;
    }

    public int getUmur() {
        return Umur;
    }
    
    
}
