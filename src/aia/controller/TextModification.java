/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package aia.controller;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import static com.itextpdf.text.pdf.PdfName.S;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author SandryR
 */
public class TextModification {
private final int threshold = 1;
private static final String LINEBREAK = "\n"; // or "\r\n";
private static final String[] ANGKA = {"", "satu", "dua", "tiga", "empat", "lima", "enam", "tujuh", "delapan", "sembilan", "sepuluh", "sebelas"};

    //Code lama
//    public String generateSpace(String text, int length){
//        String currentText = text;
//        for(int i=text.length(); i<length; i++){
//            currentText = currentText + " ";
//        }
//        return currentText;
//    }
    // Code Baru
    public String generateSpace(String text, int length) {
        return String.format("%-" + length + "s", text);
    }

    
    
    public String wrap(String string, int lineLength) {
        StringBuilder b = new StringBuilder();
        for (String line : string.split(Pattern.quote(LINEBREAK))) {
            b.append(wrapLine(line, lineLength));
        }
        return b.toString();
    }

    public String wrapLine(String line, int lineLength) {
        if (line.length() == 0) return LINEBREAK;
        if (line.length() <= lineLength) return line + LINEBREAK;
        String[] words = line.split(" ");
        StringBuilder allLines = new StringBuilder();
        StringBuilder trimmedLine = new StringBuilder();
        for (String word : words) {
            if (trimmedLine.length() + 1 + word.length() <= lineLength) {
                trimmedLine.append(word).append(" ");
            } else {
                allLines.append(trimmedLine).append(LINEBREAK);
                trimmedLine = new StringBuilder();
                trimmedLine.append(word).append(" ");
            }
        }
        if (trimmedLine.length() > 0) {
            allLines.append(trimmedLine);
        }
        allLines.append(LINEBREAK);
        return allLines.toString();
    }

    public String convertTimeTo24Hours(String tm){
        String time = tm;

        //09/18/2015 09:48:50 PM
        if(tm.length()==22){
            int hour = Integer.valueOf(tm.substring(11,13)).intValue();
            String dy = tm.substring(20);

            if(dy.equalsIgnoreCase("PM")){
                hour = convertHour(hour);
            }
            time = tm.substring(0,11) + norm2Digit(hour) + tm.substring(13,tm.length()-2);
        }

        return time;
    }

    public int getDate(String dateTime){
        return Integer.valueOf(dateTime.substring(0,2)).intValue();
    }

    public int getMonth(String dateTime){
        return Integer.valueOf(dateTime.substring(3,5)).intValue();
    }

    public int getYear(String dateTime){
        return Integer.valueOf(dateTime.substring(6,10)).intValue();
    }

    public int getHour(String dateTime){
        return Integer.valueOf(dateTime.substring(11,13)).intValue();
    }

    public int getMinute(String dateTime){
        return Integer.valueOf(dateTime.substring(14,16)).intValue();
    }

    public int getSecond(String dateTime){
        return Integer.valueOf(dateTime.substring(17,19)).intValue();
    }


    public int convertHour(int hours){
        int hour = hours;
        if(hours == 1){
            hour = 13;
        }
        else if(hours == 2){
            hour = 14;
        }
        else if(hours == 3){
            hour = 15;
        }
        else if(hours == 4){
            hour = 16;
        }
        else if(hours == 5){
            hour = 17;
        }
        else if(hours == 6){
            hour = 18;
        }
        else if(hours == 7){
            hour = 19;
        }
        else if(hours == 8){
            hour = 20;
        }
        else if(hours == 9){
            hour = 21;
        }
        else if(hours == 10){
            hour = 22;
        }
        else if(hours == 11){
            hour = 23;
        }
        else if(hours == 12){
            hour = 24;
        }
        return hour;
    }

    public String normalizationDigitNumber(String number){
        String norm = new String();
        int num = Integer.valueOf(number).intValue();
        if(num < 10){
            norm = "0" +num;
        }
        else{
            norm = "" + num;
        }
        return(norm);
    }

    public String norm4Digit(int value){
        String text = new String();
        if(value < 10){
            text = "000" + value;
        }
        else if(value < 100){
            text = "00" + value;
        }
        else if(value < 1000){
            text = "0" + value;
        }
        else {
            text = "" + value;
        }
        return text;
    }

    public String generateDir(String url){
        PathDirectory pd = new PathDirectory();
        String dir          = url;
        String generatedDir = new String();
        int first        = 0;
        int last         = 0;
        String currentText  = new String();
        String lastText     = dir;
        boolean status      = false;
        System.out.println("url : " + dir);
        do{
            last = lastText.indexOf("\\");
            //System.out.println("Last Text : " + lastText);
            if(last>-1){
                currentText = currentText + lastText.substring(0,last+1);
                lastText = lastText.substring(last+1);
                status = true;
            }
            else{
                status = false;
            }
        }while(status == true);
        //System.out.println("Current Text : " + currentText);
        return(currentText);
    }

    public String getPeriodeReport(){
        String periode = new String();
        int prevMonth = 0;
        int currentMonth =0;
        int firstDate = 0;
        int lastDate = 0;

        Date dt = new Date();
        prevMonth = 0;
        currentMonth = dt.getMonth();

        if(currentMonth == 12){
            currentMonth = 1;
        }
        else{
            currentMonth++;
        }
        if(currentMonth == 1){
            prevMonth = 12;
        }
        else{
            prevMonth = currentMonth-1;
        }
        
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.set(Calendar.DATE, 1);
        aCalendar.add(Calendar.DAY_OF_MONTH, -1);
        String lastDateOfPreviousMonth = "" + aCalendar.getTime();
        aCalendar.set(Calendar.DATE, 1);
        String firstDateOfPreviousMonth = "" + aCalendar.getTime();
        String prevMonthConversion = convertDate(prevMonth, dt.getYear()+1900);
        //System.out.println("First Date : " + firstDateOfPreviousMonth.substring(8, 10));
        //System.out.println("Last Date : " + lastDateOfPreviousMonth.substring(8, 10));
        periode = firstDateOfPreviousMonth.substring(8,10) + " " + prevMonthConversion + " - " + lastDateOfPreviousMonth.substring(8, 10) + " " + prevMonthConversion;

        return periode;
    }

    public String normDecimal(String value){
        if(value.indexOf(",") == 0 || value.indexOf(".") == 0){
            value = "0" + value;
        }
        return value;
    }

    public String getPeriodeReportLast(String month){
        String periode = new String();
        int prevMonth = 0;
        int currentMonth =0;
        int firstDate = 0;
        int lastDate = 0;

        Date dt = new Date();
        prevMonth = 0;
        currentMonth = dt.getMonth();

        if(currentMonth == 12){
            currentMonth = 1;
        }
        else{
            currentMonth++;
        }
        if(currentMonth == 1){
            prevMonth = 12;
        }
        else{
            prevMonth = currentMonth-2;
        }

        Calendar aCalendar = Calendar.getInstance();
        aCalendar.set(Calendar.DATE, 1);
        aCalendar.add(Calendar.DAY_OF_MONTH, -1);
        String lastDateOfPreviousMonth = "" + aCalendar.getTime();
        aCalendar.set(Calendar.DATE, 1);
        String firstDateOfPreviousMonth = "" + aCalendar.getTime();
        String prevMonthConversion = convertDate(prevMonth, dt.getYear()+1900);
        periode = lastDateOfPreviousMonth.substring(8, 10) + " " + getPreviousMonthName2(month);
        
        return periode;
    }

    public String getPeriodeReport(String month, int years){
        String periode = new String();
        int prevMonth = 0;
        int currentMonth =0;
        int firstDate = 0;
        int lastDate = 0;
        System.out.println("STRINGNYA : " + month + " - " + years);
        Date dt = new Date();
        prevMonth = 0;
        currentMonth = dt.getMonth();

        if(currentMonth == 12){
            currentMonth = 1;
        }
        else{
            currentMonth++;
        }
        if(currentMonth == 1){
            prevMonth = 12;
        }
        else{
            prevMonth = currentMonth-1;
        }

        Calendar aCalendar = Calendar.getInstance();
        aCalendar.set(Calendar.DATE, 1);
        aCalendar.add(Calendar.DAY_OF_MONTH, -1);
        String lastDateOfPreviousMonth = "" + aCalendar.getTime();
        aCalendar.set(Calendar.DATE, 1);
        String firstDateOfPreviousMonth = "" + aCalendar.getTime();
        String prevMonthConversion = convertDate(prevMonth, years);
        //System.out.println("First Date : " + firstDateOfPreviousMonth.substring(8, 10));
        //System.out.println("Last Date : " + lastDateOfPreviousMonth.substring(8, 10));
        periode = firstDateOfPreviousMonth.substring(8,10) + " " + getPreviousMonthName(month,years) + " - " + lastDateOfPreviousMonth.substring(8, 10) + " " + getPreviousMonthName(month,years);

        return periode;
    }

    public String getFirstText(String text, int length){
        String separateText = text.trim();
        /*int difference = 0;
        int currentPosition = 0;
        String temp = new String();
        if(text.length()> length){
            separateText = text.substring(0,length);
            temp = separateText;
            int counter = 0;
            while(currentPosition>-1){
                counter = counter + currentPosition+1;
                temp = temp.substring(currentPosition+1);
                currentPosition = temp.indexOf(" ");
            }
            separateText = text.substring(0,counter);
        }
        else{
            separateText = text;
        }
        //System.out.println("SeparateText : " + separateText);*/

        String endChar = new String();

        if(text.length()> length){
            endChar = text.substring(length-1,length);
            if(endChar.equalsIgnoreCase(" ")==true){
                separateText = text.substring(0,(length-1));
            }
            else{
                separateText = text.substring(0,(length-1)) + "-";
            }
        }
        else{
            separateText = text;
        }

        //System.out.println("Text Asal     : " + text);
        //System.out.println("Text Potongan : " + separateText);
        //System.out.println("Text Potongan : " + endChar);

        return separateText;
    }

    public int getFirstTextPosition(String text, int length){
        int counter = -1;
        /*int difference = 0;
        int currentPosition = 0;
        int counter = -1;
        String separateText = text.trim();
        String temp = new String();
        if(text.length()> length){
            separateText = text.substring(0,length);
            temp = separateText;
            counter = 0;
            while(currentPosition>-1){
                counter = counter + currentPosition+1;
                temp = temp.substring(currentPosition+1);
                currentPosition = temp.indexOf(" ");
            }
            separateText = text.substring(0,counter);
        }
        else{
            separateText = text;
        }*/
        if(text.length()> length){
            counter = length;
        }
        //System.out.println("Length : " + length);
        return counter;
    }

    public String getLastText(String text, int start, int length){
        String separateText = new String();
        if(start>-1){
            separateText = text.substring(length-1);
        }
        //System.out.println("Text Asal: " + text);
        //System.out.println("Text Potongan : " + separateText);
        return separateText;
    }

    public String setJustify(String text, int length){
        ArrayList<String> words = new ArrayList<String>(10);
        String modification = new String();
        String temp = text;
        int currentLength = 0;
        int counter = 0;
        int difference = length - text.length();
        for(int i=0; i<length; i++){
            int currentPosition = temp.indexOf(" ");
            if(currentPosition < 0){
                i = length;
            }
            else{
                words.add(temp.substring(0,currentPosition));
                temp = temp.substring(currentPosition+1);
                //System.out.println("Words " + i + " : " + words.get(i));
                counter++;
            }
        }
        int index = 0;
        for(int i=0; i<difference;i++){
            if(index == words.size()-1){
                index = 0;
                words.set(index,words.get(index));
                i--;
            }else{
                if(words.size()>3){
                    words.set(index,words.get(index) + " ");
                }
                else{
                    words.set(index,words.get(index));
                }
                
                //System.out.println("SPACE : " + words.get(index));
                index++;
            }
        }
        modification = words.get(0);

        for(int i=1; i<words.size()-1; i++){
            modification = modification + " " + words.get(i);
        }
        modification = modification + " " + words.get(words.size()-1);
        //System.out.println(modification);
        return modification;
    }

    public String setCurrency(String value){
        boolean statusKoma = false;
        String decimal = new String();
        if(value.indexOf(".")>-1){
            decimal = value.substring(value.indexOf("."));
            value = value.substring(0,value.indexOf("."));
        }
        else{
            decimal = ".00";
        }
        String newValue = value.replaceAll("\\s+", "");
        String rightSide = new String();
        String leftSide = new String();
        int length = newValue.length();
        int leftLength = 3;

        while(length > 3){
            rightSide = newValue.substring(newValue.length() - leftLength);
            leftSide  = newValue.substring(0,newValue.length() - leftLength);
            newValue = leftSide + "," + rightSide;
            length = leftSide.length();
            //System.out.println("RightSide : " + rightSide + " LeftSide : " + leftSide + " - " + newValue + " - " + length);
            leftLength = leftLength + 4;
        }
        newValue = newValue + decimal;
        return normDecimal(newValue);
    }

    public String rightStr(String value, int length) {
        // To get right characters from a string, change the begin index.
        return value.substring(value.length() - length);
    }

    public String leftStr(String st,int length){
        int stringlength=st.length();

        if(stringlength<=length){
            return st;
        }

        return st.substring((stringlength-length));
    }

    public String setCurrencyIdr(String value){
        boolean statusKoma = false;
        String decimal = new String();
        if(value.indexOf(",")>-1){
            decimal = value.substring(value.indexOf(","));
            value = value.substring(0,value.indexOf(","));
        }
        else{
            decimal = ",00";
        }
        String newValue = value.replaceAll("\\s+", "");
        String rightSide = new String();
        String leftSide = new String();
        int length = newValue.length();
        int leftLength = 3;

        while(length > 3){
            rightSide = newValue.substring(newValue.length() - leftLength);
            leftSide  = newValue.substring(0,newValue.length() - leftLength);
            newValue = leftSide + "." + rightSide;
            length = leftSide.length();
            //System.out.println("RightSide : " + rightSide + " LeftSide : " + leftSide + " - " + newValue + " - " + length);
            leftLength = leftLength + 4;
        }
        newValue = newValue + decimal;
        return normDecimal(newValue);
    }

    public String setCurrencyIdr2(String value){
        boolean statusKoma = false;
        String decimal = new String();
        if(value.indexOf(",")>-1){
            String decs = new String();
            //System.out.println("SUBSTRING : " + value.substring(value.indexOf(",")+1) + " " + value.substring(value.indexOf(",")+1).length());
            if(value.substring(value.indexOf(",")+1).length()<2){
                decs = value.substring(value.indexOf(",")+1) + "0";
            }
            else{
                decs = value.substring(value.indexOf(",")+1);
            }
            decimal = "," + decs;
            value = value.substring(0,value.indexOf(","));
        }
        else{
            decimal = ",00";
        }
        String newValue = value.replaceAll("\\s+", "");
        String rightSide = new String();
        String leftSide = new String();
        int length = newValue.length();
        int leftLength = 3;

        while(length > 3){
            rightSide = newValue.substring(newValue.length() - leftLength);
            leftSide  = newValue.substring(0,newValue.length() - leftLength);
            newValue = leftSide + "," + rightSide;
            length = leftSide.length();
            //System.out.println("RightSide : " + rightSide + " LeftSide : " + leftSide + " - " + newValue + " - " + length);
            leftLength = leftLength + 4;
        }
        newValue = newValue + decimal;
        return normDecimal(newValue);
    }

    public int getPreviousMonthNumber(String month){
        int previousMonth = 0;
        Date dt = new Date();
        int year = dt.getYear() + 1900;

        if(month.equalsIgnoreCase("januari")){
            previousMonth = 12;
        }
        else if(month.equalsIgnoreCase("februari")){
            previousMonth = 1;
        }
        else if(month.equalsIgnoreCase("maret")){
            previousMonth = 2;
        }
        else if(month.equalsIgnoreCase("april")){
            previousMonth = 3;
        }
        else if(month.equalsIgnoreCase("mei")){
            previousMonth = 4;
        }
        else if(month.equalsIgnoreCase("juni")){
            previousMonth = 5;
        }
        else if(month.equalsIgnoreCase("juli")){
            previousMonth = 6;
        }
        else if(month.equalsIgnoreCase("agustus")){
            previousMonth = 7;
        }
        else if(month.equalsIgnoreCase("september")){
            previousMonth = 8;
        }
        else if(month.equalsIgnoreCase("oktober")){
            previousMonth = 9;
        }
        else if(month.equalsIgnoreCase("november")){
            previousMonth = 10;
        }
        else if(month.equalsIgnoreCase("desember")){
            previousMonth = 11;
        }

        return previousMonth;
    }
    
    public String getPreviousMonthName(String month, int years){
        String previousMonth = new String();
        Date dt = new Date();
        int year = dt.getYear() + 1900;

        if(month.equalsIgnoreCase("januari")){
            //previousMonth = "DES " + (year - 1);
            previousMonth = "DES " + (years-1);
        }
        else if(month.equalsIgnoreCase("februari")){
            previousMonth = "JAN " + years;
        }
        else if(month.equalsIgnoreCase("maret")){
            previousMonth = "FEB " + years;
        }
        else if(month.equalsIgnoreCase("april")){
            previousMonth = "MAR " + years;
        }
        else if(month.equalsIgnoreCase("mei")){
            previousMonth = "APR " + years;
        }
        else if(month.equalsIgnoreCase("juni")){
            previousMonth = "MEI " + years;
        }
        else if(month.equalsIgnoreCase("juli")){
            previousMonth = "JUN " + years;
        }
        else if(month.equalsIgnoreCase("agustus")){
            previousMonth = "JUL " + years;
        }
        else if(month.equalsIgnoreCase("september")){
            previousMonth = "AGU " + years;
        }
        else if(month.equalsIgnoreCase("oktober")){
            previousMonth = "SEP " + years;
        }
        else if(month.equalsIgnoreCase("november")){
            previousMonth = "OKT " + years;
        }
        else if(month.equalsIgnoreCase("desember")){
            previousMonth = "NOV " + years;
        }
        
        return previousMonth;
    }

    public String getPreviousMonthName2(String month){
        String previousMonth = new String();
        Date dt = new Date();
        int year = dt.getYear() + 1900;

        if(month.equalsIgnoreCase("januari")){
            previousMonth = "Desember " + (year - 1);
        }
        else if(month.equalsIgnoreCase("februari")){
            previousMonth = "Januari " + year;
        }
        else if(month.equalsIgnoreCase("maret")){
            previousMonth = "Februari " + year;
        }
        else if(month.equalsIgnoreCase("april")){
            previousMonth = "Maret " + year;
        }
        else if(month.equalsIgnoreCase("mei")){
            previousMonth = "April " + year;
        }
        else if(month.equalsIgnoreCase("juni")){
            previousMonth = "Mei " + year;
        }
        else if(month.equalsIgnoreCase("juli")){
            previousMonth = "Juni " + year;
        }
        else if(month.equalsIgnoreCase("agustus")){
            previousMonth = "Juli " + year;
        }
        else if(month.equalsIgnoreCase("september")){
            previousMonth = "Agustus " + year;
        }
        else if(month.equalsIgnoreCase("oktober")){
            previousMonth = "September " + year;
        }
        else if(month.equalsIgnoreCase("november")){
            previousMonth = "Oktober " + year;
        }
        else if(month.equalsIgnoreCase("desember")){
            previousMonth = "November " + year;
        }

        return previousMonth;
    }

    public String convertDate(String calendar){
      if(calendar.length()>6){
        String year = calendar.substring(calendar.length()-4);
        String date = calendar.substring(calendar.length()-6,calendar.length()-4);
        String month = new String();
        
        if(calendar.length()==7){
            month = calendar.substring(calendar.length()-calendar.length(),1);
        }
        else if(calendar.length()==8){
            month = calendar.substring(calendar.length()-calendar.length(),2);
        }
        //System.out.println("DATE : " + month);

        if(month.equalsIgnoreCase("1") == true || month.equalsIgnoreCase("01") == true){
            month = "Januari";
        }
        else if(month.equalsIgnoreCase("2") == true || month.equalsIgnoreCase("02") == true){
            month = "Februari";
        }
        else if(month.equalsIgnoreCase("3") == true || month.equalsIgnoreCase("03") == true){
            month = "Maret";
        }
        else if(month.equalsIgnoreCase("4") == true || month.equalsIgnoreCase("04") == true){
            month = "April";
        }
        else if(month.equalsIgnoreCase("5") == true || month.equalsIgnoreCase("05") == true){
            month = "Mei";
        }
        else if(month.equalsIgnoreCase("6") == true || month.equalsIgnoreCase("06") == true){
            month = "Juni";
        }
        else if(month.equalsIgnoreCase("7") == true || month.equalsIgnoreCase("07") == true){
            month = "Juli";
        }
        else if(month.equalsIgnoreCase("8") == true || month.equalsIgnoreCase("08") == true){
            month = "Agustus";
        }
        else if(month.equalsIgnoreCase("9") == true || month.equalsIgnoreCase("09") == true){
            month = "September";
        }
        else if(month.equalsIgnoreCase("10") == true || month.equalsIgnoreCase("10") == true){
            month = "Oktober";
        }
        else if(month.equalsIgnoreCase("11") == true){
            month = "November";
        }
        else if(month.equalsIgnoreCase("12") == true){
            month = "Desember";
        }
        //System.out.println("CALENDAR : " + calendar);
        //System.out.println("year : " + year);
        //System.out.println("month : " + month);
        //System.out.println("date : " + date);
        calendar = "" + date + " " + month + " " + year;
      }
        return calendar;
    }
    
    public String convertDateMM(String dateParam) throws ParseException{
        SimpleDateFormat input = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat output = new SimpleDateFormat("dd MMMM yyyy", new Locale("id", "ID"));
        Date date = input.parse(dateParam);
        return output.format(date);
        
    }

    public String getSeparatorDate(String date){
        String convertDate = new String();
        String dt = new String();
        String month = new String();
        String year = new String();
        
        if(date.length()==8){
            dt    = date.substring(0,2);
            month = date.substring(2,4);
            year  = date.substring(4);
            convertDate = dt + "/" + month + "/" + year;
        }
        else if(date.length()==7){
            dt    = date.substring(0,1);
            month = date.substring(1,3);
            year  = date.substring(3);
            convertDate = dt + "/" + month + "/" + year;
        }
        return convertDate;
    }

    public String convertDate(int month, int year) {
        String[] months = {"", "JAN", "FEB", "MAR", "APR", "MEI", "JUN", "JUL", "AGS", "SEP", "OKT", "NOV", "DES"};
        return months[month] + " " + year;
    }


    public String convertDate2(int month, int year){
        String converDate = "" + year;
        if(month == 1){
            converDate = "JANUARI " + converDate;
        }
        else if(month == 2){
            converDate = "FEBRUARI " + converDate;
        }
        else if(month == 3){
            converDate = "MARET " + converDate;
        }
        else if(month == 4){
            converDate = "APRIL " + converDate;
        }
        else if(month == 5){
            converDate = "MEI " + converDate;
        }
        else if(month == 6){
            converDate = "JUNI " + converDate;
        }
        else if(month == 7){
            converDate = "JULI " + converDate;
        }
        else if(month == 8){
            converDate = "AGUSTUS " + converDate;
        }
        else if(month == 9){
            converDate = "SEPTEMBER " + converDate;
        }
        else if(month == 10){
            converDate = "OKTOBER " + converDate;
        }
        else if(month == 11){
            converDate = "NOVEMBER " + converDate;
        }
        else if(month == 12){
            converDate = "DESEMBER " + converDate;
        }
        return converDate;
    }

    public String norm6Digit(int value){
        String text = new String();
        if(value < 10){
            text = "00000" + value;
        }
        else if(value < 100){
            text = "0000" + value;
        }
        else if(value < 1000){
            text = "000" + value;
        }
        else if(value < 10000){
            text = "00" + value;
        }
        else if(value < 100000){
            text = "0" + value;
        }
        else {
            text = "" + value;
        }
        return text;
    }
    
    public String norm6Digit2(int value){
        return String.format("%06d", value);
    }


    public String norm8Digit(int value){
        String text = new String();
        if(value < 10){
            text = "0000000" + value;
        }
        else if(value < 100){
            text = "000000" + value;
        }
        else if(value < 1000){
            text = "00000" + value;
        }
        else if(value < 10000){
            text = "0000" + value;
        }
        else if(value < 100000){
            text = "000" + value;
        }
        else if(value < 1000000){
            text = "00" + value;
        }
        else if(value < 10000000){
            text = "0" + value;
        }
        else {
            text = "" + value;
        }
        return text;
    }

    public String norm2Digit(int value){
        String text = new String();
        if(value < 10){
            text = "0" + value;
        }
        else {
            text = "" + value;
        }
        return text;
    }

    public String reduceDecimal(String value){
        int index = value.indexOf(".");
        if(index>-1){
            String part1 = value.substring(0,index);
            String part2 = value.substring(index,index+3);
            value = part1 + part2;
        }
        return value;
    }

    public String replaceToIdr(String value){
        String path1 = value.substring(0,value.indexOf('.'));
        String path2 = value.substring(value.indexOf('.'));
        //System.out.println("TTTTTTTTTTTT : " + value + "   " + path1 + "   " + path2);
        value = path1.replace(',', '.');
        value = value + path2.replace('.', ',');
        //System.out.println("TTTTTTTTTTTT : " + value + "   " + path1 + "   " + path2);
        return normDecimal(value);
    }

    public String replaceDateToFormatA(String date){
        if(date.length()==7){
            date = "0" + date;
            date = date.substring(0, 2) + "/" + date.substring(2,4) + "/" + date.substring(4);
        }
        else{
            date = date.substring(0, 2) + "/" + date.substring(2,4) + "/" + date.substring(4);
        }
        return date;
    }

    public String replaceDateToFormatB(String date){
        String combine = new String();
        if(date.length()>0){
            //System.out.println(date);
            String date1 = date.substring(0,2);
            String month1 = date.substring(3,6);
            String year1 = date.substring(7,date.length());
            combine = date1 + "/" + convertMonth(month1) + "/" + year1;
        }
        return combine;
    }

    public String convertMonth(String month){
       
        if(month.equalsIgnoreCase("Jan")== true){
            month = "01";
        }
        else if(month.equalsIgnoreCase("Feb")== true){
            month = "02";
        }
        else if(month.equalsIgnoreCase("Mar")== true){
            month = "03";
        }
        else if(month.equalsIgnoreCase("Apr")== true){
            month = "04";
        }
        else if(month.equalsIgnoreCase("Mei")== true){
            month = "05";
        }
        else if(month.equalsIgnoreCase("Jun")== true){
            month = "06";
        }
        else if(month.equalsIgnoreCase("Jul")== true){
            month = "07";
        }
        else if(month.equalsIgnoreCase("agu")== true){
            month = "08";
        }
        else if(month.equalsIgnoreCase("sep")== true){
            month = "09";
        }
        else if(month.equalsIgnoreCase("okt")== true){
            month = "10";
        }
        else if(month.equalsIgnoreCase("nov")== true){
            month = "11";
        }
        else if(month.equalsIgnoreCase("des")== true){
            month = "12";
        }


        return month;
    }

    public String replaceToUsd(String value){
        String path1 = value.substring(0,value.indexOf(','));
        String path2 = value.substring(value.indexOf(','));
        //System.out.println("TTTTTTTTTTTT : " + value + "   " + path1 + "   " + path2);
        value = path1.replace('.', ',');
        value = value + path2.replace(',', '.');
        //System.out.println("TTTTTTTTTTTT : " + value + "   " + path1 + "   " + path2);
        return normDecimal(value);
    }

    public String convertFormatDate(String date){
        String currentFormat = new String();
        if(date.length()==10){
            currentFormat = date.substring(6) + "-" + date.substring(3,5) + "-" + date.substring(0,2);
        }

        return currentFormat;
    }
    
    public static String convertToTerbilang(int angka) {
    // Konversi bilangan bulat ke terbilang
    if (angka < 12) {
      return ANGKA[angka];
    }
    if (angka >= 12 && angka <= 19) {
      return ANGKA[angka % 10] + " belas";
    }
    if (angka >= 20 && angka <= 99) {
      return ANGKA[angka / 10] + " puluh " + ANGKA[angka % 10];
    }
    if (angka >= 100 && angka <= 199) {
      return "seratus " + convertToTerbilang(angka % 100);
    }
    if (angka >= 200 && angka <= 999) {
      return ANGKA[angka / 100] + " ratus " + convertToTerbilang(angka % 100);
    }
    if (angka >= 1000 && angka <= 1999999) {
      return convertToTerbilang(angka / 1000) + " ribu " + convertToTerbilang(angka % 1000);
    }
    if (angka >= 2000000 && angka <= 999999999) {
      return convertToTerbilang(angka / 1000000) + " juta " + convertToTerbilang(angka % 1000000);
    }
    return "";
  }

    public String convertFormatDate2(String date){
        String currentFormat = new String();
        if(date.length()==8){
            currentFormat = date.substring(4) + "-" + date.substring(2,4) + "-" + date.substring(0,2);
        }

        return currentFormat;
    }
    
    public static String padStr(String S, int Len, char C){
        int L = S.length();
        if(L < Len) {
            StringBuilder builder = new StringBuilder(S);
            for(int i = 0; i < (Len - L); i++){
                builder.append(S);
            }
            return builder.toString();
        } else {
            return S;
        }
    }
    
    public static String padRStr(String S, int Len, char C){
        int L = S.length();
        if(S.length() > Len){
            S =  S.substring(0, Len);
        } 
        if(L < Len){
            return S + new String(new char[Len - L]).replace('\0', C);
        } else {
            return S;
        }
    }
    
    public void writeParagraph(String text, Document document, float left, float bottom, float right, float top, PdfContentByte canvas, BaseFont baseFont, float fontSize, float lineSpacing) {
// Rectangle rect = new Rectangle(leftMargin, rightMargin);
        // Paragraph p = new Paragraph(text).
        Font linkFont = new Font(baseFont, fontSize, Font.UNDERLINE);
        Font contentFont = new Font(baseFont, fontSize);
//        Chunk chunk = new Chunk(text, font);
//        chunk.setUnderline(0.1f, -2f);
//        Phrase phrase = new Phrase(text, font);
        Phrase phrase = new Phrase();
        
        Pattern linkPattern = Pattern.compile("(https?://\\S+)");
        Matcher matcher = linkPattern.matcher(text);
        int lastEnd = 0;
        
        while (matcher.find()) {
            if (matcher.start() > lastEnd) {
                String textBefore = text.substring(lastEnd, matcher.start());
                phrase.add(new Chunk(textBefore, contentFont));
            }

            String linkText = matcher.group();
            Chunk linkChunk = new Chunk(linkText, linkFont);
//            linkChunk.setUnderline(0.1f, -2f);
//            linkChunk.setAnchor(linkText);
            phrase.add(linkChunk);

            lastEnd = matcher.end();
        }
        
        if (lastEnd < text.length()) {
            phrase.add(new Chunk(text.substring(lastEnd), contentFont));
        }

        ColumnText ct = new ColumnText(canvas);
        ct.setSimpleColumn(left, bottom, right, top);
        ct.setLeading(0, lineSpacing);
        ct.setAlignment(Element.ALIGN_JUSTIFIED);
        ct.setText(phrase);
        try {
            ct.go();
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    

}
