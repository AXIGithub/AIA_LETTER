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
public class CreatePdfESAM implements BasePdfGenerator{
    
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
                "Hal: Pemberitahuan Perpanjangan Polis Asuransi Kesehatan Premier Medical Protection (Medic Pro) " +
                "dengan Polis No. 37483449 atas nama BUDI JUDA UTAMA", xAddr, 561, 0);
        
        
        // ===================== ISI SURAT ==========================

        int topY = 560;   // posisi atas kolom
        int bottomY = 50; // posisi bawah kolom   
        int rightX = 548; // Posisi kanan kolom
        int leftX = 62; // Posisi kanan kolom
        
        String paragraph = "Bapak/Ibu Y. [OWNER] yang terhormat,\n\n" +
                "Terima kasih atas kepercayaan Bapak/Ibu yang telah memilih AIA sebagai penyedia kebutuhan perlindungan asuransi " +
                "bagi Bapak/Ibu dan keluarga.\n\nBersama ini kami informasikan bahwa Masa Asuransi Polis Anda berakhir pada tanggal 25 November 2025. Dengan \n" +
                "berakhirnya Masa Asuransi Polis tersebut, maka tidak ada perlindungan asuransi apapun yang menjadi kewajiban AIA.\n\n" +
                "Kami berharap dapat melayani kembali dan memberikan jaminan finansial kepada Anda di masa yang akan datang. " +
                "Hubungi Tenaga Pemasar kami yang selalu siap membantu dengan berbagai program asuransi sesuai dengan " +
                "kebutuhan Bapak/Ibu.\n\nUntuk informasi lebih lanjut mengenai Manfaat Akhir Polis ([b]jika ada/belum ajukan klaim ke AIA[/b]), mohon Bapak/Ibu " +
                "menghubungi Customer Care Line kami pada hari kerja, mulai hari Senin – Jumat pada pukul 08.00 – 17.00 WIB melalui " +
                "nomor telepon dan alamat email yang tertera pada bagian bawah surat. Dengan senang hati kami akan membantu " +
                "Bapak/Ibu.\n\nAtas perhatian dan kepercayaan yang telah Bapak/Ibu berikan selama ini, kami ucapkan terima kasih.\n\n" +
                "Jakarta, 05 Desember 2025\nHormat kami,\n";
        
        txt.writeParagraph(paragraph, document, xAddr, bottomY, rightX, topY, canvas, arial, 9.5f, 1.2f);
        
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
