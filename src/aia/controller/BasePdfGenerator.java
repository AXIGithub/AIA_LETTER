/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.controller;

import aia.model.BaseModel;
import java.util.List;

/**
 *
 * @author Ratino
 */
public interface BasePdfGenerator {
//    void generate(BaseModel baseModel, String Product, String cetak, String[] params) throws Exception;
    void generate(List<BaseModel> dataList, String Product, String cetak, String[] params) throws Exception;
    String getFileName();
    String getBarcodes();
    float getYaddr();
    float getXbox();
    public abstract boolean isPriority(BaseModel model);
}
