/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package aia.controller;
import com.itextpdf.text.pdf.PdfName;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
//import omrvalidation.controller.TextModification;
/**
*
* @author SandryR
*/
public class Directory {
    static int indentLevel = -1;
    private String pathDirectory = new String();
    private int monitoringTime = 24;
    private ArrayList<String> fileName             = new ArrayList<String>(10);
    private ArrayList<Long> fileSize             = new ArrayList<Long>(10);
    private TextModification txt = new TextModification();
    
    public void scanLogScanFolder(String directory) throws IOException{
        //File f = new File("D:\\Permata Billing\\Input\\"); // current directory
        PathDirectory fldr = new PathDirectory();
        boolean ds = false;
        Date dt = new Date();
        int yr = dt.getYear() + 1900;
        int mt = dt.getMonth() + 1;

        File f = new File(fldr.configurePath(directory)); // current directory
        //long fileSize = 0;
        File[] files = f.listFiles();
        int i = 0;
        for (File file : files) {
            if(file.getName().toUpperCase().endsWith(".TXT") || file.getName().toUpperCase().endsWith(".PDF")){
                fileName.add(file.getName());
                fileSize.add(file.length());
                i++;
                //System.out.println("Jumlah FIle PDF : " + i);
           }
        }
        indentLevel--;
    }
    
    public void scanZipFile(String directory) throws IOException{
        //File f = new File("D:\\Permata Billing\\Input\\"); // current directory
        PathDirectory fldr = new PathDirectory();
        boolean ds = false;
        Date dt = new Date();
        int yr = dt.getYear() + 1900;
        int mt = dt.getMonth() + 1;

        File f = new File(fldr.configurePath(directory)); // current directory
        //long fileSize = 0;
        File[] files = f.listFiles();
        int i = 0;
        for (File file : files) {
           if(file.getName().toUpperCase().endsWith(".ZIP")){
                fileName.add(file.getName());
                fileSize.add(file.length());
                i++;
                //System.out.println("Jumlah FIle PDF : " + i);
           }
        }
        indentLevel--;
    }
    
    public void scanXlsFile(String directory) throws IOException{
        //File f = new File("D:\\Permata Billing\\Input\\"); // current directory
        PathDirectory fldr = new PathDirectory();
        boolean ds = false;
        Date dt = new Date();
        int yr = dt.getYear() + 1900;
        int mt = dt.getMonth() + 1;

        File f = new File(fldr.configurePath(directory)); // current directory
        //long fileSize = 0;
        File[] files = f.listFiles();
        int i = 0;
        for (File file : files) {
            if(file.getName().toUpperCase().endsWith(".XLS") || file.getName().toUpperCase().endsWith(".XLSX")){
                fileName.add(file.getName());
                fileSize.add(file.length());
                i++;
                System.out.println("xls : " + file.getName());            
            }
        }
        indentLevel--;
    }

    public void scanPgpFile(String directory) throws IOException{
        //File f = new File("D:\\Permata Billing\\Input\\"); // current directory
        PathDirectory fldr = new PathDirectory();
        boolean ds = false;
        Date dt = new Date();
        int yr = dt.getYear() + 1900;
        int mt = dt.getMonth() + 1;

        File f = new File(fldr.configurePath(directory)); // current directory
        //long fileSize = 0;
        File[] files = f.listFiles();
        int i = 0;
        for (File file : files) {
           if(file.getName().toUpperCase().endsWith(".PGP")){
                fileName.add(file.getName());
                fileSize.add(file.length());
                i++;
                //System.out.println("Jumlah FIle PDF : " + i);
           }
        }
        indentLevel--;
    }
    public void scanPngFile(String directory) throws IOException{
        //File f = new File("D:\\Permata Billing\\Input\\"); // current directory
        PathDirectory fldr = new PathDirectory();
        boolean ds = false;
        Date dt = new Date();
        int yr = dt.getYear() + 1900;
        int mt = dt.getMonth() + 1;

        File f = new File(fldr.configurePath(directory)); // current directory
        //long fileSize = 0;
        File[] files = f.listFiles();
        int i = 0;
        for (File file : files) {
           if(file.getName().toUpperCase().endsWith(".PNG")){
                fileName.add(file.getName());
                fileSize.add(file.length());
                i++;
                //System.out.println("Jumlah FIle PDF : " + i);
           }
        }
        indentLevel--;
    }

    public void scanPdfFile(String directory) throws IOException{
        //File f = new File("D:\\Permata Billing\\Input\\"); // current directory
        PathDirectory fldr = new PathDirectory();
        boolean ds = false;
        Date dt = new Date();
        int yr = dt.getYear() + 1900;
        int mt = dt.getMonth() + 1;

        File f = new File(fldr.configurePath(directory)); // current directory
        //long fileSize = 0;
        File[] files = f.listFiles();
        int i = 0;
        for (File file : files) {
           if(file.getName().toUpperCase().endsWith(".PDF")){
                fileName.add(file.getName());
                fileSize.add(file.length());
                i++;
                //System.out.println("Jumlah FIle PDF : " + i);
           }
        }
        indentLevel--;
    }

    public void scanTextFile(String directory) throws IOException{
        //File f = new File("D:\\Permata Billing\\Input\\"); // current directory
        PathDirectory fldr = new PathDirectory();
        boolean ds = false;
        Date dt = new Date();
        int yr = dt.getYear() + 1900;
        int mt = dt.getMonth() + 1;

        File f = new File(fldr.configurePath(directory)); // current directory
        //long fileSize = 0;
        File[] files = f.listFiles();
        int i = 0;
        for (File file : files) {
//           if(file.getName().indexOf(".txt")>-1 || file.getName().indexOf(".TXT")>-1 || file.getName().indexOf(".Txt")>-1){
           if(file.getName().toUpperCase().endsWith(".TXT")){
                fileName.add(file.getName());
                fileSize.add(file.length());
                i++;
                //System.out.println("Jumlah FIle PDF : " + i);
           }
        }
        indentLevel--;
    }
    
    public void scanLogtFile(String directory) throws IOException{
        //File f = new File("D:\\Permata Billing\\Input\\"); // current directory
        PathDirectory fldr = new PathDirectory();
        boolean ds = false;
        Date dt = new Date();
        int yr = dt.getYear() + 1900;
        int mt = dt.getMonth() + 1;

        File f = new File(fldr.configurePath(directory)); // current directory
        //long fileSize = 0;
        File[] files = f.listFiles();
        int i = 0;
        for (File file : files) {
//           if(file.getName().indexOf(".txt")>-1 || file.getName().indexOf(".TXT")>-1 || file.getName().indexOf(".Txt")>-1){
           if(file.getName().toUpperCase().endsWith(".LOG")){
                fileName.add(file.getName());
                fileSize.add(file.length());
                i++;
                //System.out.println("Jumlah FIle PDF : " + i);
           }
        }
        indentLevel--;
    }


    public void scanMrdFile(String directory) throws IOException{
        //File f = new File("D:\\Permata Billing\\Input\\"); // current directory
        PathDirectory fldr = new PathDirectory();
        boolean ds = false;
        Date dt = new Date();
        int yr = dt.getYear() + 1900;
        int mt = dt.getMonth() + 1;

        File f = new File(fldr.configurePath(directory)); // current directory
        //long fileSize = 0;
        File[] files = f.listFiles();
        int i = 0;
        for (File file : files) {
//           if(file.getName().indexOf(".mrd")>-1 || file.getName().indexOf(".MRD")>-1 || file.getName().indexOf(".Mrd")>-1){
           if(file.getName().toUpperCase().endsWith(".MRD")){
                fileName.add(file.getName());
                fileSize.add(file.length());
                i++;
                //System.out.println("Jumlah FIle PDF : " + i);
           }
        }
        indentLevel--;
    }

    
    public void scanIntFile(String directory) throws IOException{
        //File f = new File("D:\\Permata Billing\\Input\\"); // current directory
        PathDirectory fldr = new PathDirectory();
        boolean ds = false;
        Date dt = new Date();
        int yr = dt.getYear() + 1900;
        int mt = dt.getMonth() + 1;

        File f = new File(fldr.configurePath(directory)); // current directory
        //long fileSize = 0;
        File[] files = f.listFiles();
        int i = 0;
        System.out.println(directory);
        for (File file : files) {
//           if(file.getName().indexOf(".int")>-1 || file.getName().indexOf(".INT")>-1){
           if(file.getName().toUpperCase().endsWith(".INT")){
               fileName.add(file.getName());
                fileSize.add(file.length());
                i++;
                //System.out.println("Jumlah FIle PDF : " + i);
           }
        }
        indentLevel--;
    }

    public void scanJpgFile(String directory) throws IOException{
        //File f = new File("D:\\Permata Billing\\Input\\"); // current directory
        PathDirectory fldr = new PathDirectory();
        boolean ds = false;
        Date dt = new Date();
        int yr = dt.getYear() + 1900;
        int mt = dt.getMonth() + 1;

        File f = new File(fldr.configurePath(directory)); // current directory
        //long fileSize = 0;
        File[] files = f.listFiles();
        int i = 0;
        for (File file : files) {
//           if(file.getName().indexOf(".jpg")>-1 || file.getName().indexOf(".JPG")>-1){
           if(txt.rightStr(file.getName(), 4).toUpperCase().indexOf(".JPG")>-1){
                fileName.add(file.getName());
                fileSize.add(file.length());
                i++;
                //System.out.println("Jumlah FIle PDF : " + i);
           }
        }
        indentLevel--;
    }

    public static long getFolderSize(File dir) {
    long size = 0;
    for (File file : dir.listFiles()) {
        if (file.isFile()) {
            // System.out.println(file.getName() + " " + file.length());
            size = size +  (file.length());
        } else
            size = size + getFolderSize(file);
        //System.out.println("Delete Status 1: " + file.delete());
    }
        return size;
    }

    public static boolean deleteFolder(File dir) throws IOException{
        long size = 0;
        boolean deleteStatus = false;
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                //System.out.println(file.getName() + " " + file.length());
                //size = size +  (file.length());
            } else
                deleteFolder(file);
            deleteStatus = file.delete();
        }
        return (deleteStatus);
    }
    
    public String getFolderName(String path, String findName){
        File folder = new File(path);
        String folderName = "";
        File[] listOfFoler = folder.listFiles(File::isDirectory);
        if(listOfFoler != null){
            for(File subfoler : listOfFoler){
                if(subfoler.getName().contains(findName)){
                    folderName = subfoler.getName();
                }
            }
        }
        return folderName;
    }

    public String currentTime(){
        Calendar cal  = Calendar.getInstance();
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.MEDIUM);
        return(df.format(cal.getTime()));
    }
    
    public String getDriveLetter(){
        File file = new File(".").getAbsoluteFile();
        File root = file.getParentFile();
        while (root.getParentFile() != null) {
            root = root.getParentFile();
        }
        System.out.println("Drive is: "+root.getPath());
        
        return (root.getPath());
    }
    
    static void deleteFolder(String folderPath){
        File folder = new File(folderPath);
        if(folder.exists() && folder.isDirectory()){
            File[] files = folder.listFiles();
            if(files != null) {
                for(File file : files){
                    if(file.isDirectory()){
                        deleteFolder(file.getAbsolutePath());
                    } else {
                        file.delete();
                    }
                }
            }
            folder.delete();
            System.out.println("Sucess Delete");
        } else {
            System.out.println("Gagal Delete");
        }
    }
    
    public void createArray(){
        
    }
    
    public void emptyArryFileName(){
       fileName.clear();
    }

    public ArrayList<String> getFileName() {
        return fileName;
    }

    public void setFileName(ArrayList<String> fileName) {
        this.fileName = fileName;
    }

    public static int getIndentLevel() {
        return indentLevel;
    }

    public static void setIndentLevel(int indentLevel) {
        Directory.indentLevel = indentLevel;
    }

    public int getMonitoringTime() {
        return monitoringTime;
    }

    public void setMonitoringTime(int monitoringTime) {
        this.monitoringTime = monitoringTime;
    }

    public String getPathDirectory() {
        return pathDirectory;
    }

    public void setPathDirectory(String pathDirectory) {
        this.pathDirectory = pathDirectory;
    }

    public ArrayList<Long> getFileSize() {
        return fileSize;
    }

    public void setFileSize(ArrayList<Long> fileSize) {
        this.fileSize = fileSize;
    }
    
   

}