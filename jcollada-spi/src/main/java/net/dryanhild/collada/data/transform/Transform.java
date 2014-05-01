package net.dryanhild.collada.data.transform;

import java.nio.FloatBuffer;

public interface Transform {

    Matrix toMatrix();

    void putAsColumnMatrix(FloatBuffer buffer);

}
