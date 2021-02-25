/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pesegato.MonkeySheet;

import com.jme3.texture.Texture;

/**
 * @author Pesegato
 */
public class MSFrame {
    int position;
    int sheet;
    Texture sheetX;

    public MSFrame(int position, int sheet) {
        this.position = position;
        this.sheet = sheet;
    }

    public int getPosition() {
        return position;
    }

    public void setTexture(Texture sheetX) {
        this.sheetX = sheetX;
    }
}
