/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pesegato.MonkeySheet;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author Pesegato
 */
public class MSHitboxControl extends AbstractControl {

    MSControl msc;
    int currentHB = -1;
    Geometry[] hitboxes;

    public MSHitboxControl(MSControl msc, Geometry[] hitboxes) {
        this.msc = msc;
        this.hitboxes = hitboxes;
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (currentHB != msc.getCurrentHitbox()) {
            currentHB = msc.getCurrentHitbox();
            Node n=(Node)spatial;
            n.detachChildNamed("hitbox");
            n.attachChild(hitboxes[currentHB]);
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

}
