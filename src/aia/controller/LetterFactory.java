/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.controller;

import aia.poa_product.CreatePdfACD;
import aia.poa_product.CreatePdfACM;
import aia.poa_product.CreatePdfAPH;
import aia.poa_product.CreatePdfAPL;
import aia.poa_product.CreatePdfKPDI1;

/**
 *
 * @author Ratino
 */
public class LetterFactory {
   
    public static BasePdfGenerator getPdfTemplate(String product){
        if(product == null) return null;
        switch (product.toUpperCase()){
            case "APH":
                return new CreatePdfAPH();
            case "APL":
                return new CreatePdfAPL();
            case "ACM":
                return new CreatePdfACM();
            case "ACD":
                return new CreatePdfACD();
            case "KPDI1":
                return new CreatePdfKPDI1();
            default:
                 throw new IllegalArgumentException("Template untuk produk " + product + " tidak ditemukan!\nHubungi IT");
        }
    }
}
