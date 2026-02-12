/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.controller;

import aia.poa_product.CreatePdfACD;
import aia.poa_product.CreatePdfACM;
import aia.poa_product.CreatePdfANRPE;
import aia.poa_product.CreatePdfANST;
import aia.poa_product.CreatePdfAPH;
import aia.poa_product.CreatePdfAPL;
import aia.poa_product.CreatePdfBTLCR;
import aia.poa_product.CreatePdfORP;
import aia.poa_product.CreatePdfCL;
import aia.poa_product.CreatePdfCVT;
import aia.poa_product.CreatePdfDVD1;
import aia.poa_product.CreatePdfEPML;
import aia.poa_product.CreatePdfEPRB;
import aia.poa_product.CreatePdfESAM;
import aia.poa_product.CreatePdfESTM;
import aia.poa_product.CreatePdfEXPY;
import aia.poa_product.CreatePdfKPDI1;
import aia.poa_product.CreatePdfKPNT;
import aia.poa_product.CreatePdfKPNT2;
import aia.poa_product.CreatePdfKPPA;
import aia.poa_product.CreatePdfLANF;
import aia.poa_product.CreatePdfLIUD;
import aia.poa_product.CreatePdfMAAMA;
import aia.poa_product.CreatePdfMAPM;
import aia.poa_product.CreatePdfMPH;
import aia.poa_product.CreatePdfNAD;
import aia.poa_product.CreatePdfNCB;
import aia.poa_product.CreatePdfNFUL;
import aia.poa_product.CreatePdfOAD;
import aia.poa_product.CreatePdfPAPH;
import aia.poa_product.CreatePdfPDFR;
import aia.poa_product.CreatePdfPDFW;
import aia.poa_product.CreatePdfPMRC;
import aia.poa_product.CreatePdfPOTNR;
import aia.poa_product.CreatePdfPRTNR;
import aia.poa_product.CreatePdfPTP;
import aia.poa_product.CreatePdfRAB;
import aia.poa_product.CreatePdfROP;
import aia.poa_product.CreatePdfSPNP;
import aia.poa_product.CreatePdfSRN1;
import aia.poa_product.CreatePdfSRN2;
import aia.poa_product.CreatePdfSSNA;
import aia.poa_product.CreatePdfTHP1;
import aia.poa_product.CreatePdfTUTP;
import aia.poa_product.CreatePdfUBC;
import aia.poa_product.CreatePdfWDNC;
import aia.poa_product.CreatePdfWDNN;
import aia.poa_product.CreatePdfWDNW;

/**
 *
 * @author Ratino
 */
public class LetterFactory {
   
    public static BasePdfGenerator getPdfTemplate(String product){
        if(product == null) return null;
        switch (product.toUpperCase()){
            case "ACD":
                return new CreatePdfACD();
            case "ACM":
                return new CreatePdfACM();
            case "ANRPE":
                return new CreatePdfANRPE();
            case "ANST":
                return new CreatePdfANST();
            case "CL":
                return new CreatePdfCL();
            case "CVT":
                return new CreatePdfCVT();
            case "ESAM":
                return new CreatePdfESAM();
            case "ESTM":
                return new CreatePdfESTM();
            case "EXPY":
                return new CreatePdfEXPY();
            case "KPNT":
                return new CreatePdfKPNT();
            case "KPNT2":
                return new CreatePdfKPNT2();
            case "MPH":
                return new CreatePdfMPH();
            case "NCB":
                return new CreatePdfNCB();
            case "ORP":
                return new CreatePdfORP();
            case "PDFR":
                return new CreatePdfPDFR();
            case "PDFW":
                return new CreatePdfPDFW();
            case "POTNR":
                return new CreatePdfPOTNR();
            case "PRTNR":
                return new CreatePdfPRTNR();
            case "RAB":
                return new CreatePdfRAB();
            case "UBC":
                return new CreatePdfUBC();
            case "WDNC":
                return new CreatePdfWDNC();
            case "WDNN":
                return new CreatePdfWDNN();
            case "WDNW":
                return new CreatePdfWDNW();
            case "APH":
                return new CreatePdfAPH();
            case "APL":
                return new CreatePdfAPL();
            case "BTLCR":
                return new CreatePdfBTLCR();
            case "DVD1":
                return new CreatePdfDVD1();
            case "EPML":
                return new CreatePdfEPML();
            case "EPRB":
                return new CreatePdfEPRB();
            case "KPDI1": 
                return new CreatePdfKPDI1();
            case "KPPA":
                return new CreatePdfKPPA();
            case "LANF":
                return new CreatePdfLANF();
            case "LIUD":
                return new CreatePdfLIUD();
            case "MAAMA":
                return new CreatePdfMAAMA();
            case "MAPM":
                return new CreatePdfMAPM();
            case "NAD":
                return new CreatePdfNAD();
            case "NFUL":
                return new CreatePdfNFUL();
            case "OAD":
                return new CreatePdfOAD();
            case "PAPH":
                return new CreatePdfPAPH();
            case "PMRC":
                return new CreatePdfPMRC();
            case "PTP":
                return new CreatePdfPTP();
            case "ROP":
                return new CreatePdfROP();
            case "SPNP":
                return new CreatePdfSPNP();
            case "SRN1":
                return new CreatePdfSRN1();
            case "SRN2":
                return new CreatePdfSRN2();
            case "SSNA":
                return new CreatePdfSSNA();
            case "THP1":
                return new CreatePdfTHP1();
            case "TUTP":
                return new CreatePdfTUTP();
            default:
                 throw new IllegalArgumentException("Template untuk produk " + product + " tidak ditemukan!\nHubungi IT");
        }
    }
}
