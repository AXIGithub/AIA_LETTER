/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import aia.controller.CreatePDFReport.ReportRow;
import aia.model.BaseModel;
import aia.model.LogModel;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author user
 */
public class LogRepository {
    private final Connection conn;
    private BufferedWriter bw = null; 
    
    public LogRepository(Connection conn) {
        this.conn = conn;
    }
    
    public void insertData(List<BaseModel> data, String product, String cetak, String barcode, String fileName, String SeqCust, String SeqEnv, String SeqPage, float yAddr, float xBox, String flag) throws SQLException{
        BaseModel header = data.get(0);
        
        String yLast = String.valueOf(yAddr);
        String xLast = String.valueOf(xBox);
        
        String sql =
            "INSERT INTO t_log2 (" +
            "barcode,id_customer,name1,name2,name3,address1,address2,address3,address4,address5,address6," +
            "b1,b2,b3,b4,b5,b6,s1,s2,s3,s4,s5,s6," +
            "product_name,courier_name,seq_page,seq_customer,seq_envelope," +
            "ss1,ss2,ss3,ss4,ss5,ss6" +
            ") VALUES (" +
            "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?" +
            ")";

        
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            int i = 1;
            ps.setString(i++, barcode);
            ps.setString(i++, header.getChdrnum());
            ps.setString(i++, header.getOwner());
            ps.setString(i++, "");
            ps.setString(i++, "");
            ps.setString(i++, header.getAddr01());
            ps.setString(i++, header.getAddr02());
            ps.setString(i++, header.getAddr03());
            ps.setString(i++, header.getAddr04());
            ps.setString(i++, header.getAddr05());
            ps.setString(i++, header.getPcode());

            ps.setString(i++, header.getFlgEstate());
            ps.setInt(i++, 0);
            ps.setInt(i++, 0);
            ps.setString(i++, "");
            ps.setInt(i++, 0);
            ps.setInt(i++, 0);

            ps.setInt(i++, 0);
            ps.setInt(i++, 0);
            ps.setInt(i++, 0);
            ps.setInt(i++, 0);
            ps.setInt(i++, 0);
            ps.setInt(i++, 0);

            ps.setString(i++, product);
            ps.setString(i++, "POS");
            ps.setString(i++, SeqPage);
            ps.setString(i++, SeqCust);
            ps.setString(i++, SeqEnv);

            ps.setString(i++, fileName);
            ps.setString(i++, yLast);
            ps.setString(i++, xLast);
            ps.setString(i++, flag);
            ps.setString(i++, header.getDteBirth());
            ps.setString(i++, "");
            
            ps.executeUpdate();
        }
    }
    
    public void insertCetakFromLog2(long log2Id, String seqPage, String seqCust, String seqEnv) throws SQLException {

        String sql = "INSERT INTO t_logCetak (barcode,id_customer,name1,name2,name3,address1,address2,address3,address4,address5,address6,b1,b2,b3,b4,b5,b6," +
                "s1,s2,s3,s4,s5,s6,product_name,courier_name,seq_page,seq_customer,seq_envelope,ss1,ss2,ss3,ss4,ss5,ss6)" +
                "SELECT barcode,id_customer,name1,name2,name3,address1,address2,address3,address4,address5,address6,b1,b2,b3,b4,b5,b6,s1,s2,s3,s4,s5,s6," +
                "product_name,courier_name,?, ?, ?,ss1,ss2,ss3,ss4,ss5,ss6 FROM t_log2 WHERE id_log = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, seqPage);
            ps.setString(2, seqCust);
            ps.setString(3, seqEnv);
            ps.setLong(4, log2Id);
            ps.executeUpdate();
        }
    } 
    
    public void insertEstateFromLog2(long log2Id, String seqPage, String seqCust, String seqEnv) throws SQLException {

        String sql = "INSERT INTO t_logEstate (barcode,id_customer,name1,name2,name3,address1,address2,address3,address4,address5,address6,b1,b2,b3,b4,b5,b6," +
                "s1,s2,s3,s4,s5,s6,product_name,courier_name,seq_page,seq_customer,seq_envelope,ss1,ss2,ss3,ss4,ss5,ss6)" +
                "SELECT barcode,id_customer,name1,name2,name3,address1,address2,address3,address4,address5,address6,b1,b2,b3,b4,b5,b6,s1,s2,s3,s4,s5,s6," +
                "product_name,courier_name,?, ?, ?,ss1,ss2,ss3,ss4,ss5,ss6 FROM t_log2 WHERE id_log = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, seqPage);
            ps.setString(2, seqCust);
            ps.setString(3, seqEnv);
            ps.setLong(4, log2Id);
            ps.executeUpdate();
        }
    } 
    
    public void insertStateFromLog2(long log2Id, String seqPage, String seqCust, String seqEnv) throws SQLException {

        String sql = "INSERT INTO t_logState (barcode,id_customer,name1,name2,name3,address1,address2,address3,address4,address5,address6,b1,b2,b3,b4,b5,b6," +
                "s1,s2,s3,s4,s5,s6,product_name,courier_name,seq_page,seq_customer,seq_envelope,ss1,ss2,ss3,ss4,ss5,ss6)" +
                "SELECT barcode,id_customer,name1,name2,name3,address1,address2,address3,address4,address5,address6,b1,b2,b3,b4,b5,b6,s1,s2,s3,s4,s5,s6," +
                "product_name,courier_name,?, ?, ?,ss1,ss2,ss3,ss4,ss5,ss6 FROM t_log2 WHERE id_log = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, seqPage);
            ps.setString(2, seqCust);
            ps.setString(3, seqEnv);
            ps.setLong(4, log2Id);
            ps.executeUpdate();
        }
    } 
    
    public List<BaseModel> getPdf(String flag) throws SQLException {
        String sql = "SELECT * FROM t_log2 WHERE b1 = ? ORDER BY address6 ASC";

        List<BaseModel> result = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, flag);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    BaseModel model = new BaseModel();
                    model.setChdrnum(rs.getString("id_customer"));
                    model.setPdfname(rs.getString("ss1"));
                    model.setDteBirth(rs.getString("ss5"));
                    model.setPcode(rs.getString("address6"));
                    model.setId(rs.getLong("id_log"));
                    model.setyLast(rs.getString("ss2"));
                    model.setxLast(rs.getString("ss3"));
                    
                    result.add(model);
                }
            }
        }
        return result;
    }
    
    public List<BaseModel> getstateForPdf() throws SQLException {
        String sql =
            "SELECT " +
            "id_customer, ss1 AS pdfname, seq_page, seq_customer, seq_envelope, ss2, ss3 " +
            "FROM t_logstate " +
            "ORDER BY seq_page ASC";

        List<BaseModel> result = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                BaseModel m = new BaseModel();
                m.setChdrnum(rs.getString("id_customer"));
                m.setPdfname(rs.getString("pdfname"));
                m.setSeqPage(rs.getString("seq_page"));
                m.setSeqCust(rs.getString("seq_customer"));
                m.setSeqEnv(rs.getString("seq_envelope"));
                m.setyLast(rs.getString("ss2"));
                m.setxLast(rs.getString("ss3"));
                result.add(m);
            }
        }
        return result;
    }
    
    public List<BaseModel> getEstateForPdf() throws SQLException {
        String sql =
            "SELECT * FROM t_logestate " +
            "ORDER BY seq_envelope ASC, seq_page ASC";

        List<BaseModel> result = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                BaseModel m = new BaseModel();
                m.setChdrnum(rs.getString("id_customer"));
                m.setPdfname(rs.getString("ss1"));
                m.setSeqEnv(rs.getString("seq_envelope"));
                m.setSeqPage(rs.getString("seq_page"));
                m.setyLast(rs.getString("ss2"));
                m.setxLast(rs.getString("ss3"));
                m.setDteBirth(rs.getString("ss5"));
                result.add(m);
            }
        }
        return result;
    }


    
    public List<ReportRow> getPrintingReport(String cetak, String product) throws SQLException {

        String sql =
            "SELECT " +
            "COUNT(DISTINCT id_customer) AS jumlah_acc, " +
            "COUNT(DISTINCT CASE WHEN ss4 = '1' THEN seq_page END) AS halaman_prio, " +
            "COUNT(DISTINCT CASE WHEN ss4 = '0' THEN seq_page END) AS halaman_non_prio, " +
            "COUNT(DISTINCT CASE WHEN ss4 = '1' THEN seq_envelope END) AS amplop_prio," +
            "COUNT(DISTINCT CASE WHEN ss4 = '0' THEN seq_envelope END) AS amplop_non_prio, " +
            "COUNT(*) AS jumlah_halaman, " +
            "COUNT(DISTINCT seq_envelope) AS jumlah_amplop, " +
            "MIN(seq_envelope) AS seq_awal, " +
            "MAX(seq_envelope) AS seq_akhir " +
            "FROM t_log2 " +
            "WHERE b1 = 0";

        List<ReportRow> result = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ReportRow r = new ReportRow();
                    r.no = 1;
                    r.cycle = cetak;
                    r.jenisSurat = product;
                    r.jumlahAcc = rs.getInt("jumlah_acc");
                    r.jumlahHalaman = rs.getInt("jumlah_halaman");
                    r.jumlahAmplop = rs.getInt("jumlah_amplop");
                    r.amplopPrio = rs.getInt("amplop_prio");
                    r.amplopNonPrio = rs.getInt("amplop_non_prio");
                    r.halamanPrio = rs.getInt("halaman_prio");
                    r.halamanNonPrio = rs.getInt("halaman_non_prio");
                    r.seqAwal = rs.getInt("seq_awal");
                    r.seqAkhir = rs.getInt("seq_akhir");
                    result.add(r);
                }
            }
        }
        return result;
    }
    
    public List<ReportRow> getStatementReport(String cetak, String product) throws SQLException {

        String sql =
            "SELECT " +
            "COUNT(DISTINCT id_customer) AS jumlah_acc, " +
            "COUNT(DISTINCT CASE WHEN ss4 = '1' THEN seq_page END) AS halaman_prio, " +
            "COUNT(DISTINCT CASE WHEN ss4 = '0' THEN seq_page END) AS halaman_non_prio, " +
            "COUNT(DISTINCT CASE WHEN ss4 = '1' THEN seq_envelope END) AS amplop_prio," +
            "COUNT(DISTINCT CASE WHEN ss4 = '0' THEN seq_envelope END) AS amplop_non_prio, " +
            "COUNT(*) AS jumlah_halaman ," +
            "COUNT(DISTINCT seq_envelope) AS jumlah_amplop, " +
            "MIN(seq_envelope) AS seq_awal, " +
            "MAX(seq_envelope) AS seq_akhir " +
            "FROM t_log2 " +
            "WHERE  b1 = 2";

        List<ReportRow> result = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ReportRow r = new ReportRow();
                    r.no = 1;
                    r.cycle = cetak;
                    r.jenisSurat = product;
                    r.jumlahAcc = rs.getInt("jumlah_acc");
                    r.jumlahHalaman = rs.getInt("jumlah_halaman");
                    r.jumlahAmplop = rs.getInt("jumlah_amplop");
                    r.amplopPrio = rs.getInt("amplop_prio");
                    r.amplopNonPrio = rs.getInt("amplop_non_prio");
                    r.halamanPrio = rs.getInt("halaman_prio");
                    r.halamanNonPrio = rs.getInt("halaman_non_prio");
                    r.seqAwal = rs.getInt("seq_awal");
                    r.seqAkhir = rs.getInt("seq_akhir");
                    result.add(r);
                }
            }
        }
        return result;
    }
    
    public List<ReportRow> getEstateReport(String cetak, String product) throws SQLException {

        String sql =
            "SELECT " +
            "COUNT(DISTINCT id_customer) AS jumlah_acc, " +
            "COUNT(DISTINCT CASE WHEN ss4 = '1' THEN seq_page END) AS halaman_prio, " +
            "COUNT(DISTINCT CASE WHEN ss4 = '0' THEN seq_page END) AS halaman_non_prio, " +
            "COUNT(DISTINCT CASE WHEN ss4 = '1' THEN seq_envelope END) AS amplop_prio," +
            "COUNT(DISTINCT CASE WHEN ss4 = '0' THEN seq_envelope END) AS amplop_non_prio, " +
            "COUNT(*) AS jumlah_halaman, " +
            "COUNT(DISTINCT seq_envelope) AS jumlah_amplop, " +
            "MIN(seq_envelope) AS seq_awal, " +
            "MAX(seq_envelope) AS seq_akhir " +
            "FROM t_log2 " +
            "WHERE b1 = 1";

        List<ReportRow> result = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ReportRow r = new ReportRow();
                    r.no = 1;
                    r.cycle = cetak;
                    r.jenisSurat = product;
                    r.jumlahAcc = rs.getInt("jumlah_acc");
                    r.jumlahHalaman = rs.getInt("jumlah_halaman");
                    r.jumlahAmplop = rs.getInt("jumlah_amplop");
                    r.amplopPrio = rs.getInt("amplop_prio");
                    r.amplopNonPrio = rs.getInt("amplop_non_prio");
                    r.halamanPrio = rs.getInt("halaman_prio");
                    r.halamanNonPrio = rs.getInt("halaman_non_prio");
                    r.seqAwal = rs.getInt("seq_awal");
                    r.seqAkhir = rs.getInt("seq_akhir");
                    result.add(r);
                }
            }
        }
        return result;
    }
    
    public List<LogModel> getLogForNormalize() throws SQLException{
        List<LogModel> list = new ArrayList<>();
        
        String sql = "SELECT id_log, barcode, id_customer, name1, " +
                "address1, address2, address3, address4, address5, address6, " +
                "product_name, courier_name, b1, ss1, ss2, ss3, ss4, ss5 " +
                "FROM t_log2 ORDER BY b1, address6 asc";
        
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    LogModel m = new LogModel();
                    m.setIdLog(rs.getLong("id_log"));
                    m.setBarcode(rs.getString("barcode"));
                    m.setNoPolis(rs.getString("id_customer"));
                    m.setName(rs.getString("name1"));
                    m.setAddress1(rs.getString("address1"));
                    m.setAddress2(rs.getString("address2"));
                    m.setAddress3(rs.getString("address3"));
                    m.setAddress4(rs.getString("address4"));
                    m.setAddress5(rs.getString("address5"));
                    m.setZipcode(rs.getString("address6"));
                    m.setProductName(rs.getString("product_name"));
                    m.setCourier(rs.getString("courier_name"));
                    m.setFlag(rs.getInt("b1"));
                    m.setFlagEnv(rs.getString("ss4"));
                    
                    list.add(m);
                }
        }
        return list;
    }
    
    public void insertNormalizedLog2(LogModel m, int seqEnv, int seqCust, int seqPage)
        throws SQLException {

        String sql =
            "INSERT INTO t_log2 (" +
            "barcode,id_customer,name1,name2,name3," +
            "address1,address2,address3,address4,address5,address6," +
            "b1,b2,b3,b4,b5,b6," +
            "s1,s2,s3,s4,s5,s6," +
            "product_name,courier_name," +
            "seq_page,seq_customer,seq_envelope," +
            "ss1,ss2,ss3,ss4,ss5,ss6" +
            ") VALUES (" +
            "?,?,?,?,?," +
            "?,?,?,?,?,?," +
            "?,?,?,?,?,?," +
            "?,?,?,?,?,?," +
            "?,?," +
            "?,?,?,"+
            "?,?,?,?,?,?" +
            ")";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            int i = 1;
            ps.setString(i++, m.getBarcode());
            ps.setString(i++, m.getNoPolis());
            ps.setString(i++, m.getName());
            ps.setString(i++, ""); 
            ps.setString(i++, "");
            ps.setString(i++, m.getAddress1());
            ps.setString(i++, m.getAddress2());
            ps.setString(i++, m.getAddress3());
            ps.setString(i++, m.getAddress4());
            ps.setString(i++, m.getAddress5());
            ps.setString(i++, m.getZipcode());
            ps.setInt(i++, m.getFlag());
            ps.setInt(i++, 0);
            ps.setInt(i++, 0);
            ps.setString(i++, "");
            ps.setInt(i++, 0);
            ps.setInt(i++, 0);
            ps.setInt(i++, 0);
            ps.setInt(i++, 0);
            ps.setInt(i++, 0);
            ps.setInt(i++, 0);
            ps.setInt(i++, 0);
            ps.setInt(i++, 0);
            ps.setString(i++, m.getProductName());
            ps.setString(i++, m.getCourier());
            ps.setInt(i++, seqPage);
            ps.setInt(i++, seqCust);
            ps.setInt(i++, seqEnv);
            ps.setString(i++, "");
            ps.setString(i++, "");
            ps.setString(i++, "");
            ps.setString(i++, m.getFlagEnv());
            ps.setString(i++, "");
            ps.setString(i++, "");

            ps.executeUpdate();
        }
    }


    
    public void createCourierLog(String kurir, String cetak, String product, String logDir) throws IOException {
        String fileName = product + "_" + cetak + "_" + "Log_" + kurir + ".LOG";
        File file = new File(logDir, fileName);

        if (!file.exists()) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                bw.write("Cycle\tNo Polis\tNama\tAddress 1\tAddress 2\tAddress 3\tAddress 4\tAddress 5\tKodepos\tKurir\tSeqEnv\tSeqCust\tSeqPage");
                bw.newLine();
            }
        }
    }
    
    public void createLogProses(String cetak, String product, String logDir) throws IOException {
        String fileName = product + "_" + cetak + "_" + "Log_Proses_Data.LOG";
        File file = new File(logDir, fileName);

        if (!file.exists()) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                bw.write("Barcode\tNo Polis\tNama\tAddress 1\tAddress 2\tAddress 3\tAddress 4\tAddress 5\tKodepos\tProduk\tKurir\tSeqEnv\tSeqCust\tSeqPage\tFlag");
                bw.newLine();
            }
        }
    }
    
    public void deleteLog() throws SQLException{
        try(PreparedStatement ps = conn.prepareStatement("DELETE FROM t_log2")){
            ps.executeUpdate();
        }
    }
}
