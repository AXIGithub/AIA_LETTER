/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model.parser;

import aia.model.BaseModel;
import aia.model.EpmlModel;

/**
 *
 * @author Ratino
 */
public class EpmlTextParser implements BaseTextParserInterface{

    @Override
    public BaseModel parse(String[] c) {
        EpmlModel model = new EpmlModel();
         model.setChrdnum(c[0]);
         model.setClientNum(c[1]);
         model.setOwner(c[2]);
         model.setAlamat1(c[3]);
         model.setAlamat2(c[4]);
         model.setAlamat3(c[5]);
         model.setAlamat4(c[6]);
         model.setAlamat5(c[7]);
         model.setCltpcode(c[8]);
         model.setRmblPhone(c[9]);
         model.setCnttype(c[10]);
         model.setSrcebus(c[11]);
         model.setStatcode(c[12]);
         model.setLife(c[13]);
         model.setInsured(c[14]);
         model.setSinstamt(c[15]);
         model.setBillfreq(c[16]);
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
