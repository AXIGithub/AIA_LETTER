/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model.parser;
import aia.model.BaseModel;
import aia.model.productMapping.EsamModel;

/**
 *
 * @author Ratino
 */
public class EsamTextParser implements BaseTextParserInterface{

    @Override
    public BaseModel parse(String[] c) {
        EsamModel model = new EsamModel();
        model.setChdrnum(c[0]);
        model.setOwner(c[1]);
        model.setLifasrname(c[2]);
        model.setOccdate(c[3]);
        model.setAddr01(c[4]);
        model.setAddr02(c[5]);
        model.setAddr03(c[6]);
        model.setAddr04(c[7]);
        model.setAddr05(c[8]);
        model.setPcode(c[9]);
        model.setSinstamt(c[10]);
        model.setPtdate(c[11]);
        model.setCntcurr(c[12]);
        model.setStatcode(c[13]);
        model.setBillFreq(c[14]);
        model.setZprname(c[15]);
        model.setCnttype(c[16]);
        model.setDob(c[17]);
        model.setRcesdte(c[18]);
        model.setRinternet(c[19]);
        model.setCrdate(c[20]);
        model.setSumins(c[21]);
        model.setCrtable(c[22]);
        model.setDescname(c[23]);
        model.setSob(c[24]);
        model.setFlgEstate(c[25]);
        model.setPeriod(c[26]);
            
        return model;
    }
    
    
}
