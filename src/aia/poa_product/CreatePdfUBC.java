/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.poa_product;

import aia.controller.BasePdfGenerator;
import aia.controller.TextModification;
import aia.model.BaseModel;
import aia.model.productMapping.UbcModel;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
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
public class CreatePdfUBC implements BasePdfGenerator{
    
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
        
        if(!(first instanceof UbcModel)){
            throw new IllegalArgumentException("Not Ubc Model");
        }
        
        UbcModel model = (UbcModel) first;
        yAddr = 712;
        xAddr = 72;
        yInfo = 717;
        xInfo = 296;
        xDot = 425f;
        xDataInfo = 432;
        sortingDir = params[2];
        getCurrentDir();
        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document,
                new FileOutputStream(sortingDir + product + "_" + model.getChdrnum() + ".pdf"));
        
        dataReaderPreprinted = new PdfReader(paperDir + "PAPER AIA.pdf");

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
        canvas.setFontAndSize(arial, 8.5f);

        // ================= HEADER =======================
        canvas.showTextAligned(Element.ALIGN_LEFT, "Kepada yang terhormat :", xAddr, yAddr, 0);
        yAddr = (float) (yAddr - 10);
        canvas.setFontAndSize(arialBold, 10f);
        
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
        yAddr -= 10f;
        if (!owner2.isEmpty()) {
            canvas.showTextAligned(Element.ALIGN_LEFT, owner2, xAddr, yAddr, 0);
            yAddr -= 10f;
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
        
        canvas.setFontAndSize(arial, 10f);
        for (String line : addressLines) {
            canvas.showTextAligned(Element.ALIGN_LEFT, line, xAddr, yAddr, 0);
            yAddr -= 10f;
        }

        // ================= INFO POLIS =====================
        String kode = model.getCurr().equalsIgnoreCase("IRP") ? "Rp" : "USD";
        // No Polis
        canvas.setFontAndSize(arial, 10f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "No. Polis", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getChdrnum(), xDataInfo, yInfo, 0);
        yInfo -= 12f;

        // Tertanggung
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tertanggung", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getLifasrname(), xDataInfo, yInfo, 0);
        yInfo -= 12f;

        // Jumlah Premi Dasar
        canvas.showTextAligned(Element.ALIGN_LEFT, "Jumlah Premi Dasar", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, kode + txt.setCurrencyIdr(model.getSinstamt()), xDataInfo, yInfo, 0);
        yInfo -= 12f;

        // Jumlah Premi Tambahan
        canvas.showTextAligned(Element.ALIGN_LEFT, "Jumlah Premi Tambahan", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, kode + txt.setCurrencyIdr(model.getInstprem()), xDataInfo, yInfo, 0);
        yInfo -= 12f;

        // Status Polis
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
        canvas.showTextAligned(Element.ALIGN_LEFT, status, xDataInfo, yInfo, 0);
        yInfo -= 12f;
        
        // Cara Bayar
        String periode;
        switch (model.getBillFreq()) {
            case "12":
                periode = "Bulanan";
                break;
            case "06":
                periode = "Tidak diketahui";
                break;
            case "04":
                periode = "Triwulan";
                break;
            case "02":
                periode = "Semesteran";
                break;
            case "01":
                periode = "Tahunan";
                break;
            default:
                periode = "Sekaligus";
                break;
        }
        canvas.showTextAligned(Element.ALIGN_LEFT, "Cara Bayar", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, periode, xDataInfo, yInfo, 0);
        yInfo -= 12f;
        
        // Tanggal Mulai Asuransi
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tanggal Mulai Asuransi", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, txt.convertDateMM(model.getOccdate()), xDataInfo, yInfo, 0);
        yInfo -= 12f;
        
        // Nama Produk
        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Produk", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getProdName(), xDataInfo, yInfo, 0);
        yInfo -= 12f;
        
        // Uang Pertanggungan Dasar
        canvas.showTextAligned(Element.ALIGN_LEFT, "Uang Pertanggungan Dasar", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, kode + txt.setCurrencyIdr(model.getSumins()), xDataInfo, yInfo, 0);
        yInfo -= 12f;
        
        // Mata Uang
        String currency = model.getCurr().equalsIgnoreCase("IRP") ? "Rupiah" : "Dollar Amerika";
        canvas.showTextAligned(Element.ALIGN_LEFT, "Mata Uang", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, currency, xDataInfo, yInfo, 0);
        yInfo -= 12f;
        
        // Tanggal Cetak
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tanggal Cetak", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, txt.convertDateMM(cetak), xDataInfo, yInfo, 0);
        

        // ========== PERIHAL ===========================
        canvas.setFontAndSize(arialBold, 9f);
        canvas.showTextAligned(Element.ALIGN_LEFT,
                "Perihal : Pemberitahuan Pengajuan Penggantian Tertanggung Sementara", xAddr, 561, 0);
        canvas.endText();
        
        // ===================== ISI SURAT ==========================

        int topY = 545;
        int bottomY = 50;   
        int rightX = 548;

        
        String paragraph = "Bapak/Ibu [b]" + model.getOwner() + "[/b] yang terhormat,\n\n" +
                "Terima kasih atas kepercayaan yang Bapak/Ibu berikan kepada PT AIA FINANCIAL (\"AIA\") sebagai penyedia kebutuhan " +
                "perlindungan asuransi bagi Bapak/Ibu dan keluarga.\n\n" +
                "Bersama ini Kami informasikan kembali bahwa agar pertanggungan manfaat perlindungan anak sebelum lahir (unborn " +
                "child benefit) atas Polis Anda aktif, maka diwajibkan untuk Anda segera melakukan perubahan penggantian Tertanggung " +
                "Sementara.\n\n" +
                "Tertanggung Sementara mengacu pada ibu hamil dimana setelah anak lahir maka Tertanggung akan digantikan oleh " +
                "anak yang baru dilahirkan dan telah didaftarkan sebagai Tertanggung, sehingga ibu tidak lagi menjadi Tertanggung " +
                "Sementara.\n\n" +
                "Untuk proses pengajuan Penggantian Tertanggung tersebut, mohon Anda melengkapi dokumen sebagai berikut:";
        
        txt.writeParagraph(paragraph, document, xAddr, bottomY, rightX, topY, canvas, arial, 9f, 1.2f);
        topY -= 150;
        
        String[] numbering = {
            "Formulir Permohonan Perubahan Polis yang telah diisi dengan benar dan lengkap oleh Anda " +
            "(dapat diisi melalui website AIA Financial: www.aia-financial.co.id);",
            "Fotokopi tanda bukti diri sah dari Anda; dan",
            "Fotokopi Identitas Tertanggung Baru yang berlaku (NIK / Akta Kelahiran / Surat Kenal Lahir)."
        };
        
        Font normalFont = new Font(arial, 8.8f);
        Font numberFont = new Font(arial, 8.8f);


        for (int i = 0; i < numbering.length; i++) {

            ColumnText ct = new ColumnText(canvas);
            ct.setSimpleColumn(xAddr, bottomY, rightX, topY);

            Paragraph p = new Paragraph();
            p.setLeading(13f);
            p.setAlignment(Element.ALIGN_JUSTIFIED);
            
            p.setIndentationLeft(10);
            p.setFirstLineIndent(-5);
            
            p.add(new Chunk(toRoman(i + 1) + ". ", numberFont));
            p.add(new Chunk(numbering[i], normalFont));

            ct.addElement(p);
            ct.go();

            topY = (int) ct.getYLine();
        }
        topY -= 10;
        
        String paragraph2 = "Selanjutnya seluruh berkas-berkas yang disyaratkan dikirimkan ke kantor cabang Kami yang terdekat atau ke Customer " +
                "Care Kami. Proses pengajuan perubahan Polis ini akan dilakukan apabila dokumen tersebut telah diterima dengan " +
                "lengkap dan benar.\n\n" +
                "Untuk informasi lebih lanjut, silakan menghubungi AIA Customer Care setiap hari Senin - Jumat pada pukul 08:00 WIB - " +
                "17:00 WIB melalui nomor telepon dan alamat email yang tertera pada bagian bawah surat ini. Dengan senang hati Kami " +
                "akan membantu.\n\n\n" +
                "Jakarta, [b] " + txt.convertDateMM(cetak) + "[/b]\nHormat kami,";
        
        txt.writeParagraph(paragraph2, document, xAddr, bottomY, rightX, topY, canvas, arial, 9f, 1.2f);
        document.close();


//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private boolean hasText(String s) {
        return s != null && !s.trim().isEmpty();
    }
    
    private String toRoman(int number) {
        String[] romans = {
            "i","ii","iii","iv","v","vi","vii","viii","ix","x",
            "xi","xii","xiii","xiv","xv","xvi","xvii","xviii","xix","xx"
        };
        return number <= romans.length ? romans[number - 1] : number + "";
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
            Set.of("TWR");
    
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
