/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model.parser;

import aia.model.AphModel;
import aia.model.BaseModel;

/**
 *
 * @author Ratino
 */
public class AphTextParser implements BaseTextParserInterface{

    @Override
    public BaseModel parse(String[] c) {
        AphModel model = new AphModel();
        model.setChrdnum(c[0]);
        model.setCnttype(c[1]);
        model.setOwner(c[2]);
        model.setAlamat1(c[3]);
        model.setAlamat2(c[4]);
        model.setAlamat3(c[5]);
        model.setAlamat4(c[6]);
        model.setAlamat5(c[7]);
        model.setCltpcode(c[8]);
        model.setName(c[9]);
        model.setSinstamt(c[10]);
        model.setCntcurr(c[11]);
        model.setBillfreq(c[12]);
        model.setPtdate(c[13]);
        model.setChdrdue(c[14]);
        model.setSrcebus(c[15]);
        model.setZmrktcd(c[16]);
        model.setProdName(c[17]);
        model.setFlgSyariah(c[18]);
        model.setSacscurbal(c[19]);
        model.setOccdate(c[20]);
        model.setStatcode(c[21]);
        model.setZservstat(c[22]);
        model.setTopupVi(c[23]);
        model.setCabLb(c[24]);
        model.setTerbilang(c[25]);
        model.setFldent(c[26]);
        model.setYbankkey(c[27]);
        model.setYsustyp(c[28]);
        model.setRekAia(c[29]);
        model.setVaOwner(c[30]);
        model.setFlgEstate(c[31]);
        model.setCltEmail(c[32]);
        model.setDteBirth(c[33]);
            
        return model;
    }
    
    
}
