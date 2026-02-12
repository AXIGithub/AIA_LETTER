/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model.parser;

import aia.model.BaseModel;
import aia.model.productMapping.RabModel;

/**
 *
 * @author Ratino
 */
public class RabTextParser implements BaseTextParserInterface{

    @Override
    public BaseModel parse(String[] c) {
        RabModel model = new RabModel();
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
        model.setBasicprem(c[10]);
        model.setProdName(c[11]);
        model.setCnttype(c[12]);
        model.setFlgEstate(c[13]);
        model.setDteBirth(c[14]);
        model.setCltEmail(c[15]);
        model.setSumins(c[16]);
        model.setSrcebus(c[17]);
        model.setPeriode(c[18]);
        
        
        return model;
    }

}
