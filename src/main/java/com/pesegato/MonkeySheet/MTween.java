/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pesegato.MonkeySheet;

import com.jme3.texture.Texture;

/**
 *
 * @author Pesegato
 */
public class MTween {
    public String name;
    MSFrame[] anim;
    MSContainer msCont;
    int[] hitbox;
    int centerX, centerY;
    public MTween(MSContainer msCont, String name, int[] pos, int[] hitbox, int size, int centerX, int centerY){
        this.msCont=msCont;
        anim=new MSFrame[pos.length];
        int lastPos=0;
        for (int i=0;i<pos.length;i++){
            lastPos+=pos[i];
            anim[i]=new MSFrame(lastPos%(size*size), lastPos/(size*size));
        }
        this.hitbox=hitbox;
        this.centerX=centerX;
        this.centerY=centerY;
    }
    public void setTextures(Texture[] sheetsX){
        for (MSFrame frame : anim) {
            frame.sheetX = sheetsX[frame.sheet];
        }
    }
}
