/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pesegato.MonkeySheet;

import com.pesegato.goldmonkey.Container;

/**
 *
 * @author Pesegato
 */
public class MSContainer {

    boolean USE_COMPRESSION=false;

    public int numTiles;
    String[] sheets;

    public MSContainer(String name){
        Container c=MonkeySheetAppState.getContainer(name);
        this.numTiles= c.size;
        this.sheets=new String[]{"Textures/"+name+(USE_COMPRESSION?".dds":".png")};
    }
}
