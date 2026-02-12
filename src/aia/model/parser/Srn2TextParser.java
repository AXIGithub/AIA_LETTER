/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model.parser;

import aia.model.BaseModel;
import aia.model.productMapping.Srn2Model;

/**
 *
 * @author Ratino
 */
public class Srn2TextParser implements BaseTextParserInterface{

    @Override
    public BaseModel parse(String[] c) {
        Srn2Model model = new Srn2Model();
        model.setNldKodeS(c[0]);
        model.setNldKodeSt(c[1]);
        model.setProdName(c[2]);
        model.setPhoneCell(c[3]);
        model.setFaxNo(c[4]);
        model.setLifcnum(c[5]);
        model.setChdrnum(c[6]);
        model.setCntcurr(c[7]);
        model.setCnttype(c[8]);
        model.setBillFreq(c[9]);
        model.setOwner(c[10]);
        model.setAddr01(c[11]);
        model.setAddr02(c[12]);
        model.setAddr03(c[13]);
        model.setAddr04(c[14]);
        model.setAddr05(c[15]);
        model.setSbName(c[16]);
        model.setName(c[17]);
        model.setZservstat(c[18]);
        model.setSumins(c[19]);
        model.setZmrktcd(c[20]);
        model.setSinstamt(c[21]);
        model.setInstfrom(c[22]);
        model.setPcode(c[23]);
        model.setCabLb(c[24]);
        model.setGroupDesc(c[25]);
        model.setAddr_Ll(c[26]);
        model.setKurs(c[27]);
        model.setAddrLl(c[28]);
        model.setFlgSyariah(c[29]);
        model.setFlagMater(c[30]);
        model.setProdSource(c[31]);
        model.setRenewalsou(c[32]);
        model.setScredt(c[33]);
        model.setBank(c[34]);
        model.setInstfrom2(c[35]);
        model.setSacscurbal(c[36]);
        model.setOccdate(c[37]);
        model.setFlagVirtu(c[38]);
        model.setChdrnumVi(c[39]);
        model.setChdrnumRe(c[40]);
        model.setGroupProd(c[41]);
        model.setStampdty(c[42]);
        model.setTotalpremi(c[43]);
        model.setNamaBank(c[44]);
        model.setInfo1(c[45]);
        model.setInfo2(c[46]);
        model.setCompany(c[47]);
        model.setCabLl(c[48]);
        model.setPeriod(c[49]);
        model.setAgent(c[50]);
        model.setFlagTempl(c[51]);
        model.setUmurPolis(c[52]);
        model.setFlagNlg(c[53]);
        model.setFlgEstate(c[54]);
        model.setDteBirth(c[55]);
        model.setTopupVi(c[56]);
        model.setDc(c[57]);
        model.setCltEmail(c[58]);
        model.setRmblphone(c[59]);
        model.setFldent(c[60]);
        model.setYbankKey(c[61]);
        model.setYsustyp(c[62]);
        model.setVaOwner(c[63]);
        model.setFlagIp(c[64]);
        model.setZvpnum(c[65]);
        model.setZvpstat(c[66]);
        model.setZvpfee(c[67]);
        model.setDiscount(c[68]);
        
        
        return model;
    }

}
