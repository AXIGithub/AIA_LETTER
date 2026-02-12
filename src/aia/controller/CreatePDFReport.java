package aia.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.util.List;

public class CreatePDFReport {
    TextModification txt = new TextModification();
    private PdfReader dataReaderPreprinted = null;

    private BaseFont arial;
    private BaseFont arialBold;
    private String fontDir;

    private final float xContent = 122;
    private final float startY = 520;
    
    public static class ReportRow {
        public int no;
        public String cycle;
        public String jenisSurat;
        public int jumlahAcc;
        public int jumlahHalaman;
        public int jumlahAmplop;
        public int amplopPrio;
        public int amplopNonPrio;
        public int halamanPrio;
        public int halamanNonPrio;
        public int seqAwal;
        public int seqAkhir;
    }


    public void createPrintingReport(String product, String bund, String cetak, String reportDir, List<ReportRow> rows) throws Exception {
        generateReport("Log " + bund + " Printing ", "Log Report Printing " + bund,
                product, cetak, reportDir, rows);
    }

    public void createStatementReport(String product, String bund, String cetak, String reportDir, List<ReportRow> rows) throws Exception {
        generateReport("Log " + bund + " Statement ", "Log Report Statement " + bund,
                product, cetak, reportDir, rows);
    }

    public void createEstateReport(String product, String bund, String cetak, String reportDir, List<ReportRow> rows) throws Exception {
        generateReport("Log " + bund + " Estatement ", "Log Report Estatement " + bund,
                product, cetak, reportDir, rows);
    }

    private void generateReport(String title, String filePrefix, String product, String cetak, String reportDir, List<ReportRow> rows) throws Exception {
        initFonts();
        String filePath =
                reportDir + filePrefix + " " + cetak + ".pdf";
        Document document = new Document(PageSize.A4.rotate());
        PdfWriter writer =
                PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();
        PdfContentByte canvas = writer.getDirectContent();

        float y = startY;

        drawHeader(canvas, title, product, cetak, y);
        y -= 90;

        PdfPTable table = createTable();
        addTableHeader(table);

        for (ReportRow r : rows) {
            addRow(table, r);
        }
        addTotalRow(table, rows);

        drawTable(canvas, table, y);
        y -= table.getTotalHeight() + 40;

        drawSignature(canvas, y);
        document.close();
    }

    private void drawHeader(PdfContentByte canvas, String title, String product, String cetak, float y) throws Exception {
        canvas.beginText();
        canvas.setFontAndSize(arialBold, 12);
        canvas.showTextAligned(Element.ALIGN_LEFT, title, xContent, y, 0);
        y -= 30;

        canvas.setFontAndSize(arial, 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, title, xContent, y, 0);
        y -= 15;

        canvas.showTextAligned(Element.ALIGN_LEFT, "Cycle " + cetak, xContent, y, 0);
        y -= 30;

        canvas.showTextAligned(
                Element.ALIGN_LEFT,
                "Tanggal " + txt.convertDateMM(cetak),
                xContent, y, 0
        );

        canvas.endText();
    }

    private PdfPTable createTable() throws Exception {
        PdfPTable table = new PdfPTable(12);
        table.setWidths(new float[]{2,4.5f,5,4,5,5,5,5,5,5,5,5});
        table.setTotalWidth(PageSize.A4.rotate().getWidth() - 204);
        table.setLockedWidth(true);
        return table;
    }

    private void addTableHeader(PdfPTable table) {
        String[] headers = {
            "NO","CYCLE","JENIS SURAT","JUMLAH ACC",
            "JUMLAH HALAMAN","JUMLAH AMPLOP",
            "AMPLOP PRIORITY","AMPLOP NON PRIO",
            "HALAMAN PRIORITY","HALAMAN NON PRIO",
            "SEQ AWAL AMPLOP","SEQ AKHIR AMPLOP"
        };

        for (String h : headers) {
            table.addCell(createHeaderCell(h));
        }
    }
    
    private PdfPCell createHeaderCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, new Font(arialBold, 9.2f)));
        cell.setBorderWidth(1);
        cell.setBackgroundColor(new BaseColor(200,200,200));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPaddingRight(3);
        cell.setPaddingLeft(3);
        return cell;
    }

    
    private PdfPCell createTextLeftCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, new Font(arial, 9.2f)));
        cell.setBorderWidth(1);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_BOTTOM);

        cell.setPaddingRight(3);
        cell.setPaddingLeft(3);
        cell.setPaddingBottom(3);
        return cell;
    }

    private PdfPCell createTextCenterCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, new Font(arial, 9.2f)));
        cell.setBorderWidth(1);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_BOTTOM);

        cell.setPaddingRight(3);
        cell.setPaddingLeft(3);
        cell.setPaddingBottom(3);
        return cell;
    }
    
    private PdfPCell createNumberCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, new Font(arial, 9.2f)));
        cell.setBorderWidth(1);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setVerticalAlignment(Element.ALIGN_BOTTOM);

        cell.setPaddingRight(3);
        cell.setPaddingLeft(3);
        cell.setPaddingBottom(3);
        return cell;
    }


    private void addRow(PdfPTable table, ReportRow r) {
        table.addCell(createNumberCell(String.valueOf(r.no)));
        table.addCell(createTextCenterCell(r.cycle));
        table.addCell(createTextLeftCell(r.jenisSurat));
        table.addCell(createNumberCell(String.valueOf(r.jumlahAcc)));
        table.addCell(createNumberCell(String.valueOf(r.jumlahHalaman)));
        table.addCell(createNumberCell(String.valueOf(r.jumlahAmplop)));
        table.addCell(createNumberCell(String.valueOf(r.amplopPrio)));
        table.addCell(createNumberCell(String.valueOf(r.amplopNonPrio)));
        table.addCell(createNumberCell(String.valueOf(r.halamanPrio)));
        table.addCell(createNumberCell(String.valueOf(r.halamanNonPrio)));
        table.addCell(createNumberCell(String.valueOf(r.seqAwal)));
        table.addCell(createNumberCell(String.valueOf(r.seqAkhir)));
    }
    
    private void addTotalRow(PdfPTable table, List<ReportRow> rows) {

        int totalAcc = 0;
        int totalHal = 0;
        int totalAmp = 0;
        int totalPrio = 0;
        int totalNonPrio = 0;
        int totalHalPrio = 0;
        int totalHalNonPrio = 0;

        for (ReportRow r : rows) {
            totalAcc += r.jumlahAcc;
            totalHal += r.jumlahHalaman;
            totalAmp += r.jumlahAmplop;
            totalPrio += r.amplopPrio;
            totalNonPrio += r.amplopNonPrio;
            totalHalPrio += r.halamanPrio;
            totalHalNonPrio += r.halamanNonPrio;
        }

        PdfPCell totalCell = new PdfPCell(new Phrase("TOTAL", new Font(arialBold, 9.2f)));
        totalCell.setColspan(3);
        totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        totalCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        totalCell.setBorderWidth(1);
        totalCell.setPadding(3);
        table.addCell(totalCell);

        table.addCell(createNumberCell(String.valueOf(totalAcc)));
        table.addCell(createNumberCell(String.valueOf(totalHal)));
        table.addCell(createNumberCell(String.valueOf(totalAmp)));
        table.addCell(createNumberCell(String.valueOf(totalPrio)));
        table.addCell(createNumberCell(String.valueOf(totalNonPrio)));
        table.addCell(createNumberCell(String.valueOf(totalHalPrio)));
        table.addCell(createNumberCell(String.valueOf(totalHalNonPrio)));
        
        table.addCell(createNumberCell(""));
        table.addCell(createNumberCell(""));
    }

    private void drawTable(PdfContentByte canvas, PdfPTable table, float y) {
        table.writeSelectedRows(0, -1, xContent, y, canvas);
    }
    
    private void drawSignature(PdfContentByte canvas, float y) throws Exception {

        float leftX = 270;
        float rightX = PageSize.A4.rotate().getWidth() - 300;

        canvas.beginText();
        canvas.setFontAndSize(arial, 11.5f);
        
        canvas.showTextAligned(Element.ALIGN_LEFT, "Diterima Oleh,", leftX, y, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Astragraphia", leftX + 4, y - 60, 0);
        
        canvas.showTextAligned(Element.ALIGN_LEFT, "Diberikan Oleh,", rightX, y, 0);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Customer", rightX + 12, y - 60, 0);

        canvas.endText();
    }


    private void initFonts() throws Exception {
        String currDir = new java.io.File(".").getCanonicalPath();
        fontDir = currDir + "\\FONTS\\";
        arial = BaseFont.createFont(fontDir + "Arial.ttf", BaseFont.IDENTITY_H, true);
        arialBold = BaseFont.createFont(fontDir + "arial_bold.ttf", BaseFont.IDENTITY_H, true);
    }
}
