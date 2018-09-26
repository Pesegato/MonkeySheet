package com.pesegato.MonkeySheet.batch;


import org.dyn4j.dynamics.Body;

@Deprecated
public abstract class BGeometryBodyControl extends BGeometryControl {

    public float offsetX;
    public float offsetY;
    public float offsetAngle;
    protected Body body;

    protected BGeometryBodyControl(Body body, BGeometry bgeo) {
        super(bgeo);
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
                (float) body.getTransform().getTranslationX() + offsetX,
                (float) body.getTransform().getTranslationY() + offsetY);
        bgeo.getTransform().setLocalRotation((float) body.getTransform().getRotation() + offsetAngle);
        bgeo.applyTransform();
        //duration -= tpf;
        //if (duration < 0) {
        //   setEnabled(false);
        //}
    }

}
