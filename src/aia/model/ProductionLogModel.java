/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model;

/**
 *
 * @author Ratino
 */

import aia.controller.TextModification;
import java.util.ArrayList;
import com.mysql.jdbc.Statement;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductionLogModel {
    private ArrayList<Integer> idLog            = new ArrayList<Integer>(10);
    private ArrayList<String>barcode            = new ArrayList<String>(10);
    private ArrayList<String>idCustomer         = new ArrayList<String>(10);
    private ArrayList<String>name1              = new ArrayList<String>(10);
    private ArrayList<String>name2              = new ArrayList<String>(10);
    private ArrayList<String>name3              = new ArrayList<String>(10);
    private ArrayList<String>address1           = new ArrayList<String>(10);
    private ArrayList<String>address2           = new ArrayList<String>(10);
    private ArrayList<String>address3           = new ArrayList<String>(10);
    private ArrayList<String>address4           = new ArrayList<String>(10);
    private ArrayList<String>address5           = new ArrayList<String>(10);
    private ArrayList<String>address6           = new ArrayList<String>(10);
    private ArrayList<String>b1                = new ArrayList<String>(10);
    private ArrayList<String>b2                = new ArrayList<String>(10);
    private ArrayList<String>b3                = new ArrayList<String>(10);
    private ArrayList<Integer>b4                = new ArrayList<Integer>(10);
    private ArrayList<String>b5                 = new ArrayList<String>(10);
    private ArrayList<Integer>b6                = new ArrayList<Integer>(10);
    private ArrayList<Integer>s1                = new ArrayList<Integer>(10);
    private ArrayList<Integer>s2                = new ArrayList<Integer>(10);
    private ArrayList<String>s3                 = new ArrayList<String>(10);
    private ArrayList<Integer>s4                = new ArrayList<Integer>(10);
    private ArrayList<Integer>s5                   = new ArrayList<Integer>(10);
    private ArrayList<Integer>s6                = new ArrayList<Integer>(10);
    private ArrayList<String>productName        = new ArrayList<String>(10);
    private ArrayList<String>courierName        = new ArrayList<String>(10);
    private ArrayList<Integer>seqPage           = new ArrayList<Integer>(10);
    private ArrayList<Integer>seqCustomer       = new ArrayList<Integer>(10);
    private ArrayList<Integer>seqEnvelope       = new ArrayList<Integer>(10);
    private ArrayList<String> ss1               = new ArrayList<String>(10);
    private ArrayList<String> ss2               = new ArrayList<String>(10);
    private ArrayList<String> ss3               = new ArrayList<String>(10);
    private ArrayList<String> ss4               = new ArrayList<String>(10);
    private ArrayList<String> ss5               = new ArrayList<String>(10);
    private ArrayList<String> ss6               = new ArrayList<String>(10);
    
    
    public void createTable(Statement stmt) throws SQLException{
        stmt.executeUpdate("DROP TABLE IF EXISTS t_log");
        stmt.executeUpdate("CREATE TABLE t_log(id_log INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,barcode VARCHAR(100) NOT NULL, id_customer VARCHAR(100) NOT NULL, " +
                    "name1 VARCHAR(100) NOT NULL, name2 VARCHAR(100) NOT NULL, name3 VARCHAR(100) NOT NULL, address1 VARCHAR(100) NOT NULL, address2 VARCHAR(100) NOT NULL," +
                    " address3 VARCHAR(100) NOT NULL, address4 VARCHAR(100) NOT NULL, address5 VARCHAR(100) NOT NULL, address6 VARCHAR(100) NOT NULL, b1 TEXT NOT NULL, " +
                    "b2 TEXT NOT NULL, b3 TEXT NOT NULL, b4 TEXT NOT NULL, b5 TEXT NOT NULL, b6 TEXT NOT NULL, s1 VARCHAR(100), s2 INT NOT NULL, s3 INT NOT NULL, s4 INT NOT NULL, " +
                    "s5 INT NOT NULL, s6 VARCHAR(100), product_name VARCHAR(100) NOT NULL, courier_name VARCHAR(100) NOT NULL, seq_page INT NOT NULL, seq_customer INT NOT NULL, " +
                    "seq_envelope INT NOT NULL, ss1 VARCHAR(100) NOT NULL, ss2 VARCHAR(100) NOT NULL, ss3 VARCHAR(100) NOT NULL, ss4 VARCHAR(100) NOT NULL, ss5 VARCHAR(100) NOT NULL, ss6 VARCHAR(100) NOT NULL) ENGINE = MYISAM");
        stmt.executeUpdate("ALTER TABLE t_log ADD INDEX tb_log_index1 (barcode)");
        stmt.executeUpdate("ALTER TABLE t_log ADD INDEX tb_log_index2 (id_customer)");        
    }
    
    public void uploadToDatabase(String path, Statement stmt) throws SQLException{
        stmt.executeUpdate("TRUNCATE TABLE T_LOG");
        System.out.println("LOAD DATA INFILE '" + path +"' INTO TABLE t_log (barcode,id_customer,name1,name2,name3,address1,address2,address3,address4,address5,address6,b1,b2,b3,b4,b5,b6,s1,s2,s3,s4,s5,s6,product_name,courier_name,seq_page,seq_customer,seq_envelope,ss1,ss2,ss3,ss4,ss5,ss6)");
        stmt.executeUpdate("LOAD DATA INFILE '" + path +"' INTO TABLE t_log (barcode,id_customer,name1,name2,name3,address1,address2,address3,address4,address5,address6,b1,b2,b3,b4,b5,b6,s1,s2,s3,s4,s5,s6,product_name,courier_name,seq_page,seq_customer,seq_envelope,ss1,ss2,ss3,ss4,ss5,ss6)");
    }
    
    public void createLogScan(String path, String product, String category, Statement stmt ) throws FileNotFoundException, SQLException, IOException{
        TextModification txt = new TextModification();
        ResultSet hasilQuery = null;
        FileOutputStream outputStream1 = new FileOutputStream(path);
        DataOutputStream out1          = new DataOutputStream(outputStream1);
        BufferedWriter bw1             = new BufferedWriter(new OutputStreamWriter(out1));
        System.out.println("SELECT seq_envelope, barcode, name1 AS name, MAX(s4), product_name, courier_name FROM t_log WHERE product_name = '" + product + "' and S6 = '"+ category +"' AND s4 = \'1\' GROUP BY s1 ORDER by seq_envelope ASC");
//        hasilQuery          = stmt.executeQuery("SELECT seq_envelope, barcode, name1, product_name, courier_name FROM t_log WHERE product_name = '" + product + "' and S6 = '"+ category +"' GROUP BY barcode ORDER by seq_envelope ASC");
//        hasilQuery          = stmt.executeQuery("SELECT seq_envelope, barcode, name1 AS name, MAX(s4), product_name, courier_name FROM t_log WHERE product_name = '" + product + "' and S6 = '"+ category +"' GROUP BY name1 ORDER by seq_envelope ASC");
//        hasilQuery          = stmt.executeQuery("SELECT seq_envelope, barcode, name1 AS name, MAX(s4), product_name, courier_name FROM t_log WHERE product_name = '" + product + "' and S6 = '"+ category +"' GROUP BY name1 ORDER by seq_envelope ASC");
//        hasilQuery          = stmt.executeQuery("SELECT seq_envelope, barcode, name1 AS name, MAX(s4), product_name, courier_name FROM t_log WHERE product_name = '" + product + "' and S6 = '"+ category +"' GROUP BY barcode ORDER by seq_envelope ASC");
//        hasilQuery          = stmt.executeQuery("SELECT seq_envelope, barcode, name1 AS name, MAX(s4), product_name, courier_name FROM t_log WHERE product_name = '" + product + "' and S6 = '"+ category +"' AND s4 = \'1\' GROUP BY barcode ORDER by seq_envelope ASC");
        if(product.contains("REKAP")){
            hasilQuery          = stmt.executeQuery("SELECT seq_envelope, barcode, name1 AS name,s4, product_name, courier_name FROM t_log WHERE product_name = '" + product + "' and S6 = '"+ category +"' AND s4 = \'1\' ORDER by seq_envelope ASC");
        } else {
            hasilQuery          = stmt.executeQuery("SELECT seq_envelope, barcode, name1 AS name, MAX(s4), product_name, courier_name FROM t_log WHERE product_name = '" + product + "' and S6 = '"+ category +"' AND s4 = \'1\' GROUP BY s1 ORDER by seq_envelope ASC");
        }
        
                
        String Kurir = "", productName="";
        int i = 0;
        while(hasilQuery.next()){
            //if ( !zKurir.equals(hasilQuery.getString("courier_name")) ) {
               i++;                
            //}
            if(category.contains("HOLD")){
                Kurir = "HOLD"+hasilQuery.getString("courier_name").substring(0, 1);
            } else {
                Kurir = hasilQuery.getString("courier_name");
            }
            productName = hasilQuery.getString("product_name");
            if(productName.equalsIgnoreCase("PLATINUM (E)")) {
                productName = productName.replace("PLATINUM (E)", "PLATINUM");
            }
            if(productName.equalsIgnoreCase("TITANIUM (A1)")) {
                productName = productName.replace("TITANIUM (A1)", "TITANIUM");
            }
            if(productName.equalsIgnoreCase("LOTTE ( G1 )")){
                productName = productName.replace("LOTTE ( G1 )", "LOTTE");
            }
            bw1.write(//hasilQuery.getString("seq_envelope") + "\t" +
                txt.norm6Digit(i) + "\t" + 
                hasilQuery.getString("barcode").substring(0, 11) + "\t" +
                hasilQuery.getString("name") + "\t" +                        
                productName.replace(" ", "_") + "\t" +
//                hasilQuery.getString("courier_name").replace("PMC", "01PMC").replace("NCS","02NCS") + "\t\r\n");
//                i + hasilQuery.getString("courier_name").replace("PMC", "01PMC").replace("NCS","02NCS") + "\t\r\n");
                Kurir + "\t\r\n");   
//             i + hasilQuery.getString("courier_name").replace("SAP", "01SAP").replace("NCS","02NCS") + "\t\r\n");
//                zKurir = hasilQuery.getString("courier_name");
        }        
        bw1.flush();
        out1.close();
        outputStream1.close(); 
        File file = new File(path); // Del File 0 size
        if (file.length() == 0) {
            file.delete();
        }
    }
    
    public void createReportPrinting(String path, Statement stmt) throws FileNotFoundException, SQLException, IOException{
        ResultSet hasilQuery = null;
        FileOutputStream outputStream1 = new FileOutputStream(path);
        DataOutputStream out1          = new DataOutputStream(outputStream1);
        BufferedWriter bw1             = new BufferedWriter(new OutputStreamWriter(out1));
        bw1.write("No" + "\t" + "KDCYC" + "\t" + "SENDSTAT" + "\t" + "PRODUK" + "\t" + "KURIR" + "\t" + "FILENAME" + "\t" + "CUST" + "\t" + "LEMBAR" + "\t" + "AMPLOP" + "\r\n");
        int i = 0;
//        hasilQuery = stmt.executeQuery("SELECT name3 AS KDCYC, s6 AS SENDSTAT, product_name AS PRODUK, courier_name AS KURIR, name2 AS FILENAME, SUM(s4) AS CUST, COUNT(barcode) AS LEMBAR, SUM(s4) AS AMPLOP FROM t_log GROUP BY barcode ORDER BY s6, product_name, courier_name");
//        hasilQuery = stmt.executeQuery("SELECT name3 AS KDCYC, s6 AS SENDSTAT, product_name AS PRODUK, courier_name AS KURIR, name2 AS FILENAME, CASE WHEN COUNT(DISTINCT name2) = COUNT(DISTINCT name2) THEN COUNT(DISTINCT name2) ELSE COUNT(DISTINCT barcode) END AS CUST, COUNT(barcode) AS LEMBAR, CASE WHEN COUNT(DISTINCT name2) = COUNT(DISTINCT name2) THEN COUNT(DISTINCT name2) ELSE COUNT(DISTINCT barcode) END AS AMPLOP FROM t_log GROUP BY name2 ORDER BY s6, product_name");
        hasilQuery = stmt.executeQuery("SELECT name3 AS KDCYC, s6 AS SENDSTAT, product_name AS PRODUK, courier_name AS KURIR, name2 AS FILENAME, SUM(S4) AS CUST, COUNT(barcode) AS LEMBAR, SUM(s4) AS AMPLOP FROM t_log GROUP BY name2 ORDER BY s6, product_name, courier_name");
    
        while(hasilQuery.next()){
            i++;
            bw1.write(i + "\t" +
                    hasilQuery.getString("KDCYC") + "\t" +
                    hasilQuery.getString("SENDSTAT") + "\t" +
                    hasilQuery.getString("PRODUK") + "\t" +
                    hasilQuery.getString("KURIR") + "\t" +
                    hasilQuery.getString("FILENAME") + "\t" +
                    hasilQuery.getString("CUST") + "\t" +
                    hasilQuery.getString("LEMBAR") + "\t" +
                    hasilQuery.getString("AMPLOP") + "\r\n");            
        }
        bw1.flush();
        out1.close();
        outputStream1.close();                        
    }
    
    public void createReportProduksi(String path, Statement stmt) throws FileNotFoundException, IOException, SQLException{
        ResultSet hasilQuery = null;
        FileOutputStream outputStream = new FileOutputStream(path);
        DataOutputStream out1 = new DataOutputStream(outputStream);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out1));
        bw.write("No" + "\t" + "KDCYC" + "\t" + "PRODUK" + "\t" + "CUST" + "\t" + "LEMBAR" + "\t" + "AMPLOP" + "\r\n");
        int i = 0;
        //hasilQuery = stmt.executeQuery("SELECT name3 AS KDCYC, product_name AS PRODUK,  COUNT(DISTINCT barcode) AS CUST, COUNT(barcode) AS LEMBAR, COUNT(DISTINCT barcode) AS AMPLOP FROM t_log GROUP BY PRODUK ORDER BY product_name");
        hasilQuery = stmt.executeQuery("SELECT name3 AS KDCYC, product_name AS PRODUK,  SUM(S4) AS CUST, COUNT(barcode) AS LEMBAR, SUM(S4) AS AMPLOP FROM t_log GROUP BY PRODUK ORDER BY product_name");
        while(hasilQuery.next()){
            i++;
            bw.write(i + "\t" + 
                    hasilQuery.getString("KDCYC") + "\t" +
                    hasilQuery.getString("PRODUK") + "\t" +
                    hasilQuery.getString("CUST") + "\t" +
                    hasilQuery.getString("LEMBAR") + "\t" +
                    hasilQuery.getString("AMPLOP") + "\r\n" );
        }
        bw.flush();
        bw.close();
        outputStream.close();
    }
    
    public void createLogLabel24(String path, Statement stmt) throws FileNotFoundException, IOException, SQLException{
        ResultSet hasilQuery = null;
        boolean empty = true;
        FileOutputStream outputStream = new FileOutputStream(path);
        DataOutputStream out1 = new DataOutputStream(outputStream);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out1));
        bw.write("Id_log" + "\t" + "LETTER" + "barcode" + "\t" + "Product" + "\t" + "KURIR" + "\t" + "Name" + "\t" + "Addr1" + "\t" + "Addr2" + "\t" + 
                "Addr3" + "\t" + "Addr4" + "\t" + "Addr5" + "\t" + "PostCode" + "\t" + "CYCLE" + "\t" + "b1" + "\t" + "b2" + "\t" + "b3" + "\t" +
                "b4" + "\t" + "b5" + "\t" + "b6" + "\t" + "s1" + "\t" + "s2" + "\t" + "s3" + "\t" + "s4" + "\t" + "s5" + "\t" + "Category" + "\t" + "Product" + 
                "\t" + "Kurir" + "\t" + "Seq Page" + "\t" + "Seq Cus" + "\t" + "Seq Env" + "\t" + "ss1" + "\t" + "ss2" + "\t" + "ss3" + "\t" + "ss4" + "\t" + "ss5" + "\t" + "ss6" +  "\r\n" );
        hasilQuery = stmt.executeQuery("SELECT *  FROM `t_log` WHERE `product_name` LIKE '%CORP REKAP%' AND ss1 = '1'");
        while(hasilQuery.next()){
            empty = false;            
            bw.write(hasilQuery.getString("id_log") + "\t" + 
                     hasilQuery.getString("barcode") + "\t" +
                     hasilQuery.getString("id_customer") + "\t" +
                     hasilQuery.getString("name1") + "\t" + hasilQuery.getString("name2") + "\t" + hasilQuery.getString("name3") + "\t" +
                     hasilQuery.getString("address1") + "\t" + hasilQuery.getString("address2") + "\t" + hasilQuery.getString("address3") + "\t" +
                     hasilQuery.getString("address4") + "\t" + hasilQuery.getString("address5") + "\t" + hasilQuery.getString("address6") + "\t" +
                     hasilQuery.getString("b1") + "\t" + hasilQuery.getString("b2") + "\t" + hasilQuery.getString("b3") + "\t" +
                     hasilQuery.getString("b4") + "\t" + hasilQuery.getString("b5") + "\t" + hasilQuery.getString("b6") + "\t" +
                     hasilQuery.getString("s1") + "\t" + hasilQuery.getString("s2") + "\t" + hasilQuery.getString("s3") + "\t" +
                     hasilQuery.getString("s4") + "\t" + hasilQuery.getString("s5") + "\t" + hasilQuery.getString("s6") + "\t" +
                     hasilQuery.getString("product_name") + "\t" +
                     hasilQuery.getString("courier_name") + "\t" +
                     hasilQuery.getString("seq_page") + "\t" +
                     hasilQuery.getString("seq_customer") + "\t" +
                     hasilQuery.getString("seq_envelope") + "\t" +
                     hasilQuery.getString("ss1") + "\t" + hasilQuery.getString("ss2") + "\t" + hasilQuery.getString("ss3") + "\t" +
                     hasilQuery.getString("ss4") + "\t" + hasilQuery.getString("ss5") + "\t" + hasilQuery.getString("ss6") + "\r\n" );
        }
        bw.flush();
        bw.close();
        outputStream.close();
        
        File file = new File(path); // Del File 0 size
        if (empty) {
            file.delete();
        }
    }
}
