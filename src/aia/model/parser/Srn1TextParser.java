/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model.parser;

import aia.model.BaseModel;
import aia.model.productMapping.SpnpModel;
import aia.model.productMapping.Srn1Model;

/**
 *
 * @author Ratino
 */
public class Srn1TextParser implements BaseTextParserInterface{

    @Override
    public BaseModel parse(String[] c) {
        Srn1Model model = new Srn1Model();
        model.setNldKodeS(c[0]);
        model.setNldKodeSt(c[1]);
        model.setProdName(c[2]);
        model.setPhoneCell(c[3]);
        model.setFaxNo(c[4]);
        model.setLifcnum(c[5]);
        model.setChdrnum(c[6]);
        model.setCntcurr(c[7]);
        model.setCnttype(c[8]);
        model.setBillChnl(c[9]);
        model.setBillFreq(c[10]);
        model.setOwner(c[11]);
        model.setAddr01(c[12]);
        model.setAddr02(c[13]);
        model.setAddr03(c[14]);
        model.setAddr04(c[15]);
        model.setAddr05(c[16]);
        model.setSbName(c[17]);
        model.setName(c[18]);
        model.setZservstat(c[19]);
        model.setSumins(c[20]);
        model.setZmrktcd(c[21]);
        model.setSinstamt(c[22]);
        model.setInstfrom(c[23]);
        model.setPcode(c[24]);
        model.setCabLb(c[25]);
        model.setGroupDesc(c[26]);
        model.setAddr_Ll(c[27]);
        model.setKurs(c[28]);
        model.setAddrLl(c[29]);
        model.setFlgSyariah(c[30]);
        model.setFlagMater(c[31]);
        model.setProdSource(c[32]);
        model.setRenewalsou(c[33]);
        model.setScredt(c[34]);
        model.setBank(c[35]);
        model.setInstfrom2(c[36]);
        model.setSacscurbal(c[37]);
        model.setOccdate(c[38]);
        model.setFlagVirtu(c[39]);
        model.setChdrnumVi(c[40]);
        model.setChdrnumRe(c[41]);
        model.setGroupProd(c[42]);
        model.setStampdty(c[43]);
        model.setTotalpremi(c[44]);
        model.setNamaBank(c[45]);
        model.setInfo1(c[46]);
        model.setInfo2(c[47]);
        model.setCompany(c[48]);
        model.setCabLl(c[49]);
        model.setPeriod(c[50]);
        model.setAgent(c[51]);
        model.setFlagTempl(c[52]);
        model.setUmurPolis(c[53]);
        model.setFlagNlg(c[54]);
        model.setFlgEstate(c[55]);
        model.setDteBirth(c[56]);
        model.setTopupVi(c[57]);
        model.setDc(c[58]);
        model.setCltEmail(c[59]);
        model.setRmblphone(c[60]);
        model.setStatcode(c[61]);
        model.setFldent(c[62]);
        model.setYbankKey(c[63]);
        model.setYsustyp(c[64]);
        model.setVaOwner(c[65]);
        model.setFlagIp(c[66]);
        model.setZvpnum(c[67]);
        model.setZvpstat(c[68]);
        model.setZvpfee(c[69]);
        model.setDiscount(c[70]);
        
        
        return model;
    }

}
