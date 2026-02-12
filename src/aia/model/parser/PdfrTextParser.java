/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model.parser;

import aia.model.BaseModel;
import aia.model.productMapping.PdfrModel;
import aia.model.productMapping.PdfwModel;

/**
 *
 * @author Ratino
 */
public class PdfrTextParser implements BaseTextParserInterface{

    @Override
    public BaseModel parse(String[] c) {
        PdfrModel model = new PdfrModel();
        model.setChdrnum(c[0]);
        model.setProdName(c[1]);
        model.setOwner(c[2]);
        model.setAddr01(c[3]);
        model.setAddr02(c[4]);
        model.setAddr03(c[5]);
        model.setAddr04(c[6]);
        model.setAddr05(c[7]);
        model.setPcode(c[8]);
        model.setCcy(c[9]);
        model.setAddf(c[10]);
        model.setStatcode(c[11]);
        model.setZmrktcde(c[12]);
        model.setZservstat(c[13]);
        model.setBillchnl(c[14]);
        model.setBankkey(c[15]);
        model.setSex(c[16]);
        model.setDob(c[17]);
        model.setPhone01(c[18]);
        model.setPhone02(c[19]);
        model.setFlag01(c[20]);
        model.setSegCode(c[21]);
        model.setNoUndian(c[22]);
        model.setTranscDat(c[23]);
        model.setDescName(c[24]);
        model.setPrDeposit(c[25]);
        model.setPrRisk(c[26]);
        model.setCoverage(c[27]);
        model.setLetterNum(c[28]);
        model.setPageNum(c[29]);
        model.setRowNum(c[30]);
        model.setBalance(c[31]);
        model.setOccdate(c[32]);
        model.setDteBirth(c[33]);
        model.setFlgEstate(c[34]);
        model.setCltEmail(c[35]);
        model.setPhoneCell(c[36]);
        
        return model;
    }

}
