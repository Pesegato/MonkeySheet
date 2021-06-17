package com.pesegato.MonkeySheet.extra;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.pesegato.MonkeySheet.MSControl;
import com.pesegato.MonkeySheet.MSMaterialControl;
import com.pesegato.MonkeySheet.MonkeySheetAppState;

/**
 * A shadow effect for MSControl
 */

public class MSShadowControl extends MSControl implements Tickable {

    MSControl targetControl;
    MSMaterialControl fxControl;
    Spatial targetSpatial;
    float currentAlpha = 1.0f;
    float alphaDecay;
    float offsetX;
    float offsetY;

    public MSShadowControl(MSControl targetControl, float delay, float offsetX, float offsetY) {
        this.targetControl = targetControl;
        this.targetSpatial = targetControl.getSpatial();
        this.alphaDecay = 1f / delay;
        this.position = targetControl.position;
        this.anim = targetControl.anim;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public void setMaterial(MSMaterialControl fxControl) {
        this.fxControl = fxControl;
    }

    public void update(float tpf) {
        currentAlpha -= (alphaDecay * tpf);
        fxControl.setAlpha(currentAlpha);
    }

    @Override
    public void tick() {
        log.debug("update {} {}", targetControl.animation, targetControl.position);
        Vector3f trans = targetSpatial.getParent().getParent().getLocalTranslation();
        spatial.setLocalTranslation(trans.x + offsetX, trans.y + offsetY, trans.z);
        anim = MonkeySheetAppState.getAnim(targetControl.animation);
        this.position = targetControl.position;
        currentAlpha = 1.0f;
    }
}
