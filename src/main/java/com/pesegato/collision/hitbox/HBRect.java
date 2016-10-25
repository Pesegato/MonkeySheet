package com.pesegato.collision.hitbox;


import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
import com.pesegato.collision.Dyn4JShapeControl;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;

import static com.pesegato.MonkeySheet.MSGlobals.SHOW_HITBOX;
import static com.pesegato.MonkeySheet.MSGlobals.SPRITE_SIZE;

public class HBRect {
    String name="";
    float w,h;
    public long id;

    public HBRect(long id, float w, float h){
        this.id=id;
        this.w=w * SPRITE_SIZE;
        this.h=h * SPRITE_SIZE;
    }

    public HBRect(String name, long id, float w, float h){
        this.name=name;
        this.id=id;
        this.w=w * SPRITE_SIZE;
        this.h=h * SPRITE_SIZE;
    }

    public Node getNode(AssetManager assetM, ColorRGBA color){
        Node n=new Node();
        if (SHOW_HITBOX) {
            //geo.setCullHint(Spatial.CullHint.Always);
        //}else {
            Material mat = new Material(assetM, "Common/MatDefs/Misc/Unshaded.j3md");
            ColorRGBA col = color.clone();
            col.a = .5f;
            mat.setColor("Color", col);
            mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
            Geometry geo = new Geometry(name, new Quad(w, h));
            geo.setMaterial(mat);
            geo.setLocalTranslation(-w / 2, -h / 2, 0);
            n.attachChild(geo);
        }
        return n;
    }

    public Convex getConvex(){
        return new Rectangle(new Float(w), new Float(h));
    }

    public Dyn4JShapeControl getControl(){
        return new Dyn4JShapeControl(getConvex(), MassType.INFINITE, this);
    }
}
