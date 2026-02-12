/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model.parser;

import aia.model.BaseModel;
import aia.model.productMapping.NfulModel;

/**
 *
 * @author Ratino
 */
public class NfulTextParser implements BaseTextParserInterface{

    @Override
    public BaseModel parse(String[] c) {
        NfulModel model = new NfulModel();
        model.setChdrnum(c[0]);
        model.setZname(c[1]);
        model.setOwner(c[2]);
        model.setAddr01(c[3]);
        model.setAddr02(c[4]);
        model.setAddr03(c[5]);
        model.setAddr04(c[6]);
        model.setAddr05(c[7]);
        model.setCltphone01(c[8]);
        model.setCltphone02(c[9]);
        model.setLetterSeq(c[10]);
        model.setCntcurr(c[11]);
        model.setSinstamt(c[12]);
        model.setZmrktcde(c[13]);
        model.setZagent(c[14]);
        model.setPtdate(c[15]);
        model.setPcode(c[16]);
        model.setAgntnum(c[17]);
        model.setBillFreq(c[18]);
        model.setZservstat(c[19]);
        model.setCnttype(c[20]);
        model.setTpfx(c[21]);
        model.setZsbname(c[22]);
        model.setChdrdue(c[23]);
        model.setSacscurbal(c[24]);
        model.setBankkey(c[25]);
        model.setStatcode(c[26]);
        model.setOccdate(c[27]);
        model.setPeriod(c[28]);
        model.setProdName(c[29]);
        model.setKodeSurat(c[30]);
        model.setKodeSts(c[31]);
        model.setCabLl(c[32]);
        model.setFlgSyariah(c[33]);
        model.setGroupProd(c[34]);
        model.setSrcebus(c[35]);
        model.setNilaiAkun(c[36]);
        model.setNilaiBtal(c[37]);
        model.setNilaiTbus(c[38]);
        model.setTungPremi(c[39]);
        model.setCovarage(c[40]);
        model.setRekAia(c[41]);
        model.setDob(c[42]);
        model.setCltEmail(c[43]);
        model.setPhoneCell(c[44]);
        model.setFlgEstate(c[45]);
        model.setTopupVi(c[46]);
        model.setCabLb(c[47]);
        model.setFldent(c[48]);
        model.setYbankkey(c[49]);
        model.setYsustyp(c[50]);
        model.setVaOwner(c[51]);
        model.setFndcurr(c[52]);
        model.setTotalPremi(c[53]);
        
        
        return model;
    }

}
