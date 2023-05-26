package model;

/**
 *
 * @author Stenly Andika
 */
public class ModelRekam {
    private final String idrekam;
    private final String nama;
    private final String tgl;
    private final String penyakit;

    public ModelRekam(String idrekam, String nama, String tgl, String penyakit) {
        this.idrekam = idrekam;
        this.nama = nama;
        this.tgl = tgl;
        this.penyakit = penyakit;
    }

    public String getIdrekam() {
        return idrekam;
    }

    public String getNama() {
        return nama;
    }

    public String getTgl() {
        return tgl;
    }

    public String getPenyakit() {
        return penyakit;
    }
    
    
}
