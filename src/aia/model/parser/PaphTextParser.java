/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model.parser;

import aia.model.BaseModel;
import aia.model.productMapping.PaphModel;

/**
 *
 * @author Ratino
 */
public class PaphTextParser implements BaseTextParserInterface{

    @Override
    public BaseModel parse(String[] c) {
        PaphModel model = new PaphModel();
        model.setChdrnum(c[0]);
        model.setOwner(c[1]);
        model.setAddr01(c[2]);
        model.setAddr02(c[3]);
        model.setAddr03(c[4]);
        model.setAddr04(c[5]);
        model.setAddr05(c[6]);
        model.setPcode(c[7]);
        model.setLifasrname(c[8]);
        model.setProdName(c[9]);
        model.setOccdate(c[10]);
        model.setBillCurr(c[11]);
        model.setSinstamt(c[12]);
        model.setBillFreq(c[13]);
        model.setBillChnl(c[14]);
        model.setPtdate(c[15]);
        model.setPeriod(c[16]);
        model.setNldKsts(c[17]);
        model.setFlgSyariah(c[18]);
        model.setFlgEstate(c[19]);
        model.setCltEmail(c[20]);
        model.setPhoneCell(c[21]);
        model.setDteBirth(c[22]);
        
        
        return model;
    }

}
