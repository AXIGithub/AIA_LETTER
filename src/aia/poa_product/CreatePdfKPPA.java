/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.poa_product;

import aia.controller.BasePdfGenerator;
import aia.controller.TextModification;
import aia.model.BaseModel;
import aia.model.productMapping.KppaModel;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;
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
public class CreatePdfKPPA implements BasePdfGenerator{
    
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
        
        if(!(first instanceof KppaModel)){
            throw new IllegalArgumentException("Not Kppa Model");
        }
        
        KppaModel model = (KppaModel) first ;
        yAddr = 712;
        xAddr = 72;
        yInfo = 717;
        xInfo = 296;
        xDot = 410;
        xDataInfo = 417;
        sortingDir = params[2];
        getCurrentDir();
        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document,
                new FileOutputStream(sortingDir + product + model.getChdrnum() + "_" + cetak + ".pdf"));
        
        dataReaderPreprinted = new PdfReader(paperDir + "PAPER AIA.pdf");

        document.open();
        PdfContentByte canvas = writer.getDirectContent();
        PdfPTable table = new PdfPTable(4);
        
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
        
        canvas.setFontAndSize(arial, 9.5f);
        for (String line : addressLines) {
            canvas.showTextAligned(Element.ALIGN_LEFT, line, xAddr, yAddr, 0);
            yAddr -= 10;
        }

        // ================= INFO POLIS =====================
        // No Polis
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "No. Polis", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getChdrnum(), xDataInfo, yInfo, 0);
        yInfo -= 12.5;

        // Nama Tertanggung
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Tertanggung/Peserta", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getZname(), xDataInfo, yInfo, 0);
        yInfo -= 12.5;

        // Nama Produk
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Produk", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getProdName(), xDataInfo, yInfo, 0);
        yInfo -= 12.5;

        // Tanggal Mulai
        String occdate = model.getOccdate();
        int dotIndex = occdate.indexOf(".");
        String subsOccdate = occdate.substring(0, dotIndex);
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tanggal Mulai Asuransi", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, txt.convertDateMM(subsOccdate), xDataInfo, yInfo, 0);
        yInfo -= 12.5;

        // Status Polis
        String status;
        switch (model.getStatcode()) {
            case "IF":
                status = "Aktif";
                break;
            case "CF":
                status = "CF";
                break;
            case "NF":
                status = "Tidak Aktif";
                break;
            default:
                status = "Unknown";
                break;
        }
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Status Polis", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, status, xDataInfo, yInfo, 0);

        // ========== PERIHAL ===========================
        canvas.setFontAndSize(arialBold, 9f);
        canvas.showTextAligned(Element.ALIGN_LEFT,
                "Perihal : Pemberitahuan Status Polis", xAddr, 561, 0);
        
        // ===================== ISI SURAT ==========================

        int topY = 545;
        int bottomY = 50;  
        int rightX = 548;
        
        String trnsdate = model.getTransDate();
        int dotIndex2 = trnsdate.indexOf(".");
        String subsTrnsdate = trnsdate.substring(0, dotIndex2);
        
        String paragraph = "Bapak/Ibu [b]" + model.getOwner() + "[/b] yang terhormat,\n\n" +
                "Terima kasih atas kepercayaan yang Bapak/Ibu berikan kepada PT AIA FINANCIAL (AIA) sebagai penyedia " +
                "kebutuhan perlindungan asuransi bagi Bapak/Ibu dan keluarga.\n\n" +
                "Bersama ini kami sampaikan bahwa kami telah menerima pembayaran Premi/Kontribusi Anda dan status Polis " +
                "Anda telah menjadi aktif kembali sejak tanggal " + txt.convertDateMM(subsTrnsdate) + ". Sebagai informasi, masa tunggu dan juga kondisi " +
                "lainnya seperti pengecualian penyakit tertentu akan dihitung kembali sejak tanggal pemulihan terakhir.\n\n" +
                "Adapun data Premi/Kontribusi Polis Bapak/Ibu sebagai berikut :";
        
        txt.writeParagraph(paragraph, document, xAddr, bottomY, rightX, topY, canvas, arial, 9.2f, 1.2f);
        topY -= 116;
        canvas.endText();
        
        table.setTotalWidth(new float[] {100, 120, 120, 120});
        table.setLockedWidth(true);
        
        PdfPCell header1 = new PdfPCell(new Phrase("Cara Bayar", new Font(arialBold, 8.5f)));
        header1.setBackgroundColor(new BaseColor(180, 180, 180));
        header1.setPaddingBottom(5);
        header1.setPaddingRight(5);
        header1.setPaddingLeft(5);
        header1.setHorizontalAlignment(Element.ALIGN_CENTER);
        header1.setVerticalAlignment(Element.ALIGN_TOP);
        
        PdfPCell header2 = new PdfPCell(new Phrase("Premi/Kontribusi", new Font(arialBold, 8.5f)));
        header2.setBackgroundColor(new BaseColor(180, 180, 180));
        header2.setPaddingBottom(5);
        header2.setPaddingRight(5);
        header2.setPaddingLeft(5);
        header2.setHorizontalAlignment(Element.ALIGN_CENTER);
        header2.setVerticalAlignment(Element.ALIGN_TOP);
        
        PdfPCell header3 = new PdfPCell(new Phrase("Premi/Kontribusi\nTerakhir Dibayar", new Font(arialBold, 8.5f)));
        header3.setBackgroundColor(new BaseColor(180, 180, 180));
        header3.setPaddingBottom(5);
        header3.setPaddingRight(5);
        header3.setPaddingLeft(5);
        header3.setHorizontalAlignment(Element.ALIGN_CENTER);
        header3.setVerticalAlignment(Element.ALIGN_TOP);
        
        PdfPCell header4 = new PdfPCell(new Phrase("Premi/Kontribusi Jatuh\nTempo Berikutnya", new Font(arialBold, 8.5f)));
        header4.setBackgroundColor(new BaseColor(180, 180, 180));
        header4.setPaddingBottom(5);
        header4.setPaddingRight(5);
        header4.setPaddingLeft(5);
        header4.setHorizontalAlignment(Element.ALIGN_CENTER);
        header4.setVerticalAlignment(Element.ALIGN_TOP);
        
        table.addCell(header1);
        table.addCell(header2);
        table.addCell(header3);
        table.addCell(header4);
        
        String periode = model.getBillFreq().equalsIgnoreCase("12") ? "Bulanan" : "Tahunan";
        String premi = txt.setCurrencyIdr(model.getPremi());
        
        String instFrom = model.getInstfrom();
        int dotIndex3 = instFrom.indexOf(".");
        String subsInstfrom = instFrom.substring(0, dotIndex3);
        
        String ptd = model.getPtd();
        int dotIndex4 = ptd.indexOf(".");
        String subsPtd = ptd.substring(0, dotIndex4);
        
        List<String[]> listData = new ArrayList<>();
        listData.add(new String[]{periode, "Rp" + premi,  txt.convertDateMM(subsInstfrom), txt.convertDateMM(subsPtd)});
        
        Font fontArial = new Font(arial, 8.5f);
        Font fontArialB = new Font(arialBold, 8.5f);
        
        for (String[] row : listData) {
            PdfPCell c1 = new PdfPCell(new Phrase(row[0], fontArial));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
            c1.setPadding(4);
            table.addCell(c1);
                    
            PdfPCell c2 = new PdfPCell(new Phrase(row[1], fontArial));
            c2.setHorizontalAlignment(Element.ALIGN_CENTER);
            c2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            c2.setPadding(4);
            table.addCell(c2);

            PdfPCell c3 = new PdfPCell(new Phrase(row[2], fontArial));
            c3.setHorizontalAlignment(Element.ALIGN_CENTER);
            c3.setVerticalAlignment(Element.ALIGN_MIDDLE);
            c3.setPadding(4);
            table.addCell(c3);
            
            PdfPCell c4 = new PdfPCell(new Phrase(row[3], fontArial));
            c4.setHorizontalAlignment(Element.ALIGN_CENTER);
            c4.setVerticalAlignment(Element.ALIGN_MIDDLE);
            c4.setPadding(4);
            table.addCell(c4);

        }
        
        table.writeSelectedRows(0, -1, xAddr, topY, canvas);
        topY -= 60;
        
        canvas.beginText();
        String afterTable = "Untuk produk-produk tertentu, perlindungan Asuransi Tambahan Anda dapat diaktifkan kembali. Silakan " +
                "menghubungi kami untuk hal ini.\n\nUntuk penjelasan lebih lanjut, silakan menghubungi AIA Customer Care kami mulai hari Senin – Jumat pada pukul " +
                "08.00 – 17.00 WIB, dengan senang hati kami akan membantu Anda.\n\n\nJakarta, [b]" + txt.convertDateMM(cetak) + "[/b]\n" +
                "Hormat kami,";
        
        txt.writeParagraph(afterTable, document, xAddr, bottomY, rightX, topY, canvas, arial, 9.2f, 1.6f);
        canvas.endText();
        
        document.close();


//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private boolean hasText(String s) {
        return s != null && !s.trim().isEmpty();
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
            Set.of("LMR");
    
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
