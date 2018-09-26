package com.pesegato.MonkeySheet.batch;


import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static com.jme3.scene.VertexBuffer.Type.*;

public class BNode {

    Mesh mesh;
    FloatBuffer posData, texData, msPosData, alphaData;
    VertexBuffer posBuffer, texBuffer, msPosBuffer, alphaBuffer, idxBuffer;
    IntBuffer idxData;
    BGeometry[] quads;
    int[] indexes;

    ArrayList<Integer> slotBusy;

    public BNode(int size) {
        mesh = new Mesh();
        quads = new BGeometry[size];
        slotBusy = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            slotBusy.add(i);
        }
        indexes = new int[6 * size];

        posData = BufferUtils.createFloatBuffer(new Vector3f[4 * size]);
        msPosData = BufferUtils.createFloatBuffer(new float[4 * size]);
        alphaData = BufferUtils.createFloatBuffer(new float[4 * size]);
        texData = BufferUtils.createFloatBuffer(new Vector2f[4 * size]);
        idxData = BufferUtils.createIntBuffer(indexes);
        mesh.setBuffer(Position, 3, posData);
        mesh.setBuffer(TexCoord, 2, texData);
        mesh.setBuffer(TexCoord2, 1, msPosData);
        mesh.setBuffer(TexCoord3, 1, alphaData);
        mesh.setBuffer(Index, 3, idxData);
        posBuffer = mesh.getBuffer(Position);
        texBuffer = mesh.getBuffer(TexCoord);
        msPosBuffer = mesh.getBuffer(TexCoord2);
        alphaBuffer = mesh.getBuffer(TexCoord3);
        idxBuffer = mesh.getBuffer(Index);
    }

    public void remove(int idx) {
        idxData.position(idx * 6);
        idxData.put(0);
        idxData.put(0);
        idxData.put(0);
        idxData.put(0);
        idxData.put(0);
        idxData.put(0);
        slotBusy.add(idx);
    }

    public void removeAll() {
        idxData.position(0);
        for (int i = 0; i < quads.length * 6; i++) {
            idxData.put(0);
        }
        slotBusy.clear();
        for (int i = 0; i < quads.length; i++)
            slotBusy.add(i);
        updateData();
    }

    public int addQuad(float x, float y) {
        int idx = getNextAvailableSlot();
        if (idx == -1) {
            System.err.println("No more free slot available for BGeometries on " + this + "!");
            System.exit(-1);
        }
        return addReusableQuad(idx, x, y);
    }

    /**
     * @return the index of a empty slot, or -1 if no slots are free
     */

    public int getNextAvailableSlot() {
        //int p=slotBusy.remove(0);
        //System.out.println("using quad "+p);
        return slotBusy.remove(0);
        /*
        for (int i = 0; i < slotBusy.length; i++) {
            if (!slotBusy[i]) {
                return i;
            }
        }
        return -1;
        */
    }

    public int getDebugFreeSlot() {
        return slotBusy.size();
        /*
        int count = 0;
        for (int i = 0; i < slotBusy.length; i++) {
            if (!slotBusy[i]) {
                count++;
            }
        }
        return count;
        */
    }

    /*
    Allocates the quad at the provided index. Does NOT check the availability!
     */

    public int addReusableQuad(int slotFreeIdx, float x, float y) {
        //slotBusy[slotFreeIdx] = true;
        quads[slotFreeIdx] = new BGeometry(this, slotFreeIdx, posData, texData, idxData, msPosData, alphaData);
        quads[slotFreeIdx].getTransform().setPosition(x, y);
        quads[slotFreeIdx].applyTransform();
        texBuffer.updateData(texData);
        posBuffer.updateData(posData);
        alphaBuffer.updateData(alphaData);
        idxBuffer.updateData(idxData);
        return slotFreeIdx;
    }

    public void addQuad(int i, int x, int y) {
        quads[i] = new BGeometry(this, i, posData, texData, idxData, msPosData, alphaData);
        quads[i].getTransform().setPosition(x, y);
        quads[i].applyTransform();
        texBuffer.updateData(texData);
        posBuffer.updateData(posData);
        alphaBuffer.updateData(alphaData);
        idxBuffer.updateData(idxData);
    }

    public void updateData() {
        texBuffer.updateData(texData);
        posBuffer.updateData(posData);
        alphaBuffer.updateData(alphaData);
        idxBuffer.updateData(idxData);
        msPosBuffer.updateData(msPosData);
    }

    public Geometry makeGeo() {
        idxBuffer.updateData(idxData);
        mesh.updateBound();
        return new Geometry("batchedSpatial", mesh);
    }

    public BGeometry[] getQuads() {
        return quads;
    }

}
