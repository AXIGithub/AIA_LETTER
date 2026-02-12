/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.poa_product;

import aia.controller.BasePdfGenerator;
import aia.controller.TextModification;
import aia.model.BaseModel;
import aia.model.productMapping.CvtModel;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
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
public class CreatePdfCVT implements BasePdfGenerator{
    
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
        
        if(!(first instanceof CvtModel)){
            throw new IllegalArgumentException("Not Cvt Model");
        }
        
        CvtModel model = (CvtModel) first;
        yAddr = 712;
        xAddr = 72;
        yInfo = 717;
        xInfo = 296;
        xDot = 481;
        xDataInfo = 486;
        sortingDir = params[2];
        getCurrentDir();
        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document,
                new FileOutputStream(sortingDir + product + model.getChdrnum() + "_" + cetak + ".pdf"));
        
        dataReaderPreprinted = new PdfReader(paperDir + "PAPER ACD.pdf");

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
        arialUnderline = BaseFont.createFont(dirFonts + "ArialUnderline.ttf", BaseFont.IDENTITY_H, true);
        arialBold = BaseFont.createFont(dirFonts + "arial_bold.ttf", BaseFont.IDENTITY_H, true);
        
        canvas.beginText();
        canvas.setFontAndSize(arial, 8.5f);

        // ================= HEADER =======================
        canvas.showTextAligned(Element.ALIGN_LEFT, "Kepada yang terhormat :", xAddr, yAddr, 0);
        yAddr = (float) (yAddr - 10);
        canvas.setFontAndSize(arialBold, 9f);
        
        String owner = model.getSurnamepp(); 
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
        
        canvas.setFontAndSize(arial, 9f);
        for (String line : addressLines) {
            canvas.showTextAligned(Element.ALIGN_LEFT, line, xAddr, yAddr, 0);
            yAddr -= 10;
        }

        // ================= INFO POLIS =====================
        // No Polis
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "No. Polis", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getChdrnum(), xDataInfo, yInfo, 0);
        yInfo -= 12.5;

        // Nama Tertanggung
        canvas.setFontAndSize(arial, 8.5f);
        String insured = model.getSurnamevp().trim();
        String[] words = insured.split("\\s+");
        int firstLineWordCount;

        if (words.length <= 2) {
            firstLineWordCount = words.length;
        } else if (words.length == 3) {
            firstLineWordCount = 2;
        } else {
            firstLineWordCount = 3;
        }
        
        String insured1 = "";
        String insured2 = "";

        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            if (i < firstLineWordCount) {
                sb1.append(words[i]).append(" ");
            } else {
                sb2.append(words[i]).append(" ");
            }
        }

        insured1 = sb1.toString().trim();
        insured2 = sb2.toString().trim();
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tertanggung/Pihak Yang Diasuransikan", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, insured1, xDataInfo, yInfo, 0);
        yInfo -= 12.5;
        if (!insured2.isEmpty()) {
            canvas.showTextAligned(Element.ALIGN_LEFT, insured2, xDataInfo, yInfo, 0);
            yInfo -= 12.5;
        }

        // Jumlah Premi/Kontribusi Dasar
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Jumlah Premi/Kontribusi Dasar", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, txt.setCurrencyIdr(model.getInstprem()), xDataInfo, yInfo, 0);
        yInfo -= 12.5;

        // Jumlah Premi/Kontribusi Top-Up Berkala
        canvas.showTextAligned(Element.ALIGN_LEFT, "Jumlah Premi/Kontribusi Top-Up Berkala", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, txt.setCurrencyIdr(model.getPrem()), xDataInfo, yInfo, 0);
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
        canvas.showTextAligned(Element.ALIGN_LEFT, "Status Polis", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, status, xDataInfo, yInfo, 0);
        yInfo -= 12.5;

        // Cara Bayar
        String periode;
        switch (model.getCarabayar()) {
            case "MONTHLY":
                periode = "Bulanan";
                break;
            case "QUARTERLY":
                periode = "Triwulan";
                break;
            case "HALFYEARLY":
                periode = "Semester";
                break;
            case "YEARLY":
                periode = "Tahunan";
                break;
            default:
                periode = "Sekaligus";
                break;
        }
        canvas.showTextAligned(Element.ALIGN_LEFT, "Cara Bayar", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, periode, xDataInfo, yInfo, 0);
        yInfo -= 12.5;

        // Tanggal Mulai Asuransi
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tanggal Mulai Asuransi", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, txt.convertDateMM(model.getRcd()), xDataInfo, yInfo, 0);
        yInfo -= 12.5;

        // Nama Produk
        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Produk", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getProdName() , xDataInfo, yInfo, 0);
        yInfo -= 12.5;
        
        // Uang Pertanggungan/Santunan Asuransi Dasar
        canvas.showTextAligned(Element.ALIGN_LEFT, "Uang Pertanggungan/Santunan Asuransi Dasar", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, txt.setCurrencyIdr(model.getSumins()) , xDataInfo, yInfo, 0);
        yInfo -= 12.5;
        
        // Mata Uang
        String currency = model.getCntcurr().equalsIgnoreCase("IRP") ? "Rupiah" : "Dollar Amerika";
        canvas.showTextAligned(Element.ALIGN_LEFT, "Mata Uang", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, currency , xDataInfo, yInfo, 0);
        yInfo -= 12.5;
        
        // Tanggal cetak
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tanggal cetak", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, txt.convertDateMM(cetak) , xDataInfo, yInfo, 0);
        yInfo -= 12.5;
        
        // ===================== ISI SURAT ==========================

        int topY = 540;
        int bottomY = 50;  
        int rightX = 548;
        int leftX = 62;
        
        String paragraph = "Selamat atas pencapaian Anda! Anda tercatat telah berhasil mencapai status [b]" + model.getZvpllsta()+ "[/b] pada Status Keanggotaan Vitality " +
               "Program Anda. Kami sungguh menghargai setiap langkah dan pilihan sehat yang Anda ambil untuk hidup lebih sehat, lebih " +
               "lama, lebih baik. Berikut adalah rangkuman transaksi pembayaran Diskon Biaya Akuisisi:";
        
        String bar = "RANGKUMAN TRANSAKSI (" + txt.convertDateMM(model.getCurrfrom()) + " - " + txt.convertDateMM(model.getCurrto()) + ")";
        
        String perihal = "Status Keanggotaan Vitality Program (29 November 2025): " + model.getZvpllsta();
        
        txt.writeParagraph(paragraph, document, leftX, bottomY, rightX, topY, canvas, arial, 8.5f, 1.2f);
        canvas.endText();
        topY -= 55;
        
        float barHeight = 17f;
        float barX = 62f;
        float barWidth = 510f;
        
        canvas.saveState();
        canvas.setColorFill(new BaseColor(200,200,200));
        canvas.rectangle(barX, topY, barWidth, barHeight);
        canvas.fill();
        canvas.restoreState();
        
        canvas.beginText();
        ColumnText ctBar = new ColumnText(canvas);
        ctBar.setSimpleColumn(
                barX,                     
                topY,                 
                barX + barWidth,          
                topY + barHeight      
        );

        Paragraph p = new Paragraph(bar, new Font(arialBold, 10));
        p.setAlignment(Element.ALIGN_CENTER);
        p.setLeading(12);

        ctBar.addElement(p);
        ctBar.go();
        topY -= 10;
        txt.writeParagraph(perihal, document, leftX, bottomY, rightX, topY, canvas, arialBold, 8.5f, 1.2f);
        topY -= 30;
        canvas.endText();
        
        table.setTotalWidth(new float[] {386, 125});
        table.setLockedWidth(true);
        
        PdfPCell header1 = new PdfPCell(new Phrase("Cashback", new Font(arialBold, 8.5f)));
        header1.setBackgroundColor(new BaseColor(230, 230, 230));
        header1.setPadding(5);
        header1.setHorizontalAlignment(Element.ALIGN_CENTER);
        header1.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        PdfPCell header2 = new PdfPCell(new Phrase("Jumlah\n(" + currency + ")", new Font(arialBold, 8.5f)));
        header2.setBackgroundColor(new BaseColor(230, 230, 230));
        header2.setPadding(5);
        header2.setHorizontalAlignment(Element.ALIGN_CENTER);
        header2.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        table.addCell(header1);
        table.addCell(header2);
        
        List<String[]> listData = new ArrayList<>();
        listData.add(new String[]{"29 November 2023", txt.setCurrencyIdr(model.getZcsbphs())});
        
        Font fontArial = new Font(arial, 8.5f);
        Font fontArialB = new Font(arialBold, 8.5f);
        
        for (String[] row : listData) {
            PdfPCell c1 = new PdfPCell(new Phrase(row[0], fontArial));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
            c1.setPadding(4);
            table.addCell(c1);
                    
            PdfPCell c2 = new PdfPCell(new Phrase(row[1], fontArial));
            c2.setHorizontalAlignment(Element.ALIGN_LEFT);
            c2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            c2.setPadding(4);
            table.addCell(c2);

        }
        
        PdfPCell t1 = new PdfPCell(new Phrase("Total", fontArialB));
        t1.setHorizontalAlignment(Element.ALIGN_CENTER);
        t1.setVerticalAlignment(Element.ALIGN_MIDDLE);
        t1.setPadding(5);
        table.addCell(t1);
        
        PdfPCell t2 = new PdfPCell(new Phrase("[AMOUNT]", fontArialB));
        t2.setHorizontalAlignment(Element.ALIGN_RIGHT);
        t2.setVerticalAlignment(Element.ALIGN_MIDDLE);
        t2.setPadding(5);
        table.addCell(t2);
        
        table.writeSelectedRows(0, -1, 62, topY, canvas);
        topY -= 80;
        
        canvas.beginText();
        String afterTable = "Keterangan:\n" +
                "Informasi mengenai syarat dan ketentuan Diskon Biaya Akuisisi selengkapnya dapat dilihat pada Polis Anda.\n\n\n" +
                "Tetap aktif dan teruskan untuk melakukan pilihan sehat untuk hidup lebih sehat, lebih lama, lebih baik. Semakin tinggi status " +
                "vitality Anda, semakin tinggi jumlah cashback yang bisa Anda dapatkan";
        
        txt.writeParagraph(afterTable, document, leftX, bottomY, rightX, topY, canvas, arial, 8.5f, 1.6f);
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
            Set.of("UDR", "UFR", "UGR", "UXR", "UYR");
    
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
