/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model.productMapping;

import aia.model.BaseModel;

/**
 *
 * @author Ratino
 */
public class EpmlModel extends BaseModel{
    private String clientNum;
    private String rmblPhone;
    private String srcebus;
    private String life;
    private String insured;
    private String sinstamt;
    private String billchnl;
    private String longdesc;
    private String ptdate;
    private String benef;
    private String batctrcde;
    private String trdtp;

    public String getClientNum() {
        return clientNum;
    }

    public void setClientNum(String clientNum) {
        this.clientNum = clientNum;
    }

    public String getRmblPhone() {
        return rmblPhone;
    }

    public void setRmblPhone(String rmblPhone) {
        this.rmblPhone = rmblPhone;
    }

    public String getSrcebus() {
        return srcebus;
    }

    public void setSrcebus(String srcebus) {
        this.srcebus = srcebus;
    }

    public String getLife() {
        return life;
    }

    public void setLife(String life) {
        this.life = life;
    }

    public String getInsured() {
        return insured;
    }

    public void setInsured(String insured) {
        this.insured = insured;
    }

    public String getSinstamt() {
        return sinstamt;
    }

    public void setSinstamt(String sinstamt) {
        this.sinstamt = sinstamt;
    }

    public String getBillchnl() {
        return billchnl;
    }

    public void setBillchnl(String billchnl) {
        this.billchnl = billchnl;
    }

    public String getLongdesc() {
        return longdesc;
    }

    public void setLongdesc(String longdesc) {
        this.longdesc = longdesc;
    }

    public String getPtdate() {
        return ptdate;
    }

    public void setPtdate(String ptdate) {
        this.ptdate = ptdate;
    }

    public String getBenef() {
        return benef;
    }

    public void setBenef(String benef) {
        this.benef = benef;
    }

    public String getBatctrcde() {
        return batctrcde;
    }

    public void setBatctrcde(String batctrcde) {
        this.batctrcde = batctrcde;
    }

    public String getTrdtp() {
        return trdtp;
    }

    public void setTrdtp(String trdtp) {
        this.trdtp = trdtp;
    }
    
    
}
