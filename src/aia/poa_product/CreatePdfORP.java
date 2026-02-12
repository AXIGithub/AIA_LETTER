/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.poa_product;

import aia.controller.BasePdfGenerator;
import aia.controller.TextModification;
import aia.model.BaseModel;
import aia.model.productMapping.OrpModel;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
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
public class CreatePdfORP implements BasePdfGenerator{
    
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
        
        if(!(first instanceof OrpModel)){
            throw new IllegalArgumentException("Not Orp Model");
        }
        
        OrpModel model = (OrpModel) first;
        yAddr = 712;
        xAddr = 72;
        yInfo = 717;
        xInfo = 296;
        xDot = 408;
        xDataInfo = 416;
        sortingDir = params[2];
        getCurrentDir();
        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document,
                new FileOutputStream(sortingDir + product + "_" + model.getChdrnum() + ".pdf"));
        
        dataReaderPreprinted = new PdfReader(paperDir + "PAPER AIA.pdf");

        document.open();
        PdfContentByte canvas = writer.getDirectContent();
        
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
        canvas.setFontAndSize(arialBold, 9.5f);
        
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
        canvas.showTextAligned(Element.ALIGN_LEFT, "No. Polis", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getChdrnum(), xDataInfo, yInfo, 0);
        yInfo -= 12.5;

        // Nama Produk
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Produk", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getProdName(), xDataInfo, yInfo, 0);
        yInfo -= 12.5;

        // Nama Tertanggung/Peserta 
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Tertanggung/Peserta", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getLifeAss(), xDataInfo, yInfo, 0);
        yInfo -= 12.5;

        // Tanggal Mulai Asuransi
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tanggal Mulai Asuransi", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, txt.convertDateMM(model.getOccdate()), xDataInfo, yInfo, 0);
        yInfo -= 12.5;

        // Tanggal Cetak
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tanggal Cetak", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, txt.convertDateMM(cetak), xDataInfo, yInfo, 0);
        yInfo -= 12.5;


        // ========== PERIHAL ===========================
        canvas.setFontAndSize(arialBold, 9.2f);
        canvas.showTextAligned(Element.ALIGN_LEFT,
                "Hal : Status Agen Asuransi", xAddr, 561, 0);
        canvas.showTextAligned(Element.ALIGN_RIGHT,
                "Jakarta, " + txt.convertDateMM(cetak) , 544, 561, 0);
        
        // ===================== ISI SURAT ==========================

        int topY = 547;
        int bottomY = 50;   
        int rightX = 548;
        int rightX2 = 548;
        
        String paragraph = "Dengan Hormat,\n\n" +
                "Terima kasih atas kepercayaan yang Bapak/Ibu berikan kepada PT AIA FINANCIAL (AIA) sebagai penyedia kebutuhan " +
                "asuransi bagi Bapak/Ibu dan keluarga.\n\n" +
                "Sehubungan dengan surat yang telah kami kirimkan kepada Bapak/Ibu tentang Pemberitahuan Terminasi Agen " +
                "tertanggal surat [b]" + txt.convertDateMM(model.getTrmdatehp1()) + "[/b]. Bersama ini kami ingin menyampaikan hal sebagai berikut:";
        
        txt.writeParagraph(paragraph, document, xAddr, bottomY, rightX, topY, canvas, arial, 9.1f, 1.2f);
        topY -= 90;
        
        String[] numbering = {
            "Bahwa Bapak/Ibu [b]" + model.getOldAgname()+ "[/b] sudah tidak aktif sebagai Agen Asuransi AIA efektif per tanggal [b]" +
            txt.convertDateMM(model.getTrmdate()) + "[/b], oleh karena itu Agen Asuransi tersebut sudah tidak dapat memberikan pelayanan terkait Polis Asuransi " +
            "Bapak/Ibu.",
            "Selanjutnya, Anda akan dibantu oleh Agen Asuransi pengganti dari AIA yaitu Bapak/Ibu [b]" + model.getNewAgname() +
            "[/b] dengan kode agen [b]" + model.getAgentCd() + "[/b] dan nomor lisensi AAJI [b]" + model.getLicenseCd() + "[/b] melalui nomor telepon " +
            "[b]" + model.getNewAghpno() + "[/b] dari [b]" + model.getNewSrvsta() + "[/b]. Bapak/Ibu [b]" + model.getNewAgname() + "[/b] akan segera menghubungi " +
            "Bapak/Ibu untuk komunikasi lebih lanjut.",
            "Apabila terdapat perubahan nomor telepon atau alamat korespondensi, mohon Bapak/Ibu dapat memberikan " +
            "informasi kepada kami agar komunikasi dari AIA dapat terjalin dengan lancar."
        };
        
        Font normalFont = new Font(arial, 9f);
        Font boldFont   = new Font(arialBold, 9f);

        for (int i = 0; i < numbering.length; i++) {

            ColumnText ct = new ColumnText(canvas);
            ct.setSimpleColumn(xAddr, bottomY, rightX2, topY);

            Paragraph p = new Paragraph();
            p.setLeading(13f);
            p.setAlignment(Element.ALIGN_JUSTIFIED);

            p.setIndentationLeft(30);
            p.setFirstLineIndent(-11);
            
            p.add(new Chunk((i + 1) + ". ", normalFont));
            
            for (Chunk c : parseBoldText(numbering[i], normalFont, boldFont)) {
                p.add(c);
            }

            ct.addElement(p);
            ct.go();

            topY = (int) ct.getYLine();
        }
        topY -= 10;
        
        String paragraph2 = "Melalui surat ini pula kami sampaikan bahwa AIA berkomitmen untuk terus memberikan pelayanan terbaik kepada " +
                "Bapak/Ibu. Saat ini untuk mendapatkan Informasi seputar Polis, Klaim, Benefit, Perubahan Data serta Informasi Umum " +
                "lainnya, kami menyediakan layanan [b]Tanya ANYA[/b] yang dapat diakses melalui WhatsApp di nomor [b]081119601000[/b].\n\n" +
                "Demikian kami sampaikan dan terima kasih atas pengertian serta kerjasama yang baik dari Bapak/Ibu. Apabila masih ada " +
                "yang ingin Bapak/Ibu tanyakan lebih lanjut, silakan menghubungi Customer Care Officer kami dan dengan senang hati " +
                "kami akan membantu.\n\n\n" +
                "Hormat Kami,";
        
        txt.writeParagraph(paragraph2, document, xAddr, bottomY, rightX, topY, canvas, arial, 9f, 1.2f);
        
        canvas.endText();
        
        document.close();


//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private boolean hasText(String s) {
        return s != null && !s.trim().isEmpty();
    }
    
    public static List<Chunk> parseBoldText(
        String text,
        Font normalFont,
        Font boldFont
    ) {
        List<Chunk> chunks = new ArrayList<>();

        int idx = 0;
        while (idx < text.length()) {

            int startBold = text.indexOf("[b]", idx);
            
            if (startBold == -1) {
                chunks.add(new Chunk(text.substring(idx), normalFont));
                break;
            }
            
            if (startBold > idx) {
                chunks.add(new Chunk(text.substring(idx, startBold), normalFont));
            }

            int endBold = text.indexOf("[/b]", startBold);
            if (endBold == -1) {
                chunks.add(new Chunk(text.substring(startBold), normalFont));
                break;
            }
            
            String boldText = text.substring(startBold + 3, endBold);
            chunks.add(new Chunk(boldText, boldFont));

            idx = endBold + 4;
        }

        return chunks;
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
            Set.of("U2R", "U4R", "U5R", "UDR", "UFR", "UGR", "UUR", "UYR");
    
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
