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
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Quad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Pesegato
 */
public class MSGlobals {

    static Logger log = LoggerFactory.getLogger(MSGlobals.class);

    public enum COMPRESSION_TYPE {
        NONE,
        BC7,
        DXT5,
        ASTC,
        ETC2
    }

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

    private static COMPRESSION_TYPE USE_COMPRESSION = COMPRESSION_TYPE.NONE;

    public static void setCompressedTexturesEnabled(COMPRESSION_TYPE compression) {
        USE_COMPRESSION = compression;
    }

    public static COMPRESSION_TYPE getCompression() {
        return USE_COMPRESSION;
    }

    public static String getExtension() {
        switch (USE_COMPRESSION) {
            case NONE:
                return ".png";
            case BC7:
            case DXT5:
                return ".dds";
            case ASTC:
                return ".astc";
            case ETC2:
                return ".etc";
        }
        return null;
    }

    public static String getComment(COMPRESSION_TYPE type) {
        switch (type) {
            case NONE:
                return "Memory hungry";//&slow
            case BC7:
                return "Needs DirectX11";
            case DXT5:
                return "Looks bad";
            case ASTC:
                return "Unavailable";
            case ETC2:
                return "Unavailable";//Raspberry Pi?
        }
        return null;
    }

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

    public static MSMaterialControl makeSprite(AssetManager assetManager, Geometry geo, MSContainer container, String sprite) {
        return new MSMaterialControl(assetManager, geo, container).setSprite(sprite);
    }

    /**
     * This method moves the spatial toward absolute target position finalX, finalY
     * with speed factors x,y.
     * For each axis, if speed is positive but target is behind then no movement is performed
     * For each axis, if speed is negative but target is in front then no movement is performed
     *
     * @param spatial the Spatial to be moved
     * @param x       speed on the X
     * @param y       speed on the Y
     * @param finalX  target x coordinate
     * @param finalY  target y coordinate
     * @return true if arrived at target position
     */

    public static boolean simpleMoveFixPixels(Spatial spatial, float x, float y, float finalX, float finalY) {
        float currentX = spatial.getLocalTranslation().x;
        float currentY = spatial.getLocalTranslation().y;
        float nextX = x + currentX;
        float nextY = y + currentY;
        if (x > 0) {
            nextX = Math.min(nextX, finalX);
        } else {
            nextX = Math.max(nextX, finalX);
        }
        if (y > 0) {
            nextY = Math.min(nextY, finalY);
        } else {
            nextY = Math.max(nextY, finalY);
        }
        spatial.setLocalTranslation(nextX, nextY, 0);
        return (nextX == finalX) && (nextY == finalY);
    }

    /**
     * This method scales the spatial toward target scale
     * with speed factors x,y.
     * For each axis, if speed is positive but target is smaller then no scale is performed
     * For each axis, if speed is negative but target is bigger then no scale is performed
     *
     * @param spatial the Spatial to be moved
     * @param x       speed on the X
     * @param y       speed on the Y
     * @param finalX  target x scale
     * @param finalY  target y scale
     * @return true if arrived at target scale
     */

    public static boolean simpleScaleFixPixels(Spatial spatial, float x, float y, float finalX, float finalY) {
        float currentX = spatial.getLocalScale().getX();
        float currentY = spatial.getLocalScale().getY();
        float nextX = x + currentX;
        float nextY = y + currentY;
        if (x > 0) {
            nextX = Math.min(nextX, finalX);
        } else {
            nextX = Math.max(nextX, finalX);
        }
        if (y > 0) {
            nextY = Math.min(nextY, finalY);
        } else {
            nextY = Math.max(nextY, finalY);
        }
        spatial.setLocalScale(nextX, nextY, 0);
        return (nextX == finalX) && (nextY == finalY);
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
