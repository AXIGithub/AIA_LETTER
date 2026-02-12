/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model.parser;

import aia.model.BaseModel;
import aia.model.productMapping.EpmlModel;

/**
 *
 * @author Ratino
 */
public class EpmlTextParser implements BaseTextParserInterface{

    @Override
    public BaseModel parse(String[] c) {
        EpmlModel model = new EpmlModel();
         model.setChdrnum(c[0]);
         model.setClientNum(c[1]);
         model.setOwner(c[2]);
         model.setAddr01(c[3]);
         model.setAddr02(c[4]);
         model.setAddr03(c[5]);
         model.setAddr04(c[6]);
         model.setAddr05(c[7]);
         model.setPcode(c[8]);
         model.setRmblPhone(c[9]);
         model.setCnttype(c[10]);
         model.setSrcebus(c[11]);
         model.setStatcode(c[12]);
         model.setLife(c[13]);
         model.setInsured(c[14]);
         model.setSinstamt(c[15]);
         model.setBillFreq(c[16]);
         model.setBillchnl(c[17]);
         model.setLongdesc(c[18]);
         model.setPtdate(c[19]);
         model.setBenef(c[20]);
         model.setBatctrcde(c[21]);
         model.setTrdtp(c[22]);
         model.setProdName(c[23]);
         model.setFlgSyariah(c[24]);
                       
        return model;
    }
    
}
