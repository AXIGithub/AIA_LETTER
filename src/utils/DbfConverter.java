package utils;

import com.linuxense.javadbf.DBFReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;

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
            }
            writer.newLine();

            Object[] row;
            while ((row = reader.nextRecord()) != null) {

                for (int i = 0; i < row.length; i++) {
                    writer.write(row[i] != null ? row[i].toString().trim() : "");
                    if (i < row.length - 1) writer.write("\t");
                }
                writer.newLine();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputPath;
    }
}
