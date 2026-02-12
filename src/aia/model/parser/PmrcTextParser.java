/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model.parser;

import aia.model.BaseModel;
import aia.model.productMapping.PmrcModel;

/**
 *
 * @author Ratino
 */
public class PmrcTextParser implements BaseTextParserInterface{

    @Override
    public BaseModel parse(String[] c) {
        PmrcModel model = new PmrcModel();
        model.setChdrnum(c[0]);
        model.setOwner(c[1]);
        model.setAmount(c[2]);
        model.setBankToname(c[3]);
        model.setBankToacc(c[4]);
        model.setPaidto(c[5]);
        model.setCnttype(c[6]);
        model.setCntcurr(c[7]);
        model.setAddr01(c[8]);
        model.setAddr02(c[9]);
        model.setAddr03(c[10]);
        model.setAddr04(c[11]);
        model.setAddr05(c[12]);
        model.setPcode(c[13]);
        model.setPtrneff04(c[14]);
        model.setSumins(c[15]);
        model.setInsured(c[16]);
        model.setZmrktcd(c[17]);
        model.setZservstat(c[18]);
        model.setStatcode(c[19]);
        model.setSubcode(c[20]);
        model.setSubtype(c[21]);
        model.setCaraBayar(c[22]);
        model.setNoBg(c[23]);
        model.setKodeSurat(c[24]);
        model.setKodeSts(c[25]);
        model.setProdName(c[26]);
        model.setGroupProd(c[27]);
        model.setTerbilang(c[28]);
        model.setCabLl(c[29]);
        model.setTglUpload(c[30]);
        model.setAgentName(c[31]);
        model.setFundCurre(c[32]);
        model.setFlgSyariah(c[33]);
        model.setDteBirth(c[34]);
        model.setFlgEstate(c[35]);
        model.setSinstamt(c[36]);
        model.setBillFreq(c[37]);
        model.setOccdate(c[38]);
        
        
        return model;
    }

}
