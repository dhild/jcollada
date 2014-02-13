package net.dryanhild.collada.schema15.geometry;

import net.dryanhild.collada.schema15.fx.glsl.ShaderInstance;

public interface GeometryInstance extends Geometry {

    boolean hasShader();

    ShaderInstance getShader();

}
