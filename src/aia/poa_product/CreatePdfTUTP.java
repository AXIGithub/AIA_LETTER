/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.poa_product;

import aia.controller.BasePdfGenerator;
import aia.controller.TextModification;
import aia.model.BaseModel;
import aia.model.productMapping.TutpModel;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Ratino
 */
public class CreatePdfTUTP implements BasePdfGenerator{
    
    TextModification txt = new TextModification();
    private PdfReader dataReaderPreprinted = null;
    private PdfWriter writerNew = null;
    private PdfImportedPage pageData = null;
    
    private BaseFont arial;
    private BaseFont arialItalic;
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
        
        if(!(first instanceof TutpModel)){
            throw new IllegalArgumentException("Not Tutp Model");
        }
        
        TutpModel model = (TutpModel) first;
        yAddr = 312;
        xAddr = 48;
        yInfo = 312;
        xInfo = 272;
        xDot = 381;
        xDataInfo = 386;
        sortingDir = params[2];
        getCurrentDir();
        Document document = new Document(PageSize.A5.rotate());
        PdfWriter writer = PdfWriter.getInstance(document,
                new FileOutputStream(sortingDir + product + "_" + model.getChdrnum() + ".pdf"));
        
        dataReaderPreprinted = new PdfReader(paperDir + "PAPER SRN.pdf");

        document.open();
        PdfContentByte canvas = writer.getDirectContent();
        PdfPTable table = new PdfPTable(2);
        
        canvas.beginText();
        canvas.endText();
        pageData = writer.getImportedPage(dataReaderPreprinted, 1);
        canvas.addTemplate(pageData, 0, 0);

        BaseFont helvatica = BaseFont.createFont(BaseFont. HELVETICA, BaseFont.CP1250, BaseFont.NOT_EMBEDDED);
        BaseFont helvaticaBold = BaseFont.createFont(BaseFont. HELVETICA_BOLD, BaseFont.CP1250, BaseFont.NOT_EMBEDDED);
        
        arial = BaseFont.createFont(dirFonts + "Arial.ttf", BaseFont.IDENTITY_H, true);
        arialItalic = BaseFont.createFont(dirFonts + "Arial_Italic.ttf", BaseFont.IDENTITY_H, true);
        arialBold = BaseFont.createFont(dirFonts + "arial_bold.ttf", BaseFont.IDENTITY_H, true);
        
        canvas.beginText();
        canvas.setFontAndSize(arial, 6.5f);

        // ================= HEADER =======================
        canvas.showTextAligned(Element.ALIGN_LEFT, "Kepada yang terhormat :", xAddr, yAddr, 0);
        yAddr = (float) (yAddr - 10);
        canvas.setFontAndSize(arialBold, 7.5f);
        
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
        if (!hasText(model.getAddr01())) emptyCount++;
        if (!hasText(model.getAddr02())) emptyCount++;
        if (!hasText(model.getAddr03())) emptyCount++;
        if (!hasText(model.getAddr04())) emptyCount++;
        if (!hasText(model.getAddr05())) emptyCount++;

        List<String> addressLines = new ArrayList<>();

        if (emptyCount >= 2) {
            if (hasText(model.getAddr01())) {
                addressLines.add(model.getAddr01());
            }

            String line23 = Stream.of(model.getAddr02(), model.getAddr03())
                    .filter(this::hasText)
                    .collect(Collectors.joining(" "));
            if (!line23.isEmpty()) {
                addressLines.add(line23);
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
        
        canvas.setFontAndSize(arial, 7.5f);
        for (String line : addressLines) {
            canvas.showTextAligned(Element.ALIGN_LEFT, line, xAddr, yAddr, 0);
            yAddr -= 10;
        }

        // ================= INFO POLIS =====================
        canvas.setFontAndSize(arialBold, 8f);
        canvas.showTextAligned(Element.ALIGN_LEFT,
                "LAPORAN INVESTASI MANFAAT BERTAHAP", xInfo, yInfo, 0);
        yInfo -= 16;

        // Nama Produk
        canvas.setFontAndSize(arial, 7.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Produk", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 7.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getProdName(), xDataInfo, yInfo, 0);
        yInfo -= 11.5;
        
        // No Polis
        canvas.setFontAndSize(arial, 7.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "No. Polis", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 7.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getChdrnum(), xDataInfo, yInfo, 0);
        yInfo -= 11.5;

        // Mata Uang
        canvas.setFontAndSize(arial, 7.5f);
        String currency = model.getCntcurr().equalsIgnoreCase("IRP") ? "Rupiah" : "Dollar Amerika";
        String kode = model.getCntcurr().equalsIgnoreCase("IRP") ? model.getCntcurr() : "USD";
        canvas.showTextAligned(Element.ALIGN_LEFT, "Mata Uang", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 7.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, currency, xDataInfo, yInfo, 0);
        yInfo -= 11.5;

        // Uang Pertanggungan
        canvas.setFontAndSize(arial, 7.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Uang Pertanggungan", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 7.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, txt.setCurrencyIdr(model.getSumins()), xDataInfo, yInfo, 0);
        canvas.endText();
        
        // ===================== ISI SURAT ==========================
        
        int topY = 197;
        Font fontArialBold = new Font(arialBold, 7.5f);
        table.setTotalWidth(new float[] {400, 90});
        table.setLockedWidth(true);
        
        PdfPCell header1 = new PdfPCell(new Phrase("Keterangan", fontArialBold));
        header1.setBackgroundColor(new BaseColor(255, 255, 255));
        header1.setPaddingLeft(10);
        header1.setHorizontalAlignment(Element.ALIGN_LEFT);
        header1.setBorder(Rectangle.LEFT |Rectangle.BOTTOM | Rectangle.TOP);
        header1.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        PdfPCell header2 = new PdfPCell(new Phrase("Jumlah (" + kode + ")", fontArialBold));
        header2.setBackgroundColor(new BaseColor(255, 255, 255));
        header2.setPaddingRight(10);
        header2.setHorizontalAlignment(Element.ALIGN_RIGHT);
        header2.setVerticalAlignment(Element.ALIGN_MIDDLE);
        header2.setBorder(Rectangle.RIGHT |Rectangle.BOTTOM | Rectangle.TOP);
        
        table.addCell(header1);
        table.addCell(header2);
        
        
        
        List<RowData> rows = new ArrayList<>();

        rows.add(new RowData(
                "Akumulasi manfaat bertahap periode sebelumnya",
                model.getAlocamt01()
        ));

        rows.add(new RowData(
                "Akumulasi bunga S/D " + txt.convertDateMM(model.getZinnto()),
                model.getAlocamt02()
        ));

        rows.add(new RowData(
                "Manfaat bertahap " + txt.convertDateMM(model.getLreqdate()),
                model.getAlocamt03()
        ));

        
        PdfPCell spacer = new PdfPCell(new Phrase(" "));
        spacer.setColspan(2);
        spacer.setBorder(Rectangle.NO_BORDER);
        spacer.setFixedHeight(6);

        table.addCell(spacer);
        
        for (RowData row : rows) {

            if (row.amount == null || row.amount.equalsIgnoreCase("0.00")) {
                continue;
            }

            PdfPCell c1 = new PdfPCell(
                    new Phrase(row.label, fontArialBold)
            );
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
            c1.setBorder(Rectangle.NO_BORDER);
            c1.setPaddingTop(1);
            c1.setPaddingLeft(10);
            c1.setPaddingRight(4);
            c1.setPaddingBottom(1);

            PdfPCell c2 = new PdfPCell(
                    new Phrase(txt.setCurrencyIdr(row.amount), fontArialBold)
            );
            c2.setHorizontalAlignment(Element.ALIGN_RIGHT);
            c2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            c2.setBorder(Rectangle.NO_BORDER);
            c2.setPaddingTop(1);
            c2.setPaddingLeft(4);
            c2.setPaddingRight(10);
            c2.setPaddingBottom(1);

            table.addCell(c1);
            table.addCell(c2);
        }

        PdfPCell spacer2 = new PdfPCell(new Phrase(" "));
        spacer2.setColspan(2);
        spacer2.setBorder(Rectangle.NO_BORDER);
        spacer2.setFixedHeight(6);

        table.addCell(spacer2);
        
        PdfPCell t1 = new PdfPCell(new Phrase("Saldo Akhir", fontArialBold));
        t1.setHorizontalAlignment(Element.ALIGN_LEFT);
        t1.setVerticalAlignment(Element.ALIGN_MIDDLE);
        t1.setBorder(Rectangle.LEFT |Rectangle.BOTTOM | Rectangle.TOP);
        t1.setPaddingLeft(10);
        table.addCell(t1);
        
        PdfPCell t2 = new PdfPCell(new Phrase(txt.setCurrencyIdr(model.getAlocamt04()), fontArialBold));
        t2.setHorizontalAlignment(Element.ALIGN_RIGHT);
        t2.setVerticalAlignment(Element.ALIGN_MIDDLE);
        t2.setBorder(Rectangle.RIGHT |Rectangle.BOTTOM | Rectangle.TOP);
        t2.setPaddingRight(10);
        table.addCell(t2);
        table.writeSelectedRows(0, -1, xAddr, topY, canvas);
        topY -= 90;
        
        canvas.beginText();
        canvas.setFontAndSize(arial, 7.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Jakarta, " + txt.convertDateMM(cetak), xAddr, topY, 0);
        canvas.endText();

        document.close();
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private boolean hasText(String s) {
        return s != null && !s.trim().isEmpty();
    }
    
    class RowData {
        String label;
        String amount;

        RowData(String label, String amount) {
            this.label = label;
            this.amount = amount;
        }
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
    
    private static final Set<String> PRIORITY_TYPES =
            Set.of("kosong");
    
    public boolean isPriority(BaseModel model){
        String cntType = model.getCnttype();
        if(cntType == null){
            return false;
        }
        return PRIORITY_TYPES.contains(cntType);
    }
    
    public String getFileName(){
        return fileName;
    }
    
    public String getBarcodes(){
        return barcode;
    }
    
    public float getYaddr(){
        return yAddr;
    }
    
    public float getXbox(){
        return boxCenterX;
    }
}
