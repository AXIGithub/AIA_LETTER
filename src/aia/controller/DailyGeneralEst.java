/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 *
 * @author Ratino
 */
public class DailyGeneralEst {
    
    private static final Font timeNewRomanBold_10 = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
    private static final Font timeNewRomanBold_9 = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLD);
    private static final Font timeNewRoman_9 = new Font(Font.FontFamily.TIMES_ROMAN, 9);
    
    private BaseFont timeNewRoman;
    private BaseFont timeNewRomanBold;
    private BaseFont timeNewRomanItalic;
    private PdfReader paper;
    private PdfContentByte canvas = null;
    private PdfWriter writer = null;
    private PdfImportedPage pageData = null;
    private PdfReader dataReaderPreprinted = null;
    Document document = new Document();
    TextModification txt = new TextModification();
    
    private String name = new String();
    
//    private float x = 36;
    private float x = 192;
    private float y = 576;
    private float xCln = 185;
    private float xText = 42;
    
    public void createDailyGeneralEst(String file, String path, String dirFonts, String paper, String department, String[] dirParams) throws DocumentException, FileNotFoundException, IOException{
        long startRun = System.currentTimeMillis();
        document = new Document(PageSize.A4);
        writer = PdfWriter.getInstance(document, new FileOutputStream(path + "DVP_AAA_"+ department + ".pdf"));
        document.open();
        canvas = writer.getDirectContent();
        long endTimeCreateDoc = System.currentTimeMillis();
        System.out.println("Duration create document : " + (endTimeCreateDoc - startRun) + "ms");
        
        Date dt = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String dateNow = dateFormat.format(dt);
        
        // Fonts
        timeNewRoman = BaseFont.createFont(dirFonts + "times new roman.ttf", BaseFont.IDENTITY_H,true);
        timeNewRomanItalic = BaseFont.createFont(dirFonts + "times new roman italic.ttf", BaseFont.IDENTITY_H,true);
        timeNewRomanBold = BaseFont.createFont(dirFonts + "times new roman bold.ttf", BaseFont.IDENTITY_H, true);
        
        try(FileInputStream fs = new FileInputStream(file);
                InputStreamReader reader = new InputStreamReader(fs);
                BufferedReader br = new BufferedReader(reader)){
            String line;
            while((line = br.readLine()) != null){
                long starRead = System.currentTimeMillis();
                
                dataReaderPreprinted = new PdfReader(paper);
                pageData = writer.getImportedPage(dataReaderPreprinted, 1);
                canvas.addTemplate(pageData, 0,0);
                
                long endGetPaper = System.currentTimeMillis();
                System.out.println("Duration get Paper : " + (endGetPaper - starRead) + "ms");
                
                canvas.beginText();
                canvas.setFontAndSize(timeNewRoman, 9);
//                canvas.setFontAndSize(timeNewRoman, 12); // Info dari Juan
                String[] columns = line.split("\t");

                canvas.setFontAndSize(timeNewRomanBold, 10);
                
                canvas.showTextAligned(Element.ALIGN_CENTER, setTitle(department), xText+260, 665, 0);                
                canvas.showTextAligned(Element.ALIGN_CENTER, "No : "+columns[6], xText+262, 644, 0);

                canvas.showTextAligned(Element.ALIGN_LEFT, setHeader1(department), xText, y+30, 0);
                
                writeDetails1(columns);
                
                canvas.setFontAndSize(timeNewRomanBold, 9);
                canvas.showTextAligned(Element.ALIGN_LEFT, setItemName(department), xText, y, 0); // Nama Obligasi (Konven)

                canvas.setFontAndSize(timeNewRoman, 9); 
                canvas.showTextAligned(Element.ALIGN_LEFT, ":", xCln, y, 0);
                canvas.showTextAligned(Element.ALIGN_LEFT, columns[13], x, y, 0); //Nama SBSN
                y = (float)(y - 17);

                canvas.setFontAndSize(timeNewRomanBold, 9);
                canvas.showTextAligned(Element.ALIGN_LEFT, setItemCode(department), xText, y, 0);

                canvas.setFontAndSize(timeNewRoman, 9); 
                canvas.showTextAligned(Element.ALIGN_LEFT, ":", xCln, y, 0);
                canvas.showTextAligned(Element.ALIGN_LEFT, columns[8], x, y, 0); //Kode SBSN
                y = (float)(y - 17);
                
                writeDetails2(columns);

                y = (float)(y - 55);

                paragraphFooter();

                canvas.setFontAndSize(timeNewRoman, 7);                    
                canvas.showTextAligned(Element.ALIGN_LEFT, "Jakarta, " + dateNow, xText, y-15, 0);
                    
//                }
                canvas.endText();
                document.newPage();
                y = 576;
                long endClosePage = System.currentTimeMillis();
                System.out.println("Duration 1 Page : " + (endClosePage - starRead) + "ms");
            }
            document.close();

        } catch(IOException e){
            e.printStackTrace();
        }
        
    }

    
    public String setTitle(String department){
//        String title = depart.contains("KONVEN") ? "KONFIRMASI PENJUALAN OBLIGASI" : "KONFIRMASI PENJUALAN SUKUK NEGARA RITEL";
        return department.contains("KONVEN") ? "KONFIRMASI PENJUALAN OBLIGASI" : "KONFIRMASI PENJUALAN SUKUK NEGARA RITEL";
    }
    
    public String setHeader1(String department){
        return department.contains("KONVEN") ? "Rincian catatan Penjualan Obligasi adalah sebagai berikut" : "Rincian catatan Penjualan Sukuk Negara Ritel adalah sebagai berikut";
    }
    
    public void writeDetails1(String[] columns){
        addDetail("External CIF", columns[1]); y = (float)(y - 17);
        addDetail("No. Rekening", columns[31]); y = (float)(y - 17);       
        addDetail("SID", columns[34]); y = (float)(y - 17);
        addDetail("Nama Pemilik", columns[2]); y = (float)(y - 17);
        addDetail("Nama Pemegang Rekening", columns[5]); y = (float)(y - 17);
        writeAddress(columns[3], columns[4]); 
        addDetail("NPWP", columns[25]);  y = (float)(y - 17);
    }
    
    public void writeDetails2(String[] columns){
        addDetail("Tanggal Beli", columns[27].substring(0, 11)); y = (float)(y - 17);
        addDetail("Nominal", txt.setCurrency(columns[7]), "IDR"); y = (float)(y - 17);
        addDetail("Harga Beli", columns[22] + " %"); y = (float)(y - 17);
        addDetail("Nilai Beli", txt.setCurrency(columns[38]), "IDR"); y = (float)(y - 17);
        addDetail("Tanggal Penyelesaian", columns[11].substring(0, 11)); y = (float)(y - 17);
        addDetail("Harga Jual", columns[23] + " %"); y = (float)(y - 17);
        addDetail("Nilai Jual", txt.setCurrency(columns[39]), "IDR"); y = (float)(y - 17);
        addDetail("Capital Gain or Loss", txt.setCurrency(columns[19]), "IDR"); y = (float)(y - 17);
        addDetail("Accrue Profit Sharing", txt.setCurrency(columns[18]), "IDR"); y = (float)(y - 17);
        addDetail("Accrue Prof. Shar. Holding Period", txt.setCurrency(columns[28]), "IDR"); y = (float)(y - 17);
        addDetail("Tax", txt.setCurrency(columns[30]), "IDR"); y = (float)(y - 17);
        addDetail("Nilai Bersih", txt.setCurrency(columns[40]), "IDR"); y = (float)(y - 17);
    }
    
    public String setItemName(String department){
        return department.contains("KONVEN") ? "Nama Obligasi" : "Nama SBSN";
    }
    
    public String setItemCode(String department){
        return department.contains("KONVEN") ? "Kode Obligasi" : "Kode SBSN";
    }
    
    public void addDetail(String label, String value){
        addDetail(label, value, null);
    }
    
    public void addDetail(String label, String value, String curr){
        showTextLeft(label, xText, y, timeNewRomanBold_9);
//        showTextLeft(":",xCln, y, timeNewRoman_9);
        showTextLeft(curr != null ? ": IDR" : ":", xCln, y, timeNewRoman_9);
        if(curr != null || value.contains("%")){
            showTextRight(value,x+150,y, timeNewRoman_9);
        } else {
            showTextLeft(value,x,y, timeNewRoman_9);
        }
        
    }
    
    public void writeAddress(String addr1, String addr2){
        showTextLeft("Alamat",xText, y, timeNewRomanBold_9);
        showTextLeft(":", xCln,y, timeNewRoman_9);
        showTextLeft(addr1, x,y, timeNewRoman_9);
        y = (float)(y - 17);
        showTextLeft(addr2, x, y, timeNewRoman_9);
        y = (float)(y - 28);
    }
    
    private void showTextCentered(String text, float x, float y, Font font) {
        ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, new Phrase(text, font), x, y, 0);
    }

    private void showTextLeft(String text, float xOffset, float yOffset, Font font) {
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(text, font), xOffset, yOffset, 0);
    }

    private void showTextRight(String text, float x, float y, Font font) {
        ColumnText.showTextAligned(canvas, Element.ALIGN_RIGHT, new Phrase(text, font), x, y, 0);
    }
    
    public void paragraphFooter(){
        this.y = 136;
        canvas.setFontAndSize(timeNewRoman, 7);
        String[] paragraphs = {"Laporan/Konfirmasi ini diterbitkan oleh PT Bank CIMB Niaga Tbk. selaku Bank Kustodian berdasarkan gabungan informasi yang diterima dari Agen Penjual dan catatan ",
                               "yang dimiliki oleh Bank Kustodian. Apabila terdapat ketidaksesuaian dalam Laporan/Konfirmasi ini, pemilik Efek harus segera melaporkannya ke pihak Agen Penjual untuk ",
                               "ditindaklanjuti. Efek yang disebutkan pada Laporan/Konfirmasi ini merupakan Surat Berharga yang dikeluarkan oleh pihak Emiten/Penerbit Surat Beharga dan bukan ",
                               "merupakan produk PT Bank CIMB Niaga Tbk., sehingga tidak dijamin oleh Bank serta tidak termasuk dalam cakupan objek program penjaminan oleh Pemerintah atau Lembaga ",
                               "Penjamin Simpanan (LPS). Laporan/Konfirmasi ini diproses oleh komputer dan tidak memerlukan tanda tangan."};
        //this.y = writeParagraphs(paragraphs, xText, y);
        this.y = writeParagraphs(paragraphs, xText, y);
        Arrays.fill(paragraphs, null); // Delete isi array
        y = (float)(y - 12);
        canvas.setFontAndSize(timeNewRomanItalic,7);
        
        paragraphs = new String[] {"This Report/Confirmation issued by PT Bank CIMB Niaga Tbk as Custodian Bank based on combined information received from Selling Agent and Custodian Bank’s records. If there ",
                                    "is any inaccuracy in this Report/Confirmation, please contact your Selling Agent immediately for further action. Securities stated in this Report/Confirmation issued to the ",
                                    "Issuer of the Securities and not the product of PT Bank CIMB Niaga Tbk., hence will not be covered by the Bank and not included under Government’s guaranteed program or Lembaga ",
                                    "Penjamin Simpanan (LPS). This is a computer generated Report/Statement and require no signature"};
        
        this.y = writeParagraphs(paragraphs, xText, y);
        Arrays.fill(paragraphs, null);
        
        
    }
    
    public float writeParagraphs(String[] paragraphs, float x, float y){
        for(String paragraph : paragraphs){
            this.canvas.showTextAligned(Element.ALIGN_LEFT, paragraph, x, y, 0);
            y = (float)(y - 10);
        }
        return y;
    }
}
