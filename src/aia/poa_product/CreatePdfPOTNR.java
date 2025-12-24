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
public class CreatePdfPOTNR implements BasePdfGenerator{
    
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
    private float yInfo = 695;
    private float xInfo = 296;
    private float xDot = 420;
    private float xDataInfo = 428;
    
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
        
        dataReaderPreprinted = new PdfReader(paperDir + "PAPER ANRPE.pdf");

        document.open();
        PdfContentByte canvas = writer.getDirectContent();
        PdfPTable table = new PdfPTable(5);
        PdfPTable table2 = new PdfPTable(8);
        
        canvas.beginText();
        canvas.endText();
        pageData = writer.getImportedPage(dataReaderPreprinted, 1);
        canvas.addTemplate(pageData, 0, 0);
        
        arial = BaseFont.createFont(dirFonts + "Arial.ttf", BaseFont.IDENTITY_H, true);
        arialUnderline = BaseFont.createFont(dirFonts + "ArialUnderline.ttf", BaseFont.IDENTITY_H, true);
        arialBold = BaseFont.createFont(dirFonts + "arial_bold.ttf", BaseFont.IDENTITY_H, true);
        
        canvas.beginText();
        canvas.setFontAndSize(arial, 8.5f);

        // ================= HEADER =======================
        canvas.showTextAligned(Element.ALIGN_LEFT, "Kepada Yth.", xAddr, yAddr, 0);
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
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "No. Polis", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[noPolis]", xDataInfo, yInfo, 0);
        yInfo -= 12.5;

        // Nama Produk
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Produk", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[namaProduk]", xDataInfo, yInfo, 0);
        

        // ========== PERIHAL ===========================
        canvas.setFontAndSize(arialBold, 9f);
        canvas.showTextAligned(Element.ALIGN_LEFT,
                "Perihal :  Pemberitahuan Perpanjangan Polis Asuransi Kesehatan AIA Health X", xAddr, 561, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT,
                "Jakarta, 09 Desember 2025", xAddr, 551, 0);
        
        
        // ===================== ISI SURAT ==========================

        int topY = 530;   // posisi atas kolom
        int bottomY = 50; // posisi bawah kolom   
        int rightX = 548; // Posisi kanan kolom
        int leftX = 62; // Posisi kanan kolom
        
        float y1 = topY;
        float y2 = topY + 140;
        float y3 = topY + 140;
        
        String paragraph = "Terima kasih atas kepercayaan Bapak/Ibu yang telah memilih PT AIA FINANCIAL (\"AIA\") sebagai penyedia " +
                "perlindungan Asuransi Anda dan keluarga.\n\nBersama surat pemberitahuan ini Kami lampirkan dokumen Endosemen Perpanjangan Polis Asuransi Kesehatan " +
                "AIA Health X dengan nomor 39337753 Plan Essential X-Tra.\n\nEndosemen terlampir merupakan satu kesatuan dan bagian yang tidak terpisahkan dari Polis, sepanjang belum " +
                "diubah atau dilakukan pengakhiran. Mohon agar endosemen terlampir disatukan dengan Polis Anda.\n\n" +
                "Apabila Anda membutuhkan informasi lebih lanjut silahkan menghubungi Tenaga Pemasar AIA atau menghubungi " +
                "[i]Customer Care[/i] AIA melalui:";
        
        txt.writeParagraph(paragraph, document, xAddr, bottomY, rightX, topY, canvas, arial, 9f, 1.2f);
        y1 -= 126;
        
        String[] numbering = {"Layanan WhatsApp Tanya Anya di nomor 081119601000",
                "Customer Care Line melalui Telepon 1500 980 dan (021) 3000 1 980",
                "Email id.customer@aia.com."
        };
        
        float leftXNum = 62;
        float rightXNum = 548;
        float bottomYNum = 100;

        Font normalFont = new Font(arial, 9.5f);

        for (int i = 0; i < numbering.length; i++) {

            ColumnText ct = new ColumnText(canvas);
            ct.setSimpleColumn(leftXNum, bottomYNum, rightXNum, y1);

            Paragraph p = new Paragraph();
            p.setFont(normalFont);
            p.setLeading(7.0f);
            p.setIndentationLeft(20);
            p.setFirstLineIndent(-10);
            
            Chunk bullet = new Chunk("•", normalFont);

            Chunk gap = new Chunk(" ", normalFont);
            gap.setCharacterSpacing(10f);

            Chunk text = new Chunk(numbering[i], normalFont);

            p.add(bullet);
            p.add(gap);
            p.add(text);
            
            ct.addElement(p);
            ct.go();
            
            y1 = (int)(ct.getYLine() - 4);
        }
        y1 -= 30;
        
        String paragraph2 = "Jakarta, [tanggalCetak]\n\n" +
                "Hormat kami,\n";
        
        txt.writeParagraph(paragraph2, document, xAddr, bottomY, rightX, y1, canvas, arial, 9f, 1.2f);
        canvas.endText();
        
        document.newPage();
        canvas = writer.getDirectContent();
        pageData = writer.getImportedPage(dataReaderPreprinted, 2);
        canvas.addTemplate(pageData, 0, 0);
        canvas.beginText();
        
        canvas.setFontAndSize(arialBold, 9.5f);
        canvas.showTextAligned(Element.ALIGN_CENTER,
                "ENDOSEMEN PERPANJANGAN POLIS ASURANSI KESEHATAN", 300, 700, 0);
        
        String paragraph3 = "Dilampirkan pada dan merupakan bagian yang tidak terpisahkan dari Polis [b]AIA Health X[/b] dengan Nomor polis " +
                "39337753 atas nama OLIVIA WANGSA.\n\nEndosemen ini diberikan untuk perpanjangan polis produk asuransi sebagai berikut :\n\n";
        
        txt.writeParagraph(paragraph3, document, xAddr, bottomY, rightX, y2, canvas, arial, 9f, 1.2f);
        
        y2 -= 140;
        
        
        int xDot2 = 262;
        int yInfo2 = 600;
        int xDataInfo2 = 275;
        
        canvas.setFontAndSize(arial, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Pemegang polis", xAddr, yInfo2, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot2, yInfo2, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[OWNER]" , xDataInfo2, yInfo2, 0);
        yInfo2 -= 12.5;
        
        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Produk Asuransi", xAddr, yInfo2, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot2, yInfo2, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[BANKTOACC]" , xDataInfo2, yInfo2, 0);
        yInfo2 -= 12.5;
        
        canvas.showTextAligned(Element.ALIGN_LEFT, "Cara Bayar", xAddr, yInfo2, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot2, yInfo2, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[BANKTONAME]" , xDataInfo2, yInfo2, 0);
        yInfo2 -= 12.5;
        
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tanggal Berlaku Polis", xAddr, yInfo2, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot2, yInfo2, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[BANKTOACC]" , xDataInfo2, yInfo2, 0);
        yInfo2 -= 12.5;
        
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tanggal Ulang Tahun Polis", xAddr, yInfo2, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot2, yInfo2, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[BANKTOACC]" , xDataInfo2, yInfo2, 0);
        
        canvas.endText();
        
        table.setTotalWidth(new float[] {90, 90, 90, 110, 100});
        table.setLockedWidth(true);
        
        //Header
        PdfPCell header1 = new PdfPCell(new Phrase("", new Font(arialBold, 8.5f)));
        header1.setBackgroundColor(new BaseColor(255, 255, 255));
        header1.setPadding(5);
        header1.setHorizontalAlignment(Element.ALIGN_CENTER);
        header1.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        PdfPCell header2 = new PdfPCell(new Phrase("Batas Tahunan Total\n\n(Rupiah)", new Font(arialBold, 8.5f)));
        header2.setBackgroundColor(new BaseColor(255, 255, 255));
        header2.setPaddingBottom(4);
        header2.setPaddingTop(5);
        header2.setPaddingLeft(1);
        header2.setPaddingRight(1);
        header2.setHorizontalAlignment(Element.ALIGN_CENTER);
        header2.setVerticalAlignment(Element.ALIGN_TOP);
        
        PdfPCell header3 = new PdfPCell(new Phrase("Premi* Per Tahun\n\n(Rupiah)", new Font(arialBold, 8.5f)));
        header3.setBackgroundColor(new BaseColor(255, 255, 255));
        header3.setPaddingBottom(4);
        header3.setPaddingTop(5);
        header3.setPaddingLeft(1);
        header3.setPaddingRight(1);
        header3.setHorizontalAlignment(Element.ALIGN_CENTER);
        header3.setVerticalAlignment(Element.ALIGN_TOP);
        
        PdfPCell header4 = new PdfPCell(new Phrase("Periode Pembayaran Premi\n(Tahun)", new Font(arialBold, 8.5f)));
        header4.setBackgroundColor(new BaseColor(255, 255, 255));
        header4.setPaddingBottom(4);
        header4.setPaddingTop(5);
        header4.setPaddingLeft(1);
        header4.setPaddingRight(1);
        header4.setHorizontalAlignment(Element.ALIGN_CENTER);
        header4.setVerticalAlignment(Element.ALIGN_TOP);
        
        PdfPCell header5 = new PdfPCell(new Phrase("Masa Pertanggungan\n\n(Tahun)", new Font(arialBold, 8.5f)));
        header5.setBackgroundColor(new BaseColor(255, 255, 255));
        header5.setPaddingBottom(4);
        header5.setPaddingTop(5);
        header5.setPaddingLeft(1);
        header5.setPaddingRight(1);
        header5.setHorizontalAlignment(Element.ALIGN_CENTER);
        header5.setVerticalAlignment(Element.ALIGN_TOP);
        
        table.addCell(header1);
        table.addCell(header2);
        table.addCell(header3);
        table.addCell(header4);
        table.addCell(header5);
        
        Font fontArial = new Font(arial, 8.5f);
        Font fontArialB = new Font(arialBold, 8.5f);
        List<String[]> listData = new ArrayList<>();
        listData.add(new String[]{"Premi tahun ke 2", "5.000.000.000,00", "13.134.000,00", "1", "1"});
        
        for (String[] row : listData) {
            PdfPCell c1 = new PdfPCell(new Phrase(row[0], fontArialB));
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
            c1.setPaddingLeft(5);
            c1.setPaddingRight(5);
            c1.setPaddingBottom(3);
            table.addCell(c1);
                    
            PdfPCell c2 = new PdfPCell(new Phrase(row[1], fontArialB));
            c2.setHorizontalAlignment(Element.ALIGN_CENTER);
            c2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            c2.setPaddingLeft(5);
            c2.setPaddingRight(5);
            c2.setPaddingBottom(3);
            table.addCell(c2);

            PdfPCell c3 = new PdfPCell(new Phrase(row[2], fontArialB));
            c3.setHorizontalAlignment(Element.ALIGN_CENTER);
            c3.setVerticalAlignment(Element.ALIGN_MIDDLE);
            c3.setPaddingLeft(5);
            c3.setPaddingRight(5);
            c3.setPaddingBottom(3);
            table.addCell(c3);
            
            PdfPCell c4 = new PdfPCell(new Phrase(row[3], fontArial));
            c4.setHorizontalAlignment(Element.ALIGN_CENTER);
            c4.setVerticalAlignment(Element.ALIGN_MIDDLE);
            c4.setPaddingLeft(5);
            c4.setPaddingRight(5);
            c4.setPaddingBottom(3);
            table.addCell(c4);
            
            PdfPCell c5 = new PdfPCell(new Phrase(row[4], fontArial));
            c5.setHorizontalAlignment(Element.ALIGN_CENTER);
            c5.setVerticalAlignment(Element.ALIGN_MIDDLE);
            c5.setPaddingLeft(5);
            c5.setPaddingRight(5);
            c5.setPaddingBottom(3);
            table.addCell(c5);

        }
        
        float leftX2 = 50;
        float rightXCol = 567;

        ColumnText ctTable = new ColumnText(canvas);
        ctTable.setSimpleColumn(
                leftX2,
                bottomY,
                rightXCol,
                y2
        );
        ctTable.addElement(table);

        int status2 = ctTable.go();
        
        y2 -= 70;
        
        canvas.beginText();
        
        canvas.showTextAligned(Element.ALIGN_LEFT, "Keterangan :", xAddr, y2, 0);
        y2 -= 1;
        String paragraph4 = "[i]* Premi akan berubah setiap tahun sesuai dengan Ketentuan Polis. Kami akan memberitahukan besaran kenaikan Premi " +
                "Asuransi tersebut paling lambat 14 hari kalender sebelum Ulang Tahun Polis berikutnya.[/i]";
        
        txt.writeParagraph(paragraph4, document, xAddr, bottomY, rightX, y2, canvas, arial, 8f, 1.2f);
        y2 -= 50;
        String paragraph5 = "Endosemen perpanjangan polis Asuransi terhitung sejak tanggal tersebut di atas dan merupakan satu kesatuan " +
                "dan bagian yang tidak terpisahkan dari Polis.\n\nDikeluarkan oleh PT AIA FINANCIAL dan ditandatangani pada tanggal diterbitkan.";
        
        txt.writeParagraph(paragraph5, document, xAddr, bottomY, rightX, y2, canvas, arial, 9f, 1.2f);
        canvas.endText();
        
        document.newPage();
        canvas = writer.getDirectContent();
        pageData = writer.getImportedPage(dataReaderPreprinted, 2);
        canvas.addTemplate(pageData, 0, 0);
        
        canvas.beginText();
        canvas.setFontAndSize(arialBold, 9.5f);
        canvas.showTextAligned(Element.ALIGN_CENTER,
                "LAMPIRAN ENDOSEMEN PERPANJANGAN POLIS ASURANSI KESEHATAN", 300, 700, 0);
        
        String paragraph6 = "Dapat kami informasikan riwayat premi, klaim, dan Accumulated-X-Factor atas Polis AIA Health X  dengan Nomor polis " +
                "39337753 adalah sebagai berikut:";
        
        txt.writeParagraph(paragraph6, document, xAddr - 25, bottomY, rightX, y3, canvas, arial, 9f, 1.2f);
        canvas.endText();
        
        y3 -= 60;
        
        table2.setTotalWidth(new float[] {37, 70, 90, 50, 50, 60, 90, 70});
        table2.setLockedWidth(true);
        
        //Header
        PdfPCell header6 = new PdfPCell(new Phrase("Tahun Polis Ke- ", new Font(arialBold, 8.5f)));
        header6.setBackgroundColor(new BaseColor(255, 255, 255));
        header6.setPaddingBottom(4);
        header6.setPaddingTop(5);
        header6.setPaddingLeft(1);
        header6.setPaddingRight(1);
        header6.setHorizontalAlignment(Element.ALIGN_CENTER);
        header6.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        PdfPCell header7 = new PdfPCell(new Phrase("Premi Standar per Frekuensi Pembayaran Premi\n(Rupiah)", new Font(arialBold, 8.5f)));
        header7.setBackgroundColor(new BaseColor(255, 255, 255));
        header7.setPaddingBottom(4);
        header7.setPaddingTop(5);
        header7.setPaddingLeft(1);
        header7.setPaddingRight(1);
        header7.setHorizontalAlignment(Element.ALIGN_CENTER);
        header7.setVerticalAlignment(Element.ALIGN_TOP);
        
        PdfPCell header8 = new PdfPCell(new Phrase("Jumlah Klaim-X*\n(Rupiah)", new Font(arialBold, 8.5f)));
        header8.setBackgroundColor(new BaseColor(255, 255, 255));
        header8.setPaddingBottom(4);
        header8.setPaddingTop(5);
        header8.setPaddingLeft(5);
        header8.setPaddingRight(5);
        header8.setHorizontalAlignment(Element.ALIGN_CENTER);
        header8.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        PdfPCell header9 = new PdfPCell(new Phrase("Status AIA Vitality*\n(Jika ada)", new Font(arialBold, 8.5f)));
        header9.setBackgroundColor(new BaseColor(255, 255, 255));
        header9.setPaddingBottom(4);
        header9.setPaddingTop(5);
        header9.setPaddingLeft(3);
        header9.setPaddingRight(3);
        header9.setHorizontalAlignment(Element.ALIGN_CENTER);
        header9.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        PdfPCell header10 = new PdfPCell(new Phrase("X-Factor", new Font(arialBold, 8.5f)));
        header10.setBackgroundColor(new BaseColor(255, 255, 255));
        header10.setPaddingBottom(4);
        header10.setPaddingTop(5);
        header10.setPaddingLeft(1);
        header10.setPaddingRight(1);
        header10.setHorizontalAlignment(Element.ALIGN_CENTER);
        header10.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        PdfPCell header11 = new PdfPCell(new Phrase("Accumulated X-Factor", new Font(arialBold, 8.5f)));
        header11.setBackgroundColor(new BaseColor(255, 255, 255));
        header11.setPaddingBottom(4);
        header11.setPaddingTop(5);
        header11.setPaddingLeft(1);
        header11.setPaddingRight(1);
        header11.setHorizontalAlignment(Element.ALIGN_CENTER);
        header11.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        PdfPCell header12 = new PdfPCell(new Phrase("Premi per Frekuensi Pembayaran Premi\n(Rupiah)", new Font(arialBold, 8.5f)));
        header12.setBackgroundColor(new BaseColor(255, 255, 255));
        header12.setPaddingBottom(4);
        header12.setPaddingTop(5);
        header12.setPaddingLeft(5);
        header12.setPaddingRight(5);
        header12.setHorizontalAlignment(Element.ALIGN_CENTER);
        header12.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        PdfPCell header13 = new PdfPCell(new Phrase("Frekuensi Pembayaran Premi", new Font(arialBold, 8.5f)));
        header13.setBackgroundColor(new BaseColor(255, 255, 255));
        header13.setPaddingBottom(4);
        header13.setPaddingTop(5);
        header13.setPaddingLeft(1);
        header13.setPaddingRight(1);
        header13.setHorizontalAlignment(Element.ALIGN_CENTER);
        header13.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        table2.addCell(header6);
        table2.addCell(header7);
        table2.addCell(header8);
        table2.addCell(header9);
        table2.addCell(header10);
        table2.addCell(header11);
        table2.addCell(header12);
        table2.addCell(header13);
        
        List<String[]> listData2 = new ArrayList<>();
        listData2.add(new String[]{"2", "13.134.000,00", "0,00", "-", "0%", "100%", "13.134.000,00", "Tahunan"});
        listData2.add(new String[]{"1", "10.567.000,00", "0,00", "-", "0%", "100%", "10.567.000,00", "Tahunan"});
        
        for (String[] row : listData2) {
            PdfPCell c1 = new PdfPCell(new Phrase(row[0], fontArial));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
            c1.setPaddingLeft(5);
            c1.setPaddingRight(5);
            c1.setPaddingBottom(3);
            table2.addCell(c1);
                    
            PdfPCell c2 = new PdfPCell(new Phrase(row[1], fontArial));
            c2.setHorizontalAlignment(Element.ALIGN_CENTER);
            c2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            c2.setPaddingLeft(5);
            c2.setPaddingRight(5);
            c2.setPaddingBottom(3);
            table2.addCell(c2);

            PdfPCell c3 = new PdfPCell(new Phrase(row[2], fontArial));
            c3.setHorizontalAlignment(Element.ALIGN_CENTER);
            c3.setVerticalAlignment(Element.ALIGN_MIDDLE);
            c3.setPaddingLeft(5);
            c3.setPaddingRight(5);
            c3.setPaddingBottom(3);
            table2.addCell(c3);
            
            PdfPCell c4 = new PdfPCell(new Phrase(row[3], fontArial));
            c4.setHorizontalAlignment(Element.ALIGN_CENTER);
            c4.setVerticalAlignment(Element.ALIGN_MIDDLE);
            c4.setPaddingLeft(5);
            c4.setPaddingRight(5);
            c4.setPaddingBottom(3);
            table2.addCell(c4);
            
            PdfPCell c5 = new PdfPCell(new Phrase(row[4], fontArial));
            c5.setHorizontalAlignment(Element.ALIGN_CENTER);
            c5.setVerticalAlignment(Element.ALIGN_MIDDLE);
            c5.setPaddingLeft(5);
            c5.setPaddingRight(5);
            c5.setPaddingBottom(3);
            table2.addCell(c5);
            
            PdfPCell c6 = new PdfPCell(new Phrase(row[5], fontArial));
            c6.setHorizontalAlignment(Element.ALIGN_CENTER);
            c6.setVerticalAlignment(Element.ALIGN_MIDDLE);
            c6.setPaddingLeft(5);
            c6.setPaddingRight(5);
            c6.setPaddingBottom(3);
            table2.addCell(c6);
            
            PdfPCell c7 = new PdfPCell(new Phrase(row[6], fontArial));
            c7.setHorizontalAlignment(Element.ALIGN_CENTER);
            c7.setVerticalAlignment(Element.ALIGN_MIDDLE);
            c7.setPaddingLeft(5);
            c7.setPaddingRight(5);
            c7.setPaddingBottom(3);
            table2.addCell(c7);
            
            PdfPCell c8 = new PdfPCell(new Phrase(row[7], fontArial));
            c8.setHorizontalAlignment(Element.ALIGN_CENTER);
            c8.setVerticalAlignment(Element.ALIGN_MIDDLE);
            c8.setPaddingLeft(5);
            c8.setPaddingRight(5);
            c8.setPaddingBottom(3);
            table2.addCell(c8);

        }
        
        float leftX3 = xAddr - 55;
        float rightXCol3 = 587;

        ColumnText ctTable2 = new ColumnText(canvas);
        ctTable2.setSimpleColumn(
                leftX3,
                bottomY,
                rightXCol3,
                y3
        );
        ctTable2.addElement(table2);

        int status = ctTable2.go(); 
        
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
