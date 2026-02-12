/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model.parser;
import aia.model.BaseModel;
import aia.model.productMapping.WdncModel;
import aia.model.productMapping.WdnnModel;

/**
 *
 * @author Ratino
 */
public class WdnnTextParser implements BaseTextParserInterface{

    @Override
    public BaseModel parse(String[] c) {
        WdnnModel model = new WdnnModel();
        model.setChdrnum(c[0]);
        model.setCnttype(c[1]);
        model.setRepnum(c[2]);
        model.setOwner(c[3]);
        model.setAddr01(c[4]);
        model.setAddr02(c[5]);
        model.setAddr03(c[6]);
        model.setAddr04(c[7]);
        model.setAddr05(c[8]);
        model.setPcode(c[9]);
        model.setSumins(c[10]);
        model.setInsured(c[11]);
        model.setHpropdte(c[12]);
        model.setZservstat(c[13]);
        model.setZmrktcd(c[14]);
        model.setNldKSrt(c[15]);
        model.setNldKSts(c[16]);
        model.setProdName(c[17]);
        model.setAddrL0(c[18]);
        model.setAddrL1(c[19]);
        model.setAddrL2(c[20]);
        model.setNoFax(c[21]);
        model.setCabLl(c[22]);
        model.setTglUpload(c[23]);
        model.setInfolayan(c[24]);
        model.setNoFax2(c[25]);
        model.setAddrL12(c[26]);
        model.setAddrL22(c[27]);
        model.setFlgSyariah(c[28]);
        model.setFlgEstate(c[29]);
        model.setCltEmail(c[30]);
        model.setPhoneCell(c[31]);
            
        return model;
    }
    
    
}
