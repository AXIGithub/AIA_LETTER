/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model.parser;
import aia.model.BaseModel;
import aia.model.productMapping.ClModel;

/**
 *
 * @author Ratino
 */
public class ClTextParser implements BaseTextParserInterface{

    @Override
    public BaseModel parse(String[] c) {
        ClModel model = new ClModel();
        model.setLettercode(c[0]);
        // Letter code kemungkinan sama dengan CNTTYPE
//        model.setCnttype(c[0]);
        model.setDategeneral(c[1]);
        model.setChdrnum(c[2]);
        model.setOwnernum(c[3]);
        model.setOwner(c[4]);
        model.setLifcnum(c[5]);
        model.setZfilename(c[6]);
        model.setAddr01(c[7]);
        model.setAddr02(c[8]);
        model.setAddr03(c[9]);
        model.setAddr04(c[10]);
        model.setAddr05(c[11]);
        model.setPcode(c[12]);
        model.setCltphone01(c[13]);
        model.setCltphone02(c[14]);
        model.setZmrktcd(c[15]);
        model.setZmrktcddes(c[16]);
        model.setCntcurr(c[17]);
        model.setCdvamount(c[18]);
        model.setTerbilang(c[19]);
        model.setBankname(c[20]);
        model.setBankbranch(c[21]);
        model.setBankaccno(c[22]);
        model.setBankpaidto(c[23]);
        model.setFuremarks(c[24]);
        model.setPtdate(c[25]);
        model.setFirstPayd(c[26]);
        model.setFinalPayd(c[27]);
        model.setClno(c[28]);
            
        return model;
    }
    
    
}
