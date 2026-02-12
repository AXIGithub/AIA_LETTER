/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model.parser;

import aia.model.BaseModel;
import aia.model.productMapping.RopModel;

/**
 *
 * @author Ratino
 */
public class RopTextParser implements BaseTextParserInterface{

    @Override
    public BaseModel parse(String[] c) {
        RopModel model = new RopModel();
        model.setInsured(c[0]);
        model.setOwner(c[1]);
        model.setAddr01(c[2]);
        model.setAddr02(c[3]);
        model.setAddr03(c[4]);
        model.setAddr04(c[5]);
        model.setCity(c[6]);
        model.setProvince(c[7]);
        model.setPcode(c[8]);
        model.setZropyear(c[9]);
        model.setChdrnum(c[10]);
        model.setBillChnl(c[11]);
        model.setBillFreq(c[12]);
        model.setProdName(c[13]);
        model.setZropamount(c[14]);
        model.setTerbilang(c[15]);
        model.setCntcurr(c[16]);
        model.setDc(c[17]);
        model.setFlgEstate(c[18]);
        model.setDob(c[19]);
        model.setCltEmail(c[20]);
        model.setPeriod(c[21]);
        
        
        return model;
    }

}
