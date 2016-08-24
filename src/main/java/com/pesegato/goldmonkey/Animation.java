package com.pesegato.goldmonkey;

public class Animation {
    public String id;
    public int[] frames;
    public int centerX;
    public int centerY;
    public Animation(String id, int[] frames, int centerX, int centerY){
        this.id=id;
        this.frames=frames;
        this.centerX=centerX;
        this.centerY=centerY;
    }
}
