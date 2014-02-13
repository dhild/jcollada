package net.dryanhild.collada.schema15.fx.glsl;

import java.util.Set;

import net.dryanhild.collada.schema15.fx.Sampler;

/**
 * Designates a Shader instance.
 * 
 * The reason for the separation is that a single ShaderProgram may be
 * instantiated multiple times. This does not necessitate recompiling the
 * shader's source, but may override the shader uniforms.
 */
public interface ShaderInstance extends ShaderProgram {

    @Override
    byte[] getUniformData(String uniform);

    @Override
    Set<Sampler> getSamplers();

}
