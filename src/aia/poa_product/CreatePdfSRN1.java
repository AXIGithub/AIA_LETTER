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
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.ListItem;
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
public class CreatePdfSRN1 implements BasePdfGenerator{
    
    TextModification txt = new TextModification();
    private PdfReader dataReaderPreprinted = null;
    private PdfWriter writerNew = null;
    private PdfImportedPage pageData = null;
    
    private BaseFont arial;
    private BaseFont arialItalic;
    private BaseFont arialBold;    
    private BaseFont barcodeFont;
    private float yAddr = 312;
    private float xAddr = 48;
    private float yInfo = 317;
    private float xInfo = 270;
    private float xDot = 381;
    private float xDataInfo = 386;
    
    private String currDir = new String();
    private String paperDir = new String();
    private String dirFonts = new String();
    private String sortingDir = new String();
    private String outputDir = new String();
    
    

    @Override
    public void generate(PolisModel polisModel, String product, String[] params) throws Exception {
        sortingDir = params[2];
        getCurrentDir();
        Document document = new Document(PageSize.A5.rotate());
        PdfWriter writer = PdfWriter.getInstance(document,
                new FileOutputStream(sortingDir + product + "_" + polisModel.getChdrnum() + ".pdf"));
        
        dataReaderPreprinted = new PdfReader(paperDir + "PAPER SRN.pdf");

        document.open();
        PdfContentByte canvas = writer.getDirectContent();
        PdfPTable table = new PdfPTable(5);
        
        canvas.beginText();
        canvas.endText();
        pageData = writer.getImportedPage(dataReaderPreprinted, 1);
        canvas.addTemplate(pageData, 0, 0);

        BaseFont helvatica = BaseFont.createFont(BaseFont. HELVETICA, BaseFont.CP1250, BaseFont.NOT_EMBEDDED);
        BaseFont helvaticaBold = BaseFont.createFont(BaseFont. HELVETICA_BOLD, BaseFont.CP1250, BaseFont.NOT_EMBEDDED);
        
        arial = BaseFont.createFont(dirFonts + "Arial.ttf", BaseFont.IDENTITY_H, true);
        arialItalic = BaseFont.createFont(dirFonts + "Arial_Italic.ttf", BaseFont.IDENTITY_H, true);
        arialBold = BaseFont.createFont(dirFonts + "arial_bold.ttf", BaseFont.IDENTITY_H, true);
        
        canvas.beginText();
        canvas.setFontAndSize(arial, 6.5f);

        // ================= HEADER =======================
        canvas.showTextAligned(Element.ALIGN_LEFT, "Kepada yang terhormat :", xAddr, yAddr, 0);
        yAddr = (float) (yAddr - 10);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Bapak/Ibu [owner]", xAddr, yAddr, 0);
        yAddr = (float) (yAddr - 10);
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[alamat1]", xAddr, yAddr, 0);
        yAddr = (float) (yAddr - 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[alamat2]" + " " + "[alamat3]", xAddr, yAddr, 0);
        yAddr = (float) (yAddr - 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[alamat4]" + " " + "[alamat5]", xAddr, yAddr, 0);
        yAddr = (float) (yAddr - 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[kodePos]", xAddr, yAddr, 0);

        // ================= INFO POLIS =====================
        // No Polis
        canvas.setFontAndSize(arial, 8f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "No. Polis", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[CHDRNUM]", xDataInfo, yInfo, 0);
        yInfo -= 11;

        // Nama Produk
        canvas.setFontAndSize(arial, 8f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Produk", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[PROD_NAME]", xDataInfo, yInfo, 0);
        yInfo -= 11;

        // Nama Tertanggung/Peserta
        canvas.setFontAndSize(arial, 8f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Tertanggung/Peserta", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[NAME]", xDataInfo, yInfo, 0);
        yInfo -= 11;

        // Mata Uang
        canvas.setFontAndSize(arial, 8f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Mata Uang", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[mataUang]", xDataInfo, yInfo, 0);
        yInfo -= 11;

        // Uang Pertanggungan Dasar
        canvas.setFontAndSize(arial, 8f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Uang Pertanggungan Dasar", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[SUMINS]", xDataInfo, yInfo, 0);
        yInfo -= 11;

        // Tanggal Mulai Asuransi
        canvas.setFontAndSize(arial, 8f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tanggal Mulai Asuransi", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[OCCDATE]", xDataInfo, yInfo, 0);
        yInfo -= 11;
        
        // Status Polis
        canvas.setFontAndSize(arial, 8f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Status Polis", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[statusPolis]", xDataInfo, yInfo, 0);
        yInfo -= 11;
        
        // Tanggal Cetak
        canvas.setFontAndSize(arial, 8f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tanggal Cetak", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[tanggalCetak]" , xDataInfo, yInfo, 0);


        // ========== PERIHAL ===========================
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT,
                "Perihal : Pemberitahuan Jatuh Tempo", xAddr, 201, 0);
        canvas.setFontAndSize(arialBold, 8f);
        canvas.showTextAligned(Element.ALIGN_RIGHT,
                "Jakarta, " + "[tanggalCetak]" , 544, 201, 0);
        canvas.endText();
        
        // ===================== ISI SURAT ==========================
        
        table.setTotalWidth(new float[] {75, 90, 150, 110, 90});
        table.setLockedWidth(true);
        
        //Header
        PdfPCell header1 = new PdfPCell(new Phrase("Periode Bayar", new Font(arialBold, 7.5f)));
        header1.setBackgroundColor(new BaseColor(150, 150, 150));
        header1.setPadding(4);
        header1.setHorizontalAlignment(Element.ALIGN_CENTER);
        header1.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        PdfPCell header2 = new PdfPCell(new Phrase("Tgl. Jatuh Tempo", new Font(arialBold, 7.5f)));
        header2.setBackgroundColor(new BaseColor(150, 150, 150));
        header2.setPadding(4);
        header2.setHorizontalAlignment(Element.ALIGN_CENTER);
        header2.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        PdfPCell header3 = new PdfPCell(new Phrase("Premi/kontribusi", new Font(arialBold, 7.5f)));
        header3.setBackgroundColor(new BaseColor(150, 150, 150));
        header3.setPadding(4);
        header3.setHorizontalAlignment(Element.ALIGN_CENTER);
        header3.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        PdfPCell header4 = new PdfPCell(new Phrase("Titipan Premi/Kontribusi", new Font(arialBold, 7.5f)));
        header4.setBackgroundColor(new BaseColor(150, 150, 150));
        header4.setPadding(4);
        header4.setHorizontalAlignment(Element.ALIGN_CENTER);
        header4.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        PdfPCell header5 = new PdfPCell(new Phrase("Total Premi/Kontribusi", new Font(arialBold, 7.5f)));
        header5.setBackgroundColor(new BaseColor(150, 150, 150));
        header5.setPadding(4);
        header5.setHorizontalAlignment(Element.ALIGN_CENTER);
        header5.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        table.addCell(header1);
        table.addCell(header2);
        table.addCell(header3);
        table.addCell(header4);
        table.addCell(header5);
        
        List<String[]> listData = new ArrayList<>();
        listData.add(new String[]{"[periodeBayar]", "[INSTFROM]", "[SINSTAMT]", "[ZVPFEE]", "[TOTALPREMI]"});
        
        Font fontArial = new Font(arial, 7.5f);
        
        for (String[] row : listData) {
            PdfPCell c1 = new PdfPCell(new Phrase(row[0], fontArial));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
            c1.setPadding(6);
            table.addCell(c1);
                    
            PdfPCell c2 = new PdfPCell(new Phrase(row[1], fontArial));
            c2.setHorizontalAlignment(Element.ALIGN_CENTER);
            c2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            c2.setPadding(6);
            table.addCell(c2);

            PdfPCell c3 = new PdfPCell(new Phrase(row[2], fontArial));
            c3.setHorizontalAlignment(Element.ALIGN_CENTER);
            c3.setVerticalAlignment(Element.ALIGN_MIDDLE);
            c3.setPadding(6);
            table.addCell(c3);
            
            PdfPCell c4 = new PdfPCell(new Phrase(row[3], fontArial));
            c4.setHorizontalAlignment(Element.ALIGN_CENTER);
            c4.setVerticalAlignment(Element.ALIGN_MIDDLE);
            c4.setPadding(6);
            table.addCell(c4);
            
            PdfPCell c5 = new PdfPCell(new Phrase(row[4], fontArial));
            c5.setHorizontalAlignment(Element.ALIGN_CENTER);
            c5.setVerticalAlignment(Element.ALIGN_MIDDLE);
            c5.setPadding(6);
            table.addCell(c5);

        }
        table.writeSelectedRows(0, -1, xAddr, 197, canvas);
        
        canvas.beginText();
        canvas.setFontAndSize(arial, 7.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Pembayaran Premi/Kontribusi melalui:", xAddr, 150, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, "- Transfer ke rekening atas nama [OWNER] :", 70, 140, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, "• No Rekening VA [YBANKKEY] atau [YBANKKEY]", 73, 130, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", 263, 130, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[FLDENT] atau [FLDENT]", 273, 130, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, "• Rekening", 73, 120, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", 263, 120, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Rupiah", 273, 120, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Keterangan :", 73, 110, 0);
        canvas.endText();
        
        float leftX  = 86;
        float rightX = 568;
        float topY   = 110;
        float bottomY = 0;
        
        Font checkFont = new Font(Font.FontFamily.ZAPFDINGBATS, 7.5f);
        Font textFont  = new Font(arial, 7.5f);

        float symbolWidth = 10f;

        Paragraph p = new Paragraph();
        p.setFont(textFont);
        p.setLeading(8f);
        
        p.setIndentationLeft(symbolWidth);
        p.setFirstLineIndent(-symbolWidth);
        
        p.add(new Chunk("4  ", checkFont));
        
        p.add(
            "Nomor rekening yang tertera di atas hanya berlaku untuk pembayaran Premi/Kontribusi no. Polis [CHDRNUM] dan nomor tersebut adalah " +
            "nomor personal Identifikasi Anda serta tidak boleh diinformasikan kepada orang lain."
        );

        ColumnText ct = new ColumnText(canvas);
        ct.setSimpleColumn(leftX, bottomY, rightX, topY);
        ct.addElement(p);
        ct.go();
        
        String end = "[b]Perhatian!![/b] PT AIA FINANCIAL tidak pernah bekerjasama atau menunjuk pihak manapun untuk melakukan penagihan, menarik, menerima atau " +
                "mengumpulkan uang untuk pembayaran Premi/Kontribusi. Setiap pembayaran Premi/Kontribusi TIDAK diperkenankan secara tunai, harus dilakukan " +
                "secara transfer ke rekening atas nama PT AIA FINANCIAL dengan detail rekening sebagaimana disebut di atas.";
        
        txt.writeParagraph(end, document, xAddr, bottomY, rightX, 90, canvas, arialItalic, 7.5f, 1.2f);

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
