/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model.parser;
import aia.model.BaseModel;
import aia.model.productMapping.KpntModel;

/**
 *
 * @author Ratino
 */
public class KpntTextParser implements BaseTextParserInterface{

    @Override
    public BaseModel parse(String[] c) {
        KpntModel model = new KpntModel();
        model.setChdrnum(c[0]);
        model.setCnttype(c[1]);
        model.setSrcebus(c[2]);
        model.setOwner(c[3]);
        model.setAddr01(c[4]);
        model.setAddr02(c[5]);
        model.setAddr03(c[6]);
        model.setAddr04(c[7]);
        model.setAddr05(c[8]);
        model.setPcode(c[9]);
        model.setLifasrname(c[10]);
        model.setOccdate(c[11]);
        model.setPtdate(c[12]);
        model.setProdName(c[13]);
        model.setFlgEstate(c[14]);
        model.setCltEmail(c[15]);
        model.setPhoneCell(c[16]);
        model.setDteBirth(c[17]);
        model.setPeriod(c[18]);
            
        return model;
    }
    
    
}
