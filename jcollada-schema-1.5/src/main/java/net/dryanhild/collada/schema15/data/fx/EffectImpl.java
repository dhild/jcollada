package net.dryanhild.collada.schema14.data.fx;

import java.util.List;

import net.dryanhild.collada.data.fx.Effect;
import net.dryanhild.collada.data.fx.glsl.ShaderProgram;
import net.dryanhild.collada.schema14.data.AbstractAddressableNameable;

import com.google.common.collect.Lists;

public class EffectImpl extends AbstractAddressableNameable implements Effect {

    private final List<ShaderProgram> shaders = Lists.newArrayList();

    public EffectImpl(String id, String name) {
        super(id, name);
    }

    public void addShader(ShaderProgram shader) {
        shaders.add(shader);
    }

    @Override
    public Iterable<ShaderProgram> getGLSLShaders() {
        return shaders;
    }

}
