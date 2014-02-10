package net.dryanhild.collada.schema14.scene.data;

import java.nio.FloatBuffer;
import java.util.Iterator;

import javax.vecmath.Matrix4d;

import net.dryanhild.collada.data.transform.Transform;

import org.collada.x2005.x11.colladaSchema.MatrixDocument.Matrix;

public class MatrixTransform extends Matrix4d implements Transform {

    public MatrixTransform(Matrix sourceMatrix) {
        Iterator<Double> values = sourceMatrix.getListValue().iterator();

        m00 = values.next().doubleValue();
        m01 = values.next().doubleValue();
        m02 = values.next().doubleValue();
        m03 = values.next().doubleValue();
        m10 = values.next().doubleValue();
        m11 = values.next().doubleValue();
        m12 = values.next().doubleValue();
        m13 = values.next().doubleValue();
        m20 = values.next().doubleValue();
        m21 = values.next().doubleValue();
        m22 = values.next().doubleValue();
        m23 = values.next().doubleValue();
        m30 = values.next().doubleValue();
        m31 = values.next().doubleValue();
        m32 = values.next().doubleValue();
        m33 = values.next().doubleValue();
    }

    @Override
    public Matrix4d toMatrix() {
        return this;
    }

    @Override
    public void putAsColumnMatrix(FloatBuffer buffer) {
        buffer.put(new float[] { //
        (float) m00, (float) m10, (float) m20, (float) m30, //
                (float) m01, (float) m11, (float) m21, (float) m31, //
                (float) m02, (float) m12, (float) m22, (float) m32, //
                (float) m03, (float) m13, (float) m23, (float) m33, //
        });
    }

}
