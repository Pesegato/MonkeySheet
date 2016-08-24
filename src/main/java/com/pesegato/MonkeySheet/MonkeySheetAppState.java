/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pesegato.MonkeySheet;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.pesegato.goldmonkey.Animation;
import com.pesegato.goldmonkey.Container;
import com.pesegato.goldmonkey.GM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.util.HashMap;

/**
 *
 * @author Pesegato
 */
public class MonkeySheetAppState extends BaseAppState {

    static Logger log = LoggerFactory.getLogger( MonkeySheetAppState.class );

    private static float tickDuration=0.025f;
    public static float tTPF = 0;

    @Override
    protected void initialize(Application app) {
        getStateManager().attach(new MSTimerAppState());
        logBuildInfo();
    }

    static HashMap<String, MTween> anis = new HashMap<>();

    public static MTween getAnim(String name){
        return anis.get(name);
    }

    public static int getCenterX(String name){
        return anis.get(name).centerX;
    }

    public static int getCenterY(String name){
        return anis.get(name).centerY;
    }

    public void addAnim(MSContainer msCont, String name, int ani[], int hitbox[], int centerX, int centerY) {
        anis.put(name,new MTween(msCont, name, ani, hitbox, msCont.numTiles, centerX, centerY));
    }

    static HashMap<String, Container> containers;

    static Container getContainer(String id){
        if (containers == null) {
            try {
                containers = new HashMap<>();
                Container[] data = new Gson().fromJson(GM.getJSON("MonkeySheet/Containers"), Container[].class);
                for (Container obj : data) {
                    containers.put(obj.id, obj);
                }
            } catch (FileNotFoundException ex) {
                log.error(null, ex);
            }
        }
        return containers.get(id);
    }
    static HashMap<String, Animation> animations;

    public void loadAnim(MSContainer container, String anim){
        if (animations == null) {
            try {
                animations = new HashMap<>();
                Animation[] data = new Gson().fromJson(GM.getJSON("MonkeySheet/Animations"), Animation[].class);
                for (Animation obj : data) {
                    animations.put(obj.id, obj);
                }
            } catch (FileNotFoundException ex) {
                log.error(null, ex);
            }
        }
        addAnim(container,anim,animations.get(anim).frames,null,animations.get(anim).centerX,animations.get(anim).centerY);
    }

    @Override
    public void update(float tpf){
        tTPF += tpf;
        if (tTPF > tickDuration) {
            tTPF = 0;
        }
    }

    public static void setTickDuration(float tickDuration){
        MonkeySheetAppState.tickDuration=tickDuration;
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

    protected void logBuildInfo() {
        try {
            java.net.URL u = Resources.getResource("monkeysheet.build.date");
            String build = Resources.toString(u, Charsets.UTF_8);
            log.info("MonkeySheet build date: " + build);
        } catch( java.io.IOException e ) {
            log.error( "Error reading build info", e );
        }
    }
}
