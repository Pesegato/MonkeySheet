package com.pesegato.MonkeySheet.batch;

import com.jme3.math.Vector2f;

public class BTransform {

    float angle = 0;
    float scale = 1;
    Vector2f center = new Vector2f();
    Vector2f offset = new Vector2f();
    Vector2f trueOffset = new Vector2f();

    public void setLocalRotation(float angle) {
        this.angle = angle;
    }

    public void setLocalScale(float scale) {
        this.scale = scale;
    }

    public void setOffset(Vector2f offset) {
        this.offset = offset;
    }

    public void setTrueOffset(float x, float y) {
        trueOffset.x = x;
        trueOffset.y = y;
    }

    public Vector2f getLocalTranslation() {
        return center;
    }

    public void setPosition(float x, float y) {
        center.x = x;
        center.y = y;
    }

    public void move(float x, float y) {
        center.x += x;
        center.y += y;
    }

}
