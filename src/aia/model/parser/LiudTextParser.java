/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model.parser;

import aia.model.BaseModel;
import aia.model.productMapping.LiudModel;

/**
 *
 * @author Ratino
 */
public class LiudTextParser implements BaseTextParserInterface{

    @Override
    public BaseModel parse(String[] c) {
        LiudModel model = new LiudModel();
        model.setChdrnum(c[0]);
        model.setCnttype(c[1]);
        model.setSrcebus(c[2]);
        model.setLifcnum(c[3]);
        model.setInsured(c[4]);
        model.setCownnum(c[5]);
        model.setOwner(c[6]);
        model.setAddr01(c[7]);
        model.setAddr02(c[8]);
        model.setAddr03(c[9]);
        model.setAddr04(c[10]);
        model.setAddr05(c[11]);
        model.setPcode(c[12]);
        model.setCntcurr(c[13]);
        model.setSumins(c[14]);
        model.setBillFreq(c[15]);
        model.setOccdate(c[16]);
        model.setPtrneff(c[17]);
        model.setUsrProfil(c[18]);
        model.setJobName(c[19]);
        model.setDatime(c[20]);
        model.setPeriod(c[21]);
        model.setProdName(c[22]);
        model.setNldKSts(c[23]);
        model.setdBillfreq(c[24]);
        model.setFlgSyariah(c[25]);
        model.setFlgEstate(c[26]);
        model.setCltEmail(c[27]);
        model.setPhoneCell(c[28]);
        model.setSacscurbal(c[29]);
        model.setSinstamt(c[30]);
        model.setStatcode(c[31]);
        model.setZservstat(c[32]);
        model.setTopupVi(c[33]);
        model.setCabLb(c[34]);
        model.setTerbilang(c[35]);
        model.setFldent(c[36]);
        model.setYbankkey(c[37]);
        model.setYsustyp(c[38]);
        model.setRekAia(c[39]);
        model.setVaOwner(c[40]);
        model.setDteBirth(c[41]);
        
        
        return model;
    }

}
