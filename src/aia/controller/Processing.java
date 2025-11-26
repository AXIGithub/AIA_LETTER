/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.controller;

import com.itextpdf.text.DocumentException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Ratino
 */
public class Processing {
    Directory dir = new Directory();
    PathDirectory pd = new PathDirectory();
    
    private String directoryFont = new String();
    private String directoryPaper = new String();
    private String directoryReadyToPrint = new String();
    private String currentDirectory = new String();
    private String readyToPrintCyDirectory = new String();
    private String readyToPrintCyLogDirectory = new String();
    private String readyToPrintCyPrintDirectory = new String();
    private String readyToPrintCyReportDirectory = new String();
    private String readyToPrintCySortingDirectory = new String();
    
    
    public void createCustody(String[] params) throws DocumentException, FileNotFoundException, IOException{
        long startRun = System.currentTimeMillis();
        String product = params[3];
        String[] dirParams = {readyToPrintCyPrintDirectory, readyToPrintCyReportDirectory, readyToPrintCySortingDirectory,
                                readyToPrintCyLogDirectory};
        setCurrentDirectory(""+ new java.io.File(".").getCanonicalPath(), product);
        setDirectory(currentDirectory);
        
        
        
        long endRun = System.currentTimeMillis();
        long duration = endRun - startRun;
        System.out.println("All Duration : " + duration + " ms");
    }
    
    
    public void setCurrentDirectory(String currentDirectory, String product) throws IOException{
        Directory dir = new Directory();
        PathDirectory pd = new PathDirectory();
        this.currentDirectory = currentDirectory;
//        tempDirectory = pd.configurePath(this.currentDirectory + "\\" +"TEMP\\");
//        paperDirectory = currentDirectory + "\\" + "Paper" + "\\";
//        fontsDirectory = pd.configurePath(this.currentDirectory + "\\" + "FONTS\\");
        
        readyToPrintCyDirectory = this.currentDirectory + "\\READY TO PRINT" + "\\AIA LETTER " + product  + "\\";
        readyToPrintCyLogDirectory = pd.configurePath(readyToPrintCyDirectory + "\\LOG\\");
        readyToPrintCyPrintDirectory = pd.configurePath(readyToPrintCyDirectory + "\\OUTPUT\\");
        readyToPrintCyReportDirectory = pd.configurePath(readyToPrintCyDirectory + "\\REPORT\\");
        readyToPrintCySortingDirectory = pd.configurePath(readyToPrintCyDirectory + "\\SORTING\\");
//        File tempDir = new File(tempDirectory);
//        dir.deleteFolder2(tempDir);
        
        File f1 = new File(readyToPrintCyLogDirectory);
        File f2 = new File(readyToPrintCyPrintDirectory);
        File f3 = new File(readyToPrintCyReportDirectory);
//        File f4 = new File(tempDirectory);
        File f5 = new File(readyToPrintCyDirectory);
        f5.mkdirs();
        File f6 = new File(readyToPrintCySortingDirectory);
        
        f1.mkdirs();
        f2.mkdirs();
        f3.mkdirs();
//        f4.mkdirs();
        f6.mkdirs();
                
    }
    
    
    public void setDirectory(String currentDir) {        
        directoryFont = pd.configurePath(currentDir + "\\\\" + "FONTS\\\\");
        directoryPaper = pd.configurePath(currentDir + "\\\\" + "PAPER\\\\");
        directoryReadyToPrint = pd.configurePath(currentDir + "\\\\" + "READY TO PRINT\\\\");
    }
}
