package com.pesegato.MonkeySheet.actions;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Transform;

import static com.pesegato.MonkeySheet.MSGlobals.SPRITE_SIZE;

abstract public class MSBodyAction extends MSAction{
    protected Body body;
    public void setBody(Body body){
        this.body=body;
    }

    @Override
    protected void move(float x, float y) {
        Transform t=body.getTransform();
        t.setTranslationX(t.getTranslationX()+(x * SPRITE_SIZE));
        t.setTranslationY(t.getTranslationY()+(y * SPRITE_SIZE));
        body.setTransform(t);
    }

    @Override
    protected boolean moveFix(float x, float y, float finalX, float finalY){
        finalX=finalX*SPRITE_SIZE;
        finalY=finalY*SPRITE_SIZE;
        Transform t=body.getTransform();
        float currentX= (float) t.getTranslationX();
        float currentY= (float) t.getTranslationY();
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
        t.setTranslationX(nextX);
        t.setTranslationY(nextY);
        body.setTransform(t);
        return (nextX==finalX)&&(nextY==finalY);
    }

}
