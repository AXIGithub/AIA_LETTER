/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.controller;

import aia.model.PolisModel;

/**
 *
 * @author Ratino
 */
public interface BasePdfGenerator {
    void generate(PolisModel polisModel, String Product, String[] params) throws Exception;
}
