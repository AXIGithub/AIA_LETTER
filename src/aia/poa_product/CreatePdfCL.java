/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.poa_product;

import aia.controller.BasePdfGenerator;
import aia.controller.TextModification;
import aia.model.BaseModel;
import aia.model.productMapping.ClModel;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
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
public class CreatePdfCL implements BasePdfGenerator{
    
    TextModification txt = new TextModification();
    private PdfReader dataReaderPreprinted = null;
    private PdfWriter writerNew = null;
    private PdfImportedPage pageData = null;
    
    private BaseFont arial;
    private BaseFont arialItalic;
    private BaseFont arialUnderline;
    private BaseFont arialBold;
    private float yAddr = 712;
    private float xAddr = 72;
    private float yInfo = 717;
    private float xInfo = 296;
    private float xDot = 430;
    private float xDataInfo = 437;
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
        
        if(!(first instanceof ClModel)){
            throw new IllegalArgumentException("Not Cl Model");
        }
        
        ClModel model = (ClModel) first;
        yAddr = 712;
        xAddr = 72;
        yInfo = 717;
        xInfo = 496;
        xDot = 430;
        xDataInfo = 437;
        sortingDir = params[2];
        getCurrentDir();
        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document,
                new FileOutputStream(sortingDir + product + model.getChdrnum() + "_" + cetak + ".pdf"));
        
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
        canvas.setFontAndSize(arial, 9.5f);

        // ================= HEADER =======================
        canvas.showTextAligned(Element.ALIGN_LEFT, "Kepada Yth.", xAddr, yAddr, 0);
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
        canvas.showTextAligned(Element.ALIGN_RIGHT,
                "Jakarta, " + txt.convertDateMM(cetak) , xInfo, yInfo, 0);
        
        // ========== PERIHAL ===========================
        canvas.setFontAndSize(arialBold, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT,
                "Hal : Pengajuan Klaim Polis no. " + model.getChdrnum() +" no Klaim " + model.getClno(), xAddr, 561, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, "atas nama " + model.getOwner(), xAddr + 24, 551, 0);
        canvas.endText();
        
        // ===================== ISI SURAT ==========================

        int topY = 540;
        int bottomY = 50;   
        int rightX = 545;
        
        
        String paragraph = "Dengan Hormat,\n" + "\n" +
                "Terima kasih atas kepercayaan Anda telah memilih PT AIA Financial sebagai penyedia kebutuhan Asuransi " +
                "bagi Anda dan keluarga.\n\n" +
                "Sehubungan dengan pengajuan klaim atas nama " + model.getOwner() + ", dapat kami sampaikan bahwa klaim tersebut telah " +
                "selesai kami proses, dan Polis No. " + model.getChdrnum() + " akan menjadi Polis bebas Premi terhitung sejak jatuh tempo " + txt.convertDateMM(model.getFirstPayd()) +
                " sampai dengan jatuh tempo " + txt.convertDateMM(model.getFinalPayd()) + ".\n\n" +
                "Demikian kami sampaikan. Untuk informasi lebih lanjut, silahkan hubungi AIA Customer Care Line di nomor " +
                "(021) 1500980, e-mail: id.customer@aia.com. Dengan senang hati kami akan membantu.\n\n\n\n\n\n" +
                "Hormat kami,";
        
            txt.writeParagraph(paragraph, document, xAddr, bottomY, rightX, topY, canvas, arial, 9.5f, 1.2f);
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
            Logger.getLogger(CreatePdfCL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static final Set<String> PRIORITY_TYPES =
            Set.of("kosong sementara");
    
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
