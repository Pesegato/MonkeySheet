/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pesegato.MonkeySheet;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.control.AbstractControl;
import com.jme3.texture.Texture;

import static com.pesegato.MonkeySheet.MonkeySheetAppState.log;

/**
 *
 * @author Pesegato
 */
public class MSMaterialControl extends AbstractControl {

    Material material;

    public String animation;
    public int position;
    MSControl msc;

    private float alphaValue=1;
    private boolean flipped=false;
    private float hueShift = 0;
    private ColorRGBA fogColor=ColorRGBA.Pink;
    private float fogIntensity=0;

    public MSMaterialControl(AssetManager assetManager, Geometry geo, MSContainer msCont, MSControl msc){
        material = new Material(assetManager, "MonkeySheet/MatDefs/Anim.j3md");
        Texture[] sheetsX=new Texture[msCont.sheets.length];
        for (int i = 0; i < msCont.sheets.length; i++) {
            System.out.println("MonkeySheet: Now loading "+msCont.sheets[i]);
            sheetsX[i]=assetManager.loadTexture(msCont.sheets[i]);
        }
        material.setFloat("SizeX", msCont.numTiles);
        material.setFloat("SizeY", msCont.numTiles);
        material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        for (MTween mt:MonkeySheetAppState.anis.values()){
            if (mt.msCont==msCont)
                mt.setTextures(sheetsX);
        }
        geo.setMaterial(material);
        geo.addControl(this);
        this.msc=msc;
            material.setFloat("Position", msc.anim.anim[msc.position].position);
            material.setTexture("ColorMap", msc.anim.anim[msc.position].sheetX);
            material.setFloat("FlipHorizontal", 0.0f);
            material.setFloat("AlphaValue", 1.0f);
            material.setColor("FogColor", fogColor);
            material.setFloat("FogIntensity", 0.0f);
            material.setFloat("HueShift", hueShift);
    }

    public void setVertexSheetPos(boolean b){
        material.setBoolean("VertexSheetPos", b);
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (MonkeySheetAppState.tTPF == 0) {
            if (msc.position >= msc.anim.anim.length) {
                log.error("Error in animation, doing {} at position {}", msc.msAction, msc.position);
            }
            material.setFloat("Position", msc.anim.anim[msc.position].position);
            material.setTexture("ColorMap", msc.anim.anim[msc.position].sheetX);
        }

    }

    public void setFlipped(boolean flipped){
        this.flipped=flipped;
        material.setFloat("FlipHorizontal", flipped?1.0f:0.0f);
    }

    public void setHueShift(float hueShift) {
        this.hueShift = hueShift;
        material.setFloat("HueShift", hueShift);
    }

    public void setAlpha(float alphaValue){
        this.alphaValue=alphaValue;
        material.setFloat("AlphaValue", alphaValue);
    }

    public void setFogColor(ColorRGBA fogColor){
        this.fogColor=fogColor;
        material.setColor("FogColor", fogColor);
    }

    public void setFogIntensity(float fogIntensity){
        this.fogIntensity=fogIntensity;
        material.setFloat("FogIntensity", fogIntensity);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

}
