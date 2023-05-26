/*
 *
 * @author Stenly Andika
 */

package model;

import connection.BridgeCon;
import javafx.scene.control.Alert;


public class ModelRule extends BridgeCon {
    private String kode,pertanyaan;
    private String nowTable, nowIndex, nowType, nowContent;
    private String nowQuestion,tblContent;

    public ModelRule() {
    
    }
    
    public void start(String table_title){
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
            start(nowTable);
        }else{
            if (super.dataFound(nowTable, nowIndex)){
                nowType = super.getType(nowTable, nowIndex);
                nowContent = super.getContent(nowTable, nowIndex);
                if ("G".equals(nowContent.substring(0,1))) {
                    tblContent = "gejala";
                } else if ("P".equals(nowContent.substring(0,1))) {
                    tblContent = "penyakit";
                }
                nowQuestion = super.getQuestion(tblContent,nowContent);
            } else {
                nowType = null;
                nowContent = null;
            }
        }
    }

    public void no(){
        nowIndex= nowIndex +"0";
        if (super.dataFound(nowTable, nowIndex)){
            nowType = super.getType(nowTable, nowIndex);
            nowContent = super.getContent(nowTable, nowIndex);
            if ("G".equals(nowContent.substring(0,1))) {
                tblContent = "gejala";
            } else if ("P".equals(nowContent.substring(0,1))) {
                tblContent = "penyakit";
            }
            nowQuestion = super.getQuestion(tblContent,nowContent);
        } else {
            nowType = null;
            nowContent = null;
        }
    }

    public void back(){
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

    public void refresh(){
        nowType = super.getType(nowTable, nowIndex);
        if (nowType == null) {
            nowContent = null;
        } else {
            nowContent = super.getContent(nowTable, nowIndex);
            if ("G".equals(nowContent.substring(0,1))) {
                tblContent = "gejala";
            } else if ("P".equals(nowContent.substring(0,1))) {
                tblContent = "penyakit";
            }
            nowQuestion = super.getQuestion(tblContent,nowContent);
        }
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

    public ModelRule(String kode, String pertanyaan) {
        this.kode = kode;
        this.pertanyaan = pertanyaan;
    }

    public String getKode() {
        return kode;
    }

    public String getPertanyaan() {
        return pertanyaan;
    }
    
    public void basicAdd(String table_name, String code, String type, String content){
        if (dataFound(getNowTable(), code)){
            delete_all_after(getNowTable(), code, "Save");
            delete(getNowTable(), code);
            addNewContent(getNowTable(),code, type, content);
        } else {
            addNewContent(getNowTable(),code, type, content);
        }
    }
    
    public void advanceAdd(String table_name, String code, String type, String content){
        if (dataFound(table_name, code)){
            delete_all_after(table_name, code, "Save");
            delete(table_name, code);
            addNewContent(table_name,code, type, content);
        } else {
            addNewContent(table_name,code, type, content);
        }
    }
    
    public void basicEdit(String table_name, String code, String nowtype, String new_type, String new_content){
        switch (new_type) {
            case "Q":
                super.edit_content(table_name, code, new_type, new_content);
                break;
            case "A":
                super.edit_content(table_name, code, new_type, new_content);
                super.delete_all_after(table_name, code, "Save");
                break;
            case "G":
                super.edit_content(table_name, code, new_type, new_content);
                super.delete_all_after(table_name, code, "Save");
                break;
            default:
                break;
        }
    }
    
    public void newDetection(String table_name){
        addMainRule(table_name);
        newTable(table_name);
        super.showAlert(Alert.AlertType.INFORMATION, "Message", null, "Rule Baru Telah Di Buat");
    }
    public void deleteDetection(String table_name){
        deleteFromMainRule(table_name);
        deleteTable(table_name);
        super.showAlert(Alert.AlertType.INFORMATION, "Message", null, "Rule Telah Di Hapus");
    }
}
