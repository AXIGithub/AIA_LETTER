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
            case "APH":
                return "Status Polis Menjadi Cuti Premi/Kontribusi Otomatis";
            case "APL":
                return "Penggunaan Fasilitas Pinjaman Polis Otomatis";
            case "KPDI1":
                return "Konfirmasi Pengembalian Dana Investasi 1";
            default :
                return "";
        }
    }
}
