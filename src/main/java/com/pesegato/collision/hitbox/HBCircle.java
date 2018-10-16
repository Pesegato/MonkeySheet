package com.pesegato.collision.hitbox;


import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
import com.pesegato.collision.DebuggableBody;
import com.pesegato.collision.Dyn4JShapeControl;
import org.dyn4j.collision.Filter;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;

import static com.pesegato.MonkeySheet.MSGlobals.SHOW_HITBOX;
import static com.pesegato.MonkeySheet.MSGlobals.SPRITE_SIZE;

public class HBCircle extends DebuggableBody {
    String name = "";
    float radius;
    public long id;

    public HBCircle(long id, float radius) {
        this.id = id;
        this.radius = radius;
        addFixture(new BodyFixture(new Circle(radius)));
    }

    public HBCircle(String name, long id, float radius) {
        this.name = name;
        this.id = id;
        this.radius = radius;
        addFixture(new BodyFixture(new Circle(radius)));
    }

    @Deprecated
    public Node getNode(AssetManager assetM, ColorRGBA color) {
        Node n = new Node();
        if (SHOW_HITBOX) {
            n.attachChild(makeHitboxMarker(assetM, n, color));
        }
        return n;
    }

    public Convex getConvex() {
        return new Circle(radius);
    }

    @Override
    public Geometry makeHitboxMarker(AssetManager assetManager, Node n, ColorRGBA color) {
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        ColorRGBA col = color.clone();
        col.a = .5f;
        mat.setColor("Color", col);
        mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        Geometry geo = new Geometry(name, new Prism(radius , 5,12));
        geo.setMaterial(mat);
        geo.rotate(FastMath.PI/2,0,0);
        return geo;
    }
}
