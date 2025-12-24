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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ratino
 */
public class CreatePdfAPL implements BasePdfGenerator{
    
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
    private float xDot = 430;
    private float xDataInfo = 437;
    
    private String currDir = new String();
    private String paperDir = new String();
    private String dirFonts = new String();
    private String sortingDir = new String();
    private String outputDir = new String();
    
    

    public void generate(PolisModel polisModel, String product, String[] params) throws Exception {
        sortingDir = params[2];
        getCurrentDir();
        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document,
                new FileOutputStream(sortingDir + product + "_" + polisModel.getChdrnum() + ".pdf"));
        
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
        canvas.showTextAligned(Element.ALIGN_LEFT, "Bapak/Ibu [owner]", xAddr, yAddr, 0);
        yAddr = (float) (yAddr - 10);
        canvas.setFontAndSize(arial, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[alamat1]", xAddr, yAddr, 0);
        yAddr = (float) (yAddr - 10);
        canvas.showTextAligned(Element.ALIGN_LEFT,"[alamat2]" + " " + "[alamat3]", xAddr, yAddr, 0);
        yAddr = (float) (yAddr - 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[alamat4]" + " " + "[alamat5]", xAddr, yAddr, 0);
        yAddr = (float) (yAddr - 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[kodePos]", xAddr, yAddr, 0);

        // ================= INFO POLIS =====================
        // No Polis
        canvas.setFontAndSize(arial, 8.75f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "No. Polis", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[noPolis]", xDataInfo, yInfo, 0);
        yInfo -= 10.5;

        // Nama Produk
        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Produk", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[namaProduk]", xDataInfo, yInfo, 0);
        yInfo -= 10.5;

        // Nama Tertanggung
        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Tertanggung", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[namaTertanggung]", xDataInfo, yInfo, 0);
        yInfo -= 10.5;

        // Tanggal Mulai Asuransi
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tanggal Mulai Asuransi", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[tanggalMulai]", xDataInfo, yInfo, 0);
        yInfo -= 10.5;

        // Mata Uang
        canvas.showTextAligned(Element.ALIGN_LEFT, "Mata Uang", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[mataUang]", xDataInfo, yInfo, 0);
        yInfo -= 10.5;

        // Premi
        canvas.showTextAligned(Element.ALIGN_LEFT, "Premi", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Rp" + "[premiKontribusi]", xDataInfo, yInfo, 0);
        yInfo -= 10.5;

        // Periode pembayaran
        String periode = polisModel.getBillfreq().equals("12") ? "Bulanan" : polisModel.getBillfreq();
        canvas.showTextAligned(Element.ALIGN_LEFT, "Periode Pembayaran", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[periode]", xDataInfo, yInfo, 0);
        yInfo -= 10.5;

        // Tanggal Cetak
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tanggal Cetak", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[tanggalCetak]" , xDataInfo, yInfo, 0);
        yInfo -= 10.5;


        // ========== PERIHAL ===========================
        canvas.setFontAndSize(arialBold, 8.75f);
        canvas.showTextAligned(Element.ALIGN_LEFT,
                "Perihal : Penggunaan Fasilitas Pinjaman Polis Otomatis", xAddr, 561, 0);
        canvas.showTextAligned(Element.ALIGN_RIGHT,
                "Jakarta, " + "[tanggalCetak]" , 544, 561, 0);
        canvas.setFontAndSize(arial, 8.75f);
        
        // ===================== ISI SURAT ==========================

        int topY = 550;   // posisi atas kolom
        int bottomY = 50; // posisi bawah kolom   
        int rightX = 545; // Posisi kanan kolom
        
        String paragraph = "Dengan Hormat,\n" + "\n" +
                "Terima kasih atas kepercayaan Anda telah memilih PT AIA FINANCIAL (AIA) sebagai penyedia kebutuhan asuransi bagi Anda dan keluarga.\n" + "\n" +
                
                "Kami memahami kesibukan Anda sehingga sampai surat ini diterbitkan, kami belum menerima pembayaran Premi/Kontribusi Polis Anda untuk jatuh tempo tanggal [b]"
                        + "[jatuhTempo]"  + "[/b] (Tanggal Jatuh Tempo) yang telah melewati Masa Leluasa.\n" + "\n" +
                
                "Kondisi tersebut di atas menyebabkan pembayaran Premi Polis Anda dilanjutkan dengan menggunakan fasilitas " +
                "Pinjaman Premi Otomatis dan pembayaran Premi dilakukan secara bulanan dari Nilai Tunai Polis Anda. Fasilitas Cuti Premi/Kontribusi Otomatis " +
                "akan berlaku selama Nilai Akun Polis Anda masih mencukupi.  Pinjaman Premi Otomatis ini akan " +
                "dikenakan bunga majemuk yang besarnya dapat berubah sewaktu-waktu sesuai dengan kebijakan AIA [i](informasi tingkat " +
                "suku bunga Pinjaman Premi Otomatis dapat dilihat melalui website aia-financial.co.id)[/i].\n" + "\n" +
                
                "Anda dapat menghentikan fasilitas Pinjaman Premi Otomatis ini dengan melunasi tunggakan Premi beserta bunga " +
                "dengan cara menyetorkan Premi ke rekening  " + "[rekAIA]" + " atau [banKey] [FLDENT] atas nama " + "[owner]" + ". " + 
                "Sebagai informasi tambahan, berdasarkan catatan kami sampai dengan surat ini diterbitkan, terdapat titipan Premi/Kontribusi sebesar Rp" + "[premiKontribusi]" +
                " pada Polis Anda.\n" + 
                
                "Apabila Anda telah melakukan pembayaran Premi sebelum Masa Leluasa berakhir silahkan menghubungi AIA Customer " +
                "Care kami agar pembayaran Premi Anda dapat dibukukan.\n\n" +
                
                "Demikian kami sampaikan. Terima kasih atas perhatian Anda. Untuk informasi lebih lanjut, silakan hubungi AIA kami mulai hari Senin - Jumat pada pukul 08.00 - 17.00 WIB, dengan senang hati kami akan membantu Anda." +
                "\n\n\n\n\n\n" +
                
                "Hormat kami,";

        
        txt.writeParagraph(paragraph, document, xAddr, bottomY, rightX, topY, canvas, arial, 8.75f, 1.2f);

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

    @Override
    public void generate(BaseModel baseModel, String Product, String[] params) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
