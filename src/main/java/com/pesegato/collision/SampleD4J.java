package com.pesegato.collision;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import com.pesegato.collision.hitbox.HBRect;
import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Vector2;

public class SampleD4J extends SimpleApplication {

    public static void main(String[] args){
        SampleD4J app = new SampleD4J();
        //app.setShowSettings(false);
        app.start(); // start the game
    }

    Dyn4jMEAppState das;
    @Override
    public void simpleInitApp() {
        das = new Dyn4jMEAppState();
        stateManager.attachAll(das,new MainAppState());
    }

    class MainAppState extends BaseAppState{

        @Override
        protected void initialize(Application app) {
            float boxSize = .5f;
            HBRect hbRect=new HBRect(1,boxSize,.5f);
            Node boxBlue= hbRect.getNode(assetManager, ColorRGBA.Blue);
            boxBlue.setLocalTranslation(200, 300, 0);
            guiNode.attachChild(boxBlue);
            Dyn4JShapeControl physics = hbRect.getControl();
            boxBlue.addControl(physics);
            boxBlue.addControl(new Down(physics.getBody()));
            das.getPhysicsSpace(0).add(boxBlue);

            HBRect hbRect2=new HBRect(2,boxSize,.1f);
            Node boxRed = hbRect2.getNode(assetManager, ColorRGBA.Red);
            boxRed.setLocalTranslation(200, 200, 0);
            guiNode.attachChild(boxRed);
            Dyn4JShapeControl physics2 = hbRect2.getControl();
            boxRed.addControl(physics2);
            das.getPhysicsSpace(0).add(boxRed);

            das.getPhysicsSpace(0).addListener(new MyCollisionListener(physics));
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

    class MyCollisionListener implements CollisionListener{

        Dyn4JShapeControl phy;
        MyCollisionListener(Dyn4JShapeControl phy){
            this.phy=phy;
        }
        @Override
        public void listen(long collider, long collided) {
            System.out.println(collider+" collided with "+collided);
            D4JSpace space=das.getPhysicsSpace(0);
            //phy.getSpatial().removeFromParent();
            phy.removeFromWorld();
        }
    }

    class Down extends AbstractControl{

        Body body;
        Down(Body body){
            this.body=body;
        }

        @Override
        protected void controlUpdate(float tpf) {
           body.translate(new Vector2(0,-5*tpf));
        }

        @Override
        protected void controlRender(RenderManager rm, ViewPort vp) {

        }
    }


}
