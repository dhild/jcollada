package net.dryanhild.collada.schema14.data.fx.glsl;

import net.dryanhild.collada.data.fx.glsl.FloatUniformType;
import net.dryanhild.collada.data.fx.glsl.ParamType;

import java.nio.ByteBuffer;

public class FloatUniform implements FloatUniformType {

    private final float[] value;

    public FloatUniform(float... values) {
        this.value = values;
    }

    @Override
    public float[] getValues() {
        return value;
    }

    @Override
    public ParamType getType() {
        switch (value.length) {
            case 4:
                return ParamType.FLOAT4;
            case 3:
                return ParamType.FLOAT3;
            case 2:
                return ParamType.FLOAT2;
            default:
                return ParamType.FLOAT;
        }
    }

    @Override
    public byte[] getData() {
        ByteBuffer buffer = ByteBuffer.allocate(4 * value.length);
        for (float f : value) {
            buffer.putFloat(f);
        }
        return buffer.array();
    }

}
