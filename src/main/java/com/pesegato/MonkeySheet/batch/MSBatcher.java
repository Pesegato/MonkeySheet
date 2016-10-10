package com.pesegato.MonkeySheet.batch;


import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.util.BufferUtils;

import java.nio.FloatBuffer;

import static com.jme3.scene.VertexBuffer.Type.*;

public class MSBatcher {

    Mesh mesh;
    FloatBuffer posBuffer, msPosBuffer;
    Vector2f[] texCoord;
    SpriteQuad[] quads;
    int[] indexes;

    public MSBatcher(int size){
        mesh = new Mesh();
        quads = new SpriteQuad[size];
        texCoord = new Vector2f[4 * size];
        indexes = new int[6 * size];

        mesh.setBuffer(Position, 3, BufferUtils.createFloatBuffer(new Vector3f[4 * size]));
        mesh.setBuffer(TexCoord2, 1, BufferUtils.createFloatBuffer(new float[4 * size]));
        posBuffer = (FloatBuffer) mesh.getBuffer(Position).getData();
        msPosBuffer = (FloatBuffer) mesh.getBuffer(TexCoord2).getData();
    }

    public void addQuad(int i, int x, int y){
        quads[i] = new SpriteQuad(i, posBuffer, texCoord, indexes, msPosBuffer);
        quads[i].setPosition(x, y);
    }

    public Geometry makeGeo(){
        mesh.setBuffer(TexCoord, 2, BufferUtils.createFloatBuffer(texCoord));
        mesh.setBuffer(Index, 3, BufferUtils.createIntBuffer(indexes));
        mesh.updateBound();
        return new Geometry("batchedSpatial", mesh);
    }

    public SpriteQuad[] getQuads(){
        return quads;
    }

    public void updateAnim(){
        mesh.setBuffer(TexCoord2, 1, msPosBuffer);
    }
}
