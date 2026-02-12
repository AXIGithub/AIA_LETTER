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
public class RabModel extends BaseModel {
    private String lifasrname;
    private String basicprem;
    private String sumins;
    private String srcebus;
    private String periode;

    public String getLifasrname() {
        return lifasrname;
    }

    public void setLifasrname(String lifasrname) {
        this.lifasrname = lifasrname;
    }

    public String getBasicprem() {
        return basicprem;
    }

    public void setBasicprem(String basicprem) {
        this.basicprem = basicprem;
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
