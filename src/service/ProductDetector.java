/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.File;

/**
 *
 * @author Ratino
 */
public class ProductDetector {
    public static String detect(String path){
        String file = new File(path).getName().toUpperCase();
        if(file.contains("APH")) return "APH";
        if (file.contains("BTLCR")) return "BTLCR";
        if (file.contains("EPML")) return "EPML";
        
        throw new IllegalArgumentException("Product tidak dikenal : " + file);
    }
}
