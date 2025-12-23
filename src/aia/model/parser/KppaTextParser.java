/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model.parser;

import aia.model.BaseModel;
import aia.model.productMapping.KppaModel;

/**
 *
 * @author Ratino
 */
public class KppaTextParser implements BaseTextParserInterface{

    @Override
    public BaseModel parse(String[] c) {
        KppaModel model = new KppaModel();
        model.setPolicyNo(c[0]);
        model.setCownnum(c[1]);
        model.setZowner(c[2]);
        model.setAddr01(c[3]);
        model.setAddr02(c[4]);
        model.setAddr03(c[5]);
        model.setAddr04(c[6]);
        model.setAddr05(c[7]);
        model.setCltphone01(c[8]);
        model.setCltphone02(c[9]);
        model.setPcode(c[10]);
        model.setCtrycode(c[11]);
        model.setZmrktcde(c[12]);
        model.setLifcnum(c[13]);
        model.setZname(c[14]);
        model.setCnttype(c[15]);
        model.setSrcebus(c[16]);
        model.setOccdate(c[17]);
        model.setStatcode(c[18]);
        model.setBillfreq(c[19]);
        model.setPremi(c[20]);
        model.setInstfrom(c[21]);
        model.setPtd(c[22]);
        model.setTransDate(c[23]);
        model.setPeriod(c[24]);
        model.setProdName(c[25]);
        model.setNldKSts(c[26]);
        model.setFlgSyariah(c[27]);
        model.setFlgEstate(c[28]);
        model.setEmail(c[29]);
        model.setPhonecell(c[30]);
        model.setDteBirth(c[31]);
        
        return model;
    }
    
}
