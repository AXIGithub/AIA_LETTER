/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.poa_product;

import aia.controller.BasePdfGenerator;
import aia.controller.TextModification;
import aia.model.BaseModel;
import aia.model.productMapping.LanfModel;
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
public class CreatePdfLANF implements BasePdfGenerator{
    
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
    final int maxLength = 28;
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
        
        if(!(first instanceof LanfModel)){
            throw new IllegalArgumentException("Not Lanf Model");
        }
        
        LanfModel model = (LanfModel) first;
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
        canvas.showTextAligned(Element.ALIGN_LEFT, "Kepada Yth :", xAddr, yAddr, 0);
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
            
            String addr02 = model.getAddr02();
            String addr03 = model.getAddr03();

            String line23 = Stream.of(addr02, addr03)
                    .filter(this::hasText)
                    .collect(Collectors.joining(" "));

            if (hasText(line23)) {
                if (tooLong(line23, maxLength)) {
                    if (hasText(addr02)) addressLines.add(addr02);
                    if (hasText(addr03)) addressLines.add(addr03);
                } else {
                    addressLines.add(line23);
                }
            }
            
            String addr04 = model.getAddr04();
            String addr05 = model.getAddr05();

            String line45 = Stream.of(addr04, addr05)
                    .filter(this::hasText)
                    .collect(Collectors.joining(" "));

            if (hasText(line45)) {
                if (tooLong(line45, maxLength)) {
                    if (hasText(addr04)) addressLines.add(addr04);
                    if (hasText(addr05)) addressLines.add(addr05);
                } else {
                    addressLines.add(line45);
                }
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

        // Mata Uang
        canvas.setFontAndSize(arial, 8.5f);
        String currency = model.getCntcurr().equals("IRP") ? "Rupiah" : "Dollar Amerika";
        String kode = model.getCntcurr().equals("IRP") ? "Rp" : "USD";
        canvas.showTextAligned(Element.ALIGN_LEFT, "Mata Uang", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, currency, xDataInfo, yInfo, 0);
        yInfo -= 12.5f;


        // Uang Pertanggungan Dasar 
        canvas.setFontAndSize(arial, 8.5f);
//        int sumins = (int) model.getSumins();
        canvas.showTextAligned(Element.ALIGN_LEFT, "Uang Pertanggungan Dasar", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, kode + txt.reduceDecimal(txt.setCurrencyIdr(model.getSumins())), xDataInfo, yInfo, 0);
        yInfo -= 12.5f;

        // Jumlah Premi / Kontribusi
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Jumlah Premi/Kontribusi", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, kode + txt.reduceDecimal(txt.setCurrencyIdr(model.getSinstamt01())), xDataInfo, yInfo, 0);
        yInfo -= 12.5f;

        // Cara Bayar
        canvas.setFontAndSize(arial, 8.5f);
        String periode;
        switch (model.getBillFreq()) {
            case "12":
                periode = "Bulanan";
                break;
            case "06":
                periode = "Tidak diketahui";
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
        canvas.showTextAligned(Element.ALIGN_LEFT, "Cara Bayar", xInfo, yInfo, 0);
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

        // Tanggal Cetak
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tanggal Cetak", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, txt.convertDateMM(cetak) , xDataInfo, yInfo, 0);
        yInfo -= 12.5f;


        // ========== PERIHAL ===========================
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT,
                "PRIBADI DAN RAHASIA" , xAddr, 578, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT,
                "Perihal : Pemberitahuan Status Polis Tidak Aktif", xAddr, 556, 0);
        canvas.endText();
        
        // ===================== ISI SURAT ==========================

        int topY = 540;
        int bottomY = 50;   
        int rightX = 545;
        
        
        String paragraph = "Dengan Hormat,\n" + "\n" +
                "Terima kasih atas kepercayaan yang Anda berikan kepada PT AIA FINANCIAL (AIA) sebagai penyedia kebutuhan " +
                "perlindungan asuransi bagi Anda dan keluarga.\n\nMelalui surat ini kami hendak menginformasikan bahwa kami belum menerima pembayaran Premi Anda yang telah jatuh " +
                "tempo  pada  tanggal  " + txt.convertDateMM(model.getPtdate()) + "  (Tanggal Jatuh Tempo).  Polis  Anda saat  ini tidak aktif karena Masa Leluasa  ([i]" +
                "Grace Period[/i]) pembayaran Premi Anda telah berakhir.\n\n" +
                "Mengingat pentingnya manfaat dari perlindungan asuransi, kami menyarankan Anda untuk secepatnya mengajukan proses pemulihan Polis dengan cara:";
        
        txt.writeParagraph(paragraph, document, xAddr, bottomY, rightX, topY, canvas, arial, 8.5f, 1.2f);
        topY -= 110;
        
        String[] numbering = {
            "Melengkapi formulir pemulihan yang dapat diunduh melalui www.aia-financial.co.id;",
            "Melampirkan fotokopi identitas diri yang masih berlaku;",
            "Melunasi seluruh tunggakan Premi dan bunga (jika ada) sejak Tanggal Jatuh Tempo sampai dengan proses " +
            "pemulihan Polis diajukan serta melampirkan bukti pembayarannya;",
            "Mengirimkan dokumen di atas ke kantor AIA yang terdekat."
        }; 
        
        for (int i = 0; i < numbering.length; i++) {

            ColumnText ct = new ColumnText(canvas);
            ct.setSimpleColumn(xAddr, bottomY, rightX, topY);

            Paragraph p = new Paragraph();
            p.setFont(new Font(arial, 8.5f));

            p.setIndentationLeft(10);  
            p.setFirstLineIndent(-10); 
            p.setLeading(13.0f);

            p.add(new Chunk((i + 1) + ". ", new Font(arial, 8.5f)));
            
            if(numbering[i].contains("www.aia-financial.co.id")){
                String beforeLink =
                    "Melengkapi formulir pemulihan yang dapat diunduh melalui ";
                String urlText = "www.aia-financial.co.id";
                String fullUrl = "https://www.aia-financial.co.id";
                
                p.add(new Chunk(beforeLink, new Font(arial, 8.5f)));
                
                Chunk linkChunk = new Chunk(urlText, new Font(arial, 8.5f));
                linkChunk.setUnderline(0.75f, -1.5f);
                linkChunk.setAnchor(fullUrl);
                p.add(linkChunk);
                
                p.add(new Chunk(";", new Font(arial, 8.5f)));                                                                           
            } else {
                p.add(new Chunk(numbering[i], new Font(arial, 8.5f)));
            }

            ct.addElement(p);

            ct.go();
            
            topY = (int) ct.getYLine();  
        }
        topY -= 10;
        
        String paragraph2 = "Demikian kami sampaikan. Terima kasih atas perhatian Anda. Untuk informasi lebih lanjut silakan hubungi AIA Customer " +
                "Care kami mulai hari Senin - Jumat pada pukul 08.00 - 17.00 WIB melalui nomor telepon dan alamat email yang tertera " +
                "pada bagian bawah surat, dengan senang hati kami akan membantu.\n\n\n" +
                "Jakarta, " + txt.convertDateMM(cetak) + "\nHormat kami,";
        
        txt.writeParagraph(paragraph2, document, xAddr, bottomY, rightX, topY, canvas, arial, 8.5f, 1.2f);
        document.close();

//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private boolean hasText(String s) {
        return s != null && !s.trim().isEmpty();
    }
    
    private boolean tooLong(String text, int maxLen) {
        return text != null && text.length() > maxLen;
    }

    
    private void getCurrentDir(){
        try {
            currDir = ""+new java.io.File(".").getCanonicalPath();
            paperDir = currDir + "\\\\" + "PAPER\\\\";
            dirFonts = currDir + "\\\\" + "FONTS\\\\";
        } catch (IOException ex) {
            Logger.getLogger(CreatePdfLANF.class.getName()).log(Level.SEVERE, null, ex);
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
