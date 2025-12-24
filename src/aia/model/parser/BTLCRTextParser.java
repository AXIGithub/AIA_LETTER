/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model.parser;

import aia.model.BaseModel;
import aia.model.productMapping.BTLCRModel;

/**
 *
 * @author Ratino
 */
public class BTLCRTextParser implements BaseTextParserInterface{

    @Override
    public BaseModel parse(String[] c) {
        BTLCRModel model = new BTLCRModel();
        model.setChdrnum(c[0]);
        model.setOwner(c[1]);
        model.setAmount(c[2]);
        model.setBankToName(c[3]);
        model.setBankToAcc(c[4]);
        model.setPaidTo(c[5]);
        model.setCnttype(c[6]);
        model.setCntcurr(c[7]);
        model.setStatcode(c[8]);
        model.setAlamat1(c[9]);
        model.setAlamat2(c[10]);
        model.setAlamat3(c[11]);
        model.setAlamat4(c[12]);
        model.setAlamat5(c[13]);
        model.setCltpcode(c[14]);
        model.setPtrneff04(c[15]);
        model.setInsured(c[16]);
        model.setSumIns(c[17]);
        model.setKodeSurat(c[18]);
        model.setKodeSts(c[19]);
        model.setProdName(c[20]);
        model.setZservstat(c[21]);
        model.setZmrktcd(c[22]);
        model.setCaraBayar(c[23]);
        model.setTerbilang(c[24]);
        model.setCabLl(c[25]);
        model.setDtUpload(c[26]);
        model.setAgentName(c[27]);
        model.setFundCurr(c[28]);
        model.setFlgSyariah(c[29]);
        model.setGroupProd(c[30]);
        model.setDteBirth(c[31]);
        model.setFlgEstate(c[32]);
        model.setSinstamt(c[33]);
        model.setBillfreq(c[34]);
        model.setOccdate(c[35]);
        model.setActiveInd(c[36]);
        model.setLifcName01(c[37]);
        model.setLifcName02(c[38]);
        model.setLifcName03(c[39]);
        model.setLifcName04(c[40]);

        return model;
    }
    
}
