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
    private float yAddr = 712;
    private float xAddr = 72;
    private float yInfo = 717;
    private float xInfo = 296;
    private float xDot = 430;
    private float xDataInfo = 437;
    
    private String currDir = new String();
    private String paperDir = new String();
    
    

    public void generate(PolisModel polisModel, String product, String[] params) throws Exception {
        getCurrentDir();
        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document,
                new FileOutputStream(product + "_" + polisModel.getChdrnum() + ".pdf"));
        
        dataReaderPreprinted = new PdfReader(paperDir + "PAPER AIA.pdf");

        document.open();
        PdfContentByte canvas = writer.getDirectContent();
        
        canvas.beginText();
        canvas.endText();
        pageData = writer.getImportedPage(dataReaderPreprinted, 1);
        canvas.addTemplate(pageData, 0, 0);

        BaseFont helvatica = BaseFont.createFont(BaseFont. HELVETICA, BaseFont.CP1250, BaseFont.NOT_EMBEDDED);
        BaseFont helvaticaBold = BaseFont.createFont(BaseFont. HELVETICA_BOLD, BaseFont.CP1250, BaseFont.NOT_EMBEDDED);
        canvas.beginText();
        canvas.setFontAndSize(helvatica, 10);

        // ================= HEADER =======================
        canvas.showTextAligned(Element.ALIGN_LEFT, "Kepada yang terhormat :", xAddr, yAddr, 0);
        yAddr = (float) (yAddr - 10);
        canvas.setFontAndSize(helvaticaBold, 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Bapak/Ibu " + polisModel.getOwner(), xAddr, yAddr, 0);
        yAddr = (float) (yAddr - 10);
        canvas.setFontAndSize(helvatica, 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, polisModel.getAlamat1(), xAddr, yAddr, 0);
        yAddr = (float) (yAddr - 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, polisModel.getAlamat2()+ " " + polisModel.getAlamat3(), xAddr, yAddr, 0);
        yAddr = (float) (yAddr - 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, polisModel.getAlamat4()+ " " + polisModel.getAlamat5(), xAddr, yAddr, 0);

        // ================= INFO POLIS =====================
        // No Polis
        canvas.showTextAligned(Element.ALIGN_LEFT, "No. Polis", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(helvaticaBold, 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, polisModel.getChdrnum(), xDataInfo, yInfo, 0);
        yInfo -= 10;

        // Nama Produk
        canvas.setFontAndSize(helvatica, 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Produk", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(helvaticaBold, 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, polisModel.getProd_name(), xDataInfo, yInfo, 0);
        yInfo -= 10;

        // Nama Tertanggung
        canvas.setFontAndSize(helvatica, 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Tertanggung", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(helvaticaBold, 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, polisModel.getOwner(), xDataInfo, yInfo, 0);
        yInfo -= 10;

        // Tanggal Mulai Asuransi
        canvas.setFontAndSize(helvatica, 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tanggal Mulai Asuransi", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(helvaticaBold, 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, polisModel.getPtdate(), xDataInfo, yInfo, 0);
        yInfo -= 10;

        // Premi / Kontribusi
        canvas.setFontAndSize(helvatica, 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Premi/Kontribusi", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(helvaticaBold, 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Rp " + polisModel.getSinstamt(), xDataInfo, yInfo, 0);
        yInfo -= 10;

        // Periode Bayar
        canvas.setFontAndSize(helvatica, 10);
        String periode = polisModel.getBillfreq().equals("12") ? "Bulanan" : polisModel.getBillfreq();
        canvas.showTextAligned(Element.ALIGN_LEFT, "Periode Bayar", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(helvaticaBold, 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, periode, xDataInfo, yInfo, 0);
        yInfo -= 10;

        // Status Polis
        canvas.setFontAndSize(helvatica, 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Status Polis", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(helvaticaBold, 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Aktif", xDataInfo, yInfo, 0);
        yInfo -= 10;

        // Tanggal Cetak
        canvas.setFontAndSize(helvatica, 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tanggal Cetak", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(helvaticaBold, 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, txt.convertDateMM(polisModel.getChdrdue()) , xDataInfo, yInfo, 0);
        yInfo -= 10;


        // ========== PERIHAL ===========================
        canvas.showTextAligned(Element.ALIGN_LEFT,
                "Perihal : Status Polis Cuti Premi/Kontribusi Otomatis", xAddr, 561, 0);
        canvas.setFontAndSize(helvaticaBold, 10);
        canvas.showTextAligned(Element.ALIGN_RIGHT,
                "Jakarta, " + txt.convertDateMM(polisModel.getChdrdue()) , 544, 561, 0);
        canvas.setFontAndSize(helvatica, 10);
        
        // ===================== ISI SURAT ==========================

        int topY = 550;   // posisi atas kolom
        int bottomY = 50; // posisi bawah kolom   
        int rightX = 545; // Posisi kanan kolom
        
        String paragraph = "Dengan Hormat,\n" + "\n" +
                "Terima kasih atas kepercayaan Anda telah memilih PT AIA FINANCIAL (AIA) sebagai penyedia kebutuhan asuransi bagi Anda dan keluarga.\n" + ".\n" +
                
                "Kami memahami kesibukan Anda sehingga sampai surat ini diterbitkan, kami belum menerima pembayaran Premi/Kontribusi Polis Anda untuk jatuh tempo tanggal "
                        + txt.convertDateMM(polisModel.getChdrdue())  + " (Tanggal Jatuh Tempo) yang telah melewati Masa Leluasa.\n" + "\n" +
                
                "Kondisi tersebut di atas menyebabkan status Polis Anda menjadi Cuti Premi/Kontribusi Otomatis dan akan " +
                "dikenakan biaya (jika ada) sebagaimana diatur dalam Ketentuan Polis. Fasilitas Cuti Premi/Kontribusi Otomatis " +
                "akan berlaku selama Nilai Akun Polis Anda masih mencukupi. Anda dapat menghentikan Fasilitas Cuti " +
                "Premi/Kontribusi ini dengan melunasi tunggakan Premi/Kontribusi yang dapat disetorkan ke rekening " + polisModel.getRek_aia() +
                " atas nama " + polisModel.getVa_owner() + ". " + 
                "Sebagai informasi tambahan, berdasarkan catatan kami sampai dengan surat ini diterbitkan, terdapat titipan Premi/Kontribusi sebesar Rp" + polisModel.getSacscurbal() +
                " pada Polis Anda.\n" + "\n" + 
                
                "Apabila Anda telah melakukan pembayaran Premi sebelum Masa Leluasa berakhir silakan menghubungi Customer Care kami agar pembayaran Premi/Kontribusi Anda dapat dibukukan.\n" +
                
                "Demikian kami sampaikan. Terima kasih atas perhatian Anda. Untuk informasi lebih lanjut, silakan hubungi AIA kami mulai hari Senin - Jumat pada pukul 08.00 - 17.00 WIB, dengan senang hati kami akan membantu Anda." +
                "\n";

        
        txt.writeParagraph(paragraph, document, xAddr, bottomY, 545, topY, canvas, helvatica, 10, 1.2f);

        canvas.endText();
        document.close();


//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void getCurrentDir(){
        try {
            currDir = ""+new java.io.File(".").getCanonicalPath();
            paperDir = currDir + "\\\\" + "PAPER\\\\";
        } catch (IOException ex) {
            Logger.getLogger(CreatePdfAPL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void generate(BaseModel baseModel, String Product, String[] params) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
