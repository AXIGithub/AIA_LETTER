/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model.parser;
import aia.model.BaseModel;
import aia.model.productMapping.EstmModel;

/**
 *
 * @author Ratino
 */
public class EstmTextParser implements BaseTextParserInterface{

    @Override
    public BaseModel parse(String[] c) {
        EstmModel model = new EstmModel();
        model.setChdrnum(c[0]);
        model.setCnttype(c[1]);
        model.setCtypedes(c[2]);
        model.setSrcebus(c[3]);
        model.setBillFreq(c[4]);
        model.setCntcurr(c[5]);
        model.setCownnum(c[6]);
        model.setOwner(c[7]);
        model.setAddr01(c[8]);
        model.setAddr02(c[9]);
        model.setAddr03(c[10]);
        model.setAddr04(c[11]);
        model.setAddr05(c[12]);
        model.setPcode(c[13]);
        model.setOccdate(c[14]);
        model.setPtdate(c[15]);
        model.setTrandate(c[16]);
        model.setWaitingPr(c[17]);
        model.setOldComp(c[18]);
        model.setOldcompcat(c[19]);
        model.setNewComp(c[20]);
        model.setNewcompcat(c[21]);
        model.setFlgEstate(c[22]);
        model.setDteBirth(c[23]);
        model.setPremitotal(c[24]);
        model.setPeriod(c[25]);
            
        return model;
    }
    
    
}
