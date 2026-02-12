/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model.parser;

import aia.model.BaseModel;
import aia.model.productMapping.OadModel;

/**
 *
 * @author Ratino
 */
public class OadTextParser implements BaseTextParserInterface{

    @Override
    public BaseModel parse(String[] c) {
        OadModel model = new OadModel();
        model.setChdrnum(c[0]);
        model.setClntnum(c[1]);
        model.setAddr01(c[2]);
        model.setAddr02(c[3]);
        model.setAddr03(c[4]);
        model.setAddr04(c[5]);
        model.setAddr05(c[6]);
        model.setCcodelm0(c[7]);
        model.setCcodelm1(c[8]);
        model.setCcodelm2(c[9]);
        model.setInsured(c[10]);
        model.setOwner(c[11]);
        model.setAddrbr1(c[12]);
        model.setAddrbr2(c[13]);
        model.setAddrbr3(c[14]);
        model.setAddrbr4(c[15]);
        model.setAddrbr5(c[16]);
        model.setCcodebr0(c[17]);
        model.setCcodebr1(c[18]);
        model.setCcodebr2(c[19]);
        model.setCnttype(c[20]);
        model.setCntcurr(c[21]);
        model.setChdrdue(c[22]);
        model.setSumins(c[23]);
        model.setZmrktcd(c[24]);
        model.setSinstamt01(c[25]);
        model.setBillFreq(c[26]);
        model.setNldKSrt(c[27]);
        model.setNldKsts(c[28]);
        model.setProdName(c[29]);
        model.setDtUpload(c[30]);
        model.setCabLl(c[31]);
        model.setUsrprf(c[32]);
        model.setFlgSyariah(c[33]);
        model.setCltEmail(c[34]);
        model.setPhoneCell(c[35]);
        model.setGroupProd(c[36]);
        model.setStatcode(c[37]);
        model.setCltdob(c[38]);
        model.setOccdate(c[39]);
        model.setFlgEstate(c[40]);
        
        
        return model;
    }

}
