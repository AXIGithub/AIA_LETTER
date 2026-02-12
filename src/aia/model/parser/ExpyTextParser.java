/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model.parser;
import aia.model.BaseModel;
import aia.model.productMapping.ExpyModel;

/**
 *
 * @author Ratino
 */
public class ExpyTextParser implements BaseTextParserInterface{

    @Override
    public BaseModel parse(String[] c) {
        ExpyModel model = new ExpyModel();
        model.setChdrnum(c[0]);
        model.setOwner(c[1]);
        model.setProdName(c[2]);
        model.setRiskCess(c[3]);
        model.setAddr01(c[4]);
        model.setAddr02(c[5]);
        model.setAddr03(c[6]);
        model.setAddr04(c[7]);
        model.setAddr05(c[8]);
        model.setCity(c[9]);
        model.setPcode(c[10]);
            
        return model;
    }
    
    
}
