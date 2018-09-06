package com.pesegato.MonkeySheet.actions;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Transform;

import static com.pesegato.MonkeySheet.MSGlobals.SPRITE_SIZE;

@Deprecated
abstract public class MSBodyAction extends MSAction{
    protected Body body;
    public void setBody(Body body){
        this.body=body;
    }

    /**
     * This method moves the body by x * SPRITE_SIZE and y * SPRITE_SIZE
     *
     * @param x movement on the X
     * @param y movement on the Y
     */

    @Override
    protected void moveSprite(float x, float y) {
        Transform t=body.getTransform();
        t.setTranslationX(t.getTranslationX()+(x * SPRITE_SIZE));
        t.setTranslationY(t.getTranslationY()+(y * SPRITE_SIZE));
        body.setTransform(t);
    }

    /**
     * This method moves the body toward absolute target position finalX, finalY
     * with speed factors x,y.
     * For each axis, if speed is positive but target is behind then no movement is performed
     * For each axis, if speed is negative but target is in front then no movement is performed
     *
     * @param x speed on the X
     * @param y speed on the Y
     * @param finalX target x coordinate
     * @param finalY target y coordinate
     * @return true if arrived at target position
     */

    @Override
    protected boolean moveFixSprite(float x, float y, float finalX, float finalY){
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
        else if (x==0){
            nextX=currentX;
        }
        else {
            nextX=Math.max(nextX, finalX);
        }
        if (y>0){
            nextY=Math.min(nextY, finalY);
        }
        else if (y==0){
            nextY=currentY;
        }
        else {
            nextY=Math.max(nextY, finalY);
        }
        t.setTranslationX(nextX);
        t.setTranslationY(nextY);
        body.setTransform(t);
        return (nextX==finalX)&&(nextY==finalY);
    }

    /**
     * This method moves the body toward absolute target position finalX
     * with speed factors x.
     * If speed is positive but target is behind then no movement is performed
     * If speed is negative but target is in front then no movement is performed
     *
     * @param x speed on the X
     * @param finalX target x coordinate
     * @return true if arrived at target position
     */

    protected boolean moveFixXSprite(float x, float finalX){
        finalX=finalX*SPRITE_SIZE;
        Transform t=body.getTransform();
        float currentX=spatial.getLocalTranslation().x;
        float nextX=SPRITE_SIZE * x + currentX;
        if (x>0){
            nextX=Math.min(nextX, finalX);
        }
        else if (x==0){
            nextX=currentX;
        }
        else {
            nextX=Math.max(nextX, finalX);
        }
        t.setTranslationX(nextX);
        return nextX==finalX;
    }

    /**
     * This method moves the body toward absolute target position finalY
     * with speed factors y.
     * If speed is positive but target is behind then no movement is performed
     * If speed is negative but target is in front then no movement is performed
     *
     * @param y speed on the Y
     * @param finalY target y coordinate
     * @return true if arrived at target position
     */

    protected boolean moveFixYSprite(float y, float finalY){
        finalY=finalY*SPRITE_SIZE;
        Transform t=body.getTransform();
        float currentY= (float) t.getTranslationY();
        float nextY=SPRITE_SIZE * y + currentY;
        if (y>0){
            nextY=Math.min(nextY, finalY);
        }
        else if (y==0){
            nextY=currentY;
        }
        else {
            nextY=Math.max(nextY, finalY);
        }
        t.setTranslationY(nextY);
        body.setTransform(t);
        return nextY==finalY;
    }

    /**
     * This method tests if the body has reached the finalX position
     * @param finalX x coordinate
     * @return true if current position matches finalX
     */

    protected boolean hasMovedFixXSprite(float finalX){
        return (body.getTransform().getTranslationX()==finalX*SPRITE_SIZE);
    }

    /**
     * This method tests if the body has reached the finalY position
     * @param finalY x coordinate
     * @return true if current position matches finalY
     */

    protected boolean hasMovedFixYSprite(float finalY){
        return (body.getTransform().getTranslationY()==finalY*SPRITE_SIZE);
    }

}
