/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.poa_product;

import aia.controller.BasePdfGenerator;
import aia.controller.TextModification;
import aia.model.productMapping.AphModel;
import aia.model.BaseModel;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
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

/**
 *
 * @author Ratino
 */
public class CreatePdfAPH implements BasePdfGenerator{
    
    TextModification txt = new TextModification();
    private PdfReader dataReaderPreprinted = null;
    private PdfWriter writerNew = null;
    private PdfImportedPage pageData = null;
    
    private BaseFont arial;
    private BaseFont arialItalic;
    private BaseFont arialUnderline;
    private BaseFont arialBold;
    private float yAddr = 712;
    private float xAddr = 72;
    private float yInfo = 717;
    private float xInfo = 296;
    private float xDot = 430;
    private float xDataInfo = 437;
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

        if (!(first instanceof AphModel)) {
            throw new IllegalArgumentException("Not Aph Model");
        }

        AphModel model = (AphModel) first;
        yAddr = 712;
        xAddr = 72;
        yInfo = 717;
        xInfo = 296;
        xDot = 430;
        xDataInfo = 437;
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
        canvas.setFontAndSize(arial, 8.5f);

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

        canvas.setFontAndSize(arial, 9.5f);
        boolean allFull =
                !model.getAddr01().trim().isEmpty() &&
                !model.getAddr02().trim().isEmpty() &&
                !model.getAddr03().trim().isEmpty() &&
                !model.getAddr04().trim().isEmpty() &&
                !model.getAddr05().trim().isEmpty();

        if (allFull) {
              
                String[] lines = {
                    model.getAddr01(),
                    model.getAddr02(),
                    model.getAddr03(),
                    model.getAddr04(),
                    model.getAddr05(),
                    model.getPcode()
                };

                for (String line : lines) {
                    if (line != null && !line.trim().isEmpty()) {
                        canvas.showTextAligned(Element.ALIGN_LEFT, line, xAddr, yAddr, 0);
                        yAddr -= 10;
                    }
                }
                
        } else {
            String alamat23 = (model.getAddr02()== null || model.getAddr02().trim().isEmpty())
                ? model.getAddr03()
                : model.getAddr02()+ " " + model.getAddr03();
            String alamat45 = (model.getAddr04()== null || model.getAddr04().trim().isEmpty())
                ? model.getAddr05()
                : model.getAddr04()+ " " + model.getAddr05();

            canvas.showTextAligned(Element.ALIGN_LEFT, model.getAddr01(), xAddr, yAddr, 0);
            yAddr -= 10;
            canvas.showTextAligned(Element.ALIGN_LEFT, alamat23, xAddr, yAddr, 0);
            yAddr -= 10;
            canvas.showTextAligned(Element.ALIGN_LEFT, alamat45, xAddr, yAddr, 0);
            yAddr -= 10;
            canvas.showTextAligned(Element.ALIGN_LEFT, model.getPcode(), xAddr, yAddr, 0);
        }
        
        
        // ================= INFO POLIS =====================
        // No Polis
        canvas.showTextAligned(Element.ALIGN_LEFT, "No. Polis", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getChdrnum(), xDataInfo, yInfo, 0);
        yInfo -= 12.5f;


        // Nama Produk
        canvas.setFontAndSize(arial, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Produk", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getProdName(), xDataInfo, yInfo, 0);
        yInfo -= 12.5f;

        // Nama Tertanggung
        canvas.setFontAndSize(arial, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Tertanggung/Peserta", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);

        canvas.setFontAndSize(arialBold, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getName(), xDataInfo, yInfo, 0);
        yInfo -= 12.5f;



        // Tanggal Mulai Asuransi
        canvas.setFontAndSize(arial, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tanggal Mulai Asuransi", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);

        canvas.setFontAndSize(arialBold, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, txt.convertDateMM(model.getOccdate()), xDataInfo, yInfo, 0);
        yInfo -= 12.5f;

        // Premi / Kontribusi
        canvas.setFontAndSize(arial, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Premi/Kontribusi", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Rp" + txt.setCurrencyIdr(model.getSinstamt()), xDataInfo, yInfo, 0);
        yInfo -= 12.5f;

        // Periode Bayar
        canvas.setFontAndSize(arial, 9.5f);
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
        canvas.showTextAligned(Element.ALIGN_LEFT, "Periode Bayar", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, periode, xDataInfo, yInfo, 0);
        yInfo -= 12.5f;

        // Status Polis
        canvas.setFontAndSize(arial, 9.5f);
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
        canvas.setFontAndSize(arialBold, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, status, xDataInfo, yInfo, 0);
        yInfo -= 12.5f;

        // Tanggal Cetak  
        canvas.setFontAndSize(arial, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tanggal Cetak", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(arialBold, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, txt.convertDateMM(cetak) , xDataInfo, yInfo, 0);
        yInfo -= 12.5f;



        // ========== PERIHAL ===========================
        canvas.showTextAligned(Element.ALIGN_LEFT,
                "Perihal : Status Polis Cuti Premi/Kontribusi Otomatis", xAddr, 561, 0);
        canvas.setFontAndSize(arialBold, 9.5f);
        canvas.showTextAligned(Element.ALIGN_RIGHT,

                "Jakarta, " + txt.convertDateMM(cetak) , 544, 561, 0);
        canvas.setFontAndSize(arial, 9.5f);
        canvas.endText();

        
        // ===================== ISI SURAT ==========================

        int topY = 550;
        int bottomY = 50;  
        int rightX = 545;

        List<String> rekeningList = new ArrayList<>();
        List<String> flDent = new ArrayList<>();

        for (BaseModel bm : dataList) {

            if (!(bm instanceof AphModel)) {
                continue;
            }

            AphModel rek = (AphModel) bm;

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
                txt.setCurrencyIdr(model.getSacscurbal()) + " pada Polis Anda.\n\n";
        } else {
            titipanText =
                "Sebagai informasi tambahan, berdasarkan catatan kami sampai dengan surat ini diterbitkan, " +
                "terdapat titipan Premi/Kontribusi sebesar Rp" +
                txt.setCurrencyIdr(model.getSacscurbal()) +
                " (" + model.getTerbilang() + ") pada Polis Anda.\n\n";
        }

        
        String paragraph = "Dengan Hormat,\n" + "\n" +
                "Terima kasih atas kepercayaan Anda telah memilih PT AIA FINANCIAL (AIA) sebagai penyedia kebutuhan asuransi bagi Anda dan keluarga.\n" + "\n" +
                

                "Kami memahami kesibukan Anda sehingga sampai surat ini diterbitkan, kami belum menerima pembayaran Premi/Kontribusi Polis Anda untuk jatuh tempo tanggal [b]"
                        + txt.convertDateMM(model.getPtdate())  + "[/b] (Tanggal Jatuh Tempo) yang telah melewati Masa Leluasa.\n" + "\n" +

                
                "Kondisi tersebut di atas menyebabkan status Polis Anda menjadi Cuti Premi/Kontribusi Otomatis dan akan " +
                "dikenakan biaya (jika ada) sebagaimana diatur dalam Ketentuan Polis. Fasilitas Cuti Premi/Kontribusi Otomatis " +
                "akan berlaku selama Nilai Akun Polis Anda masih mencukupi. Anda dapat menghentikan Fasilitas Cuti " +
                "Premi/Kontribusi ini dengan melunasi tunggakan Premi/Kontribusi yang dapat disetorkan ke rekening " + rekeningText + titipanText +

                
                "Apabila Anda telah melakukan pembayaran Premi sebelum Masa Leluasa berakhir silakan menghubungi Customer Care kami agar pembayaran Premi/Kontribusi Anda dapat dibukukan.\n\n" +
                
                "Demikian kami sampaikan. Terima kasih atas perhatian Anda. Untuk informasi lebih lanjut, silakan hubungi AIA Customer Care kami mulai hari Senin - Jumat pada pukul 08.00 - 17.00 WIB, dengan senang hati kami akan membantu Anda." +
                "\n\n\n\n" +
                "Hormat kami,";
        
            txt.writeParagraph(paragraph, document, xAddr, bottomY, rightX, topY, canvas, arial, 9.5f, 1.2f);
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
    
    private static final Set<String> PRIORITY_TYPES =
            Set.of("U2R", "U4R", "U9R", "UDR", "UER", "UFR", "UXR", "UYR");
    
    public boolean isPriority(BaseModel model) {
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
