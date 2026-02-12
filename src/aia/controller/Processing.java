/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.controller;

import aia.controller.CreatePDFReport.ReportRow;
import aia.model.BaseModel;
import aia.model.Connector;
import aia.model.LogModel;
import aia.model.PolisModel;
import aia.model.ProductionLogModel;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.mysql.jdbc.Statement;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import service.LogRepository;
import service.TextReaderService;
import utils.DbfConverter;
import utils.ProductCode;

/**
 *
 * @author Ratino
 */
public class Processing {
    Directory dir = new Directory();
    PathDirectory pd = new PathDirectory();
    DbfConverter converter = new DbfConverter();
    TextReaderService readerService = new TextReaderService();
    TextModification txt = new TextModification();
    CreatePDFReport rp = new CreatePDFReport();
    private String pathData = new String();
    private String directoryFont = new String();
    private String directoryPaper = new String();
    private String directoryReadyToPrint = new String();
    private String currentDirectory = new String();
    private String LogScanCyDirectory = new String();
    private String readyToPrintCyDirectory = new String();
    private String readyToPrintCyLogDirectory = new String();
    private String readyToPrintCyPrintDirectory = new String();
    private String readyToPrintCyReportDirectory = new String();
    private String readyToPrintCySortingDirectory = new String();
    private String readyToPrintCyStateDirectory = new String();
    private String readyToPrintCyEstateDirectory = new String();
    List<ReportRow> printingRows = new ArrayList<>();
    List<ReportRow> statementRows = new ArrayList<>();
    List<ReportRow> estateRows = new ArrayList<>();
    
    private BaseFont barcodeFont;
    private BaseFont arial;
    Connector cn =  null;
    
    public Processing() throws IOException, SQLException, SQLException{
        cn = new Connector();
    }
    
    public void createPdf(String[] params) throws DocumentException, FileNotFoundException, IOException, SQLException, Exception{
        long startRun = System.currentTimeMillis();
        LogRepository repo = new LogRepository(cn.getKoneksi());
        String product = params[3];
        String cycle = params[5];
        String cetak = params[6];
        String bund = null;
        setCurrentDirectory(""+ new java.io.File(".").getCanonicalPath(), product, cetak);
        String[] dirParams = {readyToPrintCyPrintDirectory, readyToPrintCyReportDirectory, readyToPrintCySortingDirectory,
                                readyToPrintCyLogDirectory, directoryFont};
        
        setDirectory(currentDirectory);
        
        pathData = converter.convertDbfToText(params[0]);
        CreateDb();
        
        // proses
        process2(pathData, product, cetak, dirParams);
        processCetak(cetak, directoryFont, product);
        processState(cetak, directoryFont, product);
        processEstate(cetak, directoryFont, product);
        normalizeLog("POS", cetak, product, readyToPrintCyLogDirectory);
        
        printingRows = repo.getPrintingReport(cetak, product);
        statementRows = repo.getStatementReport(cetak, product);
        estateRows = repo.getEstateReport(cetak, product);

        if(ProductCode.POA_PRODUCTS.contains(product)) {
            bund = "POA";
        } else if(ProductCode.NB_PRODUCTS.contains(product)) {
            bund = "New Business";
        } else {
            bund = product;
        }
        
        rp.createPrintingReport(product, bund, cetak, readyToPrintCyReportDirectory, printingRows);
        rp.createStatementReport(product, bund, cetak, readyToPrintCyReportDirectory, statementRows);
        rp.createEstateReport(product, bund, cetak, readyToPrintCyReportDirectory, estateRows);
        
        long endRun = System.currentTimeMillis();
        long duration = endRun - startRun;
        System.out.println("All Duration : " + duration + " ms");
    }
    
//    private void process(String path, String product, String cetak ,String[] pathOutput){
//        try {
//            
//            List<BaseModel> allData = readerService.readFromText(path, product);
//            BasePdfGenerator generator = LetterFactory.getPdfTemplate(product);
//            for(BaseModel data : allData){
//                generator.generate(data, product, cetak, pathOutput);
//
//            }
//            
//        } catch (Exception ex) {
//            Logger.getLogger(Processing.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
     private void process2(String path, String product, String cetak, String[] pathOutput){
        // by Dika untuk grouping data
        int seqCust = 0;
        int seqEnvelope = 0;
        int seqPage = 0;
        try {
            // Harus di grouping per polis untuk data nya, jangan hanya disimpan ke list
            Connection conn = cn.getKoneksi();
            LogRepository repo = new LogRepository(conn);
            
            List<BaseModel> allData = readerService.readFromText(path, product);
            Map<String, List<BaseModel>> dataPerPolis = new LinkedHashMap<>();

            for (BaseModel data : allData) {
                String noPolis = data.getChdrnum(); 

                if (!dataPerPolis.containsKey(noPolis)) {
                    dataPerPolis.put(noPolis, new ArrayList<>());
                }

                dataPerPolis.get(noPolis).add(data);
            }

            BasePdfGenerator generator = LetterFactory.getPdfTemplate(product);
            for (List<BaseModel> polisData : dataPerPolis.values()) {
                try {
                    seqCust++;
                    seqEnvelope++;

                    generator.generate(polisData, product, cetak, pathOutput);

                    String fn = generator.getFileName();
                    String br = generator.getBarcodes();
                    float yAddr = generator.getYaddr();
                    float xBox = generator.getXbox();
                    boolean isPriority = generator.isPriority(polisData.get(0));

                    String amplopF;
                    if (isPriority) {
                        amplopF = "1";
                    } else {
                        amplopF = "0";
                    }
                    
                    Path pdfPath = Paths.get(pathOutput[2]).resolve(fn);
                    int pageCount = getPdfPageCount(pdfPath.toString());

                    for (int i = 1; i <= pageCount; i++) {
                        seqPage++;

                        repo.insertData(
                            polisData,
                            product,
                            cetak,
                            br,
                            fn,
                            String.valueOf(seqCust),
                            String.valueOf(seqEnvelope),
                            String.valueOf(seqPage),
                            yAddr,
                            xBox,
                            amplopF
                        );
                    }
                } catch (Exception e) {
                    Logger.getLogger(Processing.class.getName())
                          .log(Level.SEVERE, "Generate gagal polis " + polisData.get(0).getChdrnum(), e);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Processing.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void setCurrentDirectory(String currentDirectory, String product, String cycle) throws IOException{
        Directory dir = new Directory();
        PathDirectory pd = new PathDirectory();
        this.currentDirectory = currentDirectory;
//        tempDirectory = pd.configurePath(this.currentDirectory + "\\" +"TEMP\\");
//        paperDirectory = currentDirectory + "\\" + "Paper" + "\\";
//        fontsDirectory = pd.configurePath(this.currentDirectory + "\\" + "FONTS\\");
        
        readyToPrintCyDirectory = this.currentDirectory + "\\READY TO PRINT" + "\\" + product + "_" + cycle  + "\\";
        LogScanCyDirectory = this.currentDirectory + "\\LOG SCAN\\";
        readyToPrintCyLogDirectory = pd.configurePath(readyToPrintCyDirectory + "\\LOG\\");
        readyToPrintCyPrintDirectory = pd.configurePath(readyToPrintCyDirectory + "\\OUTPUT\\");
        readyToPrintCyReportDirectory = pd.configurePath(readyToPrintCyDirectory + "\\REPORT\\");
        readyToPrintCySortingDirectory = pd.configurePath(readyToPrintCyDirectory + "\\SORTING");
        readyToPrintCyStateDirectory = pd.configurePath(readyToPrintCyDirectory + "\\PDF STATEMENT\\");
        readyToPrintCyEstateDirectory = pd.configurePath(readyToPrintCyDirectory + "\\PDF SEC ESTATEMENT\\");
//        File tempDir = new File(tempDirectory);
//        dir.deleteFolder2(tempDir);
        
        File f1 = new File(readyToPrintCyLogDirectory);
        File f2 = new File(readyToPrintCyPrintDirectory);
        File f3 = new File(readyToPrintCyReportDirectory);
//        File f4 = new File(tempDirectory);
        File f5 = new File(readyToPrintCyDirectory);
        File f9 = new File(LogScanCyDirectory);
        f5.mkdirs();
        f9.mkdirs();
        File f6 = new File(readyToPrintCySortingDirectory);
        File f7 = new File(readyToPrintCyStateDirectory);
        File f8 = new File(readyToPrintCyEstateDirectory);
        
        f1.mkdirs();
        f2.mkdirs();
        f3.mkdirs();
//        f4.mkdirs();
        f6.mkdirs();
        f7.mkdirs();
        f8.mkdirs();
                
    }
    
    public void setDirectory(String currentDir) {        
        directoryFont = pd.configurePath(currentDir + "\\\\" + "FONTS\\\\");
        directoryPaper = pd.configurePath(currentDir + "\\\\" + "PAPER\\\\");
        directoryReadyToPrint = pd.configurePath(currentDir + "\\\\" + "READY TO PRINT\\\\");
    }
    
    private void CreateDb() throws SQLException {
        LogModel model = new LogModel();
        model.createTable((Statement) cn.getStmt());
    }
    
    private void processCetak(String cetak, String path, String product) throws Exception {
        Connection conn = cn.getKoneksi();
        LogRepository repo = new LogRepository(conn);
        
        List<BaseModel> rows = repo.getPdf("0");
        
        int seqCust = 0;
        int seqEnv = 0;
        int seqPage = 0;
        
        String lastPolis = null;
        
        List<BaseModel> forCombine = new ArrayList<>();
        
        for(BaseModel m : rows){
            if(!m.getChdrnum().equals(lastPolis)){
                seqCust++;
                seqEnv++;
                lastPolis = m.getChdrnum();
            }
            seqPage++;

            m.setSeqPage(String.valueOf(seqPage));
            m.setSeqCust(String.valueOf(seqCust));
            m.setSeqEnv(String.valueOf(seqEnv));

            repo.insertCetakFromLog2(m.getId(), m.getSeqPage(), m.getSeqCust(), m.getSeqEnv());

            forCombine.add(m);
        }
        
        combinePdf(forCombine, readyToPrintCySortingDirectory, readyToPrintCyPrintDirectory + "PRINT_" + cetak + ".pdf", path, cetak, product);
    }
    
    private void processEstate(String cetak, String path, String product) throws SQLException, DocumentException, IOException{
        Connection conn = cn.getKoneksi();
        LogRepository repo = new LogRepository(conn);
        
        List<BaseModel> rows = repo.getPdf("1");
        
        int seqCust = 0;
        int seqEnv = 0;
        int seqPage = 0;
        
        String lastPolis = null;
        
        for(BaseModel m : rows){
            if(!m.getChdrnum().equals(lastPolis)){
                seqCust++;
                seqEnv++;
                lastPolis = m.getChdrnum();
            }
            seqPage++;
            m.setSeqPage(String.valueOf(seqPage));
            m.setSeqCust(String.valueOf(seqCust));
            m.setSeqEnv(String.valueOf(seqEnv));
            
            repo.insertEstateFromLog2(m.getId(), m.getSeqPage(), m.getSeqCust(), m.getSeqEnv());
        }
        List<BaseModel> forInject = repo.getEstateForPdf();
        
        injectPassword(forInject, readyToPrintCySortingDirectory, readyToPrintCyEstateDirectory, path, cetak, product);
    }
    
    private void processState(String cetak, String path, String product) throws SQLException, Exception{
        Connection conn = cn.getKoneksi();
        LogRepository repo = new LogRepository(conn);
        
        List<BaseModel> rows = repo.getPdf("2");
        
        int seqCust = 0;
        int seqEnv = 0;
        int seqPage = 0;
        
        String lastPolis = null;
        
        for(BaseModel m : rows){
            if(!m.getChdrnum().equals(lastPolis)){
                seqCust++;
                seqEnv++;
                lastPolis = m.getChdrnum();
            }
            seqPage++;
            m.setSeqPage(String.valueOf(seqPage));
            m.setSeqCust(String.valueOf(seqCust));
            m.setSeqEnv(String.valueOf(seqEnv));
            
            repo.insertStateFromLog2(m.getId(), m.getSeqPage(), m.getSeqCust(), m.getSeqEnv());
        }
        List<BaseModel> forStamp = repo.getstateForPdf();
        
        stampStatePdf(forStamp, readyToPrintCySortingDirectory, readyToPrintCyStateDirectory, path, cetak, product);
    }
    
    private void combinePdf(List<BaseModel> rows, String sourceDir, String outputFile, String path, String cetak, String product) throws Exception {
        Document document = new Document();
        PdfCopy copy = new PdfCopy(document, new FileOutputStream(outputFile));
        document.open();

        int rowIndex = 0;
        String lastPolis = null;

        while (rowIndex < rows.size()) {

            BaseModel firstRow = rows.get(rowIndex);
            String pdfName = firstRow.getPdfname();

            File pdfFile = Paths.get(sourceDir, pdfName).toFile();
            PdfReader reader = new PdfReader(pdfFile.getAbsolutePath());

            ByteArrayOutputStream stampedOut = new ByteArrayOutputStream();
            PdfStamper stamper = new PdfStamper(reader, stampedOut);

            int pages = reader.getNumberOfPages();

            for (int page = 1; page <= pages; page++) {
                BaseModel logRow = rows.get(rowIndex);
                boolean isStamped = !logRow.getChdrnum().equals(lastPolis);

                if (rowIndex >= rows.size()) {
                    throw new IllegalStateException(
                        "Jumlah row log lebih sedikit dari jumlah halaman PDF: " + pdfName
                    );
                }
                
                if(isStamped){
                    stampBarcode(
                        stamper,
                        logRow,
                        page,
                        path,
                        logRow.getChdrnum() + product + cetak
                    );
                    lastPolis = logRow.getChdrnum();
                }
                
                rowIndex++;
            }

            stamper.close();
            reader.close();

            PdfReader stampedReader = new PdfReader(new ByteArrayInputStream(stampedOut.toByteArray()));
            for (int i = 1; i <= stampedReader.getNumberOfPages(); i++) {
                copy.addPage(copy.getImportedPage(stampedReader, i));
            }
            stampedReader.close();
        }

        document.close();
        copy.close();
    }
    
    private void injectPassword(List<BaseModel> rows, String sourceFile, String outputFile, String path, String cetak, String product) throws FileNotFoundException, DocumentException, IOException{
        int rowIndex = 0;
        String lastPolis = null;

        while (rowIndex < rows.size()) {

            BaseModel firstRow = rows.get(rowIndex);
            String pdfName = firstRow.getPdfname();
            String dob = firstRow.getDteBirth();
            
            String password = createPassword(dob);

            File sourcePdf = Paths.get(sourceFile, pdfName).toFile();
            File outputPdf = Paths.get(outputFile, pdfName).toFile();

            PdfReader reader = new PdfReader(sourcePdf.getAbsolutePath());
            PdfStamper stamper =
                new PdfStamper(reader, new FileOutputStream(outputPdf));
            
            stamper.setEncryption(
                password.getBytes(),
                password.getBytes(),
                PdfWriter.ALLOW_PRINTING,
                PdfWriter.ENCRYPTION_AES_128
            );

            int pages = reader.getNumberOfPages();

            for (int page = 1; page <= pages; page++) {

                if (rowIndex >= rows.size()) {
                    throw new IllegalStateException(
                        "Jumlah row log lebih sedikit dari jumlah halaman PDF: " + pdfName
                    );
                }

                BaseModel logRow = rows.get(rowIndex);

                boolean isStamped = !Objects.equals(logRow.getChdrnum(), lastPolis);

                if (isStamped) {
                    stampBarcode(
                        stamper,
                        logRow,
                        page,
                        path,
                        logRow.getChdrnum() + product + cetak
                    );
                    lastPolis = logRow.getChdrnum();
                }

                rowIndex++;
            }

            stamper.close();
            reader.close();
            
            lastPolis = null;
        }
    }
    
    public String createPassword(String dob){
        final String fallback = "00000000";
        String raw = dob.trim();
        
        if(raw != null){
            if(raw.length() == 7){
                return "0" + dob;
            }
            return raw;
        }
        return fallback;
    }
    
    private void stampStatePdf(List<BaseModel> rows, String sourceDir, String outputDir, String path, String cetak, String product) throws Exception {
        for (BaseModel m : rows) {

            File sourcePdf = Paths.get(sourceDir, m.getPdfname()).toFile();
            File outputPdf = Paths.get(outputDir, m.getPdfname()).toFile();

            PdfReader reader = new PdfReader(sourcePdf.getAbsolutePath());
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputPdf));
            
            stampBarcode(
                stamper,
                m,
                1,
                path,
                m.getChdrnum() + product + cetak
            );

            stamper.close();
            reader.close();
        }
    }

    
    private void stampBarcode (PdfStamper stamper, BaseModel model, int page, String path, String barcode) throws DocumentException, IOException {
        String y = model.getyLast();
        float yBarcode = Float.parseFloat(y);
        String x = model.getxLast();
        float xBarcode = Float.parseFloat(x);
        String seqEnvDb = model.getSeqEnv();
        int seqEnv = Integer.parseInt(seqEnvDb);
        
        PdfContentByte canvas = stamper.getOverContent(page);
        
        barcodeFont = BaseFont.createFont(path + "free3of9.ttf", BaseFont.IDENTITY_H, true);
        arial = BaseFont.createFont(path + "Arial.ttf", BaseFont.IDENTITY_H, true);
        canvas.setFontAndSize(barcodeFont, 22);
        
        canvas.showTextAligned(
            Element.ALIGN_CENTER,
            "*" + barcode + "*",
            xBarcode,   
            yBarcode,
            0
        );
        yBarcode -= 7;

        canvas.setFontAndSize(arial, 7f);
        canvas.showTextAligned(
            Element.ALIGN_CENTER,
            "*" + barcode+ "* " + txt.norm6Digit(seqEnv),
            xBarcode,
            yBarcode,
            0
        );
    }
    
    private int getPdfPageCount(String pdfPath) throws IOException {
        PdfReader reader = new PdfReader(pdfPath);
        int pages = reader.getNumberOfPages();
        reader.close();
        return pages;
    }
    
    public void normalizeLog(String kurir, String cetak, String product, String logDir) throws IOException, SQLException {
        LogRepository repo = new LogRepository(cn.getKoneksi());
        try {
            List<LogModel> rows = repo.getLogForNormalize();
            repo.deleteLog();
            repo.createCourierLog(kurir, cetak, product, logDir);
            repo.createLogProses(cetak, product, logDir);
            
            BufferedWriter bwProses = new BufferedWriter(new FileWriter(logDir + product + "_" + cetak + "_" + "Log_Proses_Data.LOG",true));
            BufferedWriter bwCourier = new BufferedWriter(new FileWriter(logDir + product + "_" + cetak + "_" + "Log_" + kurir + ".LOG",true));
            
            int seqEnv = 0;
            int seqCust = 0;
            int seqPage = 0;
            String lastPolis = null;
            String flag = null;
            
            for(LogModel m : rows){
                if(m.getFlag() == 0){
                    flag = "Cetak";
                } else if(m.getFlag() == 1){
                    flag = "Estatement";
                } else {
                    flag = "Statement";
                }
                
                if(!m.getNoPolis().equals(lastPolis)){
                    seqCust++;
                    seqEnv++;
                    lastPolis = m.getNoPolis();
                }
                seqPage++;
                
                repo.insertNormalizedLog2(m, seqEnv, seqCust, seqPage);
                
                bwProses.write(
                    m.getBarcode() + "\t" +
                    m.getNoPolis() + "\t" +
                    m.getName() + "\t" +
                    m.getAddress1() + "\t" +
                    m.getAddress2() + "\t" +
                    m.getAddress3() + "\t" +
                    m.getAddress4() + "\t" +
                    m.getAddress5() + "\t" +
                    m.getZipcode() + "\t" +
                    m.getProductName()+ "\t" +
                    m.getCourier() + "\t" +
                    seqEnv + "\t" +
                    seqCust + "\t" +
                    seqPage + "\t" +
                    flag
                );
                bwProses.newLine();
                
                if (kurir.equals(m.getCourier())) {
                    bwCourier.write(
                        cetak + "\t" +
                        m.getNoPolis() + "\t" +
                        m.getName() + "\t" +
                        m.getAddress1() + "\t" +
                        m.getAddress2() + "\t" +
                        m.getAddress3() + "\t" +
                        m.getAddress4() + "\t" +
                        m.getAddress5() + "\t" +
                        m.getZipcode() + "\t" +
                        m.getCourier() + "\t" +
                        seqEnv + "\t" +
                        seqCust + "\t" +
                        seqPage
                    );
                    bwCourier.newLine();
                }
            }
            bwCourier.close();
            bwProses.close();
        } catch (Exception e){
            throw e;
        }
    }
    
    // inject gagal
    private String testing(String dob) {
        String fallback = "";
        if (dob == null) {
            fallback = "00000000";
            return fallback;
        }

        String raw = dob.trim();
        if (!raw.matches("\\d+")) {
            fallback = "00000000";
            return fallback;
        }
        
        String normalized;
        
        if (raw.length() == 8) {
            normalized = raw;
        } else if (raw.length() == 7) {
            normalized = "0" + raw;
        } else {
            fallback = "00000000";
            return fallback;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy").withResolverStyle(ResolverStyle.STRICT);

            LocalDate.parse(normalized, formatter);
            return normalized;

        } catch (Exception e) {
            fallback = "00000000";
            return fallback;
        }
    }
    
    


}
