package net.dryanhild.collada.schema15.fx.glsl;

import java.util.Set;

import net.dryanhild.collada.data.AddressableType;
import net.dryanhild.collada.schema15.fx.Sampler;

public interface ShaderProgram extends AddressableType {

    Set<String> getAttributes();

    String getAttributeSemantic();

    Set<String> getUniforms();

    ParamType getUniformDescriptor(String uniform);

    byte[] getUniformData(String uniform);

    Set<Sampler> getSamplers();

    Set<Stage> getStages();

    String getSource(Stage stage);

    public enum Stage {
        VERTEX, FRAGMENT
    }

}
