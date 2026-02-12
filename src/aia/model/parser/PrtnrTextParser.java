/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model.parser;

import aia.model.BaseModel;
import aia.model.productMapping.PrtnrModel;

/**
 *
 * @author Ratino
 */
public class PrtnrTextParser implements BaseTextParserInterface{

    @Override
    public BaseModel parse(String[] c) {
        PrtnrModel model = new PrtnrModel();
        model.setChdrnum(c[0]);
        model.setCrtable(c[1]);
        model.setAnniverse(c[2]);
        model.setInstprem(c[3]);
        model.setCompyrsA(c[4]);
        model.setOwner(c[5]);
        model.setBillFreq(c[6]);
        model.setCnttype(c[7]);
        model.setScrebus(c[8]);
        model.setCompyrs5(c[9]);
        model.setStdprem5(c[10]);
        model.setClmamt5(c[11]);
        model.setZvpstat5(c[12]);
        model.setZvpclsta5(c[13]);
        model.setCbpamt015(c[14]);
        model.setCbpamt025(c[15]);
        model.setActprem5(c[16]);
        model.setFrepy5(c[17]);
        model.setCompyrs4(c[18]);
        model.setStdprem4(c[19]);
        model.setClmamt4(c[20]);
        model.setZvpstat4(c[21]);
        model.setZvpclsta4(c[22]);
        model.setCbpamt014(c[23]);
        model.setCbpamt024(c[24]);
        model.setActprem4(c[25]);
        model.setFrepy4(c[26]);
        model.setCompyrs3(c[27]);
        model.setStdprem3(c[28]);
        model.setClmamt3(c[29]);
        model.setZvpstat3(c[30]);
        model.setZvpclsta3(c[31]);
        model.setCbpamt013(c[32]);
        model.setCbpamt023(c[33]);
        model.setActprem3(c[34]);
        model.setFrepy3(c[35]);
        model.setCompyrs2(c[36]);
        model.setStdprem2(c[37]);
        model.setClmamt2(c[38]);
        model.setZvpstat2(c[39]);
        model.setZvpclsta2(c[40]);
        model.setCbpamt012(c[41]);
        model.setCbpamt022(c[42]);
        model.setActprem2(c[43]);
        model.setFrepy2(c[44]);
        model.setCompyrs1(c[45]);
        model.setStdprem1(c[46]);
        model.setClmamt1(c[47]);
        model.setZvpstat1(c[48]);
        model.setZvpclsta1(c[49]);
        model.setCbpamt011(c[50]);
        model.setCbpamt021(c[51]);
        model.setActprem1(c[52]);
        model.setFrepy1(c[53]);
        model.setAddr01(c[54]);
        model.setAddr02(c[55]);
        model.setAddr03(c[56]);
        model.setAddr04(c[57]);
        model.setAddr05(c[58]);
        model.setPcode(c[59]);
        model.setFlgEstate(c[60]);
        model.setRinternet(c[61]);
        model.setCity(c[62]);
        model.setProvince(c[63]);
        model.setPhoneCell(c[64]);
        model.setDteBirth(c[65]);
        model.setCompCode(c[66]);
        model.setProdName(c[67]);
        model.setPeriod(c[68]);
        
        
        return model;
    }

}
