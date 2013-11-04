/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.dryanhild.jcollada.schema141;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import net.dryanhild.jcollada.LoaderContext;

import org.junit.Test;

import com.sun.j3d.loaders.Scene;

/**
 * 
 * @author D. Ryan Hild <d.ryan.hild@gmail.com>
 */
public class GeodesicFrameTest {

    @Test
    public void geodesicFrameLoads() throws IOException {
        URL resourceUrl = ClassLoader.getSystemResource("GeodesicFrame.dae");
        Reader reader = new InputStreamReader(resourceUrl.openStream());
        LoaderContext context = new LoaderContext(resourceUrl, 0, true, null, null);
        ColladaLoader instance = new ColladaLoader();
        Scene scene = instance.load(reader, context);
        assertNotNull(scene);
    }
}