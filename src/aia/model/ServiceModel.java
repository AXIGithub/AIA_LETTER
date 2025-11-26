/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model;

import aia.controller.PathDirectory;
import java.io.File;

/**
 *
 * @author Ratino
 */
public class ServiceModel {
    
    public void createDirInput(String path, String fileName, String product, String cycle){
        PathDirectory pd = new PathDirectory();        
        String newPath = pd.configurePath(path + cycle + "\\\\" + "HOLD" + "\\\\" + product + "\\\\" + "ZPO");
        new File(newPath).mkdirs();
        
        cutFileToNewDir(path + fileName, newPath + "\\\\" + "SPOOL-36-WIC " + product + " ZPO" + ".TXT");
    }
    
    public void cutFileToNewDir(String source, String target){
        File f1 = new File(source);
        File f2 = new File(target);
        f1.renameTo(f2);
    }
    
}
