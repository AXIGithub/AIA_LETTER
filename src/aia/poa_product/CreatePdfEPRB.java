/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.poa_product;

import aia.controller.BasePdfGenerator;
import aia.controller.TextModification;
import aia.model.PolisModel;
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
public class CreatePdfEPRB implements BasePdfGenerator{
    
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
    public void generate(PolisModel polisModel, String product, String[] params) throws Exception {
        sortingDir = params[2];
        getCurrentDir();
        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document,
                new FileOutputStream(sortingDir + product + "_" + polisModel.getChdrnum() + ".pdf"));
        
        dataReaderPreprinted = new PdfReader(paperDir + "PAPER AIA.pdf");

        document.open();
        PdfContentByte canvas = writer.getDirectContent();
        PdfPTable table = new PdfPTable(3);
        
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


        // ========== PERIHAL ===========================
        canvas.setFontAndSize(arialBold, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT,
                "Perihal : Perubahan Polis Asuransi", xAddr, 561, 0);
        // ===================== ISI SURAT ==========================

        int topY = 560;   // posisi atas kolom
        int bottomY = 50; // posisi bawah kolom   
        int rightX = 548; // Posisi kanan kolom
        int leftX = 62; // Posisi kanan kolom
        
        String paragraph = "Bapak/Ibu [b][OWNER][/b] yang terhormat,\n\n" +
                "Terima kasih atas kepercayaan yang Anda berikan kepada PT AIA FINANCIAL (AIA) sebagai penyedia kebutuhan" +
                "perlindungan asuransi bagi Bapak/Ibu dan keluarga.\n\n" +
                "Bersama ini kami sampaikan bahwa pengajuan Pemulihan Polis Bapak/Ibu telah kami setujui sehingga status Polis telah \n" +
                "aktif kembali dan masa perlindungan asuransi dinyatakan berlaku kembali dengan data di bawah ini :\n\n";
                    
        
        txt.writeParagraph(paragraph, document, xAddr, bottomY, rightX, topY, canvas, arial, 9.5f, 1.2f);
        
        int xInfo2 = (int) (xAddr + 20);
        int xDot2 = 152;
        int yInfo2 = 420;
        int xDataInfo2 = 165;
        
        canvas.setFontAndSize(arial, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "No Polis", xInfo2, yInfo2, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot2, yInfo2, 0);
        canvas.setFontAndSize(arialBold, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[CHDRNUM]" , xDataInfo2, yInfo2, 0);
        yInfo2 -= 12.5;
        
        canvas.setFontAndSize(arial, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Produk", xInfo2, yInfo2, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot2, yInfo2, 0);
        canvas.setFontAndSize(arialBold, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[PROD_NAME]" , xDataInfo2, yInfo2, 0);
        yInfo2 -= 12.5;
        
        canvas.setFontAndSize(arial, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Status Polis", xInfo2, yInfo2, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot2, yInfo2, 0);
        canvas.setFontAndSize(arialBold, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[statusPolis]" , xDataInfo2, yInfo2, 0);
        yInfo2 -= 12.5;
        
        canvas.setFontAndSize(arial, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Premi/Kontribusi", xInfo2, yInfo2, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot2, yInfo2, 0);
        canvas.setFontAndSize(arialBold, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[SINSTAMT]" , xDataInfo2, yInfo2, 0);
        yInfo2 -= 12.5;
        
        canvas.setFontAndSize(arial, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tanggal Jatuh Tempo", xInfo2, yInfo2, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot2, yInfo2, 0);
        canvas.setFontAndSize(arialBold, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[jatuhTempo]" , xDataInfo2, yInfo2, 0);
        
        canvas.setFontAndSize(arial, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Perubahan Polis untuk", xAddr, yAddr, yAddr);
        
        String paragraph2 = "Pemulihan ini berlaku terhitung sejak tanggal [b][TRDTP][/b]. Sebagai informasi, masa tunggu dan juga kondisi " +
                "lainnya seperti pengecualian penyakit tertentu akan dihitung kembali sejak tanggal pemulihan terakhir.\n\n" +
                "Jika Bapak/Ibu memiliki asuransi tambahan jenis Hospital & Surgical, bersama surat ini kami sampaikan bahwa Biaya " +
                "Asuransi Tambahan seperti yang tertulis dalam Polis dapat naik karena inflasi biaya medis (medical inflation) dan/atau " +
                "riwayat klaim secara keseluruhan. Kami akan memberitahukan besaran kenaikan Biaya Asuransi Tambahan tersebut " +
                "paling lambat 30 hari kerja sebelum diberlakukan. Kenaikan biaya ini akan memotong Nilai Unit yang lebih besar " +
                "sehingga Nilai Akun dapat berkurang lebih cepat. Untuk memastikan Bapak/Ibu Polis tetap aktif, kami anjurkan untuk " +
                "membayar Premi/Kontribusi secara terus-menerus.\n\n" +
                "Surat ini merupakan satu kesatuan dan bagian yang tidak terpisahkan dari Polis, sepanjang belum diubah atau dilakukan \n" +
                "pencabutan. Mohon agar disatukan dengan Polis Anda.\n\n" +
                "Untuk penjelasan lebih lanjut, silakan menghubungi Customer Care kami mulai hari Senin – Jumat pada pukul 08.00 – \n" +
                "17.00 WIB, dengan senang hati kami akan membantu Anda.\n\n\n" +
                "Jakarta, [b]09 Desember 2025[/b]\n" +
                "Hormat kami,\n\n";
                
        
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
