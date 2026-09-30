package com.pesegato.collision;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import org.dyn4j.collision.CollisionItem;
import org.dyn4j.collision.CollisionPair;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.MassType;
import org.dyn4j.world.AbstractCollisionWorld;
import org.dyn4j.world.WorldCollisionData;

import java.util.ArrayList;
import java.util.Iterator;

public class D4JSpace2 extends BaseAppState {

    private final MonkeyCollisionWorld collisionWorld = new MonkeyCollisionWorld();
    ArrayList<DebuggableBody> bodies = new ArrayList<>();
    String name = "unnamed space";
    ArrayList<CollisionListener> listeners = new ArrayList<>();

    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected void initialize(Application app) {
    }

    @Override
    protected void cleanup(Application app) {
        collisionWorld.removeAllBodies();
        bodies.clear();
    }

    @Override
    protected void onEnable() {
    }

    @Override
    protected void onDisable() {
    }

    public void add(DebuggableBody body, MassType massType, long id) {
        for (BodyFixture bf : body.getFixtures())
            bf.setUserData(id);
        body.setMass(massType);
        body.setAtRestDetectionEnabled(false);
        collisionWorld.addBody(body);
        bodies.add(body);
    }

    public void remove(DebuggableBody body) {
        collisionWorld.removeBody(body);
        bodies.remove(body);
    }

    float tTPF = 0;

    @Override
    public void update(float tpf) {
        tTPF += tpf;
        if (tTPF > 1 / 60f) {
            tTPF = 0;
            collisionWorld.updateDetect();
        }
    }

    public boolean checkCollisionNP(Body a, Body b) {
        for (BodyFixture bf1 : a.getFixtures()) {
            for (BodyFixture bf2 : b.getFixtures()) {
                if (collisionWorld.getNarrowphaseDetector().detect(bf1.getShape(), a.getTransform(), bf2.getShape(), b.getTransform())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkCollisionAll(Body a, Body b) {
        return checkCollisionNP(a, b);
    }

    public void addListener(CollisionListener cl) {
        listeners.add(cl);
    }

    private class MonkeyCollisionWorld extends AbstractCollisionWorld<Body, BodyFixture, WorldCollisionData<Body>> {

        @Override
        protected WorldCollisionData<Body> createCollisionData(CollisionPair<CollisionItem<Body, BodyFixture>> pair) {
            return new WorldCollisionData<>(pair);
        }

        public void updateDetect(){
            detect();
        }

        @Override
        protected void processCollisions(Iterator<WorldCollisionData<Body>> iterator) {
            while (iterator.hasNext()) {
                WorldCollisionData<Body> data = iterator.next();
                if (data.isNarrowphaseCollision()) {
                    BodyFixture fixture1 = data.getFixture1();
                    BodyFixture fixture2 = data.getFixture2();
                    for (CollisionListener listener : listeners) {
                        listener.listen((Long) fixture1.getUserData(), (Long) fixture2.getUserData());
                    }
                }
            }
        }
    }

}
