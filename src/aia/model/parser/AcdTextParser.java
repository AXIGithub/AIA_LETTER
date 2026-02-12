/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model.parser;
import aia.model.BaseModel;
import aia.model.productMapping.AcdModel;

/**
 *
 * @author Ratino
 */
public class AcdTextParser implements BaseTextParserInterface{

    @Override
    public BaseModel parse(String[] c) {
        AcdModel model = new AcdModel();
        model.setSurnamevp(c[0]);
        model.setZvpnum(c[1]);
        model.setEffstart(c[2]);
        model.setChdrnum(c[3]);
        model.setStatcode(c[4]);
        model.setSurnamepp(c[5]);
        model.setAddr01(c[6]);
        model.setAddr02(c[7]);
        model.setAddr03(c[8]);
        model.setAddr04(c[9]);
        model.setAddr05(c[10]);
        model.setPcode(c[11]);
        model.setCnttype(c[12]);
        model.setSrcebus(c[13]);
        model.setCarabayar(c[14]);
        model.setOccdate(c[15]);
        model.setCltdob(c[16]);
        model.setCltdobvp(c[17]);
        model.setCntcurr(c[18]);
        model.setProdName(c[19]);
        model.setSumins(c[20]);
        model.setInstprem(c[21]);
        model.setPrem(c[22]);
        model.setTopup(c[23]);
        model.setZvpstat(c[24]);
        model.setZvpllsta(c[25]);
        model.setZvpclsta(c[26]);
        model.setFlgEstate(c[27]);
        model.setCltEmail(c[28]);
        model.setPhoneCell(c[29]);
        model.setCrtable(c[30]);
        model.setCurrfrom(c[31]);
        model.setCurrto(c[32]);
        model.setFlgTemp(c[33]);
        model.setTotal(c[34]);
        model.setPeriod(c[35]);
            
        return model;
    }
    
    
}
