package net.dryanhild.collada.data.transform;

import java.nio.FloatBuffer;

import javax.vecmath.Matrix4d;

public interface Transform {

    Matrix4d toMatrix();

    void putAsColumnMatrix(FloatBuffer buffer);

}
