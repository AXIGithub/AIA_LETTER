/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.poa_product;

import aia.controller.BasePdfGenerator;
import aia.controller.TextModification;
import aia.model.BaseModel;
import aia.model.productMapping.LiudModel;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
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
public class CreatePdfLIUD implements BasePdfGenerator{
    
    TextModification txt = new TextModification();
    private PdfReader dataReaderPreprinted = null;
    private PdfWriter writerNew = null;
    private PdfImportedPage pageData = null;
    
    private BaseFont arial;
    private BaseFont arialItalic;
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
        
        if(!(first instanceof LiudModel)){
            throw new IllegalArgumentException("Not Liud Model");
        }
        
        LiudModel model = (LiudModel) first;
        yAddr = 712;
        xAddr = 72;
        yInfo = 717;
        xInfo = 296;
        xDot = 420;
        xDataInfo = 427;
        sortingDir = params[2];
        getCurrentDir();
        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document,
                new FileOutputStream(sortingDir + product + model.getChdrnum() + "_" + cetak + ".pdf"));
        
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
        canvas.setFontAndSize(arial, 9f);

        // ================= HEADER =======================
        canvas.showTextAligned(Element.ALIGN_LEFT, "Kepada yang terhormat :", xAddr, yAddr, 0);
        yAddr = (float) (yAddr - 10);
        canvas.setFontAndSize(arialBold, 9.5f);
        
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
        
        canvas.setFontAndSize(arial, 9.5f);
        for (String line : addressLines) {
            canvas.showTextAligned(Element.ALIGN_LEFT, line, xAddr, yAddr, 0);
            yAddr -= 10;
        }

        
        
        // ================= INFO POLIS =====================
        // No Polis
        canvas.showTextAligned(Element.ALIGN_LEFT, "No. Polis", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getChdrnum(), xDataInfo, yInfo, 0);
        yInfo -= 12.5f;

        // Nama Produk
        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Produk", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getProdName(), xDataInfo, yInfo, 0);
        yInfo -= 12.5f;

        // Nama Tertanggung/Peserta
        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Tertanggung/Peserta", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getInsured(), xDataInfo, yInfo, 0);
        yInfo -= 12.5f;

        // Tanggal Mulai Asuransi 
        String occdate = model.getOccdate();
        int dotIndex = occdate.indexOf(".");
        String subsOccdate = occdate.substring(0, dotIndex);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tanggal Mulai Asuransi", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, txt.convertDateMM(subsOccdate), xDataInfo, yInfo, 0);
        yInfo -= 12.5f;

        // Jumlah Premi / Kontribusi
        canvas.showTextAligned(Element.ALIGN_LEFT, "Jumlah Premi/Kontribusi", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Rp" + txt.setCurrencyIdr(model.getSinstamt()), xDataInfo, yInfo, 0);
        yInfo -= 12.5f;

        // Periode Bayar
        String periode = model.getBillFreq().equals("12") ? "Bulanan" : "Tahunan";
        canvas.showTextAligned(Element.ALIGN_LEFT, "Periode Bayar", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, periode, xDataInfo, yInfo, 0);
        yInfo -= 12.5f;

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
        yInfo -= 12.5f;

        // Tanggal Cetak
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tanggal Cetak", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, txt.convertDateMM(cetak) , xDataInfo, yInfo, 0);
        yInfo -= 12.5f;
        canvas.setFontAndSize(arialBold, 9.5f);


        // ========== PERIHAL ===========================
        canvas.showTextAligned(Element.ALIGN_LEFT,
                "Perihal : Pemberitahuan Status Polis", xAddr, 561, 0);
        canvas.setFontAndSize(arialBold, 9.5f);
        canvas.showTextAligned(Element.ALIGN_RIGHT,
                "Jakarta, " + txt.convertDateMM(cetak) , 544, 561, 0);
        canvas.setFontAndSize(arial, 9.5f);
        canvas.endText();
        
        // ===================== ISI SURAT ==========================

        int topY = 550;
        int bottomY = 50;   
        int rightX = 545;
        
        String ptrneff = model.getPtrneff();
        int dotIndex2 = ptrneff.indexOf(".");
        String subsPtrneff = ptrneff.substring(0, dotIndex2);
        
        String paragraph = "Dengan Hormat,\n" + "\n" +
                "Terima kasih atas kepercayaan Anda telah memilih PT AIA FINANCIAL (AIA) sebagai penyedia kebutuhan " +
                "asuransi bagi Anda dan keluarga.\n\n" +
                "Bersama surat ini kami informasikan bahwa Nilai Akun Polis Anda per tanggal " + txt.convertDateMM(subsPtrneff) + " tidak mencukupi " +
                "untuk pembayaran biaya-biaya (administrasi, pemeliharaan asuransi dan biaya asuransi tambahan), oleh " +
                "karenanya [b]status Polis anda menjadi tidak aktif saat ini[/b].\n\n" +
                "Mengingat pentingnya manfaat dari perlindungan asuransi, kami menyarankan Anda secepatnya mengajukan " +
                "pemulihan Polis ini dengan cara :\n";
        
        txt.writeParagraph(paragraph, document, xAddr, bottomY, rightX, topY, canvas, arial, 9.3f, 1.2f);
        topY -= 120;
        
        List<String> rekeningList = new ArrayList<>();
        List<String> flDent = new ArrayList<>();

        for (BaseModel bm : dataList) {

            if (!(bm instanceof LiudModel)) {
                continue;
            }

            LiudModel rek = (LiudModel) bm;

            if ("P".equalsIgnoreCase(rek.getYsustyp())) {

                if (rek.getYbankkey() != null && !rek.getYbankkey().isEmpty()) {
                    rekeningList.add(rek.getYbankkey());
                    flDent.add(rek.getFldent());
                }
            }
        }
        
        String[] namaRek = rekeningList.toArray(new String[0]);
        String[] noRek = flDent.toArray(new String[0]);

        String rekeningText = "";

        if (namaRek.length > 0) {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < namaRek.length; i++) {
                if (i > 0) {
                    sb.append(" atau ");
                }
                sb.append(namaRek[i])
                  .append(" ")
                  .append(noRek[i]);
            }

            sb.append(" atas nama ")
              .append(model.getVaOwner())
              .append(". ");

            rekeningText = sb.toString();
        }

        String titipanText;

        if (model.getSacscurbal().equals("0.00")) {
            titipanText =
                "Sebagai informasi tambahan, berdasarkan catatan kami sampai dengan surat ini diterbitkan, " +
                "terdapat titipan Premi/Kontribusi sebesar Rp" +
                txt.setCurrencyIdr(model.getSacscurbal()) + " pada Polis Anda.";
        } else {
            titipanText =
                "Sebagai informasi tambahan, berdasarkan catatan kami sampai dengan surat ini diterbitkan, " +
                "terdapat titipan Premi/Kontribusi sebesar Rp" +
                txt.setCurrencyIdr(model.getSacscurbal()) +
                " (" + model.getTerbilang() + ") pada Polis Anda.";
        }
        
        String[] numbering = {
            "Melakukan penambahan dana atau premi Top-Up dengan cara menyetorkan Premi/Kontribusi ke rekening " + rekeningText + "dan melampirkan bukti " +
            "pembayarannya." + titipanText,
            "Melengkapi formulir penambahan dana Top-Up yang dapat diisi melalui www.aia-financial.co.id.",
            "Melengkapi formulir pemulihan yang dapat diisi melalui www.aia-financial.co.id.",
            "Melampirkan fotokopi identitas diri yang masih berlaku."
        };

        for (int i = 0; i < numbering.length; i++) {

            ColumnText ct = new ColumnText(canvas);
            ct.setSimpleColumn(xAddr, bottomY, rightX, topY);

            Paragraph p = new Paragraph();
            p.setFont(new Font(arial, 9.2f));

            p.setIndentationLeft(10);  
            p.setFirstLineIndent(-10); 
            p.setLeading(11.5f);
            p.setAlignment(Rectangle.ALIGN_JUSTIFIED);

            p.add(new Chunk((i + 1) + ". ", new Font(arial, 9f)));
            
            if(numbering[i].contains("www.aia-financial.co.id")){
                String beforeLink =
                    "Melengkapi formulir pemulihan yang dapat diunduh melalui ";
                String urlText = "www.aia-financial.co.id";
                String fullUrl = "https://www.aia-financial.co.id";
                
                p.add(new Chunk(beforeLink, new Font(arial, 9f)));
                
                Chunk linkChunk = new Chunk(urlText, new Font(arial, 9f));
                linkChunk.setUnderline(0.75f, -1.5f);
                linkChunk.setAnchor(fullUrl);
                p.add(linkChunk);
                
                p.add(new Chunk(";", new Font(arial, 9f)));                                                                           
            } else {
                p.add(new Chunk(numbering[i], new Font(arial, 9f)));
            }

            ct.addElement(p);

            ct.go();
            
            topY = (int) ct.getYLine(); 
        }
        topY -= 20;
        
        String paragraph2 = "Demikian kami sampaikan. Terima kasih atas perhatian Anda. Untuk mengetahui informasi besarnya dana Top-Up " +
                "yang perlu ditambahkan atau informasi lainnya, silakan hubungi AIA Customer Care kami mulai hari Senin - Jumat " +
                "pada pukul 08.00 - 17.00 WIB, dengan senang hati kami akan membantu.\n\n\n" +
                "Hormat kami,\n\n";
        
        txt.writeParagraph(paragraph2, document, xAddr, bottomY, rightX, topY, canvas, arial, 9.3f, 1.2f);
        topY -= 150;
        
        String paragraph3 = "Catatan : Perlu diketahui bahwa Penambahan Premi Top-Up tidak menjamin Polis akan tetap aktif sampai dengan Tanggal Jatuh Tempo berikutnya, mengingat kinerja " +
                "investasi sesuai dengan pasar. Sesuai dengan Ketentuan Umum Polis apabila Nilai Akun tidak cukup untuk membayar biaya-biaya yang ada maka Polis akan kembali " +
                "menjadi tidak aktif.";
        
        txt.writeParagraph(paragraph3, document, xAddr, bottomY, rightX, topY, canvas, arial, 6.2f, 1.6f);
            
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
            Logger.getLogger(CreatePdfLIUD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static final Set<String> PRIORITY_TYPES = 
            Set.of("U3R", "U4R", "U7R", "U8R", "UFR", "UXR");
    
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
