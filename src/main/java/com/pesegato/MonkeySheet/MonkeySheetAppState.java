/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pesegato.MonkeySheet;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.pesegato.goldmonkey.AnimationBuilder;
import model.builders.definitions.BuilderManager;

import java.util.HashMap;

/**
 *
 * @author Pesegato
 */
public class MonkeySheetAppState extends BaseAppState {

    private static float tickDuration=0.025f;
    public static float tTPF = 0;

    @Override
    protected void initialize(Application app) {
        getStateManager().attach(new MSTimerAppState());
    }

    static HashMap<String, MTween> anis = new HashMap<>();

    public static MTween getAnim(String name){
        return anis.get(name);
    }

    public void addAnim(MSContainer msCont, String name, int ani[], int hitbox[]) {
        anis.put(name,new MTween(msCont, name, ani, hitbox, msCont.numTiles));
    }

    public void loadAnim(MSContainer container, String anim){
        addAnim(container,anim, ( BuilderManager.getBuilder("com.pesegato.goldmonkey.AnimationBuilder", anim, AnimationBuilder.class)).buildAnimation(), null);
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

}
