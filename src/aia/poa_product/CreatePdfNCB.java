    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.poa_product;

import aia.controller.BasePdfGenerator;
import aia.controller.TextModification;
import aia.model.BaseModel;
import aia.model.productMapping.NcbModel;
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
public class CreatePdfNCB implements BasePdfGenerator{
    
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
        
        if(!(first instanceof NcbModel)){
            throw new IllegalArgumentException("Not Ncb Model");
        }
        
        NcbModel model = (NcbModel) first;
        yAddr = 712;
        xAddr = 72;
        yInfo = 717;
        xInfo = 296;
        xDot = 408;
        xDataInfo = 416;
        sortingDir = params[2];
        getCurrentDir();
        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document,
            new FileOutputStream(sortingDir + product + "_" + model.getChdrnum() + ".pdf"));
        
        dataReaderPreprinted = new PdfReader(paperDir + "PAPER NFUL.pdf");

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
        
        String owner = model.getSurnamepp(); 
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

        
        // No Polis
        canvas.setFontAndSize(arial, 8.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "No. Polis", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getChdrnum(), xDataInfo, yInfo, 0);
        yInfo -= 12.5;

        // Nama Produk

        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Produk", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getProdName(), xDataInfo, yInfo, 0);
        yInfo -= 12.5;

        // Nama Tertanggung
        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Tertanggung", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getSurnamela(), xDataInfo, yInfo, 0);
        

        // ========== PERIHAL ===========================
        canvas.setFontAndSize(arialBold, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT,
                "Perihal : Bonus Tidak Ada Klaim (No Claim Bonus)", xAddr, 561, 0);
        
        
        // ===================== ISI SURAT ==========================

        int topY = 547;
        int bottomY = 50;   
        int rightX = 548;
        int rightX2 = 488;
        
        String paragraph = "[b]Bapak/Ibu " + model.getSurnamepp() + "[/b] yang terhormat,\n\n" +
                "Terima kasih atas kepercayaan yang Anda berikan kepada PT AIA FINANCIAL ([b]\"AIA\"[/b]) sebagai penyedia " +
                "kebutuhan perlindungan asuransi bagi Anda dan keluarga.\n\n" +
                "Bersama ini Kami informasikan bahwa Anda berhak mendapatkan Manfaat Asuransi [b]Bonus Tidak Ada Klaim (No " +
                "Claim Bonus)[/b], yang dibayarkan berdasarkan Periode Evaluasi sesuai ketentuan Polis Anda.\n\n" +
                "Bonus Tidak Ada Klaim (No Claim Bonus) yang akan Anda terima adalah sebesar [b]Rp" + txt.setCurrencyIdr(model.getNcbAmount()) + 
                " (" + model.getTerbilang() + ")[/b].\n\n" +
                "Untuk pembayaran Manfaat Asuransi tersebut, mohon Anda melengkapi dokumen sebagai berikut:\n";
        
        txt.writeParagraph(paragraph, document, xAddr, bottomY, rightX, topY, canvas, arial, 9.5f, 1.2f);
        topY -= 140;
        
        String[] numbering = {
            "Formulir Permohonan Manfaat Asuransi yang telah Anda isi dengan benar dan lengkap (dapat " +
            "diunduh di website AIA Financial: www.aia-financial.co.id);",
            "Fotokopi identitas Anda yang berlaku; dan",
            "Polis asli, khusus untuk Bonus Tidak Ada Klaim (No Claim Bonus) Periode Evaluasi terakhir."
        };
        
        Font normalFont = new Font(arial, 9.5f);
        Font italicFont = new Font(arial, 9.5f, Font.ITALIC);
        Font numberFont = new Font(arial, 9f);


        for (int i = 0; i < numbering.length; i++) {

            ColumnText ct = new ColumnText(canvas);
            ct.setSimpleColumn(xAddr, bottomY, rightX2, topY);

            Paragraph p = new Paragraph();
            p.setLeading(13f);
            
            float indent = 5f;
            p.setIndentationLeft(indent);
            p.setFirstLineIndent(-indent);
            
            p.add(new Chunk(toRoman(i + 1) + ". ", numberFont));

            if (i == 0) {
                p.add(new Chunk(
                    "Formulir Permohonan Manfaat Asuransi yang telah Anda isi dengan benar dan lengkap (",
                    normalFont
                ));
                p.add(new Chunk(
                    "dapat diunduh di website AIA Financial: ",
                    italicFont
                ));
                Chunk link = new Chunk(
                    "www.aia-financial.co.id",
                    italicFont
                );
                link.setAnchor("https://www.aia-financial.co.id");
                p.add(link);
                
                p.add(new Chunk(");", italicFont));

            } else {
                p.add(new Chunk(numbering[i], normalFont));
            }

            ct.addElement(p);
            ct.go();

            topY = (int) ct.getYLine();
        }

        topY -= 10;

        String paragraph2 = "Setelah lengkap, dokumen-dokumen sesuai persyaratan di atas dapat dikirimkan ke alamat sebagai berikut\n\n" +
                "PT AIA FINANCIAL\nAIA Central\nJl. Jend. Sudirman Kav. 48A\nJakarta Selatan 12930, Indonesia\nUp. Department POS Lt. 11\n\n" +
                "[b]Proses pembayaran Manfaat Asuransi akan dilakukan apabila dokumen tersebut di atas telah diterima " +
                "dengan lengkap dan benar.[/b]\n\n" +
                "Apabila dokumen yang dipersyaratkan tersebut di atas belum Kami terima dalam kurun waktu 30 hari kalender " +
                "sejak tanggal berlaku Manfaat Bonus Tidak Ada Klaim [b](No Claim Bonus), maka Manfaat Asuransi Bonus Tidak " +
                "Ada Klaim (No Claim Bonus) akan Kami bayarkan ke rekening bank Anda sesuai dengan data yang ada " +
                "pada Kami[/b].\n\nUntuk informasi lebih lanjut, silakan menghubungi AIA Customer Care Officer setiap hari Senin - Jum’at pada pukul " +
                "08.00 - 17.00 WIB dan dengan senang hati Kami akan membantu Anda.\n\n" +
                "Jakarta, [b]" + txt.convertDateMM(cetak) + "[/b]\nHormat kami,";
        
        txt.writeParagraph(paragraph2, document, xAddr, bottomY, rightX, topY, canvas, arial, 9f, 1.5f);
        
        canvas.endText();
        
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
            Set.of("CNR");
    
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
