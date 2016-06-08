/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pesegato.MonkeySheet;

import com.pesegato.goldmonkey.GM;

/**
 *
 * @author Pesegato
 */
public class MSContainer {

    boolean USE_COMPRESSION=false;

    public int numTiles;
    String[] sheets;

    public MSContainer(String name){
        this.numTiles= GM.getInt(name);
        this.sheets=new String[]{"Textures/"+GM.getString(name)+(USE_COMPRESSION?".dds":".png")};
    }
}
