/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.poa_product;

import aia.controller.BasePdfGenerator;
import aia.controller.TextModification;
import aia.model.PolisModel;
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
public class CreatePdfLIUD implements BasePdfGenerator{
    
    TextModification txt = new TextModification();
    private PdfReader dataReaderPreprinted = null;
    private PdfWriter writerNew = null;
    private PdfImportedPage pageData = null;
    
    private BaseFont arial;
    private BaseFont arialItalic;
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
        canvas.showTextAligned(Element.ALIGN_LEFT, "Bapak/Ibu [OWNER]", xAddr, yAddr, 0);
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
        canvas.showTextAligned(Element.ALIGN_LEFT, "No. Polis", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[noPolis]", xDataInfo, yInfo, 0);
        yInfo -= 12.5f;

        // Nama Produk
        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Produk", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[namaProduk]", xDataInfo, yInfo, 0);
        yInfo -= 12.5f;

        // Nama Tertanggung/Peserta
        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Tertanggung/Peserta", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[namaPeserta]", xDataInfo, yInfo, 0);
        yInfo -= 12.5f;


        // Tanggal Mulai Asuransi 
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tanggal Mulai Asuransi", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[tanggalMulai]", xDataInfo, yInfo, 0);
        yInfo -= 12.5f;

        // Jumlah Premi / Kontribusi
        canvas.showTextAligned(Element.ALIGN_LEFT, "Jumlah Premi/Kontribusi", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Rp" + "[jumlahPremi]", xDataInfo, yInfo, 0);
        yInfo -= 12.5f;

        // Periode Bayar
        String periode = polisModel.getBillfreq().equals("12") ? "Bulanan" : "Tahunan";
        canvas.showTextAligned(Element.ALIGN_LEFT, "Periode Bayar", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[periodeBayar]", xDataInfo, yInfo, 0);
        yInfo -= 12.5f;

        // Tanggal Mulai Asuransi
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tanggal Mulai Asuransi", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[tanggalMulai]", xDataInfo, yInfo, 0);
        yInfo -= 12.5f;

        // Tanggal Cetak
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tanggal Cetak", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[tanggalCetak]" , xDataInfo, yInfo, 0);
        yInfo -= 12.5f;
        canvas.setFontAndSize(arialBold, 9.5f);


        // ========== PERIHAL ===========================
        canvas.showTextAligned(Element.ALIGN_LEFT,
                "Perihal : Pemberitahuan Status Polis", xAddr, 561, 0);
        canvas.setFontAndSize(arialBold, 9.5f);
        canvas.showTextAligned(Element.ALIGN_RIGHT,
                "Jakarta, " + "[tanggalCetak]" , 544, 561, 0);
        canvas.setFontAndSize(arial, 9.5f);
        canvas.endText();
        
        // ===================== ISI SURAT ==========================

        int topY = 550;   // posisi atas kolom
        int bottomY = 50; // posisi bawah kolom   
        int rightX = 545; // Posisi kanan kolom

        
        String paragraph = "Dengan Hormat,\n" + "\n" +
                "Terima kasih atas kepercayaan Anda telah memilih PT AIA FINANCIAL (AIA) sebagai penyedia kebutuhan " +
                "asuransi bagi Anda dan keluarga.\n\n" +
                "Bersama surat ini kami informasikan bahwa Nilai Akun Polis Anda per tanggal 08 Desember 2025 tidak mencukupi " +
                "untuk pembayaran biaya-biaya (administrasi, pemeliharaan asuransi dan biaya asuransi tambahan), oleh " +
                "karenanya [b]status Polis anda menjadi tidak aktif saat ini[/b].\n\n" +
                "Mengingat pentingnya manfaat dari perlindungan asuransi, kami menyarankan Anda secepatnya mengajukan " +
                "pemulihan Polis ini dengan cara :\n";
        
        txt.writeParagraph(paragraph, document, xAddr, bottomY, rightX, topY, canvas, arial, 9.5f, 1.2f);
        
        String[] numbering = {
            "Melakukan penambahan dana atau premi Top-Up dengan cara menyetorkan Premi/Kontribusi ke rekening BCA " +
            "0080840023150144 atau Lainnya 40023150144 atas nama EDY SUDARMAJI dan melampirkan bukti " +
            "pembayarannya. Sebagai informasi tambahan, berdasarkan catatan kami sampai dengan surat ini diterbitkan, " +
            "terdapat titipan Premi/Kontribusi sebesar Rp0,00 pada Polis Anda.",
            "Melengkapi formulir penambahan dana Top-Up yang dapat diisi melalui www.aia-financial.co.id.",
            "Melengkapi formulir pemulihan yang dapat diisi melalui www.aia-financial.co.id.",
            "Melampirkan fotokopi identitas diri yang masih berlaku."
        };
        
        float leftXNum = 62;
        float rightXNum = 548;
        float topYNum = 425;
        float bottomYNum = 100;

        for (int i = 0; i < numbering.length; i++) {

            ColumnText ct = new ColumnText(canvas);
            ct.setSimpleColumn(leftXNum, bottomYNum, rightXNum, topYNum);

            Paragraph p = new Paragraph();
            p.setFont(new Font(arial, 9.5f));

            p.setIndentationLeft(20);  
            p.setFirstLineIndent(-10); 
            p.setLeading(13.0f);

            p.add((i + 1) + ". " + numbering[i]);

            ct.addElement(p);

            ct.go();
            
            topYNum = ct.getYLine();  
        }



        String paragraph2 = "Demikian kami sampaikan. Terima kasih atas perhatian Anda. Untuk mengetahui informasi besarnya dana Top-Up " +
                "yang perlu ditambahkan atau informasi lainnya, silakan hubungi AIA Customer Care kami mulai hari Senin - Jumat " +
                "pada pukul 08.00 - 17.00 WIB, dengan senang hati kami akan membantu.\n\n\n" +
                "Hormat kami,\n\n";
        
        txt.writeParagraph(paragraph2, document, xAddr, bottomY, rightX, 320, canvas, arial, 9.5f, 1.2f);
        
        String paragraph3 = "Catatan : Perlu diketahui bahwa Penambahan Premi Top-Up tidak menjamin Polis akan tetap aktif sampai dengan Tanggal Jatuh Tempo berikutnya, mengingat kinerja " +
                "investasi sesuai dengan pasar. Sesuai dengan Ketentuan Umum Polis apabila Nilai Akun tidak cukup untuk membayar biaya-biaya yang ada maka Polis akan kembali " +
                "menjadi tidak aktif.";
        
        txt.writeParagraph(paragraph3, document, xAddr, bottomY, rightX, 186, canvas, arial, 6.2f, 1.6f);
            
        document.close();

//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void getCurrentDir(){
        try {
            currDir = ""+new java.io.File(".").getCanonicalPath();
            paperDir = currDir + "\\\\" + "PAPER\\\\";
            dirFonts = currDir + "\\\\" + "FONTS\\\\";
        } catch (IOException ex) {
            Logger.getLogger(CreatePdfLIUD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
