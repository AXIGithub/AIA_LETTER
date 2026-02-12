/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model.parser;

import aia.model.BaseModel;
import aia.model.productMapping.Thp1Model;
import aia.model.productMapping.TutpModel;

/**
 *
 * @author Ratino
 */
public class TutpTextParser implements BaseTextParserInterface{

    @Override
    public BaseModel parse(String[] c) {
        TutpModel model = new TutpModel();
        model.setOwner(c[0]);
        model.setChdrnum(c[1]);
        model.setAddr01(c[2]);
        model.setAddr02(c[3]);
        model.setAddr03(c[4]);
        model.setAddr04(c[5]);
        model.setAddr05(c[6]);
        model.setLreqdate(c[7]);
        model.setAlocamt01(c[8]);
        model.setAlocamt02(c[9]);
        model.setAlocamt03(c[10]);
        model.setAlocamt04(c[11]);
        model.setCntcurr(c[12]);
        model.setZinnto(c[13]);
        model.setZmrktcde(c[14]);
        model.setPcode(c[15]);
        model.setZservstat(c[16]);
        model.setZsbname(c[17]);
        model.setNldKSrt(c[18]);
        model.setNldKSts(c[19]);
        model.setProdName(c[20]);
        model.setSumins(c[21]);
        model.setClntnum(c[22]);
        model.setCabLl(c[23]);
        model.setPeriode(c[24]);
        model.setFlgSyariah(c[25]);
        model.setFlgEstate(c[26]);
        model.setCltEmail(c[27]);
        model.setPhoneCell(c[28]);
        model.setDteBirth(c[29]);
        
        
        return model;
    }

}
