package net.dryanhild.collada.schema14;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import net.dryanhild.collada.data.ColladaDocument;
import net.dryanhild.collada.data.fx.Effect;
import net.dryanhild.collada.hk2spi.ParsingContext;

import org.apache.xmlbeans.impl.common.ReaderInputStream;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class WtihGLSLSource {

    private static final String SOURCE = "<?xml version=\"1.0\"?>\r\n"
            + "<COLLADA xmlns=\"http://www.collada.org/2008/03/COLLADASchema\" version=\"1.5.0\">\r\n" + " <asset>\r\n"
            + "   <created>2007-12-11T14:24:00Z</created>\r\n" + "   <modified>2014-03-02T16:10:00Z</modified>\r\n"
            + " </asset>\r\n" + " <library_materials>\r\n"
            + "   <material id=\"Textured_Bump_E0_MP_MAT\" name=\"Textured_Bump_E0_MP_MAT\">\r\n"
            + "     <instance_effect url=\"#Textured_Bump_E0_MP_FX\">\r\n"
            + "       <technique_hint platform=\"PC-OGL\" profile=\"GLSL\" ref=\"Textured_Bump_E0_MP_TECH\">"
            + "</technique_hint>\r\n" + "       <setparam ref=\"fSpecularPower_E0_P0\">\r\n"
            + "         <float>25</float>\r\n" + "       </setparam>\r\n"
            + "       <setparam ref=\"fvAmbient_E0_P0\">\r\n"
            + "         <float4>0.368627 0.368421 0.368421 1</float4>\r\n" + "       </setparam>\r\n"
            + "       <setparam ref=\"fvDiffuse_E0_P0\">\r\n"
            + "         <float4>0.886275 0.885003 0.885003 1</float4>\r\n" + "       </setparam>\r\n"
            + "       <setparam ref=\"fvEyePosition_E0_P0\">\r\n" + "         <float3>0 0 100</float3>\r\n"
            + "       </setparam>\r\n" + "       <setparam ref=\"fvLightPosition_E0_P0\">\r\n"
            + "         <float3>-100 100 100</float3>\r\n" + "       </setparam>\r\n"
            + "       <setparam ref=\"fvSpecular_E0_P0\">\r\n"
            + "         <float4>0.490196 0.488722 0.488722 1</float4>\r\n" + "       </setparam>\r\n"
            + "     </instance_effect>\r\n" + "   </material>\r\n" + " </library_materials>\r\n"
            + " <library_effects>\r\n" + "   <effect id=\"Textured_Bump_E0_MP_FX\">\r\n" + "     <profile_GLSL>\r\n"
            + "       <code sid=\"Vertex_Program_E0_P0_VP\">\r\n" + "         uniform vec3 fvLightPosition; \r\n"
            + "         uniform vec3 fvEyePosition; \r\n" + "         varying vec2 Texcoord; \r\n"
            + "         varying vec3 ViewDirection; \r\n" + "         varying vec3 LightDirection; \r\n"
            + "         attribute vec3 rm_Binormal; \r\n" + "         attribute vec3 rm_Tangent; \r\n"
            + "         void main( void ) \r\n" + "         { \r\n" + "           gl_Position = ftransform(); \r\n"
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
            + "           LightDirection.z = dot( fvNormal, fvLightDirection.xyz ); \r\n" + "         }\r\n"
            + "       </code>\r\n" + "       <code sid=\"Fragment_Program_E0_P0_FP\">\r\n"
            + "         uniform vec4 fvAmbient;\r\n" + "         uniform vec4 fvSpecular;\r\n"
            + "         uniform vec4 fvDiffuse;\r\n" + "         uniform float fSpecularPower;\r\n"
            + "         uniform sampler2D baseMap;\r\n" + "         uniform sampler2D bumpMap;\r\n"
            + "         varying vec2 Texcoord;\r\n" + "         varying vec3 ViewDirection;\r\n"
            + "         varying vec3 LightDirection;\r\n" + "         void main( void )\r\n" + "         {\r\n"
            + "           vec3 fvLightDirection = normalize( LightDirection );\r\n"
            + "           vec3 fvNormal = normalize( ( texture2D( bumpMap, Texcoord ).xyz * 2.0 ) - 1.0 );\r\n"
            + "           float fNDotL = dot( fvNormal, fvLightDirection ); \r\n"
            + "           vec3 fvReflection = normalize( ( ( 2.0 * fvNormal ) * fNDotL ) - fvLightDirection );"
            + "\r\n" + "           vec3 fvViewDirection = normalize( ViewDirection );\r\n"
            + "           float fRDotV = max( 0.0, dot( fvReflection, fvViewDirection ) );\r\n"
            + "           vec4 fvBaseColor = texture2D( baseMap, Texcoord );\r\n"
            + "           vec4 fvTotalAmbient = fvAmbient * fvBaseColor; \r\n"
            + "           vec4 fvTotalDiffuse = fvDiffuse * fNDotL * fvBaseColor; \r\n"
            + "           vec4 fvTotalSpecular = fvSpecular * ( pow( fRDotV, fSpecularPower ) );\r\n"
            + "           gl_FragColor = ( fvTotalAmbient + fvTotalDiffuse + fvTotalSpecular );\r\n" + "         }\r\n"
            + "       </code>\r\n" + "       <newparam sid=\"fSpecularPower_E0_P0\">\r\n"
            + "         <float>25</float>\r\n" + "       </newparam>\r\n"
            + "       <newparam sid=\"fvAmbient_E0_P0\">\r\n"
            + "         <float4>0.368627 0.368421 0.368421 1</float4>\r\n" + "       </newparam>\r\n"
            + "       <newparam sid=\"fvDiffuse_E0_P0\">\r\n"
            + "         <float4>0.886275 0.885003 0.885003 1</float4>\r\n" + "       </newparam>\r\n"
            + "       <newparam sid=\"fvEyePosition_E0_P0\">\r\n" + "         <float3>0 0 100</float3>\r\n"
            + "       </newparam>\r\n" + "       <newparam sid=\"fvLightPosition_E0_P0\">\r\n"
            + "         <float3>-100 100 100</float3>\r\n" + "       </newparam>\r\n"
            + "       <newparam sid=\"fvSpecular_E0_P0\">\r\n"
            + "         <float4>0.490196 0.488722 0.488722 1</float4>\r\n" + "       </newparam>\r\n"
            + "       <newparam sid=\"baseMap_Sampler\">\r\n" + "         <sampler2D>\r\n"
            + "           <instance_image url=\"base\"></instance_image>\r\n"
            + "           <minfilter>LINEAR</minfilter>\r\n" + "           <magfilter>LINEAR</magfilter>\r\n"
            + "           <mipfilter>LINEAR</mipfilter>\r\n" + "         </sampler2D>\r\n" + "       </newparam>\r\n"
            + "       <newparam sid=\"bumpMap_Sampler\">\r\n" + "         <sampler2D>\r\n"
            + "           <instance_image url=\"bump\"></instance_image>\r\n"
            + "           <minfilter>LINEAR</minfilter>\r\n" + "           <magfilter>LINEAR</magfilter>\r\n"
            + "           <mipfilter>LINEAR</mipfilter>\r\n" + "         </sampler2D>\r\n" + "       </newparam>\r\n"
            + "       <technique sid=\"Textured_Bump_E0_MP_TECH\">\r\n" + "         <pass sid=\"Pass_0\">\r\n"
            + "           <program>\r\n" + "             <shader stage=\"VERTEX\">\r\n"
            + "               <sources>\r\n" + "                 <import ref=\"Vertex_Program_E0_P0_VP\"/>\r\n"
            + "               </sources>\r\n" + "             </shader>\r\n"
            + "             <shader stage=\"FRAGMENT\">\r\n" + "               <sources>\r\n"
            + "                 <import ref=\"Fragment_Program_E0_P0_FP\"/>\r\n" + "               </sources>\r\n"
            + "             </shader> \r\n" + "              <bind_uniform symbol=\"fSpecularPower\"> \r\n"
            + "                <param ref=\"fSpecularPower_E0_P0\"></param> \r\n"
            + "              </bind_uniform> \r\n" + "              <bind_uniform symbol=\"fvAmbient\"> \r\n"
            + "                <param ref=\"fvAmbient_E0_P0\"></param> \r\n" + "              </bind_uniform> \r\n"
            + "              <bind_uniform symbol=\"fvDiffuse\"> \r\n"
            + "                <param ref=\"fvDiffuse_E0_P0\"></param> \r\n" + "              </bind_uniform> \r\n"
            + "              <bind_uniform symbol=\"fvEyePosition\"> \r\n"
            + "                <param ref=\"fvEyePosition_E0_P0\"></param> \r\n" + "              </bind_uniform> \r\n"
            + "              <bind_uniform symbol=\"fvLightPosition\"> \r\n"
            + "                <param ref=\"fvLightPosition_E0_P0\"></param> \r\n"
            + "              </bind_uniform> \r\n" + "              <bind_uniform symbol=\"fvSpecular\"> \r\n"
            + "                <param ref=\"fvSpecular_E0_P0\"></param> \r\n" + "              </bind_uniform> \r\n"
            + "              <bind_uniform symbol=\"baseMap\"> \r\n"
            + "                <param ref=\"baseMap_Sampler\"></param> \r\n" + "              </bind_uniform> \r\n"
            + "              <bind_uniform symbol=\"bumpMap\"> \r\n"
            + "                <param ref=\"bumpMap_Sampler\"></param> \r\n" + "              </bind_uniform>\r\n"
            + "           </program>\r\n" + "         </pass>\r\n" + "       </technique>\r\n"
            + "     </profile_GLSL>\r\n" + "   </effect>\r\n" + " </library_effects>\r\n" + "</COLLADA>";

    private ColladaLoaderSchema15 loader;

    @BeforeMethod
    public void resetLoader() {
        ServiceLocator locator = ServiceLocatorUtilities.createAndPopulateServiceLocator();
        loader = locator.getService(ColladaLoaderSchema15.class);
    }

    private ParsingContext createContext() {
        return new ParsingContext() {
            @Override
            public boolean isValidating() {
                return false;
            }

            @Override
            public byte[] getMainFileHeader() {
                return SOURCE.getBytes();
            }

            @Override
            public InputStream getMainFileInputStream() {
                try {
                    return new ReaderInputStream(new StringReader(SOURCE), "UTF-8");
                } catch (UnsupportedEncodingException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public URL getMainFileURL() {
                try {
                    return new URL("http://localhost/dummy/url");
                } catch (MalformedURLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        };
    }

    public void effectHasShaderProgram() {
        ColladaDocument doc = loader.load(createContext());

        Effect effect = doc.getEffect("#Textured_Bump_E0_MP_FX");
        assertThat(effect.getGLSLShaders()).hasSize(1);
    }
}
