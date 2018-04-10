package com.pesegato.collision;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

import java.util.*;

public class D4JSpaceDebugAppState extends BaseAppState {

    protected ViewPort viewPort;
    protected RenderManager rm;
    Node stateGuiNode = new Node("D4J Debug Node");
    protected HashMap<DebuggableBody, Geometry> labels = new HashMap<>();
    ArrayList<DebuggableBody> bodies;

    @Override
    protected void initialize(Application app) {
        this.rm = app.getRenderManager();
        stateGuiNode.setCullHint(Spatial.CullHint.Never);
        viewPort = rm.createMainView("D4J Debug Overlay", app.getCamera());
        viewPort.setClearFlags(false, true, false);
        viewPort.attachScene(stateGuiNode);
        bodies = getState(D4JSpace2.class).bodies;
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        updateItems();
        stateGuiNode.updateLogicalState(tpf);
        stateGuiNode.updateGeometricState();
    }

    private void updateItems() {
        HashMap<DebuggableBody, Geometry> oldObjects = labels;
        labels = new HashMap<>();
        Collection<DebuggableBody> current = bodies;
        //create new map
        for (Iterator<DebuggableBody> it = current.iterator(); it.hasNext(); ) {
            DebuggableBody physicsObject = it.next();
            //copy existing spatials
            if (oldObjects.containsKey(physicsObject)) {
                Geometry spat = oldObjects.get(physicsObject);
                spat.setLocalTranslation((float) physicsObject.getTransform().getTranslation().x, (float) physicsObject.getTransform().getTranslation().y, 0);
                labels.put(physicsObject, spat);
                oldObjects.remove(physicsObject);
            } else {
                //if (filter == null || filter.displayObject(physicsObject))
                {
                    //logger.log(Level.FINE, "Create new debug RigidBody");
                    //create new spatial
                    Geometry hudText = physicsObject.makeHitboxMarker(getApplication().getAssetManager(), stateGuiNode, ColorRGBA.Cyan);
                    //BitmapText hudText = new BitmapText(font, false);
                    //hudText.scale(0.01f);
//hudText.setSize(guiFont.getCharSet().getRenderedSize());      // font size
//hudText.setColor(ColorRGBA.Blue);                             // font color
                    //hudText.setText(physicsObject.toString());             // the text
                    hudText.setLocalTranslation((float) physicsObject.getTransform().getTranslation().x, (float) physicsObject.getTransform().getTranslation().y, 0); // position
                    //hudText.addControl(new BillboardControl());
                    labels.put(physicsObject, hudText);
                    stateGuiNode.attachChild(hudText);
                }
            }
        }
        //remove leftover spatials
        for (Map.Entry<DebuggableBody, Geometry> entry : oldObjects.entrySet()) {
            DebuggableBody object = entry.getKey();
            Geometry spatial = entry.getValue();
            spatial.removeFromParent();
        }
    }

    public void setGuiNode(Node guiNode) {
        guiNode.attachChild(stateGuiNode);
    }

    @Override
    public void render(RenderManager rm) {
        super.render(rm);
        if (viewPort != null) {
            rm.renderScene(stateGuiNode, viewPort);
        }
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
