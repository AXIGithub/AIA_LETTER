/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Ratino
 */
public class ProductCode {
    
    public String getProductCode(String fileName){
        // kondisi untuk beberapa produk
        String code = fileName.split("_")[0];
        switch (code) {
            case "WARISAN":
                code = "PDFW";
                break;
            case "PDF":
                code = "PDFR";
                break;
            case "DISHONOR":
                code = "SDHN";
                break;
            case "EPTLTH":
                code = "EPT";
                break;
            case "SURATMEDIX":
                code = "MEDIX";
                break;
            case "WDN-C":
                code = "WDNC";
                break;
            case "WDN-N":
                code = "WDNN";
                break;
            case "WDN-W":
                code = "WDNW";
                break;
            default:
                break;
        }
        return code;
    }
    
    public String getDescriptionProduct(String productCode){
        switch(productCode.toUpperCase()){
            case "ACD":
                return "Acquisition Cost Discount";
            case "ANRPE":
                return "Gaada perihal di letter code";
            case "ANST":
                return "Gaada perihal di letter code";
            case "CL":
                return "Pembayaran Klaim Polis";
            case "CVT":
                return "Laporan Tahunan";
            case "ESAM":
                return "ESAM Letter";
            case "ESTM":
                return "Waiver Letter";
            case "EXPY":
                return "EXPY";
            case "KPNT":
                return "KPNT Letter";
            case "KPNT2":
                return "KPNT2 Letter";
            case "MPH":
                return "Laporan Tahunan";
            case "NCB":
                return "NCB";
            case "ORP":
                return "Status Tenaga Pemasar";
            case "POTNR":
                return "POTNR Letter";
            case "PRTNR":
                return "PRTNR Letter";
            case "RAB":
                return "Laporan Tahunan";
            case "UBC":
                return "UBC Letter";
            case "SPN":
                return "Konfirmasi Titipan Premi/Kontribusi";
            
            // New Business product
            case "WDNC":
                return "Pembatalan Surat Pengajuan Asuransi";
            case "WDNN":
                return "Pembatalan Surat Pengajuan Asuransi";
            case "WDNW":
                return "Pembatalan Surat Pengajuan Asuransi";
                
            // POA product
            case "APH":
                return "Status Polis Menjadi Cuti Premi/Kontribusi Otomatis";
            case "APL":
                return "Penggunaan Fasilitas Pinjaman Polis Otomatis";
            case "BTLCR":
                return "Konfirmasi Pengajuan Pembatalan Polis";
            case "DVD1":
                return "Konfirmasi Nilai Dividen yang didapat";
            case "EPML":
                return "Konfirmasi Pemulihan Sudah Dilakukan (Manual Endorsment)";
            case "EPRB":
                return "Konfirmasi Perubahan Sudah Dilakukan (Manual Endorsment)";
            case "KPDI1":
                return "Konfirmasi Pengembalian Dana Investasi 1";
            case "KPPA":
                return "Konfirmasi Penghentian Cuti Premi Otomatis";
            case "LANF":
                return "Pemberitahuan Status Polis Tidak Aktif";
            case "LIUD":
                return "Pemberitahuan Status Polis Menjadi Tidak Aktif";
            case "MAAMA":
                return "Pemberitahuan Masa Asuransi Polis Berakhir";
            case "MAPM":
                return "Pemberitahuan Berakhirnya Masa Pembayaran Premi";
            case "NAD":
                return "Perubahan Alamat Korespondensi";
            case "NFUL":
                return "Pembayaran Premi/Kontribusi Tidak Berhasil didebit";
            case "OAD":
                return "Perubahan Alamat Korespondensi";
            case "PAPH":
                return "Konfirmasi Penghentian Cuti Premi Otomatis";
            case "PDFR":
                return "Laporan Transaksi Premi Rezeki Family";
            case "PDFW":
                return "Pemberitahuan Saldo Premi Deposit";
            case "PMRC":
                return "Pengambilan Manfaat Asuransi/Dana Investasi";
            case "PTP":
                return "Pemberitahuan Kelebihan Pembayaran Premi";
            case "ROP":
                return "Manfaat Pengembalian Premi Akhir Tahun Polis";
            case "SPNP":
                return "Pengambilan Titipan Premi/Kontribusi";
            case "SRN1":
                return "Pemberitahuan Jatuh Tempo 1";
            case "SRN2":
                return "Pemberitahuan Jatuh Tempo 2";
            case "SSNA":
                return "Sisa Nilai Akun";
            case "THP1":
                return "Konfirmasi Nilai Manfaat Bertahap yang didapat";
            case "TUTP":
                return "Konfirmasi Nilai Manfaat Bertahap yang didapat (Ulang Tahun Polis)";
            default :
                return "";
        }
    }
    
    public static final Set<String> POA_PRODUCTS = new HashSet<>(
        Arrays.asList(
            "APH","APL","BTLCR","DVD1","EPML","EPRB",
            "KPDI1","KPDI2","KPPA","LANF","LIUD",
            "MAAMA","MAPM","NAD","NFUL","OAD",
            "PAPH","PDFR","PDFW","PMRC","PTP","ROP","SPNP",
            "SRN1","SRN2","SSNA","THP1","TUTP",
            "PDFR","PDFW"
        )
    );
    
    public static final Set<String> NB_PRODUCTS = new HashSet<>(
        Arrays.asList(
            "WDNC","WDNN","WDNW"
        )
    );

}
