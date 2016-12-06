package com.pesegato.MonkeySheet.batch;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BGeometry {

    public float QUAD_SIZE = 4;

    int bufPosition;
    float x, y;

    FloatBuffer vertexData, msPosData;
    float[] vertices, msPos;

    public BGeometry(int bufPosition, FloatBuffer vertexData, FloatBuffer texData, IntBuffer idxData, FloatBuffer msPosData) {
        this.bufPosition = bufPosition;
        this.vertexData = vertexData;
        this.msPosData = msPosData;
        vertices = new float[12];
        msPos = new float[4];
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
        int indexes[]=new int[6];
        indexes[0] = 2 + 4 * bufPosition;
        indexes[1] = 0 + 4 * bufPosition;
        indexes[2] = 1 + 4 * bufPosition;
        indexes[3] = 1 + 4 * bufPosition;
        indexes[4] = 3 + 4 * bufPosition;
        indexes[5] = 2 + 4 * bufPosition;
        idxData.put(indexes, 0, 6);

    }

    public void setSFrame(int newPos) {
        msPosData.position(bufPosition * 4);
        msPos[0] = newPos;
        msPos[1] = newPos;
        msPos[2] = newPos;
        msPos[3] = newPos;
        msPosData.put(msPos, 0, 4);
    }

    public void setQuadSize(float size){
        QUAD_SIZE=size;
    }

    public void move(float x, float y) {
        this.x += x;
        this.y += y;
        updateBuffer();
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        updateBuffer();
    }

    private void updateBuffer() {
        vertexData.position(bufPosition * 12);
        vertices[0] = x * 4;
        vertices[1] = y * 4;
        vertices[2] = 0;
        vertices[3] = QUAD_SIZE + x * 4;
        vertices[4] = y * 4;
        vertices[5] = 0;
        vertices[6] = x * 4;
        vertices[7] = QUAD_SIZE + y * 4;
        vertices[8] = 0;
        vertices[9] = QUAD_SIZE + x * 4;
        vertices[10] = QUAD_SIZE + y * 4;
        vertices[11] = 0;
        vertexData.put(vertices, 0, 12);
    }
}
