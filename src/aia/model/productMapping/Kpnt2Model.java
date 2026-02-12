/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model.productMapping;

/**
 *
 * @author Ratino
 */
public class Kpnt2Model extends KpntModel {
    private String curr;
    private String amount;
    private String lreinDate;

    public String getCurr() {
        return curr;
    }

    public void setCurr(String curr) {
        this.curr = curr;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getLreinDate() {
        return lreinDate;
    }

    public void setLreinDate(String lreinDate) {
        this.lreinDate = lreinDate;
    }
}
