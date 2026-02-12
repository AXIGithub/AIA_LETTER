/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.poa_product;

import aia.controller.BasePdfGenerator;
import aia.controller.TextModification;
import aia.model.BaseModel;
import aia.model.productMapping.ExpyModel;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
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
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ratino
 */
public class CreatePdfANST implements BasePdfGenerator{
    
    TextModification txt = new TextModification();
    private PdfReader dataReaderPreprinted = null;
    private PdfWriter writerNew = null;
    private PdfImportedPage pageData = null;
    
    private BaseFont arial;
    private BaseFont arialUnderlineBold;
    private BaseFont arialBold;
    private float yAddr = 720;
    private float xAddr = 38;
    private float yInfo = 724;
    private float xInfo = 262;
    private float xDot = 451;
    private float xDataInfo = 566;
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
        
        if(!(first instanceof ExpyModel)){
            throw new IllegalArgumentException("Not Expy Model");
        }
        
        ExpyModel model = (ExpyModel) first;
        sortingDir = params[2];
        getCurrentDir();
        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document,
                new FileOutputStream(sortingDir + product + "_" + model.getChdrnum() + ".pdf"));
        
        dataReaderPreprinted = new PdfReader(paperDir + "PAPER ANRPE.pdf");

        document.open();
        PdfContentByte canvas = writer.getDirectContent();
        PdfPTable table = new PdfPTable(2);
        PdfPTable table2 = new PdfPTable(2);
        PdfPTable table3 = new PdfPTable(3);
        
        canvas.beginText();
        canvas.endText();
        pageData = writer.getImportedPage(dataReaderPreprinted, 1);
        canvas.addTemplate(pageData, 0, 0);

        BaseFont helvatica = BaseFont.createFont(BaseFont. HELVETICA, BaseFont.CP1250, BaseFont.NOT_EMBEDDED);
        BaseFont helvaticaBold = BaseFont.createFont(BaseFont. HELVETICA_BOLD, BaseFont.CP1250, BaseFont.NOT_EMBEDDED);
        
        arial = BaseFont.createFont(dirFonts + "Arial.ttf", BaseFont.IDENTITY_H, true);
//        arialUnderlineBold = BaseFont.createFont(dirFonts + "ArialUnderlineBold.ttf", BaseFont.IDENTITY_H, true);
        arialBold = BaseFont.createFont(dirFonts + "arial_bold.ttf", BaseFont.IDENTITY_H, true);
        
        canvas.beginText();
        canvas.setFontAndSize(arial, 8f);

        // ================= HEADER =======================
        canvas.showTextAligned(Element.ALIGN_LEFT, "Kepada yang terhormat :", xAddr, yAddr, 0);
        yAddr = (float) (yAddr - 10);
        canvas.setFontAndSize(arialBold, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Bapak/Ibu " + model.getOwner(), xAddr, yAddr, 0);
        yAddr = (float) (yAddr - 10);
        canvas.setFontAndSize(arial, 9.5f);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getAddr01(), xAddr, yAddr, 0);
        yAddr = (float) (yAddr - 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getAddr02() + " " + model.getAddr03(), xAddr, yAddr, 0);
        yAddr = (float) (yAddr - 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getAddr04() + " " + model.getAddr05(), xAddr, yAddr, 0);
        yAddr = (float) (yAddr - 10);
        canvas.showTextAligned(Element.ALIGN_LEFT, model.getPcode(), xAddr, yAddr, 0);

        // ================= INFO POLIS =====================
        // LAPORAN
        canvas.setFontAndSize(arialBold, 12f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "LAPORAN TAHUNAN", xInfo, yInfo, 0);
        yInfo -= 16;
        
        // No Polis
        canvas.setFontAndSize(arialBold, 8f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "No. Polis", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_RIGHT, "[noPolis]", xDataInfo, yInfo, 0);
        yInfo -= 10;
        // Tertanggung
        canvas.setFontAndSize(arial, 8f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tertanggung", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_RIGHT, "[namaTertanggung]", xDataInfo, yInfo, 0);
        yInfo -= 16;

        // Jumlah Premi Dasar
        canvas.showTextAligned(Element.ALIGN_LEFT, "Jumlah Premi Dasar", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_RIGHT, "[premiKontribusiDasar]", xDataInfo, yInfo, 0);
        yInfo -= 10;
        // Jumlah Premi Tambahan
        canvas.showTextAligned(Element.ALIGN_LEFT, "Jumlah Premi Tambahan", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_RIGHT, "[premiKontribusiBerkala]", xDataInfo, yInfo, 0);
        yInfo -= 16;

        // Status Polis
        canvas.showTextAligned(Element.ALIGN_LEFT, "Status Polis", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_RIGHT, "Aktif", xDataInfo, yInfo, 0);
        yInfo -= 10;
//        // Cara Bayar
//        String periode = model.model.getb.equals("12") ? "Bulanan" : model.getb;
//        canvas.showTextAligned(Element.ALIGN_LEFT, "Cara Bayar", xInfo, yInfo, 0);
//        canvas.showTextAligned(Element.ALIGN_RIGHT, "[periode]", xDataInfo, yInfo, 0);
//        yInfo -= 10;
        // Tanggal Mulai Asuransi
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tanggal Mulai Asuransi", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_RIGHT, "[tanggalMulai]", xDataInfo, yInfo, 0);
        yInfo -= 16;

        // Nama Produk
        canvas.setFontAndSize(arialBold, 8f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Nama Produk", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_RIGHT, "[namaProduk]" , xDataInfo, yInfo, 0);
        yInfo -= 10;
        // Uang Pertanggungan/Santunan Asuransi Dasar
        canvas.setFontAndSize(arial, 8f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Uang Pertanggungan/", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_RIGHT, "[uangPertanggungan]" , xDataInfo, yInfo, 0);
        yInfo -= 10;
        // Mata Uang
        canvas.showTextAligned(Element.ALIGN_LEFT, "Mata Uang", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_RIGHT, "[mataUang]" , xDataInfo, yInfo, 0);
        yInfo -= 16;
        
        // Tanggal cetak
        canvas.setFontAndSize(arialBold, 8f);
        canvas.showTextAligned(Element.ALIGN_LEFT, "Tanggal cetak", xInfo, yInfo, 0);
        canvas.showTextAligned(Element.ALIGN_RIGHT, "[tanggalCetak]" , xDataInfo, yInfo, 0);
        canvas.endText();
        
        // ===================== ISI SURAT ==========================

        float pageTopY = 550;
        float pageBottomY = 100;
        float currentY = pageTopY;
        int rightX = 548; // Posisi kanan kolom

        String bar = "RANGKUMAN MANFAAT ASURANSI ([CURRFROM] - [CURRTO])";
        
        float barHeight = 17f;
        float barX = 30f;
        float barWidth = 537f;
        
        // kotak abu
        canvas.saveState();
        canvas.setColorFill(new BaseColor(200,200,200));
        canvas.rectangle(barX, currentY, barWidth, barHeight);
        canvas.fill();
        canvas.restoreState();
        
        canvas.beginText();
        txt.writeParagraph(bar, document, 149, pageBottomY, rightX, currentY + barHeight - 2, canvas, arialBold, 10, 1.2f);
        canvas.endText();
        currentY -= (barHeight + 10);
        
        table.setTotalWidth(new float[] {400, 137});
        table.setLockedWidth(true);
        
        float tableHeight = table.getTotalHeight();

        if (currentY - tableHeight < pageBottomY) {
            document.newPage();
            canvas = writer.getDirectContent();
            currentY = pageTopY;
        }
        
        //Header
        PdfPCell header1 = new PdfPCell(new Phrase("Keterangan", new Font(arialBold, 8.5f)));
        header1.setPadding(5);
        header1.setHorizontalAlignment(Element.ALIGN_CENTER);
        header1.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        PdfPCell header2 = new PdfPCell(new Phrase("Jumlah Saldo", new Font(arialBold, 8.5f)));
        header2.setPadding(5);
        header2.setHorizontalAlignment(Element.ALIGN_CENTER);
        header2.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        table.addCell(header1);
        table.addCell(header2);
        
        Font fontArial = new Font(arial, 8.5f);
        Font fontArialB = new Font(arialBold, 8.5f);
        
        List<SaldoGroup> groups = new ArrayList<>();

        groups.add(new SaldoGroup(
            "Manfaat Dijamin",
            Arrays.asList(
                new SaldoItem(
                    "Akumulasi Manfaat Dijamin s/d tanggal (07 Des 2024)",
                    "Rp0,00"
                ),
                new SaldoItem(
                    "Manfaat Dijamin yang telah dikreditkan tanggal (06 Des 2025)",
                    "Rp0,00"
                ),
                new SaldoItem(
                    "Akumulasi penarikan Manfaat Dijamin (07 Des 2024 - 06 Des 2025)",
                    "Rp0,00"
                )
            ),
            "Rp0,00"
        ));

        groups.add(new SaldoGroup(
            "Manfaat Tidak Dijamin",
            Arrays.asList(
                new SaldoItem(
                    "Akumulasi Manfaat Tidak Dijamin s/d tanggal (07 Des 2024)",
                    "Rp0,00"
                ),
                new SaldoItem(
                    "Manfaat Tidak Dijamin yang telah dikreditkan tanggal (06 Des 2025)",
                    "Rp0,00"
                ),
                new SaldoItem(
                    "Akumulasi penarikan Manfaat Tidak Dijamin tahun berjalan (07 Des 2024 - 06 Des 2025)",
                    "Rp0,00"
                )
            ),
            "Rp0,00"
        ));
        
        for (SaldoGroup group : groups) {

            // ===== JUDUL GROUP =====
            PdfPCell groupCell = new PdfPCell(
                new Phrase(group.title, fontArialB)
            );
            groupCell.setPaddingTop(4);
            groupCell.setPaddingBottom(2);
            groupCell.setPaddingLeft(4);
            groupCell.setBorder(Rectangle.LEFT | Rectangle.RIGHT );
            table.addCell(groupCell);
            
            PdfPCell empty = new PdfPCell(
                new Phrase("", fontArialB)
            );
            empty.setPaddingTop(4);
            empty.setPaddingBottom(2);
            empty.setPaddingLeft(4);
            empty.setBorder(Rectangle.LEFT | Rectangle.RIGHT );
            table.addCell(empty);

            // ===== DETAIL =====
            for (int i = 0; i < group.items.size(); i++) {
                SaldoItem item = group.items.get(i);

                Paragraph p = new Paragraph(item.keterangan, fontArial);

                // 👉 hanya item ke-3 (index 2) yang menjorok
                if (i == 2) {
                    p.setIndentationLeft(15f); // atur sesuai kebutuhan
                }

                PdfPCell c1 = new PdfPCell();
                c1.addElement(p);
                c1.setPaddingTop(0);
                c1.setPaddingBottom(2);
                c1.setPaddingLeft(4);
                c1.setBorder(Rectangle.LEFT | Rectangle.RIGHT );
                table.addCell(c1);

                PdfPCell c2 = new PdfPCell(new Phrase(item.jumlah, fontArial));
                c2.setHorizontalAlignment(Element.ALIGN_RIGHT);
                c2.setPaddingTop(0);
                c2.setPaddingBottom(2);
                c2.setPaddingRight(5);
                c2.setBorder(Rectangle.LEFT | Rectangle.RIGHT );
                table.addCell(c2);
            }


            // ===== TOTAL =====
            PdfPCell totalLabel = new PdfPCell(
                new Phrase("Total Saldo", fontArialB)
            );
            totalLabel.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalLabel.setPadding(5);
            table.addCell(totalLabel);

            PdfPCell totalValue = new PdfPCell(
                new Phrase(group.total, fontArial)
            );
            totalValue.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalValue.setPadding(5);
            table.addCell(totalValue);
        }
        table.writeSelectedRows(0, -1, 30, currentY, canvas);
        groups.clear();
        
        currentY -= (tableHeight + 180);
        
        String keterangan = "[u]Keterangan :[/u]";
        txt.writeParagraph(keterangan, document, xAddr, pageBottomY, rightX, currentY, canvas, arialBold, 8f, 1.2f);
        currentY -= 15;
        
        List<NumberItem> items = new ArrayList<>();

        items.add(new NumberItem(
            "Manfaat Dijamin berupa Manfaat Tahunan dijamin / Manfaat Pembayaran Tunai / " +
            "Manfaat Santunan Bulanan / Manfaat Bertahap sebagaimana tercantum dalam ketentuan Polis."
        ));

        items.add(new NumberItem(
            "Manfaat Tidak Dijamin berupa Manfaat Tambahan Tidak Dijamin / Manfaat Tambahan Pembayaran Tunai Tidak Dijamin (jika ada) sebagaimana tercantum dalam ketentuan Polis, antara lain:",
            Arrays.asList(
                "Manfaat Tahunan Tidak Dijamin / Manfaat Bonus Tahunan (Annual Bonus) / Manfaat Tambahan Tahunan Tidak Dijamin / Dividen (jika ada) akan ditetapkan pada saat ulang Tahun Polis.",
                "Bonus Akhir Polis / Manfaat Bonus Akhir Polis (Terminal Bonus) / Manfaat Tambahan Akhir Polis Tidak Dijamin (jika ada) dibayarkan di akhir Masa Asuransi."
            )
        ));

        items.add(new NumberItem(
            "Diakumulasikan dengan tingkat suku bunga yang dihitung dan ditentukan sesuai dengan kebijakan PT AIA FINANCIAL. " +
            "Untuk informasi lebih lanjut mengenai tingkat suku bunga yang ditetapkan, silakan hubungi AIA Customer Care Line."
        ));
        
        com.itextpdf.text.List mainList =
                new com.itextpdf.text.List(com.itextpdf.text.List.ORDERED);
        mainList.setIndentationLeft(0);
        mainList.setSymbolIndent(10);
        Font textFont = new Font(arial, 8f);

        
        for (NumberItem item : items) {

            ListItem mainItem = new ListItem(item.text, textFont);
            mainItem.setLeading(8f);

            if (item.subItems != null && !item.subItems.isEmpty() ) {
                mainItem.add(Chunk.NEWLINE);

                int idx = 0;
                for (String sub : item.subItems) {

                    Paragraph p = new Paragraph();
                    p.setFont(textFont);
                    p.setIndentationLeft(30);
                    p.setLeading(10);

                    p.add(ROMAN[idx++] + ". ");
                    p.add(sub);

                    mainItem.add(p);
                    mainItem.add(Chunk.NEWLINE);
                }
            }
            mainList.add(mainItem);
        }
        
        ColumnText ct = new ColumnText(canvas);
        ct.setSimpleColumn(xAddr, pageBottomY, xDataInfo, currentY);
        ct.addElement(mainList);
        
        int status = ct.go();
        items.clear();
        
        if (ColumnText.hasMoreText(status)) {
            document.newPage();
            canvas = writer.getDirectContent();

            ct.setCanvas(canvas);
            ct.setSimpleColumn(xAddr, pageBottomY, rightX, pageTopY);
            ct.go();

            currentY = ct.getYLine();
        } else {
            currentY = ct.getYLine();
        }                                       
        
        canvas.beginText();
        canvas.setFontAndSize(arialBold, 8f);
        canvas.showTextAligned(Element.ALIGN_LEFT,"Jakarta, [OCCDATE]" , xAddr, 148, 0);
        canvas.endText();
        
        document.newPage();
        pageData = writer.getImportedPage(dataReaderPreprinted, 2);
        canvas.addTemplate(pageData, 0, 0);
        
        
        float yTransaksi = 700;
        float xTransaksi = 30;
        float currentY2 = yTransaksi;
        
        canvas.saveState();
        canvas.setColorFill(new BaseColor(200,200,200));
        canvas.rectangle(barX, currentY2, barWidth, barHeight);
        canvas.fill();
        canvas.restoreState();
        
        currentY2 += 17;
        
        String bar2 = "RANGKUMAN TRANSAKSI ([CURRFROM] - [CURRTO])";
        
        txt.writeParagraph(bar2, document, 165, pageBottomY, rightX, currentY2, canvas, arialBold, 10f, 1.2f);
        currentY2 -= 35;
        
        table2.setTotalWidth(new float[] {400, 137});
        table2.setLockedWidth(true);
        
        float tableHeight2 = table.getTotalHeight();

//        if (currentY2 - tableHeight2 < pageBottomY) {
//            document.newPage();
//            canvas = writer.getDirectContent();
//            currentY2 = yTransaksi;
//        }
        
        //Header
        PdfPCell header4 = new PdfPCell(new Phrase("Keterangan", new Font(arialBold, 8.5f)));
        header4.setPadding(5);
        header4.setHorizontalAlignment(Element.ALIGN_CENTER);
        header4.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        PdfPCell header5 = new PdfPCell(new Phrase("Jumlah Saldo", new Font(arialBold, 8.5f)));
        header5.setPadding(5);
        header5.setHorizontalAlignment(Element.ALIGN_CENTER);
        header5.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        table2.addCell(header4);
        table2.addCell(header5);
        
        List<SaldoGroup> groups1 = new ArrayList<>();

        groups1.add(new SaldoGroup(
            "Pinjaman Premi Otomatis",
            Arrays.asList(
                new SaldoItem(
                    "Total Pinjaman Premi Otomatis",
                    "Tidak ada"
                )
            ),
            ""
        ));

        groups1.add(new SaldoGroup(
            "Pinjaman Polis",
            Arrays.asList(
                new SaldoItem(
                    "Total Pinjaman Polis",
                    "Rp0,00"
                )
            ),
            ""
        ));
        
        for (SaldoGroup group : groups1) {

            // ===== JUDUL GROUP =====
            PdfPCell groupCell = new PdfPCell(
                new Phrase(group.title, fontArialB)
            );
            groupCell.setPaddingTop(4);
            groupCell.setPaddingBottom(2);
            groupCell.setPaddingLeft(4);
            groupCell.setBorder(Rectangle.LEFT | Rectangle.RIGHT );
            table2.addCell(groupCell);
            
            PdfPCell empty = new PdfPCell(
                new Phrase("", fontArialB)
            );
            empty.setPaddingTop(4);
            empty.setPaddingBottom(2);
            empty.setPaddingLeft(4);
            empty.setBorder(Rectangle.LEFT | Rectangle.RIGHT );
            table2.addCell(empty);

            // ===== DETAIL =====
            for (int i = 0; i < group.items.size(); i++) {
                SaldoItem item = group.items.get(i);

                Paragraph p = new Paragraph(item.keterangan, fontArial);
                
                p.setIndentationLeft(20);

                PdfPCell c1 = new PdfPCell();
                c1.addElement(p);
                c1.setPaddingTop(0);
                c1.setPaddingBottom(15);
                c1.setPaddingLeft(4);
                c1.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
                table2.addCell(c1);

                PdfPCell c2 = new PdfPCell(new Phrase(item.jumlah, fontArial));
                c2.setHorizontalAlignment(Element.ALIGN_RIGHT);
                c2.setPaddingTop(0);
                c2.setPaddingBottom(2);
                c2.setPaddingRight(5);
                c2.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM );
                table2.addCell(c2);
            }
        }
        table2.writeSelectedRows(0, -1, 30, currentY2, canvas);
        
        currentY2 -= 110;
        
        txt.writeParagraph(keterangan, document, xAddr, pageBottomY, rightX, currentY2, canvas, arialBold, 8f, 1.2f);
        currentY2 -= 15;
        
        com.itextpdf.text.List mainList1 =
                new com.itextpdf.text.List(com.itextpdf.text.List.ORDERED);
        mainList1.setIndentationLeft(0);
        mainList1.setSymbolIndent(10);
        List<NumberItem> items1 = new ArrayList<>();

        items1.add(new NumberItem(
            "Pengenaan bunga berlaku untuk Polis dengan status Pinjaman  Premi  Otomatis  (Automatic Premium Loan/APL) atau  sedang  ada Pinjaman  Polis  " +
            "(Policy Loan). Syarat dan ketentuan mengenai Pinjaman Premi Otomatis dan Pinjaman Polis di atur selengkapnya dalam buku Polis. Untuk informasi " +
            "tingkat suku bunga Pinjaman Premi Otomatis dan Pinjaman Polis dapat dilihat di website PT AIA FINANCIAL: www.aia-financial.co.id. "
        ));
        
        for (NumberItem item : items1) {

            ListItem mainItem = new ListItem(item.text, textFont);
            mainItem.setLeading(8f);
            mainList1.add(mainItem);
        }
        ColumnText ct1 = new ColumnText(canvas);
        ct1.setSimpleColumn(xAddr, pageBottomY, xDataInfo, currentY2);
        ct1.addElement(mainList1);
        ct1.go();
        
        currentY2 -= 75;
        
        canvas.saveState();
        canvas.setColorFill(new BaseColor(200,200,200));
        canvas.rectangle(barX, currentY2, barWidth, barHeight);
        canvas.fill();
        canvas.restoreState();
        
        currentY2 += 17;
        
        String bar3 = "RINCIAN TRANSAKSI ([CURRFROM] - [CURRTO])";
        
        txt.writeParagraph(bar3, document, 178, pageBottomY, rightX, currentY2, canvas, arialBold, 10f, 1.2f);
        
        currentY2 -= 20;
        
        table3.setTotalWidth(new float[]{75, 360, 102});
        table3.setWidthPercentage(100);
        table3.setHeaderRows(1);
        table3.setSpacingBefore(10f);
        table3.setSpacingAfter(5f);
        
        PdfPCell header6 = new PdfPCell(new Phrase("Tanggal Transaksi", new Font(arialBold, 8.5f)));
        header6.setPaddingLeft(5);
        header6.setPaddingTop(5);
        header6.setPaddingRight(5);
        header6.setPaddingBottom(10);
        header6.setHorizontalAlignment(Element.ALIGN_CENTER);
        header6.setVerticalAlignment(Element.ALIGN_MIDDLE);
        header6.setBorder(Rectangle.BOTTOM);
        header6.setBorderWidthBottom(0.5f);
        
        PdfPCell header7 = new PdfPCell(new Phrase("Jenis Transaksi", new Font(arialBold, 8.5f)));
        header7.setPaddingLeft(5);
        header7.setPaddingTop(5);
        header7.setPaddingRight(5);
        header7.setPaddingBottom(10);
        header7.setHorizontalAlignment(Element.ALIGN_CENTER);
        header7.setVerticalAlignment(Element.ALIGN_BOTTOM);
        header7.setBorder(Rectangle.BOTTOM);
        header7.setBorderWidthBottom(0.5f);
        
        PdfPCell header8 = new PdfPCell(new Phrase("Jumlah Saldo", new Font(arialBold, 8.5f)));
        header8.setPaddingLeft(5);
        header8.setPaddingTop(5);
        header8.setPaddingRight(5);
        header8.setPaddingBottom(10);
        header8.setHorizontalAlignment(Element.ALIGN_CENTER);
        header8.setVerticalAlignment(Element.ALIGN_BOTTOM);
        header8.setBorder(Rectangle.BOTTOM);
        header8.setBorderWidthBottom(0.5f);
        
        table3.addCell(header6);
        table3.addCell(header7);
        table3.addCell(header8);
        
        List<String[]> listData = new ArrayList<>();
        listData.add(new String[]{"05 Des 2025", "Manfaat Tahunan Dijamin", "8.750.000,00"});
        
        for (String[] row : listData) {
            PdfPCell c1 = new PdfPCell(new Phrase(row[0], fontArial));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
            c1.setPaddingLeft(5);
            c1.setPaddingTop(16);
            c1.setPaddingRight(5);
            c1.setPaddingBottom(5);
            c1.setBorder(Rectangle.NO_BORDER);
            table3.addCell(c1);
                    
            PdfPCell c2 = new PdfPCell(new Phrase(row[1], fontArial));
            c2.setHorizontalAlignment(Element.ALIGN_LEFT);
            c2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            c2.setPaddingLeft(5);
            c2.setPaddingTop(16);
            c2.setPaddingRight(5);
            c2.setPaddingBottom(5);
            c2.setBorder(Rectangle.NO_BORDER);
            table3.addCell(c2);

            PdfPCell c3 = new PdfPCell(new Phrase(row[2], fontArial));
            c3.setHorizontalAlignment(Element.ALIGN_RIGHT);
            c3.setVerticalAlignment(Element.ALIGN_MIDDLE);
            c3.setPaddingLeft(5);
            c3.setPaddingTop(16);
            c3.setPaddingRight(5);
            c3.setPaddingBottom(5);
            c3.setBorder(Rectangle.NO_BORDER);
            table3.addCell(c3);

        }
        float leftX = 30;
        float rightXCol = 567;
        float bottomY = pageBottomY;

        ColumnText ctTable = new ColumnText(canvas);
        ctTable.setSimpleColumn(
                leftX,
                bottomY,
                rightXCol,
                currentY2
        );
        ctTable.addElement(table3);

        int status2 = ctTable.go();
        
        while (ColumnText.hasMoreText(status2)) {
            document.newPage();
            canvas = writer.getDirectContent();

            ctTable.setCanvas(canvas);
            ctTable.setSimpleColumn(
                    leftX,
                    bottomY,
                    rightXCol,
                    pageTopY   
            );

            status2 = ctTable.go();
        }
        
        document.close();


//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    class SaldoItem {
        String keterangan;
        String jumlah;

        SaldoItem(String keterangan, String jumlah){
            this.keterangan = keterangan;
            this.jumlah = jumlah;
        }
    }
    
    class SaldoGroup {
        String title;
        List<SaldoItem> items;
        String total;
        
        SaldoGroup(String title, List<SaldoItem> items, String total){
            this.title = title;
            this.items = items;
            this.total = total;
        }
    }
    
    class NumberItem {
        String text;
        List<String> subItems;
        
        NumberItem(String text){
            this.text = text;
            this.subItems = null;
        }
        
        NumberItem(String text, List<String> subItems){
            this.text = text;
            this.subItems = subItems;
        }
    }
    
    private static final String[] ROMAN =
        {"i", "ii", "iii", "iv", "v", "vi", "vii", "viii", "ix", "x"};

    
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
