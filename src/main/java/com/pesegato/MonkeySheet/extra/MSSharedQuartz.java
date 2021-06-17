package com.pesegato.MonkeySheet.extra;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

import java.util.ArrayList;

public class MSSharedQuartz extends AbstractControl {

    public float tTPF = 0;
    float maxValue;
    ArrayList<Tickable> tickables = new ArrayList<>();
    ArrayList<Float> delays = new ArrayList<>();
    int pointer = 0;

    public MSSharedQuartz(float tTPF) {
        this.tTPF = tTPF;
        this.maxValue = tTPF;
    }

    public void addTickable(float delay, Tickable tickable) {
        pointer = delays.size();
        tickables.add(tickable);
        delays.add(delay);
    }

    @Override
    protected void controlUpdate(float tpf) {
        tTPF -= tpf;
        if (tTPF < delays.get(pointer)) {
            tickables.get(pointer).tick();
            pointer--;
            if (pointer < 0) {
                pointer = tickables.size() - 1;
                tTPF = maxValue;
            }
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }
}

