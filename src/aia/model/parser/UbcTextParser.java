/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model.parser;

import aia.model.BaseModel;
import aia.model.productMapping.UbcModel;

/**
 *
 * @author Ratino
 */
public class UbcTextParser implements BaseTextParserInterface{

    @Override
    public BaseModel parse(String[] c) {
        UbcModel model = new UbcModel();
        model.setChdrnum(c[0]);
        model.setOwner(c[1]);
        model.setLifasrname(c[2]);
        model.setOccdate(c[3]);
        model.setAddr01(c[4]);
        model.setAddr02(c[5]);
        model.setAddr03(c[6]);
        model.setAddr04(c[7]);
        model.setAddr05(c[8]);
        model.setPcode(c[9]);
        model.setSinstamt(c[10]);
        model.setInstprem(c[11]);
        model.setCurr(c[12]);
        model.setStatcode(c[13]);
        model.setBillFreq(c[14]);
        model.setProdName(c[15]);
        model.setCnttype(c[16]);
        model.setSumins(c[17]);
        model.setDteBirth(c[18]);
        model.setFlgEstate(c[19]);
        model.setZdistdsc(c[20]);
        model.setPeriode(c[21]);
        model.setCltEmail(c[22]);
        model.setSrcebus(c[23]);
        
        
        return model;
    }

}
