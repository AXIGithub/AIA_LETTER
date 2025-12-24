/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia.model.parser;

import aia.model.BaseModel;

/**
 *
 * @author Ratino
 */
public interface BaseTextParserInterface {
    BaseModel parse(String[] columns);
}
