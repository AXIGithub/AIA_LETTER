/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.poa_product;

import aia.controller.BasePdfGenerator;
import aia.controller.TextModification;
import aia.model.BaseModel;
import aia.model.productMapping.SpnpModel;
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
public class CreatePdfSPNP implements BasePdfGenerator{
    
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
        
        if(!(first instanceof SpnpModel)){
            throw new IllegalArgumentException("Not Spnp Model");
        }
        
        SpnpModel model = (SpnpModel) first;
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
        canvas.setFontAndSize(arialBold, 8.5f);
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
        
        canvas.setFontAndSize(arial, 8.8f);
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
        yInfo -= 12.5f;

        // Nama Produk
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Produk", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getProdName(), xDataInfo, yInfo, 0);
        yInfo -= 12.5f;

        // Nama Tertanggung
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Tertanggung/Peserta", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getInsured(), xDataInfo, yInfo, 0);
        yInfo -= 12.5f;
        
        // Jumlah Premi/Kontribusi
        canvas.setFontAndSize(arial, 8.8f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Jumlah Premi/Kontribusi", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Rp" + txt.setCurrencyIdr(model.getSinstamt()), xDataInfo, yInfo, 0);
        yInfo -= 12.5f;

        // Periode Bayar
        canvas.setFontAndSize(arial, 8.5f);
        String periode;
        switch (model.getBillFreq()) {
            case "12":
                periode = "Bulanan";
                break;
            case "04":
                periode = "Triwulan";
                break;
            case "02":
                periode = "Semesteran";
                break;
            case "01":
                periode = "Tahunan";
                break;
            default:
                periode = "Sekaligus";
                break;
        }
        canvas.showTextAligned(Element.ALIGN_LEFT, "Periode Bayar", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, periode, xDataInfo, yInfo, 0);
        yInfo -= 12.5f;
        
        // Tanggal Mulai Asuransi
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tanggal Mulai Asuransi", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, txt.convertDateMM(model.getOccdate()), xDataInfo, yInfo, 0);
        yInfo -= 12.5f;
        
        // Status Polis
        canvas.setFontAndSize(arial, 8.5f);
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
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, status, xDataInfo, yInfo, 0);
        yInfo -= 12.5f;
        
        // Tanggal Cetak
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tanggal Cetak", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, txt.convertDateMM(cetak), xDataInfo, yInfo, 0);
        yInfo -= 12.5f;

        // ========== PERIHAL ===========================
        canvas.setFontAndSize(arialBold, 8.8f);
        canvas.showTextAligned(Element.ALIGN_LEFT,
                "Perihal : Pengambilan Titipan Premi/Kontribusi", xAddr, 561, 0);
        
        // ===================== ISI SURAT ==========================

        int topY = 550;
        int bottomY = 50;   
        int rightX = 548;
        
        String paragraph = "Dengan hormat,\n\n" +
                "Terima kasih atas kepercayaan Anda telah memilih PT AIA FINANCIAL sebagai penyedia kebutuhan asuransi bagi " +
                "Anda dan keluarga.\n\n" +
                "Bersama ini kami sampaikan bahwa pengajuan pengambilan Titipan premi/Kontribusi Polis Anda telah selesai " +
                "diproses dan telah ditransfer pada tanggal " + txt.convertDateMM(model.getPaydte()) + " sebesar Rp." + txt.setCurrencyIdr(model.getAmounts()) + " (" + model.getTerbilang() + "). Keterangan transfer pembayaran " +
                "sebagai berikut:";
        
        txt.writeParagraph(paragraph, document, xAddr, bottomY, rightX, topY, canvas, arial, 8.8f, 1.2f);
        topY -= 110;
        
        int xInfo2 = (int) (xAddr + 10);
        int xDot2 = 202;
        int xDataInfo2 = 212;
        
        canvas.setFontAndSize(arial, 8.8f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Atas Nama", xInfo2, topY, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot2, topY, 0);
        canvas.setFontAndSize(arialBold, 8.8f);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getPaidTo() , xDataInfo2, topY, 0);
        topY -= 12.5;
        
        canvas.setFontAndSize(arial, 8.8f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "No Rekening", xInfo2, topY, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot2, topY, 0);
        canvas.setFontAndSize(arialBold, 8.8f);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getBankToacc(), xDataInfo2, topY, 0);
        topY -= 12.5;
        
        canvas.setFontAndSize(arial, 8.8f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Bank", xInfo2, topY, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot2, topY, 0);
        canvas.setFontAndSize(arialBold, 8.8f);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getBankToname(), xDataInfo2, topY, 0);
        topY -= 20;
        
        String paragraph2 = "Demikian kami sampaikan. Terima kasih atas perhatian Anda. Untuk informasi lebih lanjut, silakan hubungi AIA Customer" +
                "Care kami mulai hari Senin - Jumat pada pukul 08.00 - 17.00 WIB, dengan senang hati kami akan membantu Anda.\n\n\n" +
                "Hormat kami,\n\n";
        
        txt.writeParagraph(paragraph2, document, xAddr, bottomY, rightX, topY, canvas, arial, 8.8f, 1.2f);
        
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
            Set.of("U4R", "U5R", "U7R", "UFR", "UXR", "UYR");
    
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
