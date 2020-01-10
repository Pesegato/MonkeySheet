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
    public MSFrame[] anim;
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
        this.hitbox = hitbox;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public void setTextures(Texture[] sheetsX) {
        int sheet = -1;
        try {
            for (MSFrame frame : anim) {
                sheet = frame.sheet;
                frame.sheetX = sheetsX[sheet];
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            System.out.println("Missing sheet " + sheet + "!");
            System.out.println("Unless you are using multisheet, you probably referenced frames outside of the sheet 0.");
            System.out.println("For example, when using a Container with size 3, frames must be between 0 and 8!");
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
