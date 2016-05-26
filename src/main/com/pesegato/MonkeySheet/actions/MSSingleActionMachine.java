/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pesegato.MonkeySheet.actions;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author Pesegato
 */
public class MSSingleActionMachine extends AbstractControl {

    MSAction action;
    boolean inited=false;

    public MSSingleActionMachine(MSAction action) {
        this.action = action;
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (!inited){
            action.init(spatial);
            inited=true;
        }
        action.controlUpdate(tpf);
        if (action.hasEnded())
            setEnabled(false);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

}
