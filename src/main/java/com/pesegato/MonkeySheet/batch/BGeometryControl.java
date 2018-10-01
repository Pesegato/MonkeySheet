package com.pesegato.MonkeySheet.batch;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

public abstract class BGeometryControl extends AbstractControl {

    protected BGeometry bgeo;
    boolean mustInit = true;

    protected BGeometryControl(BGeometry bgeo) {
        this.bgeo = bgeo;
    }

    protected BGeometryControl(BNodeControl bnc, float posX, float posY) {
        this.bgeo = bnc.getReusableQuad(posX, posY);
    }

    abstract protected void binit();

    abstract protected void bupdate(float tpf);

    @Override
    protected void controlUpdate(float tpf) {
        if (mustInit) {
            binit();
            mustInit = false;
        }
        bupdate(tpf);
        bgeo.applyTransform();
        //duration -= tpf;
        //if (duration < 0) {
        //setEnabled(false);
        //}
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (!enabled) {
            bgeo.removeFromParent();
            spatial.removeControl(this);
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }
}
