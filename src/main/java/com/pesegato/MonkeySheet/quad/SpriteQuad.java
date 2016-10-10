package com.pesegato.MonkeySheet.quad;

import com.jme3.math.Vector2f;

import java.nio.FloatBuffer;

public class SpriteQuad {

    public static final int QUAD_SIZE = 4;

    int bufPosition;
    float x, y;

    FloatBuffer vertexBuffer, msPosBuffer;
    float[] vertices, msPos;
    Vector2f[] texCoord;

    public SpriteQuad(int bufPosition, FloatBuffer vertexBuffer, Vector2f[] texCoord, int[] indexes, FloatBuffer msPosBuffer) {
        this.bufPosition = bufPosition;
        this.vertexBuffer = vertexBuffer;
        this.msPosBuffer = msPosBuffer;
        vertices = new float[12];
        msPos = new float[4];
        this.texCoord = texCoord;
        texCoord[0 + bufPosition * 4] = new Vector2f(0, 0);
        texCoord[1 + bufPosition * 4] = new Vector2f(1 + x * 4, 0);
        texCoord[2 + bufPosition * 4] = new Vector2f(0, 1 + x * 4);
        texCoord[3 + bufPosition * 4] = new Vector2f(1 + x * 4, 1 + y * 4);
        indexes[6 * bufPosition] = 2 + 4 * bufPosition;
        indexes[1 + 6 * bufPosition] = 0 + 4 * bufPosition;
        indexes[2 + 6 * bufPosition] = 1 + 4 * bufPosition;
        indexes[3 + 6 * bufPosition] = 1 + 4 * bufPosition;
        indexes[4 + 6 * bufPosition] = 3 + 4 * bufPosition;
        indexes[5 + 6 * bufPosition] = 2 + 4 * bufPosition;
    }

    public void setSFrame(int newPos) {
        msPosBuffer.position(bufPosition * 4);
        msPos[0] = newPos;
        msPos[1] = newPos;
        msPos[2] = newPos;
        msPos[3] = newPos;
        msPosBuffer.put(msPos, 0, 4);
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
        vertexBuffer.position(bufPosition * 12);
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
        vertexBuffer.put(vertices, 0, 12);
    }
}
