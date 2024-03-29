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
    MSMaterialControl msmc;

    public BNodeControl(Node parent, int bufSize, String sheetName, Timeable timeable, MSContainer msContainer, AssetManager assetM) {
        bNode = new BNode(bufSize);
        bGeometry = bNode.getQuads();
        geometry = bNode.makeGeo();
        MSControl mscsb = new MSControl(sheetName, timeable);
        geometry.addControl(mscsb);
        msmc = new MSMaterialControl(assetM, geometry, msContainer, mscsb);
        msmc.setVertexSheetPos(true);
        parent.attachChild(geometry);
        geometry.addControl(this);
    }

    public BGeometry getReusableQuad(float x, float y) {
        int idx = bNode.getNextAvailableSlot();
        return bNode.quads[bNode.addReusableQuad(idx, x, y)];
    }

    public void addControl(BGeometryControl control) {
        geometry.addControl(control);
    }

    public void setZ(float z) {
        geometry.setLocalTranslation(0, 0, z);
    }

    public Geometry getGeometry(){
        return geometry;
    }

    public MSMaterialControl getMaterial(){
        return msmc;
    }

    public int getBufSize() {
        return bNode.quads.length;
    }

    public int getDebugFreeSlot() {
        return bNode.getDebugFreeSlot();
    }

    public void destroy() {
        geometry.removeFromParent();
    }

    @Override
    protected void controlUpdate(float tpf) {
        bNode.updateData();
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }
}
