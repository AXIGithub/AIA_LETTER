/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model.parser;
import aia.model.BaseModel;
import aia.model.productMapping.AcmModel;

/**
 *
 * @author Ratino
 */
public class AcmTextParser implements BaseTextParserInterface{

    @Override
    public BaseModel parse(String[] c) {
        AcmModel model = new AcmModel();
        model.setAgntnum(c[0]);
        model.setAgntname(c[1]);
        model.setAddr01(c[2]);
        model.setAddr02(c[3]);
        model.setAddr03(c[4]);
        model.setAddr04(c[5]);
        model.setAddr05(c[6]);
        model.setPcode(c[7]);
        model.setTrmdate(c[8]);
        model.setAgencyName(c[9]);
            
        return model;
    }
    
    
}
