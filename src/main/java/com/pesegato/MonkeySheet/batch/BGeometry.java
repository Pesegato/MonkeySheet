package com.pesegato.MonkeySheet.batch;

import com.jme3.math.Vector2f;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BGeometry {

    BNode parent;

    public float QUAD_SIZE = 1;
    float actualSize;
    BTransform transform = new BTransform();

    int bufPosition;
    Vector2f ll = new Vector2f();
    Vector2f lr = new Vector2f();
    Vector2f ur = new Vector2f();
    Vector2f ul = new Vector2f();

    FloatBuffer vertexData, msPosData, alphaData;
    IntBuffer idxData;
    float[] vertices, msPos, alpha;

    public BGeometry(BNode parent, int bufPosition, FloatBuffer vertexData, FloatBuffer texData, IntBuffer idxData, FloatBuffer msPosData, FloatBuffer alphaData) {
        this.parent = parent;
        this.bufPosition = bufPosition;
        this.idxData = idxData;
        this.vertexData = vertexData;
        this.msPosData = msPosData;
        this.alphaData = alphaData;
        vertices = new float[12];
        msPos = new float[4];
        alpha = new float[4];
        texData.position(bufPosition * 8);
        texData.put(0);
        texData.put(0);
        texData.put(1);
        texData.put(0);
        texData.put(0);
        texData.put(1);
        texData.put(1);
        texData.put(1);
        idxData.position(6 * bufPosition);
        int indexes[] = new int[6];
        indexes[0] = 2 + 4 * bufPosition;
        indexes[1] = 0 + 4 * bufPosition;
        indexes[2] = 1 + 4 * bufPosition;
        indexes[3] = 1 + 4 * bufPosition;
        indexes[4] = 3 + 4 * bufPosition;
        indexes[5] = 2 + 4 * bufPosition;
        idxData.put(indexes, 0, 6);
        alphaData.position(bufPosition * 4);
        alphaData.put(1);
        alphaData.put(1);
        alphaData.put(1);
        alphaData.put(1);

    }

    public void setSFrame(int newPos) {
        msPosData.position(bufPosition * 4);
        msPos[0] = newPos;
        msPos[1] = newPos;
        msPos[2] = newPos;
        msPos[3] = newPos;
        msPosData.put(msPos, 0, 4);
    }

    public void setAlpha(float a) {
        alphaData.position(bufPosition * 4);
        alphaData.put(a);
        alphaData.put(a);
        alphaData.put(a);
        alphaData.put(a);
    }

    public void setQuadSize(float size) {
        QUAD_SIZE = size;
    }

    public void removeFromParent() {
        idxData.position(bufPosition * 6);
        idxData.put(0);
        idxData.put(0);
        idxData.put(0);
        idxData.put(0);
        idxData.put(0);
        idxData.put(0);
        parent.slotBusy.add(bufPosition);
    }

    public BTransform getTransform() {
        return transform;
    }

    public void applyTransform() {
        vertexData.position(bufPosition * 12);

        float h = (QUAD_SIZE * transform.scale) * 0.5f;

        float cos = com.jme3.math.FastMath.cos(transform.angle);
        float sin = com.jme3.math.FastMath.sin(transform.angle);

        float ch = cos * h;
        float sh = sin * h;

        float ox = transform.offset.x;
        float oy = transform.offset.y;

        float transX = transform.center.x + transform.trueOffset.x + ox - (cos * ox - sin * oy);
        float transY = transform.center.y + transform.trueOffset.y + oy - (sin * ox + cos * oy);

        // ll = (-h, -h): x = -ch + sh + transX, y = -sh - ch + transY
        vertices[0] = -ch + sh + transX;
        vertices[1] = -sh - ch + transY;

        // lr = ( h, -h): x =  ch + sh + transX, y =  sh - ch + transY
        vertices[3] =  ch + sh + transX;
        vertices[4] =  sh - ch + transY;

        // ul = (-h,  h): x = -ch - sh + transX, y = -sh + ch + transY
        vertices[6] = -ch - sh + transX;
        vertices[7] = -sh + ch + transY;

        // ur = ( h,  h): x =  ch - sh + transX, y =  sh + ch + transY
        vertices[9] =  ch - sh + transX;
        vertices[10] = sh + ch + transY;

        vertexData.put(vertices, 0, 12);
    }
    private void manage(Vector2f vx) {
        vx.subtractLocal(transform.offset);
        vx.rotateAroundOrigin(transform.angle, false);
        vx.addLocal(transform.offset);
        vx.addLocal(transform.center);
        vx.addLocal(transform.trueOffset);
    }
}
