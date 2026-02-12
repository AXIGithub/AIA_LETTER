/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.poa_product;

import aia.controller.BasePdfGenerator;
import aia.controller.TextModification;
import aia.model.BaseModel;
import aia.model.productMapping.EpmlModel;
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
public class CreatePdfEPML implements BasePdfGenerator{
    
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
        
        if(!(first instanceof EpmlModel)){
            throw new IllegalArgumentException("Not Epml Model");
        }
        
        EpmlModel model = (EpmlModel) first;
        yAddr = 712;
        xAddr = 72;
        yInfo = 717;
        xInfo = 296;
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


        // ========== PERIHAL ===========================
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT,
                "Perihal : Pemulihan Polis Asuransi", xAddr, 561, 0);
        canvas.endText();
        
        // ===================== ISI SURAT ==========================

        int topY = 540;
        int bottomY = 50;   
        int rightX = 548;
        
        String paragraph = "Bapak/Ibu [b]" + model.getOwner() + "[/b] yang terhormat,\n\n" +
                "Terima kasih atas kepercayaan yang Anda berikan kepada PT AIA FINANCIAL (AIA) sebagai penyedia kebutuhan" +
                "perlindungan asuransi bagi Bapak/Ibu dan keluarga.\n\n" +
                "Bersama ini kami sampaikan bahwa pengajuan Pemulihan Polis Bapak/Ibu telah kami setujui sehingga status Polis telah " +
                "aktif kembali dan masa perlindungan asuransi dinyatakan berlaku kembali dengan data di bawah ini :";
                    
        
        txt.writeParagraph(paragraph, document, xAddr, bottomY, rightX, topY, canvas, arial, 8.5f, 1.2f);
        
        int xInfo2 = (int) (xAddr + 16);
        int xDot2 = 247;
        int yInfo2 = 451;
        int xDataInfo2 = 255;
        
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "No Polis", xInfo2, yInfo2, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot2, yInfo2, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getChdrnum() , xDataInfo2, yInfo2, 0);
        yInfo2 -= 12.5;
        
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Produk", xInfo2, yInfo2, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot2, yInfo2, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getProdName() , xDataInfo2, yInfo2, 0);
        yInfo2 -= 12.5;
        
        canvas.setFontAndSize(arial, 8.5f);
        String status = model.getStatcode().equalsIgnoreCase("IF") ? "Aktif" : "Tidak Aktif";
        canvas.showTextAligned(Element.ALIGN_LEFT, "Status Polis", xInfo2, yInfo2, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot2, yInfo2, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, status , xDataInfo2, yInfo2, 0);
        yInfo2 -= 12.5;
        
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Premi/Kontribusi", xInfo2, yInfo2, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot2, yInfo2, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Rp" + txt.setCurrencyIdr(model.getSinstamt()) , xDataInfo2, yInfo2, 0);
        yInfo2 -= 12.5;
        
        canvas.setFontAndSize(arial, 8.5f);
        String payment = model.getBillFreq().equals("12") ? "Bulanan" : "Tahunan";
        canvas.showTextAligned(Element.ALIGN_LEFT, "Cara Bayar", xInfo2, yInfo2, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot2, yInfo2, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, payment , xDataInfo2, yInfo2, 0);
        
        String paragraph2 = "Pemulihan ini berlaku terhitung sejak tanggal [b]"+ txt.convertDateMM(model.getTrdtp()) +"[/b]. Sebagai informasi, masa tunggu dan juga kondisi " +
                "lainnya seperti pengecualian penyakit tertentu akan dihitung kembali sejak tanggal pemulihan terakhir.\n\n" +
                "Jika Bapak/Ibu memiliki asuransi tambahan jenis Hospital & Surgical, bersama surat ini kami sampaikan bahwa Biaya " +
                "Asuransi Tambahan seperti yang tertulis dalam Polis dapat naik karena inflasi biaya medis [i](medical inflation)[/i] dan/atau " +
                "riwayat klaim secara keseluruhan. Kami akan memberitahukan besaran kenaikan Biaya Asuransi Tambahan tersebut " +
                "paling lambat 30 hari kerja sebelum diberlakukan. Kenaikan biaya ini akan memotong Nilai Unit yang lebih besar " +
                "sehingga Nilai Akun dapat berkurang lebih cepat. Untuk memastikan Bapak/Ibu Polis tetap aktif, kami anjurkan untuk " +
                "membayar Premi/Kontribusi secara terus-menerus.\n\n" +
                "Surat ini merupakan satu kesatuan dan bagian yang tidak terpisahkan dari Polis, sepanjang belum diubah atau dilakukan \n" +
                "pencabutan. Mohon agar disatukan dengan Polis Anda.\n\n" +
                "Untuk penjelasan lebih lanjut, silakan menghubungi Customer Care kami mulai hari Senin – Jumat pada pukul 08.00 – \n" +
                "17.00 WIB, dengan senang hati kami akan membantu Anda.\n\n\n" +
                "Jakarta, [b]" + txt.convertDateMM(cetak) + "[/b]\n" +
                "Hormat kami,\n\n";
                
        
        txt.writeParagraph(paragraph2, document, xAddr, bottomY, rightX, 390, canvas, arial, 8.5f, 1.2f);
        
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
            Set.of("UFR", "PIR");
    
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
