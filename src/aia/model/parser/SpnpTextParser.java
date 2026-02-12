/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model.parser;

import aia.model.BaseModel;
import aia.model.productMapping.RopModel;
import aia.model.productMapping.SpnpModel;

/**
 *
 * @author Ratino
 */
public class SpnpTextParser implements BaseTextParserInterface{

    @Override
    public BaseModel parse(String[] c) {
        SpnpModel model = new SpnpModel();
        model.setChdrnum(c[0]);
        model.setStatcode(c[1]);
        model.setCnttype(c[2]);
        model.setCntcurr(c[3]);
        model.setOccdate(c[4]);
        model.setSinstamt(c[5]);
        model.setOwner(c[6]);
        model.setAddr01(c[7]);
        model.setAddr02(c[8]);
        model.setAddr03(c[9]);
        model.setAddr04(c[10]);
        model.setAddr05(c[11]);
        model.setPcode(c[12]);
        model.setBankToname(c[13]);
        model.setBankToacc(c[14]);
        model.setPaidTo(c[15]);
        model.setInsured(c[16]);
        model.setBillFreq(c[17]);
        model.setPaydte(c[18]);
        model.setAmounts(c[19]);
        model.setCabLb(c[20]);
        model.setTopupVi(c[21]);
        model.setProdName(c[22]);
        model.setTerbilang(c[23]);
        model.setCltdob(c[24]);
        model.setFlgEstate(c[25]);
        model.setCltEmail(c[26]);
        
        
        return model;
    }

}
