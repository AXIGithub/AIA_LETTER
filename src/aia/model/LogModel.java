/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import service.LogRepository;

/**
 *
 * @author Ratino
 */
public class LogModel {
    
    private long idLog;
    private String barcode;
    private String noPolis;
    private String name;
    private String address1;
    private String address2;
    private String address3;
    private String address4;
    private String address5;
    private String zipcode;
    private int flag;
    private String flagEnv;
    private String courier;
    private String productName;
    private String seqEnv;
    private String seqCust;
    private String seqPage;
    
   
//    private ArrayList<Integer> idLog            = new ArrayList<Integer>(10);
//    private ArrayList<String>barcode            = new ArrayList<String>(10);
//    private ArrayList<String>idCustomer         = new ArrayList<String>(10);
//    private ArrayList<String>name1              = new ArrayList<String>(10);
//    private ArrayList<String>name2              = new ArrayList<String>(10);
//    private ArrayList<String>name3              = new ArrayList<String>(10);
//    private ArrayList<String>address1           = new ArrayList<String>(10);
//    private ArrayList<String>address2           = new ArrayList<String>(10);
//    private ArrayList<String>address3           = new ArrayList<String>(10);
//    private ArrayList<String>address4           = new ArrayList<String>(10);
//    private ArrayList<String>address5           = new ArrayList<String>(10);
//    private ArrayList<String>address6           = new ArrayList<String>(10);
//    private ArrayList<Integer>b1                = new ArrayList<Integer>(10);
//    private ArrayList<Integer>b2                = new ArrayList<Integer>(10);
//    private ArrayList<Integer>b3                = new ArrayList<Integer>(10);
////    private ArrayList<Integer>b4                = new ArrayList<Integer>(10);
//    private ArrayList<String>b4                = new ArrayList<String>(10);
//    private ArrayList<Integer>b5                = new ArrayList<Integer>(10);
//    private ArrayList<Integer>b6                = new ArrayList<Integer>(10);
//    private ArrayList<Integer>s1                = new ArrayList<Integer>(10);
//    private ArrayList<Integer>s2                = new ArrayList<Integer>(10);
//    private ArrayList<Integer>s3                = new ArrayList<Integer>(10);
////    private ArrayList<String>s3                = new ArrayList<String>(10);
//    private ArrayList<Integer>s4                = new ArrayList<Integer>(10);
//    private ArrayList<Integer>s5                = new ArrayList<Integer>(10);
//    private ArrayList<Integer>s6                = new ArrayList<Integer>(10);
//    private ArrayList<String>productName        = new ArrayList<String>(10);
//    private ArrayList<String>courierName        = new ArrayList<String>(10);
//    private ArrayList<Integer>seqPage            = new ArrayList<Integer>(10);
//    private ArrayList<Integer>seqCustomer        = new ArrayList<Integer>(10);
//    private ArrayList<Integer>seqEnvelope        = new ArrayList<Integer>(10);
//    private ArrayList<Integer>totalCurrentPage  = new ArrayList<Integer>(10);
//
//    private ArrayList<Integer>totalPage1      = new ArrayList<Integer>(10);
//    private ArrayList<Integer>totalCustomer1  = new ArrayList<Integer>(10);
//    private ArrayList<Integer>totalEnvelope1  = new ArrayList<Integer>(10);
//    private ArrayList<Integer>totalB11        = new ArrayList<Integer>(10);
//    private ArrayList<Integer>totalB21        = new ArrayList<Integer>(10);
//    private ArrayList<Integer>totalB31        = new ArrayList<Integer>(10);
//    private ArrayList<Integer>totalB41        = new ArrayList<Integer>(10);
//    private ArrayList<Integer>totalS11        = new ArrayList<Integer>(10);
//    private ArrayList<Integer>totalS21        = new ArrayList<Integer>(10);
//    private ArrayList<Integer>totalS31        = new ArrayList<Integer>(10);
//    private ArrayList<String>ss1              = new ArrayList<String>(10);
//    private ArrayList<String>ss2              = new ArrayList<String>(10);
//    private ArrayList<String>ss3              = new ArrayList<String>(10);
//    private ArrayList<String>ss4              = new ArrayList<String>(10);
//    private ArrayList<String>ss5              = new ArrayList<String>(10);
//    private ArrayList<String>ss6              = new ArrayList<String>(10);
//    private ArrayList<String> barcodeSample     = new ArrayList<String>(10);
//    
//    private ArrayList<String> minAmp  = new ArrayList<String>(10);
//    private ArrayList<String> maxAmp  = new ArrayList<String>(10);
//    
//    private int totalNormal = 0; private int totalStamp = 0;
//    private int totalTypeDoc = 0; private int totalKurir = 0;
//    private int totalPaperTypeDoc = 0; private int totalWaybill = 0;
//    private int totalNasabah = 0; private int totalEnvelope = 0;
//    private int totalPage = 0; private int totalB1 = 0;
//    private int totalB2 = 0; private int totalB3 = 0;
//    private int totalB4 = 0; private int totalB5 = 0;
//    private int totalB6 = 0; private int totalS1 = 0;
//    private int totalS2 = 0; private int totalS3 = 0;
//    private int totalS4 = 0; private long totalS5 = 0;
//    private int totalS6 = 0;
//    
//    private String jenis = "";    private String agen = "";
//    private String jnsKartu = ""; 
//    private int jmlcust = 0;    private int jmlhal = 0; private int jmlamp = 0;
//    private int jmlBill = 0; private int row = 0;

    
    public void createTable(Statement stmt){
        try {
            stmt.executeUpdate("DROP TABLE IF EXISTS t_log2");
            stmt.executeUpdate("CREATE TABLE t_log2(id_log INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,barcode VARCHAR(100) NOT NULL, id_customer VARCHAR(100) NOT NULL, " +
                    "name1 VARCHAR(100) NOT NULL, name2 VARCHAR(100) NOT NULL, name3 VARCHAR(100) NOT NULL, address1 VARCHAR(100) NOT NULL, address2 VARCHAR(100) NOT NULL," +
                    "address3 VARCHAR(100) NOT NULL, address4 VARCHAR(100) NOT NULL, address5 VARCHAR(100) NOT NULL, address6 VARCHAR(100) NOT NULL, b1 INT NOT NULL, " +
                    "b2 INT NOT NULL, b3 INT NOT NULL, b4 CHAR(30) NOT NULL, b5 INT NOT NULL, b6 INT NOT NULL, s1 INT NOT NULL, s2 INT NOT NULL, s3 INT NOT NULL, s4 INT NOT NULL, " +
                    "s5 INT NOT NULL, s6 INT NOT NULL, product_name VARCHAR(100) NOT NULL, courier_name VARCHAR(100) NOT NULL, seq_page INT NOT NULL, seq_customer INT NOT NULL, " +
                    "seq_envelope INT NOT NULL, ss1 CHAR(30) NOT NULL, ss2 CHAR(30) NOT NULL, ss3 CHAR(30) NOT NULL, ss4 CHAR(30) NOT NULL, ss5 CHAR(30) NOT NULL, ss6 CHAR(30) NOT NULL) ENGINE = MYISAM");
            stmt.executeUpdate("ALTER TABLE t_log2 ADD INDEX tb_log_index1 (product_name,name2)");
            stmt.executeUpdate("ALTER TABLE t_log2 ADD INDEX tb_log_index2 (product_name)");
            stmt.executeUpdate("ALTER TABLE t_log2 ADD INDEX tb_log_index3 (courier_name,product_name)");
            stmt.executeUpdate("ALTER TABLE t_log2 ADD INDEX tb_log_index4 (seq_envelope, product_name, name2)");
            
            stmt.executeUpdate("DROP TABLE IF EXISTS t_logCetak");
            stmt.executeUpdate("CREATE TABLE t_logCetak(id_log INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,barcode VARCHAR(100) NOT NULL, id_customer VARCHAR(100) NOT NULL, " +
                    "name1 VARCHAR(100) NOT NULL, name2 VARCHAR(100) NOT NULL, name3 VARCHAR(100) NOT NULL, address1 VARCHAR(100) NOT NULL, address2 VARCHAR(100) NOT NULL," +
                    "address3 VARCHAR(100) NOT NULL, address4 VARCHAR(100) NOT NULL, address5 VARCHAR(100) NOT NULL, address6 VARCHAR(100) NOT NULL, b1 INT NOT NULL, " +
                    "b2 INT NOT NULL, b3 INT NOT NULL, b4 CHAR(30) NOT NULL, b5 INT NOT NULL, b6 INT NOT NULL, s1 INT NOT NULL, s2 INT NOT NULL, s3 INT NOT NULL, s4 INT NOT NULL, " +
                    "s5 INT NOT NULL, s6 INT NOT NULL, product_name VARCHAR(100) NOT NULL, courier_name VARCHAR(100) NOT NULL, seq_page INT NOT NULL, seq_customer INT NOT NULL, " +
                    "seq_envelope INT NOT NULL, ss1 CHAR(30) NOT NULL, ss2 CHAR(30) NOT NULL, ss3 CHAR(30) NOT NULL, ss4 CHAR(30) NOT NULL, ss5 CHAR(30) NOT NULL, ss6 CHAR(30) NOT NULL) ENGINE = MYISAM");
            stmt.executeUpdate("ALTER TABLE t_logCetak ADD INDEX tb_logCetak_index1 (product_name,name2)");
            stmt.executeUpdate("ALTER TABLE t_logCetak ADD INDEX tb_logCetak_index2 (product_name)");
            stmt.executeUpdate("ALTER TABLE t_logCetak ADD INDEX tb_logCetak_index3 (courier_name,product_name)");
            stmt.executeUpdate("ALTER TABLE t_logCetak ADD INDEX tb_logCetak_index4 (seq_envelope, product_name, name2)");
            
            
            stmt.executeUpdate("DROP TABLE IF EXISTS t_logEstate");
            stmt.executeUpdate("CREATE TABLE t_logEstate(id_log INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,barcode VARCHAR(100) NOT NULL, id_customer VARCHAR(100) NOT NULL, " +
                    "name1 VARCHAR(100) NOT NULL, name2 VARCHAR(100) NOT NULL, name3 VARCHAR(100) NOT NULL, address1 VARCHAR(100) NOT NULL, address2 VARCHAR(100) NOT NULL," +
                    "address3 VARCHAR(100) NOT NULL, address4 VARCHAR(100) NOT NULL, address5 VARCHAR(100) NOT NULL, address6 VARCHAR(100) NOT NULL, b1 INT NOT NULL, " +
                    "b2 INT NOT NULL, b3 INT NOT NULL, b4 CHAR(30) NOT NULL, b5 INT NOT NULL, b6 INT NOT NULL, s1 INT NOT NULL, s2 INT NOT NULL, s3 INT NOT NULL, s4 INT NOT NULL, " +
                    "s5 INT NOT NULL, s6 INT NOT NULL, product_name VARCHAR(100) NOT NULL, courier_name VARCHAR(100) NOT NULL, seq_page INT NOT NULL, seq_customer INT NOT NULL, " +
                    "seq_envelope INT NOT NULL, ss1 CHAR(30) NOT NULL, ss2 CHAR(30) NOT NULL, ss3 CHAR(30) NOT NULL, ss4 CHAR(30) NOT NULL, ss5 CHAR(30) NOT NULL, ss6 CHAR(30) NOT NULL) ENGINE = MYISAM");
            stmt.executeUpdate("ALTER TABLE t_logEstate ADD INDEX tb_logEstate_index1 (product_name,name2)");
            stmt.executeUpdate("ALTER TABLE t_logEstate ADD INDEX tb_logEstate_index2 (product_name)");
            stmt.executeUpdate("ALTER TABLE t_logEstate ADD INDEX tb_logEstate_index3 (courier_name,product_name)");
            stmt.executeUpdate("ALTER TABLE t_logEstate ADD INDEX tb_logEstate_index4 (seq_envelope, product_name, name2)");
            
            
            stmt.executeUpdate("DROP TABLE IF EXISTS t_logState");
            stmt.executeUpdate("CREATE TABLE t_logState(id_log INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,barcode VARCHAR(100) NOT NULL, id_customer VARCHAR(100) NOT NULL, " +
                    "name1 VARCHAR(100) NOT NULL, name2 VARCHAR(100) NOT NULL, name3 VARCHAR(100) NOT NULL, address1 VARCHAR(100) NOT NULL, address2 VARCHAR(100) NOT NULL," +
                    "address3 VARCHAR(100) NOT NULL, address4 VARCHAR(100) NOT NULL, address5 VARCHAR(100) NOT NULL, address6 VARCHAR(100) NOT NULL, b1 INT NOT NULL, " +
                    "b2 INT NOT NULL, b3 INT NOT NULL, b4 CHAR(30) NOT NULL, b5 INT NOT NULL, b6 INT NOT NULL, s1 INT NOT NULL, s2 INT NOT NULL, s3 INT NOT NULL, s4 INT NOT NULL, " +
                    "s5 INT NOT NULL, s6 INT NOT NULL, product_name VARCHAR(100) NOT NULL, courier_name VARCHAR(100) NOT NULL, seq_page INT NOT NULL, seq_customer INT NOT NULL, " +
                    "seq_envelope INT NOT NULL, ss1 CHAR(30) NOT NULL, ss2 CHAR(30) NOT NULL, ss3 CHAR(30) NOT NULL, ss4 CHAR(30) NOT NULL, ss5 CHAR(30) NOT NULL, ss6 CHAR(30) NOT NULL) ENGINE = MYISAM");
            stmt.executeUpdate("ALTER TABLE t_logState ADD INDEX tb_logState_index1 (product_name,name2)");
            stmt.executeUpdate("ALTER TABLE t_logState ADD INDEX tb_logState_index2 (product_name)");
            stmt.executeUpdate("ALTER TABLE t_logState ADD INDEX tb_logState_index3 (courier_name,product_name)");
            stmt.executeUpdate("ALTER TABLE t_logState ADD INDEX tb_logState_index4 (seq_envelope, product_name, name2)");
            
            
        } catch (SQLException ex) {
            Logger.getLogger(LogModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public long getIdLog() {
        return idLog;
    }

    public void setIdLog(long idLog) {
        this.idLog = idLog;
    }
    
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getNoPolis() {
        return noPolis;
    }

    public void setNoPolis(String noPolis) {
        this.noPolis = noPolis;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getAddress4() {
        return address4;
    }

    public void setAddress4(String address4) {
        this.address4 = address4;
    }

    public String getAddress5() {
        return address5;
    }

    public void setAddress5(String address5) {
        this.address5 = address5;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getFlagEnv() {
        return flagEnv;
    }

    public void setFlagEnv(String flagEnv) {
        this.flagEnv = flagEnv;
    }

    public String getCourier() {
        return courier;
    }

    public void setCourier(String courier) {
        this.courier = courier;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSeqEnv() {
        return seqEnv;
    }

    public void setSeqEnv(String seqEnv) {
        this.seqEnv = seqEnv;
    }

    public String getSeqCust() {
        return seqCust;
    }

    public void setSeqCust(String seqCust) {
        this.seqCust = seqCust;
    }

    public String getSeqPage() {
        return seqPage;
    }

    public void setSeqPage(String seqPage) {
        this.seqPage = seqPage;
    }
    
    
}
