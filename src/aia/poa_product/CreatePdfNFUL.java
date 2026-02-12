/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.poa_product;

import aia.controller.BasePdfGenerator;
import aia.controller.TextModification;
import aia.model.BaseModel;
import aia.model.productMapping.NfulModel;
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
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private float yAddr;
    private float xAddr;
    private float yInfo;
    private float xInfo;
    private float xDot;
    private float xDataInfo;
    private float boxCenterX;
    
    private String currDir = new String();
    private String paperDir = new String();
    private String dirFonts = new String();
    private String sortingDir = new String();
    private String outputDir = new String();
    private String fileName = new String();
    private String barcode = new String();
    
    

    @Override
    public void generate(List<BaseModel> dataList, String product, String cetak, String[] params) throws Exception {
        if (dataList.isEmpty()) {
            throw new IllegalArgumentException("Data kosong");
        }

        BaseModel first = dataList.get(0);
        
        if(!(first instanceof NfulModel)){
            throw new IllegalArgumentException("Not Nful Model");
        }
        
        NfulModel model = (NfulModel) first;
        yAddr = 712;
        xAddr = 72;
        yInfo = 717;
        xInfo = 296;
        xDot = 410;
        xDataInfo = 417;
        sortingDir = params[2];
        getCurrentDir();
        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document,
                new FileOutputStream(sortingDir + product + "_" + model.getChdrnum() + ".pdf"));
        
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
        canvas.setFontAndSize(arial, 6.8f);

        // ================= HEADER =======================
        canvas.showTextAligned(Element.ALIGN_LEFT, "Kepada yang terhormat :", xAddr, yAddr, 0);
        yAddr = (float) (yAddr - 10);
        canvas.setFontAndSize(arialBold, 9f);
        
        String owner = model.getOwner(); 
        String owner1 = owner;
        String owner2 = "";
        
        String[] split = owner.split(" "); 
        
        if (owner.length() > 25) {
            StringBuilder sb1 = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();

            for (int i = 0; i < split.length; i++) {
                if (i <= 1) { 
                    sb1.append(split[i]).append(" ");
                } else {
                    sb2.append(split[i]).append(" ");
                }
            }

            owner1 = sb1.toString().trim();
            owner2 = sb2.toString().trim();
        }
        
        canvas.showTextAligned(Element.ALIGN_LEFT, "Bapak/Ibu " + owner1, xAddr, yAddr, 0);
        yAddr -= 10;
        if (!owner2.isEmpty()) {
            canvas.showTextAligned(Element.ALIGN_LEFT, owner2, xAddr, yAddr, 0);
            yAddr -= 10;
        }
        
        int emptyCount = 0;
        if (!hasText(model.getAddr01())) emptyCount++;
        if (!hasText(model.getAddr02())) emptyCount++;
        if (!hasText(model.getAddr03())) emptyCount++;
        if (!hasText(model.getAddr04())) emptyCount++;
        if (!hasText(model.getAddr05())) emptyCount++;

        List<String> addressLines = new ArrayList<>();

        if (emptyCount >= 2) {
            if (hasText(model.getAddr01())) {
                addressLines.add(model.getAddr01());
            }

            String line23 = Stream.of(model.getAddr02(), model.getAddr03())
                    .filter(this::hasText)
                    .collect(Collectors.joining(" "));
            if (!line23.isEmpty()) {
                addressLines.add(line23);
            }

            String line45 = Stream.of(model.getAddr04(), model.getAddr05())
                    .filter(this::hasText)
                    .collect(Collectors.joining(" "));
            if (!line45.isEmpty()) {
                addressLines.add(line45);
            }

        } else {
            if (hasText(model.getAddr01())) addressLines.add(model.getAddr01());
            if (hasText(model.getAddr02())) addressLines.add(model.getAddr02());
            if (hasText(model.getAddr03())) addressLines.add(model.getAddr03());
            if (hasText(model.getAddr04())) addressLines.add(model.getAddr04());
            if (hasText(model.getAddr05())) addressLines.add(model.getAddr05());
        }
        
        if (hasText(model.getPcode())) {
            addressLines.add(model.getPcode());
        }
        
        canvas.setFontAndSize(arial, 9f);
        for (String line : addressLines) {
            canvas.showTextAligned(Element.ALIGN_LEFT, line, xAddr, yAddr, 0);
            yAddr -= 10;
        }

        // ================= INFO POLIS =====================
        // No Polis
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "No. Polis", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getChdrnum(), xDataInfo, yInfo, 0);
        yInfo -= 12.5;

        // Nama Produk
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Produk", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getProdName(), xDataInfo, yInfo, 0);
        yInfo -= 12.5;

        // Nama Tertangung/Peserta
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Tertangung/Peserta", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getZname(), xDataInfo, yInfo, 0);
        yInfo -= 12.5;

        // Mata Uang
        canvas.setFontAndSize(arial, 8.5f);
        String currency = model.getCntcurr().equalsIgnoreCase("IRP") ? "Rupiah" : "Dollar Amerika";
        String kode = model.getCntcurr().equalsIgnoreCase("IRP") ? "Rp" : "USD";
        canvas.showTextAligned(Element.ALIGN_LEFT, "Mata Uang", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, currency, xDataInfo, yInfo, 0);
        yInfo -= 12.5;

        // Jumlah Premi/Kontribusi
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Jumlah Premi/Kontribusi", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, kode + txt.setCurrencyIdr(model.getTungPremi()), xDataInfo, yInfo, 0);
        yInfo -= 12.5;

        // Periode Bayar
        canvas.setFontAndSize(arial, 8.5f);
        String periode;
        switch (model.getBillFreq()) {
            case "12":
                periode = "Bulanan";
                break;
            case "04":
                periode = "Tidak diketahui";
                break;
            case "01":
                periode = "Tahunan";
                break;
            default:
                periode = "Sekaligus";
                break;
        }
        canvas.showTextAligned(Element.ALIGN_LEFT, "Periode Bayar", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, periode, xDataInfo, yInfo, 0);
        yInfo -= 12.5;

        // Tanggal Mulai Asuransi
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tanggal Mulai Asuransi", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, txt.convertDateMM(model.getOccdate()), xDataInfo, yInfo, 0);
        yInfo -= 12.5;

        // Status Polis
        canvas.setFontAndSize(arial, 8.5f);
        String status;
        switch (model.getStatcode()) {
            case "IF":
                status = "Aktif";
                break;
            case "CF":
                status = "CF";
                break;
            case "NF":
                status = "Tidak Aktif";
                break;
            default:
                status = "Unknown";
                break;
        }
        canvas.showTextAligned(Element.ALIGN_LEFT, "Status Polis", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, status , xDataInfo, yInfo, 0);
        yInfo -= 12.5;
        
        // Tanggal Cetak
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tanggal Cetak", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, txt.convertDateMM(cetak) , xDataInfo, yInfo, 0);
        yInfo -= 12.5;


        // ========== PERIHAL ===========================
        canvas.setFontAndSize(arialBold, 6.8f);
        canvas.showTextAligned(Element.ALIGN_LEFT,
                "Perihal : Status Polis Tidak Aktif", xAddr, 586, 0);
        canvas.setFontAndSize(arialBold, 9f);
        canvas.showTextAligned(Element.ALIGN_RIGHT,
                "Jakarta, " + txt.convertDateMM(cetak) , 544, 586, 0);
        
        // ===================== ISI SURAT ==========================

        int topY = 576;
        int bottomY = 50;   
        int rightX = 548;
        
        String paragraph = "Dengan hormat,\n\n" +
                "Terima kasih atas kepercayaan Anda telah memilih PT AIA FINANCIAL (\"AIA\") sebagai penyedia kebutuhan perlindungan asuransi bagi Anda dan keluarga.\n\n\n" +
                "Dengan sangat menyesal kami informasikan bahwa saat ini status polis Anda dalam keadaan tidak aktif, artinya Anda dan keluarga sudah tidak lagi " +
                "mendapatkan perlindungan dari AIA, hal ini dikarenakan kami belum menerima pembayaran Premi/Kontribusi yang telah jatuh tempo pada tanggal [b]" + txt.convertDateMM(model.getPtdate()) + "[/b].\n\n\n" +
                "Berikut adalah informasi biaya yang harus Anda bayar dan juga total Nilai Tebus yang akan dibayarkan jika Anda memutuskan untuk tidak mengaktifkan polis:";
        
        txt.writeParagraph(paragraph, document, xAddr, bottomY, rightX, topY, canvas, arial, 6.8f, 1.2f);
        topY -= 100;
        canvas.endText();
        
        table.setTotalWidth(new float[] {130, 120, 120, 105});
        table.setLockedWidth(true);
        
        PdfPCell header1 = new PdfPCell(new Phrase("Jenis Akun", new Font(arialBold, 6.8f)));
        header1.setBackgroundColor(new BaseColor(180, 180, 180));
        header1.setPaddingBottom(5);
        header1.setPaddingTop(5);
        header1.setHorizontalAlignment(Element.ALIGN_CENTER);
        header1.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        PdfPCell header2 = new PdfPCell(new Phrase("Nilai Akun", new Font(arialBold, 6.8f)));
        header2.setBackgroundColor(new BaseColor(180, 180, 180));
        header2.setPaddingBottom(5);
        header2.setPaddingTop(5);
        header2.setHorizontalAlignment(Element.ALIGN_CENTER);
        header2.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        PdfPCell header3 = new PdfPCell(new Phrase("Nilai Pembatalan", new Font(arialBold, 6.8f)));
        header3.setBackgroundColor(new BaseColor(180, 180, 180));
        header3.setPaddingBottom(5);
        header3.setPaddingTop(5);
        header3.setHorizontalAlignment(Element.ALIGN_CENTER);
        header3.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        PdfPCell header4 = new PdfPCell(new Phrase("Nilai Tebus", new Font(arialBold, 6.8f)));
        header4.setBackgroundColor(new BaseColor(180, 180, 180));
        header4.setPaddingBottom(5);
        header4.setPaddingTop(5);
        header4.setHorizontalAlignment(Element.ALIGN_CENTER);
        header4.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        table.addCell(header1);
        table.addCell(header2);
        table.addCell(header3);
        table.addCell(header4);
        
        List<String[]> listData = new ArrayList<>();
        listData.add(new String[]{"Akun Premi/Kontribusi Dasar", kode + txt.setCurrencyIdr(model.getNilaiAkun()), kode + txt.setCurrencyIdr(model.getNilaiBtal()), kode + txt.setCurrencyIdr(model.getNilaiTbus())});        
        Font fontArial = new Font(arial, 6.8f);
        Font fontArialB = new Font(arialBold, 6.8f);
        
        for (String[] row : listData) {
            PdfPCell c1 = new PdfPCell(new Phrase(row[0], fontArial));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
            c1.setPadding(4);
            table.addCell(c1);
                    
            PdfPCell c2 = new PdfPCell(new Phrase(row[1], fontArial));
            c2.setHorizontalAlignment(Element.ALIGN_RIGHT);
            c2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            c2.setPadding(4);
            table.addCell(c2);  

            PdfPCell c3 = new PdfPCell(new Phrase(row[2], fontArial));
            c3.setHorizontalAlignment(Element.ALIGN_RIGHT);
            c3.setVerticalAlignment(Element.ALIGN_MIDDLE);
            c3.setPadding(4);
            table.addCell(c3);
            
            PdfPCell c4 = new PdfPCell(new Phrase(row[3], fontArial));
            c4.setHorizontalAlignment(Element.ALIGN_RIGHT);
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
        
        PdfPCell t4 = new PdfPCell(new Phrase(kode + txt.setCurrencyIdr(model.getTotalPremi()) , fontArialB));
        t4.setHorizontalAlignment(Element.ALIGN_RIGHT);
        t4.setVerticalAlignment(Element.ALIGN_MIDDLE);
        t4.setPadding(5);
        table.addCell(t4);
        
        
        table.writeSelectedRows(0, -1, xAddr, topY, canvas);
        topY -= 50;
        
        String afterTable = "AIA merupakan perusahaan asuransi jiwa terbesar kedua di dunia yang diukur dengan nilai kapitalisasi pasar dan kami telah melindungi masyarakat selama " +
                "hampir satu abad . Hari ini kami sudah melindungi lebih dari 1.1 juta masyarakat Indonesia oleh karena itu kami sangat memahami pentingnya perlindungan " +
                "asuransi, khususnya di Indonesia di mana penetrasi asuransi masih rendah.\n\n" +
                "Berdasarkan hal tersebut, prioritas utama AIA adalah memastikan seluruh pemegang polis selalu mendapatkan perlindungan. Untuk itu, kami harapkan Anda " +
                "segera melakukan pemulihan polis [u]dalam waktu 30 hari sejak diterbitkannya surat ini[/u] dan Anda berkesempatan untuk mendapatkan hak istimewa pemulihan " +
                "otomatis (syarat dan ketentuan berlaku).\n\n" +
                "Untuk informasi lebih lanjut mohon segera menghubungi kami/tenaga pemasar Anda atau Anda dapat mengikuti langkah-langkah sederhana sebagai berikut:";
        
        txt.writeParagraph(afterTable, document, xAddr, bottomY, rightX, topY, canvas, arial, 6.8f, 1.6f);
        topY -= 110;
        
        List<String> bankList = new ArrayList<>();
        List<String> noRekList = new ArrayList<>();

        for (BaseModel bm : dataList) {

            if (!(bm instanceof NfulModel)) continue;

            NfulModel rek = (NfulModel) bm;

            if ("P".equalsIgnoreCase(rek.getYsustyp())) {
                if (rek.getYbankkey() != null && !rek.getYbankkey().isEmpty()) {
                    bankList.add(rek.getYbankkey());
                    noRekList.add(rek.getFldent());
                }
            }
        }
        
        String rekeningText = "";

        if (!bankList.isEmpty()) {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < bankList.size(); i++) {
                if (i > 0) {
                    sb.append(" atau ");
                }
                sb.append(bankList.get(i))
                  .append(" ")
                  .append(noRekList.get(i));
            }

            rekeningText = sb.toString();
        }
        
        String[] numbering = {"",
            " Melampirkan copy identitas diri yang masih berlaku.",
            " Melunasi seluruh tunggakan Premi/Kontribusi sejumlah " + kode + txt.setCurrencyIdr(model.getTotalPremi()) + " (sampai dengan surat ini diterbitkan) dan berdasarkan catatan kami terdapat " +
            "titipan Premi/Kontribusi sebesar " + kode + "[titipanPremi] pada Polis Anda sehingga total tunggakan Premi/Kontribusi yang harus dilunasi yaitu sejumlah " + kode +
            txt.setCurrencyIdr(model.getTotalPremi()) + ". Premi/Kontribusi tersebut bisa disetorkan ke rekening Bank " + rekeningText + " atas nama " + model.getVaOwner() + " dan " +
            "lampirkan bukti pembayarannya."
        };

        Font normalFont = new Font(arial, 6.8f);

        for (int i = 0; i < numbering.length; i++) {

            ColumnText ct = new ColumnText(canvas);
            ct.setSimpleColumn(xAddr, bottomY, rightX, topY);

            Paragraph p = new Paragraph();
            p.setFont(normalFont);
            p.setLeading(8.5f);
            p.setIndentationLeft(10);
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
            
            topY = (int) (ct.getYLine() - 2);
        }
        topY -= 10;

        
        canvas.beginText();
        String note = "Catatan: \n";
        txt.writeParagraph(note, document, xAddr, bottomY, rightX, topY, canvas, arial, 6.8f, 1.2f);
        topY -= 10;
        canvas.endText();
        
        String[] numbering2 = {
            " Pemulihan Polis secara otomatis sebagaimana diatur dalam Ketentuan Umum Polis (jika ada) tidak berlaku apabila Masa Tunggu Asuransi Tambahan \n" +
            "tidak berlaku.",
            " Kami akan menghubungi Anda jika ada hal lain yang dibutuhkan dan segera menginformasikan apabila perlindungan Anda sudah aktif kembali."
            
        };

        for (int i = 0; i < numbering2.length; i++) {

            ColumnText ct = new ColumnText(canvas);
            ct.setSimpleColumn(xAddr, bottomY, rightX, topY);
            Paragraph p = new Paragraph();
            p.setFont(new Font(arial, 6.8f));

            p.setIndentationLeft(10);  
            p.setFirstLineIndent(-10); 
            
            p.setLeading(7.0f);

            p.add((i + 1) + ". " + numbering2[i]);

            ct.addElement(p);

            ct.go();
            
            topY = (int) (ct.getYLine()- 2);  
        }
        topY -= 3;
        
        String endParagraph = "Jika Anda memutuskan untuk tidak mengajukan proses pemulihan Polis dalam waktu kurang dari 100 hari sejak tanggal diterbitkannya surat ini, maka kami " +
                "akan mengembalikan nilai tebus seperti tersebut di atas, ke rekening yang tercantum di data kami. Namun demikian, kami percaya Anda akan membuat " +
                "keputusan yang tepat untuk segera mengaktifkan kembali Polis, hal ini demi memberikan perlindungan masa depan Anda dan untuk keluarga tercinta.\n\n" +
                "Demikian kami sampaikan. Terima kasih atas perhatian Anda. Jika Anda membutuhkan informasi lebih lanjut, hubungi AIA Customer Care kami mulai hari " +
                "Senin - Jumat pada pukul 08.00 - 17.00 WIB, dengan senang hati kami akan membantu.\n\n" +
                "Hormat kami,";
        
        txt.writeParagraph(endParagraph, document, xAddr, bottomY, rightX, topY, canvas, arial, 6.8f, 1.6f);
        
        document.close();


//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private boolean hasText(String s) {
        return s != null && !s.trim().isEmpty();
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
    
    private static final Set<String> PRIORITY_TYPES =
            Set.of("UFR");
    
    public boolean isPriority(BaseModel model){
        String cntType = model.getCnttype();
        if(cntType == null){
            return false;
        }
        return PRIORITY_TYPES.contains(cntType);
    }
    
    public String getFileName(){
        return fileName;
    }
    
    public String getBarcodes(){
        return barcode;
    }
    
    public float getYaddr(){
        return yAddr;
    }
    
    public float getXbox(){
        return boxCenterX;
    }
}
