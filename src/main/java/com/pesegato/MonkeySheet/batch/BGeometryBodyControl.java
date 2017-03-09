package com.pesegato.MonkeySheet.batch;


import org.dyn4j.dynamics.Body;

public abstract class BGeometryBodyControl extends BGeometryControl {

    protected Body body;

    protected BGeometryBodyControl(Body body, BGeometry bgeo, float duration) {
        super(bgeo, duration);
        this.body = body;
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (mustInit) {
            binit();
            mustInit = false;
        }
        bupdate(tpf);
        bgeo.getTransform().setPosition(
                (float) body.getTransform().getTranslationX(),
                (float) body.getTransform().getTranslationY());
        bgeo.getTransform().setLocalRotation((float) body.getTransform().getRotation());
        bgeo.applyTransform();
        duration -= tpf;
        if (duration < 0) {
            vanish();
        }
    }

    public void vanish() {
        bgeo.removeFromParent();
        spatial.removeControl(this);
    }
}
