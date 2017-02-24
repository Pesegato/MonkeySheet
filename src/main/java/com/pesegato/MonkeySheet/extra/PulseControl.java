/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pesegato.MonkeySheet.extra;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import com.pesegato.MonkeySheet.MSMaterialControl;

/**
 *
 * An example effect for having a sprite 'pulsate' with a color.
 *
 * @author Pesegato
 */
public class PulseControl extends AbstractControl{

    MSMaterialControl ms;
    float pulseValue;
    final float speed=4;
    final float threshold=.5f;

    public PulseControl(MSMaterialControl ms){
        this.ms=ms;
    }
    boolean increasing=true;
    @Override
    protected void controlUpdate(float tpf) {
        if (increasing){
            pulseValue+=(speed*tpf);
            if (pulseValue>threshold)
                increasing=false;
        }
        else{
            pulseValue-=(speed*tpf);
            if (pulseValue<0)
                increasing=true;
        }
        ms.setFogIntensity(pulseValue);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

}
