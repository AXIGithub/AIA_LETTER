/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model.parser;
import aia.model.BaseModel;
import aia.model.productMapping.NcbModel;

/**
 *
 * @author Ratino
 */
public class NcbTextParser implements BaseTextParserInterface{

    @Override
    public BaseModel parse(String[] c) {
        NcbModel model = new NcbModel();
        model.setChdrnum(c[0]);
        model.setCnttype(c[1]);
        model.setSrcebus(c[2]);
        model.setOccdate(c[3]);
        model.setCntcurr(c[4]);
        model.setCrtable(c[5]);
        model.setCownnum(c[6]);
        model.setSurnamepp(c[7]);
        model.setAddr01(c[8]);
        model.setAddr02(c[9]);
        model.setAddr03(c[10]);
        model.setAddr04(c[11]);
        model.setAddr05(c[12]);
        model.setPcode(c[13]);
        model.setPhoneCell(c[14]);
        model.setDteBirth(c[15]);
        model.setCltEmail(c[16]);
        model.setClntnum(c[17]);
        model.setSurnamela(c[18]);
        model.setDtebirthla(c[19]);
        model.setProdName(c[20]);
        model.setFlgEstate(c[21]);
        model.setNcbAmount(c[22]);
        model.setTerbilang(c[23]);
        model.setPeriod(c[24]);
            
        return model;
    }
    
    
}
