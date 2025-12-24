/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Ratino
 */
public class ProductCode {
    
    
    public String getProductCode(String fileName){
        return fileName.split("_")[0];
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
            case "POTNR":
                return "POTNR Letter";
            case "RAB":
                return "Laporan Tahunan";
            case "SPN":
                return "Konfirmasi Titipan Premi/Kontribusi";
            case "WDNC":
                return "Pembatalan Surat Pengajuan Asuransi";
            case "WDNN":
                return "Pembatalan Surat Pengajuan Asuransi";
            case "WDNW":
                return "Pembatalan Surat Pengajuan Asuransi";
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
}
