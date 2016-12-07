package com.pesegato.MonkeySheet.batch;


import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static com.jme3.scene.VertexBuffer.Type.*;

public class BNode {

    Mesh mesh;
    FloatBuffer posData, texData, msPosData;
    VertexBuffer posBuffer, texBuffer, msPosBuffer, idxBuffer;
    IntBuffer idxData;
    BGeometry[] quads;
    int[] indexes;

    int slotFreeIdx=0;
    boolean slotBusy[];

    public BNode(int size){
        mesh = new Mesh();
        quads = new BGeometry[size];
        slotBusy = new boolean[size];
        indexes = new int[6 * size];

        posData = BufferUtils.createFloatBuffer(new Vector3f[4 * size]);
        msPosData = BufferUtils.createFloatBuffer(new float[4 * size]);
        texData = BufferUtils.createFloatBuffer(new Vector2f[4 * size]);
        idxData = BufferUtils.createIntBuffer(indexes);
        mesh.setBuffer(Position, 3, posData);
        mesh.setBuffer(TexCoord, 2, texData);
        mesh.setBuffer(TexCoord2, 1, msPosData);
        mesh.setBuffer(Index, 3, idxData);
        posBuffer=mesh.getBuffer(Position);
        texBuffer=mesh.getBuffer(TexCoord);
        msPosBuffer=mesh.getBuffer(TexCoord2);
        idxBuffer=mesh.getBuffer(Index);
    }

    public void removeAll(){
        posData.position(0);
        for (int i=0;i<quads.length*6;i++){
            idxData.put(0);
        }
        slotFreeIdx=0;
        updateData();
    }

    public int addQuad(float x, float y){
        if (slotBusy.length<=slotFreeIdx){
            System.err.println("No more free slot available for BGeometries on "+this+"!");
            System.exit(-1);
        }
        slotBusy[slotFreeIdx]=true;
        quads[slotFreeIdx] = new BGeometry(slotFreeIdx, posData, texData, idxData, msPosData);
        quads[slotFreeIdx].setPosition(x, y);
        texBuffer.updateData(texData);
        posBuffer.updateData(posData);
        idxBuffer.updateData(idxData);
        slotFreeIdx++;
        return slotFreeIdx-1;
    }

    public void addQuad(int i, int x, int y){
        quads[i] = new BGeometry(i, posData, texData, idxData, msPosData);
        quads[i].setPosition(x, y);
        texBuffer.updateData(texData);
        posBuffer.updateData(posData);
        idxBuffer.updateData(idxData);
    }

    public void updateData(){
        texBuffer.updateData(texData);
        posBuffer.updateData(posData);
        idxBuffer.updateData(idxData);
        msPosBuffer.updateData(msPosData);
    }

    public Geometry makeGeo(){
        idxBuffer.updateData(idxData);
        mesh.updateBound();
        return new Geometry("batchedSpatial", mesh);
    }

    public BGeometry[] getQuads(){
        return quads;
    }

}
