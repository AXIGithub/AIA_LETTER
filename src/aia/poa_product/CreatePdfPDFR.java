/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.poa_product;

import aia.controller.BasePdfGenerator;
import aia.controller.TextModification;
import aia.model.BaseModel;
import aia.model.productMapping.PdfrModel;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Ratino
 */
public class CreatePdfPDFR implements BasePdfGenerator{
    
    TextModification txt = new TextModification();
    private PdfReader dataReaderPreprinted = null;
    private PdfWriter writerNew = null;
    private PdfImportedPage pageData = null;
    
    private BaseFont arial;
    private BaseFont arialUnderline;
    private BaseFont arialBold;
    private float yAddr;
    private float xAddr;
    private float yInfo;
    private float xInfo;
    private float xDot;
    private float xDataInfo;
    private float boxCenterX;
    
    private String currDir = new String();
    private String paperDir = new String();
    private String dirFonts = new String();
    private String sortingDir = new String();
    private String outputDir = new String();
    private String fileName = new String();
    private String barcode = new String();
    
    

    @Override
    public void generate(List<BaseModel> dataList, String product, String cetak, String[] params) throws Exception {
        if (dataList.isEmpty()) {
            throw new IllegalArgumentException("Data kosong");
        }

        BaseModel first = dataList.get(0);

        if (!(first instanceof PdfrModel)) {
            throw new IllegalArgumentException("Not Pdfr Model");
        }

        PdfrModel model = (PdfrModel) first;
        yAddr = 712;
        xAddr = 72;
        yInfo = 717;
        xInfo = 296;
        xDot = 410;
        xDataInfo = 417;
        sortingDir = params[2];
        Path sortingPath = Paths.get(sortingDir).normalize();
        Files.createDirectories(sortingPath);

        getCurrentDir();
        Document document = new Document(PageSize.A4);
        fileName = product + model.getChdrnum() + "_" + cetak + ".pdf";
        Path pdfPath = sortingPath.resolve(fileName);

        PdfWriter writer = PdfWriter.getInstance(
            document,
            Files.newOutputStream(pdfPath)
        );
        
        dataReaderPreprinted = new PdfReader(paperDir + "PAPER PDFR.pdf");
        document.open();
        PdfContentByte canvas = writer.getDirectContent();
        PdfPTable table = new PdfPTable(5);
        
        canvas.beginText();
        canvas.endText();
        pageData = writer.getImportedPage(dataReaderPreprinted, 1);
        canvas.addTemplate(pageData, 0, 0);

        BaseFont helvatica = BaseFont.createFont(BaseFont. HELVETICA, BaseFont.CP1250, BaseFont.NOT_EMBEDDED);
        BaseFont helvaticaBold = BaseFont.createFont(BaseFont. HELVETICA_BOLD, BaseFont.CP1250, BaseFont.NOT_EMBEDDED);
        
        arial = BaseFont.createFont(dirFonts + "Arial.ttf", BaseFont.IDENTITY_H, true);
        arialUnderline = BaseFont.createFont(dirFonts + "ArialUnderline.ttf", BaseFont.IDENTITY_H, true);
        arialBold = BaseFont.createFont(dirFonts + "arial_bold.ttf", BaseFont.IDENTITY_H, true);
        
        canvas.beginText();
        canvas.setFontAndSize(arial, 8.5f);

        // ================= HEADER =======================
        canvas.showTextAligned(Element.ALIGN_LEFT, "Kepada yang terhormat :", xAddr, yAddr, 0);
        yAddr = (float) (yAddr - 10);
        canvas.setFontAndSize(arialBold, 9f);
        
        String owner = model.getOwner(); 
        String owner1 = owner;
        String owner2 = "";
        
        String[] split = owner.split(" "); 
        
        if (owner.length() > 25) {
            StringBuilder sb1 = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();

            for (int i = 0; i < split.length; i++) {
                if (i <= 1) { 
                    sb1.append(split[i]).append(" ");
                } else {
                    sb2.append(split[i]).append(" ");
                }
            }

            owner1 = sb1.toString().trim();
            owner2 = sb2.toString().trim();
        }
        
        canvas.showTextAligned(Element.ALIGN_LEFT, "Bapak/Ibu " + owner1, xAddr, yAddr, 0);
        yAddr -= 10;
        if (!owner2.isEmpty()) {
            canvas.showTextAligned(Element.ALIGN_LEFT, owner2, xAddr, yAddr, 0);
            yAddr -= 10;
        }
        
        int emptyCount = 0;
        int count = 5;
        if (!hasText(model.getAddr01())){
            emptyCount++;
            count--;
        }
        if (!hasText(model.getAddr02())) {
            emptyCount++;
            count--;
        }
        if (!hasText(model.getAddr03())) {
            emptyCount++;
            count--;
        }
        if (!hasText(model.getAddr04())) {
            emptyCount++;
            count--;
        }
        if (!hasText(model.getAddr05())) {
            emptyCount++;
            count--;
        }

        List<String> addressLines = new ArrayList<>();

        if (emptyCount >= 2) {
            if (hasText(model.getAddr01())) {
                addressLines.add(model.getAddr01());
            }
            if(count == 3) {
                addressLines.add(model.getAddr02());
                addressLines.add(model.getAddr03());
            } else {
                String line23 = Stream.of(model.getAddr02(), model.getAddr03())
                    .filter(this::hasText)
                    .collect(Collectors.joining(" "));
                if (!line23.isEmpty()) {
                    addressLines.add(line23);
                }
            }
            String line45 = Stream.of(model.getAddr04(), model.getAddr05())
                    .filter(this::hasText)
                    .collect(Collectors.joining(" "));
            if (!line45.isEmpty()) {
                addressLines.add(line45);
            }

        } else {
            if (hasText(model.getAddr01())) addressLines.add(model.getAddr01());
            if (hasText(model.getAddr02())) addressLines.add(model.getAddr02());
            if (hasText(model.getAddr03())) addressLines.add(model.getAddr03());
            if (hasText(model.getAddr04())) addressLines.add(model.getAddr04());
            if (hasText(model.getAddr05())) addressLines.add(model.getAddr05());
        }
        
        if (hasText(model.getPcode())) {
            addressLines.add(model.getPcode());
        }
        
        canvas.setFontAndSize(arial, 9f);
        for (String line : addressLines) {
            canvas.showTextAligned(Element.ALIGN_LEFT, line, xAddr, yAddr, 0);
            yAddr -= 10;
        }
        
        float boxX = 0;
        float boxWidth = 353;
        boxCenterX = (boxX + boxWidth) / 2;

        yAddr -= 10;
        barcode = model.getChdrnum() + product + cetak;
//        canvas.showTextAligned(Element.ALIGN_CENTER, "*" + barcode + "*", boxCenterX, yAddr, 0);
//        yAddr -= 7;
//        
//        canvas.setFontAndSize(arial, 7f);
//        String tbarcode;
//        tbarcode = "*" + barcode + "*";
//        canvas.showTextAligned(Element.ALIGN_CENTER, tbarcode + " " + txt.norm6Digit(seqEnv), boxCenterX, yAddr, 0);
//        
        // ================= INFO POLIS =====================
        canvas.setFontAndSize(arialBold, 9f);
        canvas.showTextAligned(Element.ALIGN_LEFT,
                "LAPORAN TRANSAKSI PREMI", xInfo, yInfo - 5, 0);
        yInfo -= 17.5;
        
        // Nama Produk
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Produk", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getProdName(), xDataInfo, yInfo, 0);
        yInfo -= 12.5;

        // No Polis
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "No. Polis", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getChdrnum(), xDataInfo, yInfo, 0);
        yInfo -= 12.5;
        
        // Tanggal Mulai Asuransi
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tanggal Mulai Asuransi", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, txt.convertDateMM(model.getOccdate()), xDataInfo, yInfo, 0);
        yInfo -= 12.5;
        
        // Uang Pertanggungan
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Uang Pertanggungan", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Rp" + txt.setCurrencyIdr(model.getCoverage()), xDataInfo, yInfo, 0);
        yInfo -= 12.5;
        
        // Periode
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Periode", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getAddf(), xDataInfo, yInfo, 0);
        yInfo -= 12.5;
        
        // Status Polis
        canvas.setFontAndSize(arial, 8.5f);
        String status;
        switch (model.getStatcode()) {
            case "IF":
                status = "Aktif";
                break;
            case "SU":
                status = "SU";
                break;
            case "MA":
                status = "MA";
                break;
            case "EX":
                status = "EX";
                break;
            case "LA":
                status = "Tidak Aktif";
                break;
            default:
                status = "Unknown";
                break;
        }
        canvas.showTextAligned(Element.ALIGN_LEFT, "Status Polis", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, status, xDataInfo, yInfo, 0);
        canvas.endText();
        
        
        // ===================== ISI SURAT ==========================

        int topY = 575;

        table.setWidthPercentage(100);
        table.setWidths(new float[] {75, 120, 80, 80, 110});
        ColumnText ct = new ColumnText(writer.getDirectContent());
            ct.setSimpleColumn(
                xAddr,
                235,
                559,
                topY
            );
            
        table.setHeaderRows(1);
        
        PdfPCell header1 = new PdfPCell(new Phrase("Tanggal", new Font(arialBold, 8.8f)));
        header1.setPadding(4);
        header1.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);

        PdfPCell header2 = new PdfPCell(new Phrase("Keterangan", new Font(arialBold, 8.8f)));
        header2.setPadding(4);
        header2.setBorder(Rectangle.TOP | Rectangle.BOTTOM);

        PdfPCell header3 = new PdfPCell(new Phrase("Premi Risiko", new Font(arialBold, 8.8f)));
        header3.setPadding(4);
        header3.setHorizontalAlignment(Element.ALIGN_RIGHT);
        header3.setBorder(Rectangle.TOP | Rectangle.BOTTOM);

        PdfPCell header4 = new PdfPCell(new Phrase("Premi Deposit", new Font(arialBold, 8.8f)));
        header4.setPadding(4);
        header4.setHorizontalAlignment(Element.ALIGN_RIGHT);
        header4.setBorder(Rectangle.TOP | Rectangle.BOTTOM);

        PdfPCell header5 = new PdfPCell(new Phrase("Saldo Premi Deposit", new Font(arialBold, 8.8f)));
        header5.setPadding(4);
        header5.setHorizontalAlignment(Element.ALIGN_RIGHT);
        header5.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);

        table.addCell(header1);
        table.addCell(header2);
        table.addCell(header3);
        table.addCell(header4);
        table.addCell(header5);
        
        PdfPCell spacer = new PdfPCell(new Phrase(" "));
        spacer.setColspan(5);
        spacer.setFixedHeight(8f);
        spacer.setBorder(Rectangle.NO_BORDER);
        table.addCell(spacer);
        
        for (BaseModel bm : dataList) {

            if (!(bm instanceof PdfrModel)) continue;
            PdfrModel trx = (PdfrModel) bm;

            table.addCell(createCell(trx.getTranscDat(), Element.ALIGN_LEFT));
            table.addCell(createCell(trx.getDescName(), Element.ALIGN_LEFT));
            table.addCell(createCell(txt.setCurrencyIdr(trx.getPrRisk()), Element.ALIGN_RIGHT));
            table.addCell(createCell(txt.setCurrencyIdr(trx.getPrDeposit()), Element.ALIGN_RIGHT));
            table.addCell(createCell(txt.setCurrencyIdr(trx.getBalance()), Element.ALIGN_RIGHT));
        }
        
        ct.addElement(table);
        
        int threeshold;
        do {
            threeshold = ct.go();
            canvas.setFontAndSize(arial, 8.8f);
            canvas.showTextAligned(Element.ALIGN_LEFT, "Jakarta, " + txt.convertDateMM(cetak), xAddr, 210, 0);

            if (ColumnText.hasMoreText(threeshold)) {
                document.newPage();
                pageData = writer.getImportedPage(dataReaderPreprinted, 2);
                canvas.addTemplate(pageData, 0, 0);
                
                ct.setSimpleColumn(
                    xAddr,
                    235,
                    559,
                    722
                );
                table.addCell(spacer);
                canvas.setFontAndSize(arial, 8.8f);
                canvas.showTextAligned(Element.ALIGN_LEFT, "Jakarta, " + txt.convertDateMM(cetak), xAddr, 210, 0);
            }

        } while (ColumnText.hasMoreText(threeshold));


        
        document.close();

//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private boolean hasText(String s) {
        return s != null && !s.trim().isEmpty();
    }
    
    private PdfPCell createCell(String text, int align) {
        PdfPCell cell = new PdfPCell(new Phrase(text, new Font(arial, 8.3f)));
        cell.setPaddingTop(3);
        cell.setPaddingLeft(6);
        cell.setPaddingRight(6);
        cell.setPaddingBottom(1);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(align);
        return cell;
    }

    
    private void getCurrentDir(){
        try {
            currDir = ""+new java.io.File(".").getCanonicalPath();
            paperDir = currDir + "\\\\" + "PAPER\\\\";
            dirFonts = currDir + "\\\\" + "FONTS\\\\";
        } catch (IOException ex) {
            Logger.getLogger(CreatePdfAPH.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public boolean isPriority(BaseModel model) {
        if (model.getCnttype() == null || model.getCnttype().trim().isEmpty()) {
            return false;
        }
        return model.getCnttype().equalsIgnoreCase("P");
    }
    
    public String getFileName() {
        return fileName;
    }
    
    public String getBarcodes() {
        return barcode;
    }
    
    public float getYaddr(){
        return yAddr;
    }
    
    public float getXbox(){
        return boxCenterX;
    }
}
