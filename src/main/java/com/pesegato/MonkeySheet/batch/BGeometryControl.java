package com.pesegato.MonkeySheet.batch;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

public abstract class BGeometryControl extends AbstractControl {

    protected BGeometry bgeo;
    float duration;

    protected BGeometryControl(BGeometry bgeo, float duration) {
        this.bgeo = bgeo;
        this.duration = duration;
    }

    abstract protected void bupdate(float tpf);

    @Override
    protected void controlUpdate(float tpf) {
        bupdate(tpf);
        bgeo.applyTransform();
        duration -= tpf;
        if (duration < 0) {
            bgeo.removeFromParent();
            spatial.removeControl(this);
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }
}
