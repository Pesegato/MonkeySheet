package com.pesegato.collision;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import com.pesegato.collision.hitbox.HBCircle;
import com.pesegato.collision.hitbox.HBRect;
import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

public class SampleD4J2 extends SimpleApplication {

    public static void main(String[] args) {
        SampleD4J2 app = new SampleD4J2();
        //app.setShowSettings(false);
        app.start(); // start the game
    }

    D4JSpace2 d4j;

    @Override
    public void simpleInitApp() {
        d4j = new D4JSpace2();
        stateManager.attachAll(d4j,
                new D4JSpaceDebugAppState(),
                new MainAppState());
    }

    HBRect hbRect;

    class MainAppState extends BaseAppState {

        @Override
        protected void initialize(Application app) {
            getState(D4JSpaceDebugAppState.class).setGuiNode(guiNode);
            float boxSize = .5f;
            hbRect = new HBRect(1, boxSize, .5f);
            hbRect.translate(200, 300);
            hbRect.setColor(ColorRGBA.Blue);
            d4j.add(hbRect, MassType.INFINITE, 2);

            HBCircle hbRect2 = new HBCircle(3, 15);
            hbRect2.translate(200, 200);
            hbRect2.setColor(ColorRGBA.Red);
            d4j.add(hbRect2, MassType.INFINITE, 4);

            d4j.addListener(new MyCollisionListener());
        }

        public void update(float tpf) {
            hbRect.translate(new Vector2(0, -5 * tpf));
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

    class MyCollisionListener implements CollisionListener {

        @Override
        public void listen(long collider, long collided) {
            System.out.println(collider + " collided with " + collided);
            d4j.remove(hbRect);
        }
    }

}
