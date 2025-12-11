package utils;

import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ratino
 */
public class DbfConverter {
        
    public String convertDbfToText(String pathIn) {

        String dbfPath = pathIn;
        String outputPath = pathIn.replace(".dbf", ".txt");

        try (
            InputStream inputStream = new FileInputStream(dbfPath);
            DBFReader reader = new DBFReader(inputStream);
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath));
        ) {

            int numberOfFields = reader.getFieldCount();

            // Write header
            for (int i = 0; i < numberOfFields; i++) {
                writer.write(reader.getField(i).getName());
                if (i < numberOfFields - 1) writer.write("\t");
                System.out.print(reader.getField(i).getName() + " ");
            }
            writer.newLine();

            
//            DBFRow rowDBF;
//            while ((rowDBF = reader.nextRow()) != null) {
//                System.out.println("Row DBF : " + rowDBF);
//            }
            
            Object[] row;
            while ((row = reader.nextRecord()) != null) {
                System.out.println("ROW : " + row.length);
                for (int i = 0; i < row.length; i++) {
                    System.out.println(i + ". " +row[i].toString().trim());
                    writer.write(row[i] != null ? row[i].toString().trim() : "");
                    if (i < row.length - 1) writer.write("\t");
                }
                writer.write("\n");
//                writer.newLine();
            }
            
        } catch (Exception e) {
            System.out.println("Error convert");
            e.printStackTrace();
        }
        return outputPath;
    }
}
