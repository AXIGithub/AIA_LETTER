/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.poa_product;

import aia.controller.BasePdfGenerator;
import aia.controller.TextModification;
import aia.model.productMapping.AphModel;
import aia.model.BaseModel;
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
public class CreatePdfAPH implements BasePdfGenerator{
    
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
    
    
    
    @Override
    public void generate(BaseModel baseModel, String product, String[] params) throws Exception {
        if(!(baseModel instanceof AphModel)){
            throw new IllegalArgumentException("Not Aph Model");
        }
        
        AphModel model = (AphModel) baseModel;
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
                new FileOutputStream(sortingDir + product + "_" + model.getChrdnum()+ ".pdf"));
        
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
        canvas.setFontAndSize(arial, 9);

        // ================= HEADER =======================
        canvas.showTextAligned(Element.ALIGN_LEFT, "Kepada yang terhormat :", xAddr, yAddr, 0);
        yAddr = (float) (yAddr - 10);
        canvas.setFontAndSize(arialBold, 9);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Bapak/Ibu " + model.getOwner(), xAddr, yAddr, 0);
        yAddr = (float) (yAddr - 10);
        canvas.setFontAndSize(arial, 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getAlamat1(), xAddr, yAddr, 0);
        yAddr = (float) (yAddr - 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getAlamat2()+ " " + model.getAlamat3(), xAddr, yAddr, 0);
        yAddr = (float) (yAddr - 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getAlamat4()+ " " + model.getAlamat5(), xAddr, yAddr, 0);
//
//        // ================= INFO POLIS =====================
//        // No Polis
        canvas.showTextAligned(Element.ALIGN_LEFT, "No. Polis", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 9);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getChrdnum(), xDataInfo, yInfo, 0);
        yInfo -= 10;

        // Nama Produk
        canvas.setFontAndSize(arial, 9);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Produk", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(helvaticaBold, 9);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getProdName(), xDataInfo, yInfo, 0);
        yInfo -= 10;

        // Nama Tertanggung
        canvas.setFontAndSize(arial, 9);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Tertanggung", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(helvaticaBold, 9);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getOwner(), xDataInfo, yInfo, 0);
        yInfo -= 10;

        // Tanggal Mulai Asuransi
        canvas.setFontAndSize(arial, 9);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tanggal Mulai Asuransi", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 9);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getPtdate(), xDataInfo, yInfo, 0);
        yInfo -= 10;

        // Premi / Kontribusi
        canvas.setFontAndSize(arial, 9);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Premi/Kontribusi", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 9);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Rp " + model.getSinstamt(), xDataInfo, yInfo, 0);
        yInfo -= 10;

        // Periode Bayar
        canvas.setFontAndSize(arial, 9);
        String periode = model.getBillfreq().equals("12") ? "Bulanan" : model.getBillfreq();
        canvas.showTextAligned(Element.ALIGN_LEFT, "Periode Bayar", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 9);
        canvas.showTextAligned(Element.ALIGN_LEFT, periode, xDataInfo, yInfo, 0);
        yInfo -= 10;

        // Status Polis
        canvas.setFontAndSize(arial, 9);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Status Polis", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 9);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Aktif", xDataInfo, yInfo, 0);
        yInfo -= 10;

        // Tanggal Cetak
        canvas.setFontAndSize(arial, 9);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tanggal Cetak", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 9);
        canvas.showTextAligned(Element.ALIGN_LEFT, txt.convertDateMM(model.getChdrdue()) , xDataInfo, yInfo, 0);
        yInfo -= 10;


        // ========== PERIHAL ===========================
        canvas.showTextAligned(Element.ALIGN_LEFT,
                "Perihal : Status Polis Cuti Premi/Kontribusi Otomatis", xAddr, 561, 0);
        canvas.setFontAndSize(arialBold, 9);
        canvas.showTextAligned(Element.ALIGN_RIGHT,
                "Jakarta, " + txt.convertDateMM(model.getChdrdue()) , 544, 561, 0);
        canvas.setFontAndSize(arial, 9);
        
        // ===================== ISI SURAT ==========================

        int topY = 540;   // posisi atas kolom
        int bottomY = 100; // posisi bawah kolom

        ColumnText ct = new ColumnText(canvas);
        ct.setSimpleColumn(
                xAddr,         // kiri
                bottomY,       // bawah
                550,           // kanan (lebar tulisan)
                topY           // atas
        );

        Font f = new Font(Font.FontFamily.HELVETICA, 11);
        Paragraph p = new Paragraph();
        p.setFont(f);
        p.setLeading(16f);
        p.setAlignment(Element.ALIGN_JUSTIFIED);

        
        
        String paragraph = "Dengan Hormat,\n" + "\n" +
                "Terima kasih atas kepercayaan Anda telah memilih PT AIA FINANCIAL (AIA) sebagai penyedia kebutuhan asuransi bagi Anda dan keluarga.\n" + "\n" +
                
                "Kami memahami kesibukan Anda sehingga sampai surat ini diterbitkan, kami belum menerima pembayaran Premi/Kontribusi Polis Anda untuk jatuh tempo tanggal "
                        + txt.convertDateMM(model.getChdrdue())  + " (Tanggal Jatuh Tempo) yang telah melewati Masa Leluasa.\n" + "\n" +
                
                "Kondisi tersebut di atas menyebabkan status Polis Anda menjadi Cuti Premi/Kontribusi Otomatis dan akan " +
                "dikenakan biaya (jika ada) sebagaimana diatur dalam Ketentuan Polis. Fasilitas Cuti Premi/Kontribusi Otomatis " +
                "akan berlaku selama Nilai Akun Polis Anda masih mencukupi. Anda dapat menghentikan Fasilitas Cuti " +
                "Premi/Kontribusi ini dengan melunasi tunggakan Premi/Kontribusi yang dapat disetorkan ke rekening " + model.getRekAia()+
                " atas nama " + model.getVaOwner()+ ". " + 
                "Sebagai informasi tambahan, berdasarkan catatan kami sampai dengan surat ini diterbitkan, terdapat titipan Premi/Kontribusi sebesar Rp" + model.getSacscurbal() +
                " pada Polis Anda.\n" + "\n" + 
                
                "Apabila Anda telah melakukan pembayaran Premi sebelum Masa Leluasa berakhir silakan menghubungi Customer Care kami agar pembayaran Premi/Kontribusi Anda dapat dibukukan.\n" +
                
                "Demikian kami sampaikan. Terima kasih atas perhatian Anda. Untuk informasi lebih lanjut, silakan hubungi AIA kami mulai hari Senin - Jumat pada pukul 08.00 - 17.00 WIB, dengan senang hati kami akan membantu Anda." +
                "\n";

        
        txt.writeParagraph(paragraph, document, xAddr, 50, 545, 550, canvas, arial, 9, 1.2f);

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
