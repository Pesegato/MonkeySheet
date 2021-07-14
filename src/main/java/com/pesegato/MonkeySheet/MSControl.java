/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pesegato.MonkeySheet;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.pesegato.MonkeySheet.actions.MSAction;
import com.pesegato.timing.Timeable;

/**
 * @author Pesegato
 */
public class MSControl extends MSSpriteControl {

    boolean runOnce = false;
    MSAnimationManager animManager;
    public MSAction msAction;

    public MSControl() {
    }

    public MSControl(String anim, Timeable timeable) {
        playForever(anim);
        MonkeySheetAppState.timeable = timeable;
    }

    public MSControl(MSAnimationManager animManager) {
        this.animManager = animManager;
    }

    final public void playForever(String ani) {
        anim = MonkeySheetAppState.getAnim(ani);
        position = 0;
        runOnce = false;
        if (anim == null) {
            log.warn("Running UNINITIALIZED animation {}, GOING TO CRASH VERY SOON!!!", ani);
            return;
        }
        animation = ani;
        log.debug("now playing FOREVER animation {}/{}", anim.msCont.name, ani);
    }

    public void playOnce(String ani) {
        anim = MonkeySheetAppState.getAnim(ani);
        position = 0;
        runOnce = true;
        if (anim == null) {
            log.warn("Running UNINITIALIZED animation {}, GOING TO CRASH VERY SOON!!!" + ani);
            return;
        }
        animation = ani;
        log.debug("now playing ONCE animation {}/{}", anim.msCont.name, ani);
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (MonkeySheetAppState.tTPF == 0) {
            log.trace("position: {}/{} {} - {}", anim.msCont.name, animation, position, anim.anim[position].position);
            if (position < anim.anim.length - 1) {
                position++;
            } else if (runOnce) {
                if (animManager != null) {
                    log.trace("loading animManager {}", animManager);
                    animManager.whatPlay(this);
                }
                if (msAction != null) {
                    log.trace("end of MSAction {}", msAction);
                    msAction.terminatedAnim();
                    msAction.whatPlay(this);
                }
            } else {
                position = 0;
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
