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
public class CreatePdfSSNA implements BasePdfGenerator{
    
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
    private float xDot = 451;
    private float xDataInfo = 456;
    
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
        
        dataReaderPreprinted = new PdfReader(paperDir + "PAPER ACD.pdf");

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
        canvas.showTextAligned(Element.ALIGN_LEFT, "[alamat2]" + " " + "[alamat3]", xAddr, yAddr, 0);
        yAddr = (float) (yAddr - 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[alamat4]" + " " + "[alamat5]", xAddr, yAddr, 0);
        yAddr = (float) (yAddr - 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[kodePos]", xAddr, yAddr, 0);

        // ================= INFO POLIS =====================
        // No Polis
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "No. Polis", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[noPolis]", xDataInfo, yInfo, 0);
        yInfo -= 12.5;

        // Nama Tertanggung
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tertanggung/Pihak Yang Diasuransikan", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[namaTertanggung]", xDataInfo, yInfo, 0);
        yInfo -= 12.5;

        // Jumlah Premi/Kontribusi Dasar
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Jumlah Premi/Kontribusi Dasar", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[premiKontribusiDasar]", xDataInfo, yInfo, 0);
        yInfo -= 12.5;

        // Jumlah Premi/Kontribusi Top-Up Berkala
        canvas.showTextAligned(Element.ALIGN_LEFT, "Jumlah Premi/", xInfo, yInfo, 0);
        yInfo -= 12.5;
        canvas.showTextAligned(Element.ALIGN_LEFT, "Kontribusi Top-Up Berkala", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[premiKontribusiBerkala]", xDataInfo, yInfo, 0);
        yInfo -= 12.5;

        // Status Polis
        canvas.showTextAligned(Element.ALIGN_LEFT, "Status Polis", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Aktif", xDataInfo, yInfo, 0);
        yInfo -= 12.5;

        // Cara Bayar
        String periode = polisModel.getBillfreq().equals("12") ? "Bulanan" : polisModel.getBillfreq();
        canvas.showTextAligned(Element.ALIGN_LEFT, "Cara Bayar", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[periode]", xDataInfo, yInfo, 0);
        yInfo -= 12.5;

        // Tanggal Mulai Asuransi
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tanggal Mulai Asuransi", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[tanggalMulai]", xDataInfo, yInfo, 0);
        yInfo -= 12.5;

        // Nama Produk
        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Produk", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[namaProduk]" , xDataInfo, yInfo, 0);
        yInfo -= 12.5;
        
        // Uang Pertanggungan/Santunan Asuransi Dasar
        canvas.showTextAligned(Element.ALIGN_LEFT, "Uang Pertanggungan/", xInfo, yInfo, 0);
        yInfo -= 12.5;
        canvas.showTextAligned(Element.ALIGN_LEFT, "Santunan Asuransi Dasar", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[uangPertanggungan]" , xDataInfo, yInfo, 0);
        yInfo -= 12.5;
        
        // Mata Uang
        canvas.showTextAligned(Element.ALIGN_LEFT, "Mata Uang", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[mataUang]" , xDataInfo, yInfo, 0);
        yInfo -= 12.5;
        
        // Tanggal cetak
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tanggal cetak", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[tanggalCetak]" , xDataInfo, yInfo, 0);
        yInfo -= 12.5;


        // ========== PERIHAL ===========================
//        canvas.showTextAligned(Element.ALIGN_LEFT,
//                "Perihal : Status Polis Cuti Premi/Kontribusi Otomatis", xAddr, 561, 0);
//        canvas.setFontAndSize(helvaticaBold, 10);
//        canvas.showTextAligned(Element.ALIGN_RIGHT,
//                "Jakarta, " + txt.convertDateMM(polisModel.getChdrdue()) , 544, 561, 0);
//        canvas.setFontAndSize(helvatica, 10);
        
        // ===================== ISI SURAT ==========================

        int topY = 520;   // posisi atas kolom
        int bottomY = 50; // posisi bawah kolom   
        int rightX = 548; // Posisi kanan kolom
        int leftX = 62; // Posisi kanan kolom
        
        String paragraph = "Selamat atas pencapaian Anda! Anda tercatat telah berhasil mencapai status [b][ZVPCLSTA][/b] pada Status Keanggotaan Vitality " +
               "Program Anda. Kami sungguh menghargai setiap langkah dan pilihan sehat yang Anda ambil untuk hidup lebih sehat, lebih " +
               "lama, lebih baik. Berikut adalah rangkuman transaksi pembayaran Diskon Biaya Akuisisi:";
        
        String bar = "RANGKUMAN TRANSAKSI ([CURRFROM] - [CURRTO])";
        
        txt.writeParagraph(paragraph, document, leftX, bottomY, rightX, topY, canvas, arial, 8.5f, 1.2f);
        canvas.endText();
        
        float barHeight = 17f;
        float barY = topY - 55;
        float barX = 62f;
        float barWidth = 495f;
        
        // kotak abu
        canvas.saveState();
        canvas.setColorFill(new BaseColor(200,200,200));
        canvas.rectangle(barX, barY, barWidth, barHeight);
        canvas.fill();
        canvas.restoreState();
        
        canvas.beginText();
        txt.writeParagraph(bar, document, 174, bottomY, rightX, 482, canvas, arialBold, 10, 1.2f);
        canvas.endText();
        
        table.setTotalWidth(new float[] {90, 290, 118});
        table.setLockedWidth(true);
        
        //Header
        PdfPCell header1 = new PdfPCell(new Phrase("Periode", new Font(arialBold, 8.5f)));
        header1.setBackgroundColor(new BaseColor(230, 230, 230));
        header1.setPadding(5);
        header1.setHorizontalAlignment(Element.ALIGN_CENTER);
        header1.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        PdfPCell header2 = new PdfPCell(new Phrase("Diskon Biaya Akuisisi", new Font(arialBold, 8.5f)));
        header2.setBackgroundColor(new BaseColor(230, 230, 230));
        header2.setPadding(5);
        header2.setHorizontalAlignment(Element.ALIGN_CENTER);
        header2.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        PdfPCell header3 = new PdfPCell(new Phrase("Jumlah\n(Rupiah)", new Font(arialBold, 8.5f)));
        header3.setBackgroundColor(new BaseColor(230, 230, 230));
        header3.setPadding(9);
        header3.setHorizontalAlignment(Element.ALIGN_CENTER);
        header3.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        table.addCell(header1);
        table.addCell(header2);
        table.addCell(header3);
        
        List<String[]> listData = new ArrayList<>();
        listData.add(new String[]{"29 November 2023", "Diskon Biaya Akuisisi yang dikreditkan", "1.734.000,00"});
        
        Font fontArial = new Font(arial, 8.5f);
        Font fontArialB = new Font(arialBold, 8.5f);
        
        for (String[] row : listData) {
            PdfPCell c1 = new PdfPCell(new Phrase(row[0], fontArial));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
            c1.setPadding(4);
            table.addCell(c1);
                    
            PdfPCell c2 = new PdfPCell(new Phrase(row[1], fontArial));
            c2.setHorizontalAlignment(Element.ALIGN_LEFT);
            c2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            c2.setPadding(4);
            table.addCell(c2);

            PdfPCell c3 = new PdfPCell(new Phrase(row[2], fontArial));
            c3.setHorizontalAlignment(Element.ALIGN_RIGHT);
            c3.setVerticalAlignment(Element.ALIGN_MIDDLE);
            c3.setPadding(4);
            table.addCell(c3);

        }
        
        PdfPCell t1 = new PdfPCell(new Phrase("", fontArial));
        t1.setHorizontalAlignment(Element.ALIGN_CENTER);
        t1.setVerticalAlignment(Element.ALIGN_MIDDLE);
        t1.setPadding(5);
        table.addCell(t1);
        
        PdfPCell t2 = new PdfPCell(new Phrase("Total", fontArialB));
        t2.setHorizontalAlignment(Element.ALIGN_RIGHT);
        t2.setVerticalAlignment(Element.ALIGN_MIDDLE);
        t2.setPadding(5);
        table.addCell(t2);
        
        PdfPCell t3 = new PdfPCell(new Phrase("[TOTAL]", fontArialB));
        t3.setHorizontalAlignment(Element.ALIGN_RIGHT);
        t3.setVerticalAlignment(Element.ALIGN_MIDDLE);
        t3.setPadding(5);
        table.addCell(t3);
        
        
        table.writeSelectedRows(0, -1, 62, 455, canvas);
        
        canvas.beginText();
        String afterTable = "Keterangan:\n" +
                "Informasi mengenai syarat dan ketentuan Diskon Biaya Akuisisi selengkapnya dapat dilihat pada Polis Anda.\n\n\n" +
                "Tetap aktif dan teruskan untuk melakukan pilihan sehat untuk hidup lebih sehat, lebih lama, lebih baik.";
        
        txt.writeParagraph(afterTable, document, leftX, bottomY, rightX, 385, canvas, arial, 8.5f, 1.6f);
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
