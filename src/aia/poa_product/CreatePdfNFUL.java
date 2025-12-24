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
public class CreatePdfNFUL implements BasePdfGenerator{
    
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
        
        dataReaderPreprinted = new PdfReader(paperDir + "PAPER NFUL.pdf");

        document.open();
        PdfContentByte canvas = writer.getDirectContent();
        PdfPTable table = new PdfPTable(4);
        
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
        canvas.setFontAndSize(arial, 7.5f);

        // ================= HEADER =======================
        canvas.showTextAligned(Element.ALIGN_LEFT, "Kepada yang terhormat :", xAddr, yAddr, 0);
        yAddr = (float) (yAddr - 10);
        canvas.setFontAndSize(arialBold, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Bapak/Ibu [ZOWNER]", xAddr, yAddr, 0);
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
        canvas.showTextAligned(Element.ALIGN_LEFT, "[CHDRNUM]", xDataInfo, yInfo, 0);
        yInfo -= 12.5;

        // Nama Produk
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Produk", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[PROD_NAME]", xDataInfo, yInfo, 0);
        yInfo -= 12.5;

        // Nama Tertangung/Peserta
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Tertangung/Peserta", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[ZNAME]", xDataInfo, yInfo, 0);
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
        canvas.showTextAligned(Element.ALIGN_LEFT, "[TUNG_PREMI]", xDataInfo, yInfo, 0);
        yInfo -= 12.5;

        // Periode Bayar
        canvas.setFontAndSize(arial, 8.5f);
        String periode = polisModel.getBillfreq().equals("12") ? "Bulanan" : polisModel.getBillfreq();
        canvas.showTextAligned(Element.ALIGN_LEFT, "Periode Bayar", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[periode]", xDataInfo, yInfo, 0);
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
        
        // Tanggal Cetak
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tanggal Cetak", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "[CHDRDUE]" , xDataInfo, yInfo, 0);
        yInfo -= 12.5;


        // ========== PERIHAL ===========================
        canvas.setFontAndSize(arialBold, 6.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT,
                "Perihal : Status Polis Tidak Aktif", xAddr, 586, 0);
        canvas.setFontAndSize(arialBold, 9.5f);
        canvas.showTextAligned(Element.ALIGN_RIGHT,
                "Jakarta, " + "[CHDRDUE]" , 544, 586, 0);
        canvas.setFontAndSize(arial, 6.5f);
        
        // ===================== ISI SURAT ==========================

        int topY = 576;   // posisi atas kolom
        int bottomY = 50; // posisi bawah kolom   
        int rightX = 548; // Posisi kanan kolom
        int leftX = 62; // Posisi kanan kolom
        
        String paragraph = "Dengan hormat,\n\n" +
                "Terima kasih atas kepercayaan Anda telah memilih PT AIA FINANCIAL (\"AIA\") sebagai penyedia kebutuhan perlindungan asuransi bagi Anda dan keluarga.\n\n\n" +
                "Dengan sangat menyesal kami informasikan bahwa saat ini status polis Anda dalam keadaan tidak aktif, artinya Anda dan keluarga sudah tidak lagi " +
                "mendapatkan perlindungan dari AIA, hal ini dikarenakan kami belum menerima pembayaran Premi/Kontribusi yang telah jatuh tempo pada tanggal [PTDATE].\n\n\n" +
                "Berikut adalah informasi biaya yang harus Anda bayar dan juga total Nilai Tebus yang akan dibayarkan jika Anda memutuskan untuk tidak mengaktifkan polis:\n\n";
        
        txt.writeParagraph(paragraph, document, xAddr, bottomY, rightX, topY, canvas, arial, 6.5f, 1.2f);
        canvas.endText();
        
        table.setTotalWidth(new float[] {130, 120, 120, 105});
        table.setLockedWidth(true);
        
        //Header
        PdfPCell header1 = new PdfPCell(new Phrase("Jenis Akun", new Font(arialBold, 6.5f)));
        header1.setBackgroundColor(new BaseColor(200, 200, 200));
        header1.setPadding(5);
        header1.setHorizontalAlignment(Element.ALIGN_CENTER);
        header1.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        PdfPCell header2 = new PdfPCell(new Phrase("Nilai Akun", new Font(arialBold, 6.5f)));
        header2.setBackgroundColor(new BaseColor(200, 200, 200));
        header2.setPadding(5);
        header2.setHorizontalAlignment(Element.ALIGN_CENTER);
        header2.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        PdfPCell header3 = new PdfPCell(new Phrase("Nilai Pembatalan", new Font(arialBold, 6.5f)));
        header3.setBackgroundColor(new BaseColor(200, 200, 200));
        header3.setPadding(9);
        header3.setHorizontalAlignment(Element.ALIGN_CENTER);
        header3.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        PdfPCell header4 = new PdfPCell(new Phrase("Nilai Tebus", new Font(arialBold, 6.5f)));
        header4.setBackgroundColor(new BaseColor(200, 200, 200));
        header4.setPadding(9);
        header4.setHorizontalAlignment(Element.ALIGN_CENTER);
        header4.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        table.addCell(header1);
        table.addCell(header2);
        table.addCell(header3);
        table.addCell(header4);
        
        List<String[]> listData = new ArrayList<>();
        listData.add(new String[]{"Akun Premi/Kontribusi Dasar", "Rp\t\t\t\t[NILAI_AKUN]", "Rp\t\t\t\t[NILAI_BTAL]","Rp\t\t\t\t[NILAI_TBUS]"});
        
        Font fontArial = new Font(arial, 6.5f);
        Font fontArialB = new Font(arialBold, 6.5f);
        
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
            c3.setHorizontalAlignment(Element.ALIGN_LEFT);
            c3.setVerticalAlignment(Element.ALIGN_MIDDLE);
            c3.setPadding(4);
            table.addCell(c3);
            
            PdfPCell c4 = new PdfPCell(new Phrase(row[3], fontArial));
            c4.setHorizontalAlignment(Element.ALIGN_LEFT);
            c4.setVerticalAlignment(Element.ALIGN_MIDDLE);
            c4.setPadding(4);
            table.addCell(c4);
        }
        
        PdfPCell cell = new PdfPCell(new Phrase("Total", fontArial));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setColspan(3);
        cell.setPadding(5);
        table.addCell(cell);
        
        PdfPCell t4 = new PdfPCell(new Phrase("Rp\t\t\t\t[TOTAL]", fontArialB));
        t4.setHorizontalAlignment(Element.ALIGN_LEFT);
        t4.setVerticalAlignment(Element.ALIGN_MIDDLE);
        t4.setPadding(5);
        table.addCell(t4);
        
        
        table.writeSelectedRows(0, -1, xAddr, 488, canvas);
        
        String afterTable = "AIA merupakan perusahaan asuransi jiwa terbesar kedua di dunia yang diukur dengan nilai kapitalisasi pasar dan kami telah melindungi masyarakat selama " +
                "hampir satu abad . Hari ini kami sudah melindungi lebih dari 1.1 juta masyarakat Indonesia oleh karena itu kami sangat memahami pentingnya perlindungan " +
                "asuransi, khususnya di Indonesia di mana penetrasi asuransi masih rendah.\n\n" +
                "Berdasarkan hal tersebut, prioritas utama AIA adalah memastikan seluruh pemegang polis selalu mendapatkan perlindungan. Untuk itu, kami harapkan Anda " +
                "segera melakukan pemulihan polis [u]dalam waktu 30 hari sejak diterbitkannya surat ini[/u] dan Anda berkesempatan untuk mendapatkan hak istimewa pemulihan " +
                "otomatis (syarat dan ketentuan berlaku).\n\n" +
                "Untuk informasi lebih lanjut mohon segera menghubungi kami/tenaga pemasar Anda atau Anda dapat mengikuti langkah-langkah sederhana sebagai berikut:\n\n";
        
        txt.writeParagraph(afterTable, document, xAddr, bottomY, rightX, 420, canvas, arial, 6.5f, 1.6f);
        
        String[] numbering = {"",
            " Melampirkan copy identitas diri yang masih berlaku.",
            " Melunasi seluruh tunggakan Premi/Kontribusi sejumlah [TOTALPREMI] (sampai dengan surat ini diterbitkan) dan berdasarkan catatan kami terdapat " +
            "titipan Premi/Kontribusi sebesar Rp[titipanPremi] pada Polis Anda sehingga total tunggakan Premi/Kontribusi yang harus dilunasi yaitu sejumlah " +
            "[TOTALPREMI]. Premi/Kontribusi tersebut bisa disetorkan ke rekening Bank [YBANKKEY] 00[FLDENT] atas nama [VA_OWNER] dan " +
            "lampirkan bukti pembayarannya."
        };
        
        float leftXNum = 62;
        float rightXNum = 548;
        float topYNum = 320;
        float bottomYNum = 100;

        Font normalFont = new Font(arial, 6.5f);

        for (int i = 0; i < numbering.length; i++) {

            ColumnText ct = new ColumnText(canvas);
            ct.setSimpleColumn(leftXNum, bottomYNum, rightXNum, topYNum);

            Paragraph p = new Paragraph();
            p.setFont(normalFont);
            p.setLeading(7.0f);
            p.setIndentationLeft(20);
            p.setFirstLineIndent(-10);
            
            p.add((i + 1) + ". ");

            if (i == 0) {
                p.add(" Melengkapi formulir pemulihan yang dapat diisi melalui ");

                Chunk url = new Chunk("www.aia-financial.co.id", normalFont);
                url.setUnderline(1.2f, -2f);

                p.add(url);
                p.add(".");
            } else {
                p.add(numbering[i]);
            }

            ct.addElement(p);
            ct.go();
            
            topYNum = ct.getYLine() - 2;
        }

        
        canvas.beginText();
        String note = "Catatan: \n";
        txt.writeParagraph(note, document, xAddr, bottomY, rightX, 275, canvas, arial, 6.5f, 1.2f);
        canvas.endText();
        
        String[] numbering2 = {
            " Pemulihan Polis secara otomatis sebagaimana diatur dalam Ketentuan Umum Polis (jika ada) tidak berlaku apabila Masa Tunggu Asuransi Tambahan \n" +
            "tidak berlaku.",
            " Kami akan menghubungi Anda jika ada hal lain yang dibutuhkan dan segera menginformasikan apabila perlindungan Anda sudah aktif kembali.\n\n"
            
        };
        
        float leftXNum2 = 62;
        float rightXNum2 = 548;
        float topYNum2 = 260;
        float bottomYNum2 = 100;

        for (int i = 0; i < numbering2.length; i++) {

            ColumnText ct = new ColumnText(canvas);
            ct.setSimpleColumn(leftXNum2, bottomYNum2, rightXNum2, topYNum2);

            Paragraph p = new Paragraph();
            p.setFont(new Font(arial, 6.5f));

            p.setIndentationLeft(20);  
            p.setFirstLineIndent(-10); 
            
            p.setLeading(7.0f);

            p.add((i + 1) + ". " + numbering2[i]);

            ct.addElement(p);

            ct.go();
            
            topYNum2 = ct.getYLine()- 2;  
        }
        
        String endParagraph = "Jika Anda memutuskan untuk tidak mengajukan proses pemulihan Polis dalam waktu kurang dari 100 hari sejak tanggal diterbitkannya surat ini, maka kami " +
                "akan mengembalikan nilai tebus seperti tersebut di atas, ke rekening yang tercantum di data kami. Namun demikian, kami percaya Anda akan membuat " +
                "keputusan yang tepat untuk segera mengaktifkan kembali Polis, hal ini demi memberikan perlindungan masa depan Anda dan untuk keluarga tercinta.\n\n" +
                "Demikian kami sampaikan. Terima kasih atas perhatian Anda. Jika Anda membutuhkan informasi lebih lanjut, hubungi AIA Customer Care kami mulai hari " +
                "Senin - Jumat pada pukul 08.00 - 17.00 WIB, dengan senang hati kami akan membantu.\n\n\n\n" +
                "Hormat kami,";
        
        txt.writeParagraph(endParagraph, document, xAddr, bottomY, rightX, 230, canvas, arial, 6.5f, 1.6f);
        
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
