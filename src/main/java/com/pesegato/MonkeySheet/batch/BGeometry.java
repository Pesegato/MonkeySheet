package com.pesegato.MonkeySheet.batch;

import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BGeometry {

    public float QUAD_SIZE = 1;
    float scale = 1;
    float actualSize;

    int bufPosition;
    Vector2f center=new Vector2f();
    Vector2f ll=new Vector2f();
    Vector2f lr=new Vector2f();
    Vector2f ur=new Vector2f();
    Vector2f ul=new Vector2f();

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

    float angle=0;
    public void setLocalRotation(float angle){
        this.angle=angle;
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
        center.x += x;
        center.y += y;
        updateBuffer();
    }

    public Vector2f getLocalTranslation(){
        return center;
    }

    public void setPosition(float x, float y) {
        center.x = x;
        center.y = y;
        updateBuffer();
    }

    private void updateBuffer() {
        vertexData.position(bufPosition * 12);

        ll.x=(-actualSize / 2 + center.x);
        ll.y=(-actualSize / 2 + center.y);

        lr.x=(actualSize / 2 + center.x);
        lr.y=(-actualSize / 2 + center.y);

        ul.x=(-actualSize / 2 + center.x);
        ul.y=(actualSize / 2 + center.y);

        ur.x=(actualSize / 2 + center.x);
        ur.y=(actualSize / 2 + center.y);

        float cos=FastMath.cos(angle);
        float sin=FastMath.sin(angle);

        vertices[0] = ll.x*cos-ll.y*sin;
        vertices[1] = ll.x*sin+ll.y*cos;
        vertices[2] = 0;
        vertices[3] = lr.x*cos-lr.y*sin;
        vertices[4] = lr.x*sin+lr.y*cos;
        vertices[5] = 0;
        vertices[6] = ul.x*cos-ul.y*sin;
        vertices[7] = ul.x*sin+ul.y*cos;
        vertices[8] = 0;
        vertices[9] = ur.x*cos-ur.y*sin;
        vertices[10] = ur.x*sin+ur.y*cos;
        vertices[11] = 0;
        vertexData.put(vertices, 0, 12);

/*
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
        */
    }
}
