/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.poa_product;

import aia.controller.BasePdfGenerator;
import aia.controller.TextModification;
import aia.model.BaseModel;
import aia.model.productMapping.Kpdi1Model;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ratino
 */
public class CreatePdfKPDI1 implements BasePdfGenerator{
    
    TextModification txt = new TextModification();
    private PdfReader dataReaderPreprinted = null;
    private PdfWriter writerNew = null;
    private PdfImportedPage pageData = null;
    private float yAddr = 712;
    private float xAddr = 72;
    private float yInfo = 717;
    private float xInfo = 296;
    private float xDot = 430;
    private float xDataInfo = 437;
    private float boxCenterX;
    
    private String currDir = new String();
    private String paperDir = new String();
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
        
        if(!(first instanceof Kpdi1Model)){
            throw new IllegalArgumentException("Not Kpdi1 Model");
        }
        
        Kpdi1Model model = (Kpdi1Model) first;
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
//        BaseFont arial = BaseFont.createFont(BaseFont.
        canvas.beginText();
        canvas.setFontAndSize(helvatica, 10);

        // ================= HEADER =======================
        canvas.showTextAligned(Element.ALIGN_LEFT, "Kepada yang terhormat :", xAddr, yAddr, 0);
        yAddr = (float) (yAddr - 10);
        canvas.setFontAndSize(helvaticaBold, 10);
        
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

//        canvas.showTextAligned(Element.ALIGN_LEFT, "Bapak/Ibu " + polisModel.getOwner(), xAddr, yAddr, 0);
//        yAddr = (float) (yAddr - 10);
        canvas.setFontAndSize(helvatica, 10);
//        canvas.showTextAligned(Element.ALIGN_LEFT, polisModel.getAlamat1(), xAddr, yAddr, 0);
//        yAddr = (float) (yAddr - 10);
//        canvas.showTextAligned(Element.ALIGN_LEFT, polisModel.getAlamat2()+ " " + polisModel.getAlamat3(), xAddr, yAddr, 0);
//        yAddr = (float) (yAddr - 10);
//        canvas.showTextAligned(Element.ALIGN_LEFT, polisModel.getAlamat4()+ " " + polisModel.getAlamat5(), xAddr, yAddr, 0);
        
        String[] lines = {
        model.getAddr01(),
        model.getAddr02(),
        model.getAddr03(),
        model.getAddr04(),
        model.getAddr05()
        };

        for (String line : lines) {
            if (line != null && !line.trim().isEmpty()) {
                canvas.showTextAligned(Element.ALIGN_LEFT, line, xAddr, yAddr, 0);
                yAddr -= 10;
            }
        }


        // ================= INFO POLIS =====================
        // No Polis
        canvas.showTextAligned(Element.ALIGN_LEFT, "No. Polis", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(helvaticaBold, 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getChdrnum(), xDataInfo, yInfo, 0);
        yInfo -= 10;

        // Nama Produk
        canvas.setFontAndSize(helvatica, 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Produk", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(helvaticaBold, 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getProdName(), xDataInfo, yInfo, 0);
        yInfo -= 10;

        // Nama Tertanggung
        canvas.setFontAndSize(helvatica, 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Tertanggung", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);

        canvas.setFontAndSize(helvaticaBold, 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, owner1, xDataInfo, yInfo, 0);
        yInfo -= 10;

        if (!owner2.isEmpty()) {
            canvas.showTextAligned(Element.ALIGN_LEFT, owner2, xDataInfo, yInfo, 0);
            yInfo -= 10;
        }


        // Tanggal Mulai Asuransi
        canvas.setFontAndSize(helvatica, 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tanggal Mulai Asuransi", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(helvaticaBold, 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getPtdate(), xDataInfo, yInfo, 0);
        yInfo -= 10;

        // Premi / Kontribusi
        canvas.setFontAndSize(helvatica, 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Premi/Kontribusi", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(helvaticaBold, 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Rp " + "premi", xDataInfo, yInfo, 0);
        yInfo -= 10;

        // Periode Bayar
        canvas.setFontAndSize(helvatica, 10);
        String periode = model.getBillFreq().equals("12") ? "Bulanan" : model.getBillFreq();
        canvas.showTextAligned(Element.ALIGN_LEFT, "Periode Bayar", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(helvaticaBold, 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, periode, xDataInfo, yInfo, 0);
        yInfo -= 10;

        // Status Polis
        canvas.setFontAndSize(helvatica, 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Status Polis", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(helvaticaBold, 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Aktif", xDataInfo, yInfo, 0);
        yInfo -= 10;

        // Tanggal Cetak
        canvas.setFontAndSize(helvatica, 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tanggal Cetak", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, ":", xDot, yInfo, 0);
        canvas.setFontAndSize(helvaticaBold, 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, txt.convertDateMM(model.getChdrdue()) , xDataInfo, yInfo, 0);
        yInfo -= 10;


        // ========== PERIHAL ===========================
        canvas.showTextAligned(Element.ALIGN_LEFT,
                "Perihal : Status Polis Cuti Premi/Kontribusi Otomatis", xAddr, 561, 0);
        canvas.setFontAndSize(helvaticaBold, 10);
        canvas.showTextAligned(Element.ALIGN_RIGHT,
                "Jakarta, " + txt.convertDateMM(model.getChdrdue()) , 544, 561, 0);
        canvas.setFontAndSize(helvatica, 10);
        
        // ===================== ISI SURAT ==========================

        int topY = 540;
        int bottomY = 100;

        ColumnText ct = new ColumnText(canvas);
        ct.setSimpleColumn(
                xAddr,
                bottomY,
                550,
                topY
        );

        Font f = new Font(Font.FontFamily.HELVETICA, 11);
        Paragraph p = new Paragraph();
        p.setFont(f);
        p.setLeading(16f);
        p.setAlignment(Element.ALIGN_JUSTIFIED);

        
        
        String paragraph = "Dengan Hormat,\n" + "\n" +
                "Terima kasih atas kepercayaan Anda telah memilih PT AIA FINANCIAL (AIA) sebagai penyedia kebutuhan asuransi bagi Anda dan keluarga.\n" + ".\n" +
                
                "Kami memahami kesibukan Anda sehingga sampai surat ini diterbitkan, kami belum menerima pembayaran Premi/Kontribusi Polis Anda untuk jatuh tempo tanggal "
                        + txt.convertDateMM(model.getChdrdue())  + " (Tanggal Jatuh Tempo) yang telah melewati Masa Leluasa.\n" + "\n" +
                
                "Kondisi tersebut di atas menyebabkan status Polis Anda menjadi Cuti Premi/Kontribusi Otomatis dan akan " +
                "dikenakan biaya (jika ada) sebagaimana diatur dalam Ketentuan Polis. Fasilitas Cuti Premi/Kontribusi Otomatis " +
                "akan berlaku selama Nilai Akun Polis Anda masih mencukupi. Anda dapat menghentikan Fasilitas Cuti " +
                "Premi/Kontribusi ini dengan melunasi tunggakan Premi/Kontribusi yang dapat disetorkan ke rekening " + "[REKAIA]" +
                " atas nama " + "[VAOWNER]" + ". " + 
                "Sebagai informasi tambahan, berdasarkan catatan kami sampai dengan surat ini diterbitkan, terdapat titipan Premi/Kontribusi sebesar Rp" + "[SACSCURBAL]" +
                " pada Polis Anda.\n" + "\n" + 
                
                "Apabila Anda telah melakukan pembayaran Premi sebelum Masa Leluasa berakhir silakan menghubungi Customer Care kami agar pembayaran Premi/Kontribusi Anda dapat dibukukan.\n" +
                
                "Demikian kami sampaikan. Terima kasih atas perhatian Anda. Untuk informasi lebih lanjut, silakan hubungi AIA kami mulai hari Senin - Jumat pada pukul 08.00 - 17.00 WIB, dengan senang hati kami akan membantu Anda." +
                "\n";

        
        txt.writeParagraph(paragraph, document, xAddr, 50, 545, 550, canvas, helvatica, 10, 1.2f);

        canvas.endText();
        document.close();


//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void getCurrentDir(){
        try {
            currDir = ""+new java.io.File(".").getCanonicalPath();
            paperDir = currDir + "\\\\" + "PAPER\\\\";
        } catch (IOException ex) {
            Logger.getLogger(CreatePdfKPDI1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static final Set<String> PRIORITY_TYPES =
            Set.of("U2R", "U4R", "U9R", "UDR", "UER", "UFR", "UXR", "UYR");
    
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
