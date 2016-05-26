/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pesegato.MonkeySheet;

/**
 *
 * @author Pesegato
 */
public class MSContainer {

    public int numTiles;
    String[] sheets;

    public MSContainer(int numTiles, String... sheets){
        this.numTiles=numTiles;
        this.sheets=sheets;
    }
}
