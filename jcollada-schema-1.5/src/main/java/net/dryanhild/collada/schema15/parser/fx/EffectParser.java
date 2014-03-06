package net.dryanhild.collada.schema15.parser.fx;

import javax.inject.Inject;
import javax.inject.Named;

import net.dryanhild.collada.data.fx.Effect;
import net.dryanhild.collada.data.fx.glsl.ShaderProgram;
import net.dryanhild.collada.schema15.data.fx.EffectImpl;
import net.dryanhild.collada.schema15.parser.AbstractParser;
import net.dryanhild.collada.schema15.parser.Schema15Parser;

import org.collada.x2008.x03.colladaSchema.EffectType;
import org.collada.x2008.x03.colladaSchema.ProfileGlslType;
import org.jvnet.hk2.annotations.Service;

@Service
@Named
public class EffectParser extends AbstractParser<EffectType, Effect> {

    @Inject
    @Named("GLSLProfileParser")
    private Schema15Parser<ProfileGlslType, Iterable<ShaderProgram>> glslParser;

    @Override
    public Effect parse(EffectType effect) {
        EffectImpl effectData = new EffectImpl(effect.getId(), effect.getName());
        for (ProfileGlslType profile : effect.getProfileGLSLArray()) {
            for (ShaderProgram program : glslParser.parse(profile)) {
                effectData.addShader(program);
            }
        }

        return effectData;
    }

}
