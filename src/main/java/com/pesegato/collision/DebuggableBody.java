package com.pesegato.collision;

import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import org.dyn4j.dynamics.Body;

public abstract class DebuggableBody extends Body {
    public abstract Geometry makeHitboxMarker(AssetManager assetManager, Node n, ColorRGBA colH);
}
