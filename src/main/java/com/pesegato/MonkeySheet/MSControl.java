/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pesegato.MonkeySheet;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import com.pesegato.MonkeySheet.actions.MSAction;
import com.pesegato.timing.Timeable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Pesegato
 */
public class MSControl extends AbstractControl {

    static Logger log = LoggerFactory.getLogger(MSControl.class);

    public MTween anim;
    public String animation;
    public int position;
    boolean runOnce = false;
    MSAnimationManager animManager;
    public MSAction msAction;

    public MSControl(String anim, Timeable timeable) {
        playForever(anim);
        MonkeySheetAppState.timeable = timeable;
    }

    public MSControl(MSAnimationManager animManager) {
        this.animManager = animManager;
    }

    final public void playForever(String ani) {
        log.debug("now playing animation {}", ani);
        anim = MonkeySheetAppState.getAnim(ani);
        position = 0;
        runOnce = false;
        if (anim == null) {
            log.warn("Running uninitialized animation {}", ani);
        }
    }

    public void playOnce(String ani) {
        log.debug("now playing ONCE animation {}", ani);
        anim = MonkeySheetAppState.getAnim(ani);
        position = 0;
        runOnce = true;
        if (anim == null) {
            log.warn("Running uninitialized animation " + ani);
        }
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (MonkeySheetAppState.tTPF == 0) {
            log.trace("position: {} - {}", position, anim.anim[position].position);
            position++;
            if (position > anim.anim.length - 1) {
                position = 0;
                if (runOnce) {
                    if (animManager != null) {
                        log.trace("loading animManager {}", animManager);
                        animManager.whatPlay(this);
                    }
                    if (msAction != null) {
                        log.trace("end of MSAction {}", msAction);
                        msAction.terminatedAnim();
                        msAction.whatPlay(this);
                    }
                }
            }
        }

    }

    public int getCurrentHitbox() {
        return anim.hitbox[position];
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

}
