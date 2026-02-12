/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model.parser;

import aia.model.BaseModel;
import aia.model.productMapping.PotnrModel;

/**
 *
 * @author Ratino
 */
public class PotnrTextParser implements BaseTextParserInterface{

    @Override
    public BaseModel parse(String[] c) {
        PotnrModel model = new PotnrModel();
        model.setChdrnum(c[0]);
        model.setOwner(c[1]);
        model.setLifasrname(c[2]);
        model.setBillFreq(c[3]);
        model.setOccdate(c[4]);
        model.setAnniverse(c[5]);
        model.setCompyrsA(c[6]);
        model.setAnp(c[7]);
        model.setInstprem(c[8]);
        model.setPrCeesTm(c[9]);
        model.setRkCeesTm(c[10]);
        model.setCnttype(c[11]);
        model.setScrebus(c[12]);
        model.setCompyrs5(c[13]);
        model.setStdprem5(c[14]);
        model.setClmamt5(c[15]);
        model.setZvpstat5(c[16]);
        model.setZvpclsta5(c[17]);
        model.setCbpamt015(c[18]);
        model.setCbpamt025(c[19]);
        model.setActprem5(c[20]);
        model.setFrepy5(c[21]);
        model.setCompyrs4(c[22]);
        model.setStdprem4(c[23]);
        model.setClmamt4(c[24]);
        model.setZvpstat4(c[25]);
        model.setZvpclsta4(c[26]);
        model.setCbpamt014(c[27]);
        model.setCbpamt024(c[28]);
        model.setActprem4(c[29]);
        model.setFrepy4(c[30]);
        model.setCompyrs3(c[31]);
        model.setStdprem3(c[32]);
        model.setClmamt3(c[33]);
        model.setZvpstat3(c[34]);
        model.setZvpclsta3(c[35]);
        model.setCbpamt013(c[36]);
        model.setCbpamt023(c[37]);
        model.setActprem3(c[38]);
        model.setFrepy3(c[39]);
        model.setCompyrs2(c[40]);
        model.setStdprem2(c[41]);
        model.setClmamt2(c[42]);
        model.setZvpstat2(c[43]);
        model.setZvpclsta2(c[44]);
        model.setCbpamt012(c[45]);
        model.setCbpamt022(c[46]);
        model.setActprem2(c[47]);
        model.setFrepy2(c[48]);
        model.setCompyrs1(c[49]);
        model.setStdprem1(c[50]);
        model.setClmamt1(c[51]);
        model.setZvpstat1(c[52]);
        model.setZvpclsta1(c[53]);
        model.setCbpamt011(c[54]);
        model.setCbpamt021(c[55]);
        model.setActprem1(c[56]);
        model.setFrepy1(c[57]);
        model.setAddr01(c[58]);
        model.setAddr02(c[59]);
        model.setAddr03(c[60]);
        model.setAddr04(c[61]);
        model.setAddr05(c[62]);
        model.setPcode(c[63]);
        model.setFlgEstate(c[64]);
        model.setRinternet(c[65]);
        model.setCity(c[66]);
        model.setProvince(c[67]);
        model.setPhoneCell(c[68]);
        model.setDteBirth(c[69]);
        model.setProdName(c[70]);
        model.setFlgrev(c[71]);
        model.setCompCode(c[72]);
        model.setSumAss(c[73]);
        model.setPeriod(c[74]);
        
        
        return model;
    }

}
