/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pesegato.MonkeySheet;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;
import org.lwjgl.opengl.Display;

/**
 *
 * @author Pesegato
 */
public class GeometryAppState extends BaseAppState{

    Geometry geometry;
    MSMaterialControl msc;
    @Override
    protected void initialize(Application app) {
        geometry = new Geometry("PPPP", new Quad(256, 256));
        geometry.setLocalTranslation(0, Display.getHeight()-400, 0);
//        msc=getState(MonkeySheetAppState.class).initializeGeometry(geometry, "Run");
        ((SimpleApplication)app).getGuiNode().attachChild(geometry);
        
        Geometry geometry2 = new Geometry("PPPP2", new Quad(256, 256));
        geometry2.setLocalTranslation(300, Display.getHeight()-400, 0);
//        MSControl msc2=getState(MonkeySheetAppState.class).initializeGeometry(geometry2, "Bend");
        ((SimpleApplication)app).getGuiNode().attachChild(geometry2);
        
    }

    float limit=2;
    
    @Override
    public void update(float tpf){
        limit-=tpf;
        if (limit<0){
            //msc.play("Bend");
            limit=10;
        }
        //pppp.rotate(0, tpf, 0);
    }
    
    @Override
    protected void cleanup(Application app) {
    }

    @Override
    protected void onEnable() {
    }

    @Override
    protected void onDisable() {
    }
    
}
