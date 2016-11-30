package com.pesegato.MonkeySheet.batch;


import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.util.BufferUtils;

import java.nio.FloatBuffer;

import static com.jme3.scene.VertexBuffer.Type.*;

public class BNode {

    Mesh mesh;
    FloatBuffer posBuffer, msPosBuffer;
    Vector2f[] texCoord;
    BGeometry[] quads;
    int[] indexes;

    int slotFreeIdx=0;
    boolean slotBusy[];

    public BNode(int size){
        mesh = new Mesh();
        quads = new BGeometry[size];
        slotBusy = new boolean[size];
        texCoord = new Vector2f[4 * size];
        indexes = new int[6 * size];

        mesh.setBuffer(Position, 3, BufferUtils.createFloatBuffer(new Vector3f[4 * size]));
        mesh.setBuffer(TexCoord2, 1, BufferUtils.createFloatBuffer(new float[4 * size]));
        posBuffer = (FloatBuffer) mesh.getBuffer(Position).getData();
        msPosBuffer = (FloatBuffer) mesh.getBuffer(TexCoord2).getData();
    }

    public int addQuad(float x, float y){
        if (slotBusy.length<=slotFreeIdx){
            System.err.println("No more free slot available for BGeometries on "+this+"!");
            System.exit(-1);
        }
        slotBusy[slotFreeIdx]=true;
        quads[slotFreeIdx] = new BGeometry(slotFreeIdx, posBuffer, texCoord, indexes, msPosBuffer);
        quads[slotFreeIdx].setPosition(x, y);
        slotFreeIdx++;
        return slotFreeIdx-1;
    }

    public void addQuad(int i, int x, int y){
        quads[i] = new BGeometry(i, posBuffer, texCoord, indexes, msPosBuffer);
        quads[i].setPosition(x, y);
    }

    public Geometry makeGeo(){
        mesh.setBuffer(TexCoord, 2, BufferUtils.createFloatBuffer(texCoord));
        mesh.setBuffer(Index, 3, BufferUtils.createIntBuffer(indexes));
        mesh.updateBound();
        return new Geometry("batchedSpatial", mesh);
    }

    public BGeometry[] getQuads(){
        return quads;
    }

    public void updateAnim(){
        mesh.setBuffer(TexCoord2, 1, msPosBuffer);
    }
}
