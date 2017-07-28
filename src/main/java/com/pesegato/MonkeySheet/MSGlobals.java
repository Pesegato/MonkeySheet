/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pesegato.MonkeySheet;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Pesegato
 */
public class MSGlobals {

    static Logger log = LoggerFactory.getLogger(MSGlobals.class);

    public static final int SPRITE_SIZE = 256;

    public static final int MS_WIDTH_480P = 720;
    public static final int MS_HEIGHT_480P = 480;

    public static final int MS_WIDTH_576P = 720;
    public static final int MS_HEIGHT_576P = 576;

    public static final int MS_WIDTH_720P = 1280;
    public static final int MS_HEIGHT_720P = 720;

    public static final int MS_WIDTH_1080P = 1920;
    public static final int MS_HEIGHT_1080P = 1080;

    public static int MS_WIDTH;
    public static int MS_HEIGHT;
    public static boolean SHOW_HITBOX = true;

    public static void setResolution(String res) {
        switch (res) {
            case "480p":
                MS_WIDTH = MS_WIDTH_480P;
                MS_HEIGHT = MS_HEIGHT_480P;
                break;
            case "576p":
                MS_WIDTH = MS_WIDTH_576P;
                MS_HEIGHT = MS_HEIGHT_576P;
                break;
            case "720p":
                MS_WIDTH = MS_WIDTH_720P;
                MS_HEIGHT = MS_HEIGHT_720P;
                break;
            case "1080p":
                MS_WIDTH = MS_WIDTH_1080P;
                MS_HEIGHT = MS_HEIGHT_1080P;
                break;
            default:
                log.error("Resolution unsupported: {}", res);
                System.exit(1);
        }
        log.info("Resolution set to: {}", res);
    }

    public static Geometry makeDefaultQuad(int sizeX, int sizeY) {
        return new Geometry("MSQuad", new Quad(sizeX, sizeY));
    }

    public static Material createDefaultMaterialWithAlpha(AssetManager assetManager, Geometry geo, String texture) {
        Material material = createDefaultMaterialWithAlpha(assetManager, geo);
        material.setTexture("ColorMap", assetManager.loadTexture(texture));
        return material;
    }

    public static Material createDefaultMaterialWithAlpha(AssetManager assetManager, Geometry geo) {
        Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        geo.setMaterial(material);
        return material;
    }

    public static Material createDefaultMaterialNoAlpha(AssetManager assetManager, Geometry geo, String texture) {
        Material material = createDefaultMaterialNoAlpha(assetManager, geo);
        material.setTexture("ColorMap", assetManager.loadTexture(texture));
        return material;
    }

    public static Material createDefaultMaterialNoAlpha(AssetManager assetManager, Geometry geo) {
        Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        geo.setMaterial(material);
        return material;
    }

    protected void logBuildInfo() {
        try {
            java.net.URL u = Resources.getResource("monkeysheet.build.date");
            String build = Resources.toString(u, Charsets.UTF_8);
            log.info("MonkeySheett build date:" + build);
        } catch (java.io.IOException e) {
            log.error("Error reading build info", e);
        }
    }
}
