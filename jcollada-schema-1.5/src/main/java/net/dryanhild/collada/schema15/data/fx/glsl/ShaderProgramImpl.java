package net.dryanhild.collada.schema14.data.fx.glsl;

import java.util.Map;
import java.util.Set;

import net.dryanhild.collada.data.fx.Sampler;
import net.dryanhild.collada.data.fx.glsl.ParamType;
import net.dryanhild.collada.data.fx.glsl.ShaderProgram;
import net.dryanhild.collada.data.fx.glsl.ShaderStage;
import net.dryanhild.collada.data.fx.glsl.UniformType;
import net.dryanhild.collada.schema14.data.fx.AbstractAddressableAndScoped;

import com.google.common.collect.Maps;

public class ShaderProgramImpl extends AbstractAddressableAndScoped implements ShaderProgram {

    private final Map<ShaderStage, String> shaderSources = Maps.newHashMap();
    private final Map<String, UniformType> shaderUniforms = Maps.newHashMap();
    private final Map<String, Sampler> shaderSamplers = Maps.newHashMap();

    public ShaderProgramImpl(String id, String sid) {
        super(id, sid);
    }

    @Override
    public Set<String> getAttributes() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getAttributeSemantic() {
        // TODO Auto-generated method stub
        return null;
    }

    public void addUniform(String name, UniformType value) {
        shaderUniforms.put(name, value);
    }

    @Override
    public Set<String> getUniforms() {
        return shaderUniforms.keySet();
    }

    @Override
    public UniformType getUniform(String name) {
        return shaderUniforms.get(name);
    }

    @Override
    public ParamType getUniformDescriptor(String uniform) {
        return shaderUniforms.get(uniform).getType();
    }

    @Override
    public byte[] getUniformData(String uniform) {
        return shaderUniforms.get(uniform).getData();
    }

    public void addSampler(String name, Sampler value) {
        shaderSamplers.put(name, value);
    }

    @Override
    public Set<String> getSamplers() {
        return shaderSamplers.keySet();
    }

    @Override
    public Sampler getSampler(String name) {
        return shaderSamplers.get(name);
    }

    public void addSource(ShaderStage stage, String source) {
        shaderSources.put(stage, source);
    }

    @Override
    public Set<ShaderStage> getStages() {
        return shaderSources.keySet();
    }

    @Override
    public String getSource(ShaderStage stage) {
        return shaderSources.get(stage);
    }

}
