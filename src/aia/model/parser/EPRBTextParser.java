/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model.parser;

import aia.model.BaseModel;
import aia.model.productMapping.EprbModel;

/**
 *
 * @author Ratino
 */
public class EPRBTextParser implements BaseTextParserInterface{

    @Override
    public BaseModel parse(String[] c) {
        EprbModel model = new EprbModel();
        model.setChdrnum(c[0]);
        model.setClntnum(c[1]);
        model.setOwner(c[2]);
        model.setAddr01(c[3]);
        model.setAddr02(c[4]);
        model.setAddr03(c[5]);
        model.setAddr04(c[6]);
        model.setAddr05(c[7]);
        model.setPcode(c[8]);
        model.setRmblphone(c[9]);
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
        model.setLongdescb(c[25]);
        model.setPercentage(c[26]);
        model.setFlgEstate(c[27]);
        model.setCltEmail(c[28]);
        model.setDteBirth(c[29]);

        return model;
    }
    
}
