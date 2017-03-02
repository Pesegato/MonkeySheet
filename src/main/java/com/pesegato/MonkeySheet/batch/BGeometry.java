package com.pesegato.MonkeySheet.batch;

import com.jme3.math.Vector2f;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BGeometry {

    public float QUAD_SIZE = 1;
    float scale = 1;
    float actualSize;

    int bufPosition;
    float x, y;

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
        actualSize=QUAD_SIZE*scale;
    }

    public void setLocalScale(float scale){
        this.scale=scale;
        actualSize=QUAD_SIZE*scale;
        updateBuffer();
    }

    public void removeFromParent(){
        idxData.position(bufPosition * 6);
        idxData.put(0);
        idxData.put(0);
        idxData.put(0);
        idxData.put(0);
        idxData.put(0);
        idxData.put(0);
    }

    public void move(float x, float y) {
        this.x += x;
        this.y += y;
        updateBuffer();
    }

    public Vector2f getLocalTranslation(){
        return new Vector2f(x,y);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        updateBuffer();
    }

    private void updateBuffer() {
        vertexData.position(bufPosition * 12);
        vertices[0] = -actualSize / 2 + x;
        vertices[1] = -actualSize / 2 + y;
        vertices[2] = 0;
        vertices[3] = actualSize / 2 + x;
        vertices[4] = -actualSize / 2 + y;
        vertices[5] = 0;
        vertices[6] = -actualSize / 2 + x;
        vertices[7] = actualSize / 2 + y;
        vertices[8] = 0;
        vertices[9] = actualSize / 2 + x;
        vertices[10] = actualSize / 2 + y;
        vertices[11] = 0;
        vertexData.put(vertices, 0, 12);
    }
}
