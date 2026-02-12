/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model.parser;

import aia.model.BaseModel;
import aia.model.productMapping.PdfwModel;

/**
 *
 * @author Ratino
 */
public class PdfwTextParser implements BaseTextParserInterface{

    @Override
    public BaseModel parse(String[] c) {
        PdfwModel model = new PdfwModel();
        model.setChdrnum(c[0]);
        model.setCnttype(c[1]);
        model.setProdName(c[2]);
        model.setOwner(c[3]);
        model.setAddr01(c[4]);
        model.setAddr02(c[5]);
        model.setAddr03(c[6]);
        model.setAddr04(c[7]);
        model.setAddr05(c[8]);
        model.setPcode(c[9]);
        model.setCcy(c[10]);
        model.setAddf(c[11]);
        model.setStatcode(c[12]);
        model.setZmrktcde(c[13]);
        model.setZservstat(c[14]);
        model.setCoverage(c[15]);
        model.setDate01(c[16]);
        model.setDescrip01(c[17]);
        model.setOrigamt01(c[18]);
        model.setPrem01(c[19]);
        model.setDate02(c[20]);
        model.setDescrip02(c[21]);
        model.setOrigamt02(c[22]);
        model.setPrem02(c[23]);
        model.setDate03(c[24]);
        model.setDescrip03(c[25]);
        model.setOrigamt03(c[26]);
        model.setPrem03(c[27]);
        model.setDate04(c[28]);
        model.setDescrip04(c[29]);
        model.setOrigamt04(c[30]);
        model.setPrem04(c[31]);
        model.setDate05(c[32]);
        model.setDescrip05(c[33]);
        model.setOrigamt05(c[34]);
        model.setPrem05(c[35]);
        model.setDate06(c[36]);
        model.setDescrip06(c[37]);
        model.setOrigamt06(c[38]);
        model.setPrem06(c[39]);
        model.setDate07(c[40]);
        model.setDescrip07(c[41]);
        model.setOrigamt07(c[42]);
        model.setPrem07(c[43]);
        model.setDate08(c[44]);
        model.setDescrip08(c[45]);
        model.setOrigamt08(c[46]);
        model.setPrem08(c[47]);
        model.setDate09(c[48]);
        model.setDescrip09(c[49]);
        model.setOrigamt09(c[50]);
        model.setPrem09(c[51]);
        model.setDate10(c[52]);
        model.setDescrip10(c[53]);
        model.setOrigamt10(c[54]);
        model.setPrem10(c[55]);
        model.setDate11(c[56]);
        model.setDescrip11(c[57]);
        model.setOrigamt11(c[58]);
        model.setPrem11(c[59]);
        model.setDate12(c[60]);
        model.setDescrip12(c[61]);
        model.setOrigamt12(c[62]);
        model.setPrem12(c[63]);
        model.setFlgEstate(c[64]);
        model.setCltEmail(c[65]);
        model.setPhoneCell(c[66]);
        
        
        
        return model;
    }

}
