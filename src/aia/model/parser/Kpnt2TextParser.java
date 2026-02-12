/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model.parser;
import aia.model.BaseModel;
import aia.model.productMapping.Kpnt2Model;

/**
 *
 * @author Ratino
 */
public class Kpnt2TextParser implements BaseTextParserInterface{

    @Override
    public BaseModel parse(String[] c) {
        Kpnt2Model model = new Kpnt2Model();
        model.setChdrnum(c[0]);
        model.setCnttype(c[1]);
        model.setSrcebus(c[2]);
        model.setOwner(c[3]);
        model.setCurr(c[4]);
        model.setAmount(c[5]);
        model.setLreinDate(c[6]);
        model.setAddr01(c[7]);
        model.setAddr02(c[8]);
        model.setAddr03(c[9]);
        model.setAddr04(c[10]);
        model.setAddr05(c[11]);
        model.setPcode(c[12]);
        model.setLifasrname(c[13]);
        model.setOccdate(c[14]);
        model.setPtdate(c[15]);
        model.setProdName(c[16]);
        model.setFlgEstate(c[17]);
        model.setCltEmail(c[18]);
        model.setPhoneCell(c[19]);
        model.setDteBirth(c[20]);
        model.setPeriod(c[21]);
            
        return model;
    }
    
    
}
