/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.poa_product;

import aia.controller.BasePdfGenerator;
import aia.controller.TextModification;
import aia.model.BaseModel;
import aia.model.productMapping.AcmModel;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
public class CreatePdfACM implements BasePdfGenerator{
    
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
    private String barcode = new String();
    private String fileName = new String();
    
    

    @Override
    public void generate(List<BaseModel> dataList, String product, String cetak, String[] params) throws Exception {
        if (dataList.isEmpty()) {
            throw new IllegalArgumentException("Data kosong");
        }

        BaseModel first = dataList.get(0);
        
        if(!(first instanceof AcmModel)){
            throw new IllegalArgumentException("Not Acm Model");
        }
        
        AcmModel model = (AcmModel) first;
        yAddr = 712;
        xAddr = 72;
        yInfo = 717;
        xInfo = 296;
        xDot = 430;
        xDataInfo = 437;
        sortingDir = params[2];
        Path sortingPath = Paths.get(sortingDir).normalize();
        Files.createDirectories(sortingPath);

        getCurrentDir();
        Document document = new Document(PageSize.A4);
        fileName = product + model.getChdrnum() + "_" + cetak + ".pdf";
        Path pdfPath = sortingPath.resolve(fileName);

        PdfWriter writer = PdfWriter.getInstance(
            document,
            Files.newOutputStream(pdfPath)
        );
        
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
        canvas.setFontAndSize(arial, 7.5f);

        // ================= HEADER =======================
        canvas.showTextAligned(Element.ALIGN_LEFT, "Kepada Yth.", xAddr, yAddr, 0);
        yAddr = (float) (yAddr - 10);
        canvas.setFontAndSize(arialBold, 8.5f);
        
        String owner = model.getAgntname(); 
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
        
        for (String line : addressLines) {
            canvas.showTextAligned(Element.ALIGN_LEFT, line, xAddr, yAddr, 0);
            yAddr -= 10;
        }
        
        yAddr -= 10;
        
        float boxX = 0;
        float boxWidth = 353;
        boxCenterX = (boxX + boxWidth) / 2;
        
        barcode = model.getChdrnum() + product + cetak;
        


        // ========== PERIHAL ===========================
        canvas.setFontAndSize(arialBold, 10.2f);
        canvas.showTextAligned(Element.ALIGN_LEFT,
                "Perihal : Pengakhiran Perjanjian Agen", xAddr, 561, 0);
        canvas.showTextAligned(Element.ALIGN_RIGHT,
                "Jakarta, " + txt.convertDateMM(cetak) , 544, 561, 0);
        
        // ===================== ISI SURAT ==========================

        int topY = 535;   // posisi atas kolom
        int bottomY = 50; // posisi bawah kolom   
        int rightX = 545; // Posisi kanan kolom
        
        String paragraph = "Dengan Hormat,\n" + "\n" +
                "Berdasarkan hasil evaluasi yang dilakukan oleh PT AIA FINANCIAL (“Perusahaan”), Perusahaan " +
                "mencatat bahwa Anda tidak memenuhi ketentuan target yang telah ditetapkan oleh Perusahaan. Maka " +
                "berdasarkan ketentuan Perjanjian Agen Asuransi yang telah Anda dan Perusahaan tandatangani, " +
                "Perusahaan berhak dan dengan ini melakukan pengakhiran Perjanjian Agen Asuransi efektif pada " +
                "tanggal [b]"+ model.getTrmdate() + "[/b]." + ".\n\n" +
                "Terhitung sejak Tanggal Pengakhiran, maka:";
        
        txt.writeParagraph(paragraph, document, xAddr, bottomY, rightX, topY, canvas, arial, 10.2f, 1.2f);
        topY -= 110;
        
        String[] numbering = {
            "Hak Anda atas kompensasi, bonus atau pembayaran lain dalam bentuk apapun atas semua premi " +
            "yang diterima Perusahaan akan berakhir dengan berakhirnya Perjanjian Agen/Tenaga Pemasar.",
            "Anda tidak dapat menyatakan diri sebagai Tenaga Pemasar Perusahaan atau afiliasinya untuk " +
            "tujuan apapun juga.",
            "Anda diminta setelah menerima surat ini untuk segera membayar kembali secara penuh kepada " +
            "Perusahaan setiap pembayaran yang masih tertunggak (jika ada) dan wajib mengembalikan " +
            "seluruh dokumen, data (baik yang tercetak, tertulis maupun elektronis) dan/atau barang, peralatan " +
            "elektronik, perangkat keras komputer, piranti lunak yang masih berada dalam kendali atau " +
            "penguasaan Anda yang berhubungan dengan Perusahaan ke kantor pusat Perusahaan atau " +
            "diserahkan melalui kantor pemasaran tempat Anda bergabung.",
            "Anda dilarang untuk menghubungi, membujuk atau meminta atau berusaha membujuk, baik secara " +
            "langsung maupun tidak langsung nasabah, Tenaga Pemasar lainnya, agency leader, karyawan " +
            "atau wakil Perusahaan untuk meninggalkan atau mengakhiri perjanjiannya, polis atau perjanjian " +
            "lainnya dengan Perusahaan atau afiliasinya (apabila ada).",
            "Sesuai dengan Kode Etik Agen Asuransi Jiwa Indonesia, maka Anda tidak diperbolehkan " +
            "melakukan Penggantian (Replacement) Polis asuransi seperti penggantian yang tidak terbuka dan " +
            "sistematis (Undisclosed/Systematic Replacements), pemutarbalikan (Twisting) dan " +
            "Pencampuradukan (Churning).",
            "Dalam hal Anda bergabung dengan perusahaan asuransi jiwa lain, Anda dilarang membujuk, " +
            "memasarkan, menjual, dan/atau menutup produk dari perusahaan asuransi jiwa lain tersebut, baik " +
            "langsung maupun tidak langsung, kepada Pemegang Polis, Tertanggung, atau Peserta dari S" +
            "Perusahaan."
        };
        
        Font normalFont = new Font(arial, 9.5f);

        com.itextpdf.text.List list =
                new com.itextpdf.text.List(
                        com.itextpdf.text.List.ALPHABETICAL
                );

        list.setLowercase(true);          
        list.setAutoindent(true);
        list.setIndentationLeft(0f);

        for (String text : numbering) {
            ListItem item = new ListItem(text, normalFont);
            item.setLeading(13f);
            list.add(item);
        }
        
        ColumnText ct = new ColumnText(canvas);
        ct.setSimpleColumn(xAddr, bottomY, rightX, topY);
        ct.addElement(list);
        ct.go();

        topY = (int) ct.getYLine();
        topY -= 10;
        
        String paragraph2 = "Berakhirnya Perjanjian Agen/Tenaga Pemasar tidak mengurangi kewajiban Anda yang telah timbul " +
                "sebelum Tanggal Pengakhiran yang akan berlaku sampai pelaksanaan kewajiban-kewajiban tersebut " +
                "dinyatakan selesai. Kami berharap Anda memperhatikan seluruh ketentuan sebagaimana tersebut di " +
                "atas. Demikian surat ini kami sampaikan. Terima kasih atas perhatian dan kerja sama Anda selama ini.";

        txt.writeParagraph(paragraph2, document, xAddr, bottomY, rightX, topY, canvas, arial, 10.2f, 1.2f);

        canvas.endText();
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
            Set.of("kosong");
    
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
