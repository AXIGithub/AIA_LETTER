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
public class UbcModel extends BaseModel {
    private String lifasrname;
    private String sinstamt;
    private String instprem;
    private String curr;
    private String sumins;
    private String zdistdsc;
    private String srcebus;
    private String periode;

    public String getSinstamt() {
        return sinstamt;
    }

    public void setSinstamt(String sinstamt) {
        this.sinstamt = sinstamt;
    }

    public String getInstprem() {
        return instprem;
    }

    public void setInstprem(String instprem) {
        this.instprem = instprem;
    }

    public String getCurr() {
        return curr;
    }

    public void setCurr(String curr) {
        this.curr = curr;
    }

    public String getZdistdsc() {
        return zdistdsc;
    }

    public void setZdistdsc(String zdistdsc) {
        this.zdistdsc = zdistdsc;
    }

    
    
    public String getLifasrname() {
        return lifasrname;
    }

    public void setLifasrname(String lifasrname) {
        this.lifasrname = lifasrname;
    }

    public String getSumins() {
        return sumins;
    }

    public void setSumins(String sumins) {
        this.sumins = sumins;
    }

    public String getSrcebus() {
        return srcebus;
    }

    public void setSrcebus(String srcebus) {
        this.srcebus = srcebus;
    }

    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }
    
    
}
