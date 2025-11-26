/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package aia.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JFrame;
import java.awt.*;
import javax.*;
import java.io.*;
import java.util.Date;
import javax.swing.JFileChooser;

/**
 *
 * @author Administrator
 */
public class FileChooser extends JDialog {

    private String directoryPath    = new String();
    private String fileName         = new String();

    private int idCycle   = 0;
    private int idCompany = 0;
    private String fileDescription = new String();
    //private LogFile logFile;
    
    public FileChooser() // contructor
    {

    }

    

    public String namaFile() throws IOException // Method untuk membuka file
    {
        //Buka alamat file (absolut direktory)
        //String value = ""; //pendeklrasian
        //String valueBeta = ""; //pendeklarasian
        FileDialog data = new FileDialog(this,"Open"); //Dialog Open Data
        data.show(); // Menampilkan Dialog Open
        String nm = data.getFile(); //Mengambil nama file pada saat tbl open diklik
        String dir = data.getDirectory(); // mengambil alamat direktori saat tbl open diklik
        String path = dir.concat(nm); // penggabungan alamat direktori dng nama file
        setDirectoryPath(path);
        //end Buka alamat file

        /*
        RandomAccessFile file = null; // pendeklarasian
        file = new RandomAccessFile(path,"rw"); //buka file dng ket red write
        char kar; // pendeklarasian

        file.seek(0); //menunjuk ke awal text pada file
        while(file.getFilePointer() < file.length()) //membaca karakter dari file dan tampung ke value
        {
            kar =  (char) file.readByte();
            valueBeta = new String( String.valueOf(kar));
            value = value + valueBeta;
        }

        return(value); */
        setFileName(nm);
        return(path);
    }
    
    public String selectDirectory() throws IOException {
        String pathDir = "";
        JFileChooser chooser = new JFileChooser(""+new java.io.File(".").getCanonicalPath()+"\\INPUT\\");
        chooser.setDialogTitle("Select Folder");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);        
        int returnValue = chooser.showDialog(null, "SELECT");
        if(returnValue == JFileChooser.APPROVE_OPTION){
            File selectedFolder = chooser.getSelectedFile();
            pathDir = selectedFolder.getAbsolutePath();
            setDirectoryPath(pathDir);
        }
        return pathDir;
    }

    public String getDirectory() throws IOException // Method untuk membuka file
    {
        //Buka alamat file (absolut direktory)
        //String value = ""; //pendeklrasian
        //String valueBeta = ""; //pendeklarasian
        FileDialog data = new FileDialog(this,"Open"); //Dialog Open Data
        data.show(); // Menampilkan Dialog Open
        String nm = data.getFile(); //Mengambil nama file pada saat tbl open diklik
        String dir = data.getDirectory(); // mengambil alamat direktori saat tbl open diklik
        String path = dir.concat(nm); // penggabungan alamat direktori dng nama file
        setDirectoryPath(path);
        //end Buka alamat file

        /*
        RandomAccessFile file = null; // pendeklarasian
        file = new RandomAccessFile(path,"rw"); //buka file dng ket red write
        char kar; // pendeklarasian

        file.seek(0); //menunjuk ke awal text pada file
        while(file.getFilePointer() < file.length()) //membaca karakter dari file dan tampung ke value
        {
            kar =  (char) file.readByte();
            valueBeta = new String( String.valueOf(kar));
            value = value + valueBeta;
        }

        return(value); */
        setFileName(nm);
        return(dir);
    }

    public String readFile(String path) throws IOException{
        String value = ""; //pendeklrasian
        String valueBeta = ""; //pendeklarasian
        
        RandomAccessFile file = null; // pendeklarasian
        file = new RandomAccessFile(path,"rw"); //buka file dng ket red write
        char kar; // pendeklarasian
        file.seek(0); //menunjuk ke awal text pada file
        int index = 0;
        Date date = new Date();
        System.out.println("LogFile_ Start : " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds());
        
        while(file.getFilePointer() < file.length()) //membaca karakter dari file dan tampung ke value
        {
            index++;
            
            //kar =  (char) file.readByte();
            //valueBeta = new String(String.valueOf(file.read()));
            valueBeta = file.readLine();
            //value = value + valueBeta;
            
            /*if(index == 1){
                logFile = new LogFile(idCompany, idCycle, fileDescription, valueBeta, getFileName());
                logFile.readHeader(valueBeta);
            }
            else{
                logFile.setData(valueBeta);
                logFile.importLogFile();
            }*/
            //System.out.println("File : " + index);
        }
        date = new Date();
        System.out.println("File_ Finish : " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds());
        System.out.println("File : " + index);
        return(value);
    }

    public void saveFile(String str) //Method untuk menyimpan file
    {
        //Buka alamat file (absolut direktory)
        String value = ""; //pendeklarasi
        String valueBeta = "";//pendeklarasian
        FileDialog data = new FileDialog(this,"Save",FileDialog.SAVE); // Dialog Save Data
        data.show(); // menampilkan Dialog Save Data
        String ext = data.getFile();
        int note = ext.indexOf(".");
        String info = ext.substring(note+1);
        String nm = new String();
        if(info.compareTo("cgt")==0){
        	nm = data.getFile(); //Mengambil nama file pada saat tbl open diklik
        }
        else{
        	nm = data.getFile()+".cgt"; //Mengambil nama file pada saat tbl open diklik
        }

        String dir = data.getDirectory();  //Mengambil nama direktori pada saat tbl open diklik
        String path = dir.concat(nm);  // penggabungan alamat direktori dng nama file
        //end Buka alamat file

        try
        {
            BufferedWriter out = new BufferedWriter(new FileWriter(path)); // pembuatan objek simpan data
            out.write(str); // menyalin str ke file yang akan disimpan
            out.close(); // menutup file
        }
        catch(IOException e)
        {

        }

    }
    
    public void moveFile(String in, String out){
        File input = new File(in);
        File output = new File(out);
        input.renameTo(output);        
    }
    
    public boolean fileExists(String namaFile){
        // Get the file 
        File f = new File(namaFile); 
  
        // Check if the specified file
        // Exists or not
        return f.exists();
    }

    public String getDirectoryPath() {
        return directoryPath;
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDescription() {
        return fileDescription;
    }

    public void setFileDescription(String fileDescription) {
        this.fileDescription = fileDescription;
    }

    public int getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(int idCompany) {
        this.idCompany = idCompany;
    }

    public int getIdCycle() {
        return idCycle;
    }

    public void setIdCycle(int idCycle) {
        this.idCycle = idCycle;
    }

    

}  // End Of class File buatan
