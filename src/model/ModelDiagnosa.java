package model;

import connection.BridgeCon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Stenly Andika
 */
public class ModelDiagnosa extends BridgeCon {
    private int Id;
    private String Nama;
    private String Penyakit;
    private int Persen;
    private String Tgl;

    private String nowTable, nowIndex, nowType, nowContent;
    private String nowQuestion,tblContent,FinResult;
    private String nextType,nextIndex,nextQuestion;
    
    public ObservableList tempGejala = FXCollections.observableArrayList();
    
    public void start(){
        nowTable = super.getFirstTable();
        if (super.dataFound(nowTable, "1")){
            nowIndex = "1";
            nowType = super.getType(nowTable, nowIndex);
            nowContent = super.getContent(nowTable, nowIndex);
            if ("G".equals(nowContent.substring(0,1))) {
                tblContent = "gejala";
            } else if ("P".equals(nowContent.substring(0,1))) {
                tblContent = "penyakit";
            }
            nowQuestion = super.getQuestion(tblContent,nowContent);
        }else{
            nowIndex = "1";
            nowType = null;
            nowContent = null;
        }
    }
    
    public void startNew(String table_title){
        nowTable = super.getTable(table_title);
        if (super.dataFound(nowTable, "1")){
            nowIndex = "1";
            nowType = super.getType(nowTable, nowIndex);
            nowContent = super.getContent(nowTable, nowIndex);
            if ("G".equals(nowContent.substring(0,1))) {
                tblContent = "gejala";
            } else if ("P".equals(nowContent.substring(0,1))) {
                tblContent = "penyakit";
            }
            nowQuestion = super.getQuestion(tblContent,nowContent);
        }else{
            nowIndex = "1";
            nowType = null;
            nowContent = null;
        }
    }
    
    public void yes(){
        nowIndex = nowIndex +"1";
        if(nowType.startsWith("G")){
            String nextTable = nowContent.substring(2,nowContent.length());
            nowTable = super.getTable(nextTable);
            startNew(nowTable);
        }else{
            tempGejala.add(getNowContent());
            if (super.dataFound(nowTable, nowIndex)){
                nowType = super.getType(nowTable, nowIndex);
                nowContent = super.getContent(nowTable, nowIndex);
                if ("G".equals(nowContent.substring(0,1))) {
                    tblContent = "gejala";
                    nowQuestion = super.getQuestion(tblContent,nowContent);
                } else if ("P".equals(nowContent.substring(0,1))) {
                    tblContent = "penyakit";
                    FinResult = super.getQuestion(tblContent,nowContent);
                    nowQuestion = "Diagnosa Selesai";
                }
            } else {
                nowType = null;
                nowContent = null;
            }
        }
    }

    public void no(){
        nowIndex = nowIndex +"0";
        if (super.dataFound(nowTable, nowIndex)){
            nowType = super.getType(nowTable, nowIndex);
            nowContent = super.getContent(nowTable, nowIndex);
            if ("G".equals(nowContent.substring(0,1))) {
                tblContent = "gejala";
                nowQuestion = super.getQuestion(tblContent,nowContent);
            } else if ("P".equals(nowContent.substring(0,1))) {
                tblContent = "penyakit";
                FinResult = super.getQuestion(tblContent,nowContent);
                nowQuestion = "Diagnosa Selesai";
            } else {
                nowType = "";
                nowQuestion = "Diagnosa Selesai";
            }
        } else {
            nowType = null;
            nowContent = null;
        }
    }

    public void back(){
        if (tempGejala.size()>1) {
            tempGejala.remove(tempGejala.size()-1);
        } else {
            tempGejala.removeAll(tempGejala);
        }
        nowIndex = nowIndex.substring(0, nowIndex.length()-1);
        nowType = super.getType(nowTable, nowIndex);
        nowContent = super.getContent(nowTable, nowIndex);
        if ("G".equals(nowContent.substring(0,1))) {
            tblContent = "gejala";
        } else if ("P".equals(nowContent.substring(0,1))) {
            tblContent = "penyakit";
        }
        nowQuestion = super.getQuestion(tblContent,nowContent);
    }
    
    public String getNowQuestion(){
        return nowQuestion;
    }
    
    public String getNowIndex(){
        return nowIndex;
    }

    public String getNowTable(){
        return nowTable;
    }

    public String getNowContent(){
        return nowContent;
    }

    public String getNowType(){
        return nowType;
    }

    public String getFinResult() {
        return FinResult;
    }
    
    public int getId() {
        return Id;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String Nama) {
        this.Nama = Nama;
    }

    public String getPenyakit() {
        return Penyakit;
    }

    public void setPenyakit(String Penyakit) {
        this.Penyakit = Penyakit;
    }

    public int getPersen() {
        return Persen;
    }

    public void setPersen(int Persen) {
        this.Persen = Persen;
    }

    public String getTgl() {
        return Tgl;
    }

    public void setTgl(String Tgl) {
        this.Tgl = Tgl;
    }

    public String getNextType() {
        return nextType;
    }

    public String getNextIndex() {
        return nextIndex;
    }

    public String getNextQuestion() {
        return nextQuestion;
    }
    
    
}
