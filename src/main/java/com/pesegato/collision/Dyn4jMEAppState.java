package com.pesegato.collision;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;

@Deprecated
public class Dyn4jMEAppState extends BaseAppState {

    D4JSpace spaces[];

    public D4JSpace getPhysicsSpace(int index) {
        return spaces[index];
    }

    @Override
    protected void initialize(Application app) {
        spaces = new D4JSpace[]{new D4JSpace(), new D4JSpace()};
        spaces[0].setName("Plane 0");
        spaces[1].setName("Plane 1");
        spaces[2].setName("Plane 2");
    }

    @Override
    public void update(float tpf) {
        updatePhysics(tpf);
        updateDraw(tpf);
    }

    public void updateDraw(float tpf) {
        for (D4JSpace space : spaces)
            space.updateDraw(tpf);
    }

    public void updatePhysics(float tpf) {
        for (D4JSpace space : spaces)
            space.updatePhysics(tpf);
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
