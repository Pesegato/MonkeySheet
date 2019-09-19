/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pesegato.MonkeySheet.actions;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
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
    public boolean hasEnded = false;
    public boolean uncalledFinish = true;

    public static Geometry createGeometry(String name, float scaleX, float scaleY) {
        return new Geometry(name, new Quad(SPRITE_SIZE * scaleX, SPRITE_SIZE * scaleY));
    }



    /**
     * This method moves the spatial by x * SPRITE_SIZE and y * SPRITE_SIZE
     *
     * @param x movement on the X
     * @param y movement on the Y
     */

    protected void moveSprite(float x, float y) {
        spatial.move(SPRITE_SIZE * x, SPRITE_SIZE * y, 0);
    }

    /**
     * This method moves the spatial toward absolute target position finalX, finalY
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

    protected boolean moveFixSprite(float x, float y, float finalX, float finalY) {
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
        return (nextX==finalX)&&(nextY==finalY);
    }

    /**
     * This method moves the spatial toward absolute target position finalX, finalY
     * with speed factors x,y.
     * For each axis, if speed is positive but target is behind then no movement is performed
     * For each axis, if speed is negative but target is in front then no movement is performed
     *
     * @param x      speed on the X
     * @param y      speed on the Y
     * @param finalX target x coordinate
     * @param finalY target y coordinate
     * @return true if arrived at target position
     */

    protected boolean moveFixPixels(float x, float y, float finalX, float finalY) {
        float currentX = spatial.getLocalTranslation().x;
        float currentY = spatial.getLocalTranslation().y;
        float nextX = x + currentX;
        float nextY = y + currentY;
        if (x > 0) {
            nextX = Math.min(nextX, finalX);
        } else {
            nextX = Math.max(nextX, finalX);
        }
        if (y > 0) {
            nextY = Math.min(nextY, finalY);
        } else {
            nextY = Math.max(nextY, finalY);
        }
        spatial.setLocalTranslation(nextX, nextY, 0);
        return (nextX == finalX) && (nextY == finalY);
    }

    /**
     * This method moves the spatial toward absolute target position finalX
     * with speed factors x.
     * If speed is positive but target is behind then no movement is performed
     * If speed is negative but target is in front then no movement is performed
     *
     * @param x speed on the X
     * @param finalX target x coordinate
     * @return true if arrived at target position
     * @deprecated use Sprite or Pixels
     */

    protected boolean moveFixX(float x, float finalX){
        finalX=finalX*SPRITE_SIZE;
        float currentX=spatial.getLocalTranslation().x;
        float currentY=spatial.getLocalTranslation().y;
        float nextX=SPRITE_SIZE * x + currentX;
        if (x>0){
            nextX=Math.min(nextX, finalX);
        }
        else {
            nextX=Math.max(nextX, finalX);
        }
        spatial.setLocalTranslation(nextX,currentY,0);
        return nextX==finalX;
    }

    /**
     * This method moves the spatial toward absolute target position finalX
     * with speed factors x.
     * If speed is positive but target is behind then no movement is performed
     * If speed is negative but target is in front then no movement is performed
     *
     * @param x      speed on the X
     * @param finalX target x coordinate
     * @return true if arrived at target position
     */

    protected boolean moveFixXSprite(float x, float finalX) {
        finalX = finalX * SPRITE_SIZE;
        float currentX = spatial.getLocalTranslation().x;
        float currentY = spatial.getLocalTranslation().y;
        float nextX = SPRITE_SIZE * x + currentX;
        if (x > 0) {
            nextX = Math.min(nextX, finalX);
        } else {
            nextX = Math.max(nextX, finalX);
        }
        spatial.setLocalTranslation(nextX, currentY, 0);
        return nextX == finalX;
    }

    /**
     * This method moves the spatial toward absolute target position finalX
     * with speed factors x.
     * If speed is positive but target is behind then no movement is performed
     * If speed is negative but target is in front then no movement is performed
     *
     * @param x      speed on the X
     * @param finalX target x coordinate
     * @return true if arrived at target position
     */

    protected boolean moveFixXPixels(float x, int finalX) {
        int currentX = (int) spatial.getLocalTranslation().x;
        int currentY = (int) spatial.getLocalTranslation().y;
        float nextX = x + currentX;
        if (x > 0) {
            nextX = Math.min(nextX, finalX);
        } else {
            nextX = Math.max(nextX, finalX);
        }
        spatial.setLocalTranslation(nextX, currentY, 0);
        return nextX == finalX;
    }

    /**
     * This method moves the spatial toward absolute target position finalY
     * with speed factors y.
     * If speed is positive but target is behind then no movement is performed
     * If speed is negative but target is in front then no movement is performed
     *
     * @param y speed on the Y
     * @param finalY target y coordinate
     * @return true if arrived at target position
     * @deprecated use moveFixYPixels or moveFixYSprite instead
     */

    protected boolean moveFixY(float y, float finalY){
        finalY=finalY*SPRITE_SIZE;
        float currentX=spatial.getLocalTranslation().x;
        float currentY=spatial.getLocalTranslation().y;
        float nextY=SPRITE_SIZE * y + currentY;
        if (y>0){
            nextY=Math.min(nextY, finalY);
        }
        else {
            nextY=Math.max(nextY, finalY);
        }
        spatial.setLocalTranslation(currentX,nextY,0);
        return nextY==finalY;
    }

    /**
     * This method moves the spatial toward absolute target position finalY
     * with speed factors y (multiplied by SPRITE_SIZE)
     * If speed is positive but target is behind then no movement is performed
     * If speed is negative but target is in front then no movement is performed
     *
     * @param y      speed on the Y
     * @param finalY target y coordinate
     * @return true if arrived at target position
     */

    protected boolean moveFixYSprite(float y, float finalY) {
        finalY = finalY * SPRITE_SIZE;
        float currentX = spatial.getLocalTranslation().x;
        float currentY = spatial.getLocalTranslation().y;
        float nextY = SPRITE_SIZE * y + currentY;
        if (y > 0) {
            nextY = Math.min(nextY, finalY);
        } else {
            nextY = Math.max(nextY, finalY);
        }
        spatial.setLocalTranslation(currentX, nextY, 0);
        return nextY == finalY;
    }

    /**
     * This method moves the spatial toward absolute target position finalY
     * with speed factors y (specified in Pixel units).
     * If speed is positive but target is behind then no movement is performed
     * If speed is negative but target is in front then no movement is performed
     *
     * @param y      speed on the Y
     * @param finalY target y coordinate
     * @return true if arrived at target position
     */

    protected boolean moveFixYPixels(float y, int finalY) {
        int currentX = (int) spatial.getLocalTranslation().x;
        int currentY = (int) spatial.getLocalTranslation().y;
        float nextY = y + currentY;
        if (y > 0) {
            nextY = Math.min(nextY, finalY);
        } else {
            nextY = Math.max(nextY, finalY);
        }
        spatial.setLocalTranslation(currentX, nextY, 0);
        return nextY == finalY;
    }

    /**
     * This method tests if the spatial has reached the finalX position
     * @param finalX x coordinate
     * @return true if current position matches finalX
     */

    protected boolean hasMovedFixXSprite(float finalX){
        return (spatial.getLocalTranslation().x==finalX*SPRITE_SIZE);
    }

    /**
     * This method tests if the spatial has reached the finalX position
     *
     * @param finalX x coordinate
     * @return true if current position matches finalX
     */

    protected boolean hasMovedFixXPixels(float finalX) {
        return (spatial.getLocalTranslation().x == finalX);
    }

    /**
     * This method tests if the spatial has reached the finalX position
     *
     * @param finalX x coordinate
     * @return true if current position matches finalX
     * @deprecated use either Sprite or Pixels
     */

    protected boolean hasMovedFixX(float finalX) {
        return (spatial.getLocalTranslation().x == finalX * SPRITE_SIZE);
    }

    /**
     * This method tests if the spatial has reached the finalY position (multiplied by SPRITE_SIZE)
     * @param finalY x coordinate
     * @return true if current position matches finalY
     */
    protected boolean hasMovedFixYSprite(float finalY) {
        return (spatial.getLocalTranslation().y==finalY*SPRITE_SIZE);
    }

    /**
     * This method tests if the spatial has reached the finalY position (specified in Pixel units)
     *
     * @param finalY x coordinate
     * @return true if current position matches finalY
     */
    protected boolean hasMovedFixYPixels(float finalY) {
        return (spatial.getLocalTranslation().y == finalY);
    }

    /**
     * This method tests if the spatial has reached the finalY position
     *
     * @param finalY x coordinate
     * @return true if current position matches finalY
     * @deprecated use hasMovedFixYSprite or hasMovedFixYPixels instead
     */
    protected boolean hasMovedFixY(float finalY) {
        return (spatial.getLocalTranslation().y == finalY * SPRITE_SIZE);
    }

    protected Vector2f getUVector(Vector3f v, int x2, int y2) {
        return new Vector2f(x2 - v.x, y2 - v.y).normalizeLocal();
    }

    protected void init(Spatial spatial) {
        msTimer = 0;
        hasEnded = false;
        uncalledFinish = true;
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
            if (uncalledFinish) {
                finish();
                uncalledFinish = false;
            }
        }
        return ended;
    }

    /**
     * Called on start
     */

    public void init() {
    }

    /**
     * Called when trying to interrupt the current action
     * @return true if can be interrupted
     */

    public MSTransitionAction onInterruptAttempt(){
        return null;
    }

    /**
     * Called when switching to another MSAction before this is finished normally
     */

    public void interrupted(){
    }

    /**
     * Called when finishes normally
     */

    public void finish() {
    }

}
