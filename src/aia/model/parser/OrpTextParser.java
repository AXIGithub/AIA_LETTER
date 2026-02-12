/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model.parser;
import aia.model.BaseModel;
import aia.model.productMapping.OrpModel;

/**
 *
 * @author Ratino
 */
public class OrpTextParser implements BaseTextParserInterface{

    @Override
    public BaseModel parse(String[] c) {
        OrpModel model = new OrpModel();
        model.setChdrnum(c[0]);
        model.setCnttype(c[1]);
        model.setSrcebus(c[2]);
        model.setOccdate(c[3]);
        model.setOwner(c[4]);
        model.setLifeAss(c[5]);
        model.setNewSrvsta(c[6]);
        model.setTrmdate(c[7]);
        model.setOldAgname(c[8]);
        model.setNewAgname(c[9]);
        model.setEffdate(c[10]);
        model.setNewAghpno(c[11]);
        model.setOldAgtno(c[12]);
        model.setAddr01(c[13]);
        model.setAddr02(c[14]);
        model.setAddr03(c[15]);
        model.setAddr04(c[16]);
        model.setAddr05(c[17]);
        model.setFlgEstate(c[18]);
        model.setProdName(c[19]);
        model.setPcode(c[20]);
        model.setDteBirth(c[21]);
        model.setCltEmail(c[22]);
        model.setAgentCd(c[23]);
        model.setLicenseCd(c[24]);
        model.setTrmdatehp1(c[25]);
            
        return model;
    }
    
    
}
