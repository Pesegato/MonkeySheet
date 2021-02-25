package com.pesegato.collision;

import com.jme3.scene.control.AbstractControl;
import org.dyn4j.collision.broadphase.BroadphaseDetector;

@Deprecated
public abstract class IDyn4JControl extends AbstractControl{
    void updatePhysics(BroadphaseDetector bp, float tpf){}
    void updateDraw(float tpf){}
    //void addToWorld(World world){}
    void removeFromWorld(){}
    void addToWorld(BroadphaseDetector broadphase){}
}
