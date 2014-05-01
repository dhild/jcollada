package net.dryanhild.collada.schema15.parser.fx;

import static org.assertj.core.api.Assertions.assertThat;
import net.dryanhild.collada.data.fx.glsl.FloatUniformType;
import net.dryanhild.collada.data.fx.glsl.ParamType;
import net.dryanhild.collada.data.fx.glsl.ShaderProgram;
import net.dryanhild.collada.data.fx.glsl.ShaderStage;
import net.dryanhild.collada.schema15.data.fx.glsl.Sampler2DUniform;

import org.apache.xmlbeans.XmlException;
import org.collada.x2008.x03.colladaSchema.ProfileGlslType;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class WithGLSLProfileElement {

    private static final String VERTEX_CODE = "uniform vec3 fvLightPosition; \r\n" //
            + "         uniform vec3 fvEyePosition; \r\n"
            + "         varying vec2 Texcoord; \r\n"
            + "         varying vec3 ViewDirection; \r\n"
            + "         varying vec3 LightDirection; \r\n"
            + "         attribute vec3 rm_Binormal; \r\n"
            + "         attribute vec3 rm_Tangent; \r\n"
            + "         void main( void ) \r\n"
            + "         { \r\n"
            + "           gl_Position = ftransform(); \r\n"
            + "           Texcoord = gl_MultiTexCoord0.xy; \r\n"
            + "           vec4 fvObjectPosition = gl_ModelViewMatrix * gl_Vertex; \r\n"
            + "           vec3 fvViewDirection = fvEyePosition - fvObjectPosition.xyz; \r\n"
            + "           vec3 fvLightDirection = fvLightPosition - fvObjectPosition.xyz; \r\n"
            + "           vec3 fvNormal = gl_NormalMatrix * gl_Normal; \r\n"
            + "           vec3 fvBinormal = gl_NormalMatrix * rm_Binormal; \r\n"
            + "           vec3 fvTangent = gl_NormalMatrix * rm_Tangent; \r\n"
            + "           ViewDirection.x = dot( fvTangent, fvViewDirection ); \r\n"
            + "           ViewDirection.y = dot( fvBinormal, fvViewDirection ); \r\n"
            + "           ViewDirection.z = dot( fvNormal, fvViewDirection ); \r\n"
            + "           LightDirection.x = dot( fvTangent, fvLightDirection.xyz ); \r\n"
            + "           LightDirection.y = dot( fvBinormal, fvLightDirection.xyz ); \r\n"
            + "           LightDirection.z = dot( fvNormal, fvLightDirection.xyz ); \r\n" + "         }";

    private static final String FRAGMENT_CODE = "uniform vec4 fvAmbient;\r\n" //
            + "         uniform vec4 fvSpecular;\r\n"
            + "         uniform vec4 fvDiffuse;\r\n"
            + "         uniform float fSpecularPower;\r\n"
            + "         uniform sampler2D baseMap;\r\n"
            + "         uniform sampler2D bumpMap;\r\n"
            + "         varying vec2 Texcoord;\r\n"
            + "         varying vec3 ViewDirection;\r\n"
            + "         varying vec3 LightDirection;\r\n"
            + "         void main( void )\r\n"
            + "         {\r\n"
            + "           vec3 fvLightDirection = normalize( LightDirection );\r\n"
            + "           vec3 fvNormal = normalize( ( texture2D( bumpMap, Texcoord ).xyz * 2.0 ) - 1.0 );\r\n"
            + "           float fNDotL = dot( fvNormal, fvLightDirection ); \r\n"
            + "           vec3 fvReflection = normalize( ( ( 2.0 * fvNormal ) * fNDotL ) - fvLightDirection ); \r\n"
            + "           vec3 fvViewDirection = normalize( ViewDirection );\r\n"
            + "           float fRDotV = max( 0.0, dot( fvReflection, fvViewDirection ) );\r\n"
            + "           vec4 fvBaseColor = texture2D( baseMap, Texcoord );\r\n"
            + "           vec4 fvTotalAmbient = fvAmbient * fvBaseColor; \r\n"
            + "           vec4 fvTotalDiffuse = fvDiffuse * fNDotL * fvBaseColor; \r\n"
            + "           vec4 fvTotalSpecular = fvSpecular * ( pow( fRDotV, fSpecularPower ) );\r\n"
            + "           gl_FragColor = ( fvTotalAmbient + fvTotalDiffuse + fvTotalSpecular );\r\n" + "         }\n";

    private static final String SOURCE =
            "<xml-fragment xmlns:col=\"http://www.collada.org/2008/03/COLLADASchema\">\r\n"
                    + "  <col:code sid=\"Vertex_Program_E0_P0_VP\">"
                    + VERTEX_CODE
                    + "</col:code>\r\n"
                    + "  <col:code sid=\"Fragment_Program_E0_P0_FP\">"
                    + FRAGMENT_CODE
                    + "</col:code>\r\n"
                    + "  <col:newparam sid=\"fSpecularPower_E0_P0\">\r\n"
                    + "    <col:float>25</col:float>\r\n"
                    + "  </col:newparam>\r\n"
                    + "  <col:newparam sid=\"fvAmbient_E0_P0\">\r\n"
                    + "    <col:float4>0.368627 0.368421 0.368421 1</col:float4>\r\n"
                    + "  </col:newparam>\r\n"
                    + "  <col:newparam sid=\"fvDiffuse_E0_P0\">\r\n"
                    + "    <col:float4>0.886275 0.885003 0.885003 1</col:float4>\r\n"
                    + "  </col:newparam>\r\n"
                    + "  <col:newparam sid=\"fvEyePosition_E0_P0\">\r\n"
                    + "    <col:float3>0 0 100</col:float3>\r\n"
                    + "  </col:newparam>\r\n"
                    + "  <col:newparam sid=\"fvLightPosition_E0_P0\">\r\n"
                    + "    <col:float3>-100 100 100</col:float3>\r\n"
                    + "  </col:newparam>\r\n"
                    + "  <col:newparam sid=\"fvSpecular_E0_P0\">\r\n"
                    + "    <col:float4>0.490196 0.488722 0.488722 1</col:float4>\r\n"
                    + "  </col:newparam>\r\n"
                    + "  <col:newparam sid=\"baseMap_Sampler\">\r\n"
                    + "    <col:sampler2D>\r\n"
                    + "      <col:instance_image url=\"base\"/>\r\n"
                    + "      <col:minfilter>LINEAR</col:minfilter>\r\n"
                    + "      <col:magfilter>LINEAR</col:magfilter>\r\n"
                    + "      <col:mipfilter>LINEAR</col:mipfilter>\r\n"
                    + "    </col:sampler2D>\r\n"
                    + "  </col:newparam>\r\n"
                    + "  <col:newparam sid=\"bumpMap_Sampler\">\r\n"
                    + "    <col:sampler2D>\r\n"
                    + "      <col:instance_image url=\"bump\"/>\r\n"
                    + "      <col:minfilter>LINEAR</col:minfilter>\r\n"
                    + "      <col:magfilter>LINEAR</col:magfilter>\r\n"
                    + "      <col:mipfilter>LINEAR</col:mipfilter>\r\n"
                    + "    </col:sampler2D>\r\n"
                    + "  </col:newparam>\r\n"
                    + "  <col:technique sid=\"Textured_Bump_E0_MP_TECH\">\r\n"
                    + "    <col:pass sid=\"Pass_0\">\r\n"
                    + "      <col:program>\r\n"
                    + "        <col:shader stage=\"VERTEX\">\r\n"
                    + "          <col:sources>\r\n"
                    + "            <col:import ref=\"Vertex_Program_E0_P0_VP\"/>\r\n"
                    + "          </col:sources>\r\n"
                    + "        </col:shader>\r\n"
                    + "        <col:shader stage=\"FRAGMENT\">\r\n"
                    + "          <col:sources>\r\n"
                    + "            <col:import ref=\"Fragment_Program_E0_P0_FP\"/>\r\n"
                    + "          </col:sources>\r\n"
                    + "        </col:shader>\r\n"
                    + "        <col:bind_uniform symbol=\"fSpecularPower\">\r\n"
                    + "          <col:param ref=\"fSpecularPower_E0_P0\"/>\r\n"
                    + "        </col:bind_uniform>\r\n"
                    + "        <col:bind_uniform symbol=\"fvAmbient\">\r\n"
                    + "          <col:param ref=\"fvAmbient_E0_P0\"/>\r\n"
                    + "        </col:bind_uniform>\r\n"
                    + "        <col:bind_uniform symbol=\"fvDiffuse\">\r\n"
                    + "          <col:param ref=\"fvDiffuse_E0_P0\"/>\r\n"
                    + "        </col:bind_uniform>\r\n"
                    + "        <col:bind_uniform symbol=\"fvEyePosition\">\r\n"
                    + "          <col:param ref=\"fvEyePosition_E0_P0\"/>\r\n"
                    + "        </col:bind_uniform>\r\n"
                    + "        <col:bind_uniform symbol=\"fvLightPosition\">\r\n"
                    + "          <col:param ref=\"fvLightPosition_E0_P0\"/>\r\n"
                    + "        </col:bind_uniform>\r\n"
                    + "        <col:bind_uniform symbol=\"fvSpecular\">\r\n"
                    + "          <col:param ref=\"fvSpecular_E0_P0\"/>\r\n"
                    + "        </col:bind_uniform>\r\n"
                    + "        <col:bind_uniform symbol=\"baseMap\">\r\n"
                    + "          <col:param ref=\"baseMap_Sampler\"/>\r\n"
                    + "        </col:bind_uniform>\r\n"
                    + "        <col:bind_uniform symbol=\"bumpMap\">\r\n"
                    + "          <col:param ref=\"bumpMap_Sampler\"/>\r\n"
                    + "        </col:bind_uniform>\r\n"
                    + "      </col:program>\r\n" + "    </col:pass>\r\n" + "  </col:technique>\r\n" + "</xml-fragment>";

    private static final String[] uniforms = new String[] {
            "fSpecularPower", "fvAmbient", "fvDiffuse", "fvEyePosition", "fvLightPosition", "fvSpecular" };
    private static final String[] samplers = new String[] { "baseMap", "bumpMap" };

    private ShaderProgram program;

    @BeforeMethod
    public void resetProgram() throws XmlException {
        ServiceLocator locator = ServiceLocatorUtilities.createAndPopulateServiceLocator();
        GLSLProfileParser parser = locator.getService(GLSLProfileParser.class);
        ProfileGlslType profile = ProfileGlslType.Factory.parse(SOURCE);

        program = parser.parse(profile).iterator().next();
    }

    public void shaderHasTwoCodes() {
        assertThat(program.getStages()).containsOnly(ShaderStage.VERTEX, ShaderStage.FRAGMENT);
        for (String line : VERTEX_CODE.split("\\s*\n\\s*")) {
            assertThat(program.getSource(ShaderStage.VERTEX)).contains(line);
        }
        for (String line : FRAGMENT_CODE.split("\\s*\n\\s*")) {
            assertThat(program.getSource(ShaderStage.FRAGMENT)).contains(line);
        }
    }

    public void shaderHasAllUniforms() {
        assertThat(program.getUniforms()).containsOnly(uniforms);
    }

    public void shaderHasAllSamplers() {
        assertThat(program.getSamplers()).containsOnly(samplers);
    }

    public void shaderUniformFloatHasCorrectType() {
        assertThat(program.getUniformDescriptor("fSpecularPower")).isEqualTo(ParamType.FLOAT);
    }

    public void shaderUniformFloat3HasCorrectType() {
        assertThat(program.getUniformDescriptor("fvLightPosition")).isEqualTo(ParamType.FLOAT3);
    }

    public void shaderUniformFloat4HasCorrectType() {
        assertThat(program.getUniformDescriptor("fvDiffuse")).isEqualTo(ParamType.FLOAT4);
    }

    public void shaderUniformFloatsHaveCorrectType() {
        assertThat(program.getUniform("fSpecularPower")).isInstanceOf(FloatUniformType.class);
    }

    public void shaderSamplerHasCorrectClass() {
        assertThat(program.getSampler("baseMap")).isInstanceOf(Sampler2DUniform.class);
    }
}
