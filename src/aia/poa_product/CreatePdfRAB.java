/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.poa_product;

import aia.controller.BasePdfGenerator;
import aia.controller.TextModification;
import aia.model.BaseModel;
import aia.model.productMapping.RabModel;
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
public class CreatePdfRAB implements BasePdfGenerator{
    
    TextModification txt = new TextModification();
    private PdfReader dataReaderPreprinted = null;
    private PdfWriter writerNew = null;
    private PdfImportedPage pageData = null;
    
    private BaseFont arial;
    private BaseFont arialItalic;
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
        
        if(!(first instanceof RabModel)){
            throw new IllegalArgumentException("Not Rab Model");
        }
        
        RabModel model = (RabModel) first;
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
        canvas.setFontAndSize(arial, 9.5f);

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
        canvas.setFontAndSize(arialBold, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getChdrnum(), xDataInfo, yInfo, 0);
        yInfo -= 12.5f;

        // Nama Pemegang Polis
        canvas.setFontAndSize(arial, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Pemegang Polis", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getOwner(), xDataInfo, yInfo, 0);
        yInfo -= 12.5f;

        // Nama Tertanggung
        canvas.setFontAndSize(arial, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Tertanggung", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getLifasrname(), xDataInfo, yInfo, 0);
        yInfo -= 12.5f;
        
        // Nama Produk
        canvas.setFontAndSize(arial, 9.5f);
        String prod = model.getProdName().trim();
        String[] words = prod.split("\\s+");
        int firstLineWordCount;

        if (words.length == 3) {
            firstLineWordCount = 1;
        } else {
            firstLineWordCount = 2;
        }
        
        String prod1 = "";
        String prod2 = "";

        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            if (i < firstLineWordCount) {
                sb1.append(words[i]).append(" ");
            } else {
                sb2.append(words[i]).append(" ");
            }
        }

        prod1 = sb1.toString().trim();
        prod2 = sb2.toString().trim();
        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Produk", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, prod1, xDataInfo, yInfo, 0);
        yInfo -= 12.5;
        if (!prod2.isEmpty()) {
            canvas.showTextAligned(Element.ALIGN_LEFT, prod2, xDataInfo, yInfo, 0);
            yInfo -= 12.5;
        }
        
        // Tanggal Mulai Asuransi
        canvas.setFontAndSize(arial, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tanggal Mulai Asuransi", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, txt.convertDateMM(model.getOccdate()), xDataInfo, yInfo, 0);
        yInfo -= 12.5f;


        // ========== PERIHAL ===========================
        canvas.setFontAndSize(arialBold, 9f);
        canvas.showTextAligned(Element.ALIGN_LEFT,
                "Perihal : Konfirmasi Pembayaran Manfaat Tahunan Dijamin & Manfaat Tahunan Tidak Dijamin (jika ada)", xAddr, 561, 0);
        canvas.endText();
        
        // ===================== ISI SURAT ==========================

        int topY = 550;
        int bottomY = 50;   
        int rightX = 545;
        
        
        String paragraph = "Bapak/Ibu [b]" + model.getOwner() + "[/b] yang terhormat,\n" + "\n" +
                "Terima kasih atas kepercayaan Bapak/Ibu yang telah memilih PT AIA FINANCIAL (\"AIA\") sebagai penyedia kebutuhan " +
                "perlindungan asuransi bagi Bapak/Ibu dan keluarga.\n\n" +
                "Bersama ini kami informasikan bahwa Bapak/Ibu berhak mendapatkan Manfaat Asuransi berupa [b]Manfaat Tahunan " +
                "Dijamin[/b] dan [b]Manfaat Tahunan Tidak Dijamin (jika ada)[/b] yang dibayarkan setiap tahun setelah periode Masa " +
                "Pembayaran Premi berakhir, mulai akhir Tahun Polis berikutnya sampai dengan akhir Masa Asuransi.\n\n" + 
                "Manfaat Tahunan Dijamin dan Manfaat Tahunan Tidak Dijamin (jika ada) akan [u][b]ditransfer otomatis setiap tahun ke " +
                "rekening Bapak/Ibu dengan melengkapi dan mengirimkan FORMULIR PERSETUJUAN PEMBAYARAN MANFAAT " +
                "TAHUNAN DIJAMIN & MANFAAT TAHUNAN TIDAK DIJAMIN[/b][/u] terlampir agar dapat segera kami proses. [b]Apabila " +
                "Bapak/Ibu tidak mengirimkan Formulir tersebut, maka Manfaat Tahunan Dijamin dan Manfaat Tahunan Tidak " +
                "Dijamin (jika ada) akan diakumulasikan[/b]. Untuk pengajuan permintaan pembayaran manfaat yang diakumulasikan " +
                "tersebut sewaktu-waktu, Bapak/Ibu dapat melengkapi dokumen yang diperlukan sebagaimana tercantum dalam " +
                "ketentuan Polis.\n\nDemikian disampaikan, besar harapan kami dapat kembali melayani dan memberikan perlindungan asuransi kepada " +
                "Bapak/Ibu di masa yang akan datang.\n\nAtas perhatian dan kepercayaan yang telah Bapak/Ibu berikan selama ini, kami ucapkan terima kasih.\n\n" +
                "Jakarta, [b]" + txt.convertDateMM(cetak) + "[/b]\n\nHormat kami,";
        
            txt.writeParagraph(paragraph, document, xAddr, bottomY, rightX, topY, canvas, arial, 9.2f, 1.3f);
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
            Logger.getLogger(CreatePdfRAB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static final Set<String> PRIORITY_TYPES =
            Set.of("PIR");
    
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
