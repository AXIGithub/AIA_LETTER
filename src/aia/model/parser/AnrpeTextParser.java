/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model.parser;
import aia.model.BaseModel;
import aia.model.productMapping.AnrpeModel;

/**
 *
 * @author Ratino
 */
public class AnrpeTextParser implements BaseTextParserInterface{

    @Override
    public BaseModel parse(String[] c) {
        AnrpeModel model = new AnrpeModel();
        model.setCownnum(c[0]);
        model.setOwner(c[1]);
        model.setAddr01(c[2]);
        model.setAddr02(c[3]);
        model.setAddr03(c[4]);
        model.setAddr04(c[5]);
        model.setAddr05(c[6]);
        model.setPcode(c[7]);
        model.setChdrnum(c[8]);
        model.setLifcnum(c[9]);
        model.setInsured(c[10]);
        model.setPremamt1(c[11]);
        model.setPremamt2(c[12]);
        model.setStatcode(c[13]);
        model.setBillFreq(c[14]);
        model.setOccdate(c[15]);
        model.setCnttype(c[16]);
        model.setSob(c[17]);
        model.setProdName(c[18]);
        model.setSumins(c[19]);
        model.setCntcurr(c[20]);
        model.setPeriodFr(c[21]);
        model.setPeriodTo(c[22]);
        model.setGcp1(c[23]);
        model.setGcp2(c[24]);
        model.setGcp3(c[25]);
        model.setGcpTotal(c[26]);
        model.setNgb1(c[27]);
        model.setNgb2(c[28]);
        model.setNgb3(c[29]);
        model.setNgbTotal(c[30]);
        model.setAplTotal(c[31]);
        model.setLoanTotal(c[32]);
        model.setFlagEpr(c[33]);
        model.setFlgEstate(c[34]);
        model.setDob(c[35]);
        model.setCltEmail(c[36]);
        model.setPeriod(c[37]);
        model.setTbSur(c[38]);
        model.setTbDth(c[39]);
        model.setTbMat(c[40]);
            
        return model;
    }
    
    
}
