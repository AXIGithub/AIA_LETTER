/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model.parser;

import aia.model.BaseModel;
import aia.model.productMapping.PtpModel;

/**
 *
 * @author Ratino
 */
public class PtpTextParser implements BaseTextParserInterface{

    @Override
    public BaseModel parse(String[] c) {
        PtpModel model = new PtpModel();
        model.setChdrnum(c[0]);
        model.setSurname(c[1]);
        model.setOwner(c[2]);
        model.setAddr01(c[3]);
        model.setAddr02(c[4]);
        model.setAddr03(c[5]);
        model.setAddr04(c[6]);
        model.setAddr05(c[7]);
        model.setPcode(c[8]);
        model.setCnttype(c[9]);
        model.setOccdate(c[10]);
        model.setPtdate(c[11]);
        model.setTlanjutan(c[12]);
        model.setPlanjutan(c[13]);
        model.setSduty(c[14]);
        model.setSisaSaldo(c[15]);
        model.setTglCetak(c[16]);
        model.setBillFreq(c[17]);
        model.setNldKSrt(c[18]);
        model.setNldKSts(c[19]);
        model.setProdName(c[20]);
        model.setFlgSyariah(c[21]);
        model.setFlgEstate(c[22]);
        model.setPhoneCell(c[23]);
        model.setCltEmail(c[24]);
        model.setStatcode(c[25]);
        model.setCntcurr(c[26]);
        model.setDteBirth(c[27]);
        
        
        return model;
    }

}
