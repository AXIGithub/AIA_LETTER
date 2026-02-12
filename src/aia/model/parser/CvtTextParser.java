/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model.parser;
import aia.model.BaseModel;
import aia.model.productMapping.CvtModel;

/**
 *
 * @author Ratino
 */
public class CvtTextParser implements BaseTextParserInterface{

    @Override
    public BaseModel parse(String[] c) {
        CvtModel model = new CvtModel();
        model.setSurnamevp(c[0]);
        model.setZvpnum(c[1]);
        model.setEffstart(c[2]);
        model.setChdrnum(c[3]);
        model.setStatcode(c[4]);
        model.setSurnamepp(c[5]);
        model.setAddr01(c[6]);
        model.setAddr02(c[7]);
        model.setAddr03(c[8]);
        model.setAddr04(c[9]);
        model.setAddr05(c[10]);
        model.setCnttype(c[11]);
        model.setCarabayar(c[12]);
        model.setRcd(c[13]);
        model.setSrcebus(c[14]);
        model.setPcode(c[15]);
        model.setCltdob(c[16]);
        model.setCltdobvp(c[17]);
        model.setCntcurr(c[18]);
        model.setSumins(c[19]);
        model.setInstprem(c[20]);
        model.setPrem(c[21]);
        model.setTopup(c[22]);
        model.setZvpstat(c[23]);
        model.setZvpllsta(c[24]);
        model.setZvpclsta(c[25]);
        model.setZcsbbsc(c[26]);
        model.setZcsbphs(c[27]);
        model.setZcsbnci(c[28]);
        model.setZcsbwvc(c[29]);
        model.setZcsbtot(c[30]);
        model.setZcsbbgc(c[31]);
        model.setZcsbwdr(c[32]);
        model.setZcsbacm(c[33]);
        model.setFlgEstate(c[34]);
        model.setRinternet(c[35]);
        model.setPhoneCell(c[36]);
        model.setCrtable(c[37]);
        model.setCurrfrom(c[38]);
        model.setCurrto(c[39]);
        model.setFlgTemp(c[40]);
        model.setProdName(c[41]);
        model.setPeriod(c[42]);
            
        return model;
    }
    
    
}
