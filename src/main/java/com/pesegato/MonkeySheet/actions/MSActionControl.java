package com.pesegato.MonkeySheet.actions;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import com.pesegato.MonkeySheet.MSAnimationManager;
import com.pesegato.MonkeySheet.MTween;
import com.pesegato.MonkeySheet.MonkeySheetAppState;
import com.pesegato.timing.Timeable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class MSActionControl extends AbstractControl {
    static Logger log = LoggerFactory.getLogger(MSActionControl.class);
    public MTween anim;
    public MTween nextAnim;
    public String animation;
    public int position;
    boolean runOnce = false;
    public MSAction msAction;

    public MSActionControl(String anim, Timeable timeable) {
        playForever(anim);
        MonkeySheetAppState.timeable = timeable;
    }

    final public void playForever(String ani) {
        log.debug("now playing FOREVER animation {}", ani);
        anim = MonkeySheetAppState.getAnim(ani);
        position = 0;
        runOnce = false;
        if (anim == null) {
            log.warn("Running UNINITIALIZED animation {}, GOING TO CRASH VERY SOON!!!", ani);
        }
    }

    public void playOnce(String ani) {
        log.debug("now playing ONCE animation {}", ani);
        anim = MonkeySheetAppState.getAnim(ani);
        position = 0;
        //nextAnim = MonkeySheetAppState.getAnim(ani);
        runOnce = true;
        if (anim == null) {
            log.warn("Running UNINITIALIZED animation {}, GOING TO CRASH VERY SOON!!!" + ani);
        }
    }

    /**
     * Finite state machine for MSAction
     *
     * @return the msAction for the current state
     */

    abstract MSAction updateActionState();

    @Override
    protected void controlUpdate(float tpf) {
        if (MonkeySheetAppState.tTPF == 0) {
            log.trace("msac position: {} - {}", position, anim.anim[position].getPosition());
            position++;
            if (position >= anim.anim.length - 1) {
                if (runOnce) {
                    /*
                    if (nextAnim != null) {
                        anim = nextAnim;
                        position = 0;
                        nextAnim = null;
                    }
                    */
                    if (msAction != null) {
                        log.trace("end of MSAction {}", msAction);
                        msAction.terminatedAnim();
                        updateActionState();
                    }
                } else {
                    position = 0;
                }
            }
        }

    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

}
