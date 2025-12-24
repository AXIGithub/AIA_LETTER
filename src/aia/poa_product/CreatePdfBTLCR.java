/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.poa_product;

import aia.controller.BasePdfGenerator;
import aia.controller.TextModification;
import aia.model.BaseModel;
import aia.model.PolisModel;
import aia.model.productMapping.AphModel;
import aia.model.productMapping.BTLCRModel;
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ratino
 */
public class CreatePdfBTLCR implements BasePdfGenerator{
    
    TextModification txt = new TextModification();
    private PdfReader dataReaderPreprinted = null;
    private PdfWriter writerNew = null;
    private PdfImportedPage pageData = null;
    
    private BaseFont arial;
    private BaseFont arialUnderline;
    private BaseFont arialBold;    
    private BaseFont barcodeFont;
    private float yAddr = 712;
    private float xAddr = 72;
    private float yInfo = 717;
    private float xInfo = 296;
    private float xDot = 408;
    private float xDataInfo = 416;
    
    private String currDir = new String();
    private String paperDir = new String();
    private String dirFonts = new String();
    private String sortingDir = new String();
    private String outputDir = new String();
    
    

    @Override
    public void generate(BaseModel baseModel, String product, String[] params) throws Exception {
        if(!(baseModel instanceof BTLCRModel)){
            throw new IllegalArgumentException("Not Aph Model");
        }
        
        BTLCRModel model =  (BTLCRModel) baseModel;
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
        canvas.showTextAligned(Element.ALIGN_LEFT, "Bapak/Ibu [OWNER]", xAddr, yAddr, 0);
        yAddr = (float) (yAddr - 10);
        canvas.setFontAndSize(arial, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[alamat1]", xAddr, yAddr, 0);
        yAddr = (float) (yAddr - 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[alamat2]" + " " + "[alamat3]", xAddr, yAddr, 0);
        yAddr = (float) (yAddr - 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[alamat4]" + " " + "[alamat5]", xAddr, yAddr, 0);
        yAddr = (float) (yAddr - 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[kodePos]", xAddr, yAddr, 0);

        // ================= INFO POLIS =====================
        // No Polis
        canvas.showTextAligned(Element.ALIGN_LEFT, "No. Polis", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[CHDRNUM]", xDataInfo, yInfo, 0);
        yInfo -= 12.5;

        // Nama Produk
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Produk", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[PROD_NAME]", xDataInfo, yInfo, 0);
        yInfo -= 12.5;

        // Nama Tertanggung/Peserta 
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Tertanggung/Peserta", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[INSURED]", xDataInfo, yInfo, 0);
        yInfo -= 12.5;

        // Mata Uang
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Mata Uang", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[mataUang]", xDataInfo, yInfo, 0);
        yInfo -= 12.5;

        // Jumlah Premi/Kontribusi
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Jumlah Premi/Kontribusi", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[SINSTAMT]", xDataInfo, yInfo, 0);
        yInfo -= 12.5;

        // Periode Bayar
        canvas.setFontAndSize(arial, 8.5f);
        String periode = model.getBillfreq().equals("12") ? "Bulanan" : model.getBillfreq();
        canvas.showTextAligned(Element.ALIGN_LEFT, "Periode Bayar", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[BILLFREQ]", xDataInfo, yInfo, 0);
        yInfo -= 12.5;

        // Tanggal Mulai Asuransi
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tanggal Mulai Asuransi", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[OCCDATE]", xDataInfo, yInfo, 0);
        yInfo -= 12.5;

        // Status Polis
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Status Polis", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[statusPolis]" , xDataInfo, yInfo, 0);
        yInfo -= 12.5;
        
        // Tanggal cetak
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tanggal cetak", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[PTRNEFF04]" , xDataInfo, yInfo, 0);
        yInfo -= 12.5;


        // ========== PERIHAL ===========================
        canvas.setFontAndSize(arialBold, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT,
                "Perihal : Pembatalan Polis", xAddr, 561, 0);
        canvas.showTextAligned(Element.ALIGN_RIGHT,
                "Jakarta, " + "[PTRNEFF04]" , 544, 561, 0);
        
        // ===================== ISI SURAT ==========================

        int topY = 560;   // posisi atas kolom
        int bottomY = 50; // posisi bawah kolom   
        int rightX = 548; // Posisi kanan kolom
        int leftX = 62; // Posisi kanan kolom
        
        String paragraph = "Dengan Hormat,\n\n" +
                    "Terima kasih atas kepercayaan Anda telah memilih PT AIA FINANCIAL (AIA) sebagai penyedia kebutuhan asuransi " +
                    "bagi Anda dan keluarga.\n\n" + 
                    "Bersama ini kami sampaikan bahwa proses Pengajuan Pembatalan Polis Anda telah selesai dilakukan pada " +
                    "tanggal [PTRNEFF04] dengan Nilai Pembatalan Polis sebesar [AMOUNT] ([TERBILANG])\n\n\n" +
                    "Keterangan transfer pembayaran sebagai berikut: \n";
        
        txt.writeParagraph(paragraph, document, xAddr, bottomY, rightX, topY, canvas, arial, 9.5f, 1.2f);
        
        int xInfo2 = (int) (xAddr + 20);
        int xDot2 = 152;
        int yInfo2 = 420;
        int xDataInfo2 = 165;
        
        canvas.setFontAndSize(arial, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Atas Nama", xInfo2, yInfo2, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot2, yInfo2, 0);
        canvas.setFontAndSize(arialBold, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[OWNER]" , xDataInfo2, yInfo2, 0);
        yInfo2 -= 12.5;
        
        canvas.setFontAndSize(arial, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "No. Rekening", xInfo2, yInfo2, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot2, yInfo2, 0);
        canvas.setFontAndSize(arialBold, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[BANKTOACC]" , xDataInfo2, yInfo2, 0);
        yInfo2 -= 12.5;
        
        canvas.setFontAndSize(arial, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Bank", xInfo2, yInfo2, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot2, yInfo2, 0);
        canvas.setFontAndSize(arialBold, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[BANKTONAME]" , xDataInfo2, yInfo2, 0);
        
        
        String paragraph2 = "Dengan dibatalkannya Polis tersebut, maka pertanggungan Polis ini tidak lagi menjadi tanggung jawab AIA.\n\n" +
                "Demikian kami sampaikan. Terima kasih atas perhatian Anda. Untuk informasi lebih lanjut, silakan hubungi AIA " +
                "Customer Care kami mulai hari Senin - Jumat pada pukul 08.00 - 17.00 WIB, dengan senang hati kami akan " +
                "membantu Anda\n\n\n" +
                "Hormat Kami,\n\n\n\n";
        
        txt.writeParagraph(paragraph2, document, xAddr, bottomY, rightX, 380, canvas, arial, 9.5f, 1.2f);
        
        canvas.endText();
        
        document.close();


//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    
}
