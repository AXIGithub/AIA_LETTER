/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package aia.controller;

import java.io.File;

/**
 *
 * @author MishbahulM
 */
public class PathDirectory {
    public String configurePath(String path){
         int index = 0;
         int content = 0;
         int history = 0;
         boolean status = false;
         String temp = new String();
         String temp1 = new String();
         do{
            index   = path.indexOf("\\");
            if(index < 0){
                status = true;
            }
            else{
                temp = temp +  path.substring(0,index+1) + "\\";
                path = path.substring(index+1);
            }
         }while(status == false);

         temp = temp + path.substring(0);
         return temp;
     }
    
    /**
     * params Array
     * Add By Ratino 022024 sebagai function create directory
     * Membuat sebuah folder dengan path yang diberikan
     * @param directoryPath path lengkap directory yang ingin dibuat
     */
//    public static void createDirectory(String directoryPath){ // Cara 1
    public static void createDirectory(String[] params){ //Cara 2
        for(String param : params){
            File directory = new File(param);
            if(!directory.exists()){
                if(directory.mkdirs()){
                    System.out.println("Created : " + directory);
                } else {
                    System.out.println("Gagal : " + directory);
                }
            }
        }
        
    }

}
