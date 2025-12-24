/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model.parser;

import aia.model.BaseModel;
import aia.model.productMapping.Kpdi1Model;

/**
 *
 * @author Ratino
 */
public class KPD1TextParser implements BaseTextParserInterface{

    @Override
    public BaseModel parse(String[] c) {
        Kpdi1Model model = new Kpdi1Model();
        model.setChdrnum(c[0]);
        model.setCnttype(c[1]);
        model.setSrcebus(c[2]);
        model.setOwrname(c[3]);
        model.setAddr01(c[4]);
        model.setAddr02(c[5]);
        model.setAddr03(c[6]);
        model.setAddr04(c[7]);
        model.setAddr05(c[8]);
        model.setPcode(c[9]);
        model.setLifasrname(c[10]);
        model.setOccdate(c[11]);
        model.setPtdate(c[12]);
        model.setPeriode(c[13]);
        model.setValidflag(c[14]);
        model.setProdName(c[15]);
        model.setNldKSts(c[16]);
        model.setFlgSyariah(c[17]);
        model.setFlgEstate(c[18]);
        model.setEmail(c[19]);
        model.setPhonecell(c[20]);
        model.setDteBirth(c[21]);
        
        return model;
        
    }
    
}
