/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pesegato.MonkeySheet;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author Pesegato
 */
public abstract class MSMaterialPlugin extends AbstractControl{

    public MSMaterialControl msmc;

    public MSMaterialPlugin(MSMaterialControl msmc){
        this.msmc=msmc;
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

}
