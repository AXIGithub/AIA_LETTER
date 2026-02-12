/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model.parser;
import aia.model.BaseModel;
import aia.model.productMapping.MphModel;

/**
 *
 * @author Ratino
 */
public class MphTextParser implements BaseTextParserInterface{

    @Override
    public BaseModel parse(String[] c) {
        MphModel model = new MphModel();
        model.setChdrnum(c[0]);
        model.setOwner(c[1]);
        model.setLifasrname(c[2]);
        model.setOccdate(c[3]);
        model.setCntcurr(c[4]);
        model.setPremamt(c[5]);
        model.setBillFreq(c[6]);
        model.setStatcode(c[7]);
        model.setAddr01(c[8]);
        model.setAddr02(c[9]);
        model.setAddr03(c[10]);
        model.setAddr04(c[11]);
        model.setAddr05(c[12]);
        model.setPcode(c[13]);
        model.setDteBirth(c[14]);
        model.setCltEmail(c[15]);
        model.setPtdate(c[16]);
        model.setPremsusp(c[17]);
        model.setDcname(c[18]);
        model.setFlgEstate(c[19]);
        model.setProdName(c[20]);
        model.setCnttype(c[21]);
        model.setSrcebus(c[22]);
            
        return model;
    }
    
    
}
