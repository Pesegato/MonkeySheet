package com.pesegato.MonkeySheet.batch;

import com.jme3.asset.AssetManager;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import com.pesegato.MonkeySheet.MSContainer;
import com.pesegato.MonkeySheet.MSControl;
import com.pesegato.MonkeySheet.MSMaterialControl;
import com.pesegato.timing.Timeable;

public class BNodeControl extends AbstractControl {
    BNode bNode;
    BGeometry bGeometry[];
    Geometry geometry;

    public BNodeControl(Node parent, int bufSize, String sheetName, Timeable timeable, MSContainer msContainer, AssetManager assetM) {
        bNode = new BNode(bufSize);
        bGeometry = bNode.getQuads();
        geometry = bNode.makeGeo();
        MSControl mscsb = new MSControl(sheetName, timeable);
        geometry.addControl(mscsb);
        MSMaterialControl msmc = new MSMaterialControl(assetM, geometry, msContainer, mscsb);
        msmc.setVertexSheetPos(true);
        parent.attachChild(geometry);
        geometry.addControl(this);
    }

    public BGeometry getReusableQuad(float x, float y) {
        return bNode.quads[bNode.addReusableQuad(x, y)];
    }

    public void addControl(BGeometryControl control){
        geometry.addControl(control);
    }

    @Override
    protected void controlUpdate(float tpf) {
        bNode.updateData();
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }
}
