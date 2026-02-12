/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model.parser;
import aia.model.BaseModel;
import aia.model.productMapping.AplModel;

/**
 *
 * @author Ratino
 */
public class AplTextParser implements BaseTextParserInterface{

    @Override
    public BaseModel parse(String[] c) {
        AplModel model= new AplModel();
        model.setName(c[0]);
        model.setOwner(c[1]);
        model.setAddr01(c[2]);
        model.setAddr02(c[3]);
        model.setAddr03(c[4]);
        model.setChdrnum(c[5]);
        model.setCntcurr(c[6]);
        model.setPdate(c[7]);
        model.setZservstat(c[8]);
        model.setZsbname(c[9]);
        model.setAddr04(c[10]);
        model.setAddr05(c[11]);
        model.setCnttype(c[12]);
        model.setBillFreq(c[13]);
        model.setSinstamt(c[14]);
        model.setSumins(c[15]);
        model.setPcode(c[16]);
        model.setKodeSurat(c[17]);
        model.setKodeSts(c[18]);
        model.setProdName(c[19]);
        model.setCabLl(c[20]);
        model.setDtUpload(c[21]);
        model.setJatuhtem(c[22]);
        model.setFlgSyariah(c[23]);
        model.setOccdate(c[24]);
        model.setTopupVi(c[25]);
        model.setCabLb(c[26]);
        model.setSacscurbal(c[27]);
        model.setTerbilang(c[28]);
        model.setFldent(c[29]);
        model.setYbankkey(c[30]);
        model.setYsustyp(c[31]);
        model.setRekAia(c[32]);
        model.setVaOwner(c[33]);
        model.setFlgEstate(c[34]);
        model.setCltEmail(c[35]);
        model.setDteBirth(c[36]);
            
        return model;
    }
    
    
}
