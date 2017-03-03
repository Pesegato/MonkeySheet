package com.pesegato.MonkeySheet.batch;

import com.jme3.math.Vector2f;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BGeometry {

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

    public BGeometry(int bufPosition, FloatBuffer vertexData, FloatBuffer texData, IntBuffer idxData, FloatBuffer msPosData, FloatBuffer alphaData) {
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
    }

    public BTransform getTransform() {
        return transform;
    }

    public void applyTransform() {
        vertexData.position(bufPosition * 12);

        actualSize = QUAD_SIZE * transform.scale;
        ll.x = (-actualSize / 2);
        ll.y = (-actualSize / 2);

        lr.x = (actualSize / 2);
        lr.y = (-actualSize / 2);

        ul.x = (-actualSize / 2);
        ul.y = (actualSize / 2);

        ur.x = (actualSize / 2);
        ur.y = (actualSize / 2);

        manage(ll);
        manage(lr);
        manage(ul);
        manage(ur);
        /*
        ll.subtractLocal(transform.offset);
        ll.rotateAroundOrigin(transform.angle, false);
        ll.addLocal(transform.offset);
        ll.addLocal(transform.center);

        lr.subtractLocal(transform.offset);
        lr.rotateAroundOrigin(transform.angle, false);
        lr.addLocal(transform.offset);
        lr.addLocal(transform.center);

        ul.subtractLocal(transform.offset);
        ul.rotateAroundOrigin(transform.angle, false);
        ul.addLocal(transform.offset);
        ul.addLocal(transform.center);

        ur.subtractLocal(transform.offset);
        ur.rotateAroundOrigin(transform.angle, false);
        ur.addLocal(transform.offset);
        ur.addLocal(transform.center);
        */

        vertices[0] = ll.x;
        vertices[1] = ll.y;
        vertices[2] = 0;
        vertices[3] = lr.x;
        vertices[4] = lr.y;
        vertices[5] = 0;
        vertices[6] = ul.x;
        vertices[7] = ul.y;
        vertices[8] = 0;
        vertices[9] = ur.x;
        vertices[10] = ur.y;
        vertices[11] = 0;
        vertexData.put(vertices, 0, 12);
    }

    private void manage(Vector2f vx){
        vx.subtractLocal(transform.offset);
        vx.rotateAroundOrigin(transform.angle, false);
        vx.addLocal(transform.offset);
        vx.addLocal(transform.center);
    }
}
