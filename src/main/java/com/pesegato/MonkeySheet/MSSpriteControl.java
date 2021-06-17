package com.pesegato.MonkeySheet;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The most basic sprite
 */

public class MSSpriteControl extends AbstractControl {

    public static Logger log = LoggerFactory.getLogger(MSControl.class);

    public MTween anim;
    public String animation;
    public int position;

    MSSpriteControl() {
    }

    public MSSpriteControl(MTween anim, int position) {
        this.anim = anim;
        this.position = position;
    }

    @Override
    protected void controlUpdate(float tpf) {
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
}
