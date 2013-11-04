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

import org.junit.Test;

import com.sun.j3d.loaders.Scene;

/**
 * 
 * @author D. Ryan Hild <d.ryan.hild@gmail.com>
 */
public class GeodesicFrameTest {

    @Test
    public void geodesicFrameLoads() throws IOException {
        URL resourceURL = ClassLoader.getSystemResource("resources/GeodesicFrame.dae");
        Reader reader = new InputStreamReader(resourceURL.openStream());
        net.dryanhild.jcollada.ColladaLoader instance = new net.dryanhild.jcollada.ColladaLoader();
        Scene scene = instance.load(reader);
        assertNotNull(scene);
    }
}