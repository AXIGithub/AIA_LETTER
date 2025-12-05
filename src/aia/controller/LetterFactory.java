/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.controller;

import aia.poa_product.CreatePdfAPH;
import aia.poa_product.CreatePdfAPL;

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
            default:
                 throw new IllegalArgumentException("Template untuk produk " + product + " tidak ditemukan!\nHubungi IT");
        }
    }
}
