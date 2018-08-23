/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pesegato.MonkeySheet;

import com.pesegato.goldmonkey.Container;

/**
 * @author Pesegato
 */
public class MSContainer {

    boolean USE_COMPRESSION = false;

    public int numTiles;
    String[] sheets;
    String name;

    public MSContainer(Container c) {
        this.numTiles = c.size;
        this.name = c.id;
        this.sheets = new String[]{"Textures/MonkeySheet/" + name + (USE_COMPRESSION ? ".dds" : ".png")};
    }

    public void setPath(String path) {
        this.sheets = new String[]{path + name + (USE_COMPRESSION ? ".dds" : ".png")};
    }
}
