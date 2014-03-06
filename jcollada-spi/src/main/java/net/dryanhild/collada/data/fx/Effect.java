package net.dryanhild.collada.data.fx;

import net.dryanhild.collada.data.AddressableType;
import net.dryanhild.collada.data.NameableType;
import net.dryanhild.collada.data.fx.glsl.ShaderProgram;

public interface Effect extends AddressableType, NameableType {

    Iterable<? extends ShaderProgram> getGLSLShaders();

}
