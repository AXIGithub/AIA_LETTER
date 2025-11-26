/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model;

import aia.controller.Directory;
import aia.controller.PathDirectory;
import aia.controller.TextModification;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ratino
 */
public class CreateDirectoryModel {
    
    private TextModification txt = new TextModification();
    private String currentDirectory = new String();
    private String paperDirectory = new String();
    private String fontsDirectory = new String();
    private String tempDirectory = new String();
    private String readyToPrintCyDirectory = new String();
    private String readyToPrintCyPrintDirectory = new String();
    private String readyToPrintCyLogScanDirectory = new String();
    private String readyToPrintCyLogProdDirectory = new String();
    private String readyToPrintCyReportDirectory = new String();
    private String readyToPrintCySortingDirectory = new String();
    
    
//    public CreateDirectoryModel(String currentDirectory) {
//        this.currentDirectory = currentDirectory;
//        this.paperDirectory = this.currentDirectory + "\\" + "PAPER\\";
//    }
    

    
    public void createNewDirectory(String currdir, String cycle){        
            this.currentDirectory = currdir;
            String cycleData = "";
            //currentDirectory = ""+new java.io.File(".").getCanonicalFile();
            Date dt = new Date();
            Directory dir = new Directory();
            PathDirectory pd = new PathDirectory();
            
            String date = (dt.getYear() + 1900) + txt.norm2Digit(dt.getMonth() + 1) + txt.norm2Digit(dt.getDate());
            cycleData = (dt.getYear() + 1900) + txt.norm2Digit(dt.getMonth() + 1) + cycle;
            String dateTime = (dt.getYear() + 1900) + txt.norm2Digit(dt.getMonth() + 1) + txt.norm2Digit(dt.getDate()) + " " + txt.norm2Digit(dt.getHours()) + txt.norm2Digit(dt.getMinutes()) + txt.norm2Digit(dt.getSeconds());
            
            readyToPrintCyDirectory =  pd.configurePath(this.currentDirectory + "\\READY TO PRINT\\" + "CIMB CUSTODY " + cycleData );
            readyToPrintCyLogScanDirectory = pd.configurePath(readyToPrintCyDirectory + "\\LOG SCAN\\");
            readyToPrintCyLogProdDirectory = pd.configurePath(readyToPrintCyDirectory + "\\LOG PRODUKSI\\");
            readyToPrintCyPrintDirectory = pd.configurePath(readyToPrintCyDirectory + "\\OUTPUT\\");
            readyToPrintCyReportDirectory = pd.configurePath(readyToPrintCyDirectory + "\\REPORT\\");
            readyToPrintCySortingDirectory = pd.configurePath(readyToPrintCyDirectory + "\\SORTING\\");
            
            File f1 = new File(readyToPrintCyDirectory);
            File f2 = new File(readyToPrintCyLogScanDirectory);
            File f3 = new File(readyToPrintCyLogProdDirectory);
            File f4 = new File(readyToPrintCyPrintDirectory);
            File f5 = new File(readyToPrintCyReportDirectory);
            File f6 = new File(readyToPrintCySortingDirectory);
            
            f1.mkdirs();
            f2.mkdirs();
            f3.mkdirs();
            f4.mkdirs();
            f5.mkdirs();
            f6.mkdirs();
            System.out.println("Create Directory");
        
                
    }

    public String getPaperDirectory() {
        return paperDirectory;
    }

    public String getFontsDirectory() {
        return fontsDirectory;
    }

    public String getReadyToPrintCyDirectory() {
        return readyToPrintCyDirectory;
    }

    public String getReadyToPrintCyPrintDirectory() {
        return readyToPrintCyPrintDirectory;
    }

    public String getReadyToPrintCyLogScanDirectory() {
        return readyToPrintCyLogScanDirectory;
    }

    public String getReadyToPrintCyLogProdDirectory() {
        return readyToPrintCyLogProdDirectory;
    }

    public String getReadyToPrintCyReportDirectory() {
        return readyToPrintCyReportDirectory;
    }

    public String getReadyToPrintCySortingDirectory() {
        return readyToPrintCySortingDirectory;
    }

    
    
}
