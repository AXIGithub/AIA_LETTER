/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import aia.model.BaseModel;
import aia.model.parser.AcdTextParser;
import aia.model.parser.AcmTextParser;
import aia.model.parser.AnrpeTextParser;
import aia.model.parser.AphTextParser;
import aia.model.parser.AplTextParser;
import aia.model.parser.BTLCRTextParser;
import aia.model.parser.BaseTextParserInterface;
import aia.model.parser.ClTextParser;
import aia.model.parser.CvtTextParser;
import aia.model.parser.EPRBTextParser;
import aia.model.parser.EpmlTextParser;
import aia.model.parser.EsamTextParser;
import aia.model.parser.EstmTextParser;
import aia.model.parser.ExpyTextParser;
import aia.model.parser.KPD1TextParser;
import aia.model.parser.Kpnt2TextParser;
import aia.model.parser.KpntTextParser;
import aia.model.parser.KppaTextParser;
import aia.model.parser.La4TextParser;
import aia.model.parser.LanfTextParser;
import aia.model.parser.LiudTextParser;
import aia.model.parser.MamaTextParser;
import aia.model.parser.MapmTextParser;
import aia.model.parser.MphTextParser;
import aia.model.parser.NadTextParser;
import aia.model.parser.NcbTextParser;
import aia.model.parser.NfulTextParser;
import aia.model.parser.OadTextParser;
import aia.model.parser.OrpTextParser;
import aia.model.parser.PaphTextParser;
import aia.model.parser.PdfrTextParser;
import aia.model.parser.PdfwTextParser;
import aia.model.parser.PmrcTextParser;
import aia.model.parser.PotnrTextParser;
import aia.model.parser.PrtnrTextParser;
import aia.model.parser.PtpTextParser;
import aia.model.parser.RabTextParser;
import aia.model.parser.RopTextParser;
import aia.model.parser.SpnpTextParser;
import aia.model.parser.Srn1TextParser;
import aia.model.parser.Srn2TextParser;
import aia.model.parser.Thp1TextParser;
import aia.model.parser.TutpTextParser;
import aia.model.parser.UbcTextParser;
import aia.model.parser.WdncTextParser;
import aia.model.parser.WdnnTextParser;
import aia.model.parser.WdnwTextParser;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ratino
 */
public class TextReaderService {
    public List<BaseModel> readFromText(String path, String product) throws Exception {

        BaseTextParserInterface parser;

        switch(product.toUpperCase()) {
            case "ACD": parser = new AcdTextParser(); break;
            case "ACM": parser = new AcmTextParser(); break;
            case "ANRPE": parser = new AnrpeTextParser(); break;
            case "APH": parser = new AphTextParser(); break;
            case "APL": parser = new AplTextParser(); break;
            case "BTLCR": parser = new BTLCRTextParser(); break;
            case "CL": parser = new ClTextParser(); break;
            case "CVT": parser = new CvtTextParser(); break;
            case "EPRB": parser = new EPRBTextParser(); break;
            case "EPML": parser = new EpmlTextParser(); break;
            case "ESAM": parser = new EsamTextParser(); break;
            case "ESTM": parser = new EstmTextParser(); break;
            case "EXPY": parser = new ExpyTextParser(); break;
            case "KPD1": parser = new KPD1TextParser(); break;
            case "KPNT": parser = new KpntTextParser(); break;
            case "KPNT2": parser = new Kpnt2TextParser(); break;
            case "KPPA": parser = new KppaTextParser(); break;
            case "LA4": parser = new La4TextParser(); break;
            case "LANF": parser = new LanfTextParser(); break;
            case "LIUD": parser = new LiudTextParser(); break;
            case "MAAMA": parser = new MamaTextParser(); break;
            case "MAPM": parser = new MapmTextParser(); break;
            case "MPH": parser = new MphTextParser(); break;
            case "NAD": parser = new NadTextParser(); break;
            case "NCB": parser = new NcbTextParser(); break;
            case "NFUL": parser = new NfulTextParser(); break;
            case "OAD": parser = new OadTextParser(); break;
            case "ORP": parser = new OrpTextParser(); break;
            case "PAPH": parser = new PaphTextParser(); break;
            case "PDFR": parser = new PdfrTextParser(); break;
            case "PDFW": parser = new PdfwTextParser(); break;
            case "PMRC": parser = new PmrcTextParser(); break;
            case "POTNR": parser = new PotnrTextParser(); break;
            case "PRTNR": parser = new PrtnrTextParser(); break;
            case "PTP": parser = new PtpTextParser(); break;
            case "RAB": parser = new RabTextParser(); break;
            case "ROP": parser = new RopTextParser(); break;
            case "SPNP": parser = new SpnpTextParser(); break;
            case "SRN1": parser = new Srn1TextParser(); break;
            case "SRN2": parser = new Srn2TextParser(); break;
            case "THP1": parser = new Thp1TextParser(); break;
            case "TUTP": parser = new TutpTextParser(); break;
            case "UBC": parser = new UbcTextParser(); break;
            case "WDN-C": parser = new WdncTextParser(); break;
            case "WDN-N": parser = new WdnnTextParser(); break;
            case "WDN-W": parser = new WdnwTextParser(); break;
            default:
                throw new IllegalArgumentException("Parser untuk produk " + product + " tidak ditemukan");
        }

        List<BaseModel> list = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(path));

        String header = br.readLine();
        String line;

        while((line = br.readLine()) != null){
            String[] c = line.split("\t", -1);
//<<<<<<< HEAD
////            System.out.println("Jumlah kolom = " + c.length);
//            
//            PolisModel model = new PolisModel();
////            model.setChdrnum(c[0]);
////            model.setCnttype(c[1]);
//            model.setChdrnum(c[0]);
//            model.setCnttype(c[1]);
//            model.setOwner(c[2]);
//            model.setAlamat1(c[3]);
//            model.setAlamat2(c[4]);
//            model.setAlamat3(c[5]);
//            model.setAlamat4(c[6]);
//            model.setAlamat5(c[7]);
//            model.setCltpcode(c[8]);
//            model.setName(c[9]);
//            model.setSinstamt(c[10]);
//            model.setCntcurr(c[11]);
//            model.setBillfreq(c[12]);
//            model.setPtdate(c[13]);
//            model.setChdrdue(c[14]);
//            model.setSrcebus(c[15]);
//            model.setZmrktcd(c[16]);
//            model.setProd_name(c[17]);
//            model.setFlgsyariah(c[18]);
//            model.setSacscurbal(c[19]);
//            model.setOccdate(c[20]);
//            model.setStatcode(c[21]);
//            model.setZservstat(c[22]);
//            model.setTopup_vi(c[23]);
//            model.setCab_lb(c[24]);
//            model.setTerbilang(c[25]);
//            model.setFldent(c[26]);
//            model.setYbankkey(c[27]);
//            model.setYsustyp(c[28]);
//            model.setRek_aia(c[29]);
//            model.setVa_owner(c[30]);
//            model.setFlg_estate(c[31]);
//            model.setCltemail(c[32]);
//            model.setDte_birth(c[33]);
//            
//=======
            BaseModel model = parser.parse(c);
//>>>>>>> 69462f2de11f145ecf0622aa96b38d81add7eab2
            list.add(model);
        }

        br.close();
        return list;
        
//        while((line = br.readLine()) != null){
//            String[] c = line.split("\t");
//            
//            PolisModel model = new PolisModel();          
//            model.setChdrnum(c[0]);
//            model.setCnttype(c[1]);
//            model.setOwner(c[2]);
//            model.setAlamat1(c[3]);
//            model.setAlamat2(c[4]);
//            model.setAlamat3(c[5]);
//            model.setAlamat4(c[6]);
//            model.setAlamat5(c[7]);
//            model.setCltpcode(c[8]);
//            model.setName(c[9]);
//            model.setSinstamt(c[10]);
//            model.setCntcurr(c[11]);
//            model.setBillfreq(c[12]);
//            model.setPtdate(c[13]);
//            model.setChdrdue(c[14]);
//            model.setSrcebus(c[15]);
//            model.setZmrktcd(c[16]);
//            model.setProd_name(c[17]);
//            model.setFlgsyariah(c[18]);
//            model.setSacscurbal(c[19]);
//            model.setOccdate(c[20]);
//            model.setStatcode(c[21]);
//            model.setZservstat(c[22]);
//            model.setTopup_vi(c[23]);
//            model.setCab_lb(c[24]);
//            model.setTerbilang(c[25]);
//            model.setFldent(c[26]);
//            model.setYbankkey(c[27]);
//            model.setYsustyp(c[28]);
//            model.setRek_aia(c[29]);
//            model.setVa_owner(c[30]);
//            model.setFlg_estate(c[31]);
//            model.setCltemail(c[32]);
//            model.setDte_birth(c[33]);
//            
//            list.add(model);
//        }
//        br.close();
//        return list;
    }
}
