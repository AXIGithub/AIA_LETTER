/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model.parser;

import aia.model.BaseModel;
import aia.model.productMapping.La4Model;

/**
 *
 * @author Ratino
 */
public class La4TextParser implements BaseTextParserInterface{

    @Override
    public BaseModel parse(String[] c) {
        La4Model model = new La4Model();
        model.setChdrnum(c[0]);
        model.setName(c[1]);
        model.setOwner(c[2]);
        model.setAddr01(c[3]);
        model.setAddr02(c[4]);
        model.setAddr03(c[5]);
        model.setAddr04(c[6]);
        model.setAddr05(c[7]);
        model.setLetseqno(c[8]);
        model.setCntcurr(c[9]);
        model.setJatuhTem(c[10]);
        model.setSinstamt01(c[11]);
        model.setZmrktcd(c[12]);
        model.setAgent(c[13]);
        model.setPtdate(c[14]);
        model.setCltpcode(c[15]);
        model.setProduct(c[16]);
        model.setProdName(c[17]);
        model.setZservstat(c[18]);
        model.setZsbname(c[19]);
        model.setKodeSurat(c[20]);
        model.setKodeSts(c[21]);
        model.setSumins(c[22]);
        model.setAddressLL(c[23]);
        model.setNoTelp(c[24]);
        model.setAddr01(c[25]);
        model.setAddr02(c[26]);
        model.setBillfreq(c[27]);
        model.setOccdate(c[28]);
        model.setSacscurbal(c[29]);
        model.setGroupDesc(c[30]);
        model.setAddressG1(c[31]);
        model.setAddressG2(c[32]);
        model.setAddressG3(c[33]);
        model.setAddressG4(c[34]);
        model.setAddressG5(c[35]);
        model.setPhoneNo(c[36]);
        model.setCabLl(c[37]);
        model.setPeriod(c[38]);
        model.setFlagBca(c[39]);
        model.setGroupProd(c[40]);
        model.setFlgSyariah(c[41]);
        model.setSrcebus(c[42]);
        model.setStateCode(c[43]);
        
        return model;
    }

}
