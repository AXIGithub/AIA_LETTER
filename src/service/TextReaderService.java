/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import aia.model.PolisModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ratino
 */
public class TextReaderService {
    public List<PolisModel> readFromText(String path) throws Exception {
        List<PolisModel> list = new ArrayList<>();
        
        BufferedReader br = new BufferedReader(new FileReader(path));
        
        String header = br.readLine();
        String line;
        
        while((line = br.readLine()) != null){
            String[] c = line.split("\t");
            
            PolisModel model = new PolisModel();
            model.setChdrnum(c[0]);
            model.setCnttype(c[1]);
            model.setChdrnum(c[0]);
            model.setCnttype(c[1]);
            model.setOwner(c[2]);
            model.setAlamat1(c[3]);
            model.setAlamat2(c[4]);
            model.setAlamat3(c[5]);
            model.setAlamat4(c[6]);
            model.setAlamat5(c[7]);
            model.setCltpcode(c[8]);
            model.setName(c[9]);
            model.setSinstamt(c[10]);
            model.setCntcurr(c[11]);
            model.setBillfreq(c[12]);
            model.setPtdate(c[13]);
            model.setChdrdue(c[14]);
            model.setSrcebus(c[15]);
            model.setZmrktcd(c[16]);
            model.setProd_name(c[17]);
            model.setFlgsyariah(c[18]);
            model.setSacscurbal(c[19]);
            model.setOccdate(c[20]);
            model.setStatcode(c[21]);
            model.setZservstat(c[22]);
            model.setTopup_vi(c[23]);
            model.setCab_lb(c[24]);
            model.setTerbilang(c[25]);
            model.setFldent(c[26]);
            model.setYbankkey(c[27]);
            model.setYsustyp(c[28]);
            model.setRek_aia(c[29]);
            model.setVa_owner(c[30]);
            model.setFlg_estate(c[31]);
            model.setCltemail(c[32]);
            model.setDte_birth(c[33]);
            
            list.add(model);
        }
        br.close();
        return list;
    }
}
