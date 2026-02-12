/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model.parser;

import aia.model.BaseModel;
import aia.model.productMapping.MapmModel;

/**
 *
 * @author Ratino
 */
public class MapmTextParser implements BaseTextParserInterface{

    @Override
    public BaseModel parse(String[] c) {
        MapmModel model = new MapmModel();
        model.setProdName(c[0]);
        model.setProdId(c[1]);
        model.setChdrnum(c[2]);
        model.setCnttype(c[3]);
        model.setPremCess(c[4]);
        model.setRiskCess(c[5]);
        model.setSumins(c[6]);
        model.setOwner(c[7]);
        model.setAddr01(c[8]);
        model.setAddr02(c[9]);
        model.setAddr03(c[10]);
        model.setAddr04(c[11]);
        model.setZservstat(c[12]);
        model.setZsbname(c[13]);
        model.setLetterSta(c[14]);
        model.setStatcode(c[15]);
        model.setUserProfi(c[16]);
        model.setJobName(c[17]);
        model.setDateime(c[18]);
        model.setDtupload(c[19]);
        model.setMatamt(c[20]);
        model.setRiskdateP(c[21]);
        model.setAddr05(c[22]);
        model.setPcode(c[23]);
        model.setCabLl(c[24]);
        model.setKodeSts(c[25]);
        model.setDtupload2(c[26]);
        model.setFlgSyariah(c[27]);
        
        
        return model;
    }

}
