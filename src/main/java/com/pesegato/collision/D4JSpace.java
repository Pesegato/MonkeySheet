package com.pesegato.collision;

import com.jme3.scene.Spatial;
import org.dyn4j.dynamics.World;

import java.util.HashSet;
import java.util.Set;

public class D4JSpace {
    private World world;
    private Set<Spatial> spatials = new HashSet<Spatial>();

    public D4JSpace(){
        world=new World();
        world.setDetectBroadphaseFilter(new DetectBPP());
    }

    void add(Spatial spatial) {
        if (spatial.getControl(IDyn4JControl.class) == null) throw new IllegalArgumentException("Cannot handle a node which isnt a ${Dyn4JShapeControl.getClass().getSimpleName()}");
        synchronized(spatials) {
            spatials.add(spatial);
            IDyn4JControl ctl = spatial.getControl(IDyn4JControl.class);
            ctl.addToWorld(world);
        }
    }

    void remove(Spatial spatial) {
        if (spatial == null || spatial.getControl(IDyn4JControl.class) == null) return;
        spatials.remove(spatials);
        IDyn4JControl ctl = spatial.getControl(IDyn4JControl.class);
        ctl.removeFromWorld(world);
    }
    public void updateDraw(float tpf) {
        synchronized(spatials) {
            for (Spatial spatial: spatials){
                IDyn4JControl ctl = spatial.getControl(IDyn4JControl.class);
                if (ctl == null) { spatials.remove(spatial); return; } //evict nodes which have their Dyn4JShapeControl removed
                ctl.updateDraw(tpf);
            }
        }
    }

    public void updatePhysics(float tpf) {
        synchronized(spatials) {
            for (Spatial spatial: spatials){
                IDyn4JControl ctl = spatial.getControl(IDyn4JControl.class);
                if (ctl == null) { spatials.remove(spatial); return; } //evict nodes which have their Dyn4JShapeControl removed
                ctl.updatePhysics(tpf);
            }
        }
        world.update(tpf, Integer.MAX_VALUE);
    }

}
