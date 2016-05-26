/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pesegato.MonkeySheet.actions;

import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Quad;
import com.pesegato.MonkeySheet.MSControl;
import static com.pesegato.MonkeySheet.MSGlobals.SPRITE_SIZE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Pesegato
 */
public abstract class MSAction {

     static Logger log = LoggerFactory.getLogger(MSAction.class);
 
    protected float msTimer;
    protected MSControl msc;
    protected Spatial spatial;
    boolean hasEnded = false;

    public static Geometry createGeometry(String name, float scaleX, float scaleY) {
        return new Geometry(name, new Quad(SPRITE_SIZE * scaleX, SPRITE_SIZE * scaleY));
    }

    protected void move(float x, float y) {
        spatial.move(SPRITE_SIZE * x, SPRITE_SIZE * y, 0);
    }
    
    protected void moveFix(float x, float y, float finalX, float finalY){
        finalX=finalX*SPRITE_SIZE;
        finalY=finalY*SPRITE_SIZE;
        float currentX=spatial.getLocalTranslation().x;
        float currentY=spatial.getLocalTranslation().y;
        float nextX=SPRITE_SIZE * x + currentX;
        float nextY=SPRITE_SIZE * y + currentY;
        if (x>0){
            nextX=Math.min(nextX, finalX);
        }
        else {
            nextX=Math.max(nextX, finalX);
        }
        if (y>0){
            nextY=Math.min(nextY, finalY);
        }
        else {
            nextY=Math.max(nextY, finalY);
        }
        spatial.setLocalTranslation(nextX,nextY,0);
    }

    protected boolean hasMovedFixX(float finalX){
        return (spatial.getLocalTranslation().x==finalX*SPRITE_SIZE);
    }
    
    protected boolean hasMovedFixY(float finalY){
        return (spatial.getLocalTranslation().y==finalY*SPRITE_SIZE);
    }
    
    protected void init(Spatial spatial) {
        msTimer = 0;
        hasEnded = false;
        this.spatial = spatial;
        if (msc != null) {
            msc.msAction = this;
        }
        whatPlay(msc);
        init();
    }

    protected void controlUpdate(float tpf) {
        msUpdate(tpf);
        this.msTimer += tpf;
    }

    public void whatPlay(MSControl msc) {
    }

    abstract protected void msUpdate(float tpf);

    protected boolean hasEnded() {
        return hasEnded;
    }

    final public void terminatedAnim() {
        hasEnded = true;
        maybeEnd();
    }
    
    final public boolean maybeEnd() {
        boolean ended=hasEnded();
        log.trace("{} maybe end {} {}",msc, this, ended);
        if (ended){
            finish();
        }
        return ended;
    }

    public void init() {
    }
    
    public void finish() {
    }

}
