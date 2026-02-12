/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model.parser;

import aia.model.BaseModel;
import aia.model.productMapping.NadModel;

/**
 *
 * @author Ratino
 */
public class NadTextParser implements BaseTextParserInterface{

    @Override
    public BaseModel parse(String[] c) {
        NadModel model = new NadModel();
        model.setChdrnum(c[0]);
        model.setClntnum(c[1]);
        model.setInsured(c[2]);
        model.setOwner(c[3]);
        model.setAddr01(c[4]);
        model.setAddr02(c[5]);
        model.setAddr03(c[6]);
        model.setAddr04(c[7]);
        model.setAddr05(c[8]);
        model.setBrcode0(c[9]);
        model.setBrcode1(c[10]);
        model.setBrcode2(c[11]);
        model.setCnttype(c[12]);
        model.setCntcurr(c[13]);
        model.setSumins(c[14]);
        model.setZmrktcd(c[15]);
        model.setZservstat(c[16]);
        model.setSinstamt01(c[17]);
        model.setBillFreq(c[18]);
        model.setNldkSrt(c[19]);
        model.setNldkSts(c[20]);
        model.setProdName(c[21]);
        model.setDtUpload(c[22]);
        model.setCabLl(c[23]);
        model.setUsrPrf(c[24]);
        model.setFlgSyariah(c[25]);
        model.setCltEmail(c[26]);
        model.setPhoneCell(c[27]);
        model.setGroupProd(c[28]);
        model.setStatcode(c[29]);
        model.setCltdob(c[30]);
        model.setOccdate(c[31]);
        model.setFlgEstate(c[32]);
        
        
        return model;
    }

}
