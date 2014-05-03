package net.dryanhild.collada.schema14.parser.fx;

import java.util.List;
import java.util.Map;

import javax.inject.Named;

import net.dryanhild.collada.IncorrectFormatException;
import net.dryanhild.collada.data.fx.Sampler;
import net.dryanhild.collada.data.fx.glsl.ShaderProgram;
import net.dryanhild.collada.data.fx.glsl.ShaderStage;
import net.dryanhild.collada.data.fx.glsl.UniformType;
import net.dryanhild.collada.schema14.data.fx.glsl.FloatUniform;
import net.dryanhild.collada.schema14.data.fx.glsl.Sampler2DUniform;
import net.dryanhild.collada.schema14.data.fx.glsl.ShaderProgramImpl;
import net.dryanhild.collada.schema14.parser.AbstractParser;

import org.collada.x2008.x03.colladaSchema.FxCodeType;
import org.collada.x2008.x03.colladaSchema.FxPipelineStageEnum;
import org.collada.x2008.x03.colladaSchema.FxSampler2DType;
import org.collada.x2008.x03.colladaSchema.GlslNewparamType;
import org.collada.x2008.x03.colladaSchema.GlslProgramType;
import org.collada.x2008.x03.colladaSchema.GlslProgramType.BindUniform;
import org.collada.x2008.x03.colladaSchema.GlslShaderType;
import org.collada.x2008.x03.colladaSchema.ProfileGlslType;
import org.collada.x2008.x03.colladaSchema.ProfileGlslType.Technique;
import org.collada.x2008.x03.colladaSchema.ProfileGlslType.Technique.Pass;
import org.glassfish.hk2.api.PerThread;
import org.jvnet.hk2.annotations.Service;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Service
@Named
@PerThread
public class GLSLProfileParser extends AbstractParser<ProfileGlslType, Iterable<ShaderProgram>> {

    private Map<String, String> codesBySid;
    private Map<String, UniformType> uniforms;
    private Map<String, Sampler> samplers;
    private List<ShaderProgram> programs;

    @Override
    public Iterable<ShaderProgram> parse(ProfileGlslType profile) {
        codesBySid = Maps.newHashMap();
        for (FxCodeType type : profile.getCodeArray()) {
            codesBySid.put(type.getSid(), type.getStringValue());
        }

        uniforms = Maps.newHashMap();
        samplers = Maps.newHashMap();
        for (GlslNewparamType newParam : profile.getNewparamArray()) {
            getUniform(newParam);
        }

        programs = Lists.newArrayList();

        for (Technique technique : profile.getTechniqueArray()) {
            programs.add(parseProgram(technique));
        }

        return programs;
    }

    private void getUniform(GlslNewparamType newParam) {
        Element node = (Element) newParam.getDomNode();
        NodeList childNodes = node.getElementsByTagName("*");
        List<Double> values;
        switch (childNodes.item(0).getLocalName()) {
        case "float":
            uniforms.put(newParam.getSid(), new FloatUniform((float) newParam.getFloat()));
            break;
        case "float2":
            values = newParam.getFloat2();
            uniforms.put(newParam.getSid(), new FloatUniform(values.get(0).floatValue(), values.get(1).floatValue()));
            break;
        case "float3":
            values = newParam.getFloat3();
            uniforms.put(newParam.getSid(), new FloatUniform(values.get(0).floatValue(), values.get(1).floatValue(),
                    values.get(2).floatValue()));
            break;
        case "float4":
            values = newParam.getFloat4();
            uniforms.put(newParam.getSid(), new FloatUniform(values.get(0).floatValue(), values.get(1).floatValue(),
                    values.get(2).floatValue(), values.get(3).floatValue()));
            break;
        case "sampler2D":
            FxSampler2DType sampler2DType = newParam.getSampler2D();
            Sampler2DUniform sampler = new Sampler2DUniform();

            samplers.put(newParam.getSid(), sampler);
            break;
        default:
            throw new IncorrectFormatException("Unknown <newparam> element " + newParam);
        }
    }

    private ShaderProgram parseProgram(Technique technique) {
        ShaderProgramImpl program = new ShaderProgramImpl(technique.getId(), technique.getSid());

        for (Pass pass : technique.getPassArray()) {
            GlslProgramType programType = pass.getProgram();
            if (programType == null) {
                continue;
            }

            for (GlslShaderType shader : programType.getShaderArray()) {
                processShader(program, shader);
            }

            for (BindUniform boundUniform : programType.getBindUniformArray()) {
                processUniform(program, boundUniform);
            }
        }

        return program;
    }

    private void processShader(ShaderProgramImpl program, GlslShaderType shader) {
        StringBuffer source = new StringBuffer();
        Element sourcesNode = (Element) shader.getSources().getDomNode();
        NodeList children = sourcesNode.getElementsByTagName("*");
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            switch (child.getLocalName()) {
            case "inline":
                source.append(child.getTextContent());
                break;
            case "import":
                NamedNodeMap attrs = child.getAttributes();
                Node refNode = attrs.getNamedItem("ref");
                source.append(codesBySid.get(refNode.getNodeValue()));
                break;
            default:
                throw new IncorrectFormatException("Unknown <sources> child type " + child.getLocalName());
            }
        }

        ShaderStage stage = null;
        switch (shader.getStage().intValue()) {
        case FxPipelineStageEnum.INT_VERTEX:
            stage = ShaderStage.VERTEX;
            break;
        case FxPipelineStageEnum.INT_GEOMETRY:
            stage = ShaderStage.VERTEX;
            break;
        case FxPipelineStageEnum.INT_FRAGMENT:
            stage = ShaderStage.FRAGMENT;
            break;
        case FxPipelineStageEnum.INT_TESSELLATION:
            stage = ShaderStage.TESSELATION;
            break;
        default:
            throw new IncorrectFormatException("Unknown Shader type " + shader.getStage());
        }
        program.addSource(stage, source.toString());
    }

    private void processUniform(ShaderProgramImpl program, BindUniform uniform) {
        Element uniformElement = (Element) uniform.getDomNode();
        Node childNode = uniformElement.getElementsByTagName("*").item(0);
        switch (childNode.getLocalName()) {
        case "param":
            String paramRef = uniform.getParam().getRef();
            UniformType value = uniforms.get(paramRef);
            if (value != null) {
                program.addUniform(uniform.getSymbol(), value);
            } else {
                Sampler sampler = samplers.get(paramRef);
                program.addSampler(uniform.getSymbol(), sampler);
            }
            break;
        default:
            throw new IncorrectFormatException("Unknown <bind_uniform> type " + uniform);
        }
    }
}
